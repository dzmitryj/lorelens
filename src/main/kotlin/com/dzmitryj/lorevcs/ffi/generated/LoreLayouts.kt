// Generated from lore.h 0.8.6 by :codegen. Do not edit.
package com.dzmitryj.lorevcs.ffi.generated

import java.lang.foreign.MemoryLayout
import java.lang.foreign.MemorySegment
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout

/**
 * Full address of a piece of content.
 * 
 * Pairs a content hash with a context identifier, so the same content can be
 * addressed under different contexts.
 */
object lore_address_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("hash"),
        lore_context_t.LAYOUT.withName("context"),
    ).withName("lore_address_t") as StructLayout

    /**
     * Content hash.
     */
    const val OFFSET_hash: Long = 0L
    fun hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_hash, 32L)

    /**
     * Context identifier paired with the hash.
     */
    const val OFFSET_context: Long = 32L
    fun context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_context, 16L)
}

/**
 * Arguments for clearing all stored authentication identities and tokens.
 */
object lore_auth_clear_args_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("_unused"),
    ).withName("lore_auth_clear_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET__unused, value)
    }
}

/**
 * Event data describing a stored authentication identity.
 */
object lore_auth_identity_event_data_t {
    const val SIZE: Long = 88L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("auth_url"),
        lore_string_t.LAYOUT.withName("resource"),
        lore_string_t.LAYOUT.withName("user_id"),
        lore_string_t.LAYOUT.withName("authorized_domains"),
        ValueLayout.JAVA_LONG.withName("expires"),
        lore_string_t.LAYOUT.withName("token"),
    ).withName("lore_auth_identity_event_data_t") as StructLayout

    /**
     * Auth service URL
     */
    const val OFFSET_auth_url: Long = 0L
    fun auth_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_auth_url, 16L)

    /**
     * Resource ID (empty for authentication tokens)
     */
    const val OFFSET_resource: Long = 16L
    fun resource(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_resource, 16L)

    /**
     * User identity
     */
    const val OFFSET_user_id: Long = 32L
    fun user_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_id, 16L)

    /**
     * Comma-separated list of authorized root domains
     */
    const val OFFSET_authorized_domains: Long = 48L
    fun authorized_domains(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_authorized_domains, 16L)

    /**
     * Expiry time in milliseconds since UNIX epoch, or 0 if unavailable
     */
    const val OFFSET_expires: Long = 64L
    fun expires(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_expires)
    fun expires(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_expires, value)
    }

    /**
     * Cached token (only populated when requested)
     */
    const val OFFSET_token: Long = 72L
    fun token(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_token, 16L)
}

/**
 * Arguments for listing all stored authentication identities across endpoints.
 */
object lore_auth_list_args_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("with_token"),
    ).withName("lore_auth_list_args_t") as StructLayout

    /**
     * Include the decrypted cached token in each identity
     */
    const val OFFSET_with_token: Long = 0L
    fun with_token(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_with_token)
    fun with_token(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_with_token, value)
    }
}

/**
 * Arguments for resolving user identities from locally stored JWT tokens.
 */
object lore_auth_local_user_info_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("auth_endpoint"),
        lore_string_array_t.LAYOUT.withName("user_ids"),
        ValueLayout.JAVA_BYTE.withName("with_token"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_auth_local_user_info_args_t") as StructLayout

    /**
     * Auth service remote URL; empty resolves from the repository's remote environment
     */
    const val OFFSET_auth_endpoint: Long = 0L
    fun auth_endpoint(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_auth_endpoint, 16L)

    /**
     * User identities to resolve; empty resolves the current user
     */
    const val OFFSET_user_ids: Long = 16L
    fun user_ids(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_ids, 16L)

    /**
     * Emit cached token details for identities with a local token
     */
    const val OFFSET_with_token: Long = 32L
    fun with_token(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_with_token)
    fun with_token(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_with_token, value)
    }
}

/**
 * Arguments for authenticating interactively via browser-based login flow.
 */
object lore_auth_login_interactive_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote_url"),
        ValueLayout.JAVA_BYTE.withName("no_browser"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_auth_login_interactive_args_t") as StructLayout

    /**
     * Remote URL; empty resolves from the repository config
     */
    const val OFFSET_remote_url: Long = 0L
    fun remote_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_url, 16L)

    /**
     * Emit the login URL instead of opening a browser
     */
    const val OFFSET_no_browser: Long = 16L
    fun no_browser(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_browser)
    fun no_browser(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_browser, value)
    }
}

/**
 * Arguments for authenticating against a remote URL using a provided token.
 */
object lore_auth_login_with_token_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote_url"),
        lore_string_t.LAYOUT.withName("token"),
        lore_string_t.LAYOUT.withName("token_type"),
        lore_string_t.LAYOUT.withName("auth_url"),
    ).withName("lore_auth_login_with_token_args_t") as StructLayout

    /**
     * Remote URL; empty resolves from the repository config
     */
    const val OFFSET_remote_url: Long = 0L
    fun remote_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_url, 16L)

    /**
     * Authentication token
     */
    const val OFFSET_token: Long = 16L
    fun token(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_token, 16L)

    /**
     * Token type
     */
    const val OFFSET_token_type: Long = 32L
    fun token_type(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_token_type, 16L)

    /**
     * Auth service URL with scheme (e.g. `ucs-auth://auth.example.com`); used
     * directly when non-empty, required when no remote URL is available
     */
    const val OFFSET_auth_url: Long = 48L
    fun auth_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_auth_url, 16L)
}

/**
 * Arguments for removing stored authentication and authorization tokens.
 */
object lore_auth_logout_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("auth_url"),
        lore_string_t.LAYOUT.withName("resource"),
        lore_string_t.LAYOUT.withName("user_id"),
    ).withName("lore_auth_logout_args_t") as StructLayout

    /**
     * Auth service URL; empty resolves from the repository
     */
    const val OFFSET_auth_url: Long = 0L
    fun auth_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_auth_url, 16L)

    /**
     * Resource ID (e.g. `urc-{id}`); empty removes all tokens for the auth URL
     */
    const val OFFSET_resource: Long = 16L
    fun resource(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_resource, 16L)

    /**
     * User identity to remove; empty removes all identities
     */
    const val OFFSET_user_id: Long = 32L
    fun user_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_id, 16L)
}

/**
 * Event data carrying an authentication URL for the user to open.
 */
object lore_auth_url_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("url"),
    ).withName("lore_auth_url_event_data_t") as StructLayout

    /**
     * Authentication URL
     */
    const val OFFSET_url: Long = 0L
    fun url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_url, 16L)
}

/**
 * Arguments for resolving user IDs to display names via the remote auth service.
 */
object lore_auth_user_info_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("user_ids"),
    ).withName("lore_auth_user_info_args_t") as StructLayout

    /**
     * User IDs to resolve; empty resolves the current user locally
     */
    const val OFFSET_user_ids: Long = 0L
    fun user_ids(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_ids, 16L)
}

/**
 * Event data resolving a user identity to a display name.
 */
object lore_auth_user_info_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
    ).withName("lore_auth_user_info_event_data_t") as StructLayout

    /**
     * User identity
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Display name for the user
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)
}

/**
 * Event data carrying a user token along with the identity it belongs to.
 */
object lore_auth_user_token_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_string_t.LAYOUT.withName("token"),
        lore_string_t.LAYOUT.withName("preferred_username"),
        ValueLayout.JAVA_BYTE.withName("flag_service_account"),
        MemoryLayout.paddingLayout(7),
        ValueLayout.JAVA_LONG.withName("expires"),
    ).withName("lore_auth_user_token_event_data_t") as StructLayout

    /**
     * User identity
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Display name for the user
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * The token string
     */
    const val OFFSET_token: Long = 32L
    fun token(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_token, 16L)

    /**
     * Preferred username from the token
     */
    const val OFFSET_preferred_username: Long = 48L
    fun preferred_username(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_preferred_username, 16L)

    /**
     * Non-zero if the identity is a service account
     */
    const val OFFSET_flag_service_account: Long = 64L
    fun flag_service_account(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_service_account)
    fun flag_service_account(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_service_account, value)
    }

    /**
     * Expiry time in milliseconds since UNIX epoch, or 0 if unavailable
     */
    const val OFFSET_expires: Long = 72L
    fun expires(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_expires)
    fun expires(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_expires, value)
    }
}

/**
 * A block of raw bytes described by a pointer and a length.
 */
object lore_binary_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("payload"),
        ValueLayout.JAVA_LONG.withName("length"),
    ).withName("lore_binary_t") as StructLayout

    /**
     * Pointer to the start of the byte block.
     */
    const val OFFSET_payload: Long = 0L
    fun payload(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_payload)
    fun payload(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_payload, value)
    }

    /**
     * Number of bytes in the block.
     */
    const val OFFSET_length: Long = 8L
    fun length(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_length)
    fun length(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_length, value)
    }
}

/**
 * Arguments for archiving a branch locally and (unless local mode) on the remote.
 */
object lore_branch_archive_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_archive_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Event data reported when a branch is archived.
 */
object lore_branch_archive_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("name"),
    ).withName("lore_branch_archive_event_data_t") as StructLayout

    /**
     * Name of the archived branch.
     */
    const val OFFSET_name: Long = 0L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)
}

/**
 * Arguments for creating a new branch with the given name and category.
 */
object lore_branch_create_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("category"),
        lore_string_t.LAYOUT.withName("id"),
    ).withName("lore_branch_create_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Category of the branch
     */
    const val OFFSET_category: Long = 16L
    fun category(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_category, 16L)

    /**
     * Optional explicit branch ID (hex-encoded 16-byte context)
     */
    const val OFFSET_id: Long = 32L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)
}

/**
 * Event data reported when a branch is created.
 */
object lore_branch_create_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("name"),
        lore_hash_t.LAYOUT.withName("latest"),
        ValueLayout.JAVA_BYTE.withName("is_commit"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_create_event_data_t") as StructLayout

    /**
     * Name of the created branch.
     */
    const val OFFSET_name: Long = 0L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Latest revision the new branch points at.
     */
    const val OFFSET_latest: Long = 16L
    fun latest(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest, 32L)

    /**
     * Set when creating the branch also produced a new commit.
     */
    const val OFFSET_is_commit: Long = 48L
    fun is_commit(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_commit)
    fun is_commit(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_commit, value)
    }
}

/**
 * Arguments for diffing two branches, reporting changed and conflicting files.
 */
object lore_branch_diff_args_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("source"),
        lore_string_t.LAYOUT.withName("target"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("auto_resolve"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_diff_args_t") as StructLayout

    /**
     * Source branch name
     */
    const val OFFSET_source: Long = 0L
    fun source(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source, 16L)

    /**
     * Target branch name
     */
    const val OFFSET_target: Long = 16L
    fun target(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target, 16L)

    /**
     * Optional path in the repository to limit the diff to
     */
    const val OFFSET_path: Long = 32L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Attempt to auto resolve conflicts
     */
    const val OFFSET_auto_resolve: Long = 48L
    fun auto_resolve(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_auto_resolve)
    fun auto_resolve(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_auto_resolve, value)
    }
}

/**
 * Event data reported at the start of a branch diff.
 */
object lore_branch_diff_begin_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_branch_diff_begin_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data reported at the start of the change section of a branch diff.
 */
object lore_branch_diff_change_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("changes_count"),
    ).withName("lore_branch_diff_change_begin_event_data_t") as StructLayout

    /**
     * Number of changes that follow.
     */
    const val OFFSET_changes_count: Long = 0L
    fun changes_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_changes_count)
    fun changes_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_changes_count, value)
    }
}

/**
 * Event data reported at the end of the change section of a branch diff.
 */
object lore_branch_diff_change_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_branch_diff_change_end_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data reporting a single change in a branch diff.
 */
object lore_branch_diff_change_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_branch_diff_node_data_t.LAYOUT.withName("change"),
    ).withName("lore_branch_diff_change_event_data_t") as StructLayout

    /**
     * The changed node.
     */
    const val OFFSET_change: Long = 0L
    fun change(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_change, 32L)
}

/**
 * Event data reported at the start of the conflict section of a branch diff.
 */
object lore_branch_diff_conflict_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("conflicts_count"),
    ).withName("lore_branch_diff_conflict_begin_event_data_t") as StructLayout

    /**
     * Number of conflicts that follow.
     */
    const val OFFSET_conflicts_count: Long = 0L
    fun conflicts_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_conflicts_count)
    fun conflicts_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_conflicts_count, value)
    }
}

/**
 * Event data reported at the end of the conflict section of a branch diff.
 */
object lore_branch_diff_conflict_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_branch_diff_conflict_end_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data reporting a single conflict in a branch diff.
 */
object lore_branch_diff_conflict_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_branch_diff_node_data_t.LAYOUT.withName("source_change"),
        lore_branch_diff_node_data_t.LAYOUT.withName("target_change"),
    ).withName("lore_branch_diff_conflict_event_data_t") as StructLayout

    /**
     * The change on the source side of the conflict.
     */
    const val OFFSET_source_change: Long = 0L
    fun source_change(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_change, 32L)

    /**
     * The change on the target side of the conflict.
     */
    const val OFFSET_target_change: Long = 32L
    fun target_change(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_change, 32L)
}

/**
 * Event data reported at the end of a branch diff.
 */
object lore_branch_diff_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_branch_diff_end_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data describing a single changed node in a branch diff.
 */
object lore_branch_diff_node_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("automerged"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_diff_node_data_t") as StructLayout

    /**
     * File action applied to the node.
     */
    const val OFFSET_action: Long = 0L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Path of the node.
     */
    const val OFFSET_path: Long = 8L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Set when the change was merged automatically.
     */
    const val OFFSET_automerged: Long = 24L
    fun automerged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_automerged)
    fun automerged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_automerged, value)
    }
}

/**
 * Arguments for retrieving branch metadata (name, id, category, protection status).
 */
object lore_branch_info_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_info_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Event data reported with information about a single branch.
 */
object lore_branch_info_event_data_t {
    const val SIZE: Long = 208L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_string_t.LAYOUT.withName("category"),
        lore_hash_t.LAYOUT.withName("latest"),
        lore_hash_t.LAYOUT.withName("latest_remote"),
        lore_context_t.LAYOUT.withName("parent"),
        lore_hash_t.LAYOUT.withName("branch_point"),
        lore_string_t.LAYOUT.withName("creator"),
        ValueLayout.JAVA_LONG.withName("created"),
        lore_branch_point_array_t.LAYOUT.withName("stack"),
        ValueLayout.JAVA_BYTE.withName("archived"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_info_event_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Branch name.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Branch category.
     */
    const val OFFSET_category: Long = 32L
    fun category(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_category, 16L)

    /**
     * Latest revision known locally for the branch.
     */
    const val OFFSET_latest: Long = 48L
    fun latest(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest, 32L)

    /**
     * Latest revision known on the remote for the branch.
     */
    const val OFFSET_latest_remote: Long = 80L
    fun latest_remote(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest_remote, 32L)

    /**
     * Identifier of the parent branch.
     */
    const val OFFSET_parent: Long = 112L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 16L)

    /**
     * Revision on the parent branch where this branch was created.
     */
    const val OFFSET_branch_point: Long = 128L
    fun branch_point(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_point, 32L)

    /**
     * Identifier of the user who created the branch.
     */
    const val OFFSET_creator: Long = 160L
    fun creator(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_creator, 16L)

    /**
     * Creation time of the branch as a timestamp.
     */
    const val OFFSET_created: Long = 176L
    fun created(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_created)
    fun created(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_created, value)
    }

    /**
     * Stack of branch points this branch was created from.
     */
    const val OFFSET_stack: Long = 184L
    fun stack(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_stack, 16L)

    /**
     * Set when the branch has been archived.
     */
    const val OFFSET_archived: Long = 200L
    fun archived(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_archived)
    fun archived(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_archived, value)
    }
}

/**
 * Event data reported for each entry in a branch latest-revision history listing.
 */
object lore_branch_latest_list_entry_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_latest_list_entry_event_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Revision recorded in the history entry.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for listing all branches in the repository.
 */
object lore_branch_list_args_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("archived"),
    ).withName("lore_branch_list_args_t") as StructLayout

    /**
     * Include archived local branches in listing
     */
    const val OFFSET_archived: Long = 0L
    fun archived(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_archived)
    fun archived(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_archived, value)
    }
}

/**
 * Event data reported at the start of a branch listing.
 */
object lore_branch_list_begin_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("location"),
    ).withName("lore_branch_list_begin_event_data_t") as StructLayout

    /**
     * Location the listed branches come from.
     */
    const val OFFSET_location: Long = 0L
    fun location(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_location)
    fun location(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_location, value)
    }
}

/**
 * Event data reported at the end of a branch listing.
 */
object lore_branch_list_end_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("location"),
        MemoryLayout.paddingLayout(4),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_list_end_event_data_t") as StructLayout

    /**
     * Location the listed branches came from.
     */
    const val OFFSET_location: Long = 0L
    fun location(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_location)
    fun location(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_location, value)
    }

    /**
     * Number of branches that were listed.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Event data reported for each branch in a branch listing.
 */
object lore_branch_list_entry_event_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("location"),
        lore_context_t.LAYOUT.withName("id"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("name"),
        lore_string_t.LAYOUT.withName("category"),
        lore_hash_t.LAYOUT.withName("latest"),
        lore_branch_point_array_t.LAYOUT.withName("stack"),
        lore_string_t.LAYOUT.withName("creator"),
        ValueLayout.JAVA_LONG.withName("created"),
        ValueLayout.JAVA_BYTE.withName("is_current"),
        ValueLayout.JAVA_BYTE.withName("archived"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_branch_list_entry_event_data_t") as StructLayout

    /**
     * Location this branch comes from.
     */
    const val OFFSET_location: Long = 0L
    fun location(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_location)
    fun location(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_location, value)
    }

    /**
     * Branch identifier.
     */
    const val OFFSET_id: Long = 4L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Branch name.
     */
    const val OFFSET_name: Long = 24L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Branch category.
     */
    const val OFFSET_category: Long = 40L
    fun category(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_category, 16L)

    /**
     * Latest revision the branch points at.
     */
    const val OFFSET_latest: Long = 56L
    fun latest(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest, 32L)

    /**
     * Stack of branch points this branch was created from.
     */
    const val OFFSET_stack: Long = 88L
    fun stack(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_stack, 16L)

    /**
     * Identifier of the user who created the branch.
     */
    const val OFFSET_creator: Long = 104L
    fun creator(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_creator, 16L)

    /**
     * Creation time of the branch as a timestamp.
     */
    const val OFFSET_created: Long = 120L
    fun created(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_created)
    fun created(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_created, value)
    }

    /**
     * Set when this branch is the current branch.
     */
    const val OFFSET_is_current: Long = 128L
    fun is_current(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_current)
    fun is_current(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_current, value)
    }

    /**
     * Set when this branch has been archived.
     */
    const val OFFSET_archived: Long = 129L
    fun archived(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_archived)
    fun archived(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_archived, value)
    }
}

/**
 * Arguments for aborting an in-progress branch merge.
 */
object lore_branch_merge_abort_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link"),
        ValueLayout.JAVA_BYTE.withName("ignore_links"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_merge_abort_args_t") as StructLayout

    /**
     * Optional link path for link-scoped abort
     */
    const val OFFSET_link: Long = 0L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Abort only the main repository merge, keeping link pin updates
     */
    const val OFFSET_ignore_links: Long = 16L
    fun ignore_links(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignore_links)
    fun ignore_links(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignore_links, value)
    }
}

/**
 * Data for the event sent when a branch merge abort starts.
 */
object lore_branch_merge_abort_begin_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("state_staged_revision"),
        lore_hash_t.LAYOUT.withName("state_current_revision"),
    ).withName("lore_branch_merge_abort_begin_event_data_t") as StructLayout

    /**
     * The staged revision being discarded.
     */
    const val OFFSET_state_staged_revision: Long = 0L
    fun state_staged_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_staged_revision, 32L)

    /**
     * The current revision the working state returns to.
     */
    const val OFFSET_state_current_revision: Long = 32L
    fun state_current_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_current_revision, 32L)
}

/**
 * Data for the event sent when a branch merge abort finishes.
 */
object lore_branch_merge_abort_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_branch_merge_abort_end_event_data_t") as StructLayout

    /**
     * Placeholder field. The event carries no payload.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for the event sent for each file the merge left in conflict.
 */
object lore_branch_merge_conflict_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_branch_merge_conflict_file_event_data_t") as StructLayout

    /**
     * The path of the conflicted file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for merging the current branch's staged changes into a target branch.
 */
object lore_branch_merge_into_args_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_context_t.LAYOUT.withName("branch_id"),
        lore_string_t.LAYOUT.withName("message"),
        lore_string_t.LAYOUT.withName("link"),
        ValueLayout.JAVA_BYTE.withName("ignore_links"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_merge_into_args_t") as StructLayout

    /**
     * Name of the target branch to merge into
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * ID of the target branch to merge into
     */
    const val OFFSET_branch_id: Long = 16L
    fun branch_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_id, 16L)

    /**
     * Commit message for the auto-commit
     */
    const val OFFSET_message: Long = 32L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * Optional link path for link-scoped merge into
     */
    const val OFFSET_link: Long = 48L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Merge only the main repository, skipping all linked repositories
     */
    const val OFFSET_ignore_links: Long = 64L
    fun ignore_links(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignore_links)
    fun ignore_links(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignore_links, value)
    }
}

/**
 * Data for the event sent before files are merged into the working tree.
 */
object lore_branch_merge_into_file_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_merge_into_file_begin_event_data_t") as StructLayout

    /**
     * The number of files to merge.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for the event sent after files are merged into the working tree.
 */
object lore_branch_merge_into_file_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_merge_into_file_end_event_data_t") as StructLayout

    /**
     * The number of files merged.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for the event sent for each file merged into the working tree.
 */
object lore_branch_merge_into_file_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_BYTE.withName("is_file"),
        ValueLayout.JAVA_BYTE.withName("is_directory"),
        ValueLayout.JAVA_BYTE.withName("is_link"),
        MemoryLayout.paddingLayout(5),
    ).withName("lore_branch_merge_into_file_event_data_t") as StructLayout

    /**
     * The path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * The action applied to the file.
     */
    const val OFFSET_action: Long = 16L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * The size of the file in bytes.
     */
    const val OFFSET_size: Long = 24L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Set when the entry is a regular file.
     */
    const val OFFSET_is_file: Long = 32L
    fun is_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_file)
    fun is_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_file, value)
    }

    /**
     * Set when the entry is a directory.
     */
    const val OFFSET_is_directory: Long = 33L
    fun is_directory(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_directory)
    fun is_directory(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_directory, value)
    }

    /**
     * Set when the entry is a link.
     */
    const val OFFSET_is_link: Long = 34L
    fun is_link(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_link)
    fun is_link(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_link, value)
    }
}

/**
 * Data for the event sent before the merge transfers fragments.
 */
object lore_branch_merge_into_fragment_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
    ).withName("lore_branch_merge_into_fragment_begin_event_data_t") as StructLayout

    /**
     * The number of fragments to transfer.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }
}

/**
 * Data for the event sent after the merge transfers fragments.
 */
object lore_branch_merge_into_fragment_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
    ).withName("lore_branch_merge_into_fragment_end_event_data_t") as StructLayout

    /**
     * The number of fragments transferred.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }
}

/**
 * Data for the event sent as the merge transfers fragments.
 */
object lore_branch_merge_into_fragment_progress_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("complete"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_merge_into_fragment_progress_event_data_t") as StructLayout

    /**
     * The number of fragments transferred so far.
     */
    const val OFFSET_complete: Long = 0L
    fun complete(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_complete)
    fun complete(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_complete, value)
    }

    /**
     * The total number of fragments to transfer.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for the event sent for each revision merged into the working tree.
 */
object lore_branch_merge_into_revision_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
    ).withName("lore_branch_merge_into_revision_event_data_t") as StructLayout

    /**
     * The revision merged.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The sequential number of the revision.
     */
    const val OFFSET_revision_number: Long = 32L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }
}

/**
 * Data for the event sent before the merge synchronizes revisions.
 */
object lore_branch_merge_into_sync_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_merge_into_sync_begin_event_data_t") as StructLayout

    /**
     * The number of revisions to synchronize.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for the event sent after the merge synchronizes revisions.
 */
object lore_branch_merge_into_sync_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_merge_into_sync_end_event_data_t") as StructLayout

    /**
     * The number of revisions synchronized.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for the event sent when a link is skipped during a merge.
 */
object lore_branch_merge_link_skipped_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link_path"),
        lore_partition_t.LAYOUT.withName("repository"),
        ValueLayout.JAVA_BYTE.withName("reason"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_merge_link_skipped_event_data_t") as StructLayout

    /**
     * The mount path of the skipped link.
     */
    const val OFFSET_link_path: Long = 0L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)

    /**
     * The repository of the skipped link.
     */
    const val OFFSET_repository: Long = 16L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * The reason the link was skipped.
     */
    const val OFFSET_reason: Long = 32L
    fun reason(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reason)
    fun reason(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reason, value)
    }
}

/**
 * Arguments for marking conflicted paths as resolved.
 */
object lore_branch_merge_resolve_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_branch_merge_resolve_args_t") as StructLayout

    /**
     * Paths to mark resolved
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for the event sent when a file in a merge is marked resolved.
 */
object lore_branch_merge_resolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_branch_merge_resolve_file_event_data_t") as StructLayout

    /**
     * The path of the file marked resolved.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for resolving conflicts by accepting the local ("mine") version.
 */
object lore_branch_merge_resolve_mine_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_branch_merge_resolve_mine_args_t") as StructLayout

    /**
     * Paths to resolve as "mine"
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for the event sent when a revision in a merge is marked resolved.
 */
object lore_branch_merge_resolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_merge_resolve_revision_event_data_t") as StructLayout

    /**
     * The repository of the revision marked resolved.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * The revision marked resolved.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for resolving conflicts by accepting the incoming ("theirs") version.
 */
object lore_branch_merge_resolve_theirs_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_branch_merge_resolve_theirs_args_t") as StructLayout

    /**
     * Paths to resolve as "theirs"
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for re-applying merge conflict resolution for the given paths.
 */
object lore_branch_merge_restart_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_branch_merge_restart_args_t") as StructLayout

    /**
     * Paths to re-materialize
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for merging a source branch into the current branch.
 */
object lore_branch_merge_start_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("message"),
        ValueLayout.JAVA_BYTE.withName("no_commit"),
        MemoryLayout.paddingLayout(7),
        lore_string_t.LAYOUT.withName("link"),
        ValueLayout.JAVA_BYTE.withName("ignore_links"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_merge_start_args_t") as StructLayout

    /**
     * Name of the source branch to merge into the current branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Message to use for an auto commit if no conflicts arise
     */
    const val OFFSET_message: Long = 16L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * Disable auto commit even if no conflicts arise
     */
    const val OFFSET_no_commit: Long = 32L
    fun no_commit(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_commit)
    fun no_commit(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_commit, value)
    }

    /**
     * Optional link path for link-scoped merge
     */
    const val OFFSET_link: Long = 40L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Merge only the main repository, skipping all linked repositories
     */
    const val OFFSET_ignore_links: Long = 56L
    fun ignore_links(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignore_links)
    fun ignore_links(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignore_links, value)
    }
}

/**
 * Data for the event sent when a branch merge starts.
 */
object lore_branch_merge_start_begin_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
    ).withName("lore_branch_merge_start_begin_event_data_t") as StructLayout

    /**
     * The source branch being merged.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * The source revision being merged.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The sequential number of the source revision.
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }
}

/**
 * Data for the event sent when a branch merge finishes.
 */
object lore_branch_merge_start_end_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_revision_sync_progress_event_data_t.LAYOUT.withName("stats"),
        lore_hash_t.LAYOUT.withName("signature"),
        ValueLayout.JAVA_BYTE.withName("has_conflicts"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_merge_start_end_event_data_t") as StructLayout

    /**
     * Progress totals collected while applying the merge.
     */
    const val OFFSET_stats: Long = 0L
    fun stats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_stats, 72L)

    /**
     * The revision produced by the merge.
     */
    const val OFFSET_signature: Long = 72L
    fun signature(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_signature, 32L)

    /**
     * Set when the merge produced file conflicts.
     */
    const val OFFSET_has_conflicts: Long = 104L
    fun has_conflicts(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts)
    fun has_conflicts(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts, value)
    }
}

/**
 * Arguments for marking resolved merge paths as unresolved again.
 */
object lore_branch_merge_unresolve_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_branch_merge_unresolve_args_t") as StructLayout

    /**
     * Paths to mark unresolved
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for the event sent when a file in a merge is marked unresolved.
 */
object lore_branch_merge_unresolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_branch_merge_unresolve_file_event_data_t") as StructLayout

    /**
     * The path of the file marked unresolved.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for the event sent when a revision in a merge is marked unresolved.
 */
object lore_branch_merge_unresolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_merge_unresolve_revision_event_data_t") as StructLayout

    /**
     * The repository of the revision marked unresolved.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * The revision marked unresolved.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for removing keys from branch metadata.
 */
object lore_branch_metadata_clear_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_array_t.LAYOUT.withName("keys"),
    ).withName("lore_branch_metadata_clear_args_t") as StructLayout

    /**
     * Branch name or identifier
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Keys to clear (empty array clears all user-defined keys)
     */
    const val OFFSET_keys: Long = 16L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)
}

/**
 * Arguments for retrieving branch metadata (one key or all).
 */
object lore_branch_metadata_get_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("key"),
    ).withName("lore_branch_metadata_get_args_t") as StructLayout

    /**
     * Branch name or identifier
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Metadata key (empty string lists all)
     */
    const val OFFSET_key: Long = 16L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)
}

/**
 * Arguments for setting one or more key-value pairs on branch metadata.
 */
object lore_branch_metadata_set_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_array_t.LAYOUT.withName("keys"),
        lore_string_array_t.LAYOUT.withName("values"),
        lore_metadata_type_array_t.LAYOUT.withName("formats"),
    ).withName("lore_branch_metadata_set_args_t") as StructLayout

    /**
     * Branch name or identifier
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Metadata keys to set (parallel with `values`/`formats`)
     */
    const val OFFSET_keys: Long = 16L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)

    /**
     * Values to set, one per key (decoded per the matching `formats` entry)
     */
    const val OFFSET_values: Long = 32L
    fun values(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_values, 16L)

    /**
     * Value type for each key, one per key
     */
    const val OFFSET_formats: Long = 48L
    fun formats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_formats, 16L)
}

/**
 * Event data warning that several instances share the same checked-out branch.
 */
object lore_branch_multiple_instance_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_instance_id_array_t.LAYOUT.withName("instance_ids"),
        lore_string_array_t.LAYOUT.withName("instance_paths"),
    ).withName("lore_branch_multiple_instance_event_data_t") as StructLayout

    /**
     * The branch checked out by more than one instance
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Identifiers of the other instances on the branch
     */
    const val OFFSET_instance_ids: Long = 16L
    fun instance_ids(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_instance_ids, 16L)

    /**
     * Filesystem paths of the other instances on the branch
     */
    const val OFFSET_instance_paths: Long = 32L
    fun instance_paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_instance_paths, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_branch_point_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_branch_point_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * A branch paired with a revision on that branch.
 */
object lore_branch_point_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_point_t") as StructLayout

    /**
     * The branch.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * The revision on the branch.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for applying write protection to a branch.
 */
object lore_branch_protect_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_protect_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Event data reported when a branch is protected.
 */
object lore_branch_protect_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("name"),
    ).withName("lore_branch_protect_event_data_t") as StructLayout

    /**
     * Name of the protected branch.
     */
    const val OFFSET_name: Long = 0L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)
}

/**
 * Arguments for pushing a branch and its revisions to the remote.
 */
object lore_branch_push_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        ValueLayout.JAVA_BYTE.withName("fast_forward_merge"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_push_args_t") as StructLayout

    /**
     * Optional branch to push, current branch if not given
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Allow the server to fast-forward merge if the target branch head has moved
     */
    const val OFFSET_fast_forward_merge: Long = 16L
    fun fast_forward_merge(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_fast_forward_merge)
    fun fast_forward_merge(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_fast_forward_merge, value)
    }
}

/**
 * Data for the event sent before a branch is created on the remote.
 */
object lore_branch_push_branch_create_begin_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("local_revision"),
    ).withName("lore_branch_push_branch_create_begin_event_data_t") as StructLayout

    /**
     * The local revision the branch starts from.
     */
    const val OFFSET_local_revision: Long = 0L
    fun local_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_local_revision, 32L)
}

/**
 * Data for the event sent after a branch is created on the remote.
 */
object lore_branch_push_branch_create_end_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("remote_revision"),
    ).withName("lore_branch_push_branch_create_end_event_data_t") as StructLayout

    /**
     * The revision the branch points to on the remote.
     */
    const val OFFSET_remote_revision: Long = 0L
    fun remote_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_revision, 32L)
}

/**
 * Data for the event sent when a branch push starts.
 */
object lore_branch_push_event_data_t {
    const val SIZE: Long = 152L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("branch_name"),
        lore_hash_t.LAYOUT.withName("remote_revision"),
        lore_hash_t.LAYOUT.withName("local_revision"),
        ValueLayout.JAVA_LONG.withName("remote_history"),
        ValueLayout.JAVA_LONG.withName("local_history"),
        ValueLayout.JAVA_BYTE.withName("flag_already_pushed"),
        ValueLayout.JAVA_BYTE.withName("flag_default"),
        ValueLayout.JAVA_BYTE.withName("flag_link"),
        ValueLayout.JAVA_BYTE.withName("flag_layer"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_branch_push_event_data_t") as StructLayout

    /**
     * The remote being pushed to.
     */
    const val OFFSET_remote: Long = 0L
    fun remote(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote, 16L)

    /**
     * The repository being pushed.
     */
    const val OFFSET_repository: Long = 16L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * The branch being pushed.
     */
    const val OFFSET_branch: Long = 32L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * The name of the branch being pushed.
     */
    const val OFFSET_branch_name: Long = 48L
    fun branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_name, 16L)

    /**
     * The latest revision of the branch on the remote.
     */
    const val OFFSET_remote_revision: Long = 64L
    fun remote_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_revision, 32L)

    /**
     * The latest revision of the branch in the local repository.
     */
    const val OFFSET_local_revision: Long = 96L
    fun local_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_local_revision, 32L)

    /**
     * The number of revisions on the remote that are not present locally.
     */
    const val OFFSET_remote_history: Long = 128L
    fun remote_history(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_remote_history)
    fun remote_history(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_remote_history, value)
    }

    /**
     * The number of local revisions to push.
     */
    const val OFFSET_local_history: Long = 136L
    fun local_history(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_local_history)
    fun local_history(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_local_history, value)
    }

    /**
     * Set when the local revision is already present on the remote.
     */
    const val OFFSET_flag_already_pushed: Long = 144L
    fun flag_already_pushed(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_already_pushed)
    fun flag_already_pushed(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_already_pushed, value)
    }

    /**
     * Set when the branch is the repository's default branch.
     */
    const val OFFSET_flag_default: Long = 145L
    fun flag_default(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_default)
    fun flag_default(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_default, value)
    }

    /**
     * Set when the repository is a linked repository.
     */
    const val OFFSET_flag_link: Long = 146L
    fun flag_link(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_link)
    fun flag_link(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_link, value)
    }

    /**
     * Set when the repository is a layer.
     */
    const val OFFSET_flag_layer: Long = 147L
    fun flag_layer(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_layer)
    fun flag_layer(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_layer, value)
    }
}

/**
 * Data for the event sent before fragments are transferred during push.
 */
object lore_branch_push_fragment_begin_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
        ValueLayout.JAVA_LONG.withName("bytes_total"),
    ).withName("lore_branch_push_fragment_begin_event_data_t") as StructLayout

    /**
     * The number of fragments to transfer.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }

    /**
     * The total number of bytes to transfer.
     */
    const val OFFSET_bytes_total: Long = 8L
    fun bytes_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_total)
    fun bytes_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_total, value)
    }
}

/**
 * Data for the event sent after fragments are transferred during push.
 */
object lore_branch_push_fragment_end_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
        ValueLayout.JAVA_LONG.withName("bytes_transferred"),
    ).withName("lore_branch_push_fragment_end_event_data_t") as StructLayout

    /**
     * The number of fragments transferred.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }

    /**
     * The number of bytes transferred.
     */
    const val OFFSET_bytes_transferred: Long = 8L
    fun bytes_transferred(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred)
    fun bytes_transferred(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred, value)
    }
}

/**
 * Data for the event sent as fragments are transferred during push.
 */
object lore_branch_push_fragment_progress_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("complete"),
        ValueLayout.JAVA_LONG.withName("count"),
        ValueLayout.JAVA_LONG.withName("bytes_transferred"),
        ValueLayout.JAVA_LONG.withName("bytes_total"),
    ).withName("lore_branch_push_fragment_progress_event_data_t") as StructLayout

    /**
     * The number of fragments transferred so far.
     */
    const val OFFSET_complete: Long = 0L
    fun complete(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_complete)
    fun complete(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_complete, value)
    }

    /**
     * The total number of fragments to transfer.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }

    /**
     * The number of bytes transferred so far.
     */
    const val OFFSET_bytes_transferred: Long = 16L
    fun bytes_transferred(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred)
    fun bytes_transferred(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred, value)
    }

    /**
     * The total number of bytes to transfer.
     */
    const val OFFSET_bytes_total: Long = 24L
    fun bytes_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_total)
    fun bytes_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_total, value)
    }
}

/**
 * Data for the event sent before a revision is pushed to the remote.
 */
object lore_branch_push_revision_push_begin_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("remote_revision"),
        lore_hash_t.LAYOUT.withName("local_revision"),
    ).withName("lore_branch_push_revision_push_begin_event_data_t") as StructLayout

    /**
     * The latest revision of the branch on the remote.
     */
    const val OFFSET_remote_revision: Long = 0L
    fun remote_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_revision, 32L)

    /**
     * The local revision being pushed.
     */
    const val OFFSET_local_revision: Long = 32L
    fun local_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_local_revision, 32L)
}

/**
 * Data for the event sent after a revision is pushed to the remote.
 */
object lore_branch_push_revision_push_end_event_data_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("old_remote_revision"),
        lore_hash_t.LAYOUT.withName("new_remote_revision"),
        ValueLayout.JAVA_LONG.withName("new_remote_revision_number"),
        lore_string_t.LAYOUT.withName("message"),
        ValueLayout.JAVA_BYTE.withName("fast_forward_merged"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_branch_push_revision_push_end_event_data_t") as StructLayout

    /**
     * The branch revision on the remote before the push.
     */
    const val OFFSET_old_remote_revision: Long = 0L
    fun old_remote_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_old_remote_revision, 32L)

    /**
     * The branch revision on the remote after the push.
     */
    const val OFFSET_new_remote_revision: Long = 32L
    fun new_remote_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_new_remote_revision, 32L)

    /**
     * The sequential number of the new remote revision.
     */
    const val OFFSET_new_remote_revision_number: Long = 64L
    fun new_remote_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_new_remote_revision_number)
    fun new_remote_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_new_remote_revision_number, value)
    }

    /**
     * A message returned by the remote for the push.
     */
    const val OFFSET_message: Long = 72L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * Set when the remote performed a fast-forward merge.
     */
    const val OFFSET_fast_forward_merged: Long = 88L
    fun fast_forward_merged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_fast_forward_merged)
    fun fast_forward_merged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_fast_forward_merged, value)
    }
}

/**
 * Data for the event sent when the remote assigns a pushed revision a new identity.
 */
