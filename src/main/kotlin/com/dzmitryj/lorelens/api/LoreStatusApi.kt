package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.generated.FileHashEvent
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.RepositoryStatusFileEvent
import com.dzmitryj.lorelens.ffi.generated.RepositoryStatusRevisionEvent
import com.dzmitryj.lorelens.ffi.generated.lore_file_dirty_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_hash_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_write_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_repository_status_args_t
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreFileHash
import com.dzmitryj.lorelens.model.LoreFileStatus
import com.dzmitryj.lorelens.model.LoreNodeType
import com.dzmitryj.lorelens.model.LoreRepositoryStatus
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.dzmitryj.lorelens.model.LoreRevisionStatus
import java.lang.foreign.Arena
import java.nio.file.Path

private fun Byte.toBoolean(): Boolean = this.toInt() != 0

/**
 * Path conventions differ per verb and the header only documents one of them.
 * Status takes repository-relative paths; the filesystem verbs (dirty, hash)
 * resolve against the process working directory rather than
 * globals.working_directory, so those need absolute paths.
 */
private fun absolute(root: Path, paths: List<String>): List<String> =
    paths.map { path -> root.resolve(path).toString() }

private fun relative(root: Path, path: String): String =
    runCatching { root.relativize(Path.of(path)).toString().replace('\\', '/') }.getOrDefault(path)

/**
 * Status, dirty-marking and content reads. Paths are repository-relative and
 * travel as native string arrays, so there is no argument-length ceiling to
 * work around.
 */
object LoreStatusApi {

    /**
     * @param scan walks the filesystem and reconciles every path, which is
     *   O(repository). The IDE normally avoids it by marking edits dirty as
     *   they happen.
     */
    fun status(
        root: Path,
        paths: List<String> = emptyList(),
        scan: Boolean = false,
        checkDirty: Boolean = false,
    ): LoreRepositoryStatus = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_repository_status_args_t.LAYOUT)

        // Dirty flags are persisted in the staged state, so without this the
        // report omits every file the IDE marked.
        lore_repository_status_args_t.staged(options, 1)
        lore_repository_status_args_t.scan(options, if (scan) 1 else 0)
        lore_repository_status_args_t.check_dirty(options, if (checkDirty) 1 else 0)
        args.writeStrings(lore_repository_status_args_t.paths(options), paths)

        val result = LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_repository_status.invokeExact(globals, options, callback) as Int
            },
            "status",
        )

        LoreRepositoryStatus(
            revision = result.filter<RepositoryStatusRevisionEvent>().lastOrNull()?.toModel(),
            files = result.filter<RepositoryStatusFileEvent>().map { it.toModel() },
        )
    }

    fun markDirty(root: Path, paths: List<String>) {
        if (paths.isEmpty()) return

        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_dirty_args_t.LAYOUT)
            args.writeStrings(lore_file_dirty_args_t.paths(options), absolute(root, paths))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_dirty.invokeExact(globals, options, callback) as Int
                },
                "mark dirty",
            )
        }
    }

    fun hash(root: Path, paths: List<String>): List<LoreFileHash> {
        if (paths.isEmpty()) return emptyList()

        return Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_hash_args_t.LAYOUT)
            args.writeStrings(lore_file_hash_args_t.paths(options), absolute(root, paths))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_hash.invokeExact(globals, options, callback) as Int
                },
                "hash",
            ).filter<FileHashEvent>().map {
                LoreFileHash(relative(root, it.path), it.size, LoreRevisionId(it.hash))
            }
        }
    }

    /**
     * Writes the content of [path] at [revision] to [output] on disk.
     *
     * lore_file_dump reports sizes rather than bytes, so this is the verb that
     * actually yields content.
     */
    fun writeFile(root: Path, path: String, revision: String, output: Path) {
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_write_args_t.LAYOUT)
            args.writeString(lore_file_write_args_t.path(options), path)
            args.writeString(lore_file_write_args_t.revision(options), revision)
            args.writeString(lore_file_write_args_t.output(options), output.toString())

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_write.invokeExact(globals, options, callback) as Int
                },
                "read $path at $revision",
            )
        }
    }

    private fun RepositoryStatusFileEvent.toModel() = LoreFileStatus(
        path = path,
        size = size,
        action = LoreFileAction.of(action),
        nodeType = LoreNodeType.of(type),
        staged = flag_staged.toBoolean(),
        dirty = flag_dirty.toBoolean(),
        conflicted = flag_conflict.toBoolean(),
        conflictUnresolved = flag_conflict_unresolved.toBoolean(),
        fromPath = from_path.ifEmpty { null },
    )

    private fun RepositoryStatusRevisionEvent.toModel() = LoreRevisionStatus(
        branchName = branch_name,
        revision = LoreRevisionId(revision),
        revisionNumber = revision_number,
        stagedRevision = LoreRevisionId(revision_staged),
        localAhead = is_local_ahead.toBoolean(),
        remoteAhead = is_remote_ahead.toBoolean(),
        remoteAvailable = remote_available.toBoolean(),
    )
}
