// Generated from lore.h 0.8.6 by :codegen. Do not edit.
package com.dzmitryj.lorelens.ffi.generated

import com.dzmitryj.lorelens.ffi.LoreCopy
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

sealed interface LoreEvent

data class UnknownEvent(val tag: Int) : LoreEvent

/**
 * A progress update.
 */
data class ProgressEvent(
    val _unused: Int,
) : LoreEvent

/**
 * An error encountered during an operation. A terminal failure is
 * reported on the `Complete` event in its `error` field.
 */
data class ErrorEvent(
    val error_type: Int,
    val error_inner: String,
) : LoreEvent

/**
 * An operation completed.
 */
data class CompleteEvent(
    val status: Int,
    val error: ErrorDetail,
) : LoreEvent

/**
 * A metadata key and value.
 */
data class MetadataEvent(
    val key: String,
    val value: Metadata,
) : LoreEvent

/**
 * A log message.
 */
data class LogEvent(
    val level: Int,
    val category: Int,
    val timestamp: Long,
    val location: String,
    val message: String,
) : LoreEvent

/**
 * The final event of a callback stream.
 */
data class EndEvent(
    val unused: Int,
) : LoreEvent

/**
 * A maintenance message.
 */
data class MaintenanceEvent(
    val message: String,
) : LoreEvent

/**
 * An authentication URL for the user to visit.
 */
data class AuthUrlEvent(
    val url: String,
) : LoreEvent

/**
 * Information about the authenticated user.
 */
data class AuthUserInfoEvent(
    val id: String,
    val name: String,
) : LoreEvent

/**
 * An authentication token for the user.
 */
data class AuthUserTokenEvent(
    val id: String,
    val name: String,
    val token: String,
    val preferred_username: String,
    val flag_service_account: Byte,
    val expires: Long,
) : LoreEvent

/**
 * The resolved identity of the user.
 */
data class AuthIdentityEvent(
    val auth_url: String,
    val resource: String,
    val user_id: String,
    val authorized_domains: String,
    val expires: Long,
    val token: String,
) : LoreEvent

/**
 * A branch was created.
 */
data class BranchCreateEvent(
    val name: String,
    val latest: ByteArray,
    val is_commit: Byte,
) : LoreEvent

/**
 * More than one instance of a branch was found.
 */
data class BranchMultipleInstanceEvent(
    val branch: ByteArray,
    val instance_ids: List<ByteArray>,
    val instance_paths: List<String>,
) : LoreEvent

/**
 * A branch was archived.
 */
data class BranchArchiveEvent(
    val name: String,
) : LoreEvent

/**
 * The start of a branch listing.
 */
data class BranchListBeginEvent(
    val location: Int,
) : LoreEvent

/**
 * One entry in a branch listing.
 */
data class BranchListEntryEvent(
    val location: Int,
    val id: ByteArray,
    val name: String,
    val category: String,
    val latest: ByteArray,
    val stack: List<BranchPoint>,
    val creator: String,
    val created: Long,
    val is_current: Byte,
    val archived: Byte,
) : LoreEvent

/**
 * The end of a branch listing.
 */
data class BranchListEndEvent(
    val location: Int,
    val count: Long,
) : LoreEvent

/**
 * The start of a merge abort.
 */
data class BranchMergeAbortBeginEvent(
    val state_staged_revision: ByteArray,
    val state_current_revision: ByteArray,
) : LoreEvent

/**
 * The end of a merge abort.
 */
data class BranchMergeAbortEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * Information about a branch.
 */
data class BranchInfoEvent(
    val id: ByteArray,
    val name: String,
    val category: String,
    val latest: ByteArray,
    val latest_remote: ByteArray,
    val parent: ByteArray,
    val branch_point: ByteArray,
    val creator: String,
    val created: Long,
    val stack: List<BranchPoint>,
    val archived: Byte,
) : LoreEvent

/**
 * The start of a branch diff.
 */
data class BranchDiffBeginEvent(
    val _unused: Int,
) : LoreEvent

/**
 * The start of the changes in a branch diff.
 */
data class BranchDiffChangeBeginEvent(
    val changes_count: Long,
) : LoreEvent

/**
 * One change in a branch diff.
 */
data class BranchDiffChangeEvent(
    val change: BranchDiffNodeData,
) : LoreEvent

/**
 * The end of the changes in a branch diff.
 */
data class BranchDiffChangeEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * The start of the conflicts in a branch diff.
 */
data class BranchDiffConflictBeginEvent(
    val conflicts_count: Long,
) : LoreEvent

/**
 * One conflict in a branch diff.
 */
data class BranchDiffConflictEvent(
    val source_change: BranchDiffNodeData,
    val target_change: BranchDiffNodeData,
) : LoreEvent

/**
 * The end of the conflicts in a branch diff.
 */
data class BranchDiffConflictEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * The end of a branch diff.
 */
data class BranchDiffEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * One entry in a listing of latest branch revisions.
 */