object lore_branch_push_revision_push_update_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("old_revision"),
        lore_hash_t.LAYOUT.withName("new_revision"),
        ValueLayout.JAVA_LONG.withName("new_revision_number"),
    ).withName("lore_branch_push_revision_push_update_event_data_t") as StructLayout

    /**
     * The revision before the remote reassigned it.
     */
    const val OFFSET_old_revision: Long = 0L
    fun old_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_old_revision, 32L)

    /**
     * The revision the remote assigned.
     */
    const val OFFSET_new_revision: Long = 32L
    fun new_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_new_revision, 32L)

    /**
     * The sequential number of the new revision.
     */
    const val OFFSET_new_revision_number: Long = 64L
    fun new_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_new_revision_number)
    fun new_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_new_revision_number, value)
    }
}

/**
 * Data for the event sent before a revision's parent is rewritten during push.
 */
object lore_branch_push_revision_update_begin_event_data_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
        lore_hash_t.LAYOUT.withName("old_parent"),
        lore_hash_t.LAYOUT.withName("new_parent"),
    ).withName("lore_branch_push_revision_update_begin_event_data_t") as StructLayout

    /**
     * The revision being updated.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The previous parent revision.
     */
    const val OFFSET_old_parent: Long = 32L
    fun old_parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_old_parent, 32L)

    /**
     * The new parent revision.
     */
    const val OFFSET_new_parent: Long = 64L
    fun new_parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_new_parent, 32L)
}

/**
 * Data for the event sent after a revision's parent is rewritten during push.
 */
object lore_branch_push_revision_update_end_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_push_revision_update_end_event_data_t") as StructLayout

    /**
     * The updated revision.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for resetting a branch's local LATEST pointer to a specific revision.
 */
object lore_branch_reset_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_reset_args_t") as StructLayout

    /**
     * Revision to reset the local LATEST pointer to
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Branch to reset, current branch if empty
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Event data reported when a branch is reset to a revision.
 */
object lore_branch_reset_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_branch_reset_event_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Branch name.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Revision the branch was reset to.
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for switching the working directory to a different branch or revision.
 */
object lore_branch_switch_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("reset"),
        ValueLayout.JAVA_BYTE.withName("bare"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_branch_switch_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Hash of the revision
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Reset local modified files to match the incoming revision
     */
    const val OFFSET_reset: Long = 32L
    fun reset(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reset)
    fun reset(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reset, value)
    }

    /**
     * Only update anchor tracking without modifying or verifying files
     */
    const val OFFSET_bare: Long = 33L
    fun bare(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_bare)
    fun bare(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_bare, value)
    }
}

/**
 * Data for the event emitted when a branch switch starts.
 */
object lore_branch_switch_begin_event_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_branch_switch_data_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_switch_begin_event_data_t") as StructLayout

    /**
     * Details of the branch being switched to.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 136L)
}

/**
 * Details of the branch involved in a branch switch.
 */
object lore_branch_switch_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_hash_t.LAYOUT.withName("latest_local"),
        lore_hash_t.LAYOUT.withName("latest_remote"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_INT.withName("location"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_branch_switch_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Branch name.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Latest revision known locally for the branch.
     */
    const val OFFSET_latest_local: Long = 32L
    fun latest_local(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest_local, 32L)

    /**
     * Latest revision known on the remote for the branch.
     */
    const val OFFSET_latest_remote: Long = 64L
    fun latest_remote(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_latest_remote, 32L)

    /**
     * Revision the branch is switched to.
     */
    const val OFFSET_revision: Long = 96L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Where the branch exists: local, remote, or both.
     */
    const val OFFSET_location: Long = 128L
    fun location(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_location)
    fun location(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_location, value)
    }
}

/**
 * Data for the event emitted when a branch switch finishes.
 */
object lore_branch_switch_end_event_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_branch_switch_data_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_switch_end_event_data_t") as StructLayout

    /**
     * Details of the branch that was switched to.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 136L)
}

/**
 * Arguments for removing write protection from a branch.
 */
object lore_branch_unprotect_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_branch_unprotect_args_t") as StructLayout

    /**
     * Name of the branch
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Event data reported when a branch is unprotected.
 */
object lore_branch_unprotect_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("name"),
    ).withName("lore_branch_unprotect_event_data_t") as StructLayout

    /**
     * Name of the unprotected branch.
     */
    const val OFFSET_name: Long = 0L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)
}

/**
 * Borrowed byte slice handed to callbacks.
 * 
 * The pointer is valid only for the duration of the callback that receives
 * it; callers must copy the bytes if they need them beyond that scope.
 */
object lore_bytes_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("len"),
    ).withName("lore_bytes_t") as StructLayout

    /**
     * Pointer to the start of the byte slice.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of bytes in the slice.
     */
    const val OFFSET_len: Long = 8L
    fun len(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_len)
    fun len(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_len, value)
    }
}

/**
 * Event data reported at the start of aborting a cherry-pick.
 */
object lore_cherry_pick_abort_begin_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("state_staged_revision"),
        lore_hash_t.LAYOUT.withName("state_current_revision"),
    ).withName("lore_cherry_pick_abort_begin_event_data_t") as StructLayout

    /**
     * Identifier of the staged revision being discarded.
     */
    const val OFFSET_state_staged_revision: Long = 0L
    fun state_staged_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_staged_revision, 32L)

    /**
     * Identifier of the current revision being restored.
     */
    const val OFFSET_state_current_revision: Long = 32L
    fun state_current_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_current_revision, 32L)
}

/**
 * Event data reported at the end of aborting a cherry-pick.
 */
object lore_cherry_pick_abort_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_cherry_pick_abort_end_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data reported for a file in conflict during a cherry-pick.
 */
object lore_cherry_pick_conflict_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_cherry_pick_conflict_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a file is resolved during a cherry-pick.
 */
object lore_cherry_pick_resolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_cherry_pick_resolve_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a revision is resolved during a cherry-pick.
 */
object lore_cherry_pick_resolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_cherry_pick_resolve_revision_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the revision.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Event data reported at the start of a cherry-pick.
 */
object lore_cherry_pick_start_begin_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
    ).withName("lore_cherry_pick_start_begin_event_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Identifier of the revision being cherry-picked.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Number of the revision being cherry-picked.
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }
}

/**
 * Event data reported at the end of a cherry-pick.
 */
object lore_cherry_pick_start_end_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_revision_sync_progress_event_data_t.LAYOUT.withName("stats"),
        lore_hash_t.LAYOUT.withName("signature"),
        ValueLayout.JAVA_BYTE.withName("has_conflicts"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_cherry_pick_start_end_event_data_t") as StructLayout

    /**
     * Progress statistics for the applied changes.
     */
    const val OFFSET_stats: Long = 0L
    fun stats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_stats, 72L)

    /**
     * Resulting revision hash signature.
     */
    const val OFFSET_signature: Long = 72L
    fun signature(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_signature, 32L)

    /**
     * Flag indicating the cherry-pick produced conflicts.
     */
    const val OFFSET_has_conflicts: Long = 104L
    fun has_conflicts(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts)
    fun has_conflicts(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts, value)
    }
}

/**
 * Event data reported when a file is unresolved during a cherry-pick.
 */
object lore_cherry_pick_unresolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_cherry_pick_unresolve_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a revision is unresolved during a cherry-pick.
 */
object lore_cherry_pick_unresolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_cherry_pick_unresolve_revision_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the revision.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Data for the start of a store compaction pass.
 */
object lore_compaction_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("target_bytes"),
    ).withName("lore_compaction_begin_event_data_t") as StructLayout

    /**
     * Store size in bytes the pass is reducing the store toward.
     */
    const val OFFSET_target_bytes: Long = 0L
    fun target_bytes(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_target_bytes)
    fun target_bytes(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_target_bytes, value)
    }
}

/**
 * Data for the end of a store compaction pass.
 */
object lore_compaction_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("total_compacted_bytes"),
    ).withName("lore_compaction_end_event_data_t") as StructLayout

    /**
     * Total bytes reclaimed across the pass.
     */
    const val OFFSET_total_compacted_bytes: Long = 0L
    fun total_compacted_bytes(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_total_compacted_bytes)
    fun total_compacted_bytes(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_total_compacted_bytes, value)
    }
}

/**
 * Data for one group compacted during a store compaction pass.
 */
object lore_compaction_progress_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("compacted_bytes"),
    ).withName("lore_compaction_progress_event_data_t") as StructLayout

    /**
     * Bytes reclaimed from this group.
     */
    const val OFFSET_compacted_bytes: Long = 0L
    fun compacted_bytes(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_compacted_bytes)
    fun compacted_bytes(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_compacted_bytes, value)
    }
}

/**
 * Data for a completion event, marking the end of an operation.
 */
object lore_complete_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("status"),
        MemoryLayout.paddingLayout(4),
        lore_error_detail_t.LAYOUT.withName("error"),
    ).withName("lore_complete_event_data_t") as StructLayout

    /**
     * The completion status code of the operation.
     */
    const val OFFSET_status: Long = 0L
    fun status(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_status)
    fun status(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_status, value)
    }

    /**
     * The error detail for the operation. The empty default detail on
     * success; the populated detail on failure. `#[serde(default)]` lets an
     * older payload that lacks this field deserialize: the detail then reads
     * back as the empty default with an empty trace list.
     */
    const val OFFSET_error: Long = 8L
    fun error(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_error, 40L)
}

/**
 * Opaque 128-bit context identifier.
 * 
 * Binary-compatible with `Partition`. In the storage layer, `Context` is the
 * association tag within an `Address` (e.g., file identity for dedup reasoning),
 * distinct from the `Partition` which identifies the data partition.
 */
object lore_context_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE).withName("data"),
    ).withName("lore_context_t") as StructLayout

    /**
     * The raw 16 bytes of the identifier.
     */
    const val OFFSET_data: Long = 0L
    fun data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_data, 16L)
}

/**
 * Event data reported at the start of dependency resolution.
 */
object lore_dependency_resolve_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("root_count"),
    ).withName("lore_dependency_resolve_begin_event_data_t") as StructLayout

    /**
     * Number of root files resolution starts from.
     */
    const val OFFSET_root_count: Long = 0L
    fun root_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_root_count)
    fun root_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_root_count, value)
    }
}

/**
 * Event data reported at the end of dependency resolution.
 */
object lore_dependency_resolve_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("resolved_count"),
    ).withName("lore_dependency_resolve_end_event_data_t") as StructLayout

    /**
     * Number of dependency edges that were resolved.
     */
    const val OFFSET_resolved_count: Long = 0L
    fun resolved_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_resolved_count)
    fun resolved_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_resolved_count, value)
    }
}

/**
 * Event data reported for each resolved dependency edge.
 */
object lore_dependency_resolve_item_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("source"),
        lore_string_t.LAYOUT.withName("target"),
        lore_string_array_t.LAYOUT.withName("tags"),
    ).withName("lore_dependency_resolve_item_event_data_t") as StructLayout

    /**
     * Path of the file the dependency comes from.
     */
    const val OFFSET_source: Long = 0L
    fun source(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source, 16L)

    /**
     * Path of the file the dependency points to.
     */
    const val OFFSET_target: Long = 16L
    fun target(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target, 16L)

    /**
     * Tags on this dependency edge.
     */
    const val OFFSET_tags: Long = 32L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)
}

/**
 * Data for an end event, marking the final event of a callback stream.
 */
object lore_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("unused"),
    ).withName("lore_end_event_data_t") as StructLayout

    /**
     * Placeholder field; carries no meaningful value.
     */
    const val OFFSET_unused: Long = 0L
    fun unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_unused)
    fun unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_unused, value)
    }
}

/**
 * The shared error payload carried on a failed operation.
 * 
 * Every consumer reads this on a failure. It holds the error's error code, the
 * error message, and the captured trace as structured data. `Default` yields
 * the empty detail used on success: code `0`, an empty message, and an empty
 * trace array.
 * 
 * The number of trace locations is bounded by the trace capacity in
 * `lore-error-set` ([`MAX_TRACE_DEPTH`]). The trace array is empty when the
 * `track-locations` feature is off or when the error carries no trace.
 * 
 * Memory: the library owns this data. The pointers a consumer reads from this
 * struct (the `message` string and the `trace_locations` array, and the
 * strings inside each location) are valid only for the single callback
 * invocation that delivers the event. A consumer that keeps any of this data
 * must copy it out before the callback returns.
 * 
 * [`MAX_TRACE_DEPTH`]: lore_error_set::MAX_TRACE_DEPTH
 */
object lore_error_detail_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("message"),
        lore_trace_location_array_t.LAYOUT.withName("trace_locations"),
    ).withName("lore_error_detail_t") as StructLayout

    /**
     * The error's error code. `0` on success; `-1` for an internal error.
     */
    const val OFFSET_error_code: Long = 0L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }

    /**
     * The error message, taken from the error's `Display` output. Empty on
     * success.
     */
    const val OFFSET_message: Long = 8L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * The captured trace, one location per trace entry. Empty when
     * `track-locations` is off or the error carries no trace.
     */
    const val OFFSET_trace_locations: Long = 24L
    fun trace_locations(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_trace_locations, 16L)
}

/**
 * Data for an error event.
 */
object lore_error_event_data_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("error_type"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("error_inner"),
    ).withName("lore_error_event_data_t") as StructLayout

    /**
     * The error code, matching one of the error codes.
     */
    const val OFFSET_error_type: Long = 0L
    fun error_type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_type)
    fun error_type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_type, value)
    }

    /**
     * The underlying error message.
     */
    const val OFFSET_error_inner: Long = 8L
    fun error_inner(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_error_inner, 16L)
}

/**
 * A callback function paired with a caller-supplied context value, used to
 * receive events.
 * 
 * The callback does not run inside the lore_* call that configured it. It runs
 * on a thread the library manages, one of a pool of worker threads, not the
 * calling thread.
 * 
 * The event pointer, and everything it points to, is valid only until the
 * callback returns. Copy any data you need to keep, and do not use the event
 * pointer after the callback returns.
 * 
 * Events for a single call arrive one at a time. Two concurrent asynchronous
 * calls that share one configuration can run the callback at the same time, so
 * a shared callback must be safe to call from more than one thread at once. A
 * callback that blocks delays the library's other work and can stall other
 * in-flight calls. Do long or blocking work on your own thread and return from
 * the callback promptly.
 */
object lore_event_callback_config_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("user_context"),
        ValueLayout.ADDRESS.withName("func"),
    ).withName("lore_event_callback_config_t") as StructLayout

    /**
     * Caller-supplied value passed back to the callback on each call.
     */
    const val OFFSET_user_context: Long = 0L
    fun user_context(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_user_context)
    fun user_context(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_user_context, value)
    }

    /**
     * Function invoked for each event, or none to receive no events.
     */
    const val OFFSET_func: Long = 8L
    fun func(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_func)
    fun func(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_func, value)
    }
}

object lore_event_t {
    const val SIZE: Long = 280L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("tag"),
        MemoryLayout.paddingLayout(4),
        MemoryLayout.unionLayout(
        MemoryLayout.structLayout(lore_progress_event_data_t.LAYOUT.withName("progress"), MemoryLayout.paddingLayout(268)).withName("progress"),
        MemoryLayout.structLayout(lore_error_event_data_t.LAYOUT.withName("error"), MemoryLayout.paddingLayout(248)).withName("error"),
        MemoryLayout.structLayout(lore_complete_event_data_t.LAYOUT.withName("complete"), MemoryLayout.paddingLayout(224)).withName("complete"),
        MemoryLayout.structLayout(lore_metadata_event_data_t.LAYOUT.withName("metadata"), MemoryLayout.paddingLayout(200)).withName("metadata"),
        MemoryLayout.structLayout(lore_log_event_data_t.LAYOUT.withName("log"), MemoryLayout.paddingLayout(224)).withName("log"),
        MemoryLayout.structLayout(lore_end_event_data_t.LAYOUT.withName("end"), MemoryLayout.paddingLayout(268)).withName("end"),
        MemoryLayout.structLayout(lore_maintenance_event_data_t.LAYOUT.withName("maintenance"), MemoryLayout.paddingLayout(256)).withName("maintenance"),
        MemoryLayout.structLayout(lore_auth_url_event_data_t.LAYOUT.withName("auth_url"), MemoryLayout.paddingLayout(256)).withName("auth_url"),
        MemoryLayout.structLayout(lore_auth_user_info_event_data_t.LAYOUT.withName("auth_user_info"), MemoryLayout.paddingLayout(240)).withName("auth_user_info"),
        MemoryLayout.structLayout(lore_auth_user_token_event_data_t.LAYOUT.withName("auth_user_token"), MemoryLayout.paddingLayout(192)).withName("auth_user_token"),
        MemoryLayout.structLayout(lore_auth_identity_event_data_t.LAYOUT.withName("auth_identity"), MemoryLayout.paddingLayout(184)).withName("auth_identity"),
        MemoryLayout.structLayout(lore_branch_create_event_data_t.LAYOUT.withName("branch_create"), MemoryLayout.paddingLayout(216)).withName("branch_create"),
        MemoryLayout.structLayout(lore_branch_multiple_instance_event_data_t.LAYOUT.withName("branch_multiple_instance"), MemoryLayout.paddingLayout(224)).withName("branch_multiple_instance"),
        MemoryLayout.structLayout(lore_branch_archive_event_data_t.LAYOUT.withName("branch_archive"), MemoryLayout.paddingLayout(256)).withName("branch_archive"),
        MemoryLayout.structLayout(lore_branch_list_begin_event_data_t.LAYOUT.withName("branch_list_begin"), MemoryLayout.paddingLayout(268)).withName("branch_list_begin"),
        MemoryLayout.structLayout(lore_branch_list_entry_event_data_t.LAYOUT.withName("branch_list_entry"), MemoryLayout.paddingLayout(136)).withName("branch_list_entry"),
        MemoryLayout.structLayout(lore_branch_list_end_event_data_t.LAYOUT.withName("branch_list_end"), MemoryLayout.paddingLayout(256)).withName("branch_list_end"),
        MemoryLayout.structLayout(lore_branch_merge_abort_begin_event_data_t.LAYOUT.withName("branch_merge_abort_begin"), MemoryLayout.paddingLayout(208)).withName("branch_merge_abort_begin"),
        MemoryLayout.structLayout(lore_branch_merge_abort_end_event_data_t.LAYOUT.withName("branch_merge_abort_end"), MemoryLayout.paddingLayout(268)).withName("branch_merge_abort_end"),
        MemoryLayout.structLayout(lore_branch_info_event_data_t.LAYOUT.withName("branch_info"), MemoryLayout.paddingLayout(64)).withName("branch_info"),
        MemoryLayout.structLayout(lore_branch_diff_begin_event_data_t.LAYOUT.withName("branch_diff_begin"), MemoryLayout.paddingLayout(268)).withName("branch_diff_begin"),
        MemoryLayout.structLayout(lore_branch_diff_change_begin_event_data_t.LAYOUT.withName("branch_diff_change_begin"), MemoryLayout.paddingLayout(264)).withName("branch_diff_change_begin"),
        MemoryLayout.structLayout(lore_branch_diff_change_event_data_t.LAYOUT.withName("branch_diff_change"), MemoryLayout.paddingLayout(240)).withName("branch_diff_change"),
        MemoryLayout.structLayout(lore_branch_diff_change_end_event_data_t.LAYOUT.withName("branch_diff_change_end"), MemoryLayout.paddingLayout(268)).withName("branch_diff_change_end"),
        MemoryLayout.structLayout(lore_branch_diff_conflict_begin_event_data_t.LAYOUT.withName("branch_diff_conflict_begin"), MemoryLayout.paddingLayout(264)).withName("branch_diff_conflict_begin"),
        MemoryLayout.structLayout(lore_branch_diff_conflict_event_data_t.LAYOUT.withName("branch_diff_conflict"), MemoryLayout.paddingLayout(208)).withName("branch_diff_conflict"),
        MemoryLayout.structLayout(lore_branch_diff_conflict_end_event_data_t.LAYOUT.withName("branch_diff_conflict_end"), MemoryLayout.paddingLayout(268)).withName("branch_diff_conflict_end"),
        MemoryLayout.structLayout(lore_branch_diff_end_event_data_t.LAYOUT.withName("branch_diff_end"), MemoryLayout.paddingLayout(268)).withName("branch_diff_end"),
        MemoryLayout.structLayout(lore_branch_latest_list_entry_event_data_t.LAYOUT.withName("branch_latest_list_entry"), MemoryLayout.paddingLayout(224)).withName("branch_latest_list_entry"),
        MemoryLayout.structLayout(lore_branch_merge_conflict_file_event_data_t.LAYOUT.withName("branch_merge_conflict_file"), MemoryLayout.paddingLayout(256)).withName("branch_merge_conflict_file"),
        MemoryLayout.structLayout(lore_branch_merge_link_skipped_event_data_t.LAYOUT.withName("branch_merge_link_skipped"), MemoryLayout.paddingLayout(232)).withName("branch_merge_link_skipped"),
        MemoryLayout.structLayout(lore_branch_merge_unresolve_file_event_data_t.LAYOUT.withName("branch_merge_unresolve_file"), MemoryLayout.paddingLayout(256)).withName("branch_merge_unresolve_file"),
        MemoryLayout.structLayout(lore_branch_merge_unresolve_revision_event_data_t.LAYOUT.withName("branch_merge_unresolve_revision"), MemoryLayout.paddingLayout(224)).withName("branch_merge_unresolve_revision"),
        MemoryLayout.structLayout(lore_branch_merge_into_file_begin_event_data_t.LAYOUT.withName("branch_merge_into_file_begin"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_file_begin"),
        MemoryLayout.structLayout(lore_branch_merge_into_file_event_data_t.LAYOUT.withName("branch_merge_into_file"), MemoryLayout.paddingLayout(232)).withName("branch_merge_into_file"),
        MemoryLayout.structLayout(lore_branch_merge_into_file_end_event_data_t.LAYOUT.withName("branch_merge_into_file_end"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_file_end"),
        MemoryLayout.structLayout(lore_branch_merge_into_fragment_begin_event_data_t.LAYOUT.withName("branch_merge_into_fragment_begin"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_fragment_begin"),
        MemoryLayout.structLayout(lore_branch_merge_into_fragment_progress_event_data_t.LAYOUT.withName("branch_merge_into_fragment_progress"), MemoryLayout.paddingLayout(256)).withName("branch_merge_into_fragment_progress"),
        MemoryLayout.structLayout(lore_branch_merge_into_fragment_end_event_data_t.LAYOUT.withName("branch_merge_into_fragment_end"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_fragment_end"),
        MemoryLayout.structLayout(lore_branch_merge_into_revision_event_data_t.LAYOUT.withName("branch_merge_into_revision"), MemoryLayout.paddingLayout(232)).withName("branch_merge_into_revision"),
        MemoryLayout.structLayout(lore_branch_merge_into_sync_begin_event_data_t.LAYOUT.withName("branch_merge_into_sync_begin"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_sync_begin"),
        MemoryLayout.structLayout(lore_branch_merge_into_sync_end_event_data_t.LAYOUT.withName("branch_merge_into_sync_end"), MemoryLayout.paddingLayout(264)).withName("branch_merge_into_sync_end"),
        MemoryLayout.structLayout(lore_branch_merge_resolve_file_event_data_t.LAYOUT.withName("branch_merge_resolve_file"), MemoryLayout.paddingLayout(256)).withName("branch_merge_resolve_file"),
        MemoryLayout.structLayout(lore_branch_merge_resolve_revision_event_data_t.LAYOUT.withName("branch_merge_resolve_revision"), MemoryLayout.paddingLayout(224)).withName("branch_merge_resolve_revision"),
        MemoryLayout.structLayout(lore_branch_merge_start_begin_event_data_t.LAYOUT.withName("branch_merge_start_begin"), MemoryLayout.paddingLayout(216)).withName("branch_merge_start_begin"),
        MemoryLayout.structLayout(lore_branch_merge_start_end_event_data_t.LAYOUT.withName("branch_merge_start_end"), MemoryLayout.paddingLayout(160)).withName("branch_merge_start_end"),
        MemoryLayout.structLayout(lore_cherry_pick_start_begin_event_data_t.LAYOUT.withName("cherry_pick_start_begin"), MemoryLayout.paddingLayout(216)).withName("cherry_pick_start_begin"),
        MemoryLayout.structLayout(lore_cherry_pick_start_end_event_data_t.LAYOUT.withName("cherry_pick_start_end"), MemoryLayout.paddingLayout(160)).withName("cherry_pick_start_end"),
        MemoryLayout.structLayout(lore_cherry_pick_abort_begin_event_data_t.LAYOUT.withName("cherry_pick_abort_begin"), MemoryLayout.paddingLayout(208)).withName("cherry_pick_abort_begin"),
        MemoryLayout.structLayout(lore_cherry_pick_abort_end_event_data_t.LAYOUT.withName("cherry_pick_abort_end"), MemoryLayout.paddingLayout(268)).withName("cherry_pick_abort_end"),
        MemoryLayout.structLayout(lore_cherry_pick_conflict_file_event_data_t.LAYOUT.withName("cherry_pick_conflict_file"), MemoryLayout.paddingLayout(256)).withName("cherry_pick_conflict_file"),
        MemoryLayout.structLayout(lore_cherry_pick_unresolve_file_event_data_t.LAYOUT.withName("cherry_pick_unresolve_file"), MemoryLayout.paddingLayout(256)).withName("cherry_pick_unresolve_file"),
        MemoryLayout.structLayout(lore_cherry_pick_unresolve_revision_event_data_t.LAYOUT.withName("cherry_pick_unresolve_revision"), MemoryLayout.paddingLayout(224)).withName("cherry_pick_unresolve_revision"),
        MemoryLayout.structLayout(lore_cherry_pick_resolve_file_event_data_t.LAYOUT.withName("cherry_pick_resolve_file"), MemoryLayout.paddingLayout(256)).withName("cherry_pick_resolve_file"),
        MemoryLayout.structLayout(lore_cherry_pick_resolve_revision_event_data_t.LAYOUT.withName("cherry_pick_resolve_revision"), MemoryLayout.paddingLayout(224)).withName("cherry_pick_resolve_revision"),
        MemoryLayout.structLayout(lore_revert_start_begin_event_data_t.LAYOUT.withName("revert_start_begin"), MemoryLayout.paddingLayout(216)).withName("revert_start_begin"),
        MemoryLayout.structLayout(lore_revert_start_end_event_data_t.LAYOUT.withName("revert_start_end"), MemoryLayout.paddingLayout(160)).withName("revert_start_end"),
        MemoryLayout.structLayout(lore_revert_abort_begin_event_data_t.LAYOUT.withName("revert_abort_begin"), MemoryLayout.paddingLayout(208)).withName("revert_abort_begin"),
        MemoryLayout.structLayout(lore_revert_abort_end_event_data_t.LAYOUT.withName("revert_abort_end"), MemoryLayout.paddingLayout(268)).withName("revert_abort_end"),
        MemoryLayout.structLayout(lore_revert_resolve_file_event_data_t.LAYOUT.withName("revert_resolve_file"), MemoryLayout.paddingLayout(256)).withName("revert_resolve_file"),
        MemoryLayout.structLayout(lore_revert_resolve_revision_event_data_t.LAYOUT.withName("revert_resolve_revision"), MemoryLayout.paddingLayout(224)).withName("revert_resolve_revision"),
        MemoryLayout.structLayout(lore_revert_conflict_file_event_data_t.LAYOUT.withName("revert_conflict_file"), MemoryLayout.paddingLayout(256)).withName("revert_conflict_file"),
        MemoryLayout.structLayout(lore_revert_unresolve_file_event_data_t.LAYOUT.withName("revert_unresolve_file"), MemoryLayout.paddingLayout(256)).withName("revert_unresolve_file"),
        MemoryLayout.structLayout(lore_revert_unresolve_revision_event_data_t.LAYOUT.withName("revert_unresolve_revision"), MemoryLayout.paddingLayout(224)).withName("revert_unresolve_revision"),
        MemoryLayout.structLayout(lore_branch_protect_event_data_t.LAYOUT.withName("branch_protect"), MemoryLayout.paddingLayout(256)).withName("branch_protect"),
        MemoryLayout.structLayout(lore_branch_push_event_data_t.LAYOUT.withName("branch_push"), MemoryLayout.paddingLayout(120)).withName("branch_push"),
        MemoryLayout.structLayout(lore_branch_push_revision_update_begin_event_data_t.LAYOUT.withName("branch_push_revision_update_begin"), MemoryLayout.paddingLayout(176)).withName("branch_push_revision_update_begin"),
        MemoryLayout.structLayout(lore_branch_push_revision_update_end_event_data_t.LAYOUT.withName("branch_push_revision_update_end"), MemoryLayout.paddingLayout(240)).withName("branch_push_revision_update_end"),
        MemoryLayout.structLayout(lore_branch_push_fragment_begin_event_data_t.LAYOUT.withName("branch_push_fragment_begin"), MemoryLayout.paddingLayout(256)).withName("branch_push_fragment_begin"),
        MemoryLayout.structLayout(lore_branch_push_fragment_progress_event_data_t.LAYOUT.withName("branch_push_fragment_progress"), MemoryLayout.paddingLayout(240)).withName("branch_push_fragment_progress"),
        MemoryLayout.structLayout(lore_branch_push_fragment_end_event_data_t.LAYOUT.withName("branch_push_fragment_end"), MemoryLayout.paddingLayout(256)).withName("branch_push_fragment_end"),
        MemoryLayout.structLayout(lore_branch_push_branch_create_begin_event_data_t.LAYOUT.withName("branch_push_branch_create_begin"), MemoryLayout.paddingLayout(240)).withName("branch_push_branch_create_begin"),
        MemoryLayout.structLayout(lore_branch_push_branch_create_end_event_data_t.LAYOUT.withName("branch_push_branch_create_end"), MemoryLayout.paddingLayout(240)).withName("branch_push_branch_create_end"),
        MemoryLayout.structLayout(lore_branch_push_revision_push_begin_event_data_t.LAYOUT.withName("branch_push_revision_push_begin"), MemoryLayout.paddingLayout(208)).withName("branch_push_revision_push_begin"),
        MemoryLayout.structLayout(lore_branch_push_revision_push_update_event_data_t.LAYOUT.withName("branch_push_revision_push_update"), MemoryLayout.paddingLayout(200)).withName("branch_push_revision_push_update"),
        MemoryLayout.structLayout(lore_branch_push_revision_push_end_event_data_t.LAYOUT.withName("branch_push_revision_push_end"), MemoryLayout.paddingLayout(176)).withName("branch_push_revision_push_end"),
        MemoryLayout.structLayout(lore_branch_reset_event_data_t.LAYOUT.withName("branch_reset"), MemoryLayout.paddingLayout(208)).withName("branch_reset"),
        MemoryLayout.structLayout(lore_branch_switch_begin_event_data_t.LAYOUT.withName("branch_switch_begin"), MemoryLayout.paddingLayout(136)).withName("branch_switch_begin"),
        MemoryLayout.structLayout(lore_branch_switch_end_event_data_t.LAYOUT.withName("branch_switch_end"), MemoryLayout.paddingLayout(136)).withName("branch_switch_end"),
        MemoryLayout.structLayout(lore_branch_unprotect_event_data_t.LAYOUT.withName("branch_unprotect"), MemoryLayout.paddingLayout(256)).withName("branch_unprotect"),
        MemoryLayout.structLayout(lore_file_info_event_data_t.LAYOUT.withName("file_info"), MemoryLayout.paddingLayout(144)).withName("file_info"),
        MemoryLayout.structLayout(lore_file_diff_event_data_t.LAYOUT.withName("file_diff"), MemoryLayout.paddingLayout(232)).withName("file_diff"),
        MemoryLayout.structLayout(lore_file_hash_event_data_t.LAYOUT.withName("file_hash"), MemoryLayout.paddingLayout(216)).withName("file_hash"),
        MemoryLayout.structLayout(lore_file_history_event_data_t.LAYOUT.withName("file_history"), MemoryLayout.paddingLayout(72)).withName("file_history"),
        MemoryLayout.structLayout(lore_file_write_event_data_t.LAYOUT.withName("file_write"), MemoryLayout.paddingLayout(256)).withName("file_write"),
        MemoryLayout.structLayout(lore_file_obliterate_event_data_t.LAYOUT.withName("file_obliterate"), MemoryLayout.paddingLayout(208)).withName("file_obliterate"),
        MemoryLayout.structLayout(lore_file_dump_event_data_t.LAYOUT.withName("file_dump"), MemoryLayout.paddingLayout(200)).withName("file_dump"),
        MemoryLayout.structLayout(lore_file_dependency_add_begin_event_data_t.LAYOUT.withName("file_dependency_add_begin"), MemoryLayout.paddingLayout(256)).withName("file_dependency_add_begin"),
        MemoryLayout.structLayout(lore_file_dependency_add_entry_event_data_t.LAYOUT.withName("file_dependency_add_entry"), MemoryLayout.paddingLayout(224)).withName("file_dependency_add_entry"),
        MemoryLayout.structLayout(lore_file_dependency_add_end_event_data_t.LAYOUT.withName("file_dependency_add_end"), MemoryLayout.paddingLayout(264)).withName("file_dependency_add_end"),
        MemoryLayout.structLayout(lore_file_dependency_remove_begin_event_data_t.LAYOUT.withName("file_dependency_remove_begin"), MemoryLayout.paddingLayout(256)).withName("file_dependency_remove_begin"),
        MemoryLayout.structLayout(lore_file_dependency_remove_entry_event_data_t.LAYOUT.withName("file_dependency_remove_entry"), MemoryLayout.paddingLayout(224)).withName("file_dependency_remove_entry"),
        MemoryLayout.structLayout(lore_file_dependency_remove_end_event_data_t.LAYOUT.withName("file_dependency_remove_end"), MemoryLayout.paddingLayout(264)).withName("file_dependency_remove_end"),
        MemoryLayout.structLayout(lore_file_dependency_list_begin_event_data_t.LAYOUT.withName("file_dependency_list_begin"), MemoryLayout.paddingLayout(264)).withName("file_dependency_list_begin"),
        MemoryLayout.structLayout(lore_file_dependency_list_file_event_data_t.LAYOUT.withName("file_dependency_list_file"), MemoryLayout.paddingLayout(248)).withName("file_dependency_list_file"),
        MemoryLayout.structLayout(lore_file_dependency_list_entry_event_data_t.LAYOUT.withName("file_dependency_list_entry"), MemoryLayout.paddingLayout(232)).withName("file_dependency_list_entry"),
        MemoryLayout.structLayout(lore_file_dependency_list_file_end_event_data_t.LAYOUT.withName("file_dependency_list_file_end"), MemoryLayout.paddingLayout(256)).withName("file_dependency_list_file_end"),
        MemoryLayout.structLayout(lore_file_dependency_list_end_event_data_t.LAYOUT.withName("file_dependency_list_end"), MemoryLayout.paddingLayout(264)).withName("file_dependency_list_end"),
        MemoryLayout.structLayout(lore_file_reset_begin_event_data_t.LAYOUT.withName("file_reset_begin"), MemoryLayout.paddingLayout(264)).withName("file_reset_begin"),
        MemoryLayout.structLayout(lore_file_reset_progress_event_data_t.LAYOUT.withName("file_reset_progress"), MemoryLayout.paddingLayout(240)).withName("file_reset_progress"),
        MemoryLayout.structLayout(lore_file_reset_end_event_data_t.LAYOUT.withName("file_reset_end"), MemoryLayout.paddingLayout(240)).withName("file_reset_end"),
        MemoryLayout.structLayout(lore_file_reset_file_event_data_t.LAYOUT.withName("file_reset_file"), MemoryLayout.paddingLayout(232)).withName("file_reset_file"),
        MemoryLayout.structLayout(lore_filter_exclude_event_data_t.LAYOUT.withName("filter_exclude"), MemoryLayout.paddingLayout(248)).withName("filter_exclude"),
        MemoryLayout.structLayout(lore_file_stage_begin_event_data_t.LAYOUT.withName("file_stage_begin"), MemoryLayout.paddingLayout(264)).withName("file_stage_begin"),
        MemoryLayout.structLayout(lore_file_stage_progress_event_data_t.LAYOUT.withName("file_stage_progress"), MemoryLayout.paddingLayout(200)).withName("file_stage_progress"),
        MemoryLayout.structLayout(lore_file_stage_end_event_data_t.LAYOUT.withName("file_stage_end"), MemoryLayout.paddingLayout(200)).withName("file_stage_end"),
        MemoryLayout.structLayout(lore_file_stage_revision_event_data_t.LAYOUT.withName("file_stage_revision"), MemoryLayout.paddingLayout(224)).withName("file_stage_revision"),
        MemoryLayout.structLayout(lore_file_stage_file_event_data_t.LAYOUT.withName("file_stage_file"), MemoryLayout.paddingLayout(232)).withName("file_stage_file"),
        MemoryLayout.structLayout(lore_file_unstage_begin_event_data_t.LAYOUT.withName("file_unstage_begin"), MemoryLayout.paddingLayout(264)).withName("file_unstage_begin"),
        MemoryLayout.structLayout(lore_file_unstage_progress_event_data_t.LAYOUT.withName("file_unstage_progress"), MemoryLayout.paddingLayout(232)).withName("file_unstage_progress"),
        MemoryLayout.structLayout(lore_file_unstage_end_event_data_t.LAYOUT.withName("file_unstage_end"), MemoryLayout.paddingLayout(232)).withName("file_unstage_end"),
        MemoryLayout.structLayout(lore_file_unstage_revision_event_data_t.LAYOUT.withName("file_unstage_revision"), MemoryLayout.paddingLayout(224)).withName("file_unstage_revision"),
        MemoryLayout.structLayout(lore_file_unstage_file_event_data_t.LAYOUT.withName("file_unstage_file"), MemoryLayout.paddingLayout(248)).withName("file_unstage_file"),
        MemoryLayout.structLayout(lore_fragment_write_event_data_t.LAYOUT.withName("fragment_write"), MemoryLayout.paddingLayout(248)).withName("fragment_write"),
        MemoryLayout.structLayout(lore_layer_add_event_data_t.LAYOUT.withName("layer_add"), MemoryLayout.paddingLayout(176)).withName("layer_add"),
        MemoryLayout.structLayout(lore_layer_entry_event_data_t.LAYOUT.withName("layer_entry"), MemoryLayout.paddingLayout(176)).withName("layer_entry"),
        MemoryLayout.structLayout(lore_layer_remove_event_data_t.LAYOUT.withName("layer_remove"), MemoryLayout.paddingLayout(160)).withName("layer_remove"),
        MemoryLayout.structLayout(lore_layer_staged_entry_event_data_t.LAYOUT.withName("layer_staged_entry"), MemoryLayout.paddingLayout(232)).withName("layer_staged_entry"),
        MemoryLayout.structLayout(lore_link_change_event_data_t.LAYOUT.withName("link_change"), MemoryLayout.paddingLayout(184)).withName("link_change"),
        MemoryLayout.structLayout(lore_link_entry_event_data_t.LAYOUT.withName("link_entry"), MemoryLayout.paddingLayout(136)).withName("link_entry"),
        MemoryLayout.structLayout(lore_lock_file_acquire_begin_event_data_t.LAYOUT.withName("lock_file_acquire_begin"), MemoryLayout.paddingLayout(256)).withName("lock_file_acquire_begin"),
        MemoryLayout.structLayout(lore_lock_file_acquire_event_data_t.LAYOUT.withName("lock_file_acquire"), MemoryLayout.paddingLayout(256)).withName("lock_file_acquire"),
        MemoryLayout.structLayout(lore_lock_file_status_begin_event_data_t.LAYOUT.withName("lock_file_status_begin"), MemoryLayout.paddingLayout(264)).withName("lock_file_status_begin"),
        MemoryLayout.structLayout(lore_lock_file_status_event_data_t.LAYOUT.withName("lock_file_status"), MemoryLayout.paddingLayout(232)).withName("lock_file_status"),
        MemoryLayout.structLayout(lore_lock_file_query_begin_event_data_t.LAYOUT.withName("lock_file_query_begin"), MemoryLayout.paddingLayout(264)).withName("lock_file_query_begin"),
        MemoryLayout.structLayout(lore_lock_file_query_event_data_t.LAYOUT.withName("lock_file_query"), MemoryLayout.paddingLayout(216)).withName("lock_file_query"),
        MemoryLayout.structLayout(lore_lock_file_release_begin_event_data_t.LAYOUT.withName("lock_file_release_begin"), MemoryLayout.paddingLayout(256)).withName("lock_file_release_begin"),
        MemoryLayout.structLayout(lore_lock_file_release_event_data_t.LAYOUT.withName("lock_file_release"), MemoryLayout.paddingLayout(256)).withName("lock_file_release"),
        MemoryLayout.structLayout(lore_metadata_clear_file_event_data_t.LAYOUT.withName("metadata_clear_file"), MemoryLayout.paddingLayout(256)).withName("metadata_clear_file"),
        MemoryLayout.structLayout(lore_metadata_clear_revision_event_data_t.LAYOUT.withName("metadata_clear_revision"), MemoryLayout.paddingLayout(240)).withName("metadata_clear_revision"),
        MemoryLayout.structLayout(lore_path_ignore_event_data_t.LAYOUT.withName("path_ignore"), MemoryLayout.paddingLayout(256)).withName("path_ignore"),
        MemoryLayout.structLayout(lore_repository_create_event_data_t.LAYOUT.withName("repository_create"), MemoryLayout.paddingLayout(224)).withName("repository_create"),
        MemoryLayout.structLayout(lore_repository_clone_begin_event_data_t.LAYOUT.withName("repository_clone_begin"), MemoryLayout.paddingLayout(192)).withName("repository_clone_begin"),
        MemoryLayout.structLayout(lore_repository_clone_progress_event_data_t.LAYOUT.withName("repository_clone_progress"), MemoryLayout.paddingLayout(200)).withName("repository_clone_progress"),
        MemoryLayout.structLayout(lore_repository_clone_end_event_data_t.LAYOUT.withName("repository_clone_end"), MemoryLayout.paddingLayout(152)).withName("repository_clone_end"),
        MemoryLayout.structLayout(lore_dependency_resolve_begin_event_data_t.LAYOUT.withName("dependency_resolve_begin"), MemoryLayout.paddingLayout(264)).withName("dependency_resolve_begin"),
        MemoryLayout.structLayout(lore_dependency_resolve_item_event_data_t.LAYOUT.withName("dependency_resolve_item"), MemoryLayout.paddingLayout(224)).withName("dependency_resolve_item"),
        MemoryLayout.structLayout(lore_dependency_resolve_end_event_data_t.LAYOUT.withName("dependency_resolve_end"), MemoryLayout.paddingLayout(264)).withName("dependency_resolve_end"),
        MemoryLayout.structLayout(lore_repository_data_event_data_t.LAYOUT.withName("repository_data"), MemoryLayout.paddingLayout(152)).withName("repository_data"),
        MemoryLayout.structLayout(lore_repository_config_get_event_data_t.LAYOUT.withName("repository_config_get"), MemoryLayout.paddingLayout(240)).withName("repository_config_get"),
        MemoryLayout.structLayout(lore_repository_dump_begin_event_data_t.LAYOUT.withName("repository_dump_begin"), MemoryLayout.paddingLayout(224)).withName("repository_dump_begin"),
        MemoryLayout.structLayout(lore_repository_dump_end_event_data_t.LAYOUT.withName("repository_dump_end"), MemoryLayout.paddingLayout(268)).withName("repository_dump_end"),
        MemoryLayout.structLayout(lore_repository_list_entry_event_data_t.LAYOUT.withName("repository_list_entry"), MemoryLayout.paddingLayout(240)).withName("repository_list_entry"),
        MemoryLayout.structLayout(lore_repository_instance_event_data_t.LAYOUT.withName("repository_instance"), MemoryLayout.paddingLayout(168)).withName("repository_instance"),
        MemoryLayout.structLayout(lore_repository_verify_state_begin_event_data_t.LAYOUT.withName("repository_verify_state_begin"), MemoryLayout.paddingLayout(268)).withName("repository_verify_state_begin"),
        MemoryLayout.structLayout(lore_repository_verify_state_end_event_data_t.LAYOUT.withName("repository_verify_state_end"), MemoryLayout.paddingLayout(240)).withName("repository_verify_state_end"),
        MemoryLayout.structLayout(lore_repository_verify_fragment_event_data_t.LAYOUT.withName("repository_verify_fragment"), MemoryLayout.paddingLayout(168)).withName("repository_verify_fragment"),
        MemoryLayout.structLayout(lore_repository_verify_fragment_match_event_data_t.LAYOUT.withName("repository_verify_fragment_match"), MemoryLayout.paddingLayout(168)).withName("repository_verify_fragment_match"),
        MemoryLayout.structLayout(lore_repository_verify_fragment_remote_event_data_t.LAYOUT.withName("repository_verify_fragment_remote"), MemoryLayout.paddingLayout(200)).withName("repository_verify_fragment_remote"),
        MemoryLayout.structLayout(lore_repository_state_dump_event_data_t.LAYOUT.withName("repository_state_dump"), MemoryLayout.paddingLayout(192)).withName("repository_state_dump"),
        MemoryLayout.structLayout(lore_repository_state_dump_node_event_data_t.LAYOUT.withName("repository_state_dump_node"), MemoryLayout.paddingLayout(208)).withName("repository_state_dump_node"),
        lore_repository_status_revision_event_data_t.LAYOUT.withName("repository_status_revision"),
        MemoryLayout.structLayout(lore_repository_status_file_event_data_t.LAYOUT.withName("repository_status_file"), MemoryLayout.paddingLayout(216)).withName("repository_status_file"),
        MemoryLayout.structLayout(lore_repository_status_count_event_data_t.LAYOUT.withName("repository_status_count"), MemoryLayout.paddingLayout(256)).withName("repository_status_count"),
        MemoryLayout.structLayout(lore_repository_status_summary_event_data_t.LAYOUT.withName("repository_status_summary"), MemoryLayout.paddingLayout(232)).withName("repository_status_summary"),
        MemoryLayout.structLayout(lore_repository_store_immutable_query_event_data_t.LAYOUT.withName("repository_store_immutable_query"), MemoryLayout.paddingLayout(192)).withName("repository_store_immutable_query"),
        MemoryLayout.structLayout(lore_revision_commit_begin_event_data_t.LAYOUT.withName("revision_commit_begin"), MemoryLayout.paddingLayout(268)).withName("revision_commit_begin"),
        MemoryLayout.structLayout(lore_revision_commit_progress_event_data_t.LAYOUT.withName("revision_commit_progress"), MemoryLayout.paddingLayout(192)).withName("revision_commit_progress"),
        MemoryLayout.structLayout(lore_revision_commit_end_event_data_t.LAYOUT.withName("revision_commit_end"), MemoryLayout.paddingLayout(192)).withName("revision_commit_end"),
        MemoryLayout.structLayout(lore_revision_commit_revision_event_data_t.LAYOUT.withName("revision_commit_revision"), MemoryLayout.paddingLayout(136)).withName("revision_commit_revision"),
        MemoryLayout.structLayout(lore_revision_info_event_data_t.LAYOUT.withName("revision_info"), MemoryLayout.paddingLayout(152)).withName("revision_info"),
        MemoryLayout.structLayout(lore_revision_info_delta_event_data_t.LAYOUT.withName("revision_info_delta"), MemoryLayout.paddingLayout(240)).withName("revision_info_delta"),
        MemoryLayout.structLayout(lore_revision_diff_file_event_data_t.LAYOUT.withName("revision_diff_file"), MemoryLayout.paddingLayout(152)).withName("revision_diff_file"),
        MemoryLayout.structLayout(lore_revision_find_event_data_t.LAYOUT.withName("revision_find"), MemoryLayout.paddingLayout(240)).withName("revision_find"),
        MemoryLayout.structLayout(lore_revision_history_event_data_t.LAYOUT.withName("revision_history"), MemoryLayout.paddingLayout(240)).withName("revision_history"),
        MemoryLayout.structLayout(lore_revision_history_entry_event_data_t.LAYOUT.withName("revision_history_entry"), MemoryLayout.paddingLayout(168)).withName("revision_history_entry"),
        MemoryLayout.structLayout(lore_revision_restore_file_begin_event_data_t.LAYOUT.withName("revision_restore_file_begin"), MemoryLayout.paddingLayout(264)).withName("revision_restore_file_begin"),
        MemoryLayout.structLayout(lore_revision_restore_file_event_data_t.LAYOUT.withName("revision_restore_file"), MemoryLayout.paddingLayout(232)).withName("revision_restore_file"),
        MemoryLayout.structLayout(lore_revision_restore_file_end_event_data_t.LAYOUT.withName("revision_restore_file_end"), MemoryLayout.paddingLayout(264)).withName("revision_restore_file_end"),
        MemoryLayout.structLayout(lore_revision_restore_fragment_begin_event_data_t.LAYOUT.withName("revision_restore_fragment_begin"), MemoryLayout.paddingLayout(264)).withName("revision_restore_fragment_begin"),
        MemoryLayout.structLayout(lore_revision_restore_fragment_progress_event_data_t.LAYOUT.withName("revision_restore_fragment_progress"), MemoryLayout.paddingLayout(256)).withName("revision_restore_fragment_progress"),
        MemoryLayout.structLayout(lore_revision_restore_fragment_end_event_data_t.LAYOUT.withName("revision_restore_fragment_end"), MemoryLayout.paddingLayout(264)).withName("revision_restore_fragment_end"),
        MemoryLayout.structLayout(lore_revision_restore_revision_event_data_t.LAYOUT.withName("revision_restore_revision"), MemoryLayout.paddingLayout(232)).withName("revision_restore_revision"),
        MemoryLayout.structLayout(lore_revision_restore_sync_begin_event_data_t.LAYOUT.withName("revision_restore_sync_begin"), MemoryLayout.paddingLayout(264)).withName("revision_restore_sync_begin"),
        MemoryLayout.structLayout(lore_revision_restore_sync_end_event_data_t.LAYOUT.withName("revision_restore_sync_end"), MemoryLayout.paddingLayout(264)).withName("revision_restore_sync_end"),
        MemoryLayout.structLayout(lore_revision_resolve_event_data_t.LAYOUT.withName("revision_resolve"), MemoryLayout.paddingLayout(208)).withName("revision_resolve"),
        MemoryLayout.structLayout(lore_revision_sync_target_event_data_t.LAYOUT.withName("revision_sync_target"), MemoryLayout.paddingLayout(120)).withName("revision_sync_target"),
        MemoryLayout.structLayout(lore_revision_sync_file_event_data_t.LAYOUT.withName("revision_sync_file"), MemoryLayout.paddingLayout(240)).withName("revision_sync_file"),
        MemoryLayout.structLayout(lore_revision_sync_progress_event_data_t.LAYOUT.withName("revision_sync_progress"), MemoryLayout.paddingLayout(200)).withName("revision_sync_progress"),
        MemoryLayout.structLayout(lore_revision_sync_revision_event_data_t.LAYOUT.withName("revision_sync_revision"), MemoryLayout.paddingLayout(208)).withName("revision_sync_revision"),
        MemoryLayout.structLayout(lore_revision_bisect_event_data_t.LAYOUT.withName("revision_bisect"), MemoryLayout.paddingLayout(240)).withName("revision_bisect"),
        MemoryLayout.structLayout(lore_notification_branch_created_event_data_t.LAYOUT.withName("notification_branch_created"), MemoryLayout.paddingLayout(256)).withName("notification_branch_created"),
        MemoryLayout.structLayout(lore_notification_branch_deleted_event_data_t.LAYOUT.withName("notification_branch_deleted"), MemoryLayout.paddingLayout(256)).withName("notification_branch_deleted"),
        MemoryLayout.structLayout(lore_notification_branch_pushed_event_data_t.LAYOUT.withName("notification_branch_pushed"), MemoryLayout.paddingLayout(200)).withName("notification_branch_pushed"),
        MemoryLayout.structLayout(lore_notification_resource_locked_event_data_t.LAYOUT.withName("notification_resource_locked"), MemoryLayout.paddingLayout(224)).withName("notification_resource_locked"),
        MemoryLayout.structLayout(lore_notification_resource_unlocked_event_data_t.LAYOUT.withName("notification_resource_unlocked"), MemoryLayout.paddingLayout(224)).withName("notification_resource_unlocked"),
        MemoryLayout.structLayout(lore_notification_subscribed_event_data_t.LAYOUT.withName("notification_subscribed"), MemoryLayout.paddingLayout(256)).withName("notification_subscribed"),
        MemoryLayout.structLayout(lore_notification_unsubscribed_event_data_t.LAYOUT.withName("notification_unsubscribed"), MemoryLayout.paddingLayout(256)).withName("notification_unsubscribed"),
        MemoryLayout.structLayout(lore_shared_store_create_event_data_t.LAYOUT.withName("shared_store_create"), MemoryLayout.paddingLayout(256)).withName("shared_store_create"),
        MemoryLayout.structLayout(lore_shared_store_info_event_data_t.LAYOUT.withName("shared_store_info"), MemoryLayout.paddingLayout(216)).withName("shared_store_info"),
        MemoryLayout.structLayout(lore_link_staged_entry_event_data_t.LAYOUT.withName("link_staged_entry"), MemoryLayout.paddingLayout(232)).withName("link_staged_entry"),
        MemoryLayout.structLayout(lore_storage_opened_event_data_t.LAYOUT.withName("storage_opened"), MemoryLayout.paddingLayout(264)).withName("storage_opened"),
        MemoryLayout.structLayout(lore_storage_put_item_complete_event_data_t.LAYOUT.withName("storage_put_item_complete"), MemoryLayout.paddingLayout(208)).withName("storage_put_item_complete"),
        MemoryLayout.structLayout(lore_storage_get_header_event_data_t.LAYOUT.withName("storage_get_header"), MemoryLayout.paddingLayout(208)).withName("storage_get_header"),
        MemoryLayout.structLayout(lore_storage_get_data_event_data_t.LAYOUT.withName("storage_get_data"), MemoryLayout.paddingLayout(192)).withName("storage_get_data"),
        MemoryLayout.structLayout(lore_storage_get_item_complete_event_data_t.LAYOUT.withName("storage_get_item_complete"), MemoryLayout.paddingLayout(208)).withName("storage_get_item_complete"),
        MemoryLayout.structLayout(lore_storage_get_metadata_item_complete_event_data_t.LAYOUT.withName("storage_get_metadata_item_complete"), MemoryLayout.paddingLayout(192)).withName("storage_get_metadata_item_complete"),
        MemoryLayout.structLayout(lore_storage_copy_item_complete_event_data_t.LAYOUT.withName("storage_copy_item_complete"), MemoryLayout.paddingLayout(160)).withName("storage_copy_item_complete"),
        MemoryLayout.structLayout(lore_storage_obliterate_item_complete_event_data_t.LAYOUT.withName("storage_obliterate_item_complete"), MemoryLayout.paddingLayout(208)).withName("storage_obliterate_item_complete"),
        MemoryLayout.structLayout(lore_storage_upload_item_complete_event_data_t.LAYOUT.withName("storage_upload_item_complete"), MemoryLayout.paddingLayout(208)).withName("storage_upload_item_complete"),
        MemoryLayout.structLayout(lore_revision_tree_loaded_event_data_t.LAYOUT.withName("revision_tree_loaded"), MemoryLayout.paddingLayout(264)).withName("revision_tree_loaded"),
        MemoryLayout.structLayout(lore_revision_tree_resolve_path_complete_event_data_t.LAYOUT.withName("revision_tree_resolve_path_complete"), MemoryLayout.paddingLayout(208)).withName("revision_tree_resolve_path_complete"),
        MemoryLayout.structLayout(lore_revision_tree_child_event_data_t.LAYOUT.withName("revision_tree_child"), MemoryLayout.paddingLayout(160)).withName("revision_tree_child"),
        MemoryLayout.structLayout(lore_revision_tree_node_info_event_data_t.LAYOUT.withName("revision_tree_node_info"), MemoryLayout.paddingLayout(96)).withName("revision_tree_node_info"),
        MemoryLayout.structLayout(lore_revision_tree_node_path_event_data_t.LAYOUT.withName("revision_tree_node_path"), MemoryLayout.paddingLayout(192)).withName("revision_tree_node_path"),
        MemoryLayout.structLayout(lore_revision_tree_add_complete_event_data_t.LAYOUT.withName("revision_tree_add_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_add_complete"),
        MemoryLayout.structLayout(lore_revision_tree_delete_complete_event_data_t.LAYOUT.withName("revision_tree_delete_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_delete_complete"),
        MemoryLayout.structLayout(lore_revision_tree_modify_complete_event_data_t.LAYOUT.withName("revision_tree_modify_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_modify_complete"),
        MemoryLayout.structLayout(lore_revision_tree_move_complete_event_data_t.LAYOUT.withName("revision_tree_move_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_move_complete"),
        MemoryLayout.structLayout(lore_revision_tree_metadata_set_complete_event_data_t.LAYOUT.withName("revision_tree_metadata_set_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_metadata_set_complete"),
        MemoryLayout.structLayout(lore_revision_tree_metadata_get_complete_event_data_t.LAYOUT.withName("revision_tree_metadata_get_complete"), MemoryLayout.paddingLayout(184)).withName("revision_tree_metadata_get_complete"),
        MemoryLayout.structLayout(lore_revision_tree_commit_complete_event_data_t.LAYOUT.withName("revision_tree_commit_complete"), MemoryLayout.paddingLayout(192)).withName("revision_tree_commit_complete"),
        MemoryLayout.structLayout(lore_revision_tree_close_complete_event_data_t.LAYOUT.withName("revision_tree_close_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_close_complete"),
        MemoryLayout.structLayout(lore_revision_tree_list_children_begin_event_data_t.LAYOUT.withName("revision_tree_list_children_begin"), MemoryLayout.paddingLayout(208)).withName("revision_tree_list_children_begin"),
        MemoryLayout.structLayout(lore_revision_tree_info_event_data_t.LAYOUT.withName("revision_tree_info"), MemoryLayout.paddingLayout(120)).withName("revision_tree_info"),
        MemoryLayout.structLayout(lore_storage_mutable_load_item_complete_event_data_t.LAYOUT.withName("storage_mutable_load_item_complete"), MemoryLayout.paddingLayout(224)).withName("storage_mutable_load_item_complete"),
        MemoryLayout.structLayout(lore_storage_mutable_store_item_complete_event_data_t.LAYOUT.withName("storage_mutable_store_item_complete"), MemoryLayout.paddingLayout(256)).withName("storage_mutable_store_item_complete"),
        MemoryLayout.structLayout(lore_storage_mutable_compare_and_swap_item_complete_event_data_t.LAYOUT.withName("storage_mutable_compare_and_swap_item_complete"), MemoryLayout.paddingLayout(224)).withName("storage_mutable_compare_and_swap_item_complete"),
        MemoryLayout.structLayout(lore_storage_mutable_list_entry_event_data_t.LAYOUT.withName("storage_mutable_list_entry"), MemoryLayout.paddingLayout(200)).withName("storage_mutable_list_entry"),
        MemoryLayout.structLayout(lore_storage_mutable_list_item_complete_event_data_t.LAYOUT.withName("storage_mutable_list_item_complete"), MemoryLayout.paddingLayout(256)).withName("storage_mutable_list_item_complete"),
        MemoryLayout.structLayout(lore_eviction_begin_event_data_t.LAYOUT.withName("eviction_begin"), MemoryLayout.paddingLayout(264)).withName("eviction_begin"),
        MemoryLayout.structLayout(lore_eviction_progress_event_data_t.LAYOUT.withName("eviction_progress"), MemoryLayout.paddingLayout(264)).withName("eviction_progress"),
        MemoryLayout.structLayout(lore_eviction_end_event_data_t.LAYOUT.withName("eviction_end"), MemoryLayout.paddingLayout(264)).withName("eviction_end"),
        MemoryLayout.structLayout(lore_compaction_begin_event_data_t.LAYOUT.withName("compaction_begin"), MemoryLayout.paddingLayout(264)).withName("compaction_begin"),
        MemoryLayout.structLayout(lore_compaction_progress_event_data_t.LAYOUT.withName("compaction_progress"), MemoryLayout.paddingLayout(264)).withName("compaction_progress"),
        MemoryLayout.structLayout(lore_compaction_end_event_data_t.LAYOUT.withName("compaction_end"), MemoryLayout.paddingLayout(264)).withName("compaction_end"),
        MemoryLayout.structLayout(lore_revision_tree_batch_complete_event_data_t.LAYOUT.withName("revision_tree_batch_complete"), MemoryLayout.paddingLayout(256)).withName("revision_tree_batch_complete"),
    ).withName("union"),
    ).withName("lore_event_t") as StructLayout

    const val OFFSET_tag: Long = 0L
    fun tag(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_tag)
    fun tag(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_tag, value)
    }

    const val OFFSET_union: Long = 8L
    fun union(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_union, 272L)
}

/**
 * Data for the start of a store eviction pass.
 */
object lore_eviction_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("target_fragments"),
    ).withName("lore_eviction_begin_event_data_t") as StructLayout

    /**
     * Fragment capacity the pass is reducing the store toward.
     */
    const val OFFSET_target_fragments: Long = 0L
    fun target_fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_target_fragments)
    fun target_fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_target_fragments, value)
    }
}

/**
 * Data for the end of a store eviction pass.
 */
object lore_eviction_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("total_evicted"),
    ).withName("lore_eviction_end_event_data_t") as StructLayout

    /**
     * Total fragments evicted across the pass.
     */
    const val OFFSET_total_evicted: Long = 0L
    fun total_evicted(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_total_evicted)
    fun total_evicted(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_total_evicted, value)
    }
}

/**
 * Data for one bucket evicted during a store eviction pass.
 */
object lore_eviction_progress_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("evicted"),
    ).withName("lore_eviction_progress_event_data_t") as StructLayout

    /**
     * Fragments evicted from this bucket.
     */
    const val OFFSET_evicted: Long = 0L
    fun evicted(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_evicted)
    fun evicted(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_evicted, value)
    }
}

/**
 * Arguments for adding file dependencies, expanded from flat parallel arrays.
 */
object lore_file_dependency_add_args_t {
    const val SIZE: Long = 88L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_array_t.LAYOUT.withName("dependencies"),
        lore_string_array_t.LAYOUT.withName("tags"),
        lore_uint32_array_t.LAYOUT.withName("dep_counts"),
        lore_uint32_array_t.LAYOUT.withName("tag_counts"),
        ValueLayout.JAVA_BYTE.withName("force"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_file_dependency_add_args_t") as StructLayout

    /**
     * Source file paths that will have dependencies added.
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Dependency target file paths (flat array).
     */
    const val OFFSET_dependencies: Long = 16L
    fun dependencies(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependencies, 16L)

    /**
     * Tags to apply to the added dependencies (flat array).
     */
    const val OFFSET_tags: Long = 32L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)

    /**
     * Number of dependencies per source file path.
     */
    const val OFFSET_dep_counts: Long = 48L
    fun dep_counts(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dep_counts, 16L)

    /**
     * Number of tags per dependency entry.
     */
    const val OFFSET_tag_counts: Long = 64L
    fun tag_counts(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tag_counts, 16L)

    /**
     * Skip cycle detection.
     */
    const val OFFSET_force: Long = 80L
    fun force(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_force)
    fun force(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_force, value)
    }
}

/**
 * Event data reported at the start of adding file dependencies.
 */
object lore_file_dependency_add_begin_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("path_count"),
        ValueLayout.JAVA_LONG.withName("dependency_count"),
    ).withName("lore_file_dependency_add_begin_event_data_t") as StructLayout

    /**
     * Number of source files being processed.
     */
    const val OFFSET_path_count: Long = 0L
    fun path_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_path_count)
    fun path_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_path_count, value)
    }

    /**
     * Number of dependency edges being added.
     */
    const val OFFSET_dependency_count: Long = 8L
    fun dependency_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_dependency_count)
    fun dependency_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_dependency_count, value)
    }
}

/**
 * Event data reported at the end of adding file dependencies.
 */
object lore_file_dependency_add_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("added_count"),
    ).withName("lore_file_dependency_add_end_event_data_t") as StructLayout

    /**
     * Number of dependency edges that were added.
     */
    const val OFFSET_added_count: Long = 0L
    fun added_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_added_count)
    fun added_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_added_count, value)
    }
}

/**
 * Event data reported for each dependency edge being added.
 */
object lore_file_dependency_add_entry_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("dependency"),
        lore_string_array_t.LAYOUT.withName("tags"),
    ).withName("lore_file_dependency_add_entry_event_data_t") as StructLayout

    /**
     * Path of the source file that gains the dependency.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Path of the file being depended on.
     */
    const val OFFSET_dependency: Long = 16L
    fun dependency(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependency, 16L)

    /**
     * Tags applied to this dependency edge.
     */
    const val OFFSET_tags: Long = 32L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)
}

/**
 * Arguments for listing file dependencies (or dependents) at a given revision.
 */
object lore_file_dependency_list_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("recursive"),
        ValueLayout.JAVA_BYTE.withName("reverse"),
        MemoryLayout.paddingLayout(6),
        lore_string_array_t.LAYOUT.withName("tags"),
        ValueLayout.JAVA_INT.withName("depth_limit"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_dependency_list_args_t") as StructLayout

    /**
     * Files to query.
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Revision to query at.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Follow transitive dependencies recursively.
     */
    const val OFFSET_recursive: Long = 32L
    fun recursive(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_recursive)
    fun recursive(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_recursive, value)
    }

    /**
     * Return dependents instead of dependencies.
     */
    const val OFFSET_reverse: Long = 33L
    fun reverse(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reverse)
    fun reverse(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reverse, value)
    }

    /**
     * Filter results by tags.
     */
    const val OFFSET_tags: Long = 40L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)

    /**
     * Maximum recursion depth (0 = unlimited).
     */
    const val OFFSET_depth_limit: Long = 56L
    fun depth_limit(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_depth_limit)
    fun depth_limit(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_depth_limit, value)
    }
}

/**
 * Event data reported at the start of listing file dependencies.
 */
object lore_file_dependency_list_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("file_count"),
    ).withName("lore_file_dependency_list_begin_event_data_t") as StructLayout

    /**
     * Number of files being listed.
     */
    const val OFFSET_file_count: Long = 0L
    fun file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_count)
    fun file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_count, value)
    }
}

/**
 * Event data reported at the end of listing file dependencies.
 */
object lore_file_dependency_list_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("total_entry_count"),
    ).withName("lore_file_dependency_list_end_event_data_t") as StructLayout

    /**
     * Total number of dependency entries that were listed.
     */
    const val OFFSET_total_entry_count: Long = 0L
    fun total_entry_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_total_entry_count)
    fun total_entry_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_total_entry_count, value)
    }
}

/**
 * Event data reported for each dependency entry in a listing.
 */
object lore_file_dependency_list_entry_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_array_t.LAYOUT.withName("tags"),
        ValueLayout.JAVA_INT.withName("depth"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_dependency_list_entry_event_data_t") as StructLayout

    /**
     * Path of the dependency.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Tags on this dependency edge.
     */
    const val OFFSET_tags: Long = 16L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)

    /**
     * Traversal depth, zero for a direct dependency.
     */
    const val OFFSET_depth: Long = 32L
    fun depth(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_depth)
    fun depth(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_depth, value)
    }
}

/**
 * Event data reported at the end of listing a single file's dependencies.
 */
object lore_file_dependency_list_file_end_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_file_dependency_list_file_end_event_data_t") as StructLayout

    /**
     * Path of the file whose dependencies were listed.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported at the start of listing a single file's dependencies.
 */
object lore_file_dependency_list_file_event_data_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("entry_count"),
    ).withName("lore_file_dependency_list_file_event_data_t") as StructLayout

    /**
     * Path of the file whose dependencies are being listed.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Number of dependency entries for this file.
     */
    const val OFFSET_entry_count: Long = 16L
    fun entry_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_entry_count)
    fun entry_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_entry_count, value)
    }
}

/**
 * Arguments for removing file dependencies, expanded from flat parallel arrays.
 */
object lore_file_dependency_remove_args_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_array_t.LAYOUT.withName("dependencies"),
        lore_string_array_t.LAYOUT.withName("tags"),
        lore_uint32_array_t.LAYOUT.withName("dep_counts"),
        lore_uint32_array_t.LAYOUT.withName("tag_counts"),
    ).withName("lore_file_dependency_remove_args_t") as StructLayout

    /**
     * Source file paths to remove dependencies from.
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Dependency target paths to remove (flat array).
     */
    const val OFFSET_dependencies: Long = 16L
    fun dependencies(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependencies, 16L)

    /**
     * Tags to remove.
     */
    const val OFFSET_tags: Long = 32L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)

    /**
     * Number of dependencies per source file.
     */
    const val OFFSET_dep_counts: Long = 48L
    fun dep_counts(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dep_counts, 16L)

    /**
     * Number of tags per dependency entry.
     */
    const val OFFSET_tag_counts: Long = 64L
    fun tag_counts(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tag_counts, 16L)
}

/**
 * Event data reported at the start of removing file dependencies.
 */
object lore_file_dependency_remove_begin_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("path_count"),
        ValueLayout.JAVA_LONG.withName("dependency_count"),
    ).withName("lore_file_dependency_remove_begin_event_data_t") as StructLayout

    /**
     * Number of source files being processed.
     */
    const val OFFSET_path_count: Long = 0L
    fun path_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_path_count)
    fun path_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_path_count, value)
    }

    /**
     * Number of dependency edges being removed.
     */
    const val OFFSET_dependency_count: Long = 8L
    fun dependency_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_dependency_count)
    fun dependency_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_dependency_count, value)
    }
}

/**
 * Event data reported at the end of removing file dependencies.
 */
object lore_file_dependency_remove_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("removed_count"),
    ).withName("lore_file_dependency_remove_end_event_data_t") as StructLayout

    /**
     * Number of dependency edges that were removed.
     */
    const val OFFSET_removed_count: Long = 0L
    fun removed_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_removed_count)
    fun removed_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_removed_count, value)
    }
}

/**
 * Event data reported for each dependency edge being removed.
 */
object lore_file_dependency_remove_entry_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("dependency"),
        lore_string_array_t.LAYOUT.withName("tags"),
    ).withName("lore_file_dependency_remove_entry_event_data_t") as StructLayout

    /**
     * Path of the source file that loses the dependency.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Path of the file that was depended on.
     */
    const val OFFSET_dependency: Long = 16L
    fun dependency(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependency, 16L)

    /**
     * Tags on the dependency edge being removed.
     */
    const val OFFSET_tags: Long = 32L
    fun tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tags, 16L)
}

/**
 * Arguments for diffing files between two revisions.
 */
object lore_file_diff_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("source_revision"),
        lore_string_t.LAYOUT.withName("target_revision"),
        ValueLayout.JAVA_BYTE.withName("diff3"),
        MemoryLayout.paddingLayout(3),
        ValueLayout.JAVA_INT.withName("context_lines"),
        ValueLayout.JAVA_BYTE.withName("ignore_whitespace_eol"),
        ValueLayout.JAVA_BYTE.withName("ignore_whitespace_inline"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_file_diff_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Source revision
     */
    const val OFFSET_source_revision: Long = 16L
    fun source_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_revision, 16L)

    /**
     * Target revision
     */
    const val OFFSET_target_revision: Long = 32L
    fun target_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_revision, 16L)

    /**
     * Produce three-way merge output with conflict markers
     */
    const val OFFSET_diff3: Long = 48L
    fun diff3(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_diff3)
    fun diff3(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_diff3, value)
    }

    /**
     * Number of unchanged context lines per unified-diff hunk
     */
    const val OFFSET_context_lines: Long = 52L
    fun context_lines(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_context_lines)
    fun context_lines(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_context_lines, value)
    }

    /**
     * Treat lines that differ only in trailing whitespace as equal
     */
    const val OFFSET_ignore_whitespace_eol: Long = 56L
    fun ignore_whitespace_eol(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignore_whitespace_eol)
    fun ignore_whitespace_eol(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignore_whitespace_eol, value)
    }

    /**
     * Collapse runs of internal whitespace to a single space for comparison
     */
    const val OFFSET_ignore_whitespace_inline: Long = 57L
    fun ignore_whitespace_inline(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignore_whitespace_inline)
    fun ignore_whitespace_inline(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignore_whitespace_inline, value)
    }
}

/**
 * Data for the event carrying the diff of a single file.
 */
object lore_file_diff_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("patch"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_diff_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Unified-diff text describing the change.
     */
    const val OFFSET_patch: Long = 16L
    fun patch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_patch, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 32L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }
}

/**
 * Arguments for marking files dirty in the staged state (add/modify/delete inferred from filesystem).
 */
object lore_file_dirty_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_file_dirty_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for marking a file dirty-copied (creates a new staged destination node, no filesystem checks).
 */
object lore_file_dirty_copy_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("from_path"),
        lore_string_t.LAYOUT.withName("to_path"),
    ).withName("lore_file_dirty_copy_args_t") as StructLayout

    /**
     * Source path of file
     */
    const val OFFSET_from_path: Long = 0L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)

    /**
     * Destination path of copy
     */
    const val OFFSET_to_path: Long = 16L
    fun to_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_to_path, 16L)
}

/**
 * Arguments for marking a file dirty-moved (relocates the staged node, no filesystem checks).
 */
object lore_file_dirty_move_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("from_path"),
        lore_string_t.LAYOUT.withName("to_path"),
    ).withName("lore_file_dirty_move_args_t") as StructLayout

    /**
     * Original path of file
     */
    const val OFFSET_from_path: Long = 0L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)

    /**
     * New path of file
     */
    const val OFFSET_to_path: Long = 16L
    fun to_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_to_path, 16L)
}

/**
 * Arguments for dumping the binary content of a file by path or address.
 */
object lore_file_dump_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("address"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_file_dump_args_t") as StructLayout

    /**
     * Address of data to dump; takes precedence over `path` when non-empty
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 16L)

    /**
     * Repository path to dump; used when `address` is empty
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for the event reporting the stored representation of file content.
 */
object lore_file_dump_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_INT.withName("flags"),
        ValueLayout.JAVA_INT.withName("size_payload"),
        ValueLayout.JAVA_LONG.withName("size_content"),
        ValueLayout.JAVA_BYTE.withName("match_made"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_file_dump_event_data_t") as StructLayout

    /**
     * Address of the content.
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Flags describing the stored content.
     */
    const val OFFSET_flags: Long = 48L
    fun flags(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_flags, value)
    }

    /**
     * Size of the stored payload in bytes.
     */
    const val OFFSET_size_payload: Long = 52L
    fun size_payload(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_size_payload)
    fun size_payload(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_size_payload, value)
    }

    /**
     * Size of the content in bytes.
     */
    const val OFFSET_size_content: Long = 56L
    fun size_content(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size_content)
    fun size_content(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size_content, value)
    }

    /**
     * Set when a matching stored object was found.
     */
    const val OFFSET_match_made: Long = 64L
    fun match_made(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_match_made)
    fun match_made(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_match_made, value)
    }
}

/**
 * Arguments for computing the hash and size of one or more files.
 */
object lore_file_hash_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_file_hash_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for the event reporting the hash of a single file.
 */
object lore_file_hash_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("size"),
        lore_hash_t.LAYOUT.withName("hash"),
    ).withName("lore_file_hash_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Size of the file in bytes.
     */
    const val OFFSET_size: Long = 16L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Content hash of the file.
     */
    const val OFFSET_hash: Long = 24L
    fun hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_hash, 32L)
}

/**
 * Arguments for retrieving the revision history of a specific file.
 */
object lore_file_history_args_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("branch"),
        ValueLayout.JAVA_INT.withName("length"),
        ValueLayout.JAVA_INT.withName("depth"),
    ).withName("lore_file_history_args_t") as StructLayout

    /**
     * A path to a file
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Optional revision specifier to start from
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Restrict history to revisions on this branch
     */
    const val OFFSET_branch: Long = 32L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Number of revisions to list
     */
    const val OFFSET_length: Long = 48L
    fun length(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_length)
    fun length(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_length, value)
    }

    /**
     * Number of revisions to search initially
     */
    const val OFFSET_depth: Long = 52L
    fun depth(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_depth)
    fun depth(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_depth, value)
    }
}

/**
 * Data for the event describing one entry in a file's history.
 */
object lore_file_history_event_data_t {
    const val SIZE: Long = 200L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        MemoryLayout.sequenceLayout(2, lore_hash_t.LAYOUT).withName("parent"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_history_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Identifier of the repository.
     */
    const val OFFSET_repository: Long = 16L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision this entry belongs to.
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Sequential number of the revision.
     */
    const val OFFSET_revision_number: Long = 64L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Parent revisions of this revision.
     */
    const val OFFSET_parent: Long = 72L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 64L)

    /**
     * Address of the file content at this revision.
     */
    const val OFFSET_address: Long = 136L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Size of the file in bytes at this revision.
     */
    const val OFFSET_size: Long = 184L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Action applied to the file at this revision.
     */
    const val OFFSET_action: Long = 192L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }
}

/**
 * Arguments for retrieving file information (size, hash, staged status).
 */
object lore_file_info_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("local"),
        ValueLayout.JAVA_BYTE.withName("filtered"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_file_info_args_t") as StructLayout

    /**
     * Array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Revision to get info for
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Calculate the filtered local filesystem hash and size
     */
    const val OFFSET_local: Long = 32L
    fun local(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local)
    fun local(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local, value)
    }

    /**
     * Calculate the filtered repository size
     */
    const val OFFSET_filtered: Long = 33L
    fun filtered(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_filtered)
    fun filtered(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_filtered, value)
    }
}

/**
 * Data for the event reporting information about a single file or directory.
 */
object lore_file_info_event_data_t {
    const val SIZE: Long = 128L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_context_t.LAYOUT.withName("context"),
        lore_hash_t.LAYOUT.withName("hash"),
        ValueLayout.JAVA_BYTE.withName("is_file"),
        ValueLayout.JAVA_BYTE.withName("is_dir"),
        ValueLayout.JAVA_BYTE.withName("flag_modified"),
        ValueLayout.JAVA_BYTE.withName("flag_deleted"),
        ValueLayout.JAVA_BYTE.withName("flag_added"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_LONG.withName("local_size"),
        lore_hash_t.LAYOUT.withName("local_hash"),
        ValueLayout.JAVA_LONG.withName("filter_size"),
    ).withName("lore_file_info_event_data_t") as StructLayout