data class BranchLatestListEntryEvent(
    val branch: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * A file in conflict during a merge.
 */
data class BranchMergeConflictFileEvent(
    val path: String,
) : LoreEvent

/**
 * A link was skipped during a merge.
 */
data class BranchMergeLinkSkippedEvent(
    val link_path: String,
    val repository: ByteArray,
    val reason: Byte,
) : LoreEvent

/**
 * A file conflict was marked unresolved during a merge.
 */
data class BranchMergeUnresolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was marked unresolved during a merge.
 */
data class BranchMergeUnresolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * The start of merging changes into a file.
 */
data class BranchMergeIntoFileBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * Merging changes into a file.
 */
data class BranchMergeIntoFileEvent(
    val path: String,
    val action: Int,
    val size: Long,
    val is_file: Byte,
    val is_directory: Byte,
    val is_link: Byte,
) : LoreEvent

/**
 * The end of merging changes into a file.
 */
data class BranchMergeIntoFileEndEvent(
    val count: Long,
) : LoreEvent

/**
 * The start of merging a fragment.
 */
data class BranchMergeIntoFragmentBeginEvent(
    val fragments: Long,
) : LoreEvent

/**
 * Progress while merging a fragment.
 */
data class BranchMergeIntoFragmentProgressEvent(
    val complete: Long,
    val count: Long,
) : LoreEvent

/**
 * The end of merging a fragment.
 */
data class BranchMergeIntoFragmentEndEvent(
    val fragments: Long,
) : LoreEvent

/**
 * A revision merged into the target.
 */
data class BranchMergeIntoRevisionEvent(
    val revision: ByteArray,
    val revision_number: Long,
) : LoreEvent

/**
 * The start of synchronizing data for a merge.
 */
data class BranchMergeIntoSyncBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * The end of synchronizing data for a merge.
 */
data class BranchMergeIntoSyncEndEvent(
    val count: Long,
) : LoreEvent

/**
 * A file conflict was resolved during a merge.
 */
data class BranchMergeResolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was resolved during a merge.
 */
data class BranchMergeResolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * The start of a merge.
 */
data class BranchMergeStartBeginEvent(
    val branch: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
) : LoreEvent

/**
 * The end of starting a merge.
 */
data class BranchMergeStartEndEvent(
    val stats: RevisionSyncProgressEventData,
    val signature: ByteArray,
    val has_conflicts: Byte,
) : LoreEvent

/**
 * The start of a cherry-pick.
 */
data class CherryPickStartBeginEvent(
    val branch: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
) : LoreEvent

/**
 * The end of starting a cherry-pick.
 */
data class CherryPickStartEndEvent(
    val stats: RevisionSyncProgressEventData,
    val signature: ByteArray,
    val has_conflicts: Byte,
) : LoreEvent

/**
 * The start of a cherry-pick abort.
 */
data class CherryPickAbortBeginEvent(
    val state_staged_revision: ByteArray,
    val state_current_revision: ByteArray,
) : LoreEvent

/**
 * The end of a cherry-pick abort.
 */
data class CherryPickAbortEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * A file in conflict during a cherry-pick.
 */
data class CherryPickConflictFileEvent(
    val path: String,
) : LoreEvent

/**
 * A file conflict was marked unresolved during a cherry-pick.
 */
data class CherryPickUnresolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was marked unresolved during a cherry-pick.
 */
data class CherryPickUnresolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * A file conflict was resolved during a cherry-pick.
 */
data class CherryPickResolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was resolved during a cherry-pick.
 */
data class CherryPickResolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * The start of a revert.
 */
data class RevertStartBeginEvent(
    val branch: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
) : LoreEvent

/**
 * The end of starting a revert.
 */
data class RevertStartEndEvent(
    val stats: RevisionSyncProgressEventData,
    val signature: ByteArray,
    val has_conflicts: Byte,
) : LoreEvent

/**
 * The start of a revert abort.
 */
data class RevertAbortBeginEvent(
    val state_staged_revision: ByteArray,
    val state_current_revision: ByteArray,
) : LoreEvent

/**
 * The end of a revert abort.
 */
data class RevertAbortEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * A file conflict was resolved during a revert.
 */
data class RevertResolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was resolved during a revert.
 */
data class RevertResolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * A file in conflict during a revert.
 */
data class RevertConflictFileEvent(
    val path: String,
) : LoreEvent

/**
 * A file conflict was marked unresolved during a revert.
 */
data class RevertUnresolveFileEvent(
    val path: String,
) : LoreEvent

/**
 * A revision was marked unresolved during a revert.
 */
data class RevertUnresolveRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * A branch was protected.
 */
data class BranchProtectEvent(
    val name: String,
) : LoreEvent

/**
 * A branch was pushed.
 */
data class BranchPushEvent(
    val remote: String,
    val repository: ByteArray,
    val branch: ByteArray,
    val branch_name: String,
    val remote_revision: ByteArray,
    val local_revision: ByteArray,
    val remote_history: Long,
    val local_history: Long,
    val flag_already_pushed: Byte,
    val flag_default: Byte,
    val flag_link: Byte,
    val flag_layer: Byte,
) : LoreEvent

/**
 * The start of updating a revision during a push.
 */
data class BranchPushRevisionUpdateBeginEvent(
    val revision: ByteArray,
    val old_parent: ByteArray,
    val new_parent: ByteArray,
) : LoreEvent

/**
 * The end of updating a revision during a push.
 */
data class BranchPushRevisionUpdateEndEvent(
    val revision: ByteArray,
) : LoreEvent

/**
 * The start of pushing a fragment.
 */
data class BranchPushFragmentBeginEvent(
    val fragments: Long,
    val bytes_total: Long,
) : LoreEvent

/**
 * Progress while pushing a fragment.
 */
data class BranchPushFragmentProgressEvent(
    val complete: Long,
    val count: Long,
    val bytes_transferred: Long,
    val bytes_total: Long,
) : LoreEvent

/**
 * The end of pushing a fragment.
 */
data class BranchPushFragmentEndEvent(
    val fragments: Long,
    val bytes_transferred: Long,
) : LoreEvent

/**
 * The start of creating a branch during a push.
 */
data class BranchPushBranchCreateBeginEvent(
    val local_revision: ByteArray,
) : LoreEvent

/**
 * The end of creating a branch during a push.
 */
data class BranchPushBranchCreateEndEvent(
    val remote_revision: ByteArray,
) : LoreEvent

/**
 * The start of pushing a revision.
 */
data class BranchPushRevisionPushBeginEvent(
    val remote_revision: ByteArray,
    val local_revision: ByteArray,
) : LoreEvent

/**
 * An update while pushing a revision.
 */
data class BranchPushRevisionPushUpdateEvent(
    val old_revision: ByteArray,
    val new_revision: ByteArray,
    val new_revision_number: Long,
) : LoreEvent

/**
 * The end of pushing a revision.
 */
data class BranchPushRevisionPushEndEvent(
    val old_remote_revision: ByteArray,
    val new_remote_revision: ByteArray,
    val new_remote_revision_number: Long,
    val message: String,
    val fast_forward_merged: Byte,
) : LoreEvent

/**
 * A branch was reset.
 */
data class BranchResetEvent(
    val id: ByteArray,
    val name: String,
    val revision: ByteArray,
) : LoreEvent

/**
 * The start of switching the active branch.
 */
data class BranchSwitchBeginEvent(
    val branch: BranchSwitchData,
) : LoreEvent

/**
 * The end of switching the active branch.
 */
data class BranchSwitchEndEvent(
    val branch: BranchSwitchData,
) : LoreEvent

/**
 * A branch was unprotected.
 */
data class BranchUnprotectEvent(
    val name: String,
) : LoreEvent

/**
 * Information about a file.
 */
data class FileInfoEvent(
    val path: String,
    val context: ByteArray,
    val hash: ByteArray,
    val is_file: Byte,
    val is_dir: Byte,
    val flag_modified: Byte,
    val flag_deleted: Byte,
    val flag_added: Byte,
    val flag_conflict: Byte,
    val mode: Short,
    val size: Long,
    val local_size: Long,
    val local_hash: ByteArray,
    val filter_size: Long,
) : LoreEvent

/**
 * A diff for a file.
 */
data class FileDiffEvent(
    val path: String,
    val patch: String,
    val action: Int,
) : LoreEvent

/**
 * The hash of a file.
 */
data class FileHashEvent(
    val path: String,
    val size: Long,
    val hash: ByteArray,
) : LoreEvent

/**
 * The history of a file.
 */
data class FileHistoryEvent(
    val path: String,
    val repository: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
    val parent: List<ByteArray>,
    val address: Address,
    val size: Long,
    val action: Int,
) : LoreEvent

/**
 * A file was written.
 */
data class FileWriteEvent(
    val path: String,
) : LoreEvent

/**
 * A file was obliterated.
 */
data class FileObliterateEvent(
    val address: Address,
    val num_fragments: Long,
    val num_payloads: Long,
) : LoreEvent

/**
 * A dump of a file.
 */
data class FileDumpEvent(
    val address: Address,
    val flags: Int,
    val size_payload: Int,
    val size_content: Long,
    val match_made: Byte,
) : LoreEvent

/**
 * The start of adding file dependencies.
 */
data class FileDependencyAddBeginEvent(
    val path_count: Long,
    val dependency_count: Long,
) : LoreEvent

/**
 * One entry while adding file dependencies.
 */
data class FileDependencyAddEntryEvent(
    val path: String,
    val dependency: String,
    val tags: List<String>,
) : LoreEvent

/**
 * The end of adding file dependencies.
 */
data class FileDependencyAddEndEvent(
    val added_count: Long,
) : LoreEvent

/**
 * The start of removing file dependencies.
 */
data class FileDependencyRemoveBeginEvent(
    val path_count: Long,
    val dependency_count: Long,
) : LoreEvent

/**
 * One entry while removing file dependencies.
 */
data class FileDependencyRemoveEntryEvent(
    val path: String,
    val dependency: String,
    val tags: List<String>,
) : LoreEvent

/**
 * The end of removing file dependencies.
 */
data class FileDependencyRemoveEndEvent(
    val removed_count: Long,
) : LoreEvent

/**
 * The start of listing file dependencies.
 */
data class FileDependencyListBeginEvent(
    val file_count: Long,
) : LoreEvent

/**
 * A file in a dependency listing.
 */
data class FileDependencyListFileEvent(
    val path: String,
    val entry_count: Long,
) : LoreEvent

/**
 * One entry in a file dependency listing.
 */
data class FileDependencyListEntryEvent(
    val path: String,
    val tags: List<String>,
    val depth: Int,
) : LoreEvent

/**
 * The end of the entries for one file in a dependency listing.
 */
data class FileDependencyListFileEndEvent(
    val path: String,
) : LoreEvent

/**
 * The end of listing file dependencies.
 */
data class FileDependencyListEndEvent(
    val total_entry_count: Long,
) : LoreEvent

/**
 * The start of a file reset.
 */
data class FileResetBeginEvent(
    val path_count: Long,
) : LoreEvent

/**
 * Progress during a file reset.
 */
data class FileResetProgressEvent(
    val count: FileResetCountData,
) : LoreEvent

/**
 * The end of a file reset.
 */
data class FileResetEndEvent(
    val count: FileResetCountData,
) : LoreEvent

/**
 * One file reset.
 */
data class FileResetFileEvent(
    val path: String,
    val action: Int,
    val from_path: String,
) : LoreEvent

/**
 * A path was excluded by a filter.
 */
data class FilterExcludeEvent(
    val reason: Byte,
    val path: String,
) : LoreEvent

/**
 * The start of staging files.
 */
data class FileStageBeginEvent(
    val path_count: Long,
) : LoreEvent

/**
 * Progress while staging files.
 */
data class FileStageProgressEvent(
    val count: FileStageCountData,
) : LoreEvent

/**
 * The end of staging files.
 */
data class FileStageEndEvent(
    val count: FileStageCountData,
) : LoreEvent

/**
 * The revision involved in staging files.
 */
data class FileStageRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * One file staged.
 */
data class FileStageFileEvent(
    val from_path: String,
    val path: String,
    val action: Int,
) : LoreEvent

/**
 * The start of unstaging files.
 */
data class FileUnstageBeginEvent(
    val path_count: Long,
) : LoreEvent

/**
 * Progress while unstaging files.
 */
data class FileUnstageProgressEvent(
    val count: FileUnstageCountData,
) : LoreEvent

/**
 * The end of unstaging files.
 */
data class FileUnstageEndEvent(
    val count: FileUnstageCountData,
) : LoreEvent

/**
 * The revision involved in unstaging files.
 */
data class FileUnstageRevisionEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * One file unstaged.
 */
data class FileUnstageFileEvent(
    val path: String,
    val action: Int,
) : LoreEvent

/**
 * A fragment was written.
 */
data class FragmentWriteEvent(
    val fragment: Fragment,
    val deduplicated: Byte,
) : LoreEvent

/**
 * A layer was added.
 */
data class LayerAddEvent(
    val target_path: String,
    val source_repository: ByteArray,
    val source_path: String,
    val metadata: String,
    val revision: ByteArray,
) : LoreEvent

/**
 * One entry in a layer listing.
 */
data class LayerEntryEvent(
    val target_path: String,
    val source_repository: ByteArray,
    val source_path: String,
    val metadata: String,
    val revision: ByteArray,
) : LoreEvent

/**
 * A layer was removed.
 */
data class LayerRemoveEvent(
    val target_path: String,
    val source_repository: ByteArray,
    val source_path: String,
    val revision: ByteArray,
    val forced: Byte,
    val purged: Byte,
    val file_count: Long,
    val directory_count: Long,
    val modified_count: Long,
) : LoreEvent

/**
 * One staged entry in a layer listing.
 */
data class LayerStagedEntryEvent(
    val target_path: String,
    val source_repository: ByteArray,
    val staged_file_count: Long,
) : LoreEvent

/**
 * A link was changed.
 */
data class LinkChangeEvent(
    val link_path: String,
    val link_repository: ByteArray,
    val branch: ByteArray,
    val revision: ByteArray,
    val action: Int,
) : LoreEvent

/**
 * One entry in a link listing.
 */
data class LinkEntryEvent(
    val link: ByteArray,
    val link_node: Int,
    val link_path: String,
    val source_node: Int,
    val source_path: String,
    val branch: ByteArray,
    val branch_name: String,
    val revision: ByteArray,
    val flags: Int,
) : LoreEvent

/**
 * The start of a file lock acquire report.
 */
data class LockFileAcquireBeginEvent(
    val count: Long,
    val ignored: Byte,
) : LoreEvent

/**
 * A file concerning the lock acquire report.
 */
data class LockFileAcquireEvent(
    val path: String,
) : LoreEvent

/**
 * The start of a file lock status report.
 */
data class LockFileStatusBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * One file lock status entry.
 */
data class LockFileStatusEvent(
    val path: String,
    val owner: String,
    val locked_at: Long,
) : LoreEvent

/**
 * The start of a file lock query.
 */
data class LockFileQueryBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * One file lock query result.
 */
data class LockFileQueryEvent(
    val branch: ByteArray,
    val path: String,
    val owner: String,
    val locked_at: Long,
) : LoreEvent

/**
 * The start of a file lock release report.
 */
data class LockFileReleaseBeginEvent(
    val count: Long,
    val not_found: Byte,
) : LoreEvent

/**
 * A file concerning the lock release report.
 */
data class LockFileReleaseEvent(
    val path: String,
) : LoreEvent

/**
 * Metadata was cleared on a file.
 */
data class MetadataClearFileEvent(
    val path: String,
) : LoreEvent

/**
 * Metadata was cleared on a revision.
 */
data class MetadataClearRevisionEvent(
    val revision: ByteArray,
) : LoreEvent

/**
 * A path was ignored.
 */
data class PathIgnoreEvent(
    val path: String,
) : LoreEvent

/**
 * A repository was created.
 */
data class RepositoryCreateEvent(
    val id: ByteArray,
    val name: String,
    val path: String,
) : LoreEvent

/**
 * The start of a repository clone.
 */
data class RepositoryCloneBeginEvent(
    val repository: ByteArray,
    val branch: String,
    val revision: ByteArray,
    val path: String,
) : LoreEvent

/**
 * Progress during a repository clone.
 */
data class RepositoryCloneProgressEvent(
    val count: RepositoryCloneCountData,
) : LoreEvent

/**
 * The end of a repository clone.
 */
data class RepositoryCloneEndEvent(
    val branch: String,
    val revision: ByteArray,
    val count: RepositoryCloneCountData,
) : LoreEvent

/**
 * The start of resolving dependencies.
 */
data class DependencyResolveBeginEvent(
    val root_count: Long,
) : LoreEvent

/**
 * One item while resolving dependencies.
 */
data class DependencyResolveItemEvent(
    val source: String,
    val target: String,
    val tags: List<String>,
) : LoreEvent

/**
 * The end of resolving dependencies.
 */
data class DependencyResolveEndEvent(
    val resolved_count: Long,
) : LoreEvent

/**
 * Data about a repository.
 */
data class RepositoryDataEvent(
    val remote_url: String,
    val id: ByteArray,
    val name: String,
    val description: String,
    val default_branch: ByteArray,
    val default_branch_name: String,
    val creator: String,
    val created: Long,
) : LoreEvent

/**
 * A repository configuration value.
 */
data class RepositoryConfigGetEvent(
    val key: String,
    val value: String,
) : LoreEvent

/**
 * The start of a repository dump.
 */
data class RepositoryDumpBeginEvent(
    val repository: ByteArray,
    val revision: ByteArray,
) : LoreEvent

/**
 * The end of a repository dump.
 */
data class RepositoryDumpEndEvent(
    val _unused: Int,
) : LoreEvent

/**
 * One entry in a repository listing.
 */
data class RepositoryListEntryEvent(
    val id: ByteArray,
    val name: String,
) : LoreEvent

/**
 * An instance of a repository.
 */
data class RepositoryInstanceEvent(
    val instance_id: ByteArray,
    val path: String,
    val branch_name: String,
    val branch: ByteArray,
    val revision: ByteArray,
    val stale: Byte,
) : LoreEvent

/**
 * The start of verifying repository state.
 */
data class RepositoryVerifyStateBeginEvent(
    val _unused: Int,
) : LoreEvent

/**
 * The end of verifying repository state.
 */
data class RepositoryVerifyStateEndEvent(
    val healed_staged_state: ByteArray,
) : LoreEvent

/**
 * A fragment verified in a repository.
 */
data class RepositoryVerifyFragmentEvent(
    val hash: ByteArray,
    val group_index: Int,
    val bucket_index: Int,
    val index_path: String,
    val entry_count: Int,
    val packfile_entry_count: Int,
    val match_count: Int,
    val matches: List<RepositoryVerifyFragmentMatchEventData>,
    val error: String,
) : LoreEvent

/**
 * A fragment match found while verifying a repository.
 */
data class RepositoryVerifyFragmentMatchEvent(
    val slot: Int,
    val index: Int,
    val repository: ByteArray,
    val address_hash: ByteArray,
    val address_context: ByteArray,
    val flags: Int,
    val size_payload: Int,
    val size_content: Long,
    val pack_offset: Int,
    val pack_file: Int,
    val last_access: Long,
) : LoreEvent

/**
 * A remote fragment checked while verifying a repository.
 */
data class RepositoryVerifyFragmentRemoteEvent(
    val address_hash: ByteArray,
    val address_context: ByteArray,
    val corrupted: Byte,
    val healed: Byte,
    val error: String,
) : LoreEvent

/**
 * A dump of repository state.
 */
data class RepositoryStateDumpEvent(
    val revision_number: Long,
    val revision: ByteArray,
    val tree_hash: ByteArray,
    val tree_size: Long,
) : LoreEvent

/**
 * One node in a repository state dump.
 */
data class RepositoryStateDumpNodeEvent(
    val name: String,
    val id: Int,
    val parent: Int,
    val sibling: Int,
    val mode: Short,
    val size: Long,
    val flags: Short,
    val type_data: String,
) : LoreEvent

/**
 * The revision involved in a repository status report.
 */
data class RepositoryStatusRevisionEvent(
    val repository: ByteArray,
    val branch: ByteArray,
    val branch_name: String,
    val revision: ByteArray,
    val revision_number: Long,
    val revision_staged: ByteArray,
    val revision_merged: ByteArray,
    val revision_merged_parent_branch: ByteArray,
    val revision_local: ByteArray,
    val revision_local_number: Long,
    val revision_remote: ByteArray,
    val revision_remote_number: Long,
    val is_local_ahead: Byte,
    val is_remote_ahead: Byte,
    val remote_available: Byte,
    val remote_authorized: Byte,
    val remote_branch_exist: Byte,
) : LoreEvent

/**
 * One file in a repository status report.
 */
data class RepositoryStatusFileEvent(
    val path: String,
    val size: Long,
    val action: Int,
    val type: Int,
    val flag_staged: Byte,
    val flag_merged: Byte,
    val flag_conflict: Byte,
    val flag_conflict_unresolved: Byte,
    val flag_conflict_automerged: Byte,
    val flag_conflict_mine: Byte,
    val flag_conflict_theirs: Byte,
    val flag_dirty: Byte,
    val from_path: String,
) : LoreEvent

/**
 * File counts in a repository status report.
 */
data class RepositoryStatusCountEvent(
    val directories: Long,
    val files: Long,
) : LoreEvent

/**
 * A summary of a repository status report.
 */
data class RepositoryStatusSummaryEvent(
    val adds: Long,
    val deletes: Long,
    val modifies: Long,
    val moves: Long,
    val copies: Long,
) : LoreEvent

/**
 * A result from querying the immutable store.
 */
data class RepositoryStoreImmutableQueryEvent(
    val address: Address,
    val remote: Byte,
    val status: Int,
    val payload: Byte,
    val subfragment: Byte,
    val flags: Int,
    val payload_size: Int,
    val content_size: Long,
) : LoreEvent

/**
 * The start of committing a revision.
 */
data class RevisionCommitBeginEvent(
    val _unused: Int,
) : LoreEvent

/**
 * Progress while committing a revision.
 */
data class RevisionCommitProgressEvent(
    val count: RevisionCommitCountData,
) : LoreEvent

/**
 * The end of committing a revision.
 */
data class RevisionCommitEndEvent(
    val count: RevisionCommitCountData,
) : LoreEvent

/**
 * The committed revision.
 */
data class RevisionCommitRevisionEvent(
    val repository: ByteArray,
    val branch: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
    val parent: ByteArray,
    val parent_other: ByteArray,
) : LoreEvent

/**
 * Information about a revision.
 */
data class RevisionInfoEvent(
    val repository: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
    val parent: List<ByteArray>,
) : LoreEvent

/**
 * A change in a revision's delta.
 */
data class RevisionInfoDeltaEvent(
    val path: String,
    val size: Long,
    val action: Int,
    val flag_modify: Byte,
    val flag_merged: Byte,
    val flag_file: Byte,
) : LoreEvent

/**
 * One file in a revision diff.
 */
data class RevisionDiffFileEvent(
    val path: String,
    val action: Int,
    val old_is_file: Byte,
    val new_is_file: Byte,
    val old_address: Address,
    val new_address: Address,
) : LoreEvent

/**
 * A revision found by a search.
 */
data class RevisionFindEvent(
    val signature: ByteArray,
) : LoreEvent

/**
 * The history of a revision.
 */
data class RevisionHistoryEvent(
    val repository: ByteArray,
    val branch: ByteArray,
) : LoreEvent

/**
 * One entry in a revision history.
 */
data class RevisionHistoryEntryEvent(
    val revision: ByteArray,
    val revision_number: Long,
    val parent: List<ByteArray>,
) : LoreEvent

/**
 * The start of restoring a file from a revision.
 */
data class RevisionRestoreFileBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * A file restored from a revision.
 */
data class RevisionRestoreFileEvent(
    val path: String,
    val action: Int,
    val size: Long,
    val is_file: Byte,
    val is_directory: Byte,
    val is_module: Byte,
) : LoreEvent

/**
 * The end of restoring a file from a revision.
 */
data class RevisionRestoreFileEndEvent(
    val count: Long,
) : LoreEvent

/**
 * The start of restoring a fragment.
 */
data class RevisionRestoreFragmentBeginEvent(
    val fragments: Long,
) : LoreEvent

/**
 * Progress while restoring a fragment.
 */
data class RevisionRestoreFragmentProgressEvent(
    val complete: Long,
    val count: Long,
) : LoreEvent

/**
 * The end of restoring a fragment.
 */
data class RevisionRestoreFragmentEndEvent(
    val fragments: Long,
) : LoreEvent

/**
 * The revision being restored.
 */
data class RevisionRestoreRevisionEvent(
    val revision: ByteArray,
    val revision_number: Long,
) : LoreEvent

/**
 * The start of synchronizing data for a restore.
 */
data class RevisionRestoreSyncBeginEvent(
    val count: Long,
) : LoreEvent

/**
 * The end of synchronizing data for a restore.
 */
data class RevisionRestoreSyncEndEvent(
    val count: Long,
) : LoreEvent

/**
 * A revision was resolved.
 */
data class RevisionResolveEvent(
    val repository: ByteArray,
    val branch: ByteArray,
    val revision: String,
    val revision_number: Long,
    val remote: Byte,
    val local: Byte,
) : LoreEvent

/**
 * The target revision of a sync.
 */
data class RevisionSyncTargetEvent(
    val remote: String,
    val repository: ByteArray,
    val branch: ByteArray,
    val branch_name: String,
    val source_revision: ByteArray,
    val source_revision_number: Long,
    val target_revision: ByteArray,
    val target_revision_number: Long,
    val is_latest: Byte,
    val local: Byte,
) : LoreEvent

/**
 * One file synced.
 */
data class RevisionSyncFileEvent(
    val path: String,
    val size: Long,
    val action: Int,
    val flag_file: Byte,
) : LoreEvent

/**
 * Progress during a revision sync.
 */
data class RevisionSyncProgressEvent(
    val file_update: Long,
    val file_update_total: Long,
    val file_delete: Long,
    val file_delete_total: Long,
    val file_automerge: Long,
    val file_conflict: Long,
    val bytes_update: Long,
    val bytes_update_total: Long,
    val discovery_complete: Byte,
) : LoreEvent

/**
 * The revision involved in a sync.
 */
data class RevisionSyncRevisionEvent(
    val branch: ByteArray,
    val revision: ByteArray,
    val revision_number: Long,
    val flag_merge: Byte,
    val flag_conflict: Byte,
) : LoreEvent

/**
 * A bisect result.
 */
data class RevisionBisectEvent(
    val start_revision_number: Long,
    val target_revision_number: Long,
    val end_revision_number: Long,
    val done: Byte,
) : LoreEvent

/**
 * A notification that a branch was created.
 */
data class NotificationBranchCreatedEvent(
    val branch: ByteArray,
) : LoreEvent

/**
 * A notification that a branch was deleted.
 */
data class NotificationBranchDeletedEvent(
    val branch: ByteArray,
) : LoreEvent

/**
 * A notification that a branch was pushed.
 */
data class NotificationBranchPushedEvent(
    val revision: ByteArray,
    val revision_number: Long,
    val branch: ByteArray,
    val user_id: String,
) : LoreEvent

/**
 * A notification that a resource was locked.
 */
data class NotificationResourceLockedEvent(
    val user_id: String,
    val branch: ByteArray,
    val paths: List<String>,
) : LoreEvent

/**
 * A notification that a resource was unlocked.
 */
data class NotificationResourceUnlockedEvent(
    val user_id: String,
    val branch: ByteArray,
    val paths: List<String>,
) : LoreEvent

/**
 * A notification that a subscription was created.
 */
data class NotificationSubscribedEvent(
    val repository: ByteArray,
) : LoreEvent

/**
 * A notification that a subscription was removed.
 */
data class NotificationUnsubscribedEvent(
    val repository: ByteArray,
) : LoreEvent

/**
 * A shared store was created.
 */
data class SharedStoreCreateEvent(
    val path: String,
) : LoreEvent

/**
 * Information about a shared store.
 */
data class SharedStoreInfoEvent(
    val use_automatically: Byte,
    val remote_urls: List<String>,
    val paths: List<String>,
    val exists: List<Byte>,
) : LoreEvent

/**
 * One staged entry in a link listing.
 */
data class LinkStagedEntryEvent(
    val path: String,
    val repository: ByteArray,
    val staged_file_count: Long,
) : LoreEvent

/**
 * A store was opened.
 */
data class StorageOpenedEvent(
    val handle_id: Long,
) : LoreEvent

/**
 * A put item completed.
 */
data class StoragePutItemCompleteEvent(
    val id: Long,
    val address: Address,
    val error_code: Int,
) : LoreEvent

/**
 * The header for a get item.
 */
data class StorageGetHeaderEvent(
    val id: Long,
    val address: Address,
    val size_content: Long,
) : LoreEvent

/**
 * A data payload for a get item.
 */
data class StorageGetDataEvent(
    val id: Long,
    val address: Address,
    val offset: Long,
    val bytes: ByteArray,
) : LoreEvent

/**
 * A get item completed.
 */
data class StorageGetItemCompleteEvent(
    val id: Long,
    val address: Address,
    val error_code: Int,
) : LoreEvent

/**
 * A get-metadata item completed.
 */
data class StorageGetMetadataItemCompleteEvent(
    val id: Long,
    val address: Address,
    val fragment: Fragment,
    val error_code: Int,
) : LoreEvent

/**
 * A copy item completed.
 */
data class StorageCopyItemCompleteEvent(
    val id: Long,
    val source_partition: ByteArray,
    val target_partition: ByteArray,
    val source_address: Address,
    val target_context: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * An obliterate item completed.
 */
data class StorageObliterateItemCompleteEvent(
    val id: Long,
    val address: Address,
    val local_success: Byte,
    val remote_success: Byte,
    val local_skipped: Byte,
    val remote_skipped: Byte,
    val error_code: Int,
) : LoreEvent

/**
 * An upload item completed.
 */
data class StorageUploadItemCompleteEvent(
    val id: Long,
    val address: Address,
    val already_durable: Byte,
    val error_code: Int,
) : LoreEvent

/**
 * A revision tree was loaded.
 */
data class RevisionTreeLoadedEvent(
    val handle_id: Long,
) : LoreEvent

/**
 * A resolve-path call completed.
 */
data class RevisionTreeResolvePathCompleteEvent(
    val id: Long,
    val node_id: Int,
    val repository: ByteArray,
    val revision: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * One child node in a revision tree.
 */
data class RevisionTreeChildEvent(
    val id: Long,
    val node_id: Int,
    val name: String,
    val parent_id: Int,
    val kind: Int,
    val mode: Short,
    val size: Long,
    val address: Address,
    val error_code: Int,
) : LoreEvent

/**
 * Information about a revision tree node.
 */
data class RevisionTreeNodeInfoEvent(
    val id: Long,
    val node_id: Int,
    val repository: ByteArray,
    val revision: ByteArray,
    val name: String,
    val parent_id: Int,
    val kind: Int,
    val mode: Short,
    val size: Long,
    val address: Address,
    val file_id: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * The path of a revision tree node.
 */
data class RevisionTreeNodePathEvent(
    val id: Long,
    val repository: ByteArray,
    val revision: ByteArray,
    val path: String,
    val error_code: Int,
) : LoreEvent

/**
 * An add call completed.
 */
data class RevisionTreeAddCompleteEvent(
    val id: Long,
    val node_id: Int,
    val error_code: Int,
) : LoreEvent

/**
 * A delete call completed.
 */
data class RevisionTreeDeleteCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

/**
 * A modify call completed.
 */
data class RevisionTreeModifyCompleteEvent(
    val id: Long,
    val node_id: Int,
    val error_code: Int,
) : LoreEvent

/**
 * A move call completed.
 */
data class RevisionTreeMoveCompleteEvent(
    val id: Long,
    val node_id: Int,
    val error_code: Int,
) : LoreEvent

/**
 * A metadata-set call completed.
 */
data class RevisionTreeMetadataSetCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

/**
 * A metadata-get call completed.
 */
data class RevisionTreeMetadataGetCompleteEvent(
    val id: Long,
    val key: String,
    val value: Metadata,
    val error_code: Int,
) : LoreEvent

/**
 * A commit call completed.
 */
data class RevisionTreeCommitCompleteEvent(
    val id: Long,
    val revision_hash: ByteArray,
    val new_tip_hash: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * A close call completed.
 */
data class RevisionTreeCloseCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

/**
 * A list-children call began; carries the target repository and revision.
 */
data class RevisionTreeListChildrenBeginEvent(
    val id: Long,
    val repository: ByteArray,
    val revision: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * Revision-record metadata for a loaded revision tree.
 */
data class RevisionTreeInfoEvent(
    val id: Long,
    val repository: ByteArray,
    val revision: ByteArray,
    val parent: List<ByteArray>,
    val creation_timestamp: Long,
    val author_identity: String,
    val metadata_key_count: Int,
    val error_code: Int,
) : LoreEvent

/**
 * A mutable-load item completed.
 */
data class StorageMutableLoadItemCompleteEvent(
    val id: Long,
    val value: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * A mutable-store item completed.
 */
data class StorageMutableStoreItemCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

/**
 * A mutable-compare-and-swap item completed.
 */
data class StorageMutableCompareAndSwapItemCompleteEvent(
    val id: Long,
    val previous: ByteArray,
    val error_code: Int,
) : LoreEvent

/**
 * One key-value entry in a mutable listing.
 */
data class StorageMutableListEntryEvent(
    val id: Long,
    val key: ByteArray,
    val value: ByteArray,
) : LoreEvent

/**
 * A mutable-list item completed.
 */
data class StorageMutableListItemCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

/**
 * A store eviction pass began.
 */
data class EvictionBeginEvent(
    val target_fragments: Long,
) : LoreEvent

/**
 * One bucket was evicted during a store eviction pass.
 */
data class EvictionProgressEvent(
    val evicted: Long,
) : LoreEvent

/**
 * A store eviction pass ended.
 */
data class EvictionEndEvent(
    val total_evicted: Long,
) : LoreEvent

/**
 * A store compaction pass began.
 */
data class CompactionBeginEvent(
    val target_bytes: Long,
) : LoreEvent

/**
 * One group was compacted during a store compaction pass.
 */
data class CompactionProgressEvent(
    val compacted_bytes: Long,
) : LoreEvent

/**
 * A store compaction pass ended.
 */
data class CompactionEndEvent(
    val total_compacted_bytes: Long,
) : LoreEvent

/**
 * A batch write call on a revision tree completed as a whole.
 */
data class RevisionTreeBatchCompleteEvent(
    val id: Long,
    val error_code: Int,
) : LoreEvent

data class ErrorDetail(
    val error_code: Int,
    val message: String,
    val trace_locations: List<TraceLocation>,
)

data class TraceLocation(
    val file: String,
    val line: Int,
    val column: Int,
    val context: String,
)

data class Address(
    val hash: ByteArray,
    val context: ByteArray,
)

data class Binary(
    val length: Long,
)

data class BranchPoint(
    val branch: ByteArray,
    val revision: ByteArray,
)

data class BranchDiffNodeData(
    val action: Int,
    val path: String,
    val automerged: Byte,
)

data class RevisionSyncProgressEventData(
    val file_update: Long,
    val file_update_total: Long,
    val file_delete: Long,
    val file_delete_total: Long,
    val file_automerge: Long,
    val file_conflict: Long,
    val bytes_update: Long,
    val bytes_update_total: Long,
    val discovery_complete: Byte,
)

data class BranchSwitchData(
    val id: ByteArray,
    val name: String,
    val latest_local: ByteArray,
    val latest_remote: ByteArray,
    val revision: ByteArray,
    val location: Int,
)

data class FileResetCountData(
    val directory_reset_count: Long,
    val directory_delete_count: Long,
    val file_reset_count: Long,
    val file_delete_count: Long,
)

data class FileStageCountData(
    val directory_modify_count: Long,
    val directory_add_count: Long,
    val directory_delete_count: Long,
    val directory_move_count: Long,
    val file_modify_count: Long,
    val file_add_count: Long,
    val file_delete_count: Long,
    val file_move_count: Long,
    val total_count: Long,
)

data class FileUnstageCountData(
    val directory_unstaged_count: Long,
    val directory_discarded_count: Long,
    val file_unstaged_count: Long,
    val file_discarded_count: Long,
    val total_count: Long,
)

data class Fragment(
    val flags: Int,
    val size_payload: Int,
    val size_content: Long,
)

data class RepositoryCloneCountData(
    val file_complete: Long,
    val file_retain: Long,
    val file_replace: Long,
    val file_count: Long,
    val file_inflight: Long,
    val fragment_inflight: Long,
    val bytes_transferred: Long,
    val bytes_total: Long,
    val discovery_complete: Byte,
)

data class RepositoryVerifyFragmentMatchEventData(
    val slot: Int,
    val index: Int,
    val repository: ByteArray,
    val address_hash: ByteArray,
    val address_context: ByteArray,
    val flags: Int,
    val size_payload: Int,
    val size_content: Long,
    val pack_offset: Int,
    val pack_file: Int,
    val last_access: Long,
)

data class RevisionCommitCountData(
    val directory_count: Long,
    val directory_total: Long,
    val file_count: Long,
    val file_total: Long,
    val directory_delete_count: Long,
    val file_modify_count: Long,
    val file_delete_count: Long,
    val bytes_transferred: Long,
    val bytes_total: Long,
    val discovery_complete: Byte,
)

sealed interface Metadata {
    data class Unknown(val tag: Int) : Metadata
    data class AddressValue(val value: Address) : Metadata
    data class BooleanValue(val value: Byte) : Metadata
    data class BinaryValue(val value: Binary) : Metadata
    data class ContextValue(val value: ByteArray) : Metadata
    data class HashValue(val value: ByteArray) : Metadata
    data class NumericValue(val value: Long) : Metadata
    data class StringValue(val value: String) : Metadata
}

private fun readMetadata(struct: MemorySegment): Metadata =
    when (lore_metadata_t.tag(struct)) {
        0 -> Metadata.AddressValue(Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_metadata_t.union(struct).asSlice(0L, 48L)))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_metadata_t.union(struct).asSlice(0L, 48L))))))
        1 -> Metadata.BooleanValue(lore_metadata_t.union(struct).get(ValueLayout.JAVA_BYTE, 0L))
        2 -> Metadata.BinaryValue(Binary(length = lore_binary_t.length(lore_metadata_t.union(struct).asSlice(0L, 16L))))
        3 -> Metadata.ContextValue(LoreCopy.fixedBytes(lore_context_t.data(lore_metadata_t.union(struct).asSlice(0L, 16L))))
        4 -> Metadata.HashValue(LoreCopy.fixedBytes(lore_hash_t.data(lore_metadata_t.union(struct).asSlice(0L, 32L))))
        5 -> Metadata.NumericValue(lore_metadata_t.union(struct).get(ValueLayout.JAVA_LONG, 0L))
        6 -> Metadata.StringValue(LoreCopy.string(lore_metadata_t.union(struct).asSlice(0L, 16L)))
        else -> Metadata.Unknown(lore_metadata_t.tag(struct))
    }


object LoreEventReader {

    fun read(event: MemorySegment): LoreEvent {
        val payload = lore_event_t.union(event)
        return when (lore_event_t.tag(event)) {
            0 -> ProgressEvent(_unused = lore_progress_event_data_t._unused(payload.asSlice(0L, lore_progress_event_data_t.SIZE)))
            1 -> ErrorEvent(error_type = lore_error_event_data_t.error_type(payload.asSlice(0L, lore_error_event_data_t.SIZE)), error_inner = LoreCopy.string(lore_error_event_data_t.error_inner(payload.asSlice(0L, lore_error_event_data_t.SIZE))))
            2 -> CompleteEvent(status = lore_complete_event_data_t.status(payload.asSlice(0L, lore_complete_event_data_t.SIZE)), error = ErrorDetail(error_code = lore_error_detail_t.error_code(lore_complete_event_data_t.error(payload.asSlice(0L, lore_complete_event_data_t.SIZE))), message = LoreCopy.string(lore_error_detail_t.message(lore_complete_event_data_t.error(payload.asSlice(0L, lore_complete_event_data_t.SIZE)))), trace_locations = LoreCopy.array(lore_error_detail_t.trace_locations(lore_complete_event_data_t.error(payload.asSlice(0L, lore_complete_event_data_t.SIZE))), 40L) { TraceLocation(file = LoreCopy.string(lore_trace_location_t.file(it)), line = lore_trace_location_t.line(it), column = lore_trace_location_t.column(it), context = LoreCopy.string(lore_trace_location_t.context(it))) }))
            3 -> MetadataEvent(key = LoreCopy.string(lore_metadata_event_data_t.key(payload.asSlice(0L, lore_metadata_event_data_t.SIZE))), value = readMetadata(lore_metadata_event_data_t.value(payload.asSlice(0L, lore_metadata_event_data_t.SIZE))))
            4 -> LogEvent(level = lore_log_event_data_t.level(payload.asSlice(0L, lore_log_event_data_t.SIZE)), category = lore_log_event_data_t.category(payload.asSlice(0L, lore_log_event_data_t.SIZE)), timestamp = lore_log_event_data_t.timestamp(payload.asSlice(0L, lore_log_event_data_t.SIZE)), location = LoreCopy.string(lore_log_event_data_t.location(payload.asSlice(0L, lore_log_event_data_t.SIZE))), message = LoreCopy.string(lore_log_event_data_t.message(payload.asSlice(0L, lore_log_event_data_t.SIZE))))
            5 -> EndEvent(unused = lore_end_event_data_t.unused(payload.asSlice(0L, lore_end_event_data_t.SIZE)))
            6 -> MaintenanceEvent(message = LoreCopy.string(lore_maintenance_event_data_t.message(payload.asSlice(0L, lore_maintenance_event_data_t.SIZE))))
            7 -> AuthUrlEvent(url = LoreCopy.string(lore_auth_url_event_data_t.url(payload.asSlice(0L, lore_auth_url_event_data_t.SIZE))))
            8 -> AuthUserInfoEvent(id = LoreCopy.string(lore_auth_user_info_event_data_t.id(payload.asSlice(0L, lore_auth_user_info_event_data_t.SIZE))), name = LoreCopy.string(lore_auth_user_info_event_data_t.name(payload.asSlice(0L, lore_auth_user_info_event_data_t.SIZE))))
            9 -> AuthUserTokenEvent(id = LoreCopy.string(lore_auth_user_token_event_data_t.id(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE))), name = LoreCopy.string(lore_auth_user_token_event_data_t.name(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE))), token = LoreCopy.string(lore_auth_user_token_event_data_t.token(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE))), preferred_username = LoreCopy.string(lore_auth_user_token_event_data_t.preferred_username(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE))), flag_service_account = lore_auth_user_token_event_data_t.flag_service_account(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE)), expires = lore_auth_user_token_event_data_t.expires(payload.asSlice(0L, lore_auth_user_token_event_data_t.SIZE)))
            10 -> AuthIdentityEvent(auth_url = LoreCopy.string(lore_auth_identity_event_data_t.auth_url(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE))), resource = LoreCopy.string(lore_auth_identity_event_data_t.resource(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE))), user_id = LoreCopy.string(lore_auth_identity_event_data_t.user_id(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE))), authorized_domains = LoreCopy.string(lore_auth_identity_event_data_t.authorized_domains(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE))), expires = lore_auth_identity_event_data_t.expires(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE)), token = LoreCopy.string(lore_auth_identity_event_data_t.token(payload.asSlice(0L, lore_auth_identity_event_data_t.SIZE))))
            11 -> BranchCreateEvent(name = LoreCopy.string(lore_branch_create_event_data_t.name(payload.asSlice(0L, lore_branch_create_event_data_t.SIZE))), latest = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_create_event_data_t.latest(payload.asSlice(0L, lore_branch_create_event_data_t.SIZE)))), is_commit = lore_branch_create_event_data_t.is_commit(payload.asSlice(0L, lore_branch_create_event_data_t.SIZE)))
            12 -> BranchMultipleInstanceEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_multiple_instance_event_data_t.branch(payload.asSlice(0L, lore_branch_multiple_instance_event_data_t.SIZE)))), instance_ids = LoreCopy.array(lore_branch_multiple_instance_event_data_t.instance_ids(payload.asSlice(0L, lore_branch_multiple_instance_event_data_t.SIZE)), 16L) { LoreCopy.fixedBytes(lore_instance_id_t.data(it)) }, instance_paths = LoreCopy.array(lore_branch_multiple_instance_event_data_t.instance_paths(payload.asSlice(0L, lore_branch_multiple_instance_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            13 -> BranchArchiveEvent(name = LoreCopy.string(lore_branch_archive_event_data_t.name(payload.asSlice(0L, lore_branch_archive_event_data_t.SIZE))))
            14 -> BranchListBeginEvent(location = lore_branch_list_begin_event_data_t.location(payload.asSlice(0L, lore_branch_list_begin_event_data_t.SIZE)))
            15 -> BranchListEntryEvent(location = lore_branch_list_entry_event_data_t.location(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)), id = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_list_entry_event_data_t.id(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)))), name = LoreCopy.string(lore_branch_list_entry_event_data_t.name(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE))), category = LoreCopy.string(lore_branch_list_entry_event_data_t.category(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE))), latest = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_list_entry_event_data_t.latest(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)))), stack = LoreCopy.array(lore_branch_list_entry_event_data_t.stack(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)), 48L) { BranchPoint(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_point_t.branch(it))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_point_t.revision(it)))) }, creator = LoreCopy.string(lore_branch_list_entry_event_data_t.creator(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE))), created = lore_branch_list_entry_event_data_t.created(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)), is_current = lore_branch_list_entry_event_data_t.is_current(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)), archived = lore_branch_list_entry_event_data_t.archived(payload.asSlice(0L, lore_branch_list_entry_event_data_t.SIZE)))
            16 -> BranchListEndEvent(location = lore_branch_list_end_event_data_t.location(payload.asSlice(0L, lore_branch_list_end_event_data_t.SIZE)), count = lore_branch_list_end_event_data_t.count(payload.asSlice(0L, lore_branch_list_end_event_data_t.SIZE)))
            17 -> BranchMergeAbortBeginEvent(state_staged_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_abort_begin_event_data_t.state_staged_revision(payload.asSlice(0L, lore_branch_merge_abort_begin_event_data_t.SIZE)))), state_current_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_abort_begin_event_data_t.state_current_revision(payload.asSlice(0L, lore_branch_merge_abort_begin_event_data_t.SIZE)))))
            18 -> BranchMergeAbortEndEvent(_unused = lore_branch_merge_abort_end_event_data_t._unused(payload.asSlice(0L, lore_branch_merge_abort_end_event_data_t.SIZE)))
            19 -> BranchInfoEvent(id = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_info_event_data_t.id(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))), name = LoreCopy.string(lore_branch_info_event_data_t.name(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE))), category = LoreCopy.string(lore_branch_info_event_data_t.category(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE))), latest = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_info_event_data_t.latest(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))), latest_remote = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_info_event_data_t.latest_remote(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))), parent = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_info_event_data_t.parent(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))), branch_point = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_info_event_data_t.branch_point(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))), creator = LoreCopy.string(lore_branch_info_event_data_t.creator(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE))), created = lore_branch_info_event_data_t.created(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)), stack = LoreCopy.array(lore_branch_info_event_data_t.stack(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)), 48L) { BranchPoint(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_point_t.branch(it))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_point_t.revision(it)))) }, archived = lore_branch_info_event_data_t.archived(payload.asSlice(0L, lore_branch_info_event_data_t.SIZE)))
            20 -> BranchDiffBeginEvent(_unused = lore_branch_diff_begin_event_data_t._unused(payload.asSlice(0L, lore_branch_diff_begin_event_data_t.SIZE)))
            21 -> BranchDiffChangeBeginEvent(changes_count = lore_branch_diff_change_begin_event_data_t.changes_count(payload.asSlice(0L, lore_branch_diff_change_begin_event_data_t.SIZE)))
            22 -> BranchDiffChangeEvent(change = BranchDiffNodeData(action = lore_branch_diff_node_data_t.action(lore_branch_diff_change_event_data_t.change(payload.asSlice(0L, lore_branch_diff_change_event_data_t.SIZE))), path = LoreCopy.string(lore_branch_diff_node_data_t.path(lore_branch_diff_change_event_data_t.change(payload.asSlice(0L, lore_branch_diff_change_event_data_t.SIZE)))), automerged = lore_branch_diff_node_data_t.automerged(lore_branch_diff_change_event_data_t.change(payload.asSlice(0L, lore_branch_diff_change_event_data_t.SIZE)))))
            23 -> BranchDiffChangeEndEvent(_unused = lore_branch_diff_change_end_event_data_t._unused(payload.asSlice(0L, lore_branch_diff_change_end_event_data_t.SIZE)))
            24 -> BranchDiffConflictBeginEvent(conflicts_count = lore_branch_diff_conflict_begin_event_data_t.conflicts_count(payload.asSlice(0L, lore_branch_diff_conflict_begin_event_data_t.SIZE)))
            25 -> BranchDiffConflictEvent(source_change = BranchDiffNodeData(action = lore_branch_diff_node_data_t.action(lore_branch_diff_conflict_event_data_t.source_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE))), path = LoreCopy.string(lore_branch_diff_node_data_t.path(lore_branch_diff_conflict_event_data_t.source_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE)))), automerged = lore_branch_diff_node_data_t.automerged(lore_branch_diff_conflict_event_data_t.source_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE)))), target_change = BranchDiffNodeData(action = lore_branch_diff_node_data_t.action(lore_branch_diff_conflict_event_data_t.target_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE))), path = LoreCopy.string(lore_branch_diff_node_data_t.path(lore_branch_diff_conflict_event_data_t.target_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE)))), automerged = lore_branch_diff_node_data_t.automerged(lore_branch_diff_conflict_event_data_t.target_change(payload.asSlice(0L, lore_branch_diff_conflict_event_data_t.SIZE)))))
            26 -> BranchDiffConflictEndEvent(_unused = lore_branch_diff_conflict_end_event_data_t._unused(payload.asSlice(0L, lore_branch_diff_conflict_end_event_data_t.SIZE)))
            27 -> BranchDiffEndEvent(_unused = lore_branch_diff_end_event_data_t._unused(payload.asSlice(0L, lore_branch_diff_end_event_data_t.SIZE)))
            28 -> BranchLatestListEntryEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_latest_list_entry_event_data_t.branch(payload.asSlice(0L, lore_branch_latest_list_entry_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_latest_list_entry_event_data_t.revision(payload.asSlice(0L, lore_branch_latest_list_entry_event_data_t.SIZE)))))
            29 -> BranchMergeConflictFileEvent(path = LoreCopy.string(lore_branch_merge_conflict_file_event_data_t.path(payload.asSlice(0L, lore_branch_merge_conflict_file_event_data_t.SIZE))))
            30 -> BranchMergeLinkSkippedEvent(link_path = LoreCopy.string(lore_branch_merge_link_skipped_event_data_t.link_path(payload.asSlice(0L, lore_branch_merge_link_skipped_event_data_t.SIZE))), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_branch_merge_link_skipped_event_data_t.repository(payload.asSlice(0L, lore_branch_merge_link_skipped_event_data_t.SIZE)))), reason = lore_branch_merge_link_skipped_event_data_t.reason(payload.asSlice(0L, lore_branch_merge_link_skipped_event_data_t.SIZE)))
            31 -> BranchMergeUnresolveFileEvent(path = LoreCopy.string(lore_branch_merge_unresolve_file_event_data_t.path(payload.asSlice(0L, lore_branch_merge_unresolve_file_event_data_t.SIZE))))
            32 -> BranchMergeUnresolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_branch_merge_unresolve_revision_event_data_t.repository(payload.asSlice(0L, lore_branch_merge_unresolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_unresolve_revision_event_data_t.revision(payload.asSlice(0L, lore_branch_merge_unresolve_revision_event_data_t.SIZE)))))
            33 -> BranchMergeIntoFileBeginEvent(count = lore_branch_merge_into_file_begin_event_data_t.count(payload.asSlice(0L, lore_branch_merge_into_file_begin_event_data_t.SIZE)))
            34 -> BranchMergeIntoFileEvent(path = LoreCopy.string(lore_branch_merge_into_file_event_data_t.path(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE))), action = lore_branch_merge_into_file_event_data_t.action(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE)), size = lore_branch_merge_into_file_event_data_t.size(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE)), is_file = lore_branch_merge_into_file_event_data_t.is_file(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE)), is_directory = lore_branch_merge_into_file_event_data_t.is_directory(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE)), is_link = lore_branch_merge_into_file_event_data_t.is_link(payload.asSlice(0L, lore_branch_merge_into_file_event_data_t.SIZE)))
            35 -> BranchMergeIntoFileEndEvent(count = lore_branch_merge_into_file_end_event_data_t.count(payload.asSlice(0L, lore_branch_merge_into_file_end_event_data_t.SIZE)))
            36 -> BranchMergeIntoFragmentBeginEvent(fragments = lore_branch_merge_into_fragment_begin_event_data_t.fragments(payload.asSlice(0L, lore_branch_merge_into_fragment_begin_event_data_t.SIZE)))
            37 -> BranchMergeIntoFragmentProgressEvent(complete = lore_branch_merge_into_fragment_progress_event_data_t.complete(payload.asSlice(0L, lore_branch_merge_into_fragment_progress_event_data_t.SIZE)), count = lore_branch_merge_into_fragment_progress_event_data_t.count(payload.asSlice(0L, lore_branch_merge_into_fragment_progress_event_data_t.SIZE)))
            38 -> BranchMergeIntoFragmentEndEvent(fragments = lore_branch_merge_into_fragment_end_event_data_t.fragments(payload.asSlice(0L, lore_branch_merge_into_fragment_end_event_data_t.SIZE)))
            39 -> BranchMergeIntoRevisionEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_into_revision_event_data_t.revision(payload.asSlice(0L, lore_branch_merge_into_revision_event_data_t.SIZE)))), revision_number = lore_branch_merge_into_revision_event_data_t.revision_number(payload.asSlice(0L, lore_branch_merge_into_revision_event_data_t.SIZE)))
            40 -> BranchMergeIntoSyncBeginEvent(count = lore_branch_merge_into_sync_begin_event_data_t.count(payload.asSlice(0L, lore_branch_merge_into_sync_begin_event_data_t.SIZE)))
            41 -> BranchMergeIntoSyncEndEvent(count = lore_branch_merge_into_sync_end_event_data_t.count(payload.asSlice(0L, lore_branch_merge_into_sync_end_event_data_t.SIZE)))
            42 -> BranchMergeResolveFileEvent(path = LoreCopy.string(lore_branch_merge_resolve_file_event_data_t.path(payload.asSlice(0L, lore_branch_merge_resolve_file_event_data_t.SIZE))))
            43 -> BranchMergeResolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_branch_merge_resolve_revision_event_data_t.repository(payload.asSlice(0L, lore_branch_merge_resolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_resolve_revision_event_data_t.revision(payload.asSlice(0L, lore_branch_merge_resolve_revision_event_data_t.SIZE)))))
            44 -> BranchMergeStartBeginEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_merge_start_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_merge_start_begin_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_start_begin_event_data_t.revision(payload.asSlice(0L, lore_branch_merge_start_begin_event_data_t.SIZE)))), revision_number = lore_branch_merge_start_begin_event_data_t.revision_number(payload.asSlice(0L, lore_branch_merge_start_begin_event_data_t.SIZE)))
            45 -> BranchMergeStartEndEvent(stats = RevisionSyncProgressEventData(file_update = lore_revision_sync_progress_event_data_t.file_update(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), file_update_total = lore_revision_sync_progress_event_data_t.file_update_total(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), file_delete = lore_revision_sync_progress_event_data_t.file_delete(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), file_delete_total = lore_revision_sync_progress_event_data_t.file_delete_total(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), file_automerge = lore_revision_sync_progress_event_data_t.file_automerge(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), file_conflict = lore_revision_sync_progress_event_data_t.file_conflict(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), bytes_update = lore_revision_sync_progress_event_data_t.bytes_update(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), bytes_update_total = lore_revision_sync_progress_event_data_t.bytes_update_total(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE))), discovery_complete = lore_revision_sync_progress_event_data_t.discovery_complete(lore_branch_merge_start_end_event_data_t.stats(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE)))), signature = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_merge_start_end_event_data_t.signature(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE)))), has_conflicts = lore_branch_merge_start_end_event_data_t.has_conflicts(payload.asSlice(0L, lore_branch_merge_start_end_event_data_t.SIZE)))
            46 -> CherryPickStartBeginEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_cherry_pick_start_begin_event_data_t.branch(payload.asSlice(0L, lore_cherry_pick_start_begin_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_start_begin_event_data_t.revision(payload.asSlice(0L, lore_cherry_pick_start_begin_event_data_t.SIZE)))), revision_number = lore_cherry_pick_start_begin_event_data_t.revision_number(payload.asSlice(0L, lore_cherry_pick_start_begin_event_data_t.SIZE)))
            47 -> CherryPickStartEndEvent(stats = RevisionSyncProgressEventData(file_update = lore_revision_sync_progress_event_data_t.file_update(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), file_update_total = lore_revision_sync_progress_event_data_t.file_update_total(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), file_delete = lore_revision_sync_progress_event_data_t.file_delete(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), file_delete_total = lore_revision_sync_progress_event_data_t.file_delete_total(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), file_automerge = lore_revision_sync_progress_event_data_t.file_automerge(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), file_conflict = lore_revision_sync_progress_event_data_t.file_conflict(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), bytes_update = lore_revision_sync_progress_event_data_t.bytes_update(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), bytes_update_total = lore_revision_sync_progress_event_data_t.bytes_update_total(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE))), discovery_complete = lore_revision_sync_progress_event_data_t.discovery_complete(lore_cherry_pick_start_end_event_data_t.stats(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE)))), signature = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_start_end_event_data_t.signature(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE)))), has_conflicts = lore_cherry_pick_start_end_event_data_t.has_conflicts(payload.asSlice(0L, lore_cherry_pick_start_end_event_data_t.SIZE)))
            48 -> CherryPickAbortBeginEvent(state_staged_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_abort_begin_event_data_t.state_staged_revision(payload.asSlice(0L, lore_cherry_pick_abort_begin_event_data_t.SIZE)))), state_current_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_abort_begin_event_data_t.state_current_revision(payload.asSlice(0L, lore_cherry_pick_abort_begin_event_data_t.SIZE)))))
            49 -> CherryPickAbortEndEvent(_unused = lore_cherry_pick_abort_end_event_data_t._unused(payload.asSlice(0L, lore_cherry_pick_abort_end_event_data_t.SIZE)))
            50 -> CherryPickConflictFileEvent(path = LoreCopy.string(lore_cherry_pick_conflict_file_event_data_t.path(payload.asSlice(0L, lore_cherry_pick_conflict_file_event_data_t.SIZE))))
            51 -> CherryPickUnresolveFileEvent(path = LoreCopy.string(lore_cherry_pick_unresolve_file_event_data_t.path(payload.asSlice(0L, lore_cherry_pick_unresolve_file_event_data_t.SIZE))))
            52 -> CherryPickUnresolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_cherry_pick_unresolve_revision_event_data_t.repository(payload.asSlice(0L, lore_cherry_pick_unresolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_unresolve_revision_event_data_t.revision(payload.asSlice(0L, lore_cherry_pick_unresolve_revision_event_data_t.SIZE)))))
            53 -> CherryPickResolveFileEvent(path = LoreCopy.string(lore_cherry_pick_resolve_file_event_data_t.path(payload.asSlice(0L, lore_cherry_pick_resolve_file_event_data_t.SIZE))))
            54 -> CherryPickResolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_cherry_pick_resolve_revision_event_data_t.repository(payload.asSlice(0L, lore_cherry_pick_resolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_cherry_pick_resolve_revision_event_data_t.revision(payload.asSlice(0L, lore_cherry_pick_resolve_revision_event_data_t.SIZE)))))
            55 -> RevertStartBeginEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revert_start_begin_event_data_t.branch(payload.asSlice(0L, lore_revert_start_begin_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_start_begin_event_data_t.revision(payload.asSlice(0L, lore_revert_start_begin_event_data_t.SIZE)))), revision_number = lore_revert_start_begin_event_data_t.revision_number(payload.asSlice(0L, lore_revert_start_begin_event_data_t.SIZE)))
            56 -> RevertStartEndEvent(stats = RevisionSyncProgressEventData(file_update = lore_revision_sync_progress_event_data_t.file_update(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), file_update_total = lore_revision_sync_progress_event_data_t.file_update_total(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), file_delete = lore_revision_sync_progress_event_data_t.file_delete(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), file_delete_total = lore_revision_sync_progress_event_data_t.file_delete_total(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), file_automerge = lore_revision_sync_progress_event_data_t.file_automerge(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), file_conflict = lore_revision_sync_progress_event_data_t.file_conflict(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), bytes_update = lore_revision_sync_progress_event_data_t.bytes_update(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), bytes_update_total = lore_revision_sync_progress_event_data_t.bytes_update_total(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE))), discovery_complete = lore_revision_sync_progress_event_data_t.discovery_complete(lore_revert_start_end_event_data_t.stats(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE)))), signature = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_start_end_event_data_t.signature(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE)))), has_conflicts = lore_revert_start_end_event_data_t.has_conflicts(payload.asSlice(0L, lore_revert_start_end_event_data_t.SIZE)))
            57 -> RevertAbortBeginEvent(state_staged_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_abort_begin_event_data_t.state_staged_revision(payload.asSlice(0L, lore_revert_abort_begin_event_data_t.SIZE)))), state_current_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_abort_begin_event_data_t.state_current_revision(payload.asSlice(0L, lore_revert_abort_begin_event_data_t.SIZE)))))
            58 -> RevertAbortEndEvent(_unused = lore_revert_abort_end_event_data_t._unused(payload.asSlice(0L, lore_revert_abort_end_event_data_t.SIZE)))
            59 -> RevertResolveFileEvent(path = LoreCopy.string(lore_revert_resolve_file_event_data_t.path(payload.asSlice(0L, lore_revert_resolve_file_event_data_t.SIZE))))
            60 -> RevertResolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revert_resolve_revision_event_data_t.repository(payload.asSlice(0L, lore_revert_resolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_resolve_revision_event_data_t.revision(payload.asSlice(0L, lore_revert_resolve_revision_event_data_t.SIZE)))))
            61 -> RevertConflictFileEvent(path = LoreCopy.string(lore_revert_conflict_file_event_data_t.path(payload.asSlice(0L, lore_revert_conflict_file_event_data_t.SIZE))))
            62 -> RevertUnresolveFileEvent(path = LoreCopy.string(lore_revert_unresolve_file_event_data_t.path(payload.asSlice(0L, lore_revert_unresolve_file_event_data_t.SIZE))))
            63 -> RevertUnresolveRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revert_unresolve_revision_event_data_t.repository(payload.asSlice(0L, lore_revert_unresolve_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revert_unresolve_revision_event_data_t.revision(payload.asSlice(0L, lore_revert_unresolve_revision_event_data_t.SIZE)))))
            64 -> BranchProtectEvent(name = LoreCopy.string(lore_branch_protect_event_data_t.name(payload.asSlice(0L, lore_branch_protect_event_data_t.SIZE))))
            65 -> BranchPushEvent(remote = LoreCopy.string(lore_branch_push_event_data_t.remote(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE))), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_branch_push_event_data_t.repository(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_push_event_data_t.branch(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)))), branch_name = LoreCopy.string(lore_branch_push_event_data_t.branch_name(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE))), remote_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_event_data_t.remote_revision(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)))), local_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_event_data_t.local_revision(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)))), remote_history = lore_branch_push_event_data_t.remote_history(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)), local_history = lore_branch_push_event_data_t.local_history(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)), flag_already_pushed = lore_branch_push_event_data_t.flag_already_pushed(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)), flag_default = lore_branch_push_event_data_t.flag_default(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)), flag_link = lore_branch_push_event_data_t.flag_link(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)), flag_layer = lore_branch_push_event_data_t.flag_layer(payload.asSlice(0L, lore_branch_push_event_data_t.SIZE)))
            66 -> BranchPushRevisionUpdateBeginEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_update_begin_event_data_t.revision(payload.asSlice(0L, lore_branch_push_revision_update_begin_event_data_t.SIZE)))), old_parent = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_update_begin_event_data_t.old_parent(payload.asSlice(0L, lore_branch_push_revision_update_begin_event_data_t.SIZE)))), new_parent = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_update_begin_event_data_t.new_parent(payload.asSlice(0L, lore_branch_push_revision_update_begin_event_data_t.SIZE)))))
            67 -> BranchPushRevisionUpdateEndEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_update_end_event_data_t.revision(payload.asSlice(0L, lore_branch_push_revision_update_end_event_data_t.SIZE)))))
            68 -> BranchPushFragmentBeginEvent(fragments = lore_branch_push_fragment_begin_event_data_t.fragments(payload.asSlice(0L, lore_branch_push_fragment_begin_event_data_t.SIZE)), bytes_total = lore_branch_push_fragment_begin_event_data_t.bytes_total(payload.asSlice(0L, lore_branch_push_fragment_begin_event_data_t.SIZE)))
            69 -> BranchPushFragmentProgressEvent(complete = lore_branch_push_fragment_progress_event_data_t.complete(payload.asSlice(0L, lore_branch_push_fragment_progress_event_data_t.SIZE)), count = lore_branch_push_fragment_progress_event_data_t.count(payload.asSlice(0L, lore_branch_push_fragment_progress_event_data_t.SIZE)), bytes_transferred = lore_branch_push_fragment_progress_event_data_t.bytes_transferred(payload.asSlice(0L, lore_branch_push_fragment_progress_event_data_t.SIZE)), bytes_total = lore_branch_push_fragment_progress_event_data_t.bytes_total(payload.asSlice(0L, lore_branch_push_fragment_progress_event_data_t.SIZE)))
            70 -> BranchPushFragmentEndEvent(fragments = lore_branch_push_fragment_end_event_data_t.fragments(payload.asSlice(0L, lore_branch_push_fragment_end_event_data_t.SIZE)), bytes_transferred = lore_branch_push_fragment_end_event_data_t.bytes_transferred(payload.asSlice(0L, lore_branch_push_fragment_end_event_data_t.SIZE)))
            71 -> BranchPushBranchCreateBeginEvent(local_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_branch_create_begin_event_data_t.local_revision(payload.asSlice(0L, lore_branch_push_branch_create_begin_event_data_t.SIZE)))))
            72 -> BranchPushBranchCreateEndEvent(remote_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_branch_create_end_event_data_t.remote_revision(payload.asSlice(0L, lore_branch_push_branch_create_end_event_data_t.SIZE)))))
            73 -> BranchPushRevisionPushBeginEvent(remote_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_begin_event_data_t.remote_revision(payload.asSlice(0L, lore_branch_push_revision_push_begin_event_data_t.SIZE)))), local_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_begin_event_data_t.local_revision(payload.asSlice(0L, lore_branch_push_revision_push_begin_event_data_t.SIZE)))))
            74 -> BranchPushRevisionPushUpdateEvent(old_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_update_event_data_t.old_revision(payload.asSlice(0L, lore_branch_push_revision_push_update_event_data_t.SIZE)))), new_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_update_event_data_t.new_revision(payload.asSlice(0L, lore_branch_push_revision_push_update_event_data_t.SIZE)))), new_revision_number = lore_branch_push_revision_push_update_event_data_t.new_revision_number(payload.asSlice(0L, lore_branch_push_revision_push_update_event_data_t.SIZE)))
            75 -> BranchPushRevisionPushEndEvent(old_remote_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_end_event_data_t.old_remote_revision(payload.asSlice(0L, lore_branch_push_revision_push_end_event_data_t.SIZE)))), new_remote_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_push_revision_push_end_event_data_t.new_remote_revision(payload.asSlice(0L, lore_branch_push_revision_push_end_event_data_t.SIZE)))), new_remote_revision_number = lore_branch_push_revision_push_end_event_data_t.new_remote_revision_number(payload.asSlice(0L, lore_branch_push_revision_push_end_event_data_t.SIZE)), message = LoreCopy.string(lore_branch_push_revision_push_end_event_data_t.message(payload.asSlice(0L, lore_branch_push_revision_push_end_event_data_t.SIZE))), fast_forward_merged = lore_branch_push_revision_push_end_event_data_t.fast_forward_merged(payload.asSlice(0L, lore_branch_push_revision_push_end_event_data_t.SIZE)))
            76 -> BranchResetEvent(id = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_reset_event_data_t.id(payload.asSlice(0L, lore_branch_reset_event_data_t.SIZE)))), name = LoreCopy.string(lore_branch_reset_event_data_t.name(payload.asSlice(0L, lore_branch_reset_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_reset_event_data_t.revision(payload.asSlice(0L, lore_branch_reset_event_data_t.SIZE)))))
            77 -> BranchSwitchBeginEvent(branch = BranchSwitchData(id = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_switch_data_t.id(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE))))), name = LoreCopy.string(lore_branch_switch_data_t.name(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE)))), latest_local = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.latest_local(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE))))), latest_remote = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.latest_remote(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE))))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.revision(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE))))), location = lore_branch_switch_data_t.location(lore_branch_switch_begin_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_begin_event_data_t.SIZE)))))
            78 -> BranchSwitchEndEvent(branch = BranchSwitchData(id = LoreCopy.fixedBytes(lore_context_t.data(lore_branch_switch_data_t.id(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE))))), name = LoreCopy.string(lore_branch_switch_data_t.name(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE)))), latest_local = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.latest_local(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE))))), latest_remote = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.latest_remote(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE))))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_branch_switch_data_t.revision(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE))))), location = lore_branch_switch_data_t.location(lore_branch_switch_end_event_data_t.branch(payload.asSlice(0L, lore_branch_switch_end_event_data_t.SIZE)))))
            79 -> BranchUnprotectEvent(name = LoreCopy.string(lore_branch_unprotect_event_data_t.name(payload.asSlice(0L, lore_branch_unprotect_event_data_t.SIZE))))
            80 -> FileInfoEvent(path = LoreCopy.string(lore_file_info_event_data_t.path(payload.asSlice(0L, lore_file_info_event_data_t.SIZE))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_file_info_event_data_t.context(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)))), hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_info_event_data_t.hash(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)))), is_file = lore_file_info_event_data_t.is_file(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), is_dir = lore_file_info_event_data_t.is_dir(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), flag_modified = lore_file_info_event_data_t.flag_modified(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), flag_deleted = lore_file_info_event_data_t.flag_deleted(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), flag_added = lore_file_info_event_data_t.flag_added(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), flag_conflict = lore_file_info_event_data_t.flag_conflict(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), mode = lore_file_info_event_data_t.mode(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), size = lore_file_info_event_data_t.size(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), local_size = lore_file_info_event_data_t.local_size(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)), local_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_info_event_data_t.local_hash(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)))), filter_size = lore_file_info_event_data_t.filter_size(payload.asSlice(0L, lore_file_info_event_data_t.SIZE)))
            81 -> FileDiffEvent(path = LoreCopy.string(lore_file_diff_event_data_t.path(payload.asSlice(0L, lore_file_diff_event_data_t.SIZE))), patch = LoreCopy.string(lore_file_diff_event_data_t.patch(payload.asSlice(0L, lore_file_diff_event_data_t.SIZE))), action = lore_file_diff_event_data_t.action(payload.asSlice(0L, lore_file_diff_event_data_t.SIZE)))
            82 -> FileHashEvent(path = LoreCopy.string(lore_file_hash_event_data_t.path(payload.asSlice(0L, lore_file_hash_event_data_t.SIZE))), size = lore_file_hash_event_data_t.size(payload.asSlice(0L, lore_file_hash_event_data_t.SIZE)), hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_hash_event_data_t.hash(payload.asSlice(0L, lore_file_hash_event_data_t.SIZE)))))
            83 -> FileHistoryEvent(path = LoreCopy.string(lore_file_history_event_data_t.path(payload.asSlice(0L, lore_file_history_event_data_t.SIZE))), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_file_history_event_data_t.repository(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_history_event_data_t.revision(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)))), revision_number = lore_file_history_event_data_t.revision_number(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)), parent = LoreCopy.inlineArray(lore_file_history_event_data_t.parent(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)), 2, 32L) { LoreCopy.fixedBytes(lore_hash_t.data(it)) }, address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_file_history_event_data_t.address(payload.asSlice(0L, lore_file_history_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_file_history_event_data_t.address(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)))))), size = lore_file_history_event_data_t.size(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)), action = lore_file_history_event_data_t.action(payload.asSlice(0L, lore_file_history_event_data_t.SIZE)))
            84 -> FileWriteEvent(path = LoreCopy.string(lore_file_write_event_data_t.path(payload.asSlice(0L, lore_file_write_event_data_t.SIZE))))
            85 -> FileObliterateEvent(address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_file_obliterate_event_data_t.address(payload.asSlice(0L, lore_file_obliterate_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_file_obliterate_event_data_t.address(payload.asSlice(0L, lore_file_obliterate_event_data_t.SIZE)))))), num_fragments = lore_file_obliterate_event_data_t.num_fragments(payload.asSlice(0L, lore_file_obliterate_event_data_t.SIZE)), num_payloads = lore_file_obliterate_event_data_t.num_payloads(payload.asSlice(0L, lore_file_obliterate_event_data_t.SIZE)))
            86 -> FileDumpEvent(address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_file_dump_event_data_t.address(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_file_dump_event_data_t.address(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE)))))), flags = lore_file_dump_event_data_t.flags(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE)), size_payload = lore_file_dump_event_data_t.size_payload(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE)), size_content = lore_file_dump_event_data_t.size_content(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE)), match_made = lore_file_dump_event_data_t.match_made(payload.asSlice(0L, lore_file_dump_event_data_t.SIZE)))
            87 -> FileDependencyAddBeginEvent(path_count = lore_file_dependency_add_begin_event_data_t.path_count(payload.asSlice(0L, lore_file_dependency_add_begin_event_data_t.SIZE)), dependency_count = lore_file_dependency_add_begin_event_data_t.dependency_count(payload.asSlice(0L, lore_file_dependency_add_begin_event_data_t.SIZE)))
            88 -> FileDependencyAddEntryEvent(path = LoreCopy.string(lore_file_dependency_add_entry_event_data_t.path(payload.asSlice(0L, lore_file_dependency_add_entry_event_data_t.SIZE))), dependency = LoreCopy.string(lore_file_dependency_add_entry_event_data_t.dependency(payload.asSlice(0L, lore_file_dependency_add_entry_event_data_t.SIZE))), tags = LoreCopy.array(lore_file_dependency_add_entry_event_data_t.tags(payload.asSlice(0L, lore_file_dependency_add_entry_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            89 -> FileDependencyAddEndEvent(added_count = lore_file_dependency_add_end_event_data_t.added_count(payload.asSlice(0L, lore_file_dependency_add_end_event_data_t.SIZE)))
            90 -> FileDependencyRemoveBeginEvent(path_count = lore_file_dependency_remove_begin_event_data_t.path_count(payload.asSlice(0L, lore_file_dependency_remove_begin_event_data_t.SIZE)), dependency_count = lore_file_dependency_remove_begin_event_data_t.dependency_count(payload.asSlice(0L, lore_file_dependency_remove_begin_event_data_t.SIZE)))
            91 -> FileDependencyRemoveEntryEvent(path = LoreCopy.string(lore_file_dependency_remove_entry_event_data_t.path(payload.asSlice(0L, lore_file_dependency_remove_entry_event_data_t.SIZE))), dependency = LoreCopy.string(lore_file_dependency_remove_entry_event_data_t.dependency(payload.asSlice(0L, lore_file_dependency_remove_entry_event_data_t.SIZE))), tags = LoreCopy.array(lore_file_dependency_remove_entry_event_data_t.tags(payload.asSlice(0L, lore_file_dependency_remove_entry_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            92 -> FileDependencyRemoveEndEvent(removed_count = lore_file_dependency_remove_end_event_data_t.removed_count(payload.asSlice(0L, lore_file_dependency_remove_end_event_data_t.SIZE)))
            93 -> FileDependencyListBeginEvent(file_count = lore_file_dependency_list_begin_event_data_t.file_count(payload.asSlice(0L, lore_file_dependency_list_begin_event_data_t.SIZE)))
            94 -> FileDependencyListFileEvent(path = LoreCopy.string(lore_file_dependency_list_file_event_data_t.path(payload.asSlice(0L, lore_file_dependency_list_file_event_data_t.SIZE))), entry_count = lore_file_dependency_list_file_event_data_t.entry_count(payload.asSlice(0L, lore_file_dependency_list_file_event_data_t.SIZE)))
            95 -> FileDependencyListEntryEvent(path = LoreCopy.string(lore_file_dependency_list_entry_event_data_t.path(payload.asSlice(0L, lore_file_dependency_list_entry_event_data_t.SIZE))), tags = LoreCopy.array(lore_file_dependency_list_entry_event_data_t.tags(payload.asSlice(0L, lore_file_dependency_list_entry_event_data_t.SIZE)), 16L) { LoreCopy.string(it) }, depth = lore_file_dependency_list_entry_event_data_t.depth(payload.asSlice(0L, lore_file_dependency_list_entry_event_data_t.SIZE)))
            96 -> FileDependencyListFileEndEvent(path = LoreCopy.string(lore_file_dependency_list_file_end_event_data_t.path(payload.asSlice(0L, lore_file_dependency_list_file_end_event_data_t.SIZE))))
            97 -> FileDependencyListEndEvent(total_entry_count = lore_file_dependency_list_end_event_data_t.total_entry_count(payload.asSlice(0L, lore_file_dependency_list_end_event_data_t.SIZE)))
            98 -> FileResetBeginEvent(path_count = lore_file_reset_begin_event_data_t.path_count(payload.asSlice(0L, lore_file_reset_begin_event_data_t.SIZE)))
            99 -> FileResetProgressEvent(count = FileResetCountData(directory_reset_count = lore_file_reset_count_data_t.directory_reset_count(lore_file_reset_progress_event_data_t.count(payload.asSlice(0L, lore_file_reset_progress_event_data_t.SIZE))), directory_delete_count = lore_file_reset_count_data_t.directory_delete_count(lore_file_reset_progress_event_data_t.count(payload.asSlice(0L, lore_file_reset_progress_event_data_t.SIZE))), file_reset_count = lore_file_reset_count_data_t.file_reset_count(lore_file_reset_progress_event_data_t.count(payload.asSlice(0L, lore_file_reset_progress_event_data_t.SIZE))), file_delete_count = lore_file_reset_count_data_t.file_delete_count(lore_file_reset_progress_event_data_t.count(payload.asSlice(0L, lore_file_reset_progress_event_data_t.SIZE)))))
            100 -> FileResetEndEvent(count = FileResetCountData(directory_reset_count = lore_file_reset_count_data_t.directory_reset_count(lore_file_reset_end_event_data_t.count(payload.asSlice(0L, lore_file_reset_end_event_data_t.SIZE))), directory_delete_count = lore_file_reset_count_data_t.directory_delete_count(lore_file_reset_end_event_data_t.count(payload.asSlice(0L, lore_file_reset_end_event_data_t.SIZE))), file_reset_count = lore_file_reset_count_data_t.file_reset_count(lore_file_reset_end_event_data_t.count(payload.asSlice(0L, lore_file_reset_end_event_data_t.SIZE))), file_delete_count = lore_file_reset_count_data_t.file_delete_count(lore_file_reset_end_event_data_t.count(payload.asSlice(0L, lore_file_reset_end_event_data_t.SIZE)))))
            101 -> FileResetFileEvent(path = LoreCopy.string(lore_file_reset_file_event_data_t.path(payload.asSlice(0L, lore_file_reset_file_event_data_t.SIZE))), action = lore_file_reset_file_event_data_t.action(payload.asSlice(0L, lore_file_reset_file_event_data_t.SIZE)), from_path = LoreCopy.string(lore_file_reset_file_event_data_t.from_path(payload.asSlice(0L, lore_file_reset_file_event_data_t.SIZE))))
            102 -> FilterExcludeEvent(reason = lore_filter_exclude_event_data_t.reason(payload.asSlice(0L, lore_filter_exclude_event_data_t.SIZE)), path = LoreCopy.string(lore_filter_exclude_event_data_t.path(payload.asSlice(0L, lore_filter_exclude_event_data_t.SIZE))))
            103 -> FileStageBeginEvent(path_count = lore_file_stage_begin_event_data_t.path_count(payload.asSlice(0L, lore_file_stage_begin_event_data_t.SIZE)))
            104 -> FileStageProgressEvent(count = FileStageCountData(directory_modify_count = lore_file_stage_count_data_t.directory_modify_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), directory_add_count = lore_file_stage_count_data_t.directory_add_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), directory_delete_count = lore_file_stage_count_data_t.directory_delete_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), directory_move_count = lore_file_stage_count_data_t.directory_move_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), file_modify_count = lore_file_stage_count_data_t.file_modify_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), file_add_count = lore_file_stage_count_data_t.file_add_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), file_delete_count = lore_file_stage_count_data_t.file_delete_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), file_move_count = lore_file_stage_count_data_t.file_move_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE))), total_count = lore_file_stage_count_data_t.total_count(lore_file_stage_progress_event_data_t.count(payload.asSlice(0L, lore_file_stage_progress_event_data_t.SIZE)))))
            105 -> FileStageEndEvent(count = FileStageCountData(directory_modify_count = lore_file_stage_count_data_t.directory_modify_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), directory_add_count = lore_file_stage_count_data_t.directory_add_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), directory_delete_count = lore_file_stage_count_data_t.directory_delete_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), directory_move_count = lore_file_stage_count_data_t.directory_move_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), file_modify_count = lore_file_stage_count_data_t.file_modify_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), file_add_count = lore_file_stage_count_data_t.file_add_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), file_delete_count = lore_file_stage_count_data_t.file_delete_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), file_move_count = lore_file_stage_count_data_t.file_move_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE))), total_count = lore_file_stage_count_data_t.total_count(lore_file_stage_end_event_data_t.count(payload.asSlice(0L, lore_file_stage_end_event_data_t.SIZE)))))
            106 -> FileStageRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_file_stage_revision_event_data_t.repository(payload.asSlice(0L, lore_file_stage_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_stage_revision_event_data_t.revision(payload.asSlice(0L, lore_file_stage_revision_event_data_t.SIZE)))))
            107 -> FileStageFileEvent(from_path = LoreCopy.string(lore_file_stage_file_event_data_t.from_path(payload.asSlice(0L, lore_file_stage_file_event_data_t.SIZE))), path = LoreCopy.string(lore_file_stage_file_event_data_t.path(payload.asSlice(0L, lore_file_stage_file_event_data_t.SIZE))), action = lore_file_stage_file_event_data_t.action(payload.asSlice(0L, lore_file_stage_file_event_data_t.SIZE)))
            108 -> FileUnstageBeginEvent(path_count = lore_file_unstage_begin_event_data_t.path_count(payload.asSlice(0L, lore_file_unstage_begin_event_data_t.SIZE)))
            109 -> FileUnstageProgressEvent(count = FileUnstageCountData(directory_unstaged_count = lore_file_unstage_count_data_t.directory_unstaged_count(lore_file_unstage_progress_event_data_t.count(payload.asSlice(0L, lore_file_unstage_progress_event_data_t.SIZE))), directory_discarded_count = lore_file_unstage_count_data_t.directory_discarded_count(lore_file_unstage_progress_event_data_t.count(payload.asSlice(0L, lore_file_unstage_progress_event_data_t.SIZE))), file_unstaged_count = lore_file_unstage_count_data_t.file_unstaged_count(lore_file_unstage_progress_event_data_t.count(payload.asSlice(0L, lore_file_unstage_progress_event_data_t.SIZE))), file_discarded_count = lore_file_unstage_count_data_t.file_discarded_count(lore_file_unstage_progress_event_data_t.count(payload.asSlice(0L, lore_file_unstage_progress_event_data_t.SIZE))), total_count = lore_file_unstage_count_data_t.total_count(lore_file_unstage_progress_event_data_t.count(payload.asSlice(0L, lore_file_unstage_progress_event_data_t.SIZE)))))
            110 -> FileUnstageEndEvent(count = FileUnstageCountData(directory_unstaged_count = lore_file_unstage_count_data_t.directory_unstaged_count(lore_file_unstage_end_event_data_t.count(payload.asSlice(0L, lore_file_unstage_end_event_data_t.SIZE))), directory_discarded_count = lore_file_unstage_count_data_t.directory_discarded_count(lore_file_unstage_end_event_data_t.count(payload.asSlice(0L, lore_file_unstage_end_event_data_t.SIZE))), file_unstaged_count = lore_file_unstage_count_data_t.file_unstaged_count(lore_file_unstage_end_event_data_t.count(payload.asSlice(0L, lore_file_unstage_end_event_data_t.SIZE))), file_discarded_count = lore_file_unstage_count_data_t.file_discarded_count(lore_file_unstage_end_event_data_t.count(payload.asSlice(0L, lore_file_unstage_end_event_data_t.SIZE))), total_count = lore_file_unstage_count_data_t.total_count(lore_file_unstage_end_event_data_t.count(payload.asSlice(0L, lore_file_unstage_end_event_data_t.SIZE)))))
            111 -> FileUnstageRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_file_unstage_revision_event_data_t.repository(payload.asSlice(0L, lore_file_unstage_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_file_unstage_revision_event_data_t.revision(payload.asSlice(0L, lore_file_unstage_revision_event_data_t.SIZE)))))
            112 -> FileUnstageFileEvent(path = LoreCopy.string(lore_file_unstage_file_event_data_t.path(payload.asSlice(0L, lore_file_unstage_file_event_data_t.SIZE))), action = lore_file_unstage_file_event_data_t.action(payload.asSlice(0L, lore_file_unstage_file_event_data_t.SIZE)))
            113 -> FragmentWriteEvent(fragment = Fragment(flags = lore_fragment_t.flags(lore_fragment_write_event_data_t.fragment(payload.asSlice(0L, lore_fragment_write_event_data_t.SIZE))), size_payload = lore_fragment_t.size_payload(lore_fragment_write_event_data_t.fragment(payload.asSlice(0L, lore_fragment_write_event_data_t.SIZE))), size_content = lore_fragment_t.size_content(lore_fragment_write_event_data_t.fragment(payload.asSlice(0L, lore_fragment_write_event_data_t.SIZE)))), deduplicated = lore_fragment_write_event_data_t.deduplicated(payload.asSlice(0L, lore_fragment_write_event_data_t.SIZE)))
            114 -> LayerAddEvent(target_path = LoreCopy.string(lore_layer_add_event_data_t.target_path(payload.asSlice(0L, lore_layer_add_event_data_t.SIZE))), source_repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_layer_add_event_data_t.source_repository(payload.asSlice(0L, lore_layer_add_event_data_t.SIZE)))), source_path = LoreCopy.string(lore_layer_add_event_data_t.source_path(payload.asSlice(0L, lore_layer_add_event_data_t.SIZE))), metadata = LoreCopy.string(lore_layer_add_event_data_t.metadata(payload.asSlice(0L, lore_layer_add_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_layer_add_event_data_t.revision(payload.asSlice(0L, lore_layer_add_event_data_t.SIZE)))))
            115 -> LayerEntryEvent(target_path = LoreCopy.string(lore_layer_entry_event_data_t.target_path(payload.asSlice(0L, lore_layer_entry_event_data_t.SIZE))), source_repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_layer_entry_event_data_t.source_repository(payload.asSlice(0L, lore_layer_entry_event_data_t.SIZE)))), source_path = LoreCopy.string(lore_layer_entry_event_data_t.source_path(payload.asSlice(0L, lore_layer_entry_event_data_t.SIZE))), metadata = LoreCopy.string(lore_layer_entry_event_data_t.metadata(payload.asSlice(0L, lore_layer_entry_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_layer_entry_event_data_t.revision(payload.asSlice(0L, lore_layer_entry_event_data_t.SIZE)))))
            116 -> LayerRemoveEvent(target_path = LoreCopy.string(lore_layer_remove_event_data_t.target_path(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE))), source_repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_layer_remove_event_data_t.source_repository(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)))), source_path = LoreCopy.string(lore_layer_remove_event_data_t.source_path(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_layer_remove_event_data_t.revision(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)))), forced = lore_layer_remove_event_data_t.forced(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)), purged = lore_layer_remove_event_data_t.purged(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)), file_count = lore_layer_remove_event_data_t.file_count(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)), directory_count = lore_layer_remove_event_data_t.directory_count(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)), modified_count = lore_layer_remove_event_data_t.modified_count(payload.asSlice(0L, lore_layer_remove_event_data_t.SIZE)))
            117 -> LayerStagedEntryEvent(target_path = LoreCopy.string(lore_layer_staged_entry_event_data_t.target_path(payload.asSlice(0L, lore_layer_staged_entry_event_data_t.SIZE))), source_repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_layer_staged_entry_event_data_t.source_repository(payload.asSlice(0L, lore_layer_staged_entry_event_data_t.SIZE)))), staged_file_count = lore_layer_staged_entry_event_data_t.staged_file_count(payload.asSlice(0L, lore_layer_staged_entry_event_data_t.SIZE)))
            118 -> LinkChangeEvent(link_path = LoreCopy.string(lore_link_change_event_data_t.link_path(payload.asSlice(0L, lore_link_change_event_data_t.SIZE))), link_repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_link_change_event_data_t.link_repository(payload.asSlice(0L, lore_link_change_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_link_change_event_data_t.branch(payload.asSlice(0L, lore_link_change_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_link_change_event_data_t.revision(payload.asSlice(0L, lore_link_change_event_data_t.SIZE)))), action = lore_link_change_event_data_t.action(payload.asSlice(0L, lore_link_change_event_data_t.SIZE)))
            119 -> LinkEntryEvent(link = LoreCopy.fixedBytes(lore_partition_t.data(lore_link_entry_event_data_t.link(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)))), link_node = lore_link_entry_event_data_t.link_node(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)), link_path = LoreCopy.string(lore_link_entry_event_data_t.link_path(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE))), source_node = lore_link_entry_event_data_t.source_node(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)), source_path = LoreCopy.string(lore_link_entry_event_data_t.source_path(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_link_entry_event_data_t.branch(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)))), branch_name = LoreCopy.string(lore_link_entry_event_data_t.branch_name(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_link_entry_event_data_t.revision(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)))), flags = lore_link_entry_event_data_t.flags(payload.asSlice(0L, lore_link_entry_event_data_t.SIZE)))
            120 -> LockFileAcquireBeginEvent(count = lore_lock_file_acquire_begin_event_data_t.count(payload.asSlice(0L, lore_lock_file_acquire_begin_event_data_t.SIZE)), ignored = lore_lock_file_acquire_begin_event_data_t.ignored(payload.asSlice(0L, lore_lock_file_acquire_begin_event_data_t.SIZE)))
            121 -> LockFileAcquireEvent(path = LoreCopy.string(lore_lock_file_acquire_event_data_t.path(payload.asSlice(0L, lore_lock_file_acquire_event_data_t.SIZE))))
            122 -> LockFileStatusBeginEvent(count = lore_lock_file_status_begin_event_data_t.count(payload.asSlice(0L, lore_lock_file_status_begin_event_data_t.SIZE)))
            123 -> LockFileStatusEvent(path = LoreCopy.string(lore_lock_file_status_event_data_t.path(payload.asSlice(0L, lore_lock_file_status_event_data_t.SIZE))), owner = LoreCopy.string(lore_lock_file_status_event_data_t.owner(payload.asSlice(0L, lore_lock_file_status_event_data_t.SIZE))), locked_at = lore_lock_file_status_event_data_t.locked_at(payload.asSlice(0L, lore_lock_file_status_event_data_t.SIZE)))
            124 -> LockFileQueryBeginEvent(count = lore_lock_file_query_begin_event_data_t.count(payload.asSlice(0L, lore_lock_file_query_begin_event_data_t.SIZE)))
            125 -> LockFileQueryEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_lock_file_query_event_data_t.branch(payload.asSlice(0L, lore_lock_file_query_event_data_t.SIZE)))), path = LoreCopy.string(lore_lock_file_query_event_data_t.path(payload.asSlice(0L, lore_lock_file_query_event_data_t.SIZE))), owner = LoreCopy.string(lore_lock_file_query_event_data_t.owner(payload.asSlice(0L, lore_lock_file_query_event_data_t.SIZE))), locked_at = lore_lock_file_query_event_data_t.locked_at(payload.asSlice(0L, lore_lock_file_query_event_data_t.SIZE)))
            126 -> LockFileReleaseBeginEvent(count = lore_lock_file_release_begin_event_data_t.count(payload.asSlice(0L, lore_lock_file_release_begin_event_data_t.SIZE)), not_found = lore_lock_file_release_begin_event_data_t.not_found(payload.asSlice(0L, lore_lock_file_release_begin_event_data_t.SIZE)))
            127 -> LockFileReleaseEvent(path = LoreCopy.string(lore_lock_file_release_event_data_t.path(payload.asSlice(0L, lore_lock_file_release_event_data_t.SIZE))))
            128 -> MetadataClearFileEvent(path = LoreCopy.string(lore_metadata_clear_file_event_data_t.path(payload.asSlice(0L, lore_metadata_clear_file_event_data_t.SIZE))))
            129 -> MetadataClearRevisionEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_metadata_clear_revision_event_data_t.revision(payload.asSlice(0L, lore_metadata_clear_revision_event_data_t.SIZE)))))
            130 -> PathIgnoreEvent(path = LoreCopy.string(lore_path_ignore_event_data_t.path(payload.asSlice(0L, lore_path_ignore_event_data_t.SIZE))))
            131 -> RepositoryCreateEvent(id = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_create_event_data_t.id(payload.asSlice(0L, lore_repository_create_event_data_t.SIZE)))), name = LoreCopy.string(lore_repository_create_event_data_t.name(payload.asSlice(0L, lore_repository_create_event_data_t.SIZE))), path = LoreCopy.string(lore_repository_create_event_data_t.path(payload.asSlice(0L, lore_repository_create_event_data_t.SIZE))))
            132 -> RepositoryCloneBeginEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_clone_begin_event_data_t.repository(payload.asSlice(0L, lore_repository_clone_begin_event_data_t.SIZE)))), branch = LoreCopy.string(lore_repository_clone_begin_event_data_t.branch(payload.asSlice(0L, lore_repository_clone_begin_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_clone_begin_event_data_t.revision(payload.asSlice(0L, lore_repository_clone_begin_event_data_t.SIZE)))), path = LoreCopy.string(lore_repository_clone_begin_event_data_t.path(payload.asSlice(0L, lore_repository_clone_begin_event_data_t.SIZE))))
            133 -> RepositoryCloneProgressEvent(count = RepositoryCloneCountData(file_complete = lore_repository_clone_count_data_t.file_complete(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), file_retain = lore_repository_clone_count_data_t.file_retain(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), file_replace = lore_repository_clone_count_data_t.file_replace(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), file_count = lore_repository_clone_count_data_t.file_count(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), file_inflight = lore_repository_clone_count_data_t.file_inflight(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), fragment_inflight = lore_repository_clone_count_data_t.fragment_inflight(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), bytes_transferred = lore_repository_clone_count_data_t.bytes_transferred(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), bytes_total = lore_repository_clone_count_data_t.bytes_total(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE))), discovery_complete = lore_repository_clone_count_data_t.discovery_complete(lore_repository_clone_progress_event_data_t.count(payload.asSlice(0L, lore_repository_clone_progress_event_data_t.SIZE)))))
            134 -> RepositoryCloneEndEvent(branch = LoreCopy.string(lore_repository_clone_end_event_data_t.branch(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_clone_end_event_data_t.revision(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE)))), count = RepositoryCloneCountData(file_complete = lore_repository_clone_count_data_t.file_complete(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), file_retain = lore_repository_clone_count_data_t.file_retain(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), file_replace = lore_repository_clone_count_data_t.file_replace(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), file_count = lore_repository_clone_count_data_t.file_count(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), file_inflight = lore_repository_clone_count_data_t.file_inflight(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), fragment_inflight = lore_repository_clone_count_data_t.fragment_inflight(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), bytes_transferred = lore_repository_clone_count_data_t.bytes_transferred(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), bytes_total = lore_repository_clone_count_data_t.bytes_total(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE))), discovery_complete = lore_repository_clone_count_data_t.discovery_complete(lore_repository_clone_end_event_data_t.count(payload.asSlice(0L, lore_repository_clone_end_event_data_t.SIZE)))))
            135 -> DependencyResolveBeginEvent(root_count = lore_dependency_resolve_begin_event_data_t.root_count(payload.asSlice(0L, lore_dependency_resolve_begin_event_data_t.SIZE)))
            136 -> DependencyResolveItemEvent(source = LoreCopy.string(lore_dependency_resolve_item_event_data_t.source(payload.asSlice(0L, lore_dependency_resolve_item_event_data_t.SIZE))), target = LoreCopy.string(lore_dependency_resolve_item_event_data_t.target(payload.asSlice(0L, lore_dependency_resolve_item_event_data_t.SIZE))), tags = LoreCopy.array(lore_dependency_resolve_item_event_data_t.tags(payload.asSlice(0L, lore_dependency_resolve_item_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            137 -> DependencyResolveEndEvent(resolved_count = lore_dependency_resolve_end_event_data_t.resolved_count(payload.asSlice(0L, lore_dependency_resolve_end_event_data_t.SIZE)))
            138 -> RepositoryDataEvent(remote_url = LoreCopy.string(lore_repository_data_event_data_t.remote_url(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE))), id = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_data_event_data_t.id(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE)))), name = LoreCopy.string(lore_repository_data_event_data_t.name(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE))), description = LoreCopy.string(lore_repository_data_event_data_t.description(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE))), default_branch = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_data_event_data_t.default_branch(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE)))), default_branch_name = LoreCopy.string(lore_repository_data_event_data_t.default_branch_name(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE))), creator = LoreCopy.string(lore_repository_data_event_data_t.creator(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE))), created = lore_repository_data_event_data_t.created(payload.asSlice(0L, lore_repository_data_event_data_t.SIZE)))
            139 -> RepositoryConfigGetEvent(key = LoreCopy.string(lore_repository_config_get_event_data_t.key(payload.asSlice(0L, lore_repository_config_get_event_data_t.SIZE))), value = LoreCopy.string(lore_repository_config_get_event_data_t.value(payload.asSlice(0L, lore_repository_config_get_event_data_t.SIZE))))
            140 -> RepositoryDumpBeginEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_dump_begin_event_data_t.repository(payload.asSlice(0L, lore_repository_dump_begin_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_dump_begin_event_data_t.revision(payload.asSlice(0L, lore_repository_dump_begin_event_data_t.SIZE)))))
            141 -> RepositoryDumpEndEvent(_unused = lore_repository_dump_end_event_data_t._unused(payload.asSlice(0L, lore_repository_dump_end_event_data_t.SIZE)))
            142 -> RepositoryListEntryEvent(id = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_list_entry_event_data_t.id(payload.asSlice(0L, lore_repository_list_entry_event_data_t.SIZE)))), name = LoreCopy.string(lore_repository_list_entry_event_data_t.name(payload.asSlice(0L, lore_repository_list_entry_event_data_t.SIZE))))
            143 -> RepositoryInstanceEvent(instance_id = LoreCopy.fixedBytes(lore_instance_id_t.data(lore_repository_instance_event_data_t.instance_id(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE)))), path = LoreCopy.string(lore_repository_instance_event_data_t.path(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE))), branch_name = LoreCopy.string(lore_repository_instance_event_data_t.branch_name(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_instance_event_data_t.branch(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_instance_event_data_t.revision(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE)))), stale = lore_repository_instance_event_data_t.stale(payload.asSlice(0L, lore_repository_instance_event_data_t.SIZE)))
            144 -> RepositoryVerifyStateBeginEvent(_unused = lore_repository_verify_state_begin_event_data_t._unused(payload.asSlice(0L, lore_repository_verify_state_begin_event_data_t.SIZE)))
            145 -> RepositoryVerifyStateEndEvent(healed_staged_state = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_verify_state_end_event_data_t.healed_staged_state(payload.asSlice(0L, lore_repository_verify_state_end_event_data_t.SIZE)))))
            146 -> RepositoryVerifyFragmentEvent(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_verify_fragment_event_data_t.hash(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)))), group_index = lore_repository_verify_fragment_event_data_t.group_index(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), bucket_index = lore_repository_verify_fragment_event_data_t.bucket_index(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), index_path = LoreCopy.string(lore_repository_verify_fragment_event_data_t.index_path(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE))), entry_count = lore_repository_verify_fragment_event_data_t.entry_count(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), packfile_entry_count = lore_repository_verify_fragment_event_data_t.packfile_entry_count(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), match_count = lore_repository_verify_fragment_event_data_t.match_count(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), matches = LoreCopy.array(lore_repository_verify_fragment_event_data_t.matches(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE)), 104L) { RepositoryVerifyFragmentMatchEventData(slot = lore_repository_verify_fragment_match_event_data_t.slot(it), index = lore_repository_verify_fragment_match_event_data_t.index(it), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_verify_fragment_match_event_data_t.repository(it))), address_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_verify_fragment_match_event_data_t.address_hash(it))), address_context = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_verify_fragment_match_event_data_t.address_context(it))), flags = lore_repository_verify_fragment_match_event_data_t.flags(it), size_payload = lore_repository_verify_fragment_match_event_data_t.size_payload(it), size_content = lore_repository_verify_fragment_match_event_data_t.size_content(it), pack_offset = lore_repository_verify_fragment_match_event_data_t.pack_offset(it), pack_file = lore_repository_verify_fragment_match_event_data_t.pack_file(it), last_access = lore_repository_verify_fragment_match_event_data_t.last_access(it)) }, error = LoreCopy.string(lore_repository_verify_fragment_event_data_t.error(payload.asSlice(0L, lore_repository_verify_fragment_event_data_t.SIZE))))
            147 -> RepositoryVerifyFragmentMatchEvent(slot = lore_repository_verify_fragment_match_event_data_t.slot(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), index = lore_repository_verify_fragment_match_event_data_t.index(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_verify_fragment_match_event_data_t.repository(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)))), address_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_verify_fragment_match_event_data_t.address_hash(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)))), address_context = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_verify_fragment_match_event_data_t.address_context(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)))), flags = lore_repository_verify_fragment_match_event_data_t.flags(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), size_payload = lore_repository_verify_fragment_match_event_data_t.size_payload(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), size_content = lore_repository_verify_fragment_match_event_data_t.size_content(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), pack_offset = lore_repository_verify_fragment_match_event_data_t.pack_offset(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), pack_file = lore_repository_verify_fragment_match_event_data_t.pack_file(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)), last_access = lore_repository_verify_fragment_match_event_data_t.last_access(payload.asSlice(0L, lore_repository_verify_fragment_match_event_data_t.SIZE)))
            148 -> RepositoryVerifyFragmentRemoteEvent(address_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_verify_fragment_remote_event_data_t.address_hash(payload.asSlice(0L, lore_repository_verify_fragment_remote_event_data_t.SIZE)))), address_context = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_verify_fragment_remote_event_data_t.address_context(payload.asSlice(0L, lore_repository_verify_fragment_remote_event_data_t.SIZE)))), corrupted = lore_repository_verify_fragment_remote_event_data_t.corrupted(payload.asSlice(0L, lore_repository_verify_fragment_remote_event_data_t.SIZE)), healed = lore_repository_verify_fragment_remote_event_data_t.healed(payload.asSlice(0L, lore_repository_verify_fragment_remote_event_data_t.SIZE)), error = LoreCopy.string(lore_repository_verify_fragment_remote_event_data_t.error(payload.asSlice(0L, lore_repository_verify_fragment_remote_event_data_t.SIZE))))
            149 -> RepositoryStateDumpEvent(revision_number = lore_repository_state_dump_event_data_t.revision_number(payload.asSlice(0L, lore_repository_state_dump_event_data_t.SIZE)), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_state_dump_event_data_t.revision(payload.asSlice(0L, lore_repository_state_dump_event_data_t.SIZE)))), tree_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_state_dump_event_data_t.tree_hash(payload.asSlice(0L, lore_repository_state_dump_event_data_t.SIZE)))), tree_size = lore_repository_state_dump_event_data_t.tree_size(payload.asSlice(0L, lore_repository_state_dump_event_data_t.SIZE)))
            150 -> RepositoryStateDumpNodeEvent(name = LoreCopy.string(lore_repository_state_dump_node_event_data_t.name(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE))), id = lore_repository_state_dump_node_event_data_t.id(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), parent = lore_repository_state_dump_node_event_data_t.parent(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), sibling = lore_repository_state_dump_node_event_data_t.sibling(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), mode = lore_repository_state_dump_node_event_data_t.mode(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), size = lore_repository_state_dump_node_event_data_t.size(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), flags = lore_repository_state_dump_node_event_data_t.flags(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE)), type_data = LoreCopy.string(lore_repository_state_dump_node_event_data_t.type_data(payload.asSlice(0L, lore_repository_state_dump_node_event_data_t.SIZE))))
            151 -> RepositoryStatusRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_repository_status_revision_event_data_t.repository(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_repository_status_revision_event_data_t.branch(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), branch_name = LoreCopy.string(lore_repository_status_revision_event_data_t.branch_name(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_number = lore_repository_status_revision_event_data_t.revision_number(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), revision_staged = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision_staged(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_merged = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision_merged(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_merged_parent_branch = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision_merged_parent_branch(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_local = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision_local(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_local_number = lore_repository_status_revision_event_data_t.revision_local_number(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), revision_remote = LoreCopy.fixedBytes(lore_hash_t.data(lore_repository_status_revision_event_data_t.revision_remote(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))), revision_remote_number = lore_repository_status_revision_event_data_t.revision_remote_number(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), is_local_ahead = lore_repository_status_revision_event_data_t.is_local_ahead(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), is_remote_ahead = lore_repository_status_revision_event_data_t.is_remote_ahead(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), remote_available = lore_repository_status_revision_event_data_t.remote_available(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), remote_authorized = lore_repository_status_revision_event_data_t.remote_authorized(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)), remote_branch_exist = lore_repository_status_revision_event_data_t.remote_branch_exist(payload.asSlice(0L, lore_repository_status_revision_event_data_t.SIZE)))
            152 -> RepositoryStatusFileEvent(path = LoreCopy.string(lore_repository_status_file_event_data_t.path(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE))), size = lore_repository_status_file_event_data_t.size(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), action = lore_repository_status_file_event_data_t.action(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), type = lore_repository_status_file_event_data_t.type(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_staged = lore_repository_status_file_event_data_t.flag_staged(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_merged = lore_repository_status_file_event_data_t.flag_merged(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_conflict = lore_repository_status_file_event_data_t.flag_conflict(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_conflict_unresolved = lore_repository_status_file_event_data_t.flag_conflict_unresolved(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_conflict_automerged = lore_repository_status_file_event_data_t.flag_conflict_automerged(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_conflict_mine = lore_repository_status_file_event_data_t.flag_conflict_mine(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_conflict_theirs = lore_repository_status_file_event_data_t.flag_conflict_theirs(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), flag_dirty = lore_repository_status_file_event_data_t.flag_dirty(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE)), from_path = LoreCopy.string(lore_repository_status_file_event_data_t.from_path(payload.asSlice(0L, lore_repository_status_file_event_data_t.SIZE))))
            153 -> RepositoryStatusCountEvent(directories = lore_repository_status_count_event_data_t.directories(payload.asSlice(0L, lore_repository_status_count_event_data_t.SIZE)), files = lore_repository_status_count_event_data_t.files(payload.asSlice(0L, lore_repository_status_count_event_data_t.SIZE)))
            154 -> RepositoryStatusSummaryEvent(adds = lore_repository_status_summary_event_data_t.adds(payload.asSlice(0L, lore_repository_status_summary_event_data_t.SIZE)), deletes = lore_repository_status_summary_event_data_t.deletes(payload.asSlice(0L, lore_repository_status_summary_event_data_t.SIZE)), modifies = lore_repository_status_summary_event_data_t.modifies(payload.asSlice(0L, lore_repository_status_summary_event_data_t.SIZE)), moves = lore_repository_status_summary_event_data_t.moves(payload.asSlice(0L, lore_repository_status_summary_event_data_t.SIZE)), copies = lore_repository_status_summary_event_data_t.copies(payload.asSlice(0L, lore_repository_status_summary_event_data_t.SIZE)))
            155 -> RepositoryStoreImmutableQueryEvent(address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_repository_store_immutable_query_event_data_t.address(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_repository_store_immutable_query_event_data_t.address(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)))))), remote = lore_repository_store_immutable_query_event_data_t.remote(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), status = lore_repository_store_immutable_query_event_data_t.status(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), payload = lore_repository_store_immutable_query_event_data_t.payload(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), subfragment = lore_repository_store_immutable_query_event_data_t.subfragment(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), flags = lore_repository_store_immutable_query_event_data_t.flags(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), payload_size = lore_repository_store_immutable_query_event_data_t.payload_size(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)), content_size = lore_repository_store_immutable_query_event_data_t.content_size(payload.asSlice(0L, lore_repository_store_immutable_query_event_data_t.SIZE)))
            156 -> RevisionCommitBeginEvent(_unused = lore_revision_commit_begin_event_data_t._unused(payload.asSlice(0L, lore_revision_commit_begin_event_data_t.SIZE)))
            157 -> RevisionCommitProgressEvent(count = RevisionCommitCountData(directory_count = lore_revision_commit_count_data_t.directory_count(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), directory_total = lore_revision_commit_count_data_t.directory_total(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), file_count = lore_revision_commit_count_data_t.file_count(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), file_total = lore_revision_commit_count_data_t.file_total(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), directory_delete_count = lore_revision_commit_count_data_t.directory_delete_count(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), file_modify_count = lore_revision_commit_count_data_t.file_modify_count(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), file_delete_count = lore_revision_commit_count_data_t.file_delete_count(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), bytes_transferred = lore_revision_commit_count_data_t.bytes_transferred(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), bytes_total = lore_revision_commit_count_data_t.bytes_total(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE))), discovery_complete = lore_revision_commit_count_data_t.discovery_complete(lore_revision_commit_progress_event_data_t.count(payload.asSlice(0L, lore_revision_commit_progress_event_data_t.SIZE)))))
            158 -> RevisionCommitEndEvent(count = RevisionCommitCountData(directory_count = lore_revision_commit_count_data_t.directory_count(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), directory_total = lore_revision_commit_count_data_t.directory_total(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), file_count = lore_revision_commit_count_data_t.file_count(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), file_total = lore_revision_commit_count_data_t.file_total(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), directory_delete_count = lore_revision_commit_count_data_t.directory_delete_count(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), file_modify_count = lore_revision_commit_count_data_t.file_modify_count(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), file_delete_count = lore_revision_commit_count_data_t.file_delete_count(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), bytes_transferred = lore_revision_commit_count_data_t.bytes_transferred(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), bytes_total = lore_revision_commit_count_data_t.bytes_total(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE))), discovery_complete = lore_revision_commit_count_data_t.discovery_complete(lore_revision_commit_end_event_data_t.count(payload.asSlice(0L, lore_revision_commit_end_event_data_t.SIZE)))))
            159 -> RevisionCommitRevisionEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_commit_revision_event_data_t.repository(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_commit_revision_event_data_t.branch(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_commit_revision_event_data_t.revision(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)))), revision_number = lore_revision_commit_revision_event_data_t.revision_number(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)), parent = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_commit_revision_event_data_t.parent(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)))), parent_other = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_commit_revision_event_data_t.parent_other(payload.asSlice(0L, lore_revision_commit_revision_event_data_t.SIZE)))))
            160 -> RevisionInfoEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_info_event_data_t.repository(payload.asSlice(0L, lore_revision_info_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_info_event_data_t.revision(payload.asSlice(0L, lore_revision_info_event_data_t.SIZE)))), revision_number = lore_revision_info_event_data_t.revision_number(payload.asSlice(0L, lore_revision_info_event_data_t.SIZE)), parent = LoreCopy.inlineArray(lore_revision_info_event_data_t.parent(payload.asSlice(0L, lore_revision_info_event_data_t.SIZE)), 2, 32L) { LoreCopy.fixedBytes(lore_hash_t.data(it)) })
            161 -> RevisionInfoDeltaEvent(path = LoreCopy.string(lore_revision_info_delta_event_data_t.path(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE))), size = lore_revision_info_delta_event_data_t.size(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE)), action = lore_revision_info_delta_event_data_t.action(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE)), flag_modify = lore_revision_info_delta_event_data_t.flag_modify(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE)), flag_merged = lore_revision_info_delta_event_data_t.flag_merged(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE)), flag_file = lore_revision_info_delta_event_data_t.flag_file(payload.asSlice(0L, lore_revision_info_delta_event_data_t.SIZE)))
            162 -> RevisionDiffFileEvent(path = LoreCopy.string(lore_revision_diff_file_event_data_t.path(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE))), action = lore_revision_diff_file_event_data_t.action(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE)), old_is_file = lore_revision_diff_file_event_data_t.old_is_file(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE)), new_is_file = lore_revision_diff_file_event_data_t.new_is_file(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE)), old_address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_revision_diff_file_event_data_t.old_address(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_revision_diff_file_event_data_t.old_address(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE)))))), new_address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_revision_diff_file_event_data_t.new_address(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_revision_diff_file_event_data_t.new_address(payload.asSlice(0L, lore_revision_diff_file_event_data_t.SIZE)))))))
            163 -> RevisionFindEvent(signature = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_find_event_data_t.signature(payload.asSlice(0L, lore_revision_find_event_data_t.SIZE)))))
            164 -> RevisionHistoryEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_history_event_data_t.repository(payload.asSlice(0L, lore_revision_history_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_history_event_data_t.branch(payload.asSlice(0L, lore_revision_history_event_data_t.SIZE)))))
            165 -> RevisionHistoryEntryEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_history_entry_event_data_t.revision(payload.asSlice(0L, lore_revision_history_entry_event_data_t.SIZE)))), revision_number = lore_revision_history_entry_event_data_t.revision_number(payload.asSlice(0L, lore_revision_history_entry_event_data_t.SIZE)), parent = LoreCopy.inlineArray(lore_revision_history_entry_event_data_t.parent(payload.asSlice(0L, lore_revision_history_entry_event_data_t.SIZE)), 2, 32L) { LoreCopy.fixedBytes(lore_hash_t.data(it)) })
            166 -> RevisionRestoreFileBeginEvent(count = lore_revision_restore_file_begin_event_data_t.count(payload.asSlice(0L, lore_revision_restore_file_begin_event_data_t.SIZE)))
            167 -> RevisionRestoreFileEvent(path = LoreCopy.string(lore_revision_restore_file_event_data_t.path(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE))), action = lore_revision_restore_file_event_data_t.action(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE)), size = lore_revision_restore_file_event_data_t.size(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE)), is_file = lore_revision_restore_file_event_data_t.is_file(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE)), is_directory = lore_revision_restore_file_event_data_t.is_directory(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE)), is_module = lore_revision_restore_file_event_data_t.is_module(payload.asSlice(0L, lore_revision_restore_file_event_data_t.SIZE)))
            168 -> RevisionRestoreFileEndEvent(count = lore_revision_restore_file_end_event_data_t.count(payload.asSlice(0L, lore_revision_restore_file_end_event_data_t.SIZE)))
            169 -> RevisionRestoreFragmentBeginEvent(fragments = lore_revision_restore_fragment_begin_event_data_t.fragments(payload.asSlice(0L, lore_revision_restore_fragment_begin_event_data_t.SIZE)))
            170 -> RevisionRestoreFragmentProgressEvent(complete = lore_revision_restore_fragment_progress_event_data_t.complete(payload.asSlice(0L, lore_revision_restore_fragment_progress_event_data_t.SIZE)), count = lore_revision_restore_fragment_progress_event_data_t.count(payload.asSlice(0L, lore_revision_restore_fragment_progress_event_data_t.SIZE)))
            171 -> RevisionRestoreFragmentEndEvent(fragments = lore_revision_restore_fragment_end_event_data_t.fragments(payload.asSlice(0L, lore_revision_restore_fragment_end_event_data_t.SIZE)))
            172 -> RevisionRestoreRevisionEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_restore_revision_event_data_t.revision(payload.asSlice(0L, lore_revision_restore_revision_event_data_t.SIZE)))), revision_number = lore_revision_restore_revision_event_data_t.revision_number(payload.asSlice(0L, lore_revision_restore_revision_event_data_t.SIZE)))
            173 -> RevisionRestoreSyncBeginEvent(count = lore_revision_restore_sync_begin_event_data_t.count(payload.asSlice(0L, lore_revision_restore_sync_begin_event_data_t.SIZE)))
            174 -> RevisionRestoreSyncEndEvent(count = lore_revision_restore_sync_end_event_data_t.count(payload.asSlice(0L, lore_revision_restore_sync_end_event_data_t.SIZE)))
            175 -> RevisionResolveEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_resolve_event_data_t.repository(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_resolve_event_data_t.branch(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE)))), revision = LoreCopy.string(lore_revision_resolve_event_data_t.revision(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE))), revision_number = lore_revision_resolve_event_data_t.revision_number(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE)), remote = lore_revision_resolve_event_data_t.remote(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE)), local = lore_revision_resolve_event_data_t.local(payload.asSlice(0L, lore_revision_resolve_event_data_t.SIZE)))
            176 -> RevisionSyncTargetEvent(remote = LoreCopy.string(lore_revision_sync_target_event_data_t.remote(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE))), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_sync_target_event_data_t.repository(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_sync_target_event_data_t.branch(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)))), branch_name = LoreCopy.string(lore_revision_sync_target_event_data_t.branch_name(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE))), source_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_sync_target_event_data_t.source_revision(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)))), source_revision_number = lore_revision_sync_target_event_data_t.source_revision_number(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)), target_revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_sync_target_event_data_t.target_revision(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)))), target_revision_number = lore_revision_sync_target_event_data_t.target_revision_number(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)), is_latest = lore_revision_sync_target_event_data_t.is_latest(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)), local = lore_revision_sync_target_event_data_t.local(payload.asSlice(0L, lore_revision_sync_target_event_data_t.SIZE)))
            177 -> RevisionSyncFileEvent(path = LoreCopy.string(lore_revision_sync_file_event_data_t.path(payload.asSlice(0L, lore_revision_sync_file_event_data_t.SIZE))), size = lore_revision_sync_file_event_data_t.size(payload.asSlice(0L, lore_revision_sync_file_event_data_t.SIZE)), action = lore_revision_sync_file_event_data_t.action(payload.asSlice(0L, lore_revision_sync_file_event_data_t.SIZE)), flag_file = lore_revision_sync_file_event_data_t.flag_file(payload.asSlice(0L, lore_revision_sync_file_event_data_t.SIZE)))
            178 -> RevisionSyncProgressEvent(file_update = lore_revision_sync_progress_event_data_t.file_update(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), file_update_total = lore_revision_sync_progress_event_data_t.file_update_total(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), file_delete = lore_revision_sync_progress_event_data_t.file_delete(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), file_delete_total = lore_revision_sync_progress_event_data_t.file_delete_total(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), file_automerge = lore_revision_sync_progress_event_data_t.file_automerge(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), file_conflict = lore_revision_sync_progress_event_data_t.file_conflict(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), bytes_update = lore_revision_sync_progress_event_data_t.bytes_update(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), bytes_update_total = lore_revision_sync_progress_event_data_t.bytes_update_total(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)), discovery_complete = lore_revision_sync_progress_event_data_t.discovery_complete(payload.asSlice(0L, lore_revision_sync_progress_event_data_t.SIZE)))
            179 -> RevisionSyncRevisionEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_sync_revision_event_data_t.branch(payload.asSlice(0L, lore_revision_sync_revision_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_sync_revision_event_data_t.revision(payload.asSlice(0L, lore_revision_sync_revision_event_data_t.SIZE)))), revision_number = lore_revision_sync_revision_event_data_t.revision_number(payload.asSlice(0L, lore_revision_sync_revision_event_data_t.SIZE)), flag_merge = lore_revision_sync_revision_event_data_t.flag_merge(payload.asSlice(0L, lore_revision_sync_revision_event_data_t.SIZE)), flag_conflict = lore_revision_sync_revision_event_data_t.flag_conflict(payload.asSlice(0L, lore_revision_sync_revision_event_data_t.SIZE)))
            180 -> RevisionBisectEvent(start_revision_number = lore_revision_bisect_event_data_t.start_revision_number(payload.asSlice(0L, lore_revision_bisect_event_data_t.SIZE)), target_revision_number = lore_revision_bisect_event_data_t.target_revision_number(payload.asSlice(0L, lore_revision_bisect_event_data_t.SIZE)), end_revision_number = lore_revision_bisect_event_data_t.end_revision_number(payload.asSlice(0L, lore_revision_bisect_event_data_t.SIZE)), done = lore_revision_bisect_event_data_t.done(payload.asSlice(0L, lore_revision_bisect_event_data_t.SIZE)))
            181 -> NotificationBranchCreatedEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_notification_branch_created_event_data_t.branch(payload.asSlice(0L, lore_notification_branch_created_event_data_t.SIZE)))))
            182 -> NotificationBranchDeletedEvent(branch = LoreCopy.fixedBytes(lore_context_t.data(lore_notification_branch_deleted_event_data_t.branch(payload.asSlice(0L, lore_notification_branch_deleted_event_data_t.SIZE)))))
            183 -> NotificationBranchPushedEvent(revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_notification_branch_pushed_event_data_t.revision(payload.asSlice(0L, lore_notification_branch_pushed_event_data_t.SIZE)))), revision_number = lore_notification_branch_pushed_event_data_t.revision_number(payload.asSlice(0L, lore_notification_branch_pushed_event_data_t.SIZE)), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_notification_branch_pushed_event_data_t.branch(payload.asSlice(0L, lore_notification_branch_pushed_event_data_t.SIZE)))), user_id = LoreCopy.string(lore_notification_branch_pushed_event_data_t.user_id(payload.asSlice(0L, lore_notification_branch_pushed_event_data_t.SIZE))))
            184 -> NotificationResourceLockedEvent(user_id = LoreCopy.string(lore_notification_resource_locked_event_data_t.user_id(payload.asSlice(0L, lore_notification_resource_locked_event_data_t.SIZE))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_notification_resource_locked_event_data_t.branch(payload.asSlice(0L, lore_notification_resource_locked_event_data_t.SIZE)))), paths = LoreCopy.array(lore_notification_resource_locked_event_data_t.paths(payload.asSlice(0L, lore_notification_resource_locked_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            185 -> NotificationResourceUnlockedEvent(user_id = LoreCopy.string(lore_notification_resource_unlocked_event_data_t.user_id(payload.asSlice(0L, lore_notification_resource_unlocked_event_data_t.SIZE))), branch = LoreCopy.fixedBytes(lore_context_t.data(lore_notification_resource_unlocked_event_data_t.branch(payload.asSlice(0L, lore_notification_resource_unlocked_event_data_t.SIZE)))), paths = LoreCopy.array(lore_notification_resource_unlocked_event_data_t.paths(payload.asSlice(0L, lore_notification_resource_unlocked_event_data_t.SIZE)), 16L) { LoreCopy.string(it) })
            186 -> NotificationSubscribedEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_notification_subscribed_event_data_t.repository(payload.asSlice(0L, lore_notification_subscribed_event_data_t.SIZE)))))
            187 -> NotificationUnsubscribedEvent(repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_notification_unsubscribed_event_data_t.repository(payload.asSlice(0L, lore_notification_unsubscribed_event_data_t.SIZE)))))
            188 -> SharedStoreCreateEvent(path = LoreCopy.string(lore_shared_store_create_event_data_t.path(payload.asSlice(0L, lore_shared_store_create_event_data_t.SIZE))))
            189 -> SharedStoreInfoEvent(use_automatically = lore_shared_store_info_event_data_t.use_automatically(payload.asSlice(0L, lore_shared_store_info_event_data_t.SIZE)), remote_urls = LoreCopy.array(lore_shared_store_info_event_data_t.remote_urls(payload.asSlice(0L, lore_shared_store_info_event_data_t.SIZE)), 16L) { LoreCopy.string(it) }, paths = LoreCopy.array(lore_shared_store_info_event_data_t.paths(payload.asSlice(0L, lore_shared_store_info_event_data_t.SIZE)), 16L) { LoreCopy.string(it) }, exists = LoreCopy.array(lore_shared_store_info_event_data_t.exists(payload.asSlice(0L, lore_shared_store_info_event_data_t.SIZE)), 1L) { it.get(ValueLayout.JAVA_BYTE, 0L) })
            190 -> LinkStagedEntryEvent(path = LoreCopy.string(lore_link_staged_entry_event_data_t.path(payload.asSlice(0L, lore_link_staged_entry_event_data_t.SIZE))), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_link_staged_entry_event_data_t.repository(payload.asSlice(0L, lore_link_staged_entry_event_data_t.SIZE)))), staged_file_count = lore_link_staged_entry_event_data_t.staged_file_count(payload.asSlice(0L, lore_link_staged_entry_event_data_t.SIZE)))
            191 -> StorageOpenedEvent(handle_id = lore_storage_opened_event_data_t.handle_id(payload.asSlice(0L, lore_storage_opened_event_data_t.SIZE)))
            192 -> StoragePutItemCompleteEvent(id = lore_storage_put_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_put_item_complete_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_put_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_put_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_put_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_put_item_complete_event_data_t.SIZE)))))), error_code = lore_storage_put_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_put_item_complete_event_data_t.SIZE)))
            193 -> StorageGetHeaderEvent(id = lore_storage_get_header_event_data_t.id(payload.asSlice(0L, lore_storage_get_header_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_get_header_event_data_t.address(payload.asSlice(0L, lore_storage_get_header_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_get_header_event_data_t.address(payload.asSlice(0L, lore_storage_get_header_event_data_t.SIZE)))))), size_content = lore_storage_get_header_event_data_t.size_content(payload.asSlice(0L, lore_storage_get_header_event_data_t.SIZE)))
            194 -> StorageGetDataEvent(id = lore_storage_get_data_event_data_t.id(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_get_data_event_data_t.address(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_get_data_event_data_t.address(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE)))))), offset = lore_storage_get_data_event_data_t.offset(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE)), bytes = LoreCopy.bytes(lore_storage_get_data_event_data_t.bytes(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE)), lore_bytes_t.len(lore_storage_get_data_event_data_t.bytes(payload.asSlice(0L, lore_storage_get_data_event_data_t.SIZE)))))
            195 -> StorageGetItemCompleteEvent(id = lore_storage_get_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_get_item_complete_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_get_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_get_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_get_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_get_item_complete_event_data_t.SIZE)))))), error_code = lore_storage_get_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_get_item_complete_event_data_t.SIZE)))
            196 -> StorageGetMetadataItemCompleteEvent(id = lore_storage_get_metadata_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_get_metadata_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_get_metadata_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE)))))), fragment = Fragment(flags = lore_fragment_t.flags(lore_storage_get_metadata_item_complete_event_data_t.fragment(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE))), size_payload = lore_fragment_t.size_payload(lore_storage_get_metadata_item_complete_event_data_t.fragment(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE))), size_content = lore_fragment_t.size_content(lore_storage_get_metadata_item_complete_event_data_t.fragment(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE)))), error_code = lore_storage_get_metadata_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_get_metadata_item_complete_event_data_t.SIZE)))
            197 -> StorageCopyItemCompleteEvent(id = lore_storage_copy_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)), source_partition = LoreCopy.fixedBytes(lore_partition_t.data(lore_storage_copy_item_complete_event_data_t.source_partition(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)))), target_partition = LoreCopy.fixedBytes(lore_partition_t.data(lore_storage_copy_item_complete_event_data_t.target_partition(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)))), source_address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_copy_item_complete_event_data_t.source_address(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_copy_item_complete_event_data_t.source_address(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)))))), target_context = LoreCopy.fixedBytes(lore_context_t.data(lore_storage_copy_item_complete_event_data_t.target_context(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)))), error_code = lore_storage_copy_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_copy_item_complete_event_data_t.SIZE)))
            198 -> StorageObliterateItemCompleteEvent(id = lore_storage_obliterate_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_obliterate_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_obliterate_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)))))), local_success = lore_storage_obliterate_item_complete_event_data_t.local_success(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)), remote_success = lore_storage_obliterate_item_complete_event_data_t.remote_success(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)), local_skipped = lore_storage_obliterate_item_complete_event_data_t.local_skipped(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)), remote_skipped = lore_storage_obliterate_item_complete_event_data_t.remote_skipped(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)), error_code = lore_storage_obliterate_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_obliterate_item_complete_event_data_t.SIZE)))
            199 -> StorageUploadItemCompleteEvent(id = lore_storage_upload_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_upload_item_complete_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_storage_upload_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_upload_item_complete_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_storage_upload_item_complete_event_data_t.address(payload.asSlice(0L, lore_storage_upload_item_complete_event_data_t.SIZE)))))), already_durable = lore_storage_upload_item_complete_event_data_t.already_durable(payload.asSlice(0L, lore_storage_upload_item_complete_event_data_t.SIZE)), error_code = lore_storage_upload_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_upload_item_complete_event_data_t.SIZE)))
            200 -> RevisionTreeLoadedEvent(handle_id = lore_revision_tree_loaded_event_data_t.handle_id(payload.asSlice(0L, lore_revision_tree_loaded_event_data_t.SIZE)))
            201 -> RevisionTreeResolvePathCompleteEvent(id = lore_revision_tree_resolve_path_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_resolve_path_complete_event_data_t.SIZE)), node_id = lore_revision_tree_resolve_path_complete_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_resolve_path_complete_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_tree_resolve_path_complete_event_data_t.repository(payload.asSlice(0L, lore_revision_tree_resolve_path_complete_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_resolve_path_complete_event_data_t.revision(payload.asSlice(0L, lore_revision_tree_resolve_path_complete_event_data_t.SIZE)))), error_code = lore_revision_tree_resolve_path_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_resolve_path_complete_event_data_t.SIZE)))
            202 -> RevisionTreeChildEvent(id = lore_revision_tree_child_event_data_t.id(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), node_id = lore_revision_tree_child_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), name = LoreCopy.string(lore_revision_tree_child_event_data_t.name(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE))), parent_id = lore_revision_tree_child_event_data_t.parent_id(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), kind = lore_revision_tree_child_event_data_t.kind(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), mode = lore_revision_tree_child_event_data_t.mode(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), size = lore_revision_tree_child_event_data_t.size(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_revision_tree_child_event_data_t.address(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_revision_tree_child_event_data_t.address(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)))))), error_code = lore_revision_tree_child_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_child_event_data_t.SIZE)))
            203 -> RevisionTreeNodeInfoEvent(id = lore_revision_tree_node_info_event_data_t.id(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), node_id = lore_revision_tree_node_info_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_tree_node_info_event_data_t.repository(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_node_info_event_data_t.revision(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)))), name = LoreCopy.string(lore_revision_tree_node_info_event_data_t.name(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE))), parent_id = lore_revision_tree_node_info_event_data_t.parent_id(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), kind = lore_revision_tree_node_info_event_data_t.kind(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), mode = lore_revision_tree_node_info_event_data_t.mode(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), size = lore_revision_tree_node_info_event_data_t.size(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)), address = Address(hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_address_t.hash(lore_revision_tree_node_info_event_data_t.address(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE))))), context = LoreCopy.fixedBytes(lore_context_t.data(lore_address_t.context(lore_revision_tree_node_info_event_data_t.address(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)))))), file_id = LoreCopy.fixedBytes(lore_context_t.data(lore_revision_tree_node_info_event_data_t.file_id(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)))), error_code = lore_revision_tree_node_info_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_node_info_event_data_t.SIZE)))
            204 -> RevisionTreeNodePathEvent(id = lore_revision_tree_node_path_event_data_t.id(payload.asSlice(0L, lore_revision_tree_node_path_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_tree_node_path_event_data_t.repository(payload.asSlice(0L, lore_revision_tree_node_path_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_node_path_event_data_t.revision(payload.asSlice(0L, lore_revision_tree_node_path_event_data_t.SIZE)))), path = LoreCopy.string(lore_revision_tree_node_path_event_data_t.path(payload.asSlice(0L, lore_revision_tree_node_path_event_data_t.SIZE))), error_code = lore_revision_tree_node_path_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_node_path_event_data_t.SIZE)))
            205 -> RevisionTreeAddCompleteEvent(id = lore_revision_tree_add_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_add_complete_event_data_t.SIZE)), node_id = lore_revision_tree_add_complete_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_add_complete_event_data_t.SIZE)), error_code = lore_revision_tree_add_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_add_complete_event_data_t.SIZE)))
            206 -> RevisionTreeDeleteCompleteEvent(id = lore_revision_tree_delete_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_delete_complete_event_data_t.SIZE)), error_code = lore_revision_tree_delete_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_delete_complete_event_data_t.SIZE)))
            207 -> RevisionTreeModifyCompleteEvent(id = lore_revision_tree_modify_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_modify_complete_event_data_t.SIZE)), node_id = lore_revision_tree_modify_complete_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_modify_complete_event_data_t.SIZE)), error_code = lore_revision_tree_modify_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_modify_complete_event_data_t.SIZE)))
            208 -> RevisionTreeMoveCompleteEvent(id = lore_revision_tree_move_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_move_complete_event_data_t.SIZE)), node_id = lore_revision_tree_move_complete_event_data_t.node_id(payload.asSlice(0L, lore_revision_tree_move_complete_event_data_t.SIZE)), error_code = lore_revision_tree_move_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_move_complete_event_data_t.SIZE)))
            209 -> RevisionTreeMetadataSetCompleteEvent(id = lore_revision_tree_metadata_set_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_metadata_set_complete_event_data_t.SIZE)), error_code = lore_revision_tree_metadata_set_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_metadata_set_complete_event_data_t.SIZE)))
            210 -> RevisionTreeMetadataGetCompleteEvent(id = lore_revision_tree_metadata_get_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_metadata_get_complete_event_data_t.SIZE)), key = LoreCopy.string(lore_revision_tree_metadata_get_complete_event_data_t.key(payload.asSlice(0L, lore_revision_tree_metadata_get_complete_event_data_t.SIZE))), value = readMetadata(lore_revision_tree_metadata_get_complete_event_data_t.value(payload.asSlice(0L, lore_revision_tree_metadata_get_complete_event_data_t.SIZE))), error_code = lore_revision_tree_metadata_get_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_metadata_get_complete_event_data_t.SIZE)))
            211 -> RevisionTreeCommitCompleteEvent(id = lore_revision_tree_commit_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_commit_complete_event_data_t.SIZE)), revision_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_commit_complete_event_data_t.revision_hash(payload.asSlice(0L, lore_revision_tree_commit_complete_event_data_t.SIZE)))), new_tip_hash = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_commit_complete_event_data_t.new_tip_hash(payload.asSlice(0L, lore_revision_tree_commit_complete_event_data_t.SIZE)))), error_code = lore_revision_tree_commit_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_commit_complete_event_data_t.SIZE)))
            212 -> RevisionTreeCloseCompleteEvent(id = lore_revision_tree_close_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_close_complete_event_data_t.SIZE)), error_code = lore_revision_tree_close_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_close_complete_event_data_t.SIZE)))
            213 -> RevisionTreeListChildrenBeginEvent(id = lore_revision_tree_list_children_begin_event_data_t.id(payload.asSlice(0L, lore_revision_tree_list_children_begin_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_tree_list_children_begin_event_data_t.repository(payload.asSlice(0L, lore_revision_tree_list_children_begin_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_list_children_begin_event_data_t.revision(payload.asSlice(0L, lore_revision_tree_list_children_begin_event_data_t.SIZE)))), error_code = lore_revision_tree_list_children_begin_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_list_children_begin_event_data_t.SIZE)))
            214 -> RevisionTreeInfoEvent(id = lore_revision_tree_info_event_data_t.id(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)), repository = LoreCopy.fixedBytes(lore_partition_t.data(lore_revision_tree_info_event_data_t.repository(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)))), revision = LoreCopy.fixedBytes(lore_hash_t.data(lore_revision_tree_info_event_data_t.revision(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)))), parent = LoreCopy.inlineArray(lore_revision_tree_info_event_data_t.parent(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)), 2, 32L) { LoreCopy.fixedBytes(lore_hash_t.data(it)) }, creation_timestamp = lore_revision_tree_info_event_data_t.creation_timestamp(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)), author_identity = LoreCopy.string(lore_revision_tree_info_event_data_t.author_identity(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE))), metadata_key_count = lore_revision_tree_info_event_data_t.metadata_key_count(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)), error_code = lore_revision_tree_info_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_info_event_data_t.SIZE)))
            215 -> StorageMutableLoadItemCompleteEvent(id = lore_storage_mutable_load_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_mutable_load_item_complete_event_data_t.SIZE)), value = LoreCopy.fixedBytes(lore_hash_t.data(lore_storage_mutable_load_item_complete_event_data_t.value(payload.asSlice(0L, lore_storage_mutable_load_item_complete_event_data_t.SIZE)))), error_code = lore_storage_mutable_load_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_mutable_load_item_complete_event_data_t.SIZE)))
            216 -> StorageMutableStoreItemCompleteEvent(id = lore_storage_mutable_store_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_mutable_store_item_complete_event_data_t.SIZE)), error_code = lore_storage_mutable_store_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_mutable_store_item_complete_event_data_t.SIZE)))
            217 -> StorageMutableCompareAndSwapItemCompleteEvent(id = lore_storage_mutable_compare_and_swap_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_mutable_compare_and_swap_item_complete_event_data_t.SIZE)), previous = LoreCopy.fixedBytes(lore_hash_t.data(lore_storage_mutable_compare_and_swap_item_complete_event_data_t.previous(payload.asSlice(0L, lore_storage_mutable_compare_and_swap_item_complete_event_data_t.SIZE)))), error_code = lore_storage_mutable_compare_and_swap_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_mutable_compare_and_swap_item_complete_event_data_t.SIZE)))
            218 -> StorageMutableListEntryEvent(id = lore_storage_mutable_list_entry_event_data_t.id(payload.asSlice(0L, lore_storage_mutable_list_entry_event_data_t.SIZE)), key = LoreCopy.fixedBytes(lore_hash_t.data(lore_storage_mutable_list_entry_event_data_t.key(payload.asSlice(0L, lore_storage_mutable_list_entry_event_data_t.SIZE)))), value = LoreCopy.fixedBytes(lore_hash_t.data(lore_storage_mutable_list_entry_event_data_t.value(payload.asSlice(0L, lore_storage_mutable_list_entry_event_data_t.SIZE)))))
            219 -> StorageMutableListItemCompleteEvent(id = lore_storage_mutable_list_item_complete_event_data_t.id(payload.asSlice(0L, lore_storage_mutable_list_item_complete_event_data_t.SIZE)), error_code = lore_storage_mutable_list_item_complete_event_data_t.error_code(payload.asSlice(0L, lore_storage_mutable_list_item_complete_event_data_t.SIZE)))
            220 -> EvictionBeginEvent(target_fragments = lore_eviction_begin_event_data_t.target_fragments(payload.asSlice(0L, lore_eviction_begin_event_data_t.SIZE)))
            221 -> EvictionProgressEvent(evicted = lore_eviction_progress_event_data_t.evicted(payload.asSlice(0L, lore_eviction_progress_event_data_t.SIZE)))
            222 -> EvictionEndEvent(total_evicted = lore_eviction_end_event_data_t.total_evicted(payload.asSlice(0L, lore_eviction_end_event_data_t.SIZE)))
            223 -> CompactionBeginEvent(target_bytes = lore_compaction_begin_event_data_t.target_bytes(payload.asSlice(0L, lore_compaction_begin_event_data_t.SIZE)))
            224 -> CompactionProgressEvent(compacted_bytes = lore_compaction_progress_event_data_t.compacted_bytes(payload.asSlice(0L, lore_compaction_progress_event_data_t.SIZE)))
            225 -> CompactionEndEvent(total_compacted_bytes = lore_compaction_end_event_data_t.total_compacted_bytes(payload.asSlice(0L, lore_compaction_end_event_data_t.SIZE)))
            226 -> RevisionTreeBatchCompleteEvent(id = lore_revision_tree_batch_complete_event_data_t.id(payload.asSlice(0L, lore_revision_tree_batch_complete_event_data_t.SIZE)), error_code = lore_revision_tree_batch_complete_event_data_t.error_code(payload.asSlice(0L, lore_revision_tree_batch_complete_event_data_t.SIZE)))
            else -> UnknownEvent(lore_event_t.tag(event))
        }
    }
}