    /**
     * Path of the file or directory.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Context identifying the file or directory.
     */
    const val OFFSET_context: Long = 16L
    fun context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_context, 16L)

    /**
     * Content hash of the file or directory.
     */
    const val OFFSET_hash: Long = 32L
    fun hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_hash, 32L)

    /**
     * Set when the entry is a file.
     */
    const val OFFSET_is_file: Long = 64L
    fun is_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_file)
    fun is_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_file, value)
    }

    /**
     * Set when the entry is a directory.
     */
    const val OFFSET_is_dir: Long = 65L
    fun is_dir(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_dir)
    fun is_dir(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_dir, value)
    }

    /**
     * Set when the entry has been modified.
     */
    const val OFFSET_flag_modified: Long = 66L
    fun flag_modified(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_modified)
    fun flag_modified(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_modified, value)
    }

    /**
     * Set when the entry has been deleted.
     */
    const val OFFSET_flag_deleted: Long = 67L
    fun flag_deleted(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_deleted)
    fun flag_deleted(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_deleted, value)
    }

    /**
     * Set when the entry has been added.
     */
    const val OFFSET_flag_added: Long = 68L
    fun flag_added(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_added)
    fun flag_added(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_added, value)
    }

    /**
     * Set when the entry is in conflict.
     */
    const val OFFSET_flag_conflict: Long = 69L
    fun flag_conflict(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict)
    fun flag_conflict(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict, value)
    }

    /**
     * File mode bits.
     */
    const val OFFSET_mode: Long = 70L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * Size of the entry in the repository, in bytes.
     */
    const val OFFSET_size: Long = 72L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Size of the entry on the local filesystem, in bytes.
     */
    const val OFFSET_local_size: Long = 80L
    fun local_size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_local_size)
    fun local_size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_local_size, value)
    }

    /**
     * Content hash of the entry on the local filesystem.
     */
    const val OFFSET_local_hash: Long = 88L
    fun local_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_local_hash, 32L)

    /**
     * Size of the entry after filters are applied, in bytes.
     */
    const val OFFSET_filter_size: Long = 120L
    fun filter_size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_filter_size)
    fun filter_size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_filter_size, value)
    }
}

/**
 * Arguments for clearing all metadata associated with a file.
 */
object lore_file_metadata_clear_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_file_metadata_clear_args_t") as StructLayout

    /**
     * Which file to clear metadata for
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for retrieving a single metadata value for a file by key and revision.
 */
object lore_file_metadata_get_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("key"),
    ).withName("lore_file_metadata_get_args_t") as StructLayout

    /**
     * Revision to get metadata for
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Where to get metadata for
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Metadata key
     */
    const val OFFSET_key: Long = 32L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)
}

/**
 * Arguments for listing all metadata key/value pairs for a file at a revision.
 */
object lore_file_metadata_list_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("revision"),
    ).withName("lore_file_metadata_list_args_t") as StructLayout

    /**
     * What to list metadata for
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Revision to list metadata for
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)
}

/**
 * Arguments for setting metadata key/value pairs on one or more files.
 */
object lore_file_metadata_set_args_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_array_t.LAYOUT.withName("keys"),
        lore_string_array_t.LAYOUT.withName("values"),
        lore_metadata_type_array_t.LAYOUT.withName("formats"),
        lore_uint32_array_t.LAYOUT.withName("entries"),
    ).withName("lore_file_metadata_set_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * An array of keys
     */
    const val OFFSET_keys: Long = 16L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)

    /**
     * An array of values
     */
    const val OFFSET_values: Long = 32L
    fun values(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_values, 16L)

    /**
     * Pointer to an array of formats
     */
    const val OFFSET_formats: Long = 48L
    fun formats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_formats, 16L)

    /**
     * Pointer to an array of entry counts per path
     */
    const val OFFSET_entries: Long = 64L
    fun entries(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_entries, 16L)
}

/**
 * Arguments for permanently removing a file or address from repository history.
 */
object lore_file_obliterate_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("address"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_file_obliterate_args_t") as StructLayout

    /**
     * Address of data to obliterate; takes precedence over `path` when non-empty
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 16L)

    /**
     * Repository path to obliterate; used when `address` is empty
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for the event emitted when file content is obliterated.
 */
object lore_file_obliterate_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_LONG.withName("num_fragments"),
        ValueLayout.JAVA_LONG.withName("num_payloads"),
    ).withName("lore_file_obliterate_event_data_t") as StructLayout

    /**
     * Address of the obliterated content.
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Number of fragments removed.
     */
    const val OFFSET_num_fragments: Long = 48L
    fun num_fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_num_fragments)
    fun num_fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_num_fragments, value)
    }

    /**
     * Number of payloads removed.
     */
    const val OFFSET_num_payloads: Long = 56L
    fun num_payloads(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_num_payloads)
    fun num_payloads(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_num_payloads, value)
    }
}

/**
 * Arguments for resetting files to a revision, optionally purging untracked files.
 */
object lore_file_reset_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("purge"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_file_reset_args_t") as StructLayout

    /**
     * Pointer to an array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Revision to reset files into
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Purge untracked files
     */
    const val OFFSET_purge: Long = 32L
    fun purge(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_purge)
    fun purge(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_purge, value)
    }
}

/**
 * Data for the event emitted when a reset operation begins.
 */
object lore_file_reset_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("path_count"),
    ).withName("lore_file_reset_begin_event_data_t") as StructLayout

    /**
     * Number of paths requested for reset.
     */
    const val OFFSET_path_count: Long = 0L
    fun path_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_path_count)
    fun path_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_path_count, value)
    }
}

/**
 * Running counts of items processed during a reset operation.
 */
object lore_file_reset_count_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("directory_reset_count"),
        ValueLayout.JAVA_LONG.withName("directory_delete_count"),
        ValueLayout.JAVA_LONG.withName("file_reset_count"),
        ValueLayout.JAVA_LONG.withName("file_delete_count"),
    ).withName("lore_file_reset_count_data_t") as StructLayout

    /**
     * Number of directories that were reset.
     */
    const val OFFSET_directory_reset_count: Long = 0L
    fun directory_reset_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_reset_count)
    fun directory_reset_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_reset_count, value)
    }

    /**
     * Number of directories that were deleted.
     */
    const val OFFSET_directory_delete_count: Long = 8L
    fun directory_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count)
    fun directory_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count, value)
    }

    /**
     * Number of files that were reset.
     */
    const val OFFSET_file_reset_count: Long = 16L
    fun file_reset_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_reset_count)
    fun file_reset_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_reset_count, value)
    }

    /**
     * Number of files that were deleted.
     */
    const val OFFSET_file_delete_count: Long = 24L
    fun file_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_delete_count)
    fun file_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_delete_count, value)
    }
}

/**
 * Data for the event emitted when a reset operation completes.
 */
object lore_file_reset_end_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_reset_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_reset_end_event_data_t") as StructLayout

    /**
     * Final counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 32L)
}

/**
 * Data for the event emitted for each file affected by a reset operation.
 */
object lore_file_reset_file_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("from_path"),
    ).withName("lore_file_reset_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 16L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Previous path of the file, when it was moved.
     */
    const val OFFSET_from_path: Long = 24L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)
}

/**
 * Data for the progress event emitted periodically during a reset operation.
 */
object lore_file_reset_progress_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_reset_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_reset_progress_event_data_t") as StructLayout

    /**
     * Current counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 32L)
}

/**
 * Arguments for resetting files to the last merged revision on a branch.
 */
object lore_file_reset_to_last_merged_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("branch"),
        ValueLayout.JAVA_BYTE.withName("purge"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_file_reset_to_last_merged_args_t") as StructLayout

    /**
     * Pointer to an array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Branch whose last merged revision to reset to
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Purge untracked files
     */
    const val OFFSET_purge: Long = 32L
    fun purge(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_purge)
    fun purge(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_purge, value)
    }
}

/**
 * Arguments for staging one or more files for the next commit.
 */
object lore_file_stage_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        ValueLayout.JAVA_INT.withName("case_change"),
        ValueLayout.JAVA_BYTE.withName("scan"),
        MemoryLayout.paddingLayout(3),
    ).withName("lore_file_stage_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Case change handling, 0 = error, 1 = update filesystem (keep), 2 = update repository (rename)
     */
    const val OFFSET_case_change: Long = 16L
    fun case_change(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_case_change)
    fun case_change(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_case_change, value)
    }

    /**
     * Force a recursive filesystem scan of directory paths (no effect on file paths)
     */
    const val OFFSET_scan: Long = 20L
    fun scan(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_scan)
    fun scan(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_scan, value)
    }
}

/**
 * Data for the event emitted when a stage operation begins.
 */
object lore_file_stage_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("path_count"),
    ).withName("lore_file_stage_begin_event_data_t") as StructLayout

    /**
     * Number of paths requested for staging.
     */
    const val OFFSET_path_count: Long = 0L
    fun path_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_path_count)
    fun path_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_path_count, value)
    }
}

/**
 * Running counts of items processed during a stage operation.
 */
object lore_file_stage_count_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("directory_modify_count"),
        ValueLayout.JAVA_LONG.withName("directory_add_count"),
        ValueLayout.JAVA_LONG.withName("directory_delete_count"),
        ValueLayout.JAVA_LONG.withName("directory_move_count"),
        ValueLayout.JAVA_LONG.withName("file_modify_count"),
        ValueLayout.JAVA_LONG.withName("file_add_count"),
        ValueLayout.JAVA_LONG.withName("file_delete_count"),
        ValueLayout.JAVA_LONG.withName("file_move_count"),
        ValueLayout.JAVA_LONG.withName("total_count"),
    ).withName("lore_file_stage_count_data_t") as StructLayout

    /**
     * Number of directories staged as modified.
     */
    const val OFFSET_directory_modify_count: Long = 0L
    fun directory_modify_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_modify_count)
    fun directory_modify_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_modify_count, value)
    }

    /**
     * Number of directories staged as added.
     */
    const val OFFSET_directory_add_count: Long = 8L
    fun directory_add_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_add_count)
    fun directory_add_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_add_count, value)
    }

    /**
     * Number of directories staged as deleted.
     */
    const val OFFSET_directory_delete_count: Long = 16L
    fun directory_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count)
    fun directory_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count, value)
    }

    /**
     * Number of directories staged as moved.
     */
    const val OFFSET_directory_move_count: Long = 24L
    fun directory_move_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_move_count)
    fun directory_move_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_move_count, value)
    }

    /**
     * Number of files staged as modified.
     */
    const val OFFSET_file_modify_count: Long = 32L
    fun file_modify_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_modify_count)
    fun file_modify_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_modify_count, value)
    }

    /**
     * Number of files staged as added.
     */
    const val OFFSET_file_add_count: Long = 40L
    fun file_add_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_add_count)
    fun file_add_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_add_count, value)
    }

    /**
     * Number of files staged as deleted.
     */
    const val OFFSET_file_delete_count: Long = 48L
    fun file_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_delete_count)
    fun file_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_delete_count, value)
    }

    /**
     * Number of files staged as moved.
     */
    const val OFFSET_file_move_count: Long = 56L
    fun file_move_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_move_count)
    fun file_move_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_move_count, value)
    }

    /**
     * Total number of items processed.
     */
    const val OFFSET_total_count: Long = 64L
    fun total_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_total_count)
    fun total_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_total_count, value)
    }
}

/**
 * Data for the event emitted when a stage operation completes.
 */
object lore_file_stage_end_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_stage_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_stage_end_event_data_t") as StructLayout

    /**
     * Final counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 72L)
}

/**
 * Data for the event emitted for each file affected by a stage operation.
 */
object lore_file_stage_file_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("from_path"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_stage_file_event_data_t") as StructLayout

    /**
     * Previous path of the file, when it was moved.
     */
    const val OFFSET_from_path: Long = 0L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 32L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }
}

/**
 * Arguments for staging one or more files as merge resolutions.
 */
object lore_file_stage_merge_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_file_stage_merge_args_t") as StructLayout

    /**
     * Paths to files to stage as merge
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for staging a file move from one path to another.
 */
object lore_file_stage_move_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("from_path"),
        lore_string_t.LAYOUT.withName("to_path"),
    ).withName("lore_file_stage_move_args_t") as StructLayout

    /**
     * Original path of file
     */
    const val OFFSET_from_path: Long = 0L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)

    /**
     * New path of file
     */
    const val OFFSET_to_path: Long = 16L
    fun to_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_to_path, 16L)
}

/**
 * Data for the progress event emitted periodically during a stage operation.
 */
object lore_file_stage_progress_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_stage_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_stage_progress_event_data_t") as StructLayout

    /**
     * Current counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 72L)
}

/**
 * Data for the event identifying the repository and revision involved in a stage operation.
 */
object lore_file_stage_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_file_stage_revision_event_data_t") as StructLayout

    /**
     * Identifier of the repository.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the files are staged against.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for removing one or more files from the staged changeset.
 */
object lore_file_unstage_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_file_unstage_args_t") as StructLayout

    /**
     * An array of paths
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for the event emitted when an unstage operation begins.
 */
object lore_file_unstage_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("path_count"),
    ).withName("lore_file_unstage_begin_event_data_t") as StructLayout

    /**
     * Number of paths requested for unstaging.
     */
    const val OFFSET_path_count: Long = 0L
    fun path_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_path_count)
    fun path_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_path_count, value)
    }
}

/**
 * Running counts of items processed during an unstage operation.
 */
object lore_file_unstage_count_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("directory_unstaged_count"),
        ValueLayout.JAVA_LONG.withName("directory_discarded_count"),
        ValueLayout.JAVA_LONG.withName("file_unstaged_count"),
        ValueLayout.JAVA_LONG.withName("file_discarded_count"),
        ValueLayout.JAVA_LONG.withName("total_count"),
    ).withName("lore_file_unstage_count_data_t") as StructLayout

    /**
     * Number of directories that were unstaged.
     */
    const val OFFSET_directory_unstaged_count: Long = 0L
    fun directory_unstaged_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_unstaged_count)
    fun directory_unstaged_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_unstaged_count, value)
    }

    /**
     * Number of directories that were discarded.
     */
    const val OFFSET_directory_discarded_count: Long = 8L
    fun directory_discarded_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_discarded_count)
    fun directory_discarded_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_discarded_count, value)
    }

    /**
     * Number of files that were unstaged.
     */
    const val OFFSET_file_unstaged_count: Long = 16L
    fun file_unstaged_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_unstaged_count)
    fun file_unstaged_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_unstaged_count, value)
    }

    /**
     * Number of files that were discarded.
     */
    const val OFFSET_file_discarded_count: Long = 24L
    fun file_discarded_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_discarded_count)
    fun file_discarded_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_discarded_count, value)
    }

    /**
     * Total number of items processed.
     */
    const val OFFSET_total_count: Long = 32L
    fun total_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_total_count)
    fun total_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_total_count, value)
    }
}

/**
 * Data for the event emitted when an unstage operation completes.
 */
object lore_file_unstage_end_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_unstage_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_unstage_end_event_data_t") as StructLayout

    /**
     * Final counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 40L)
}

/**
 * Data for the event emitted for each file affected by an unstage operation.
 */
object lore_file_unstage_file_event_data_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_file_unstage_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 16L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }
}

/**
 * Data for the progress event emitted periodically during an unstage operation.
 */
object lore_file_unstage_progress_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_file_unstage_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_file_unstage_progress_event_data_t") as StructLayout

    /**
     * Current counts of items processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 40L)
}

/**
 * Data for the event identifying the repository and revision involved in an unstage operation.
 */
object lore_file_unstage_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_file_unstage_revision_event_data_t") as StructLayout

    /**
     * Identifier of the repository.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the files are unstaged against.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for writing a file to a destination by path/revision or by address.
 */
object lore_file_write_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("address"),
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("output"),
    ).withName("lore_file_write_args_t") as StructLayout

    /**
     * Address of data to write; takes precedence over `path` when non-empty
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 16L)

    /**
     * Repository path to the file; used when `address` is empty
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Revision of the file to write (used with `path`)
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Destination filesystem path to write to
     */
    const val OFFSET_output: Long = 48L
    fun output(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_output, 16L)
}

/**
 * Data for the event emitted when file content is written to a destination.
 */
object lore_file_write_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_file_write_event_data_t") as StructLayout

    /**
     * Path that was written.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for the event emitted when a path is excluded by a filter.
 */
object lore_filter_exclude_event_data_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("reason"),
        MemoryLayout.paddingLayout(7),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_filter_exclude_event_data_t") as StructLayout

    /**
     * Reason the path was excluded.
     */
    const val OFFSET_reason: Long = 0L
    fun reason(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reason)
    fun reason(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reason, value)
    }

    /**
     * Path that was excluded.
     */
    const val OFFSET_path: Long = 8L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Header describing a stored piece of content.
 * 
 * Records how the payload is stored and how large it is, both as held in
 * storage and once fully reassembled.
 */
object lore_fragment_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("flags"),
        ValueLayout.JAVA_INT.withName("size_payload"),
        ValueLayout.JAVA_LONG.withName("size_content"),
    ).withName("lore_fragment_t") as StructLayout

    /**
     * Flags
     */
    const val OFFSET_flags: Long = 0L
    fun flags(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_flags, value)
    }

    /**
     * Payload size
     */
    const val OFFSET_size_payload: Long = 4L
    fun size_payload(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_size_payload)
    fun size_payload(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_size_payload, value)
    }

    /**
     * Size of the uncompressed and reassembled content
     */
    const val OFFSET_size_content: Long = 8L
    fun size_content(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size_content)
    fun size_content(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size_content, value)
    }
}

/**
 * Event data reporting a single fragment written or deduplicated.
 */
object lore_fragment_write_event_data_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_fragment_t.LAYOUT.withName("fragment"),
        ValueLayout.JAVA_BYTE.withName("deduplicated"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_fragment_write_event_data_t") as StructLayout

    /**
     * The fragment that was written
     */
    const val OFFSET_fragment: Long = 0L
    fun fragment(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_fragment, 16L)

    /**
     * Non-zero if the fragment already existed and was deduplicated
     */
    const val OFFSET_deduplicated: Long = 16L
    fun deduplicated(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_deduplicated)
    fun deduplicated(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_deduplicated, value)
    }
}

/**
 * Common options shared by repository operations.
 */
object lore_global_args_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("repository_path"),
        lore_string_t.LAYOUT.withName("working_directory"),
        lore_string_t.LAYOUT.withName("correlation_id"),
        lore_string_t.LAYOUT.withName("identity"),
        ValueLayout.JAVA_BYTE.withName("force"),
        ValueLayout.JAVA_BYTE.withName("offline"),
        ValueLayout.JAVA_BYTE.withName("local"),
        ValueLayout.JAVA_BYTE.withName("remote"),
        ValueLayout.JAVA_BYTE.withName("dry_run"),
        ValueLayout.JAVA_BYTE.withName("no_atime"),
        MemoryLayout.paddingLayout(2),
        ValueLayout.JAVA_INT.withName("max_connections"),
        ValueLayout.JAVA_INT.withName("search_limit"),
        ValueLayout.JAVA_BYTE.withName("search_nearest"),
        ValueLayout.JAVA_BYTE.withName("no_gc"),
        ValueLayout.JAVA_BYTE.withName("in_memory"),
        MemoryLayout.paddingLayout(5),
        ValueLayout.JAVA_LONG.withName("file_count_limit"),
        ValueLayout.JAVA_LONG.withName("file_size_limit"),
        ValueLayout.JAVA_LONG.withName("compress_task_limit"),
        ValueLayout.JAVA_BYTE.withName("store_keep_alive"),
        MemoryLayout.paddingLayout(7),
        ValueLayout.JAVA_LONG.withName("store_keep_alive_seconds"),
        ValueLayout.JAVA_BYTE.withName("sync_data"),
        ValueLayout.JAVA_BYTE.withName("cache"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_global_args_t") as StructLayout

    /**
     * Repository path
     */
    const val OFFSET_repository_path: Long = 0L
    fun repository_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository_path, 16L)

    /**
     * Directory that relative paths in this call are resolved against. Set it
     * when a call may be executed by another process, such as the Lore
     * service, whose own working directory is unrelated to the caller's. When
     * empty, relative paths resolve against the working directory of the
     * process performing the call.
     */
    const val OFFSET_working_directory: Long = 16L
    fun working_directory(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_working_directory, 16L)

    /**
     * Correlation ID
     */
    const val OFFSET_correlation_id: Long = 32L
    fun correlation_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_correlation_id, 16L)

    /**
     * Identity to use
     */
    const val OFFSET_identity: Long = 48L
    fun identity(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_identity, 16L)

    /**
     * Force the operation if possible
     */
    const val OFFSET_force: Long = 64L
    fun force(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_force)
    fun force(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_force, value)
    }

    /**
     * Run operation without connecting to server
     */
    const val OFFSET_offline: Long = 65L
    fun offline(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_offline)
    fun offline(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_offline, value)
    }

    /**
     * Use only local data
     */
    const val OFFSET_local: Long = 66L
    fun local(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local)
    fun local(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local, value)
    }

    /**
     * Use only remote data
     */
    const val OFFSET_remote: Long = 67L
    fun remote(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote)
    fun remote(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote, value)
    }

    /**
     * Dry run mode, only report what would have been changed and perform no changes to local file system
     */
    const val OFFSET_dry_run: Long = 68L
    fun dry_run(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_dry_run)
    fun dry_run(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_dry_run, value)
    }

    /**
     * Avoid recording last access timestamps in the data stores
     */
    const val OFFSET_no_atime: Long = 69L
    fun no_atime(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_atime)
    fun no_atime(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_atime, value)
    }

    /**
     * Maximum number of parallel connections for bulk data transfer
     */
    const val OFFSET_max_connections: Long = 72L
    fun max_connections(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_max_connections)
    fun max_connections(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_max_connections, value)
    }

    /**
     * Search limit when iterating revisions
     */
    const val OFFSET_search_limit: Long = 76L
    fun search_limit(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_search_limit)
    fun search_limit(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_search_limit, value)
    }

    /**
     * Allow matching to the nearest matching revision when a perfect match is not available
     */
    const val OFFSET_search_nearest: Long = 80L
    fun search_nearest(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_search_nearest)
    fun search_nearest(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_search_nearest, value)
    }

    /**
     * Prevent the automatic incremental/step GC for this operation; it otherwise runs in the background on write operations. `repository gc` always runs a full pass regardless
     */
    const val OFFSET_no_gc: Long = 81L
    fun no_gc(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_gc)
    fun no_gc(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_gc, value)
    }

    /**
     * Use in-memory stores instead of file-backed stores. No store data is
     * read from or written to the .urc/immutable/ and .urc/mutable/ directories.
     */
    const val OFFSET_in_memory: Long = 82L
    fun in_memory(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_in_memory)
    fun in_memory(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_in_memory, value)
    }

    /**
     * Maximum number of files being processed in parallel
     */
    const val OFFSET_file_count_limit: Long = 88L
    fun file_count_limit(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_count_limit)
    fun file_count_limit(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_count_limit, value)
    }

    /**
     * Maximum total size of all files being processed in parallel
     */
    const val OFFSET_file_size_limit: Long = 96L
    fun file_size_limit(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_size_limit)
    fun file_size_limit(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_size_limit, value)
    }

    /**
     * Maximum number of parallel compression tasks
     */
    const val OFFSET_compress_task_limit: Long = 104L
    fun compress_task_limit(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_compress_task_limit)
    fun compress_task_limit(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_compress_task_limit, value)
    }

    /**
     * Keep store references alive after a repository call completes to avoid
     * repeated store open/close cycles for consecutive API calls in the same process.
     */
    const val OFFSET_store_keep_alive: Long = 112L
    fun store_keep_alive(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_store_keep_alive)
    fun store_keep_alive(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_store_keep_alive, value)
    }

    /**
     * Duration in seconds to keep store references alive. Only used when
     * `store_keep_alive` is set. 0 means use the default (10 seconds).
     */
    const val OFFSET_store_keep_alive_seconds: Long = 120L
    fun store_keep_alive_seconds(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_store_keep_alive_seconds)
    fun store_keep_alive_seconds(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_store_keep_alive_seconds, value)
    }

    /**
     * Force sync data to storage media during store flush
     */
    const val OFFSET_sync_data: Long = 128L
    fun sync_data(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_sync_data)
    fun sync_data(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_sync_data, value)
    }

    /**
     * Cache fragment payloads fetched from remote in the local store. Without
     * this only state fragments and fragments flagged for local cache priority
     * are retained
     */
    const val OFFSET_cache: Long = 129L
    fun cache(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_cache)
    fun cache(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_cache, value)
    }
}

/**
 * Opaque 256-bit content hash.
 * 
 * Identifies a piece of content by the digest of its bytes. Two pieces of
 * identical content share the same hash.
 */
object lore_hash_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE).withName("data"),
    ).withName("lore_hash_t") as StructLayout

    /**
     * The raw 32 bytes of the hash digest.
     */
    const val OFFSET_data: Long = 0L
    fun data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_data, 32L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_instance_id_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_instance_id_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * A unique identity for a repository instance (a local checkout).
 * 
 * Each instance gets a stable `UUIDv7` generated once at creation time
 * and stored in `.lore/instance`. The instance ID is used to derive
 * per-instance anchor keys in the mutable store, distinguishing one
 * instance's checkout state from another when sharing a shared store.
 */
object lore_instance_id_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE).withName("data"),
    ).withName("lore_instance_id_t") as StructLayout

    /**
     * The raw 16-byte identifier
     */
    const val OFFSET_data: Long = 0L
    fun data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_data, 16L)
}

/**
 * Arguments for adding a layer from a source repository into the current repository.
 */
object lore_layer_add_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_string_t.LAYOUT.withName("source_repository"),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_string_t.LAYOUT.withName("metadata"),
    ).withName("lore_layer_add_args_t") as StructLayout

    /**
     * Path in the current repository where the layer should be placed
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Repository to add as a layer
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Path in the layer repository where the layer should start
     */
    const val OFFSET_source_path: Long = 32L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Metadata key to use to match revisions
     */
    const val OFFSET_metadata: Long = 48L
    fun metadata(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_metadata, 16L)
}

/**
 * Data for the event emitted when a layer is added.
 */
object lore_layer_add_event_data_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_partition_t.LAYOUT.withName("source_repository"),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_string_t.LAYOUT.withName("metadata"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_layer_add_event_data_t") as StructLayout

    /**
     * Path in the outer repository where the layer is placed.
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Identifier of the source repository.
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Path inside the source repository where the layer starts.
     */
    const val OFFSET_source_path: Long = 32L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Metadata used to match revisions between the repositories.
     */
    const val OFFSET_metadata: Long = 48L
    fun metadata(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_metadata, 16L)

    /**
     * Revision of the source repository.
     */
    const val OFFSET_revision: Long = 64L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Data for the event describing a single configured layer.
 */
object lore_layer_entry_event_data_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_partition_t.LAYOUT.withName("source_repository"),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_string_t.LAYOUT.withName("metadata"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_layer_entry_event_data_t") as StructLayout

    /**
     * Path in the outer repository where the layer is placed.
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Identifier of the source repository.
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Path inside the source repository where the layer starts.
     */
    const val OFFSET_source_path: Long = 32L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Metadata used to match revisions between the repositories.
     */
    const val OFFSET_metadata: Long = 48L
    fun metadata(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_metadata, 16L)

    /**
     * Revision of the source repository.
     */
    const val OFFSET_revision: Long = 64L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for listing all layers configured in the repository (no parameters).
 */
object lore_layer_list_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_layer_list_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for removing a layer from the repository at the specified path.
 */
object lore_layer_remove_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_string_t.LAYOUT.withName("source_repository"),
        ValueLayout.JAVA_BYTE.withName("purge"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_layer_remove_args_t") as StructLayout

    /**
     * Path in the current repository where the layer is placed
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Repository added as a layer at the given path
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Remove all untracked files and directories inside the layer mount
     */
    const val OFFSET_purge: Long = 32L
    fun purge(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_purge)
    fun purge(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_purge, value)
    }
}

/**
 * Data for the event emitted when a layer is removed.
 */
object lore_layer_remove_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_partition_t.LAYOUT.withName("source_repository"),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("forced"),
        ValueLayout.JAVA_BYTE.withName("purged"),
        MemoryLayout.paddingLayout(6),
        ValueLayout.JAVA_LONG.withName("file_count"),
        ValueLayout.JAVA_LONG.withName("directory_count"),
        ValueLayout.JAVA_LONG.withName("modified_count"),
    ).withName("lore_layer_remove_event_data_t") as StructLayout

    /**
     * Path in the outer repository where the layer was placed.
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Identifier of the source repository.
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Path inside the source repository where the layer started.
     */
    const val OFFSET_source_path: Long = 32L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Revision of the source repository.
     */
    const val OFFSET_revision: Long = 48L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Set when removal was forced.
     */
    const val OFFSET_forced: Long = 80L
    fun forced(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_forced)
    fun forced(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_forced, value)
    }

    /**
     * Set when the layer files were purged from disk.
     */
    const val OFFSET_purged: Long = 81L
    fun purged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_purged)
    fun purged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_purged, value)
    }

    /**
     * Number of files removed.
     */
    const val OFFSET_file_count: Long = 88L
    fun file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_count)
    fun file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_count, value)
    }

    /**
     * Number of directories removed.
     */
    const val OFFSET_directory_count: Long = 96L
    fun directory_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_count)
    fun directory_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_count, value)
    }

    /**
     * Number of modified files encountered.
     */
    const val OFFSET_modified_count: Long = 104L
    fun modified_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_modified_count)
    fun modified_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_modified_count, value)
    }
}

/**
 * Data for the event describing a layer that has staged changes.
 */
object lore_layer_staged_entry_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("target_path"),
        lore_partition_t.LAYOUT.withName("source_repository"),
        ValueLayout.JAVA_LONG.withName("staged_file_count"),
    ).withName("lore_layer_staged_entry_event_data_t") as StructLayout

    /**
     * Path in the outer repository where the layer is placed.
     */
    const val OFFSET_target_path: Long = 0L
    fun target_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_path, 16L)

    /**
     * Identifier of the source repository.
     */
    const val OFFSET_source_repository: Long = 16L
    fun source_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_repository, 16L)

    /**
     * Number of staged files in the layer.
     */
    const val OFFSET_staged_file_count: Long = 32L
    fun staged_file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_staged_file_count)
    fun staged_file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_staged_file_count, value)
    }
}

/**
 * Arguments for adding a new link to a linked repository at the given path.
 */
object lore_link_add_args_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link"),
        lore_string_t.LAYOUT.withName("link_path"),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_string_t.LAYOUT.withName("pin"),
        ValueLayout.JAVA_BYTE.withName("disable_branching"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_link_add_args_t") as StructLayout

    /**
     * Link repository URL
     */
    const val OFFSET_link: Long = 0L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Path within this repository where the link is added
     */
    const val OFFSET_link_path: Long = 16L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)

    /**
     * Source path within the linked repository; `/` or `\` means the root
     */
    const val OFFSET_source_path: Long = 32L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Branch or revision to set the link pin at
     */
    const val OFFSET_pin: Long = 48L
    fun pin(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_pin, 16L)

    /**
     * Disable automatic branch creation in the linked repository
     */
    const val OFFSET_disable_branching: Long = 64L
    fun disable_branching(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_disable_branching)
    fun disable_branching(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_disable_branching, value)
    }
}

/**
 * Data for an event reporting a change to a link.
 */
object lore_link_change_event_data_t {
    const val SIZE: Long = 88L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link_path"),
        lore_partition_t.LAYOUT.withName("link_repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_link_change_event_data_t") as StructLayout

    /**
     * Path of the link within the parent repository.
     */
    const val OFFSET_link_path: Long = 0L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)

    /**
     * Identifier of the repository the link points to.
     */
    const val OFFSET_link_repository: Long = 16L
    fun link_repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_repository, 16L)

    /**
     * Identifier of the branch the link is pinned to.
     */
    const val OFFSET_branch: Long = 32L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Hash of the revision the link is pinned to.
     */
    const val OFFSET_revision: Long = 48L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Kind of change applied to the link.
     */
    const val OFFSET_action: Long = 80L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }
}

/**
 * Data for an event describing a single link in a repository.
 */
object lore_link_entry_event_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("link"),
        ValueLayout.JAVA_INT.withName("link_node"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("link_path"),
        ValueLayout.JAVA_INT.withName("source_node"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("source_path"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("branch_name"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_INT.withName("flags"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_link_entry_event_data_t") as StructLayout

    /**
     * Identifier of the repository the link points to.
     */
    const val OFFSET_link: Long = 0L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Identifier of the link node in the parent repository.
     */
    const val OFFSET_link_node: Long = 16L
    fun link_node(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_link_node)
    fun link_node(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_link_node, value)
    }

    /**
     * Path of the link within the parent repository.
     */
    const val OFFSET_link_path: Long = 24L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)

    /**
     * Identifier of the source node in the linked repository.
     */
    const val OFFSET_source_node: Long = 40L
    fun source_node(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_source_node)
    fun source_node(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_source_node, value)
    }

    /**
     * Path of the source within the linked repository.
     */
    const val OFFSET_source_path: Long = 48L
    fun source_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_path, 16L)

    /**
     * Identifier of the branch the link is pinned to.
     */
    const val OFFSET_branch: Long = 64L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Name of the branch the link is pinned to.
     */
    const val OFFSET_branch_name: Long = 80L
    fun branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_name, 16L)

    /**
     * Hash of the revision the link is pinned to.
     */
    const val OFFSET_revision: Long = 96L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Link flags.
     */
    const val OFFSET_flags: Long = 128L
    fun flags(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_flags, value)
    }
}

/**
 * Arguments for listing all linked repositories in the current repository.
 */
object lore_link_list_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_link_list_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for removing a link from the repository at the given path.
 */
object lore_link_remove_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link_path"),
    ).withName("lore_link_remove_args_t") as StructLayout

    /**
     * Path within this repository where the link is removed
     */
    const val OFFSET_link_path: Long = 0L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)
}

/**
 * Data for an event describing a link that has staged changes.
 */
object lore_link_staged_entry_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_partition_t.LAYOUT.withName("repository"),
        ValueLayout.JAVA_LONG.withName("staged_file_count"),
    ).withName("lore_link_staged_entry_event_data_t") as StructLayout

    /**
     * Path of the link within the parent repository.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Identifier of the repository the link points to.
     */
    const val OFFSET_repository: Long = 16L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Number of staged files inside the link.
     */
    const val OFFSET_staged_file_count: Long = 32L
    fun staged_file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_staged_file_count)
    fun staged_file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_staged_file_count, value)
    }
}

/**
 * Arguments for updating the pin or properties of an existing link.
 */
object lore_link_update_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("link_path"),
        lore_string_t.LAYOUT.withName("pin"),
    ).withName("lore_link_update_args_t") as StructLayout

    /**
     * Path within this repository of the link to update
     */
    const val OFFSET_link_path: Long = 0L
    fun link_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_path, 16L)

    /**
     * Branch or specific revision to pin the link to
     */
    const val OFFSET_pin: Long = 16L
    fun pin(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_pin, 16L)
}

/**
 * Arguments for acquiring file locks on the given paths for a branch.
 */
object lore_lock_file_acquire_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_lock_file_acquire_args_t") as StructLayout

    /**
     * Paths to acquire locks on
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Branch the locks are acquired on
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Data for an event that marks the start of a lock acquire report.
 */
object lore_lock_file_acquire_begin_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
        ValueLayout.JAVA_BYTE.withName("ignored"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_lock_file_acquire_begin_event_data_t") as StructLayout

    /**
     * Number of acquire entries that follow.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }

    /**
     * Whether the entries that follow were already owned.
     */
    const val OFFSET_ignored: Long = 8L
    fun ignored(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_ignored)
    fun ignored(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_ignored, value)
    }
}

/**
 * Data for an event reporting a path whose lock is being acquired.
 */
object lore_lock_file_acquire_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_lock_file_acquire_event_data_t") as StructLayout

    /**
     * The path whose lock is being acquired.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for querying file locks on a branch, optionally filtered by owner and path.
 */
object lore_lock_file_query_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("owner"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_lock_file_query_args_t") as StructLayout

    /**
     * Branch to query locks on
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Owner filter; empty matches any owner
     */
    const val OFFSET_owner: Long = 16L
    fun owner(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_owner, 16L)

    /**
     * Path filter; empty matches any path
     */
    const val OFFSET_path: Long = 32L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for an event that marks the start of a lock query result.
 */
object lore_lock_file_query_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_lock_file_query_begin_event_data_t") as StructLayout

    /**
     * Number of query entries that follow.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for an event reporting a single lock matched by a query.
 */
object lore_lock_file_query_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("owner"),
        ValueLayout.JAVA_LONG.withName("locked_at"),
    ).withName("lore_lock_file_query_event_data_t") as StructLayout

    /**
     * Identifier of the branch the lock belongs to.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Path the lock applies to.
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Identifier of the user that holds the lock.
     */
    const val OFFSET_owner: Long = 32L
    fun owner(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_owner, 16L)

    /**
     * Timestamp recorded when the lock was acquired.
     */
    const val OFFSET_locked_at: Long = 48L
    fun locked_at(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_locked_at)
    fun locked_at(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_locked_at, value)
    }
}

/**
 * Arguments for releasing file locks on the given paths for a branch and owner.
 */
object lore_lock_file_release_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("owner"),
        lore_string_t.LAYOUT.withName("owner_id"),
    ).withName("lore_lock_file_release_args_t") as StructLayout

    /**
     * Paths to release locks on
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Branch the locks were acquired on
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Owner of the lock
     */
    const val OFFSET_owner: Long = 32L
    fun owner(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_owner, 16L)

    /**
     * Owner id of the lock
     */
    const val OFFSET_owner_id: Long = 48L
    fun owner_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_owner_id, 16L)
}

/**
 * Data for an event that marks the start of a lock release report.
 */
object lore_lock_file_release_begin_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
        ValueLayout.JAVA_BYTE.withName("not_found"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_lock_file_release_begin_event_data_t") as StructLayout

    /**
     * Number of release entries that follow.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }

    /**
     * Whether no matching lock was found to release.
     */
    const val OFFSET_not_found: Long = 8L
    fun not_found(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_not_found)
    fun not_found(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_not_found, value)
    }
}

/**
 * Data for an event reporting a path whose lock is being released.
 */
object lore_lock_file_release_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_lock_file_release_event_data_t") as StructLayout

    /**
     * The path whose lock is being released.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for returning the lock status of the given files on a branch.
 */
object lore_lock_file_status_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_string_t.LAYOUT.withName("branch"),
    ).withName("lore_lock_file_status_args_t") as StructLayout

    /**
     * Paths to get the lock status of
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Branch the locks were acquired on
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Data for an event that marks the start of a lock status report.
 */
object lore_lock_file_status_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_lock_file_status_begin_event_data_t") as StructLayout

    /**
     * Number of status entries that follow.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for an event reporting the lock status of a single path.
 */
object lore_lock_file_status_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("owner"),
        ValueLayout.JAVA_LONG.withName("locked_at"),
    ).withName("lore_lock_file_status_event_data_t") as StructLayout

    /**
     * Path the status applies to.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Identifier of the user that holds the lock.
     */
    const val OFFSET_owner: Long = 16L
    fun owner(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_owner, 16L)

    /**
     * Timestamp recorded when the lock was acquired.
     */
    const val OFFSET_locked_at: Long = 32L
    fun locked_at(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_locked_at)
    fun locked_at(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_locked_at, value)
    }
}

/**
 * Configuration controlling Lore's file and event logging.
 */
object lore_log_config_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("file"),
        ValueLayout.JAVA_BYTE.withName("file_rolling"),
        MemoryLayout.paddingLayout(6),
        lore_string_t.LAYOUT.withName("file_path"),
        lore_string_t.LAYOUT.withName("file_prefix"),
        ValueLayout.JAVA_INT.withName("level"),
        ValueLayout.JAVA_INT.withName("categories"),
        ValueLayout.JAVA_INT.withName("file_max_size"),
        ValueLayout.JAVA_INT.withName("file_max_count"),
    ).withName("lore_log_config_t") as StructLayout

    /**
     * Enable logging to a file (disabled by default)
     */
    const val OFFSET_file: Long = 0L
    fun file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_file)
    fun file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_file, value)
    }

    /**
     * Enable daily rolling logfile
     */
    const val OFFSET_file_rolling: Long = 1L
    fun file_rolling(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_file_rolling)
    fun file_rolling(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_file_rolling, value)
    }

    /**
     * Path to the log file
     */
    const val OFFSET_file_path: Long = 8L
    fun file_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_file_path, 16L)

    /**
     * Prefix for log files
     */
    const val OFFSET_file_prefix: Long = 24L
    fun file_prefix(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_file_prefix, 16L)

    /**
     * Minimum log level
     */
    const val OFFSET_level: Long = 40L
    fun level(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_level)
    fun level(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_level, value)
    }

    /**
     * Log categories bitflags (local, remote, transport)
     */
    const val OFFSET_categories: Long = 44L
    fun categories(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_categories)
    fun categories(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_categories, value)
    }

    /**
     * Maximum log file size
     */
    const val OFFSET_file_max_size: Long = 48L
    fun file_max_size(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_file_max_size)
    fun file_max_size(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_file_max_size, value)
    }

    /**
     * Maximum log file count
     */
    const val OFFSET_file_max_count: Long = 52L
    fun file_max_count(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_file_max_count)
    fun file_max_count(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_file_max_count, value)
    }
}

/**
 * Data for a log event.
 */
object lore_log_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("level"),
        ValueLayout.JAVA_INT.withName("category"),
        ValueLayout.JAVA_LONG.withName("timestamp"),
        lore_string_t.LAYOUT.withName("location"),
        lore_string_t.LAYOUT.withName("message"),
    ).withName("lore_log_event_data_t") as StructLayout

    /**
     * The severity level of the log message.
     */
    const val OFFSET_level: Long = 0L
    fun level(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_level)
    fun level(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_level, value)
    }

    /**
     * The category of the log message.
     */
    const val OFFSET_category: Long = 4L
    fun category(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_category)
    fun category(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_category, value)
    }

    /**
     * The time the message was produced.
     */
    const val OFFSET_timestamp: Long = 8L
    fun timestamp(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_timestamp)
    fun timestamp(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_timestamp, value)
    }

    /**
     * The source location that produced the message.
     */
    const val OFFSET_location: Long = 16L
    fun location(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_location, 16L)

    /**
     * The log message text.
     */
    const val OFFSET_message: Long = 32L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)
}

/**
 * Data for a maintenance event, carrying an informational message.
 */
object lore_maintenance_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("message"),
    ).withName("lore_maintenance_event_data_t") as StructLayout

    /**
     * The maintenance message text.
     */
    const val OFFSET_message: Long = 0L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)
}

/**
 * Data for an event reporting that a file's metadata was cleared.
 */
object lore_metadata_clear_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_metadata_clear_file_event_data_t") as StructLayout

    /**
     * Path of the file whose metadata was cleared.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for an event reporting that revision metadata was cleared.
 */
object lore_metadata_clear_revision_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_metadata_clear_revision_event_data_t") as StructLayout

    /**
     * Hash of the revision whose metadata was cleared.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Data for a metadata event, carrying a single key and value.
 */
object lore_metadata_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
        lore_metadata_t.LAYOUT.withName("value"),
    ).withName("lore_metadata_event_data_t") as StructLayout

    /**
     * The metadata key.
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * The metadata value.
     */
    const val OFFSET_value: Long = 16L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 56L)
}

object lore_metadata_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("tag"),
        MemoryLayout.paddingLayout(4),
        MemoryLayout.unionLayout(
        lore_address_t.LAYOUT.withName("address"),
        MemoryLayout.structLayout(ValueLayout.JAVA_BYTE.withName("boolean"), MemoryLayout.paddingLayout(47)).withName("boolean"),
        MemoryLayout.structLayout(lore_binary_t.LAYOUT.withName("binary"), MemoryLayout.paddingLayout(32)).withName("binary"),
        MemoryLayout.structLayout(lore_context_t.LAYOUT.withName("context"), MemoryLayout.paddingLayout(32)).withName("context"),
        MemoryLayout.structLayout(lore_hash_t.LAYOUT.withName("hash"), MemoryLayout.paddingLayout(16)).withName("hash"),
        MemoryLayout.structLayout(ValueLayout.JAVA_LONG.withName("numeric"), MemoryLayout.paddingLayout(40)).withName("numeric"),
        MemoryLayout.structLayout(lore_string_t.LAYOUT.withName("string"), MemoryLayout.paddingLayout(32)).withName("string"),
    ).withName("union"),
    ).withName("lore_metadata_t") as StructLayout

    const val OFFSET_tag: Long = 0L
    fun tag(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_tag)
    fun tag(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_tag, value)
    }

    const val OFFSET_union: Long = 8L
    fun union(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_union, 48L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_metadata_type_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_metadata_type_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Data for a notification that a branch was created.
 */
object lore_notification_branch_created_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
    ).withName("lore_notification_branch_created_event_data_t") as StructLayout

    /**
     * Identifier of the created branch.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Data for a notification that a branch was deleted.
 */
object lore_notification_branch_deleted_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
    ).withName("lore_notification_branch_deleted_event_data_t") as StructLayout

    /**
     * Identifier of the deleted branch.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Data for a notification that a branch received a new revision.
 */
object lore_notification_branch_pushed_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("user_id"),
    ).withName("lore_notification_branch_pushed_event_data_t") as StructLayout

    /**
     * Hash of the pushed revision.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Sequence number of the pushed revision.
     */
    const val OFFSET_revision_number: Long = 32L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Identifier of the branch that received the revision.
     */
    const val OFFSET_branch: Long = 40L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Identifier of the user that pushed the revision.
     */
    const val OFFSET_user_id: Long = 56L
    fun user_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_id, 16L)
}

/**
 * Data for a notification that resources were locked.
 */
object lore_notification_resource_locked_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("user_id"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_notification_resource_locked_event_data_t") as StructLayout

    /**
     * Identifier of the user that locked the resources.
     */
    const val OFFSET_user_id: Long = 0L
    fun user_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_id, 16L)

    /**
     * Identifier of the branch the resources belong to.
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Paths of the locked resources.
     */
    const val OFFSET_paths: Long = 32L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Data for a notification that resources were unlocked.
 */
object lore_notification_resource_unlocked_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("user_id"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_notification_resource_unlocked_event_data_t") as StructLayout

    /**
     * Identifier of the user that unlocked the resources.
     */
    const val OFFSET_user_id: Long = 0L
    fun user_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_user_id, 16L)

    /**
     * Identifier of the branch the resources belong to.
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Paths of the unlocked resources.
     */
    const val OFFSET_paths: Long = 32L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for subscribing to repository notifications (no parameters).
 */
object lore_notification_subscribe_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_notification_subscribe_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for a notification that a subscription to a repository was established.
 */
object lore_notification_subscribed_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
    ).withName("lore_notification_subscribed_event_data_t") as StructLayout

    /**
     * Identifier of the subscribed repository.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)
}

/**
 * Arguments for unsubscribing from repository notifications (no parameters).
 */
object lore_notification_unsubscribe_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_notification_unsubscribe_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for a notification that a subscription to a repository was removed.
 */
object lore_notification_unsubscribed_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
    ).withName("lore_notification_unsubscribed_event_data_t") as StructLayout

    /**
     * Identifier of the unsubscribed repository.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)
}

/**
 * Opaque 128-bit partition identifier.
 * 
 * Binary-compatible with `Context`. In the Lore domain, a `Partition` represents
 * a repository identifier; the storage layer uses it to segregate data without
 * understanding what the partition represents.
 */
object lore_partition_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE).withName("data"),
    ).withName("lore_partition_t") as StructLayout

    /**
     * The raw 16 bytes of the identifier.
     */
    const val OFFSET_data: Long = 0L
    fun data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_data, 16L)
}

/**
 * Event data naming a path that was ignored or could not be resolved.
 */
object lore_path_ignore_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_path_ignore_event_data_t") as StructLayout

    /**
     * The ignored path
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Data for a generic progress event.
 */
object lore_progress_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_progress_event_data_t") as StructLayout

    /**
     * Placeholder field; carries no meaningful value.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for cloning a remote repository to the local path.
 */
object lore_repository_clone_args_t {
    const val SIZE: Long = 176L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("repository_url"),
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("view"),
        ValueLayout.JAVA_BYTE.withName("bare"),
        ValueLayout.JAVA_BYTE.withName("virtually"),
        ValueLayout.JAVA_BYTE.withName("direct_file_write"),
        ValueLayout.JAVA_BYTE.withName("direct_file_io"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("layer"),
        lore_string_t.LAYOUT.withName("layer_metadata"),
        lore_string_t.LAYOUT.withName("prefetch"),
        ValueLayout.JAVA_BYTE.withName("use_shared_store"),
        MemoryLayout.paddingLayout(7),
        lore_string_t.LAYOUT.withName("shared_store_path"),
        ValueLayout.JAVA_BYTE.withName("no_tracking"),
        MemoryLayout.paddingLayout(7),
        lore_string_array_t.LAYOUT.withName("root_files"),
        lore_string_array_t.LAYOUT.withName("dependency_tags"),
        ValueLayout.JAVA_BYTE.withName("dependency_recursive"),
        MemoryLayout.paddingLayout(3),
        ValueLayout.JAVA_INT.withName("dependency_depth_limit"),
    ).withName("lore_repository_clone_args_t") as StructLayout

    /**
     * URL to the repository
     */
    const val OFFSET_repository_url: Long = 0L
    fun repository_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository_url, 16L)

    /**
     * [Optional] Revision to clone
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * [Optional] Client side view filter to use
     */
    const val OFFSET_view: Long = 32L
    fun view(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_view, 16L)

    /**
     * Clone without any files
     */
    const val OFFSET_bare: Long = 48L
    fun bare(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_bare)
    fun bare(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_bare, value)
    }

    /**
     * Clone virtually using split-write filesystem
     */
    const val OFFSET_virtually: Long = 49L
    fun virtually(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_virtually)
    fun virtually(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_virtually, value)
    }

    /**
     * Use direct file write
     */
    const val OFFSET_direct_file_write: Long = 50L
    fun direct_file_write(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_direct_file_write)
    fun direct_file_write(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_direct_file_write, value)
    }

    /**
     * Use direct file I/O instead of memory mapping files
     */
    const val OFFSET_direct_file_io: Long = 51L
    fun direct_file_io(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_direct_file_io)
    fun direct_file_io(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_direct_file_io, value)
    }

    /**
     * (Optional) Layer module
     */
    const val OFFSET_layer: Long = 56L
    fun layer(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_layer, 16L)

    /**
     * (Optional) Layer metadata key to link revisions with
     */
    const val OFFSET_layer_metadata: Long = 72L
    fun layer_metadata(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_layer_metadata, 16L)

    /**
     * (Optional) File containing list of files to prefetch
     */
    const val OFFSET_prefetch: Long = 88L
    fun prefetch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_prefetch, 16L)

    /**
     * Use the shared store instead of a local immutable store
     */
    const val OFFSET_use_shared_store: Long = 104L
    fun use_shared_store(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_use_shared_store)
    fun use_shared_store(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_use_shared_store, value)
    }

    /**
     * [Optional] Path to use for the shared store, an empty string means to use the default
     */
    const val OFFSET_shared_store_path: Long = 112L
    fun shared_store_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_shared_store_path, 16L)

    /**
     * Clone without local repository tracking (memory-only stores)
     */
    const val OFFSET_no_tracking: Long = 128L
    fun no_tracking(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_tracking)
    fun no_tracking(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_tracking, value)
    }

    /**
     * Root files for dependency-based selective clone
     */
    const val OFFSET_root_files: Long = 136L
    fun root_files(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_root_files, 16L)

    /**
     * Tags to filter dependencies by during resolution
     */
    const val OFFSET_dependency_tags: Long = 152L
    fun dependency_tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependency_tags, 16L)

    /**
     * Follow transitive dependencies recursively
     */
    const val OFFSET_dependency_recursive: Long = 168L
    fun dependency_recursive(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_dependency_recursive)
    fun dependency_recursive(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_dependency_recursive, value)
    }

    /**
     * Maximum dependency traversal depth. 0 means unlimited.
     */
    const val OFFSET_dependency_depth_limit: Long = 172L
    fun dependency_depth_limit(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_dependency_depth_limit)
    fun dependency_depth_limit(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_dependency_depth_limit, value)
    }
}

/**
 * Data for the event emitted when a clone starts.
 */
object lore_repository_clone_begin_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_string_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_repository_clone_begin_event_data_t") as StructLayout

    /**
     * Identifier of the repository being cloned.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Name of the branch being cloned.
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Revision being cloned.
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Local path the clone is written to.
     */
    const val OFFSET_path: Long = 64L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Progress counts for a clone operation.
 */
object lore_repository_clone_count_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("file_complete"),
        ValueLayout.JAVA_LONG.withName("file_retain"),
        ValueLayout.JAVA_LONG.withName("file_replace"),
        ValueLayout.JAVA_LONG.withName("file_count"),
        ValueLayout.JAVA_LONG.withName("file_inflight"),
        ValueLayout.JAVA_LONG.withName("fragment_inflight"),
        ValueLayout.JAVA_LONG.withName("bytes_transferred"),
        ValueLayout.JAVA_LONG.withName("bytes_total"),
        ValueLayout.JAVA_BYTE.withName("discovery_complete"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_repository_clone_count_data_t") as StructLayout

    /**
     * Number of files finished.
     */
    const val OFFSET_file_complete: Long = 0L
    fun file_complete(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_complete)
    fun file_complete(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_complete, value)
    }

    /**
     * Number of files kept as they already matched.
     */
    const val OFFSET_file_retain: Long = 8L
    fun file_retain(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_retain)
    fun file_retain(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_retain, value)
    }

    /**
     * Number of files replaced.
     */
    const val OFFSET_file_replace: Long = 16L
    fun file_replace(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_replace)
    fun file_replace(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_replace, value)
    }

    /**
     * Total number of files discovered to process.
     */
    const val OFFSET_file_count: Long = 24L
    fun file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_count)
    fun file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_count, value)
    }

    /**
     * Number of files currently being processed.
     */
    const val OFFSET_file_inflight: Long = 32L
    fun file_inflight(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_inflight)
    fun file_inflight(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_inflight, value)
    }

    /**
     * Number of fragment fetches currently in flight.
     */
    const val OFFSET_fragment_inflight: Long = 40L
    fun fragment_inflight(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragment_inflight)
    fun fragment_inflight(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragment_inflight, value)
    }

    /**
     * Number of bytes transferred so far.
     */
    const val OFFSET_bytes_transferred: Long = 48L
    fun bytes_transferred(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred)
    fun bytes_transferred(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred, value)
    }

    /**
     * Total number of bytes to transfer.
     */
    const val OFFSET_bytes_total: Long = 56L
    fun bytes_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_total)
    fun bytes_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_total, value)
    }

    /**
     * Non-zero once file discovery has finished.
     */
    const val OFFSET_discovery_complete: Long = 64L
    fun discovery_complete(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete)
    fun discovery_complete(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete, value)
    }
}

/**
 * Data for the event emitted when a clone finishes.
 */
object lore_repository_clone_end_event_data_t {
    const val SIZE: Long = 120L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        lore_repository_clone_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_repository_clone_end_event_data_t") as StructLayout

    /**
     * Name of the branch that was cloned.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Revision that was cloned.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Final progress counts.
     */
    const val OFFSET_count: Long = 48L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 72L)
}

/**
 * Data for the event emitted to report clone progress.
 */
object lore_repository_clone_progress_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_repository_clone_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_repository_clone_progress_event_data_t") as StructLayout

    /**
     * Current progress counts.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 72L)
}

/**
 * Arguments for reading a value from the repository config.
 */
object lore_repository_config_get_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
    ).withName("lore_repository_config_get_args_t") as StructLayout

    /**
     * Config key to read (`remote_url` or `identity`)
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)
}

/**
 * Data for the event emitted when a repository configuration value is read.
 */
object lore_repository_config_get_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
        lore_string_t.LAYOUT.withName("value"),
    ).withName("lore_repository_config_get_event_data_t") as StructLayout

    /**
     * Configuration key.
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * Configuration value for the key.
     */
    const val OFFSET_value: Long = 16L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 16L)
}

/**
 * Arguments for creating a new repository at the specified URL.
 */
object lore_repository_create_args_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("repository_url"),
        lore_string_t.LAYOUT.withName("description"),
        lore_string_t.LAYOUT.withName("id"),
        ValueLayout.JAVA_BYTE.withName("use_shared_store"),
        MemoryLayout.paddingLayout(7),
        lore_string_t.LAYOUT.withName("shared_store_path"),
    ).withName("lore_repository_create_args_t") as StructLayout

    /**
     * URL to the repository
     */
    const val OFFSET_repository_url: Long = 0L
    fun repository_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository_url, 16L)

    /**
     * Optional repository description
     */
    const val OFFSET_description: Long = 16L
    fun description(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_description, 16L)

    /**
     * Optional repository ID, set to empty string to generate a new ID
     */
    const val OFFSET_id: Long = 32L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Use the shared store instead of a local immutable store
     */
    const val OFFSET_use_shared_store: Long = 48L
    fun use_shared_store(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_use_shared_store)
    fun use_shared_store(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_use_shared_store, value)
    }

    /**
     * [Optional] Path to use for the shared store, an empty string means to use the default
     */
    const val OFFSET_shared_store_path: Long = 56L
    fun shared_store_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_shared_store_path, 16L)
}

/**
 * Data for the event emitted when a repository is created.
 */
object lore_repository_create_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_repository_create_event_data_t") as StructLayout

    /**
     * Identifier of the created repository.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Name of the created repository.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Local path of the created repository.
     */
    const val OFFSET_path: Long = 32L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Descriptive data for a repository.
 */
object lore_repository_data_event_data_t {
    const val SIZE: Long = 120L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote_url"),
        lore_partition_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
        lore_string_t.LAYOUT.withName("description"),
        lore_context_t.LAYOUT.withName("default_branch"),
        lore_string_t.LAYOUT.withName("default_branch_name"),
        lore_string_t.LAYOUT.withName("creator"),
        ValueLayout.JAVA_LONG.withName("created"),
    ).withName("lore_repository_data_event_data_t") as StructLayout

    /**
     * Remote URL of the repository.
     */
    const val OFFSET_remote_url: Long = 0L
    fun remote_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_url, 16L)

    /**
     * Repository identifier.
     */
    const val OFFSET_id: Long = 16L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Repository name.
     */
    const val OFFSET_name: Long = 32L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Repository description.
     */
    const val OFFSET_description: Long = 48L
    fun description(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_description, 16L)

    /**
     * Identifier of the default branch.
     */
    const val OFFSET_default_branch: Long = 64L
    fun default_branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_default_branch, 16L)

    /**
     * Name of the default branch.
     */
    const val OFFSET_default_branch_name: Long = 80L
    fun default_branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_default_branch_name, 16L)

    /**
     * Name of the user who created the repository.
     */
    const val OFFSET_creator: Long = 96L
    fun creator(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_creator, 16L)

    /**
     * Creation time of the repository, in seconds since the Unix epoch.
     */
    const val OFFSET_created: Long = 112L
    fun created(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_created)
    fun created(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_created, value)
    }
}

/**
 * Arguments for dumping the internal state tree of the repository.
 */
object lore_repository_dump_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("max_depth"),
    ).withName("lore_repository_dump_args_t") as StructLayout

    /**
     * Revision to dump; empty string uses the current revision
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Repository-relative path to start dumping from; empty dumps the root
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Maximum tree traversal depth
     */
    const val OFFSET_max_depth: Long = 32L
    fun max_depth(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_max_depth)
    fun max_depth(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_max_depth, value)
    }
}

/**
 * Data for the event emitted when a repository dump starts.
 */
object lore_repository_dump_begin_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_repository_dump_begin_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision being dumped.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Data for the event emitted when a repository dump finishes.
 */
object lore_repository_dump_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_dump_end_event_data_t") as StructLayout

    /**
     * Placeholder field. The event carries no data.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for waiting on outstanding asynchronous repository tasks.
 */
object lore_repository_flush_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_flush_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for running garbage collection on the local repository store.
 */
object lore_repository_gc_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_gc_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for retrieving metadata about a remote repository.
 */
object lore_repository_info_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("repository_url"),
    ).withName("lore_repository_info_args_t") as StructLayout

    /**
     * URL of the remote repository to query
     */
    const val OFFSET_repository_url: Long = 0L
    fun repository_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository_url, 16L)
}

/**
 * Event data describing an instance — used for both listing and prune notifications.
 */
object lore_repository_instance_event_data_t {
    const val SIZE: Long = 104L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_instance_id_t.LAYOUT.withName("instance_id"),
        lore_string_t.LAYOUT.withName("path"),
        lore_string_t.LAYOUT.withName("branch_name"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("stale"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_repository_instance_event_data_t") as StructLayout

    /**
     * Identifier of the instance
     */
    const val OFFSET_instance_id: Long = 0L
    fun instance_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_instance_id, 16L)

    /**
     * Filesystem path of the instance
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Name of the branch the instance has checked out
     */
    const val OFFSET_branch_name: Long = 32L
    fun branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_name, 16L)

    /**
     * Identifier of the branch the instance has checked out
     */
    const val OFFSET_branch: Long = 48L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Current revision hash for the instance
     */
    const val OFFSET_revision: Long = 64L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Non-zero if the instance path no longer exists on disk
     */
    const val OFFSET_stale: Long = 96L
    fun stale(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_stale)
    fun stale(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_stale, value)
    }
}

/**
 * Arguments for listing the tracked instances of the repository.
 */
object lore_repository_instance_list_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_instance_list_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for pruning stale instances of the repository.
 */
object lore_repository_instance_prune_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_instance_prune_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for listing all repositories available at a remote URL.
 */
object lore_repository_list_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("url"),
    ).withName("lore_repository_list_args_t") as StructLayout

    /**
     * Remote URL to list repositories from
     */
    const val OFFSET_url: Long = 0L
    fun url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_url, 16L)
}

/**
 * One entry in a repository listing.
 */
object lore_repository_list_entry_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("id"),
        lore_string_t.LAYOUT.withName("name"),
    ).withName("lore_repository_list_entry_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_id, 16L)

    /**
     * Repository name.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)
}

/**
 * Arguments for removing metadata keys from the current repository.
 */
object lore_repository_metadata_clear_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("keys"),
    ).withName("lore_repository_metadata_clear_args_t") as StructLayout

    /**
     * Keys to clear; empty array clears all user-defined keys
     */
    const val OFFSET_keys: Long = 0L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)
}

/**
 * Arguments for retrieving repository metadata.
 */
object lore_repository_metadata_get_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
    ).withName("lore_repository_metadata_get_args_t") as StructLayout

    /**
     * Metadata key to fetch; empty string lists all entries
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)
}

/**
 * Arguments for setting metadata key-value pairs on the current repository.
 */
object lore_repository_metadata_set_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("keys"),
        lore_string_array_t.LAYOUT.withName("values"),
        lore_metadata_type_array_t.LAYOUT.withName("formats"),
    ).withName("lore_repository_metadata_set_args_t") as StructLayout

    /**
     * Metadata keys to set, positionally aligned with `values` and `formats`
     */
    const val OFFSET_keys: Long = 0L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)

    /**
     * Values to set, one per key, encoded per the matching `formats` entry
     */
    const val OFFSET_values: Long = 16L
    fun values(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_values, 16L)

    /**
     * Value format/type for each key-value pair
     */
    const val OFFSET_formats: Long = 32L
    fun formats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_formats, 16L)
}

/**
 * Arguments for releasing cached store references for the repository path.
 */
object lore_repository_release_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_release_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for an event summarizing a dumped repository state.
 */
object lore_repository_state_dump_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("revision_number"),
        lore_hash_t.LAYOUT.withName("revision"),
        lore_hash_t.LAYOUT.withName("tree_hash"),
        ValueLayout.JAVA_LONG.withName("tree_size"),
    ).withName("lore_repository_state_dump_event_data_t") as StructLayout

    /**
     * Sequence number of the revision.
     */
    const val OFFSET_revision_number: Long = 0L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Hash of the revision.
     */
    const val OFFSET_revision: Long = 8L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Hash of the state's node tree.
     */
    const val OFFSET_tree_hash: Long = 40L
    fun tree_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_tree_hash, 32L)

    /**
     * Size of the node tree in bytes.
     */
    const val OFFSET_tree_size: Long = 72L
    fun tree_size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_tree_size)
    fun tree_size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_tree_size, value)
    }
}

/**
 * Data for an event describing a single node in a dumped repository state.
 */
object lore_repository_state_dump_node_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("name"),
        ValueLayout.JAVA_INT.withName("id"),
        ValueLayout.JAVA_INT.withName("parent"),
        ValueLayout.JAVA_INT.withName("sibling"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        MemoryLayout.paddingLayout(2),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_SHORT.withName("flags"),
        MemoryLayout.paddingLayout(6),
        lore_string_t.LAYOUT.withName("type_data"),
    ).withName("lore_repository_state_dump_node_event_data_t") as StructLayout

    /**
     * Name of the node.
     */
    const val OFFSET_name: Long = 0L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * Identifier of the node.
     */
    const val OFFSET_id: Long = 16L
    fun id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_id)
    fun id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_id, value)
    }

    /**
     * Identifier of the parent node.
     */
    const val OFFSET_parent: Long = 20L
    fun parent(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent)
    fun parent(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent, value)
    }

    /**
     * Identifier of the next sibling node.
     */
    const val OFFSET_sibling: Long = 24L
    fun sibling(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_sibling)
    fun sibling(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_sibling, value)
    }

    /**
     * File mode of the node.
     */
    const val OFFSET_mode: Long = 28L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * Size of the node's content in bytes.
     */
    const val OFFSET_size: Long = 32L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Node flags.
     */
    const val OFFSET_flags: Long = 40L
    fun flags(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_flags, value)
    }

    /**
     * Type-specific detail for the node.
     */
    const val OFFSET_type_data: Long = 48L
    fun type_data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_type_data, 16L)
}

/**
 * Arguments for reporting the working directory status.
 */
object lore_repository_status_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("staged"),
        ValueLayout.JAVA_BYTE.withName("scan"),
        ValueLayout.JAVA_BYTE.withName("check_dirty"),
        ValueLayout.JAVA_BYTE.withName("reset"),
        ValueLayout.JAVA_BYTE.withName("sync_point"),
        ValueLayout.JAVA_BYTE.withName("revision_only"),
        ValueLayout.JAVA_BYTE.withName("count"),
        MemoryLayout.paddingLayout(1),
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_repository_status_args_t") as StructLayout

    /**
     * Include staged state in the report
     */
    const val OFFSET_staged: Long = 0L
    fun staged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_staged)
    fun staged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_staged, value)
    }

    /**
     * Reconcile against the filesystem and refresh dirty tracking.
     * 
     * By default, status reports the currently tracked state: the
     * staged revision (if any) plus any files and directories already
     * marked dirty. No filesystem reads are performed beyond the existing
     * dirty flags — clean or unmarked files on disk are not inspected even
     * if they differ from the current revision.
     * 
     * When enabled, the filesystem is walked under each requested path, every
     * file is reconciled against the current revision, and dirty flags are
     * set or cleared accordingly. The refreshed flags are persisted in the
     * staged state so subsequent operations (commit, stage, status) see an
     * accurate picture without rescanning.
     */
    const val OFFSET_scan: Long = 1L
    fun scan(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_scan)
    fun scan(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_scan, value)
    }

    /**
     * Verify dirty flags against the filesystem without a full scan.
     * 
     * When enabled, files already marked dirty are re-examined individually: a
     * dirty file whose on-disk content matches its tracked node (same size,
     * and same content when the modification time differs) has its dirty flag
     * cleared and is omitted from the report, unless it is also staged.
     * Structural dirty actions (add/move/copy/delete) are always reported.
     * The refreshed flags are persisted in the staged state.
     */
    const val OFFSET_check_dirty: Long = 2L
    fun check_dirty(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_check_dirty)
    fun check_dirty(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_check_dirty, value)
    }

    /**
     * Reset the tracked state before computing status
     */
    const val OFFSET_reset: Long = 3L
    fun reset(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reset)
    fun reset(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reset, value)
    }

    /**
     * Include the sync point in the report
     */
    const val OFFSET_sync_point: Long = 4L
    fun sync_point(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_sync_point)
    fun sync_point(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_sync_point, value)
    }

    /**
     * Only emit revision info, skipping all diffs
     */
    const val OFFSET_revision_only: Long = 5L
    fun revision_only(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_revision_only)
    fun revision_only(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_revision_only, value)
    }

    /**
     * Count directories and files (view-filtered) in the staged state if
     * present, otherwise the current revision
     */
    const val OFFSET_count: Long = 6L
    fun count(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_count)
    fun count(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_count, value)
    }

    /**
     * Repository-relative paths to limit the status check to; empty checks all
     */
    const val OFFSET_paths: Long = 8L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Counts of directories and files in the repository tree.
 */
object lore_repository_status_count_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("directories"),
        ValueLayout.JAVA_LONG.withName("files"),
    ).withName("lore_repository_status_count_event_data_t") as StructLayout

    /**
     * Number of directories in the tree, view-filtered (staged state if
     * present, otherwise the current revision)
     */
    const val OFFSET_directories: Long = 0L
    fun directories(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directories)
    fun directories(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directories, value)
    }

    /**
     * Number of files in the tree, view-filtered (staged state if present,
     * otherwise the current revision)
     */
    const val OFFSET_files: Long = 8L
    fun files(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_files)
    fun files(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_files, value)
    }
}

/**
 * Status of a single file or node reported by a repository status operation.
 */
object lore_repository_status_file_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_INT.withName("action"),
        ValueLayout.JAVA_INT.withName("type"),
        ValueLayout.JAVA_BYTE.withName("flag_staged"),
        ValueLayout.JAVA_BYTE.withName("flag_merged"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict_unresolved"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict_automerged"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict_mine"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict_theirs"),
        ValueLayout.JAVA_BYTE.withName("flag_dirty"),
        lore_string_t.LAYOUT.withName("from_path"),
    ).withName("lore_repository_status_file_event_data_t") as StructLayout

    /**
     * Path of the file relative to the repository root.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Size of the file in bytes.
     */
    const val OFFSET_size: Long = 16L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Change applied to the file, such as add, modify, delete, or move.
     */
    const val OFFSET_action: Long = 24L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Kind of node: file, directory, or link.
     */
    const val OFFSET_type: Long = 28L
    fun type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_type)
    fun type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_type, value)
    }

    /**
     * Non-zero when the change is staged.
     */
    const val OFFSET_flag_staged: Long = 32L
    fun flag_staged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_staged)
    fun flag_staged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_staged, value)
    }

    /**
     * Non-zero when the change comes from a merge.
     */
    const val OFFSET_flag_merged: Long = 33L
    fun flag_merged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_merged)
    fun flag_merged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_merged, value)
    }

    /**
     * Non-zero when the file is in conflict.
     */
    const val OFFSET_flag_conflict: Long = 34L
    fun flag_conflict(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict)
    fun flag_conflict(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict, value)
    }

    /**
     * Non-zero when the conflict is not yet resolved.
     */
    const val OFFSET_flag_conflict_unresolved: Long = 35L
    fun flag_conflict_unresolved(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_unresolved)
    fun flag_conflict_unresolved(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_unresolved, value)
    }

    /**
     * Non-zero when the conflict was resolved automatically.
     */
    const val OFFSET_flag_conflict_automerged: Long = 36L
    fun flag_conflict_automerged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_automerged)
    fun flag_conflict_automerged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_automerged, value)
    }

    /**
     * Non-zero when the local side was chosen to resolve the conflict.
     */
    const val OFFSET_flag_conflict_mine: Long = 37L
    fun flag_conflict_mine(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_mine)
    fun flag_conflict_mine(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_mine, value)
    }

    /**
     * Non-zero when the incoming side was chosen to resolve the conflict.
     */
    const val OFFSET_flag_conflict_theirs: Long = 38L
    fun flag_conflict_theirs(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_theirs)
    fun flag_conflict_theirs(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict_theirs, value)
    }

    /**
     * Non-zero when the file differs from the recorded state.
     */
    const val OFFSET_flag_dirty: Long = 39L
    fun flag_dirty(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_dirty)
    fun flag_dirty(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_dirty, value)
    }

    /**
     * Previous path of the file when it was moved or copied. Empty otherwise.
     */
    const val OFFSET_from_path: Long = 40L
    fun from_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_from_path, 16L)
}

/**
 * Revision status of a repository, describing the current, local, and remote
 * positions of the active branch.
 */
object lore_repository_status_revision_event_data_t {
    const val SIZE: Long = 272L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("branch_name"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        lore_hash_t.LAYOUT.withName("revision_staged"),
        lore_hash_t.LAYOUT.withName("revision_merged"),
        lore_hash_t.LAYOUT.withName("revision_merged_parent_branch"),
        lore_hash_t.LAYOUT.withName("revision_local"),
        ValueLayout.JAVA_LONG.withName("revision_local_number"),
        lore_hash_t.LAYOUT.withName("revision_remote"),
        ValueLayout.JAVA_LONG.withName("revision_remote_number"),
        ValueLayout.JAVA_BYTE.withName("is_local_ahead"),
        ValueLayout.JAVA_BYTE.withName("is_remote_ahead"),
        ValueLayout.JAVA_BYTE.withName("remote_available"),
        ValueLayout.JAVA_BYTE.withName("remote_authorized"),
        ValueLayout.JAVA_BYTE.withName("remote_branch_exist"),
        MemoryLayout.paddingLayout(3),
    ).withName("lore_repository_status_revision_event_data_t") as StructLayout

    /**
     * Repository identifier
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Current branch identifier
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Current branch name
     */
    const val OFFSET_branch_name: Long = 32L
    fun branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_name, 16L)

    /**
     * Current revision identifier
     */
    const val OFFSET_revision: Long = 48L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Current revision number
     */
    const val OFFSET_revision_number: Long = 80L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Staged revision identifier (zero when nothing is staged)
     */
    const val OFFSET_revision_staged: Long = 88L
    fun revision_staged(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_staged, 32L)

    /**
     * Incoming revision identifier of a pending merge (zero when none)
     */
    const val OFFSET_revision_merged: Long = 120L
    fun revision_merged(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_merged, 32L)

    /**
     * Last revision merged in from the parent branch (calculated and reported if sync point option is set).
     */
    const val OFFSET_revision_merged_parent_branch: Long = 152L
    fun revision_merged_parent_branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_merged_parent_branch, 32L)

    /**
     * Local branch latest revision identifier
     */
    const val OFFSET_revision_local: Long = 184L
    fun revision_local(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_local, 32L)

    /**
     * Local branch latest revision number
     */
    const val OFFSET_revision_local_number: Long = 216L
    fun revision_local_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_local_number)
    fun revision_local_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_local_number, value)
    }

    /**
     * Remote branch latest revision identifier (zero if unknown, branch not existing on remote or remote not available)
     */
    const val OFFSET_revision_remote: Long = 224L
    fun revision_remote(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_remote, 32L)

    /**
     * Remote branch latest revision number (zero if corresponding identifier is zero)
     */
    const val OFFSET_revision_remote_number: Long = 256L
    fun revision_remote_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_remote_number)
    fun revision_remote_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_remote_number, value)
    }

    /**
     * Local holds revisions not on the remote history line
     */
    const val OFFSET_is_local_ahead: Long = 264L
    fun is_local_ahead(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_local_ahead)
    fun is_local_ahead(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_local_ahead, value)
    }

    /**
     * Remote holds revisions not present locally
     */
    const val OFFSET_is_remote_ahead: Long = 265L
    fun is_remote_ahead(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_remote_ahead)
    fun is_remote_ahead(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_remote_ahead, value)
    }

    /**
     * Remote configured and reachable with a local identity; connectivity only, not authorization
     */
    const val OFFSET_remote_available: Long = 266L
    fun remote_available(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_available)
    fun remote_available(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_available, value)
    }

    /**
     * Remote revision query returned an authoritative answer, identity is authorized to access the repository
     */
    const val OFFSET_remote_authorized: Long = 267L
    fun remote_authorized(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_authorized)
    fun remote_authorized(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_authorized, value)
    }

    /**
     * Branch exists on the remote and the query returned a latest revisoin (possibly zero if branch does not exist on remote)
     */
    const val OFFSET_remote_branch_exist: Long = 268L
    fun remote_branch_exist(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_branch_exist)
    fun remote_branch_exist(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_branch_exist, value)
    }
}

/**
 * Aggregate counts of dirty nodes by action type, emitted once at the end of
 * a reconciling status (`--scan` or `--check-dirty`). For `--scan` these are
 * the changes detected against the filesystem; for `--check-dirty` they are
 * the nodes that remained dirty after the filesystem verification.
 */
object lore_repository_status_summary_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("adds"),
        ValueLayout.JAVA_LONG.withName("deletes"),
        ValueLayout.JAVA_LONG.withName("modifies"),
        ValueLayout.JAVA_LONG.withName("moves"),
        ValueLayout.JAVA_LONG.withName("copies"),
    ).withName("lore_repository_status_summary_event_data_t") as StructLayout

    /**
     * Number of files added.
     */
    const val OFFSET_adds: Long = 0L
    fun adds(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_adds)
    fun adds(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_adds, value)
    }

    /**
     * Number of files deleted.
     */
    const val OFFSET_deletes: Long = 8L
    fun deletes(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_deletes)
    fun deletes(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_deletes, value)
    }

    /**
     * Number of files modified.
     */
    const val OFFSET_modifies: Long = 16L
    fun modifies(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_modifies)
    fun modifies(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_modifies, value)
    }

    /**
     * Number of files moved.
     */
    const val OFFSET_moves: Long = 24L
    fun moves(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_moves)
    fun moves(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_moves, value)
    }

    /**
     * Number of files copied.
     */
    const val OFFSET_copies: Long = 32L
    fun copies(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_copies)
    fun copies(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_copies, value)
    }
}

/**
 * Arguments for querying the local immutable store by fragment address.
 */
object lore_repository_store_immutable_query_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_BYTE.withName("recurse"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_repository_store_immutable_query_args_t") as StructLayout

    /**
     * Fragment address to query
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 16L)

    /**
     * Recurse into and query subfragments
     */
    const val OFFSET_recurse: Long = 16L
    fun recurse(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_recurse)
    fun recurse(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_recurse, value)
    }
}

/**
 * Result of a query against the immutable store for a single fragment.
 */
object lore_repository_store_immutable_query_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_BYTE.withName("remote"),
        MemoryLayout.paddingLayout(3),
        ValueLayout.JAVA_INT.withName("status"),
        ValueLayout.JAVA_BYTE.withName("payload"),
        ValueLayout.JAVA_BYTE.withName("subfragment"),
        MemoryLayout.paddingLayout(2),
        ValueLayout.JAVA_INT.withName("flags"),
        ValueLayout.JAVA_INT.withName("payload_size"),
        MemoryLayout.paddingLayout(4),
        ValueLayout.JAVA_LONG.withName("content_size"),
    ).withName("lore_repository_store_immutable_query_event_data_t") as StructLayout

    /**
     * Address of fragment
     */
    const val OFFSET_address: Long = 0L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Remote flag, true if results are from remote store, false if local store
     */
    const val OFFSET_remote: Long = 48L
    fun remote(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote)
    fun remote(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote, value)
    }

    /**
     * Status, where
     * 0 = exact address exist
     * 1 = hash exist in repository
     * 2 = hash exist in other repository
     * 3 = hash does not exist
     */
    const val OFFSET_status: Long = 52L
    fun status(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_status)
    fun status(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_status, value)
    }

    /**
     * Payload flag, true if payload data is present in the store, false if not
     */
    const val OFFSET_payload: Long = 56L
    fun payload(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_payload)
    fun payload(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_payload, value)
    }

    /**
     * Subfragment flag, true if this fragment was a subfragment of the original query, false if not
     */
    const val OFFSET_subfragment: Long = 57L
    fun subfragment(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_subfragment)
    fun subfragment(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_subfragment, value)
    }

    /**
     * Internal flags
     */
    const val OFFSET_flags: Long = 60L
    fun flags(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_flags, value)
    }

    /**
     * Payload size
     */
    const val OFFSET_payload_size: Long = 64L
    fun payload_size(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_payload_size)
    fun payload_size(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_payload_size, value)
    }

    /**
     * Content size
     */
    const val OFFSET_content_size: Long = 72L
    fun content_size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_content_size)
    fun content_size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_content_size, value)
    }
}

/**
 * Arguments for updating the recorded path of the current repository instance.
 */
object lore_repository_update_path_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_update_path_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Result of verifying a single fragment, including every stored copy found.
 */
object lore_repository_verify_fragment_event_data_t {
    const val SIZE: Long = 104L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("hash"),
        ValueLayout.JAVA_INT.withName("group_index"),
        ValueLayout.JAVA_INT.withName("bucket_index"),
        lore_string_t.LAYOUT.withName("index_path"),
        ValueLayout.JAVA_INT.withName("entry_count"),
        ValueLayout.JAVA_INT.withName("packfile_entry_count"),
        ValueLayout.JAVA_INT.withName("match_count"),
        MemoryLayout.paddingLayout(4),
        lore_repository_verify_fragment_match_event_data_array_t.LAYOUT.withName("matches"),
        lore_string_t.LAYOUT.withName("error"),
    ).withName("lore_repository_verify_fragment_event_data_t") as StructLayout

    /**
     * Hash of the fragment that was verified.
     */
    const val OFFSET_hash: Long = 0L
    fun hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_hash, 32L)

    /**
     * Index of the group the fragment belongs to.
     */
    const val OFFSET_group_index: Long = 32L
    fun group_index(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_group_index)
    fun group_index(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_group_index, value)
    }

    /**
     * Index of the bucket the fragment belongs to.
     */
    const val OFFSET_bucket_index: Long = 36L
    fun bucket_index(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_bucket_index)
    fun bucket_index(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_bucket_index, value)
    }

    /**
     * Path of the index file examined for the fragment.
     */
    const val OFFSET_index_path: Long = 40L
    fun index_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_index_path, 16L)

    /**
     * Number of entries in the index.
     */
    const val OFFSET_entry_count: Long = 56L
    fun entry_count(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_entry_count)
    fun entry_count(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_entry_count, value)
    }

    /**
     * Number of entries in the pack file.
     */
    const val OFFSET_packfile_entry_count: Long = 60L
    fun packfile_entry_count(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_packfile_entry_count)
    fun packfile_entry_count(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_packfile_entry_count, value)
    }

    /**
     * Number of stored copies found for the fragment.
     */
    const val OFFSET_match_count: Long = 64L
    fun match_count(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_match_count)
    fun match_count(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_match_count, value)
    }

    /**
     * The stored copies found for the fragment.
     */
    const val OFFSET_matches: Long = 72L
    fun matches(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_matches, 16L)

    /**
     * Error message produced during verification. Empty on success.
     */
    const val OFFSET_error: Long = 88L
    fun error(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_error, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_repository_verify_fragment_match_event_data_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_repository_verify_fragment_match_event_data_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * One stored copy of a fragment found during fragment verification.
 */
object lore_repository_verify_fragment_match_event_data_t {
    const val SIZE: Long = 104L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("slot"),
        ValueLayout.JAVA_INT.withName("index"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("address_hash"),
        lore_context_t.LAYOUT.withName("address_context"),
        ValueLayout.JAVA_INT.withName("flags"),
        ValueLayout.JAVA_INT.withName("size_payload"),
        ValueLayout.JAVA_LONG.withName("size_content"),
        ValueLayout.JAVA_INT.withName("pack_offset"),
        ValueLayout.JAVA_INT.withName("pack_file"),
        ValueLayout.JAVA_LONG.withName("last_access"),
    ).withName("lore_repository_verify_fragment_match_event_data_t") as StructLayout

    /**
     * Slot the match was found in.
     */
    const val OFFSET_slot: Long = 0L
    fun slot(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_slot)
    fun slot(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_slot, value)
    }

    /**
     * Index of the match within the slot.
     */
    const val OFFSET_index: Long = 4L
    fun index(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_index)
    fun index(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_index, value)
    }

    /**
     * Identifier of the repository the match belongs to.
     */
    const val OFFSET_repository: Long = 8L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Hash part of the fragment address.
     */
    const val OFFSET_address_hash: Long = 24L
    fun address_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address_hash, 32L)

    /**
     * Context part of the fragment address.
     */
    const val OFFSET_address_context: Long = 56L
    fun address_context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address_context, 16L)

    /**
     * Storage flags recorded for the fragment.
     */
    const val OFFSET_flags: Long = 72L
    fun flags(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_flags)
    fun flags(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_flags, value)
    }

    /**
     * Stored size of the fragment payload in bytes.
     */
    const val OFFSET_size_payload: Long = 76L
    fun size_payload(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_size_payload)
    fun size_payload(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_size_payload, value)
    }

    /**
     * Size of the fragment content in bytes.
     */
    const val OFFSET_size_content: Long = 80L
    fun size_content(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size_content)
    fun size_content(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size_content, value)
    }

    /**
     * Offset of the fragment within its pack file.
     */
    const val OFFSET_pack_offset: Long = 88L
    fun pack_offset(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_pack_offset)
    fun pack_offset(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_pack_offset, value)
    }

    /**
     * Index of the pack file holding the fragment.
     */
    const val OFFSET_pack_file: Long = 92L
    fun pack_file(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_pack_file)
    fun pack_file(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_pack_file, value)
    }

    /**
     * Time the fragment was last accessed, in seconds since the Unix epoch.
     */
    const val OFFSET_last_access: Long = 96L
    fun last_access(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_last_access)
    fun last_access(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_last_access, value)
    }
}

/**
 * Result of verifying a single fragment on the remote.
 */
object lore_repository_verify_fragment_remote_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("address_hash"),
        lore_context_t.LAYOUT.withName("address_context"),
        ValueLayout.JAVA_BYTE.withName("corrupted"),
        ValueLayout.JAVA_BYTE.withName("healed"),
        MemoryLayout.paddingLayout(6),
        lore_string_t.LAYOUT.withName("error"),
    ).withName("lore_repository_verify_fragment_remote_event_data_t") as StructLayout

    /**
     * Hash part of the fragment address.
     */
    const val OFFSET_address_hash: Long = 0L
    fun address_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address_hash, 32L)

    /**
     * Context part of the fragment address.
     */
    const val OFFSET_address_context: Long = 32L
    fun address_context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address_context, 16L)

    /**
     * Non-zero when the fragment was found to be corrupted.
     */
    const val OFFSET_corrupted: Long = 48L
    fun corrupted(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_corrupted)
    fun corrupted(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_corrupted, value)
    }

    /**
     * Non-zero when the fragment was healed.
     */
    const val OFFSET_healed: Long = 49L
    fun healed(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_healed)
    fun healed(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_healed, value)
    }

    /**
     * Error message produced during verification. Empty on success.
     */
    const val OFFSET_error: Long = 56L
    fun error(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_error, 16L)
}

/**
 * Arguments for verifying the integrity of the local repository state.
 */
object lore_repository_verify_state_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("heal"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_repository_verify_state_args_t") as StructLayout

    /**
     * Repository-relative path to verify; empty verifies the whole repository
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Heal detected inconsistencies
     */
    const val OFFSET_heal: Long = 16L
    fun heal(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_heal)
    fun heal(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_heal, value)
    }
}

/**
 * Data for the event emitted when state verification starts.
 */
object lore_repository_verify_state_begin_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_repository_verify_state_begin_event_data_t") as StructLayout

    /**
     * Placeholder field. The event carries no data.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for the event emitted when state verification finishes.
 */
object lore_repository_verify_state_end_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("healed_staged_state"),
    ).withName("lore_repository_verify_state_end_event_data_t") as StructLayout

    /**
     * Identifier of the staged state after healing. Zero when nothing was healed.
     */
    const val OFFSET_healed_staged_state: Long = 0L
    fun healed_staged_state(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_healed_staged_state, 32L)
}

/**
 * Event data reported at the start of aborting a revert.
 */
object lore_revert_abort_begin_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("state_staged_revision"),
        lore_hash_t.LAYOUT.withName("state_current_revision"),
    ).withName("lore_revert_abort_begin_event_data_t") as StructLayout

    /**
     * Identifier of the staged revision being discarded.
     */
    const val OFFSET_state_staged_revision: Long = 0L
    fun state_staged_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_staged_revision, 32L)

    /**
     * Identifier of the current revision being restored.
     */
    const val OFFSET_state_current_revision: Long = 32L
    fun state_current_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_state_current_revision, 32L)
}

/**
 * Event data reported at the end of aborting a revert.
 */
object lore_revert_abort_end_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_revert_abort_end_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Event data reported for a file in conflict during a revert.
 */
object lore_revert_conflict_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_revert_conflict_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a file is resolved during a revert.
 */
object lore_revert_resolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_revert_resolve_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a revision is resolved during a revert.
 */
object lore_revert_resolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_revert_resolve_revision_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the revision.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Event data reported at the start of a revert.
 */
object lore_revert_start_begin_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
    ).withName("lore_revert_start_begin_event_data_t") as StructLayout

    /**
     * Branch identifier.
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Identifier of the revision being reverted.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Number of the revision being reverted.
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }
}

/**
 * Event data reported at the end of a revert.
 */
object lore_revert_start_end_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_revision_sync_progress_event_data_t.LAYOUT.withName("stats"),
        lore_hash_t.LAYOUT.withName("signature"),
        ValueLayout.JAVA_BYTE.withName("has_conflicts"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revert_start_end_event_data_t") as StructLayout

    /**
     * Progress statistics for the applied changes.
     */
    const val OFFSET_stats: Long = 0L
    fun stats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_stats, 72L)

    /**
     * Resulting revision hash signature.
     */
    const val OFFSET_signature: Long = 72L
    fun signature(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_signature, 32L)

    /**
     * Flag indicating the revert produced conflicts.
     */
    const val OFFSET_has_conflicts: Long = 104L
    fun has_conflicts(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts)
    fun has_conflicts(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_has_conflicts, value)
    }
}

/**
 * Event data reported when a file is unresolved during a revert.
 */
object lore_revert_unresolve_file_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_revert_unresolve_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Event data reported when a revision is unresolved during a revert.
 */
object lore_revert_unresolve_revision_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
    ).withName("lore_revert_unresolve_revision_event_data_t") as StructLayout

    /**
     * Repository identifier.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the revision.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)
}

/**
 * Arguments for amending the most recent revision's commit message.
 */
object lore_revision_amend_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("message"),
    ).withName("lore_revision_amend_args_t") as StructLayout

    /**
     * New commit message
     */
    const val OFFSET_message: Long = 0L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)
}

/**
 * Progress of a bisect search across a range of revisions.
 */
object lore_revision_bisect_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("start_revision_number"),
        ValueLayout.JAVA_LONG.withName("target_revision_number"),
        ValueLayout.JAVA_LONG.withName("end_revision_number"),
        ValueLayout.JAVA_BYTE.withName("done"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_bisect_event_data_t") as StructLayout

    /**
     * Revision number at the start of the search range.
     */
    const val OFFSET_start_revision_number: Long = 0L
    fun start_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_start_revision_number)
    fun start_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_start_revision_number, value)
    }

    /**
     * Revision number selected to test next.
     */
    const val OFFSET_target_revision_number: Long = 8L
    fun target_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_target_revision_number)
    fun target_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_target_revision_number, value)
    }

    /**
     * Revision number at the end of the search range.
     */
    const val OFFSET_end_revision_number: Long = 16L
    fun end_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_end_revision_number)
    fun end_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_end_revision_number, value)
    }

    /**
     * Flag indicating the search has finished.
     */
    const val OFFSET_done: Long = 24L
    fun done(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_done)
    fun done(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_done, value)
    }
}

/**
 * Arguments for committing staged changes into a new revision.
 */
object lore_revision_commit_args_t {
    const val SIZE: Long = 120L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("message"),
        lore_string_t.LAYOUT.withName("link"),
        lore_string_array_t.LAYOUT.withName("link_paths"),
        lore_string_array_t.LAYOUT.withName("link_messages"),
        lore_string_t.LAYOUT.withName("layer"),
        lore_string_array_t.LAYOUT.withName("layer_paths"),
        lore_string_array_t.LAYOUT.withName("layer_messages"),
        ValueLayout.JAVA_BYTE.withName("stats"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_commit_args_t") as StructLayout

    /**
     * Commit message
     */
    const val OFFSET_message: Long = 0L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * If set, commit only this linked repository (mount path relative to repo root)
     */
    const val OFFSET_link: Long = 16L
    fun link(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link, 16L)

    /**
     * Array of link relative paths that have specific messages
     */
    const val OFFSET_link_paths: Long = 32L
    fun link_paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_paths, 16L)

    /**
     * Array of messages corresponding to each link path (parallel array with `link_paths`)
     */
    const val OFFSET_link_messages: Long = 48L
    fun link_messages(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_link_messages, 16L)

    /**
     * If set, commit only this layer (mount path relative to repo root)
     */
    const val OFFSET_layer: Long = 64L
    fun layer(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_layer, 16L)

    /**
     * Array of layer mount paths that have specific messages
     */
    const val OFFSET_layer_paths: Long = 80L
    fun layer_paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_layer_paths, 16L)

    /**
     * Array of messages corresponding to each layer path (parallel array with `layer_paths`)
     */
    const val OFFSET_layer_messages: Long = 96L
    fun layer_messages(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_layer_messages, 16L)

    /**
     * Emit per-fragment write stats during the commit
     */
    const val OFFSET_stats: Long = 112L
    fun stats(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_stats)
    fun stats(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_stats, value)
    }
}

/**
 * Event data reported at the start of a commit.
 */
object lore_revision_commit_begin_event_data_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_revision_commit_begin_event_data_t") as StructLayout

    /**
     * Unused placeholder field.
     */
    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Progress counters describing how far a commit has advanced.
 */
object lore_revision_commit_count_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("directory_count"),
        ValueLayout.JAVA_LONG.withName("directory_total"),
        ValueLayout.JAVA_LONG.withName("file_count"),
        ValueLayout.JAVA_LONG.withName("file_total"),
        ValueLayout.JAVA_LONG.withName("directory_delete_count"),
        ValueLayout.JAVA_LONG.withName("file_modify_count"),
        ValueLayout.JAVA_LONG.withName("file_delete_count"),
        ValueLayout.JAVA_LONG.withName("bytes_transferred"),
        ValueLayout.JAVA_LONG.withName("bytes_total"),
        ValueLayout.JAVA_BYTE.withName("discovery_complete"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_commit_count_data_t") as StructLayout

    /**
     * Number of directories processed so far.
     */
    const val OFFSET_directory_count: Long = 0L
    fun directory_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_count)
    fun directory_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_count, value)
    }

    /**
     * Total number of directories to process.
     */
    const val OFFSET_directory_total: Long = 8L
    fun directory_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_total)
    fun directory_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_total, value)
    }

    /**
     * Number of files processed so far.
     */
    const val OFFSET_file_count: Long = 16L
    fun file_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_count)
    fun file_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_count, value)
    }

    /**
     * Total number of files to process.
     */
    const val OFFSET_file_total: Long = 24L
    fun file_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_total)
    fun file_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_total, value)
    }

    /**
     * Number of directories deleted.
     */
    const val OFFSET_directory_delete_count: Long = 32L
    fun directory_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count)
    fun directory_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_directory_delete_count, value)
    }

    /**
     * Number of files modified.
     */
    const val OFFSET_file_modify_count: Long = 40L
    fun file_modify_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_modify_count)
    fun file_modify_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_modify_count, value)
    }

    /**
     * Number of files deleted.
     */
    const val OFFSET_file_delete_count: Long = 48L
    fun file_delete_count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_delete_count)
    fun file_delete_count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_delete_count, value)
    }

    /**
     * Number of content bytes transferred so far.
     */
    const val OFFSET_bytes_transferred: Long = 56L
    fun bytes_transferred(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred)
    fun bytes_transferred(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_transferred, value)
    }

    /**
     * Total number of content bytes to transfer.
     */
    const val OFFSET_bytes_total: Long = 64L
    fun bytes_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_total)
    fun bytes_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_total, value)
    }

    /**
     * Set when file and directory discovery has finished.
     */
    const val OFFSET_discovery_complete: Long = 72L
    fun discovery_complete(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete)
    fun discovery_complete(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete, value)
    }
}

/**
 * Event data reported at the end of a commit.
 */
object lore_revision_commit_end_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_revision_commit_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_revision_commit_end_event_data_t") as StructLayout

    /**
     * Final progress counters.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 80L)
}

/**
 * Event data reporting commit progress.
 */
object lore_revision_commit_progress_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_revision_commit_count_data_t.LAYOUT.withName("count"),
    ).withName("lore_revision_commit_progress_event_data_t") as StructLayout

    /**
     * Current progress counters.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_count, 80L)
}

/**
 * Event data describing a revision produced by a commit.
 */
object lore_revision_commit_revision_event_data_t {
    const val SIZE: Long = 136L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        lore_hash_t.LAYOUT.withName("parent"),
        lore_hash_t.LAYOUT.withName("parent_other"),
    ).withName("lore_revision_commit_revision_event_data_t") as StructLayout

    /**
     * Identifier of the repository the revision belongs to.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the branch the revision was committed on.
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Signature of the committed revision.
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Sequential number of the revision.
     */
    const val OFFSET_revision_number: Long = 64L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Signature of the first parent revision.
     */
    const val OFFSET_parent: Long = 72L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 32L)

    /**
     * Signature of the second parent revision, set for a merge.
     */
    const val OFFSET_parent_other: Long = 104L
    fun parent_other(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent_other, 32L)
}

/**
 * Arguments for computing file-level differences between two revisions.
 */
object lore_revision_diff_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision_source"),
        lore_string_t.LAYOUT.withName("revision_target"),
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_diff_args_t") as StructLayout

    /**
     * Source revision to diff from
     */
    const val OFFSET_revision_source: Long = 0L
    fun revision_source(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_source, 16L)

    /**
     * Target revision to diff to; empty for current
     */
    const val OFFSET_revision_target: Long = 16L
    fun revision_target(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_target, 16L)

    /**
     * Repository-relative paths to restrict the diff to; empty for all
     */
    const val OFFSET_paths: Long = 32L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Details of a single file that differs between two revisions.
 */
object lore_revision_diff_file_event_data_t {
    const val SIZE: Long = 120L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        ValueLayout.JAVA_BYTE.withName("old_is_file"),
        ValueLayout.JAVA_BYTE.withName("new_is_file"),
        lore_address_t.LAYOUT.withName("old_address"),
        lore_address_t.LAYOUT.withName("new_address"),
        MemoryLayout.paddingLayout(2),
    ).withName("lore_revision_diff_file_event_data_t") as StructLayout

    /**
     * Path of the file relative to the repository root.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 16L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Flag indicating the entry on the source side is a file rather than a directory.
     */
    const val OFFSET_old_is_file: Long = 20L
    fun old_is_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_old_is_file)
    fun old_is_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_old_is_file, value)
    }

    /**
     * Flag indicating the entry on the target side is a file rather than a directory.
     */
    const val OFFSET_new_is_file: Long = 21L
    fun new_is_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_new_is_file)
    fun new_is_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_new_is_file, value)
    }

    /**
     * Address of the file content on the source side.
     */
    const val OFFSET_old_address: Long = 22L
    fun old_address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_old_address, 48L)

    /**
     * Address of the file content on the target side.
     */
    const val OFFSET_new_address: Long = 70L
    fun new_address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_new_address, 48L)
}

/**
 * Arguments for finding revisions by metadata or revision number.
 */
object lore_revision_find_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
        lore_string_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_LONG.withName("number"),
    ).withName("lore_revision_find_args_t") as StructLayout

    /**
     * Metadata key to search for; non-empty selects key/value search
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * Metadata value to match against `key`
     */
    const val OFFSET_value: Long = 16L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 16L)

    /**
     * Revision number to search for when `key` is empty; 0 disables
     */
    const val OFFSET_number: Long = 32L
    fun number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_number)
    fun number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_number, value)
    }
}

/**
 * Data for the event reporting a revision found by a search.
 */
object lore_revision_find_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("signature"),
    ).withName("lore_revision_find_event_data_t") as StructLayout

    /**
     * Signature of the revision that was found.
     */
    const val OFFSET_signature: Long = 0L
    fun signature(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_signature, 32L)
}

/**
 * Arguments for retrieving the revision history of a branch or revision.
 */
object lore_revision_history_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("branch"),
        ValueLayout.JAVA_LONG.withName("date"),
        ValueLayout.JAVA_INT.withName("length"),
        ValueLayout.JAVA_BYTE.withName("only_branch"),
        MemoryLayout.paddingLayout(3),
    ).withName("lore_revision_history_args_t") as StructLayout

    /**
     * Start from this revision; empty for current
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Restrict to this branch; empty for current
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Stop at revisions created before this date (Unix timestamp; 0 disables)
     */
    const val OFFSET_date: Long = 32L
    fun date(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_date)
    fun date(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_date, value)
    }

    /**
     * Maximum number of revisions to return; 0 for unlimited
     */
    const val OFFSET_length: Long = 40L
    fun length(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_length)
    fun length(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_length, value)
    }

    /**
     * Stop when reaching a different branch
     */
    const val OFFSET_only_branch: Long = 44L
    fun only_branch(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_only_branch)
    fun only_branch(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_only_branch, value)
    }
}

/**
 * A single entry in a revision history listing.
 */
object lore_revision_history_entry_event_data_t {
    const val SIZE: Long = 104L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        MemoryLayout.sequenceLayout(2, lore_hash_t.LAYOUT).withName("parent"),
    ).withName("lore_revision_history_entry_event_data_t") as StructLayout

    /**
     * Revision hash signature.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Revision number.
     */
    const val OFFSET_revision_number: Long = 32L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Parent revision hashes; the first is the direct parent and the second
     * is the other parent of a merge, or zero when there is none.
     */
    const val OFFSET_parent: Long = 40L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 64L)
}

/**
 * Header information for a revision history listing.
 */
object lore_revision_history_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
    ).withName("lore_revision_history_event_data_t") as StructLayout

    /**
     * Repository identifier the history belongs to.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Branch identifier the history is listed for.
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)
}

/**
 * Arguments for retrieving metadata and file information for a revision.
 */
object lore_revision_info_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("delta"),
        ValueLayout.JAVA_BYTE.withName("metadata"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_revision_info_args_t") as StructLayout

    /**
     * Revision to get info for; empty for current
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Include delta against parent
     */
    const val OFFSET_delta: Long = 16L
    fun delta(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_delta)
    fun delta(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_delta, value)
    }

    /**
     * Include file metadata entries
     */
    const val OFFSET_metadata: Long = 17L
    fun metadata(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_metadata)
    fun metadata(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_metadata, value)
    }
}

/**
 * Per-file change information between a revision and its parent.
 */
object lore_revision_info_delta_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_INT.withName("action"),
        ValueLayout.JAVA_BYTE.withName("flag_modify"),
        ValueLayout.JAVA_BYTE.withName("flag_merged"),
        ValueLayout.JAVA_BYTE.withName("flag_file"),
        MemoryLayout.paddingLayout(1),
    ).withName("lore_revision_info_delta_event_data_t") as StructLayout

    /**
     * Path of the file relative to the repository root.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Size of the file in bytes.
     */
    const val OFFSET_size: Long = 16L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 24L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Flag indicating the file content was modified.
     */
    const val OFFSET_flag_modify: Long = 28L
    fun flag_modify(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_modify)
    fun flag_modify(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_modify, value)
    }

    /**
     * Flag indicating the change came from a merge.
     */
    const val OFFSET_flag_merged: Long = 29L
    fun flag_merged(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_merged)
    fun flag_merged(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_merged, value)
    }

    /**
     * Flag indicating the entry is a file rather than a directory.
     */
    const val OFFSET_flag_file: Long = 30L
    fun flag_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_file)
    fun flag_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_file, value)
    }
}

/**
 * Summary information about a single revision.
 */
object lore_revision_info_event_data_t {
    const val SIZE: Long = 120L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        MemoryLayout.sequenceLayout(2, lore_hash_t.LAYOUT).withName("parent"),
    ).withName("lore_revision_info_event_data_t") as StructLayout

    /**
     * Repository identifier the revision belongs to.
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision hash signature.
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Revision number.
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Parent revision hashes; the first is the direct parent and the second
     * is the other parent of a merge, or zero when there is none.
     */
    const val OFFSET_parent: Long = 56L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 64L)
}

/**
 * Arguments for clearing all metadata from the current revision.
 */
object lore_revision_metadata_clear_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_revision_metadata_clear_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for retrieving a single metadata value by key from a revision.
 */
object lore_revision_metadata_get_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("key"),
        lore_string_t.LAYOUT.withName("revision"),
    ).withName("lore_revision_metadata_get_args_t") as StructLayout

    /**
     * Metadata key to look up
     */
    const val OFFSET_key: Long = 0L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * Revision to get metadata for; empty for current
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)
}

/**
 * Arguments for listing all metadata key/value pairs of a revision.
 */
object lore_revision_metadata_list_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
    ).withName("lore_revision_metadata_list_args_t") as StructLayout

    /**
     * Revision to list metadata for; empty for current
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)
}

/**
 * Arguments for setting metadata key/value pairs on the current revision.
 */
object lore_revision_metadata_set_args_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("keys"),
        lore_string_array_t.LAYOUT.withName("values"),
        lore_metadata_type_array_t.LAYOUT.withName("formats"),
    ).withName("lore_revision_metadata_set_args_t") as StructLayout

    /**
     * Metadata keys (parallel with `values` and `formats`)
     */
    const val OFFSET_keys: Long = 0L
    fun keys(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_keys, 16L)

    /**
     * Metadata values, decoded per the matching format
     */
    const val OFFSET_values: Long = 16L
    fun values(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_values, 16L)

    /**
     * Value type for each entry
     */
    const val OFFSET_formats: Long = 32L
    fun formats(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_formats, 16L)
}

/**
 * Information about a revision being resolved from a signature.
 */
object lore_revision_resolve_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        ValueLayout.JAVA_BYTE.withName("remote"),
        ValueLayout.JAVA_BYTE.withName("local"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_revision_resolve_event_data_t") as StructLayout

    /**
     * Repository identifier in which repository
     */
    const val OFFSET_repository: Long = 0L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Identifier of the branch on which resolution is being done
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * If set to non-empty, the partial hash being resolved
     */
    const val OFFSET_revision: Long = 32L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * If set to non-zero, the revision number being resolved
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Resolving using remote data
     */
    const val OFFSET_remote: Long = 56L
    fun remote(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote)
    fun remote(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote, value)
    }

    /**
     * Resolving using local data
     */
    const val OFFSET_local: Long = 57L
    fun local(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local)
    fun local(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local, value)
    }
}

/**
 * Arguments for restoring the current branch to a previously synced revision.
 */
object lore_revision_restore_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("message"),
    ).withName("lore_revision_restore_args_t") as StructLayout

    /**
     * Commit message for the restored revision
     */
    const val OFFSET_message: Long = 0L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)
}

/**
 * Event data reported at the start of the file phase of a restore.
 */
object lore_revision_restore_file_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_restore_file_begin_event_data_t") as StructLayout

    /**
     * Number of files to process.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Event data reported at the end of the file phase of a restore.
 */
object lore_revision_restore_file_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_restore_file_end_event_data_t") as StructLayout

    /**
     * Number of files processed.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Event data reported for a single file during a restore.
 */
object lore_revision_restore_file_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("action"),
        MemoryLayout.paddingLayout(4),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_BYTE.withName("is_file"),
        ValueLayout.JAVA_BYTE.withName("is_directory"),
        ValueLayout.JAVA_BYTE.withName("is_module"),
        MemoryLayout.paddingLayout(5),
    ).withName("lore_revision_restore_file_event_data_t") as StructLayout

    /**
     * Path of the file.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 16L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Size of the file in bytes.
     */
    const val OFFSET_size: Long = 24L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Flag indicating the entry is a file.
     */
    const val OFFSET_is_file: Long = 32L
    fun is_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_file)
    fun is_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_file, value)
    }

    /**
     * Flag indicating the entry is a directory.
     */
    const val OFFSET_is_directory: Long = 33L
    fun is_directory(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_directory)
    fun is_directory(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_directory, value)
    }

    /**
     * Flag indicating the entry is a module.
     */
    const val OFFSET_is_module: Long = 34L
    fun is_module(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_module)
    fun is_module(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_module, value)
    }
}

/**
 * Event data reported at the start of the fragment phase of a restore.
 */
object lore_revision_restore_fragment_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
    ).withName("lore_revision_restore_fragment_begin_event_data_t") as StructLayout

    /**
     * Number of fragments to transfer.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }
}

/**
 * Event data reported at the end of the fragment phase of a restore.
 */
object lore_revision_restore_fragment_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("fragments"),
    ).withName("lore_revision_restore_fragment_end_event_data_t") as StructLayout

    /**
     * Number of fragments transferred.
     */
    const val OFFSET_fragments: Long = 0L
    fun fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fragments)
    fun fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fragments, value)
    }
}

/**
 * Event data reported on progress of the fragment phase of a restore.
 */
object lore_revision_restore_fragment_progress_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("complete"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_restore_fragment_progress_event_data_t") as StructLayout

    /**
     * Number of fragments completed.
     */
    const val OFFSET_complete: Long = 0L
    fun complete(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_complete)
    fun complete(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_complete, value)
    }

    /**
     * Total number of fragments.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Event data reported with the resulting revision of a restore.
 */
object lore_revision_restore_revision_event_data_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
    ).withName("lore_revision_restore_revision_event_data_t") as StructLayout

    /**
     * Resulting revision hash signature.
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Resulting revision number.
     */
    const val OFFSET_revision_number: Long = 32L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }
}

/**
 * Event data reported at the start of the sync phase of a restore.
 */
object lore_revision_restore_sync_begin_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_restore_sync_begin_event_data_t") as StructLayout

    /**
     * Number of changes to apply.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Event data reported at the end of the sync phase of a restore.
 */
object lore_revision_restore_sync_end_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_restore_sync_end_event_data_t") as StructLayout

    /**
     * Number of changes applied.
     */
    const val OFFSET_count: Long = 0L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Arguments for aborting a revert operation in progress.
 */
object lore_revision_revert_abort_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_revision_revert_abort_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for reverting the working directory to a specified revision.
 */
object lore_revision_revert_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("message"),
        ValueLayout.JAVA_BYTE.withName("no_commit"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_revert_args_t") as StructLayout

    /**
     * Revision to revert
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Message to use for an auto-commit if no conflicts arise
     */
    const val OFFSET_message: Long = 16L
    fun message(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_message, 16L)

    /**
     * Disable auto-commit even if no conflicts arise
     */
    const val OFFSET_no_commit: Long = 32L
    fun no_commit(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_no_commit)
    fun no_commit(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_no_commit, value)
    }
}

/**
 * Arguments for marking revert conflicts as resolved for paths.
 */
object lore_revision_revert_resolve_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_revert_resolve_args_t") as StructLayout

    /**
     * Repository-relative paths to mark resolved
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for resolving revert conflicts by keeping the "mine" version.
 */
object lore_revision_revert_resolve_mine_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_revert_resolve_mine_args_t") as StructLayout

    /**
     * Repository-relative paths to resolve in favor of "mine"
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for resolving revert conflicts by keeping the "theirs" version.
 */
object lore_revision_revert_resolve_theirs_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_revert_resolve_theirs_args_t") as StructLayout

    /**
     * Repository-relative paths to resolve in favor of "theirs"
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for restarting revert conflict resolution for paths.
 */
object lore_revision_revert_restart_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_revert_restart_args_t") as StructLayout

    /**
     * Repository-relative paths to re-materialize for resolution
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for marking revert paths as unresolved again.
 */
object lore_revision_revert_unresolve_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_array_t.LAYOUT.withName("paths"),
    ).withName("lore_revision_revert_unresolve_args_t") as StructLayout

    /**
     * Repository-relative paths to mark unresolved
     */
    const val OFFSET_paths: Long = 0L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)
}

/**
 * Arguments for synchronizing the working directory to a target revision.
 */
object lore_revision_sync_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_BYTE.withName("forward_changes"),
        ValueLayout.JAVA_BYTE.withName("reset"),
        MemoryLayout.paddingLayout(6),
        lore_string_array_t.LAYOUT.withName("root_files"),
        lore_string_array_t.LAYOUT.withName("dependency_tags"),
        ValueLayout.JAVA_BYTE.withName("dependency_recursive"),
        MemoryLayout.paddingLayout(3),
        ValueLayout.JAVA_INT.withName("dependency_depth_limit"),
    ).withName("lore_revision_sync_args_t") as StructLayout

    /**
     * Revision to synchronize to; empty for branch tip
     */
    const val OFFSET_revision: Long = 0L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 16L)

    /**
     * Fast forward and keep local changes when syncing to a local revision
     */
    const val OFFSET_forward_changes: Long = 16L
    fun forward_changes(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_forward_changes)
    fun forward_changes(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_forward_changes, value)
    }

    /**
     * Reset local modified files to match the incoming revision
     */
    const val OFFSET_reset: Long = 17L
    fun reset(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_reset)
    fun reset(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_reset, value)
    }

    /**
     * Root files for dependency-based selective sync
     */
    const val OFFSET_root_files: Long = 24L
    fun root_files(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_root_files, 16L)

    /**
     * Tags to filter dependencies by during resolution
     */
    const val OFFSET_dependency_tags: Long = 40L
    fun dependency_tags(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dependency_tags, 16L)

    /**
     * Follow transitive dependencies recursively
     */
    const val OFFSET_dependency_recursive: Long = 56L
    fun dependency_recursive(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_dependency_recursive)
    fun dependency_recursive(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_dependency_recursive, value)
    }

    /**
     * Maximum dependency traversal depth; 0 means unlimited
     */
    const val OFFSET_dependency_depth_limit: Long = 60L
    fun dependency_depth_limit(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_dependency_depth_limit)
    fun dependency_depth_limit(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_dependency_depth_limit, value)
    }
}

/**
 * Details of a single file changed by a sync.
 */
object lore_revision_sync_file_event_data_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.JAVA_INT.withName("action"),
        ValueLayout.JAVA_BYTE.withName("flag_file"),
        MemoryLayout.paddingLayout(3),
    ).withName("lore_revision_sync_file_event_data_t") as StructLayout

    /**
     * Path of the file relative to the repository root.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Size of the file in bytes.
     */
    const val OFFSET_size: Long = 16L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Action applied to the file.
     */
    const val OFFSET_action: Long = 24L
    fun action(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_action)
    fun action(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_action, value)
    }

    /**
     * Flag indicating the entry is a file rather than a directory.
     */
    const val OFFSET_flag_file: Long = 28L
    fun flag_file(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_file)
    fun flag_file(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_file, value)
    }
}

/**
 * Progress counters reported while a sync updates the working files.
 */
object lore_revision_sync_progress_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("file_update"),
        ValueLayout.JAVA_LONG.withName("file_update_total"),
        ValueLayout.JAVA_LONG.withName("file_delete"),
        ValueLayout.JAVA_LONG.withName("file_delete_total"),
        ValueLayout.JAVA_LONG.withName("file_automerge"),
        ValueLayout.JAVA_LONG.withName("file_conflict"),
        ValueLayout.JAVA_LONG.withName("bytes_update"),
        ValueLayout.JAVA_LONG.withName("bytes_update_total"),
        ValueLayout.JAVA_BYTE.withName("discovery_complete"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_sync_progress_event_data_t") as StructLayout

    /**
     * Number of files updated so far.
     */
    const val OFFSET_file_update: Long = 0L
    fun file_update(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_update)
    fun file_update(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_update, value)
    }

    /**
     * Total number of files to update.
     */
    const val OFFSET_file_update_total: Long = 8L
    fun file_update_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_update_total)
    fun file_update_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_update_total, value)
    }

    /**
     * Number of files deleted so far.
     */
    const val OFFSET_file_delete: Long = 16L
    fun file_delete(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_delete)
    fun file_delete(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_delete, value)
    }

    /**
     * Total number of files to delete.
     */
    const val OFFSET_file_delete_total: Long = 24L
    fun file_delete_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_delete_total)
    fun file_delete_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_delete_total, value)
    }

    /**
     * Number of files merged automatically so far.
     */
    const val OFFSET_file_automerge: Long = 32L
    fun file_automerge(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_automerge)
    fun file_automerge(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_automerge, value)
    }

    /**
     * Number of files with conflicts so far.
     */
    const val OFFSET_file_conflict: Long = 40L
    fun file_conflict(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_file_conflict)
    fun file_conflict(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_file_conflict, value)
    }

    /**
     * Number of bytes updated so far.
     */
    const val OFFSET_bytes_update: Long = 48L
    fun bytes_update(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_update)
    fun bytes_update(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_update, value)
    }

    /**
     * Total number of bytes to update.
     */
    const val OFFSET_bytes_update_total: Long = 56L
    fun bytes_update_total(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_bytes_update_total)
    fun bytes_update_total(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_bytes_update_total, value)
    }

    /**
     * Flag indicating discovery of the work to do has finished.
     */
    const val OFFSET_discovery_complete: Long = 64L
    fun discovery_complete(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete)
    fun discovery_complete(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_discovery_complete, value)
    }
}

/**
 * The revision that resulted from a sync.
 */
object lore_revision_sync_revision_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_context_t.LAYOUT.withName("branch"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_LONG.withName("revision_number"),
        ValueLayout.JAVA_BYTE.withName("flag_merge"),
        ValueLayout.JAVA_BYTE.withName("flag_conflict"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_revision_sync_revision_event_data_t") as StructLayout

    /**
     * Branch (if any)
     */
    const val OFFSET_branch: Long = 0L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Resulting revision hash signature
     */
    const val OFFSET_revision: Long = 16L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * Resulting revision number, or 0 if sync resulted in a merge
     */
    const val OFFSET_revision_number: Long = 48L
    fun revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_revision_number)
    fun revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_revision_number, value)
    }

    /**
     * Sync resulted in a staged merge revision
     */
    const val OFFSET_flag_merge: Long = 56L
    fun flag_merge(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_merge)
    fun flag_merge(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_merge, value)
    }

    /**
     * Sync resulted in a staged merged revision with conflicts
     */
    const val OFFSET_flag_conflict: Long = 57L
    fun flag_conflict(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict)
    fun flag_conflict(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_flag_conflict, value)
    }
}

/**
 * Source and target revisions selected for a sync.
 */
object lore_revision_sync_target_event_data_t {
    const val SIZE: Long = 152L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_string_t.LAYOUT.withName("branch_name"),
        lore_hash_t.LAYOUT.withName("source_revision"),
        ValueLayout.JAVA_LONG.withName("source_revision_number"),
        lore_hash_t.LAYOUT.withName("target_revision"),
        ValueLayout.JAVA_LONG.withName("target_revision_number"),
        ValueLayout.JAVA_BYTE.withName("is_latest"),
        ValueLayout.JAVA_BYTE.withName("local"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_revision_sync_target_event_data_t") as StructLayout

    /**
     * Remote URL
     */
    const val OFFSET_remote: Long = 0L
    fun remote(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote, 16L)

    /**
     * Repository identifier
     */
    const val OFFSET_repository: Long = 16L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Branch identifier (if any)
     */
    const val OFFSET_branch: Long = 32L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Branch name (if any)
     */
    const val OFFSET_branch_name: Long = 48L
    fun branch_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch_name, 16L)

    /**
     * Current (source) revision identifier
     */
    const val OFFSET_source_revision: Long = 64L
    fun source_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_revision, 32L)

    /**
     * Current (source) revision number
     */
    const val OFFSET_source_revision_number: Long = 96L
    fun source_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_source_revision_number)
    fun source_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_source_revision_number, value)
    }

    /**
     * Target revision identifier
     */
    const val OFFSET_target_revision: Long = 104L
    fun target_revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_revision, 32L)

    /**
     * Target revision number
     */
    const val OFFSET_target_revision_number: Long = 136L
    fun target_revision_number(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_target_revision_number)
    fun target_revision_number(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_target_revision_number, value)
    }

    /**
     * Flag indicating revision is the latest revision of the branch
     */
    const val OFFSET_is_latest: Long = 144L
    fun is_latest(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_is_latest)
    fun is_latest(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_is_latest, value)
    }

    /**
     * Flag indicating revision was from local revision history, not remote
     */
    const val OFFSET_local: Long = 145L
    fun local(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local)
    fun local(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local, value)
    }
}

/**
 * Arguments for `lore_revision_tree_add`.
 */
object lore_revision_tree_add_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        lore_revision_tree_add_entry_array_t.LAYOUT.withName("entries"),
    ).withName("lore_revision_tree_add_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in `BATCH_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to mutate
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Nodes to add; each emits its own `ADD_COMPLETE`
     */
    const val OFFSET_entries: Long = 16L
    fun entries(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_entries, 16L)
}

/**
 * Terminal per-entry event for `add`. On success `node_id` is the
 * newly-allocated child; on failure `node_id` is undefined.
 */
object lore_revision_tree_add_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_revision_tree_add_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The newly-added node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 12L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_revision_tree_add_entry_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_revision_tree_add_entry_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * One node to add. The parent is `parent_node_id`, or the node created by an
 * earlier entry when `parent_node_id` is the invalid-node sentinel.
 */
object lore_revision_tree_add_entry_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("parent_node_id"),
        ValueLayout.JAVA_INT.withName("parent_entry"),
        lore_string_t.LAYOUT.withName("name"),
        ValueLayout.JAVA_INT.withName("kind"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        MemoryLayout.paddingLayout(2),
        ValueLayout.JAVA_LONG.withName("size"),
        lore_address_t.LAYOUT.withName("address"),
    ).withName("lore_revision_tree_add_entry_t") as StructLayout

    /**
     * Caller-chosen id echoed back in this entry's `ADD_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Parent for the new node; the invalid-node sentinel selects `parent_entry`
     */
    const val OFFSET_parent_node_id: Long = 8L
    fun parent_node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent_node_id)
    fun parent_node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent_node_id, value)
    }

    /**
     * Index of an earlier entry in this batch whose new node is the parent;
     * read only when `parent_node_id` is the invalid-node sentinel
     */
    const val OFFSET_parent_entry: Long = 12L
    fun parent_entry(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent_entry)
    fun parent_entry(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent_entry, value)
    }

    /**
     * UTF-8 name of the new child within its parent
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * `LoreNodeType` encoding: `DIRECTORY = 0`, `FILE = 1`, `LINK = 2`
     */
    const val OFFSET_kind: Long = 32L
    fun kind(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_kind)
    fun kind(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_kind, value)
    }

    /**
     * POSIX permission bits for the new node
     */
    const val OFFSET_mode: Long = 36L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * Content size in bytes (leaf nodes); `0` for a directory
     */
    const val OFFSET_size: Long = 40L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * Content address `(hash, file_id context)` of the new node
     */
    const val OFFSET_address: Long = 48L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)
}

/**
 * Terminal event for a batch write call as a whole, carrying the call's own id
 * rather than any entry's.
 * 
 * Every batch write verb emits exactly one of these, after any per-entry
 * terminals and before `Complete`. The error code is `NONE` when the call did
 * what it was asked; otherwise it names a failure belonging to the call rather
 * than to a single entry, such as an unknown or closed handle. A per-entry
 * failure is reported on that entry's own terminal instead.
 */
object lore_revision_tree_batch_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_batch_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome of the call as a whole
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Per-child event from `list_children`. One event is emitted per entry;
 * the caller correlates entries by `id` and detects end-of-list via the
 * trailing `Complete` event.
 */
object lore_revision_tree_child_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("name"),
        ValueLayout.JAVA_INT.withName("parent_id"),
        ValueLayout.JAVA_INT.withName("kind"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        MemoryLayout.paddingLayout(6),
        ValueLayout.JAVA_LONG.withName("size"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_child_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The child node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * The name of the child node.
     */
    const val OFFSET_name: Long = 16L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * The parent node.
     */
    const val OFFSET_parent_id: Long = 32L
    fun parent_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent_id)
    fun parent_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent_id, value)
    }

    /**
     * The kind of node.
     */
    const val OFFSET_kind: Long = 36L
    fun kind(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_kind)
    fun kind(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_kind, value)
    }

    /**
     * The file mode bits.
     */
    const val OFFSET_mode: Long = 40L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * The size of the node's content in bytes.
     */
    const val OFFSET_size: Long = 48L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * The address of the node's content.
     */
    const val OFFSET_address: Long = 56L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 104L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_close`.
 */
object lore_revision_tree_close_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
    ).withName("lore_revision_tree_close_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Revision-tree handle to release
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)
}

/**
 * Terminal per-call event for `close`.
 */
object lore_revision_tree_close_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_close_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_commit`.
 */
object lore_revision_tree_commit_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        lore_context_t.LAYOUT.withName("branch"),
        lore_revision_tree_commit_options_t.LAYOUT.withName("options"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_revision_tree_commit_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to freeze and commit
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Branch whose tip is atomically advanced to the new revision
     */
    const val OFFSET_branch: Long = 16L
    fun branch(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_branch, 16L)

    /**
     * Commit tuneables (local-only vs remote-uploading)
     */
    const val OFFSET_options: Long = 32L
    fun options(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_options, 1L)
}

/**
 * Terminal per-call event for `commit`. On success `revision_hash` is the
 * newly-committed revision and `new_tip_hash` is `Hash::default()`. When
 * `error_code` reports `BranchAdvanced`, `new_tip_hash` carries the
 * observed branch tip so the caller can reload without an extra
 * `branch::load_latest` round-trip.
 */
object lore_revision_tree_commit_complete_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_hash_t.LAYOUT.withName("revision_hash"),
        lore_hash_t.LAYOUT.withName("new_tip_hash"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_commit_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The newly-committed revision.
     */
    const val OFFSET_revision_hash: Long = 8L
    fun revision_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_hash, 32L)

    /**
     * The observed branch tip when the branch had advanced.
     */
    const val OFFSET_new_tip_hash: Long = 40L
    fun new_tip_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_new_tip_hash, 32L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 72L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Tuneables for `lore_revision_tree_commit`.
 */
object lore_revision_tree_commit_options_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("remote_write"),
    ).withName("lore_revision_tree_commit_options_t") as StructLayout

    /**
     * Also upload the new revision to remote (local-only by default)
     */
    const val OFFSET_remote_write: Long = 0L
    fun remote_write(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_write)
    fun remote_write(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_write, value)
    }
}

/**
 * Arguments for `lore_revision_tree_delete`.
 */
object lore_revision_tree_delete_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("node_id"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_delete_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to mutate
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Subtree root to mark deleted, including its transitive children
     */
    const val OFFSET_node_id: Long = 16L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }
}

/**
 * Terminal per-call event for `delete`.
 */
object lore_revision_tree_delete_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_delete_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_info`.
 */
object lore_revision_tree_info_args_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
    ).withName("lore_revision_tree_info_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle whose revision metadata is fetched
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)
}

/**
 * Terminal per-call event for `revision_info` (the `lore_revision_tree_info`
 * verb). Carries the loaded revision's record-level metadata: the parent
 * revision signatures (from the State) plus the creation timestamp, author
 * identity, and metadata key count (from the Metadata fragment), alongside the
 * `(repository, revision)` the handle represents. On failure the fields are
 * zeroed and `error_code` is populated. This is revision-scoped, not
 * node-scoped — it takes no node id.
 */
object lore_revision_tree_info_event_data_t {
    const val SIZE: Long = 152L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        MemoryLayout.sequenceLayout(2, lore_hash_t.LAYOUT).withName("parent"),
        ValueLayout.JAVA_LONG.withName("creation_timestamp"),
        lore_string_t.LAYOUT.withName("author_identity"),
        ValueLayout.JAVA_INT.withName("metadata_key_count"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_revision_tree_info_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Repository the revision belongs to.
     */
    const val OFFSET_repository: Long = 8L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * The loaded revision.
     */
    const val OFFSET_revision: Long = 24L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The parent revision signatures.
     */
    const val OFFSET_parent: Long = 56L
    fun parent(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_parent, 64L)

    /**
     * The time the revision was created.
     */
    const val OFFSET_creation_timestamp: Long = 120L
    fun creation_timestamp(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_creation_timestamp)
    fun creation_timestamp(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_creation_timestamp, value)
    }

    /**
     * The identity of the revision's author.
     */
    const val OFFSET_author_identity: Long = 128L
    fun author_identity(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_author_identity, 16L)

    /**
     * The number of metadata keys on the revision.
     */
    const val OFFSET_metadata_key_count: Long = 144L
    fun metadata_key_count(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_metadata_key_count)
    fun metadata_key_count(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_metadata_key_count, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 148L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_list_children`.
 */
object lore_revision_tree_list_children_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("parent_node_id"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_list_children_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to read from
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Directory node whose children are streamed
     */
    const val OFFSET_parent_node_id: Long = 16L
    fun parent_node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent_node_id)
    fun parent_node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent_node_id, value)
    }
}

/**
 * Header for `list_children`, emitted once before any child event. Carries
 * the `(repository, revision)` the listing targets — the handle's own tree,
 * or a link target's tree after the link is resolved — so the caller can
 * reopen that tree to act on the children's node ids. On failure carries the
 * outcome with a zeroed `repository`/`revision` and no children follow.
 */
object lore_revision_tree_list_children_begin_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_list_children_begin_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Repository the listed children belong to.
     */
    const val OFFSET_repository: Long = 8L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the listed children belong to.
     */
    const val OFFSET_revision: Long = 24L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 56L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_load`.
 */
object lore_revision_tree_load_args_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("store"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision_hash"),
    ).withName("lore_revision_tree_load_args_t") as StructLayout

    /**
     * Open storage handle the revision tree is loaded against
     */
    const val OFFSET_store: Long = 0L
    fun store(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_store, 8L)

    /**
     * Repository partition the loaded revision belongs to
     */
    const val OFFSET_repository: Long = 8L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision to open; `0` opens an empty tree for an initial commit
     */
    const val OFFSET_revision_hash: Long = 24L
    fun revision_hash(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision_hash, 32L)
}

/**
 * Delivered on successful `lore_revision_tree_load`. Carries the registry
 * id the caller must pass to subsequent verbs against this revision tree.
 */
object lore_revision_tree_loaded_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("handle_id"),
    ).withName("lore_revision_tree_loaded_event_data_t") as StructLayout

    /**
     * Registry id for the loaded revision tree.
     */
    const val OFFSET_handle_id: Long = 0L
    fun handle_id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_handle_id)
    fun handle_id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_handle_id, value)
    }
}

/**
 * Arguments for `lore_revision_tree_metadata_get`.
 */
object lore_revision_tree_metadata_get_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        lore_string_t.LAYOUT.withName("key"),
    ).withName("lore_revision_tree_metadata_get_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to read from
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Metadata key to read; pending edits take precedence over the revision
     */
    const val OFFSET_key: Long = 16L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)
}

/**
 * Per-call event carrying a metadata value from `metadata_get`. The
 * missing-key case emits no value event and lets the trailing `Complete`
 * fire on its own.
 * 
 * No `Debug` derive: the embedded `LoreMetadata` enum does not implement
 * `Debug`. Use `serde_json::to_string` to render this for diagnostics.
 */
object lore_revision_tree_metadata_get_complete_event_data_t {
    const val SIZE: Long = 88L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_string_t.LAYOUT.withName("key"),
        lore_metadata_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_metadata_get_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The metadata key.
     */
    const val OFFSET_key: Long = 8L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * The metadata value.
     */
    const val OFFSET_value: Long = 24L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 56L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 80L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_metadata_set`.
 */
object lore_revision_tree_metadata_set_args_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        lore_string_t.LAYOUT.withName("key"),
        lore_string_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_INT.withName("format"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_metadata_set_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to mutate
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Metadata key; re-setting it overwrites the pending value
     */
    const val OFFSET_key: Long = 16L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 16L)

    /**
     * Value stored under the key
     */
    const val OFFSET_value: Long = 32L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 16L)

    /**
     * Value encoding, matching `LoreRevisionMetadataSetArgs::formats`
     */
    const val OFFSET_format: Long = 48L
    fun format(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_format)
    fun format(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_format, value)
    }
}

/**
 * Terminal per-call event for `metadata_set`.
 */
object lore_revision_tree_metadata_set_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_metadata_set_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_modify`.
 */
object lore_revision_tree_modify_args_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("node_id"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        MemoryLayout.paddingLayout(2),
        ValueLayout.JAVA_LONG.withName("size"),
        lore_address_t.LAYOUT.withName("address"),
    ).withName("lore_revision_tree_modify_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to mutate
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Leaf node to update; non-leaf targets are rejected
     */
    const val OFFSET_node_id: Long = 16L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * New POSIX permission bits
     */
    const val OFFSET_mode: Long = 20L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * New content size in bytes
     */
    const val OFFSET_size: Long = 24L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * New content address; the existing `file_id` context is preserved
     */
    const val OFFSET_address: Long = 32L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)
}

/**
 * Terminal per-call event for `modify`. `node_id` echoes the modified
 * node so the caller can chain operations without re-resolving.
 */
object lore_revision_tree_modify_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_revision_tree_modify_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The modified node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 12L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_move`.
 */
object lore_revision_tree_move_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("node_id"),
        ValueLayout.JAVA_INT.withName("destination_parent_id"),
        lore_string_t.LAYOUT.withName("dst_name"),
    ).withName("lore_revision_tree_move_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to mutate
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Node to move; its `file_id` is preserved across the move
     */
    const val OFFSET_node_id: Long = 16L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * Parent node the moved node is reparented under
     */
    const val OFFSET_destination_parent_id: Long = 20L
    fun destination_parent_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_destination_parent_id)
    fun destination_parent_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_destination_parent_id, value)
    }

    /**
     * UTF-8 name the moved node takes at the destination
     */
    const val OFFSET_dst_name: Long = 24L
    fun dst_name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_dst_name, 16L)
}

/**
 * Terminal per-call event for `move`. `node_id` echoes the moved node so
 * the caller observes that `file_id` is preserved across the reparent.
 */
object lore_revision_tree_move_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_revision_tree_move_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The moved node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 12L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_node_info`.
 */
object lore_revision_tree_node_info_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("node_id"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_node_info_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to read from
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Node whose record is fetched
     */
    const val OFFSET_node_id: Long = 16L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }
}

/**
 * Terminal per-call event for `node_info`. On success `error_code == None` and
 * the per-node record matches `list_children` plus the preserved `file_id`
 * (the `address.context` slot of the node's original add), with
 * `repository`/`revision` identifying the tree the node belongs to (the
 * handle's own — `node_info` does not follow links). The record is uniform
 * across every node id, including the root; revision-level metadata is a
 * separate concern served by `lore_revision_tree_info`. On failure the record
 * is undefined and `error_code` is populated.
 */
object lore_revision_tree_node_info_event_data_t {
    const val SIZE: Long = 176L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        MemoryLayout.paddingLayout(4),
        lore_string_t.LAYOUT.withName("name"),
        ValueLayout.JAVA_INT.withName("parent_id"),
        ValueLayout.JAVA_INT.withName("kind"),
        ValueLayout.JAVA_SHORT.withName("mode"),
        MemoryLayout.paddingLayout(6),
        ValueLayout.JAVA_LONG.withName("size"),
        lore_address_t.LAYOUT.withName("address"),
        lore_context_t.LAYOUT.withName("file_id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_node_info_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The queried node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * Repository the node belongs to.
     */
    const val OFFSET_repository: Long = 12L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the node belongs to.
     */
    const val OFFSET_revision: Long = 28L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The name of the node.
     */
    const val OFFSET_name: Long = 64L
    fun name(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_name, 16L)

    /**
     * The parent node.
     */
    const val OFFSET_parent_id: Long = 80L
    fun parent_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_parent_id)
    fun parent_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_parent_id, value)
    }

    /**
     * The kind of node.
     */
    const val OFFSET_kind: Long = 84L
    fun kind(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_kind)
    fun kind(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_kind, value)
    }

    /**
     * The file mode bits.
     */
    const val OFFSET_mode: Long = 88L
    fun mode(struct: MemorySegment): Short =
        struct.get(ValueLayout.JAVA_SHORT, OFFSET_mode)
    fun mode(struct: MemorySegment, value: Short) {
        struct.set(ValueLayout.JAVA_SHORT, OFFSET_mode, value)
    }

    /**
     * The size of the node's content in bytes.
     */
    const val OFFSET_size: Long = 96L
    fun size(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size)
    fun size(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size, value)
    }

    /**
     * The address of the node's content.
     */
    const val OFFSET_address: Long = 104L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The preserved file id of the node.
     */
    const val OFFSET_file_id: Long = 152L
    fun file_id(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_file_id, 16L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 168L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_node_path`.
 */
object lore_revision_tree_node_path_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        ValueLayout.JAVA_INT.withName("node_id"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_node_path_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to read from
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Node whose full UTF-8 path is reconstructed by walking parents
     */
    const val OFFSET_node_id: Long = 16L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }
}

/**
 * Terminal per-call event for `node_path`. On success `error_code == None` and
 * `path` is the reconstructed UTF-8 path from the root to the queried node,
 * with `repository`/`revision` identifying the tree it was reconstructed in
 * (the handle's own — `node_path` walks within the handle's revision and does
 * not follow links). On failure `path` is empty and `error_code` is populated.
 */
object lore_revision_tree_node_path_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_revision_tree_node_path_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Repository the path was reconstructed in.
     */
    const val OFFSET_repository: Long = 8L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the path was reconstructed in.
     */
    const val OFFSET_revision: Long = 24L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The reconstructed path from the root to the queried node.
     */
    const val OFFSET_path: Long = 56L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 72L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Arguments for `lore_revision_tree_resolve_path`.
 */
object lore_revision_tree_resolve_path_args_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_revision_tree_t.LAYOUT.withName("handle"),
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_revision_tree_resolve_path_args_t") as StructLayout

    /**
     * Per-call correlation id echoed back in events
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Loaded revision-tree handle to resolve against
     */
    const val OFFSET_handle: Long = 8L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * UTF-8 path relative to the tree root; empty resolves to the root node
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Terminal per-call event for `resolve_path`. On success `error_code ==
 * None`, `node_id` is the resolved node, and `repository`/`revision` identify
 * the tree it belongs to (they differ from the handle's when the path crosses
 * a link). On failure `node_id` is undefined and `error_code` is populated.
 */
object lore_revision_tree_resolve_path_complete_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("node_id"),
        lore_partition_t.LAYOUT.withName("repository"),
        lore_hash_t.LAYOUT.withName("revision"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_revision_tree_resolve_path_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the originating call.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The resolved node.
     */
    const val OFFSET_node_id: Long = 8L
    fun node_id(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_node_id)
    fun node_id(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_node_id, value)
    }

    /**
     * Repository the resolved node belongs to.
     */
    const val OFFSET_repository: Long = 12L
    fun repository(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository, 16L)

    /**
     * Revision the resolved node belongs to.
     */
    const val OFFSET_revision: Long = 28L
    fun revision(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_revision, 32L)

    /**
     * The outcome of the call.
     */
    const val OFFSET_error_code: Long = 60L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * Opaque handle to an open memory-based revision tree instance.
 * 
 * Treat this as an opaque value; never cast it directly to or from raw
 * pointers.
 */
object lore_revision_tree_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("handle_id"),
    ).withName("lore_revision_tree_t") as StructLayout

    /**
     * Registry key; `0` is the reserved invalid/unregistered sentinel (zero-init = null handle)
     */
    const val OFFSET_handle_id: Long = 0L
    fun handle_id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_handle_id)
    fun handle_id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_handle_id, value)
    }
}

/**
 * Arguments for starting the Lore service process for the current repository (no parameters).
 */
object lore_service_start_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_service_start_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Arguments for stopping the Lore service process for the current or all repositories.
 */
object lore_service_stop_args_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("all"),
    ).withName("lore_service_stop_args_t") as StructLayout

    /**
     * Stop all repositories rather than just the current one
     */
    const val OFFSET_all: Long = 0L
    fun all(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_all)
    fun all(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_all, value)
    }
}

/**
 * Arguments for creating a new shared store.
 */
object lore_shared_store_create_args_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote_url"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("make_default"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_shared_store_create_args_t") as StructLayout

    /**
     * Remote URL backing the store
     */
    const val OFFSET_remote_url: Long = 0L
    fun remote_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_url, 16L)

    /**
     * Path where the store will be created; empty string uses the default location
     */
    const val OFFSET_path: Long = 16L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Set this as the default shared store in the global config
     */
    const val OFFSET_make_default: Long = 32L
    fun make_default(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_make_default)
    fun make_default(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_make_default, value)
    }
}

/**
 * Data for an event reporting that a shared store was created.
 */
object lore_shared_store_create_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("path"),
    ).withName("lore_shared_store_create_event_data_t") as StructLayout

    /**
     * Filesystem path of the created shared store.
     */
    const val OFFSET_path: Long = 0L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)
}

/**
 * Arguments for querying the configured default shared store (no parameters).
 */
object lore_shared_store_info_args_t {
    const val SIZE: Long = 4L
    const val ALIGNMENT: Long = 4L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_INT.withName("_unused"),
    ).withName("lore_shared_store_info_args_t") as StructLayout

    const val OFFSET__unused: Long = 0L
    fun _unused(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET__unused)
    fun _unused(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET__unused, value)
    }
}

/**
 * Data for an event describing the configured shared stores.
 */
object lore_shared_store_info_event_data_t {
    const val SIZE: Long = 56L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("use_automatically"),
        MemoryLayout.paddingLayout(7),
        lore_string_array_t.LAYOUT.withName("remote_urls"),
        lore_string_array_t.LAYOUT.withName("paths"),
        lore_uint8_array_t.LAYOUT.withName("exists"),
    ).withName("lore_shared_store_info_event_data_t") as StructLayout

    /**
     * Nonzero when a shared store is used automatically for the repository.
     */
    const val OFFSET_use_automatically: Long = 0L
    fun use_automatically(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_use_automatically)
    fun use_automatically(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_use_automatically, value)
    }

    /**
     * Remote URLs of the shared stores.
     */
    const val OFFSET_remote_urls: Long = 8L
    fun remote_urls(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_urls, 16L)

    /**
     * Filesystem paths of the shared stores.
     */
    const val OFFSET_paths: Long = 24L
    fun paths(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_paths, 16L)

    /**
     * Per-store flag, nonzero when the store exists on disk.
     */
    const val OFFSET_exists: Long = 40L
    fun exists(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_exists, 16L)
}

/**
 * Arguments for setting whether to automatically use the shared store.
 */
object lore_shared_store_set_use_automatically_args_t {
    const val SIZE: Long = 1L
    const val ALIGNMENT: Long = 1L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("enabled"),
    ).withName("lore_shared_store_set_use_automatically_args_t") as StructLayout

    /**
     * Automatically use the shared store
     */
    const val OFFSET_enabled: Long = 0L
    fun enabled(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_enabled)
    fun enabled(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_enabled, value)
    }
}

/**
 * Arguments for `lore_storage_close`.
 */
object lore_storage_close_args_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
    ).withName("lore_storage_close_args_t") as StructLayout

    /**
     * Handle to release; from `LORE_EVENT_STORAGE_OPENED`
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)
}

/**
 * Arguments for `lore_storage_copy`.
 */
object lore_storage_copy_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_copy_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_copy_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Copy requests; each runs independently and emits its own `COPY_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_copy_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_copy_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `copy`. `source_partition` /
 * `target_partition` disambiguate the per-item source and target. The item's content hash is
 * preserved across the copy so only `source_address` is carried; `target_context` is the
 * destination tuple's context — the destination address is `(target_partition,
 * source_address.hash, target_context)`.
 */
object lore_storage_copy_item_complete_event_data_t {
    const val SIZE: Long = 112L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("source_partition"),
        lore_partition_t.LAYOUT.withName("target_partition"),
        lore_address_t.LAYOUT.withName("source_address"),
        lore_context_t.LAYOUT.withName("target_context"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_copy_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The partition the item was copied from.
     */
    const val OFFSET_source_partition: Long = 8L
    fun source_partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_partition, 16L)

    /**
     * The partition the item was copied to.
     */
    const val OFFSET_target_partition: Long = 24L
    fun target_partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_partition, 16L)

    /**
     * The address of the item in the source.
     */
    const val OFFSET_source_address: Long = 40L
    fun source_address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_address, 48L)

    /**
     * The context of the item in the target.
     */
    const val OFFSET_target_context: Long = 88L
    fun target_context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_context, 16L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 104L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One copy item — relocate content from `(source_partition, source_address)` to
 * `(target_partition, source_address.hash, target_context)`, preserving the content hash.
 */
object lore_storage_copy_item_t {
    const val SIZE: Long = 104L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("source_partition"),
        lore_partition_t.LAYOUT.withName("target_partition"),
        lore_address_t.LAYOUT.withName("source_address"),
        lore_context_t.LAYOUT.withName("target_context"),
    ).withName("lore_storage_copy_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `COPY_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Source partition; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_source_partition: Long = 8L
    fun source_partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_partition, 16L)

    /**
     * Destination partition; zero/default rejects, as does an exact `(source_partition, source
     * context)` match (no-op) — a different `target_context` enables in-partition duplication
     */
    const val OFFSET_target_partition: Long = 24L
    fun target_partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_partition, 16L)

    /**
     * Source content address; its `hash` carries over to the destination address unchanged
     */
    const val OFFSET_source_address: Long = 40L
    fun source_address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_source_address, 48L)

    /**
     * Dedup tag for the destination address `(target_partition, source_address.hash,
     * target_context)`; may match the source tag or re-tag the payload
     */
    const val OFFSET_target_context: Long = 88L
    fun target_context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_target_context, 16L)
}

/**
 * Arguments for `lore_storage_flush`.
 */
object lore_storage_flush_args_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
    ).withName("lore_storage_flush_args_t") as StructLayout

    /**
     * Open handle whose pending writes to flush
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)
}

/**
 * Arguments for `lore_storage_get`.
 */
object lore_storage_get_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_get_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_get_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Addresses to read; each runs independently and emits its own event sequence
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * Per-fragment (or single-buffer) payload event for `get`. The `bytes`
 * view is valid only during the callback invocation.
 */
object lore_storage_get_data_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_LONG.withName("offset"),
        lore_bytes_t.LAYOUT.withName("bytes"),
    ).withName("lore_storage_get_data_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The byte offset of this payload within the item's content.
     */
    const val OFFSET_offset: Long = 56L
    fun offset(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_offset)
    fun offset(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_offset, value)
    }

    /**
     * The payload bytes for this part of the item.
     */
    const val OFFSET_bytes: Long = 64L
    fun bytes(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_bytes, 16L)
}

/**
 * Arguments for `lore_storage_get_file`.
 */
object lore_storage_get_file_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_get_file_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_get_file_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Addresses and destination paths; each runs independently
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_get_file_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_get_file_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * One `get_file` item — read content at `(partition, address)` and
 * write it to the file at `path`.
 */
object lore_storage_get_file_item_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_address_t.LAYOUT.withName("address"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("local_cache"),
        MemoryLayout.paddingLayout(7),
    ).withName("lore_storage_get_file_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `GET_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition to read from; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Content address to read; `hash == Hash::default()` truncates `path` to zero bytes
     */
    const val OFFSET_address: Long = 24L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Destination path; empty rejects with `INVALID_ARGUMENTS`. Multi-fragment writes
     * stage via `<path>.loretmp` then atomically rename
     */
    const val OFFSET_path: Long = 72L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Cache fetched fragments back to the local store, not just write them to `path`
     */
    const val OFFSET_local_cache: Long = 88L
    fun local_cache(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_cache)
    fun local_cache(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_cache, value)
    }
}

/**
 * Leading event for each regular `get` item. Reports the total
 * reassembled content size before any `GET_DATA` events arrive.
 */
object lore_storage_get_header_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_LONG.withName("size_content"),
    ).withName("lore_storage_get_header_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The total reassembled content size in bytes.
     */
    const val OFFSET_size_content: Long = 56L
    fun size_content(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_size_content)
    fun size_content(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_size_content, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_get_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_get_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `get` and `get_file`. For `get_file` this
 * is emitted without any preceding `HEADER`/`DATA` events — the payload
 * is written directly to the filesystem.
 */
object lore_storage_get_item_complete_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_get_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 56L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One get item — the `(partition, address)` to read.
 */
object lore_storage_get_item_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_BYTE.withName("streaming"),
        ValueLayout.JAVA_BYTE.withName("local_cache"),
        MemoryLayout.paddingLayout(6),
    ).withName("lore_storage_get_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in every event for this item
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition to read from; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Content address to read; `hash == Hash::default()` short-circuits to an empty buffer
     */
    const val OFFSET_address: Long = 24L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * Stream one `GET_DATA` per leaf fragment instead of a single reassembled buffer
     */
    const val OFFSET_streaming: Long = 72L
    fun streaming(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_streaming)
    fun streaming(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_streaming, value)
    }

    /**
     * Cache fetched bytes back to the local store even without the producer's
     * `PayloadLocalCachePriority` hint
     */
    const val OFFSET_local_cache: Long = 73L
    fun local_cache(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_cache)
    fun local_cache(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_cache, value)
    }
}

/**
 * Arguments for `lore_storage_get_metadata`.
 */
object lore_storage_get_metadata_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_get_metadata_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_get_metadata_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Addresses to look up; each runs independently and emits its own `GET_METADATA_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_get_metadata_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_get_metadata_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `get_metadata`. On success `fragment` is
 * valid and `error_code == None`; on miss `error_code == ADDRESS_NOT_FOUND`.
 * Mirrors `LoreStorageGetItemCompleteEventData`'s shape minus the absence of
 * any preceding `GET_HEADER` / `GET_DATA` events — `get_metadata` carries no
 * payload bytes.
 */
object lore_storage_get_metadata_item_complete_event_data_t {
    const val SIZE: Long = 80L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        lore_fragment_t.LAYOUT.withName("fragment"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_get_metadata_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The metadata fragment for the item.
     */
    const val OFFSET_fragment: Long = 56L
    fun fragment(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_fragment, 16L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 72L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One `get_metadata` item — the `(partition, address)` to look up.
 */
object lore_storage_get_metadata_item_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_address_t.LAYOUT.withName("address"),
    ).withName("lore_storage_get_metadata_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `GET_METADATA_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition to look up; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Content address to look up; `hash == Hash::default()` short-circuits to an empty fragment
     */
    const val OFFSET_address: Long = 24L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)
}

/**
 * Arguments for `lore_storage_mutable_compare_and_swap`.
 */
object lore_storage_mutable_compare_and_swap_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_mutable_compare_and_swap_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_mutable_compare_and_swap_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Swaps to perform; each runs independently and emits its own `MUTABLE_COMPARE_AND_SWAP_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_mutable_compare_and_swap_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_mutable_compare_and_swap_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `mutable_compare_and_swap`. `previous` is the value the key held
 * before the swap (equal to the caller's `expected` when the swap took effect, otherwise the
 * actual current value). `error_code == None` on success.
 */
object lore_storage_mutable_compare_and_swap_item_complete_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_hash_t.LAYOUT.withName("previous"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_compare_and_swap_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The value the key held before the swap.
     */
    const val OFFSET_previous: Long = 8L
    fun previous(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_previous, 32L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 40L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One `mutable_compare_and_swap` item — the `(partition, key, expected, value, key_type)` swap.
 */
object lore_storage_mutable_compare_and_swap_item_t {
    const val SIZE: Long = 128L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_hash_t.LAYOUT.withName("key"),
        lore_hash_t.LAYOUT.withName("expected"),
        lore_hash_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_INT.withName("key_type"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_compare_and_swap_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `MUTABLE_COMPARE_AND_SWAP_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition (repository) to act on; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Key to swap
     */
    const val OFFSET_key: Long = 24L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 32L)

    /**
     * Value the key must currently hold for the swap to take effect (null matches an absent key)
     */
    const val OFFSET_expected: Long = 56L
    fun expected(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_expected, 32L)

    /**
     * Value to store when the swap takes effect; the null value removes the key
     */
    const val OFFSET_value: Long = 88L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 32L)

    /**
     * Kind of value the key refers to
     */
    const val OFFSET_key_type: Long = 120L
    fun key_type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_key_type)
    fun key_type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_key_type, value)
    }
}

/**
 * Arguments for `lore_storage_mutable_list`.
 */
object lore_storage_mutable_list_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_mutable_list_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_mutable_list_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Listings to perform; each runs independently and emits its own entries and terminal event
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * One `(key, value)` pair emitted by `mutable_list`, before the item's terminal event.
 */
object lore_storage_mutable_list_entry_event_data_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_hash_t.LAYOUT.withName("key"),
        lore_hash_t.LAYOUT.withName("value"),
    ).withName("lore_storage_mutable_list_entry_event_data_t") as StructLayout

    /**
     * Correlation id of the listing item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The key of this entry.
     */
    const val OFFSET_key: Long = 8L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 32L)

    /**
     * The value stored for the key.
     */
    const val OFFSET_value: Long = 40L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 32L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_mutable_list_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_mutable_list_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `mutable_list`, emitted after every `MUTABLE_LIST_ENTRY` for the
 * item. `error_code == None` once the listing completes.
 */
object lore_storage_mutable_list_item_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_list_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the listing item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One `mutable_list` item — the `(partition, key_type)` to list.
 */
object lore_storage_mutable_list_item_t {
    const val SIZE: Long = 32L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        ValueLayout.JAVA_INT.withName("key_type"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_list_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back on every entry and the terminal event
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition (repository) to list; the zero/default partition lists every accessible partition
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Kind of value to list
     */
    const val OFFSET_key_type: Long = 24L
    fun key_type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_key_type)
    fun key_type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_key_type, value)
    }
}

/**
 * Arguments for `lore_storage_mutable_load`.
 */
object lore_storage_mutable_load_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_mutable_load_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_mutable_load_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Keys to read; each runs independently and emits its own `MUTABLE_LOAD_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_mutable_load_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_mutable_load_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `mutable_load`. On success `error_code == None` and `value` is
 * the loaded value hash (`Hash::default()` when the key holds a null/removed value); on miss
 * `error_code == ADDRESS_NOT_FOUND` and `value` is zero.
 */
object lore_storage_mutable_load_item_complete_event_data_t {
    const val SIZE: Long = 48L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_hash_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_load_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The value stored for the key.
     */
    const val OFFSET_value: Long = 8L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 32L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 40L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One `mutable_load` item — the `(partition, key, key_type)` to read.
 */
object lore_storage_mutable_load_item_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_hash_t.LAYOUT.withName("key"),
        ValueLayout.JAVA_INT.withName("key_type"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_load_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `MUTABLE_LOAD_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition (repository) to read from; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Key to read
     */
    const val OFFSET_key: Long = 24L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 32L)

    /**
     * Kind of value the key refers to
     */
    const val OFFSET_key_type: Long = 56L
    fun key_type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_key_type)
    fun key_type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_key_type, value)
    }
}

/**
 * Arguments for `lore_storage_mutable_store`.
 */
object lore_storage_mutable_store_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_mutable_store_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_mutable_store_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Key-value pairs to write; each runs independently and emits its own `MUTABLE_STORE_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_mutable_store_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_mutable_store_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `mutable_store`. `error_code == None` on a successful store.
 */
object lore_storage_mutable_store_item_complete_event_data_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_store_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 8L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One `mutable_store` item — the `(partition, key, value, key_type)` to write.
 */
object lore_storage_mutable_store_item_t {
    const val SIZE: Long = 96L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_hash_t.LAYOUT.withName("key"),
        lore_hash_t.LAYOUT.withName("value"),
        ValueLayout.JAVA_INT.withName("key_type"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_mutable_store_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `MUTABLE_STORE_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition (repository) to write to; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Key to write
     */
    const val OFFSET_key: Long = 24L
    fun key(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_key, 32L)

    /**
     * Value to store; the null value (`Hash::default()`) removes the key
     */
    const val OFFSET_value: Long = 56L
    fun value(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_value, 32L)

    /**
     * Kind of value the key refers to
     */
    const val OFFSET_key_type: Long = 88L
    fun key_type(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_key_type)
    fun key_type(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_key_type, value)
    }
}

/**
 * Arguments for `lore_storage_obliterate`.
 */
object lore_storage_obliterate_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_obliterate_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_obliterate_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Addresses to delete; each runs independently and emits its own `OBLITERATE_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_obliterate_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_obliterate_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `obliterate`. `local_success` / `remote_success` report
 * whether the corresponding side completed without error. `local_skipped` / `remote_skipped`
 * report whether the corresponding side was suppressed up front by the handle's bound flags
 * (`globals.offline`/`local`/`remote`) — when a side is skipped, its `_success` flag is `0`
 * rather than a misleading `1`. `error_code` is populated if either side that DID run
 * failed.
 */
object lore_storage_obliterate_item_complete_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_BYTE.withName("local_success"),
        ValueLayout.JAVA_BYTE.withName("remote_success"),
        ValueLayout.JAVA_BYTE.withName("local_skipped"),
        ValueLayout.JAVA_BYTE.withName("remote_skipped"),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_storage_obliterate_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * 1 when the local side completed without error.
     */
    const val OFFSET_local_success: Long = 56L
    fun local_success(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_success)
    fun local_success(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_success, value)
    }

    /**
     * 1 when the remote side completed without error.
     */
    const val OFFSET_remote_success: Long = 57L
    fun remote_success(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_success)
    fun remote_success(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_success, value)
    }

    /**
     * 1 when the local side was skipped.
     */
    const val OFFSET_local_skipped: Long = 58L
    fun local_skipped(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_skipped)
    fun local_skipped(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_skipped, value)
    }

    /**
     * 1 when the remote side was skipped.
     */
    const val OFFSET_remote_skipped: Long = 59L
    fun remote_skipped(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_skipped)
    fun remote_skipped(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_skipped, value)
    }

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 60L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One obliterate item — the `(partition, address)` to delete.
 */
object lore_storage_obliterate_item_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_address_t.LAYOUT.withName("address"),
    ).withName("lore_storage_obliterate_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `OBLITERATE_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition to delete from; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Content address to delete; absence on a side is idempotent success for that side
     */
    const val OFFSET_address: Long = 24L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)
}

/**
 * Arguments for `lore_storage_open`.
 */
object lore_storage_open_args_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("repository_path"),
        ValueLayout.JAVA_BYTE.withName("in_memory"),
        MemoryLayout.paddingLayout(7),
        lore_storage_remote_config_t.LAYOUT.withName("remote_config"),
        ValueLayout.JAVA_BYTE.withName("has_remote_config"),
        MemoryLayout.paddingLayout(7),
        ValueLayout.JAVA_LONG.withName("cache_target_bytes"),
        ValueLayout.JAVA_LONG.withName("cache_target_fragments"),
    ).withName("lore_storage_open_args_t") as StructLayout

    /**
     * Path to an existing lore repository; must be empty when `in_memory` is set
     */
    const val OFFSET_repository_path: Long = 0L
    fun repository_path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_repository_path, 16L)

    /**
     * Open a fresh in-memory store; `repository_path` must then be empty
     */
    const val OFFSET_in_memory: Long = 16L
    fun in_memory(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_in_memory)
    fun in_memory(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_in_memory, value)
    }

    /**
     * Remote endpoint binding for ops that consult a peer; honored only when `has_remote_config` is set
     */
    const val OFFSET_remote_config: Long = 24L
    fun remote_config(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_config, 16L)

    /**
     * Activate `remote_config`; otherwise the handle has no remote
     */
    const val OFFSET_has_remote_config: Long = 40L
    fun has_remote_config(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_has_remote_config)
    fun has_remote_config(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_has_remote_config, value)
    }

    /**
     * Soft cap on total immutable-store bytes (compactor target). A non-zero cache target enables
     * incremental background GC for the handle; `0` then selects the default. Shared disk backends
     * inherit the first opener's value
     */
    const val OFFSET_cache_target_bytes: Long = 48L
    fun cache_target_bytes(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_cache_target_bytes)
    fun cache_target_bytes(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_cache_target_bytes, value)
    }

    /**
     * Soft cap on immutable-store fragment count (evictor target). A non-zero cache target enables
     * incremental background GC for the handle; `0` then selects the default
     */
    const val OFFSET_cache_target_fragments: Long = 56L
    fun cache_target_fragments(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_cache_target_fragments)
    fun cache_target_fragments(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_cache_target_fragments, value)
    }
}

/**
 * Delivered on successful `lore_storage_open`. Carries the handle id the
 * caller must pass to subsequent ops against this store.
 */
object lore_storage_opened_event_data_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("handle_id"),
    ).withName("lore_storage_opened_event_data_t") as StructLayout

    /**
     * Handle id for the opened store.
     */
    const val OFFSET_handle_id: Long = 0L
    fun handle_id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_handle_id)
    fun handle_id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_handle_id, value)
    }
}

/**
 * Arguments for `lore_storage_put`.
 */
object lore_storage_put_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_put_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_put_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Buffers to store; each runs independently and emits its own `PUT_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * Arguments for `lore_storage_put_file`.
 */
object lore_storage_put_file_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_put_file_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_put_file_args_t") as StructLayout

    /**
     * Open storage handle
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Files to store; each runs independently and emits its own `PUT_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_put_file_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_put_file_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * One `put_file` item — read the file at `path` and store it at
 * `(partition, context)`.
 */
object lore_storage_put_file_item_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_context_t.LAYOUT.withName("context"),
        lore_string_t.LAYOUT.withName("path"),
        ValueLayout.JAVA_BYTE.withName("remote_write"),
        ValueLayout.JAVA_BYTE.withName("local_cache"),
        MemoryLayout.paddingLayout(6),
        ValueLayout.JAVA_LONG.withName("fixed_size_chunk"),
    ).withName("lore_storage_put_file_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `PUT_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Target partition; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Dedup tag stored alongside the content hash in the resulting address
     */
    const val OFFSET_context: Long = 24L
    fun context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_context, 16L)

    /**
     * Source path; empty, missing, or non-file rejects with `INVALID_ARGUMENTS`; a zero-length
     * file maps to the zero-hash address
     */
    const val OFFSET_path: Long = 40L
    fun path(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_path, 16L)

    /**
     * Opt into remote upload — honored on the remote path, ignored local-only
     */
    const val OFFSET_remote_write: Long = 56L
    fun remote_write(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_write)
    fun remote_write(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_write, value)
    }

    /**
     * Tag the resulting fragment with `PayloadLocalCachePriority` so future remote reads always cache it locally
     */
    const val OFFSET_local_cache: Long = 57L
    fun local_cache(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_cache)
    fun local_cache(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_cache, value)
    }

    /**
     * Leaf fragment size cap for large files; `0` lets `write_content` choose
     */
    const val OFFSET_fixed_size_chunk: Long = 64L
    fun fixed_size_chunk(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fixed_size_chunk)
    fun fixed_size_chunk(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fixed_size_chunk, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_put_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_put_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `put` and `put_file`. On success
 * `error_code == None` and `address` is the computed content hash; on
 * failure `error_code` is populated and `address` is zero.
 */
object lore_storage_put_item_complete_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_INT.withName("error_code"),
        MemoryLayout.paddingLayout(4),
    ).withName("lore_storage_put_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The computed content address of the stored item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 56L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One put item — a buffer to hash and store at `(partition, context)`.
 */
object lore_storage_put_item_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_context_t.LAYOUT.withName("context"),
        lore_bytes_t.LAYOUT.withName("data"),
        ValueLayout.JAVA_BYTE.withName("remote_write"),
        ValueLayout.JAVA_BYTE.withName("local_cache"),
        MemoryLayout.paddingLayout(6),
        ValueLayout.JAVA_LONG.withName("fixed_size_chunk"),
    ).withName("lore_storage_put_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `PUT_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Target partition; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Dedup tag stored alongside the content hash in the resulting address
     */
    const val OFFSET_context: Long = 24L
    fun context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_context, 16L)

    /**
     * Borrowed view into caller memory; bytes must live until `Complete` fires
     */
    const val OFFSET_data: Long = 40L
    fun data(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_data, 16L)

    /**
     * Opt into remote upload — honored on the remote path, ignored local-only
     */
    const val OFFSET_remote_write: Long = 56L
    fun remote_write(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_remote_write)
    fun remote_write(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_remote_write, value)
    }

    /**
     * Tag the fragment with `PayloadLocalCachePriority` so future remote reads always cache it locally
     */
    const val OFFSET_local_cache: Long = 57L
    fun local_cache(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_local_cache)
    fun local_cache(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_local_cache, value)
    }

    /**
     * Leaf fragment size cap for large buffers; `0` lets `write_content` choose. Ignored
     * for buffers under `FRAGMENT_SIZE_THRESHOLD`
     */
    const val OFFSET_fixed_size_chunk: Long = 64L
    fun fixed_size_chunk(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_fixed_size_chunk)
    fun fixed_size_chunk(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_fixed_size_chunk, value)
    }
}

/**
 * Remote endpoint configuration for a storage handle.
 */
object lore_storage_remote_config_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("remote_url"),
    ).withName("lore_storage_remote_config_t") as StructLayout

    /**
     * gRPC endpoint of the peer storage service; authenticated with the open call's `globals.identity`
     */
    const val OFFSET_remote_url: Long = 0L
    fun remote_url(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_remote_url, 16L)
}

/**
 * Arguments for `lore_storage_upload`.
 */
object lore_storage_upload_args_t {
    const val SIZE: Long = 24L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_store_t.LAYOUT.withName("handle"),
        lore_storage_upload_item_array_t.LAYOUT.withName("items"),
    ).withName("lore_storage_upload_args_t") as StructLayout

    /**
     * Open storage handle; must have been opened with `remote_config`
     */
    const val OFFSET_handle: Long = 0L
    fun handle(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_handle, 8L)

    /**
     * Addresses to push to remote; each runs independently and emits its own `UPLOAD_ITEM_COMPLETE`
     */
    const val OFFSET_items: Long = 8L
    fun items(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_items, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_storage_upload_item_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_storage_upload_item_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * Terminal per-item event for `upload`. `already_durable` is 1 when the
 * item was already flagged durable and no upload was performed.
 */
object lore_storage_upload_item_complete_event_data_t {
    const val SIZE: Long = 64L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_address_t.LAYOUT.withName("address"),
        ValueLayout.JAVA_BYTE.withName("already_durable"),
        MemoryLayout.paddingLayout(3),
        ValueLayout.JAVA_INT.withName("error_code"),
    ).withName("lore_storage_upload_item_complete_event_data_t") as StructLayout

    /**
     * Correlation id of the item.
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * The content address of the item.
     */
    const val OFFSET_address: Long = 8L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)

    /**
     * 1 when the item was already durable and no upload was performed.
     */
    const val OFFSET_already_durable: Long = 56L
    fun already_durable(struct: MemorySegment): Byte =
        struct.get(ValueLayout.JAVA_BYTE, OFFSET_already_durable)
    fun already_durable(struct: MemorySegment, value: Byte) {
        struct.set(ValueLayout.JAVA_BYTE, OFFSET_already_durable, value)
    }

    /**
     * The outcome for the item.
     */
    const val OFFSET_error_code: Long = 60L
    fun error_code(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_error_code)
    fun error_code(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_error_code, value)
    }
}

/**
 * One upload item — the `(partition, address)` of locally-stored content to push to remote.
 */
object lore_storage_upload_item_t {
    const val SIZE: Long = 72L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("id"),
        lore_partition_t.LAYOUT.withName("partition"),
        lore_address_t.LAYOUT.withName("address"),
    ).withName("lore_storage_upload_item_t") as StructLayout

    /**
     * Caller-chosen id echoed back in `UPLOAD_ITEM_COMPLETE`
     */
    const val OFFSET_id: Long = 0L
    fun id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_id)
    fun id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_id, value)
    }

    /**
     * Partition of the local content to push; the zero/default partition rejects with `INVALID_ARGUMENTS`
     */
    const val OFFSET_partition: Long = 8L
    fun partition(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_partition, 16L)

    /**
     * Local content address to push; `hash == Hash::default()` is no-op success with `already_durable=1`
     */
    const val OFFSET_address: Long = 24L
    fun address(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_address, 48L)
}

/**
 * Opaque handle to an open content-addressed storage instance.
 */
object lore_store_t {
    const val SIZE: Long = 8L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("handle_id"),
    ).withName("lore_store_t") as StructLayout

    /**
     * Registry key; `0` is the reserved invalid/unregistered sentinel (zero-init = null handle)
     */
    const val OFFSET_handle_id: Long = 0L
    fun handle_id(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_handle_id)
    fun handle_id(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_handle_id, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_string_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_string_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * A string described by a pointer to its character data and a length, holding
 * text as a sequence of bytes.
 * 
 * The text is UTF-8 by convention, but the bytes are never validated on
 * construction: a string carrying any other encoding is accepted here and
 * rejected by whichever verb needs to read it as text. The length field counts
 * the bytes before the trailing NUL. An empty string is a NULL pointer with
 * length 0, and a length of 0 means the string is empty.
 */
object lore_string_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("string"),
        ValueLayout.JAVA_LONG.withName("length"),
    ).withName("lore_string_t") as StructLayout

    /**
     * Pointer to the start of the character data.
     */
    const val OFFSET_string: Long = 0L
    fun string(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_string)
    fun string(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_string, value)
    }

    /**
     * Number of bytes in the string, not counting any trailing terminator.
     */
    const val OFFSET_length: Long = 8L
    fun length(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_length)
    fun length(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_length, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_trace_location_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_trace_location_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * One captured trace entry, carried across the FFI boundary as structured
 * data.
 * 
 * It records the source location where an error was created or forwarded:
 * the file path, line, column, and an optional per-location context string.
 * The struct owns its `file` and `context` strings. `Clone` deep-clones them
 * and `Drop` frees them.
 * 
 * Memory: the library owns this data. The pointers a consumer reads from this
 * struct are valid only for the single callback invocation that delivers the
 * event. A consumer that keeps any of this data must copy it out before the
 * callback returns.
 */
object lore_trace_location_t {
    const val SIZE: Long = 40L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        lore_string_t.LAYOUT.withName("file"),
        ValueLayout.JAVA_INT.withName("line"),
        ValueLayout.JAVA_INT.withName("column"),
        lore_string_t.LAYOUT.withName("context"),
    ).withName("lore_trace_location_t") as StructLayout

    /**
     * The source file path.
     */
    const val OFFSET_file: Long = 0L
    fun file(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_file, 16L)

    /**
     * The line number in the source file.
     */
    const val OFFSET_line: Long = 16L
    fun line(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_line)
    fun line(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_line, value)
    }

    /**
     * The column number in the source file.
     */
    const val OFFSET_column: Long = 20L
    fun column(struct: MemorySegment): Int =
        struct.get(ValueLayout.JAVA_INT, OFFSET_column)
    fun column(struct: MemorySegment, value: Int) {
        struct.set(ValueLayout.JAVA_INT, OFFSET_column, value)
    }

    /**
     * The context describing the operation at this location, or an empty
     * string when the location has none.
     */
    const val OFFSET_context: Long = 24L
    fun context(struct: MemorySegment): MemorySegment =
        struct.asSlice(OFFSET_context, 16L)
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_uint32_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_uint32_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/**
 * A contiguous array of elements described by a pointer and a count.
 * Holds zero or more values of the element type laid out one after another.
 */
object lore_uint8_array_t {
    const val SIZE: Long = 16L
    const val ALIGNMENT: Long = 8L

    val LAYOUT: StructLayout = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("ptr"),
        ValueLayout.JAVA_LONG.withName("count"),
    ).withName("lore_uint8_array_t") as StructLayout

    /**
     * Pointer to the first element.
     */
    const val OFFSET_ptr: Long = 0L
    fun ptr(struct: MemorySegment): MemorySegment =
        struct.get(ValueLayout.ADDRESS, OFFSET_ptr)
    fun ptr(struct: MemorySegment, value: MemorySegment) {
        struct.set(ValueLayout.ADDRESS, OFFSET_ptr, value)
    }

    /**
     * Number of elements in the array.
     */
    const val OFFSET_count: Long = 8L
    fun count(struct: MemorySegment): Long =
        struct.get(ValueLayout.JAVA_LONG, OFFSET_count)
    fun count(struct: MemorySegment, value: Long) {
        struct.set(ValueLayout.JAVA_LONG, OFFSET_count, value)
    }
}

/** Every generated layout, so a test can force-initialise them all. */
object AllLayouts {
    val layouts: List<StructLayout> = listOf(
        lore_address_t.LAYOUT,
        lore_auth_clear_args_t.LAYOUT,
        lore_auth_identity_event_data_t.LAYOUT,
        lore_auth_list_args_t.LAYOUT,
        lore_auth_local_user_info_args_t.LAYOUT,
        lore_auth_login_interactive_args_t.LAYOUT,
        lore_auth_login_with_token_args_t.LAYOUT,
        lore_auth_logout_args_t.LAYOUT,
        lore_auth_url_event_data_t.LAYOUT,
        lore_auth_user_info_args_t.LAYOUT,
        lore_auth_user_info_event_data_t.LAYOUT,
        lore_auth_user_token_event_data_t.LAYOUT,
        lore_binary_t.LAYOUT,
        lore_branch_archive_args_t.LAYOUT,
        lore_branch_archive_event_data_t.LAYOUT,
        lore_branch_create_args_t.LAYOUT,
        lore_branch_create_event_data_t.LAYOUT,
        lore_branch_diff_args_t.LAYOUT,
        lore_branch_diff_begin_event_data_t.LAYOUT,
        lore_branch_diff_change_begin_event_data_t.LAYOUT,
        lore_branch_diff_change_end_event_data_t.LAYOUT,
        lore_branch_diff_change_event_data_t.LAYOUT,
        lore_branch_diff_conflict_begin_event_data_t.LAYOUT,
        lore_branch_diff_conflict_end_event_data_t.LAYOUT,
        lore_branch_diff_conflict_event_data_t.LAYOUT,
        lore_branch_diff_end_event_data_t.LAYOUT,
        lore_branch_diff_node_data_t.LAYOUT,
        lore_branch_info_args_t.LAYOUT,
        lore_branch_info_event_data_t.LAYOUT,
        lore_branch_latest_list_entry_event_data_t.LAYOUT,
        lore_branch_list_args_t.LAYOUT,
        lore_branch_list_begin_event_data_t.LAYOUT,
        lore_branch_list_end_event_data_t.LAYOUT,
        lore_branch_list_entry_event_data_t.LAYOUT,
        lore_branch_merge_abort_args_t.LAYOUT,
        lore_branch_merge_abort_begin_event_data_t.LAYOUT,
        lore_branch_merge_abort_end_event_data_t.LAYOUT,
        lore_branch_merge_conflict_file_event_data_t.LAYOUT,
        lore_branch_merge_into_args_t.LAYOUT,
        lore_branch_merge_into_file_begin_event_data_t.LAYOUT,
        lore_branch_merge_into_file_end_event_data_t.LAYOUT,
        lore_branch_merge_into_file_event_data_t.LAYOUT,
        lore_branch_merge_into_fragment_begin_event_data_t.LAYOUT,
        lore_branch_merge_into_fragment_end_event_data_t.LAYOUT,
        lore_branch_merge_into_fragment_progress_event_data_t.LAYOUT,
        lore_branch_merge_into_revision_event_data_t.LAYOUT,
        lore_branch_merge_into_sync_begin_event_data_t.LAYOUT,
        lore_branch_merge_into_sync_end_event_data_t.LAYOUT,
        lore_branch_merge_link_skipped_event_data_t.LAYOUT,
        lore_branch_merge_resolve_args_t.LAYOUT,
        lore_branch_merge_resolve_file_event_data_t.LAYOUT,
        lore_branch_merge_resolve_mine_args_t.LAYOUT,
        lore_branch_merge_resolve_revision_event_data_t.LAYOUT,
        lore_branch_merge_resolve_theirs_args_t.LAYOUT,
        lore_branch_merge_restart_args_t.LAYOUT,
        lore_branch_merge_start_args_t.LAYOUT,
        lore_branch_merge_start_begin_event_data_t.LAYOUT,
        lore_branch_merge_start_end_event_data_t.LAYOUT,
        lore_branch_merge_unresolve_args_t.LAYOUT,
        lore_branch_merge_unresolve_file_event_data_t.LAYOUT,
        lore_branch_merge_unresolve_revision_event_data_t.LAYOUT,
        lore_branch_metadata_clear_args_t.LAYOUT,
        lore_branch_metadata_get_args_t.LAYOUT,
        lore_branch_metadata_set_args_t.LAYOUT,
        lore_branch_multiple_instance_event_data_t.LAYOUT,
        lore_branch_point_array_t.LAYOUT,
        lore_branch_point_t.LAYOUT,
        lore_branch_protect_args_t.LAYOUT,
        lore_branch_protect_event_data_t.LAYOUT,
        lore_branch_push_args_t.LAYOUT,
        lore_branch_push_branch_create_begin_event_data_t.LAYOUT,
        lore_branch_push_branch_create_end_event_data_t.LAYOUT,
        lore_branch_push_event_data_t.LAYOUT,
        lore_branch_push_fragment_begin_event_data_t.LAYOUT,
        lore_branch_push_fragment_end_event_data_t.LAYOUT,
        lore_branch_push_fragment_progress_event_data_t.LAYOUT,
        lore_branch_push_revision_push_begin_event_data_t.LAYOUT,
        lore_branch_push_revision_push_end_event_data_t.LAYOUT,
        lore_branch_push_revision_push_update_event_data_t.LAYOUT,
        lore_branch_push_revision_update_begin_event_data_t.LAYOUT,
        lore_branch_push_revision_update_end_event_data_t.LAYOUT,
        lore_branch_reset_args_t.LAYOUT,
        lore_branch_reset_event_data_t.LAYOUT,
        lore_branch_switch_args_t.LAYOUT,
        lore_branch_switch_begin_event_data_t.LAYOUT,
        lore_branch_switch_data_t.LAYOUT,
        lore_branch_switch_end_event_data_t.LAYOUT,
        lore_branch_unprotect_args_t.LAYOUT,
        lore_branch_unprotect_event_data_t.LAYOUT,
        lore_bytes_t.LAYOUT,
        lore_cherry_pick_abort_begin_event_data_t.LAYOUT,
        lore_cherry_pick_abort_end_event_data_t.LAYOUT,
        lore_cherry_pick_conflict_file_event_data_t.LAYOUT,
        lore_cherry_pick_resolve_file_event_data_t.LAYOUT,
        lore_cherry_pick_resolve_revision_event_data_t.LAYOUT,
        lore_cherry_pick_start_begin_event_data_t.LAYOUT,
        lore_cherry_pick_start_end_event_data_t.LAYOUT,
        lore_cherry_pick_unresolve_file_event_data_t.LAYOUT,
        lore_cherry_pick_unresolve_revision_event_data_t.LAYOUT,
        lore_compaction_begin_event_data_t.LAYOUT,
        lore_compaction_end_event_data_t.LAYOUT,
        lore_compaction_progress_event_data_t.LAYOUT,
        lore_complete_event_data_t.LAYOUT,
        lore_context_t.LAYOUT,
        lore_dependency_resolve_begin_event_data_t.LAYOUT,
        lore_dependency_resolve_end_event_data_t.LAYOUT,
        lore_dependency_resolve_item_event_data_t.LAYOUT,
        lore_end_event_data_t.LAYOUT,
        lore_error_detail_t.LAYOUT,
        lore_error_event_data_t.LAYOUT,
        lore_event_callback_config_t.LAYOUT,
        lore_event_t.LAYOUT,
        lore_eviction_begin_event_data_t.LAYOUT,
        lore_eviction_end_event_data_t.LAYOUT,
        lore_eviction_progress_event_data_t.LAYOUT,
        lore_file_dependency_add_args_t.LAYOUT,
        lore_file_dependency_add_begin_event_data_t.LAYOUT,
        lore_file_dependency_add_end_event_data_t.LAYOUT,
        lore_file_dependency_add_entry_event_data_t.LAYOUT,
        lore_file_dependency_list_args_t.LAYOUT,
        lore_file_dependency_list_begin_event_data_t.LAYOUT,
        lore_file_dependency_list_end_event_data_t.LAYOUT,
        lore_file_dependency_list_entry_event_data_t.LAYOUT,
        lore_file_dependency_list_file_end_event_data_t.LAYOUT,
        lore_file_dependency_list_file_event_data_t.LAYOUT,
        lore_file_dependency_remove_args_t.LAYOUT,
        lore_file_dependency_remove_begin_event_data_t.LAYOUT,
        lore_file_dependency_remove_end_event_data_t.LAYOUT,
        lore_file_dependency_remove_entry_event_data_t.LAYOUT,
        lore_file_diff_args_t.LAYOUT,
        lore_file_diff_event_data_t.LAYOUT,
        lore_file_dirty_args_t.LAYOUT,
        lore_file_dirty_copy_args_t.LAYOUT,
        lore_file_dirty_move_args_t.LAYOUT,
        lore_file_dump_args_t.LAYOUT,
        lore_file_dump_event_data_t.LAYOUT,
        lore_file_hash_args_t.LAYOUT,
        lore_file_hash_event_data_t.LAYOUT,
        lore_file_history_args_t.LAYOUT,
        lore_file_history_event_data_t.LAYOUT,
        lore_file_info_args_t.LAYOUT,
        lore_file_info_event_data_t.LAYOUT,
        lore_file_metadata_clear_args_t.LAYOUT,
        lore_file_metadata_get_args_t.LAYOUT,
        lore_file_metadata_list_args_t.LAYOUT,
        lore_file_metadata_set_args_t.LAYOUT,
        lore_file_obliterate_args_t.LAYOUT,
        lore_file_obliterate_event_data_t.LAYOUT,
        lore_file_reset_args_t.LAYOUT,
        lore_file_reset_begin_event_data_t.LAYOUT,
        lore_file_reset_count_data_t.LAYOUT,
        lore_file_reset_end_event_data_t.LAYOUT,
        lore_file_reset_file_event_data_t.LAYOUT,
        lore_file_reset_progress_event_data_t.LAYOUT,
        lore_file_reset_to_last_merged_args_t.LAYOUT,
        lore_file_stage_args_t.LAYOUT,
        lore_file_stage_begin_event_data_t.LAYOUT,
        lore_file_stage_count_data_t.LAYOUT,
        lore_file_stage_end_event_data_t.LAYOUT,
        lore_file_stage_file_event_data_t.LAYOUT,
        lore_file_stage_merge_args_t.LAYOUT,
        lore_file_stage_move_args_t.LAYOUT,
        lore_file_stage_progress_event_data_t.LAYOUT,
        lore_file_stage_revision_event_data_t.LAYOUT,
        lore_file_unstage_args_t.LAYOUT,
        lore_file_unstage_begin_event_data_t.LAYOUT,
        lore_file_unstage_count_data_t.LAYOUT,
        lore_file_unstage_end_event_data_t.LAYOUT,
        lore_file_unstage_file_event_data_t.LAYOUT,
        lore_file_unstage_progress_event_data_t.LAYOUT,
        lore_file_unstage_revision_event_data_t.LAYOUT,
        lore_file_write_args_t.LAYOUT,
        lore_file_write_event_data_t.LAYOUT,
        lore_filter_exclude_event_data_t.LAYOUT,
        lore_fragment_t.LAYOUT,
        lore_fragment_write_event_data_t.LAYOUT,
        lore_global_args_t.LAYOUT,
        lore_hash_t.LAYOUT,
        lore_instance_id_array_t.LAYOUT,
        lore_instance_id_t.LAYOUT,
        lore_layer_add_args_t.LAYOUT,
        lore_layer_add_event_data_t.LAYOUT,
        lore_layer_entry_event_data_t.LAYOUT,
        lore_layer_list_args_t.LAYOUT,
        lore_layer_remove_args_t.LAYOUT,
        lore_layer_remove_event_data_t.LAYOUT,
        lore_layer_staged_entry_event_data_t.LAYOUT,
        lore_link_add_args_t.LAYOUT,
        lore_link_change_event_data_t.LAYOUT,
        lore_link_entry_event_data_t.LAYOUT,
        lore_link_list_args_t.LAYOUT,
        lore_link_remove_args_t.LAYOUT,
        lore_link_staged_entry_event_data_t.LAYOUT,
        lore_link_update_args_t.LAYOUT,
        lore_lock_file_acquire_args_t.LAYOUT,
        lore_lock_file_acquire_begin_event_data_t.LAYOUT,
        lore_lock_file_acquire_event_data_t.LAYOUT,
        lore_lock_file_query_args_t.LAYOUT,
        lore_lock_file_query_begin_event_data_t.LAYOUT,
        lore_lock_file_query_event_data_t.LAYOUT,
        lore_lock_file_release_args_t.LAYOUT,
        lore_lock_file_release_begin_event_data_t.LAYOUT,
        lore_lock_file_release_event_data_t.LAYOUT,
        lore_lock_file_status_args_t.LAYOUT,
        lore_lock_file_status_begin_event_data_t.LAYOUT,
        lore_lock_file_status_event_data_t.LAYOUT,
        lore_log_config_t.LAYOUT,
        lore_log_event_data_t.LAYOUT,
        lore_maintenance_event_data_t.LAYOUT,
        lore_metadata_clear_file_event_data_t.LAYOUT,
        lore_metadata_clear_revision_event_data_t.LAYOUT,
        lore_metadata_event_data_t.LAYOUT,
        lore_metadata_t.LAYOUT,
        lore_metadata_type_array_t.LAYOUT,
        lore_notification_branch_created_event_data_t.LAYOUT,
        lore_notification_branch_deleted_event_data_t.LAYOUT,
        lore_notification_branch_pushed_event_data_t.LAYOUT,
        lore_notification_resource_locked_event_data_t.LAYOUT,
        lore_notification_resource_unlocked_event_data_t.LAYOUT,
        lore_notification_subscribe_args_t.LAYOUT,
        lore_notification_subscribed_event_data_t.LAYOUT,
        lore_notification_unsubscribe_args_t.LAYOUT,
        lore_notification_unsubscribed_event_data_t.LAYOUT,
        lore_partition_t.LAYOUT,
        lore_path_ignore_event_data_t.LAYOUT,
        lore_progress_event_data_t.LAYOUT,
        lore_repository_clone_args_t.LAYOUT,
        lore_repository_clone_begin_event_data_t.LAYOUT,
        lore_repository_clone_count_data_t.LAYOUT,
        lore_repository_clone_end_event_data_t.LAYOUT,
        lore_repository_clone_progress_event_data_t.LAYOUT,
        lore_repository_config_get_args_t.LAYOUT,
        lore_repository_config_get_event_data_t.LAYOUT,
        lore_repository_create_args_t.LAYOUT,
        lore_repository_create_event_data_t.LAYOUT,
        lore_repository_data_event_data_t.LAYOUT,
        lore_repository_dump_args_t.LAYOUT,
        lore_repository_dump_begin_event_data_t.LAYOUT,
        lore_repository_dump_end_event_data_t.LAYOUT,
        lore_repository_flush_args_t.LAYOUT,
        lore_repository_gc_args_t.LAYOUT,
        lore_repository_info_args_t.LAYOUT,
        lore_repository_instance_event_data_t.LAYOUT,
        lore_repository_instance_list_args_t.LAYOUT,
        lore_repository_instance_prune_args_t.LAYOUT,
        lore_repository_list_args_t.LAYOUT,
        lore_repository_list_entry_event_data_t.LAYOUT,
        lore_repository_metadata_clear_args_t.LAYOUT,
        lore_repository_metadata_get_args_t.LAYOUT,
        lore_repository_metadata_set_args_t.LAYOUT,
        lore_repository_release_args_t.LAYOUT,
        lore_repository_state_dump_event_data_t.LAYOUT,
        lore_repository_state_dump_node_event_data_t.LAYOUT,
        lore_repository_status_args_t.LAYOUT,
        lore_repository_status_count_event_data_t.LAYOUT,
        lore_repository_status_file_event_data_t.LAYOUT,
        lore_repository_status_revision_event_data_t.LAYOUT,
        lore_repository_status_summary_event_data_t.LAYOUT,
        lore_repository_store_immutable_query_args_t.LAYOUT,
        lore_repository_store_immutable_query_event_data_t.LAYOUT,
        lore_repository_update_path_args_t.LAYOUT,
        lore_repository_verify_fragment_event_data_t.LAYOUT,
        lore_repository_verify_fragment_match_event_data_array_t.LAYOUT,
        lore_repository_verify_fragment_match_event_data_t.LAYOUT,
        lore_repository_verify_fragment_remote_event_data_t.LAYOUT,
        lore_repository_verify_state_args_t.LAYOUT,
        lore_repository_verify_state_begin_event_data_t.LAYOUT,
        lore_repository_verify_state_end_event_data_t.LAYOUT,
        lore_revert_abort_begin_event_data_t.LAYOUT,
        lore_revert_abort_end_event_data_t.LAYOUT,
        lore_revert_conflict_file_event_data_t.LAYOUT,
        lore_revert_resolve_file_event_data_t.LAYOUT,
        lore_revert_resolve_revision_event_data_t.LAYOUT,
        lore_revert_start_begin_event_data_t.LAYOUT,
        lore_revert_start_end_event_data_t.LAYOUT,
        lore_revert_unresolve_file_event_data_t.LAYOUT,
        lore_revert_unresolve_revision_event_data_t.LAYOUT,
        lore_revision_amend_args_t.LAYOUT,
        lore_revision_bisect_event_data_t.LAYOUT,
        lore_revision_commit_args_t.LAYOUT,
        lore_revision_commit_begin_event_data_t.LAYOUT,
        lore_revision_commit_count_data_t.LAYOUT,
        lore_revision_commit_end_event_data_t.LAYOUT,
        lore_revision_commit_progress_event_data_t.LAYOUT,
        lore_revision_commit_revision_event_data_t.LAYOUT,
        lore_revision_diff_args_t.LAYOUT,
        lore_revision_diff_file_event_data_t.LAYOUT,
        lore_revision_find_args_t.LAYOUT,
        lore_revision_find_event_data_t.LAYOUT,
        lore_revision_history_args_t.LAYOUT,
        lore_revision_history_entry_event_data_t.LAYOUT,
        lore_revision_history_event_data_t.LAYOUT,
        lore_revision_info_args_t.LAYOUT,
        lore_revision_info_delta_event_data_t.LAYOUT,
        lore_revision_info_event_data_t.LAYOUT,
        lore_revision_metadata_clear_args_t.LAYOUT,
        lore_revision_metadata_get_args_t.LAYOUT,
        lore_revision_metadata_list_args_t.LAYOUT,
        lore_revision_metadata_set_args_t.LAYOUT,
        lore_revision_resolve_event_data_t.LAYOUT,
        lore_revision_restore_args_t.LAYOUT,
        lore_revision_restore_file_begin_event_data_t.LAYOUT,
        lore_revision_restore_file_end_event_data_t.LAYOUT,
        lore_revision_restore_file_event_data_t.LAYOUT,
        lore_revision_restore_fragment_begin_event_data_t.LAYOUT,
        lore_revision_restore_fragment_end_event_data_t.LAYOUT,
        lore_revision_restore_fragment_progress_event_data_t.LAYOUT,
        lore_revision_restore_revision_event_data_t.LAYOUT,
        lore_revision_restore_sync_begin_event_data_t.LAYOUT,
        lore_revision_restore_sync_end_event_data_t.LAYOUT,
        lore_revision_revert_abort_args_t.LAYOUT,
        lore_revision_revert_args_t.LAYOUT,
        lore_revision_revert_resolve_args_t.LAYOUT,
        lore_revision_revert_resolve_mine_args_t.LAYOUT,
        lore_revision_revert_resolve_theirs_args_t.LAYOUT,
        lore_revision_revert_restart_args_t.LAYOUT,
        lore_revision_revert_unresolve_args_t.LAYOUT,
        lore_revision_sync_args_t.LAYOUT,
        lore_revision_sync_file_event_data_t.LAYOUT,
        lore_revision_sync_progress_event_data_t.LAYOUT,
        lore_revision_sync_revision_event_data_t.LAYOUT,
        lore_revision_sync_target_event_data_t.LAYOUT,
        lore_revision_tree_add_args_t.LAYOUT,
        lore_revision_tree_add_complete_event_data_t.LAYOUT,
        lore_revision_tree_add_entry_array_t.LAYOUT,
        lore_revision_tree_add_entry_t.LAYOUT,
        lore_revision_tree_batch_complete_event_data_t.LAYOUT,
        lore_revision_tree_child_event_data_t.LAYOUT,
        lore_revision_tree_close_args_t.LAYOUT,
        lore_revision_tree_close_complete_event_data_t.LAYOUT,
        lore_revision_tree_commit_args_t.LAYOUT,
        lore_revision_tree_commit_complete_event_data_t.LAYOUT,
        lore_revision_tree_commit_options_t.LAYOUT,
        lore_revision_tree_delete_args_t.LAYOUT,
        lore_revision_tree_delete_complete_event_data_t.LAYOUT,
        lore_revision_tree_info_args_t.LAYOUT,
        lore_revision_tree_info_event_data_t.LAYOUT,
        lore_revision_tree_list_children_args_t.LAYOUT,
        lore_revision_tree_list_children_begin_event_data_t.LAYOUT,
        lore_revision_tree_load_args_t.LAYOUT,
        lore_revision_tree_loaded_event_data_t.LAYOUT,
        lore_revision_tree_metadata_get_args_t.LAYOUT,
        lore_revision_tree_metadata_get_complete_event_data_t.LAYOUT,
        lore_revision_tree_metadata_set_args_t.LAYOUT,
        lore_revision_tree_metadata_set_complete_event_data_t.LAYOUT,
        lore_revision_tree_modify_args_t.LAYOUT,
        lore_revision_tree_modify_complete_event_data_t.LAYOUT,
        lore_revision_tree_move_args_t.LAYOUT,
        lore_revision_tree_move_complete_event_data_t.LAYOUT,
        lore_revision_tree_node_info_args_t.LAYOUT,
        lore_revision_tree_node_info_event_data_t.LAYOUT,
        lore_revision_tree_node_path_args_t.LAYOUT,
        lore_revision_tree_node_path_event_data_t.LAYOUT,
        lore_revision_tree_resolve_path_args_t.LAYOUT,
        lore_revision_tree_resolve_path_complete_event_data_t.LAYOUT,
        lore_revision_tree_t.LAYOUT,
        lore_service_start_args_t.LAYOUT,
        lore_service_stop_args_t.LAYOUT,
        lore_shared_store_create_args_t.LAYOUT,
        lore_shared_store_create_event_data_t.LAYOUT,
        lore_shared_store_info_args_t.LAYOUT,
        lore_shared_store_info_event_data_t.LAYOUT,
        lore_shared_store_set_use_automatically_args_t.LAYOUT,
        lore_storage_close_args_t.LAYOUT,
        lore_storage_copy_args_t.LAYOUT,
        lore_storage_copy_item_array_t.LAYOUT,
        lore_storage_copy_item_complete_event_data_t.LAYOUT,
        lore_storage_copy_item_t.LAYOUT,
        lore_storage_flush_args_t.LAYOUT,
        lore_storage_get_args_t.LAYOUT,
        lore_storage_get_data_event_data_t.LAYOUT,
        lore_storage_get_file_args_t.LAYOUT,
        lore_storage_get_file_item_array_t.LAYOUT,
        lore_storage_get_file_item_t.LAYOUT,
        lore_storage_get_header_event_data_t.LAYOUT,
        lore_storage_get_item_array_t.LAYOUT,
        lore_storage_get_item_complete_event_data_t.LAYOUT,
        lore_storage_get_item_t.LAYOUT,
        lore_storage_get_metadata_args_t.LAYOUT,
        lore_storage_get_metadata_item_array_t.LAYOUT,
        lore_storage_get_metadata_item_complete_event_data_t.LAYOUT,
        lore_storage_get_metadata_item_t.LAYOUT,
        lore_storage_mutable_compare_and_swap_args_t.LAYOUT,
        lore_storage_mutable_compare_and_swap_item_array_t.LAYOUT,
        lore_storage_mutable_compare_and_swap_item_complete_event_data_t.LAYOUT,
        lore_storage_mutable_compare_and_swap_item_t.LAYOUT,
        lore_storage_mutable_list_args_t.LAYOUT,
        lore_storage_mutable_list_entry_event_data_t.LAYOUT,
        lore_storage_mutable_list_item_array_t.LAYOUT,
        lore_storage_mutable_list_item_complete_event_data_t.LAYOUT,
        lore_storage_mutable_list_item_t.LAYOUT,
        lore_storage_mutable_load_args_t.LAYOUT,
        lore_storage_mutable_load_item_array_t.LAYOUT,
        lore_storage_mutable_load_item_complete_event_data_t.LAYOUT,
        lore_storage_mutable_load_item_t.LAYOUT,
        lore_storage_mutable_store_args_t.LAYOUT,
        lore_storage_mutable_store_item_array_t.LAYOUT,
        lore_storage_mutable_store_item_complete_event_data_t.LAYOUT,
        lore_storage_mutable_store_item_t.LAYOUT,
        lore_storage_obliterate_args_t.LAYOUT,
        lore_storage_obliterate_item_array_t.LAYOUT,
        lore_storage_obliterate_item_complete_event_data_t.LAYOUT,
        lore_storage_obliterate_item_t.LAYOUT,
        lore_storage_open_args_t.LAYOUT,
        lore_storage_opened_event_data_t.LAYOUT,
        lore_storage_put_args_t.LAYOUT,
        lore_storage_put_file_args_t.LAYOUT,
        lore_storage_put_file_item_array_t.LAYOUT,
        lore_storage_put_file_item_t.LAYOUT,
        lore_storage_put_item_array_t.LAYOUT,
        lore_storage_put_item_complete_event_data_t.LAYOUT,
        lore_storage_put_item_t.LAYOUT,
        lore_storage_remote_config_t.LAYOUT,
        lore_storage_upload_args_t.LAYOUT,
        lore_storage_upload_item_array_t.LAYOUT,
        lore_storage_upload_item_complete_event_data_t.LAYOUT,
        lore_storage_upload_item_t.LAYOUT,
        lore_store_t.LAYOUT,
        lore_string_array_t.LAYOUT,
        lore_string_t.LAYOUT,
        lore_trace_location_array_t.LAYOUT,
        lore_trace_location_t.LAYOUT,
        lore_uint32_array_t.LAYOUT,
        lore_uint8_array_t.LAYOUT,
    )
}
