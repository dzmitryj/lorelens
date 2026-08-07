// Generated from lore.h 0.8.6 by :codegen. Do not edit.
package com.dzmitryj.lorevcs.ffi.generated

import com.dzmitryj.lorevcs.ffi.LoreLinker
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemoryLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

/**
 * Handles bind lazily so a symbol missing from the loaded library fails at the
 * call site that needs it rather than at load time.
 */
object LoreFunctions {

    /**
     * Clear all stored authentication data.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_auth_clear: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_clear",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_clear`.
     */
    val lore_auth_clear_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_clear_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all stored authentication identities.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_IDENTITY` | `lore_auth_identity_event_data_t` | Emitted once per stored identity |
     */
    val lore_auth_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_list`.
     */
    val lore_auth_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve user identities to display names from locally stored JWT tokens.
     * 
     * Does not contact the auth service. Decodes cached JWT tokens to extract
     * display names. For user IDs without a local token, returns the raw user
     * ID. For remote resolution with proper authorization, use
     * `lore_auth_user_info` which queries the remote authentication service.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_USER_INFO` | `lore_auth_user_info_event_data_t` | Emitted with the resolved user id and display name |
     */
    val lore_auth_local_user_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_local_user_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_local_user_info`.
     */
    val lore_auth_local_user_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_local_user_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Authenticate interactively via a browser-based login flow.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_URL` | `lore_auth_url_event_data_t` | Emitted with the login URL when no_browser mode is requested |
     * | `LORE_EVENT_AUTH_USER_INFO` | `lore_auth_user_info_event_data_t` | Emitted with user id and display name after successful interactive authentication |
     */
    val lore_auth_login_interactive: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_login_interactive",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_login_interactive`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_URL` | `lore_auth_url_event_data_t` | Emitted with the login URL when no_browser mode is requested |
     * | `LORE_EVENT_AUTH_USER_INFO` | `lore_auth_user_info_event_data_t` | Emitted with user id and display name after successful interactive authentication |
     */
    val lore_auth_login_interactive_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_login_interactive_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Authenticate using an existing bearer token.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_USER_INFO` | `lore_auth_user_info_event_data_t` | Emitted with user id and display name after successful token authentication |
     */
    val lore_auth_login_with_token: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_login_with_token",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_login_with_token`.
     */
    val lore_auth_login_with_token_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_login_with_token_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove stored authentication and authorization tokens.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_auth_logout: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_logout",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_logout`.
     */
    val lore_auth_logout_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_logout_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve user IDs to display names using the remote authentication service.
     * Requires an authenticated connection.
     * 
     * When no user IDs are provided, returns the current user's identity using
     * locally cached tokens (equivalent to `lore_auth_local_user_info`).
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Auth Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_AUTH_USER_INFO` | `lore_auth_user_info_event_data_t` | Emitted with user id and display name for each resolved user |
     */
    val lore_auth_user_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_user_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_auth_user_info`.
     */
    val lore_auth_user_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_auth_user_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Archive a branch in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_ARCHIVE` | `lore_branch_archive_event_data_t` | Emitted when the branch has been successfully archived |
     */
    val lore_branch_archive: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_archive",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_archive`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_ARCHIVE` | `lore_branch_archive_event_data_t` | Emitted when the branch has been successfully archived |
     */
    val lore_branch_archive_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_archive_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Create a new branch in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_CREATE` | `lore_branch_create_event_data_t` | Emitted when the branch has been successfully created, includes branch name and id |
     */
    val lore_branch_create: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_create",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_create`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_CREATE` | `lore_branch_create_event_data_t` | Emitted when the branch has been successfully created, includes branch name and id |
     */
    val lore_branch_create_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_create_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Show the changes and conflicts between two branches.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_DIFF_BEGIN` | `lore_branch_diff_begin_event_data_t` | Emitted before diff results begin streaming |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE_BEGIN` | `lore_branch_diff_change_begin_event_data_t` | Emitted before the list of changed files begins |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE` | `lore_branch_diff_change_event_data_t` | Emitted for each changed file between the two branches |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE_END` | `lore_branch_diff_change_end_event_data_t` | Emitted after all changed files have been reported |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT_BEGIN` | `lore_branch_diff_conflict_begin_event_data_t` | Emitted before the list of conflicting files begins |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT` | `lore_branch_diff_conflict_event_data_t` | Emitted for each file that has a conflict between the two branches |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT_END` | `lore_branch_diff_conflict_end_event_data_t` | Emitted after all conflict files have been reported |
     * | `LORE_EVENT_BRANCH_DIFF_END` | `lore_branch_diff_end_event_data_t` | Emitted after all diff results have been streamed |
     */
    val lore_branch_diff: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_diff",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_diff`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_DIFF_BEGIN` | `lore_branch_diff_begin_event_data_t` | Emitted before diff results begin streaming |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE_BEGIN` | `lore_branch_diff_change_begin_event_data_t` | Emitted before the list of changed files begins |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE` | `lore_branch_diff_change_event_data_t` | Emitted for each changed file between the two branches |
     * | `LORE_EVENT_BRANCH_DIFF_CHANGE_END` | `lore_branch_diff_change_end_event_data_t` | Emitted after all changed files have been reported |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT_BEGIN` | `lore_branch_diff_conflict_begin_event_data_t` | Emitted before the list of conflicting files begins |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT` | `lore_branch_diff_conflict_event_data_t` | Emitted for each file that has a conflict between the two branches |
     * | `LORE_EVENT_BRANCH_DIFF_CONFLICT_END` | `lore_branch_diff_conflict_end_event_data_t` | Emitted after all conflict files have been reported |
     * | `LORE_EVENT_BRANCH_DIFF_END` | `lore_branch_diff_end_event_data_t` | Emitted after all diff results have been streamed |
     */
    val lore_branch_diff_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_diff_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve metadata about a specific branch.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_INFO` | `lore_branch_info_event_data_t` | Emitted with branch metadata (name, id, category, protection status, etc.) |
     */
    val lore_branch_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_info`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_INFO` | `lore_branch_info_event_data_t` | Emitted with branch metadata (name, id, category, protection status, etc.) |
     */
    val lore_branch_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all branches in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_LIST_BEGIN` | `lore_branch_list_begin_event_data_t` | Emitted before branch list entries begin streaming |
     * | `LORE_EVENT_BRANCH_LIST_ENTRY` | `lore_branch_list_entry_event_data_t` | Emitted for each branch in the repository |
     * | `LORE_EVENT_BRANCH_LIST_END` | `lore_branch_list_end_event_data_t` | Emitted after all branch entries have been streamed |
     */
    val lore_branch_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_LIST_BEGIN` | `lore_branch_list_begin_event_data_t` | Emitted before branch list entries begin streaming |
     * | `LORE_EVENT_BRANCH_LIST_ENTRY` | `lore_branch_list_entry_event_data_t` | Emitted for each branch in the repository |
     * | `LORE_EVENT_BRANCH_LIST_END` | `lore_branch_list_end_event_data_t` | Emitted after all branch entries have been streamed |
     */
    val lore_branch_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Abort an in-progress branch merge and restore the pre-merge state.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_ABORT_BEGIN` | `lore_branch_merge_abort_begin_event_data_t` | Emitted when aborting a branch merge, includes staged and current revision hashes |
     * | `LORE_EVENT_BRANCH_MERGE_ABORT_END` | `lore_branch_merge_abort_end_event_data_t` | Emitted after the merge abort has been completed |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization while reverting merge changes |
     */
    val lore_branch_merge_abort: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_abort",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_abort`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_ABORT_BEGIN` | `lore_branch_merge_abort_begin_event_data_t` | Emitted when aborting a branch merge, includes staged and current revision hashes |
     * | `LORE_EVENT_BRANCH_MERGE_ABORT_END` | `lore_branch_merge_abort_end_event_data_t` | Emitted after the merge abort has been completed |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization while reverting merge changes |
     */
    val lore_branch_merge_abort_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_abort_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Merge the current branch into a target branch.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE_BEGIN` | `lore_branch_merge_into_file_begin_event_data_t` | Emitted when starting to merge files into the target branch |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE` | `lore_branch_merge_into_file_event_data_t` | Emitted for each file being merged into the target branch |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE_END` | `lore_branch_merge_into_file_end_event_data_t` | Emitted after all files have been merged |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_BEGIN` | `lore_branch_merge_into_fragment_begin_event_data_t` | Emitted when starting fragment transfer for a file |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_PROGRESS` | `lore_branch_merge_into_fragment_progress_event_data_t` | Emitted periodically during fragment transfer |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_END` | `lore_branch_merge_into_fragment_end_event_data_t` | Emitted when fragment transfer for a file completes |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_REVISION` | `lore_branch_merge_into_revision_event_data_t` | Emitted with the resulting revision after the merge into is complete |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_SYNC_BEGIN` | `lore_branch_merge_into_sync_begin_event_data_t` | Emitted when starting to apply the changes on the target state |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_SYNC_END` | `lore_branch_merge_into_sync_end_event_data_t` | Emitted after applying the changes on the target state is complete |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (if no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during auto-commit file processing |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during changes realization |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each file fragment written or deduplicated during commit |
     */
    val lore_branch_merge_into: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_into",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_into`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE_BEGIN` | `lore_branch_merge_into_file_begin_event_data_t` | Emitted when starting to merge files into the target branch |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE` | `lore_branch_merge_into_file_event_data_t` | Emitted for each file being merged into the target branch |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FILE_END` | `lore_branch_merge_into_file_end_event_data_t` | Emitted after all files have been merged |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_BEGIN` | `lore_branch_merge_into_fragment_begin_event_data_t` | Emitted when starting fragment transfer for a file |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_PROGRESS` | `lore_branch_merge_into_fragment_progress_event_data_t` | Emitted periodically during fragment transfer |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_END` | `lore_branch_merge_into_fragment_end_event_data_t` | Emitted when fragment transfer for a file completes |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_REVISION` | `lore_branch_merge_into_revision_event_data_t` | Emitted with the resulting revision after the merge into is complete |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_SYNC_BEGIN` | `lore_branch_merge_into_sync_begin_event_data_t` | Emitted when starting to apply the changes on the target state |
     * | `LORE_EVENT_BRANCH_MERGE_INTO_SYNC_END` | `lore_branch_merge_into_sync_end_event_data_t` | Emitted after applying the changes on the target state is complete |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (if no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during auto-commit file processing |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during changes realization |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each file fragment written or deduplicated during commit |
     */
    val lore_branch_merge_into_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_into_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark conflicting files in a merge as resolved.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file that was marked as resolved |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision after resolve completes |
     */
    val lore_branch_merge_resolve: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_resolve`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file that was marked as resolved |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision after resolve completes |
     */
    val lore_branch_merge_resolve_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a merge conflict by accepting the "mine" version of each conflicting file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file resolved by keeping "mine" |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_branch_merge_resolve_mine: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve_mine",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_resolve_mine`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file resolved by keeping "mine" |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_branch_merge_resolve_mine_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve_mine_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a merge conflict by accepting the "theirs" version of each conflicting file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file resolved by keeping "theirs" |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_branch_merge_resolve_theirs: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve_theirs",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_resolve_theirs`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE` | `lore_branch_merge_resolve_file_event_data_t` | Emitted for each file resolved by keeping "theirs" |
     * | `LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION` | `lore_branch_merge_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_branch_merge_resolve_theirs_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_resolve_theirs_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Restart an in-progress merge, re-materializing conflicted files.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with a remaining merge conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization during restart |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file re-materialized during restart |
     */
    val lore_branch_merge_restart: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_restart",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_restart`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with a remaining merge conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization during restart |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file re-materialized during restart |
     */
    val lore_branch_merge_restart_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_restart_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Start a merge from another branch into the current branch.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_START_BEGIN` | `lore_branch_merge_start_begin_event_data_t` | Emitted when merge begins, includes source branch and revision info |
     * | `LORE_EVENT_BRANCH_MERGE_START_END` | `lore_branch_merge_start_end_event_data_t` | Emitted when merge operation completes, includes sync stats and conflict flag |
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with an unresolved merge conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during the apply_diff phase of the merge |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified during merge realization |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during merge realization |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (no conflicts, no_commit=false) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written during auto-commit |
     */
    val lore_branch_merge_start: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_start",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_start`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_START_BEGIN` | `lore_branch_merge_start_begin_event_data_t` | Emitted when merge begins, includes source branch and revision info |
     * | `LORE_EVENT_BRANCH_MERGE_START_END` | `lore_branch_merge_start_end_event_data_t` | Emitted when merge operation completes, includes sync stats and conflict flag |
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with an unresolved merge conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during the apply_diff phase of the merge |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified during merge realization |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during merge realization |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (no conflicts, no_commit=false) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written during auto-commit |
     */
    val lore_branch_merge_start_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_start_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark conflicting files in a merge as unresolved.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_UNRESOLVE_FILE` | `lore_branch_merge_unresolve_file_event_data_t` | Emitted for each file that was marked as unresolved |
     * | `LORE_EVENT_BRANCH_MERGE_UNRESOLVE_REVISION` | `lore_branch_merge_unresolve_revision_event_data_t` | Emitted with the updated staged revision after unresolve completes |
     */
    val lore_branch_merge_unresolve: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_unresolve",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_merge_unresolve`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_MERGE_UNRESOLVE_FILE` | `lore_branch_merge_unresolve_file_event_data_t` | Emitted for each file that was marked as unresolved |
     * | `LORE_EVENT_BRANCH_MERGE_UNRESOLVE_REVISION` | `lore_branch_merge_unresolve_revision_event_data_t` | Emitted with the updated staged revision after unresolve completes |
     */
    val lore_branch_merge_unresolve_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_merge_unresolve_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Clear branch metadata keys.
     */
    val lore_branch_metadata_clear: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_clear",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_metadata_clear`.
     */
    val lore_branch_metadata_clear_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_clear_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve branch metadata.
     */
    val lore_branch_metadata_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_metadata_get`.
     */
    val lore_branch_metadata_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set branch metadata key-value pairs.
     */
    val lore_branch_metadata_set: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_set",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_metadata_set`.
     */
    val lore_branch_metadata_set_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_metadata_set_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Enable write protection on a branch to prevent direct commits.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_PROTECT` | `lore_branch_protect_event_data_t` | Emitted when the branch has been successfully protected |
     */
    val lore_branch_protect: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_protect",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_protect`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_PROTECT` | `lore_branch_protect_event_data_t` | Emitted when the branch has been successfully protected |
     */
    val lore_branch_protect_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_protect_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Push local branch commits to the remote repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_PUSH` | `lore_branch_push_event_data_t` | Emitted when push begins, includes branch name and revision info |
     * | `LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_BEGIN` | `lore_branch_push_branch_create_begin_event_data_t` | Emitted when creating the remote branch (first push) |
     * | `LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_END` | `lore_branch_push_branch_create_end_event_data_t` | Emitted when remote branch creation completes |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_BEGIN` | `lore_branch_push_revision_update_begin_event_data_t` | Emitted when updating a revision on the remote |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_END` | `lore_branch_push_revision_update_end_event_data_t` | Emitted when a revision update completes |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_BEGIN` | `lore_branch_push_fragment_begin_event_data_t` | Emitted when uploading fragment data begins |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_PROGRESS` | `lore_branch_push_fragment_progress_event_data_t` | Emitted periodically during fragment upload |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_END` | `lore_branch_push_fragment_end_event_data_t` | Emitted when fragment upload completes |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_BEGIN` | `lore_branch_push_revision_push_begin_event_data_t` | Emitted when pushing a revision to the remote begins |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_UPDATE` | `lore_branch_push_revision_push_update_event_data_t` | Emitted with progress updates during revision push |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_END` | `lore_branch_push_revision_push_end_event_data_t` | Emitted when revision push completes |
     */
    val lore_branch_push: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_push",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_push`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_PUSH` | `lore_branch_push_event_data_t` | Emitted when push begins, includes branch name and revision info |
     * | `LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_BEGIN` | `lore_branch_push_branch_create_begin_event_data_t` | Emitted when creating the remote branch (first push) |
     * | `LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_END` | `lore_branch_push_branch_create_end_event_data_t` | Emitted when remote branch creation completes |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_BEGIN` | `lore_branch_push_revision_update_begin_event_data_t` | Emitted when updating a revision on the remote |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_END` | `lore_branch_push_revision_update_end_event_data_t` | Emitted when a revision update completes |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_BEGIN` | `lore_branch_push_fragment_begin_event_data_t` | Emitted when uploading fragment data begins |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_PROGRESS` | `lore_branch_push_fragment_progress_event_data_t` | Emitted periodically during fragment upload |
     * | `LORE_EVENT_BRANCH_PUSH_FRAGMENT_END` | `lore_branch_push_fragment_end_event_data_t` | Emitted when fragment upload completes |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_BEGIN` | `lore_branch_push_revision_push_begin_event_data_t` | Emitted when pushing a revision to the remote begins |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_UPDATE` | `lore_branch_push_revision_push_update_event_data_t` | Emitted with progress updates during revision push |
     * | `LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_END` | `lore_branch_push_revision_push_end_event_data_t` | Emitted when revision push completes |
     */
    val lore_branch_push_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_push_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Reset the current branch to a specific revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_RESET` | `lore_branch_reset_event_data_t` | Emitted when the branch has been reset to the target revision |
     */
    val lore_branch_reset: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_reset",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_reset`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_RESET` | `lore_branch_reset_event_data_t` | Emitted when the branch has been reset to the target revision |
     */
    val lore_branch_reset_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_reset_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Switch to a different branch and update the working directory.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_SWITCH_BEGIN` | `lore_branch_switch_begin_event_data_t` | Emitted when branch switch starts |
     * | `LORE_EVENT_BRANCH_SWITCH_END` | `lore_branch_switch_end_event_data_t` | Emitted when branch switch completes successfully |
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted with target revision info after resolving the switch target |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified/added/deleted during switch |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during file realization |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted with the resulting revision after switch |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial revision reference |
     */
    val lore_branch_switch: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_switch",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_switch`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_SWITCH_BEGIN` | `lore_branch_switch_begin_event_data_t` | Emitted when branch switch starts |
     * | `LORE_EVENT_BRANCH_SWITCH_END` | `lore_branch_switch_end_event_data_t` | Emitted when branch switch completes successfully |
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted with target revision info after resolving the switch target |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified/added/deleted during switch |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during file realization |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted with the resulting revision after switch |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial revision reference |
     */
    val lore_branch_switch_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_switch_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove write protection from a branch.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_UNPROTECT` | `lore_branch_unprotect_event_data_t` | Emitted when the branch has been successfully unprotected |
     */
    val lore_branch_unprotect: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_unprotect",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_branch_unprotect`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Branch Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_BRANCH_UNPROTECT` | `lore_branch_unprotect_event_data_t` | Emitted when the branch has been successfully unprotected |
     */
    val lore_branch_unprotect_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_branch_unprotect_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Return the tag identifying the type of an event.
     */
    val lore_event_type: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_event_type",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS),
        )
    }

    /**
     * Adds dependency relationships between files.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_BEGIN` | `lore_file_dependency_add_begin_event_data_t` | Start of operation |
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_ENTRY` | `lore_file_dependency_add_entry_event_data_t` | Each dependency added |
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_END` | `lore_file_dependency_add_end_event_data_t` | Operation complete |
     */
    val lore_file_dependency_add: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_add",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dependency_add`.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_BEGIN` | `lore_file_dependency_add_begin_event_data_t` | Start of operation |
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_ENTRY` | `lore_file_dependency_add_entry_event_data_t` | Each dependency added |
     * | `LORE_EVENT_FILE_DEPENDENCY_ADD_END` | `lore_file_dependency_add_end_event_data_t` | Operation complete |
     */
    val lore_file_dependency_add_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_add_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Queries dependency information for files.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_BEGIN` | `lore_file_dependency_list_begin_event_data_t` | Start of listing |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_FILE` | `lore_file_dependency_list_file_event_data_t` | Start of entries for one file |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_ENTRY` | `lore_file_dependency_list_entry_event_data_t` | One dependency entry |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_FILE_END` | `lore_file_dependency_list_file_end_event_data_t` | End of entries for one file |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_END` | `lore_file_dependency_list_end_event_data_t` | End of listing |
     */
    val lore_file_dependency_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dependency_list`.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_BEGIN` | `lore_file_dependency_list_begin_event_data_t` | Start of listing |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_FILE` | `lore_file_dependency_list_file_event_data_t` | Start of entries for one file |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_ENTRY` | `lore_file_dependency_list_entry_event_data_t` | One dependency entry |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_FILE_END` | `lore_file_dependency_list_file_end_event_data_t` | End of entries for one file |
     * | `LORE_EVENT_FILE_DEPENDENCY_LIST_END` | `lore_file_dependency_list_end_event_data_t` | End of listing |
     */
    val lore_file_dependency_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Removes dependency relationships between files.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_BEGIN` | `lore_file_dependency_remove_begin_event_data_t` | Start of operation |
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_ENTRY` | `lore_file_dependency_remove_entry_event_data_t` | Each dependency removed |
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_END` | `lore_file_dependency_remove_end_event_data_t` | Operation complete |
     */
    val lore_file_dependency_remove: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_remove",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dependency_remove`.
     * 
     * # Events
     * 
     * ## Standard Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Dependency Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_BEGIN` | `lore_file_dependency_remove_begin_event_data_t` | Start of operation |
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_ENTRY` | `lore_file_dependency_remove_entry_event_data_t` | Each dependency removed |
     * | `LORE_EVENT_FILE_DEPENDENCY_REMOVE_END` | `lore_file_dependency_remove_end_event_data_t` | Operation complete |
     */
    val lore_file_dependency_remove_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dependency_remove_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Show which files differ between two revisions.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DIFF` | `lore_file_diff_event_data_t` | Emitted for each file that differs between the two revisions |
     */
    val lore_file_diff: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_diff",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_diff`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DIFF` | `lore_file_diff_event_data_t` | Emitted for each file that differs between the two revisions |
     */
    val lore_file_diff_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_diff_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark files as dirty in the staged state without staging their content.
     * 
     * Action is determined by checking filesystem existence and current revision state
     * (modify, add, delete, or revert-add). Respects ignore and view filters.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_PATH_IGNORE` | `lore_path_ignore_event_data_t` | Emitted for each input path that could not be resolved to a repository-relative path |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     */
    val lore_file_dirty: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dirty`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_PATH_IGNORE` | `lore_path_ignore_event_data_t` | Emitted for each input path that could not be resolved to a repository-relative path |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     */
    val lore_file_dirty_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark a file as dirty-copied from one path to another in the staged state.
     * 
     * Creates a new destination node flagged `DirtyCopy`; the source node is unchanged.
     * No filesystem access is performed.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_dirty_copy: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty_copy",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dirty_copy`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_dirty_copy_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty_copy_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark a file as dirty-moved from one path to another in the staged state.
     * 
     * Updates the source node's parent/name and flags it with `DirtyMove`, propagating
     * `Dirty` to both the old and new parent directories. For directories, the move
     * is propagated recursively to children. No filesystem access is performed.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_dirty_move: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty_move",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dirty_move`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_dirty_move_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dirty_move_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve the binary content of a file at a specific revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DUMP` | `lore_file_dump_event_data_t` | Emitted with binary content of the requested file |
     */
    val lore_file_dump: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dump",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_dump`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_DUMP` | `lore_file_dump_event_data_t` | Emitted with binary content of the requested file |
     */
    val lore_file_dump_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_dump_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Compute the hash of a local file for comparison with repository content.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_HASH` | `lore_file_hash_event_data_t` | Emitted with the computed hash and size of the specified file |
     */
    val lore_file_hash: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_hash",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_hash`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_HASH` | `lore_file_hash_event_data_t` | Emitted with the computed hash and size of the specified file |
     */
    val lore_file_hash_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_hash_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve the revision history for a specific file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_HISTORY` | `lore_file_history_event_data_t` | Emitted for each revision in which the file was modified |
     */
    val lore_file_history: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_history",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_history`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_HISTORY` | `lore_file_history_event_data_t` | Emitted for each revision in which the file was modified |
     */
    val lore_file_history_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_history_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve metadata for one or more files in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_INFO` | `lore_file_info_event_data_t` | Emitted for each file with its metadata (size, hash, staged status, etc.) |
     */
    val lore_file_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_info`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_INFO` | `lore_file_info_event_data_t` | Emitted for each file with its metadata (size, hash, staged status, etc.) |
     */
    val lore_file_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Clear all metadata from a file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA_CLEAR_FILE` | `lore_metadata_clear_file_event_data_t` | Emitted when metadata has been cleared for the file |
     */
    val lore_file_metadata_clear: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_clear",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_metadata_clear`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA_CLEAR_FILE` | `lore_metadata_clear_file_event_data_t` | Emitted when metadata has been cleared for the file |
     */
    val lore_file_metadata_clear_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_clear_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Get a specific metadata key/value pair from a file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for the requested metadata key/value pair |
     */
    val lore_file_metadata_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_metadata_get`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for the requested metadata key/value pair |
     */
    val lore_file_metadata_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all metadata key/value pairs associated with a file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value pair associated with the file |
     */
    val lore_file_metadata_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_metadata_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value pair associated with the file |
     */
    val lore_file_metadata_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set a metadata key/value pair on a file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_metadata_set: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_set",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_metadata_set`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_file_metadata_set_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_metadata_set_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Permanently remove a file and all its history from the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_OBLITERATE` | `lore_file_obliterate_event_data_t` | Emitted for each file permanently removed from repository history |
     */
    val lore_file_obliterate: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_obliterate",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_obliterate`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_OBLITERATE` | `lore_file_obliterate_event_data_t` | Emitted for each file permanently removed from repository history |
     */
    val lore_file_obliterate_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_obliterate_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Reset files to the state recorded in the current or target revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_RESET_BEGIN` | `lore_file_reset_begin_event_data_t` | Emitted when reset starts, includes path count |
     * | `LORE_EVENT_FILE_RESET_PROGRESS` | `lore_file_reset_progress_event_data_t` | Emitted periodically during file reset with progress counts |
     * | `LORE_EVENT_FILE_RESET_END` | `lore_file_reset_end_event_data_t` | Emitted when reset completes |
     * | `LORE_EVENT_FILE_RESET_FILE` | `lore_file_reset_file_event_data_t` | Emitted for each file that was reset |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file materialized |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by filters |
     */
    val lore_file_reset: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_reset",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_reset`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_RESET_BEGIN` | `lore_file_reset_begin_event_data_t` | Emitted when reset starts, includes path count |
     * | `LORE_EVENT_FILE_RESET_PROGRESS` | `lore_file_reset_progress_event_data_t` | Emitted periodically during file reset with progress counts |
     * | `LORE_EVENT_FILE_RESET_END` | `lore_file_reset_end_event_data_t` | Emitted when reset completes |
     * | `LORE_EVENT_FILE_RESET_FILE` | `lore_file_reset_file_event_data_t` | Emitted for each file that was reset |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file materialized |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by filters |
     */
    val lore_file_reset_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_reset_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Reset files to their state at the last merged revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_RESET_BEGIN` | `lore_file_reset_begin_event_data_t` | Emitted when reset starts |
     * | `LORE_EVENT_FILE_RESET_PROGRESS` | `lore_file_reset_progress_event_data_t` | Emitted periodically during file reset |
     * | `LORE_EVENT_FILE_RESET_END` | `lore_file_reset_end_event_data_t` | Emitted when reset completes |
     * | `LORE_EVENT_FILE_RESET_FILE` | `lore_file_reset_file_event_data_t` | Emitted for each file that was reset |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file materialized |
     */
    val lore_file_reset_to_last_merged: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_reset_to_last_merged",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_reset_to_last_merged`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_RESET_BEGIN` | `lore_file_reset_begin_event_data_t` | Emitted when reset starts |
     * | `LORE_EVENT_FILE_RESET_PROGRESS` | `lore_file_reset_progress_event_data_t` | Emitted periodically during file reset |
     * | `LORE_EVENT_FILE_RESET_END` | `lore_file_reset_end_event_data_t` | Emitted when reset completes |
     * | `LORE_EVENT_FILE_RESET_FILE` | `lore_file_reset_file_event_data_t` | Emitted for each file that was reset |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file materialized |
     */
    val lore_file_reset_to_last_merged_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_reset_to_last_merged_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stage files for the next commit.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when staging begins, includes path count |
     * | `LORE_EVENT_FILE_STAGE_PROGRESS` | `lore_file_stage_progress_event_data_t` | Emitted periodically during staging with file counts |
     * | `LORE_EVENT_FILE_STAGE_END` | `lore_file_stage_end_event_data_t` | Emitted when staging completes |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged or staged for deletion |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by filters |
     */
    val lore_file_stage: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_stage`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when staging begins, includes path count |
     * | `LORE_EVENT_FILE_STAGE_PROGRESS` | `lore_file_stage_progress_event_data_t` | Emitted periodically during staging with file counts |
     * | `LORE_EVENT_FILE_STAGE_END` | `lore_file_stage_end_event_data_t` | Emitted when staging completes |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged or staged for deletion |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by filters |
     */
    val lore_file_stage_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stage files for a merge commit, recording resolved merge content.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when merge-staging begins |
     * | `LORE_EVENT_FILE_STAGE_PROGRESS` | `lore_file_stage_progress_event_data_t` | Emitted periodically during merge-staging |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged |
     */
    val lore_file_stage_merge: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage_merge",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_stage_merge`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when merge-staging begins |
     * | `LORE_EVENT_FILE_STAGE_PROGRESS` | `lore_file_stage_progress_event_data_t` | Emitted periodically during merge-staging |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged |
     */
    val lore_file_stage_merge_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage_merge_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stage a file move (rename) operation for commit.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when move staging begins |
     * | `LORE_EVENT_FILE_STAGE_END` | `lore_file_stage_end_event_data_t` | Emitted when move staging completes |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged (deletion of original and new path) |
     */
    val lore_file_stage_move: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage_move",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_stage_move`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_STAGE_BEGIN` | `lore_file_stage_begin_event_data_t` | Emitted when move staging begins |
     * | `LORE_EVENT_FILE_STAGE_END` | `lore_file_stage_end_event_data_t` | Emitted when move staging completes |
     * | `LORE_EVENT_FILE_STAGE_REVISION` | `lore_file_stage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged (deletion of original and new path) |
     */
    val lore_file_stage_move_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_stage_move_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove files from the staging area without discarding local changes.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_UNSTAGE_BEGIN` | `lore_file_unstage_begin_event_data_t` | Emitted when unstage begins, includes path count |
     * | `LORE_EVENT_FILE_UNSTAGE_PROGRESS` | `lore_file_unstage_progress_event_data_t` | Emitted periodically during unstaging |
     * | `LORE_EVENT_FILE_UNSTAGE_END` | `lore_file_unstage_end_event_data_t` | Emitted when unstaging completes |
     * | `LORE_EVENT_FILE_UNSTAGE_REVISION` | `lore_file_unstage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_UNSTAGE_FILE` | `lore_file_unstage_file_event_data_t` | Emitted for each file that was unstaged |
     */
    val lore_file_unstage: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_unstage",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_unstage`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_UNSTAGE_BEGIN` | `lore_file_unstage_begin_event_data_t` | Emitted when unstage begins, includes path count |
     * | `LORE_EVENT_FILE_UNSTAGE_PROGRESS` | `lore_file_unstage_progress_event_data_t` | Emitted periodically during unstaging |
     * | `LORE_EVENT_FILE_UNSTAGE_END` | `lore_file_unstage_end_event_data_t` | Emitted when unstaging completes |
     * | `LORE_EVENT_FILE_UNSTAGE_REVISION` | `lore_file_unstage_revision_event_data_t` | Emitted with the resulting staged revision |
     * | `LORE_EVENT_FILE_UNSTAGE_FILE` | `lore_file_unstage_file_event_data_t` | Emitted for each file that was unstaged |
     */
    val lore_file_unstage_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_unstage_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Write binary content to a file in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_WRITE` | `lore_file_write_event_data_t` | Emitted when the file has been successfully written to the repository |
     */
    val lore_file_write: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_write",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_file_write`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## File Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_FILE_WRITE` | `lore_file_write_event_data_t` | Emitted when the file has been successfully written to the repository |
     */
    val lore_file_write_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_file_write_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Add a new layer to the repository configuration.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Layer Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LAYER_ADD` | `lore_layer_add_event_data_t` | Emitted when a layer has been successfully added |
     */
    val lore_layer_add: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_add",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_layer_add`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Layer Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LAYER_ADD` | `lore_layer_add_event_data_t` | Emitted when a layer has been successfully added |
     */
    val lore_layer_add_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_add_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all layers configured in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Layer Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LAYER_ENTRY` | `lore_layer_entry_event_data_t` | Emitted for each layer configured in the repository |
     */
    val lore_layer_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_layer_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Layer Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LAYER_ENTRY` | `lore_layer_entry_event_data_t` | Emitted for each layer configured in the repository |
     */
    val lore_layer_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove a layer from the repository configuration.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_layer_remove: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_remove",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_layer_remove`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_layer_remove_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_layer_remove_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Add a link to another repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CLONE_BEGIN` | `lore_repository_clone_begin_event_data_t` | Emitted when cloning a linked repository begins |
     * | `LORE_EVENT_REPOSITORY_CLONE_END` | `lore_repository_clone_end_event_data_t` | Emitted when cloning a linked repository completes |
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when the link has been added and saved |
     */
    val lore_link_add: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_add",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_link_add`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CLONE_BEGIN` | `lore_repository_clone_begin_event_data_t` | Emitted when cloning a linked repository begins |
     * | `LORE_EVENT_REPOSITORY_CLONE_END` | `lore_repository_clone_end_event_data_t` | Emitted when cloning a linked repository completes |
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when the link has been added and saved |
     */
    val lore_link_add_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_add_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all repository links configured in the current repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_ENTRY` | `lore_link_entry_event_data_t` | Emitted for each linked repository |
     */
    val lore_link_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_link_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_ENTRY` | `lore_link_entry_event_data_t` | Emitted for each linked repository |
     */
    val lore_link_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove a link to another repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when the link has been removed |
     */
    val lore_link_remove: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_remove",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_link_remove`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when the link has been removed |
     */
    val lore_link_remove_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_remove_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Update properties of an existing repository link.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when a link property is updated or finalized |
     */
    val lore_link_update: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_update",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_link_update`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Link Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LINK_CHANGE` | `lore_link_change_event_data_t` | Emitted when a link property is updated or finalized |
     */
    val lore_link_update_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_link_update_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Acquire exclusive locks on one or more files in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_ACQUIRE` | `lore_lock_file_acquire_event_data_t` | Emitted for each file for which a lock was successfully acquired |
     * | `LORE_EVENT_LOCK_FILE_ACQUIRE_IGNORE` | `lore_lock_file_acquire_ignore_event_data_t` | Emitted for each file for which a lock was ignored (already owned) |
     */
    val lore_lock_file_acquire: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_acquire",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_lock_file_acquire`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_ACQUIRE` | `lore_lock_file_acquire_event_data_t` | Emitted for each file for which a lock was successfully acquired |
     * | `LORE_EVENT_LOCK_FILE_ACQUIRE_IGNORE` | `lore_lock_file_acquire_ignore_event_data_t` | Emitted for each file for which a lock was ignored (already owned) |
     */
    val lore_lock_file_acquire_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_acquire_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Query which files are currently locked, optionally filtered by user or path.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_QUERY_BEGIN` | `lore_lock_file_query_begin_event_data_t` | Emitted before query results begin streaming |
     * | `LORE_EVENT_LOCK_FILE_QUERY` | `lore_lock_file_query_event_data_t` | Emitted for each file matching the query |
     */
    val lore_lock_file_query: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_query",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_lock_file_query`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_QUERY_BEGIN` | `lore_lock_file_query_begin_event_data_t` | Emitted before query results begin streaming |
     * | `LORE_EVENT_LOCK_FILE_QUERY` | `lore_lock_file_query_event_data_t` | Emitted for each file matching the query |
     */
    val lore_lock_file_query_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_query_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Release file locks previously acquired by this client.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_RELEASE` | `lore_lock_file_release_event_data_t` | Emitted for each file lock successfully released |
     * | `LORE_EVENT_LOCK_FILE_RELEASE_NOT_FOUND` | `lore_lock_file_release_not_found_event_data_t` | Emitted for each file whose lock was not found |
     */
    val lore_lock_file_release: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_release",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_lock_file_release`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_RELEASE` | `lore_lock_file_release_event_data_t` | Emitted for each file lock successfully released |
     * | `LORE_EVENT_LOCK_FILE_RELEASE_NOT_FOUND` | `lore_lock_file_release_not_found_event_data_t` | Emitted for each file whose lock was not found |
     */
    val lore_lock_file_release_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_release_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Get the lock status of files in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_STATUS_BEGIN` | `lore_lock_file_status_begin_event_data_t` | Emitted before lock status results begin streaming |
     * | `LORE_EVENT_LOCK_FILE_STATUS` | `lore_lock_file_status_event_data_t` | Emitted for each locked file with owner and lock details |
     */
    val lore_lock_file_status: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_status",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_lock_file_status`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Lock Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOCK_FILE_STATUS_BEGIN` | `lore_lock_file_status_begin_event_data_t` | Emitted before lock status results begin streaming |
     * | `LORE_EVENT_LOCK_FILE_STATUS` | `lore_lock_file_status_event_data_t` | Emitted for each locked file with owner and lock details |
     */
    val lore_lock_file_status_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_lock_file_status_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Apply the given logging configuration.
     * 
     * Returns 0 when the configuration was applied and a non-zero value when it
     * was not.
     */
    val lore_log_configure: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_log_configure",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS),
        )
    }

    /**
     * Subscribe to repository notifications.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Notification Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_NOTIFICATION_SUBSCRIBED` | `lore_notification_subscribed_event_data_t` | Emitted when successfully subscribed to repository notifications |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_CREATED` | `lore_notification_branch_created_event_data_t` | Emitted when a branch is created in the repository (push notification) |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_DELETED` | `lore_notification_branch_deleted_event_data_t` | Emitted when a branch is deleted in the repository (push notification) |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_PUSHED` | `lore_notification_branch_pushed_event_data_t` | Emitted when a branch is pushed to (push notification) |
     * | `LORE_EVENT_NOTIFICATION_RESOURCE_LOCKED` | `lore_notification_resource_locked_event_data_t` | Emitted when a resource is locked (push notification) |
     * | `LORE_EVENT_NOTIFICATION_RESOURCE_UNLOCKED` | `lore_notification_resource_unlocked_event_data_t` | Emitted when a resource is unlocked (push notification) |
     */
    val lore_notification_subscribe: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_notification_subscribe",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_notification_subscribe`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Notification Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_NOTIFICATION_SUBSCRIBED` | `lore_notification_subscribed_event_data_t` | Emitted when successfully subscribed to repository notifications |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_CREATED` | `lore_notification_branch_created_event_data_t` | Emitted when a branch is created in the repository (push notification) |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_DELETED` | `lore_notification_branch_deleted_event_data_t` | Emitted when a branch is deleted in the repository (push notification) |
     * | `LORE_EVENT_NOTIFICATION_BRANCH_PUSHED` | `lore_notification_branch_pushed_event_data_t` | Emitted when a branch is pushed to (push notification) |
     * | `LORE_EVENT_NOTIFICATION_RESOURCE_LOCKED` | `lore_notification_resource_locked_event_data_t` | Emitted when a resource is locked (push notification) |
     * | `LORE_EVENT_NOTIFICATION_RESOURCE_UNLOCKED` | `lore_notification_resource_unlocked_event_data_t` | Emitted when a resource is unlocked (push notification) |
     */
    val lore_notification_subscribe_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_notification_subscribe_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Unsubscribe from repository notifications.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Notification Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_NOTIFICATION_UNSUBSCRIBED` | `lore_notification_unsubscribed_event_data_t` | Emitted when successfully unsubscribed from repository notifications |
     */
    val lore_notification_unsubscribe: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_notification_unsubscribe",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_notification_unsubscribe`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Notification Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_NOTIFICATION_UNSUBSCRIBED` | `lore_notification_unsubscribed_event_data_t` | Emitted when successfully unsubscribed from repository notifications |
     */
    val lore_notification_unsubscribe_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_notification_unsubscribe_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Clone a remote repository to a local path.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CLONE_BEGIN` | `lore_repository_clone_begin_event_data_t` | Emitted when clone begins, includes remote URL and target path |
     * | `LORE_EVENT_REPOSITORY_CLONE_PROGRESS` | `lore_repository_clone_progress_event_data_t` | Emitted periodically during clone with progress data |
     * | `LORE_EVENT_REPOSITORY_CLONE_END` | `lore_repository_clone_end_event_data_t` | Emitted when clone completes successfully |
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted after resolving the target revision to sync during clone |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file written during initial sync |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during initial file sync |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted with the resulting revision |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view filters |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written to the local store |
     */
    val lore_repository_clone: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_clone",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_clone`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CLONE_BEGIN` | `lore_repository_clone_begin_event_data_t` | Emitted when clone begins, includes remote URL and target path |
     * | `LORE_EVENT_REPOSITORY_CLONE_PROGRESS` | `lore_repository_clone_progress_event_data_t` | Emitted periodically during clone with progress data |
     * | `LORE_EVENT_REPOSITORY_CLONE_END` | `lore_repository_clone_end_event_data_t` | Emitted when clone completes successfully |
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted after resolving the target revision to sync during clone |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file written during initial sync |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during initial file sync |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted with the resulting revision |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view filters |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written to the local store |
     */
    val lore_repository_clone_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_clone_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read a configuration value of the current repository by key.
     */
    val lore_repository_config_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_config_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_config_get`.
     */
    val lore_repository_config_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_config_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Create a new Lore repository on the remote server.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CREATE` | `lore_repository_create_event_data_t` | Emitted when the repository has been successfully created |
     */
    val lore_repository_create: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_create",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_create`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_CREATE` | `lore_repository_create_event_data_t` | Emitted when the repository has been successfully created |
     */
    val lore_repository_create_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_create_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Dump the internal state of the repository for diagnostic purposes.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_DUMP_BEGIN` | `lore_repository_dump_begin_event_data_t` | Emitted before dump output begins |
     * | `LORE_EVENT_REPOSITORY_DUMP_END` | `lore_repository_dump_end_event_data_t` | Emitted when dump completes |
     * | `LORE_EVENT_REPOSITORY_STATE_DUMP` | `lore_repository_state_dump_event_data_t` | Emitted with repository state summary |
     * | `LORE_EVENT_REPOSITORY_STATE_DUMP_NODE` | `lore_repository_state_dump_node_event_data_t` | Emitted for each node in the state tree |
     */
    val lore_repository_dump: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_dump",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_dump`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_DUMP_BEGIN` | `lore_repository_dump_begin_event_data_t` | Emitted before dump output begins |
     * | `LORE_EVENT_REPOSITORY_DUMP_END` | `lore_repository_dump_end_event_data_t` | Emitted when dump completes |
     * | `LORE_EVENT_REPOSITORY_STATE_DUMP` | `lore_repository_state_dump_event_data_t` | Emitted with repository state summary |
     * | `LORE_EVENT_REPOSITORY_STATE_DUMP_NODE` | `lore_repository_state_dump_node_event_data_t` | Emitted for each node in the state tree |
     */
    val lore_repository_dump_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_dump_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Flush pending repository state to persistent storage.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_flush: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_flush",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_flush`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_flush_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_flush_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Run garbage collection to reclaim unreferenced storage in the repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_gc: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_gc",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_gc`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_gc_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_gc_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve metadata about the current repository.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_DATA` | `lore_repository_data_event_data_t` | Emitted with repository metadata (name, URL, branch info, etc.) |
     */
    val lore_repository_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_info`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_DATA` | `lore_repository_data_event_data_t` | Emitted with repository metadata (name, URL, branch info, etc.) |
     */
    val lore_repository_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List the tracked instances of the repository.
     */
    val lore_repository_instance_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_instance_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_instance_list`.
     */
    val lore_repository_instance_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_instance_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Remove stale instances of the repository that are no longer present.
     */
    val lore_repository_instance_prune: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_instance_prune",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_instance_prune`.
     */
    val lore_repository_instance_prune_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_instance_prune_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all repositories available on the remote server.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_LIST_ENTRY` | `lore_repository_list_entry_event_data_t` | Emitted for each repository found |
     */
    val lore_repository_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_LIST_ENTRY` | `lore_repository_list_entry_event_data_t` | Emitted for each repository found |
     */
    val lore_repository_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Clear repository metadata keys. Clears all user-defined keys when none are
     * given.
     */
    val lore_repository_metadata_clear: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_clear",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_metadata_clear`.
     */
    val lore_repository_metadata_clear_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_clear_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve repository metadata. Reads a single key, or all entries when no
     * key is given.
     */
    val lore_repository_metadata_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_metadata_get`.
     */
    val lore_repository_metadata_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set repository metadata key-value pairs.
     */
    val lore_repository_metadata_set: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_set",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_metadata_set`.
     */
    val lore_repository_metadata_set_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_metadata_set_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Release all cached store references for the given repository path.
     * 
     * Frees in-memory store data and releases file-backed store cache entries.
     * Any active repository contexts for this path remain valid, but once they
     * are dropped the stores will be freed. Subsequent opens will create fresh stores.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_release: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_release",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_release`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_repository_release_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_release_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Show the working directory status, including staged, dirty, and conflicted files.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_STATUS_REVISION` | `lore_repository_status_revision_event_data_t` | Emitted with current and staged revision info |
     * | `LORE_EVENT_REPOSITORY_STATUS_FILE` | `lore_repository_status_file_event_data_t` | Emitted for each file with pending changes, conflict status, or untracked status |
     * | `LORE_EVENT_PATH_IGNORE` | `lore_path_ignore_event_data_t` | Emitted for each path excluded by ignore rules |
     */
    val lore_repository_status: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_status",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_status`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_STATUS_REVISION` | `lore_repository_status_revision_event_data_t` | Emitted with current and staged revision info |
     * | `LORE_EVENT_REPOSITORY_STATUS_FILE` | `lore_repository_status_file_event_data_t` | Emitted for each file with pending changes, conflict status, or untracked status |
     * | `LORE_EVENT_PATH_IGNORE` | `lore_path_ignore_event_data_t` | Emitted for each path excluded by ignore rules |
     */
    val lore_repository_status_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_status_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Query the repository's immutable fragment store.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_STORE_IMMUTABLE_QUERY` | `lore_repository_store_immutable_query_event_data_t` | Emitted for each fragment entry found in the immutable store |
     */
    val lore_repository_store_immutable_query: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_store_immutable_query",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_store_immutable_query`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_STORE_IMMUTABLE_QUERY` | `lore_repository_store_immutable_query_event_data_t` | Emitted for each fragment entry found in the immutable store |
     */
    val lore_repository_store_immutable_query_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_store_immutable_query_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Update the recorded path of the current repository instance to its present
     * location.
     */
    val lore_repository_update_path: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_update_path",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_update_path`.
     */
    val lore_repository_update_path_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_update_path_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Verify the integrity of the repository's stored fragments.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_VERIFY_STATE_BEGIN` | `lore_repository_verify_state_begin_event_data_t` | Emitted when verify begins |
     * | `LORE_EVENT_REPOSITORY_VERIFY_STATE_END` | `lore_repository_verify_state_end_event_data_t` | Emitted when verify completes (success or with errors) |
     * | `LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT` | `lore_repository_verify_fragment_event_data_t` | Emitted for each fragment verified in the local store |
     * | `LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT_REMOTE` | `lore_repository_verify_fragment_remote_event_data_t` | Emitted for each fragment verified against the remote store |
     */
    val lore_repository_verify_state: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_verify_state",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_repository_verify_state`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Repository Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REPOSITORY_VERIFY_STATE_BEGIN` | `lore_repository_verify_state_begin_event_data_t` | Emitted when verify begins |
     * | `LORE_EVENT_REPOSITORY_VERIFY_STATE_END` | `lore_repository_verify_state_end_event_data_t` | Emitted when verify completes (success or with errors) |
     * | `LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT` | `lore_repository_verify_fragment_event_data_t` | Emitted for each fragment verified in the local store |
     * | `LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT_REMOTE` | `lore_repository_verify_fragment_remote_event_data_t` | Emitted for each fragment verified against the remote store |
     */
    val lore_repository_verify_state_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_repository_verify_state_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Amend the most recent revision with updated metadata.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the amended revision details |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the amended revision |
     */
    val lore_revision_amend: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_amend",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_amend`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the amended revision details |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the amended revision |
     */
    val lore_revision_amend_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_amend_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Commit staged files to create a new revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when commit begins fragmenting files |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during commit with file processing counts |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details (hash, branch, parents) |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written or deduplicated |
     */
    val lore_revision_commit: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_commit",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_commit`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when commit begins fragmenting files |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted periodically during commit with file processing counts |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when commit file processing completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revision details (hash, branch, parents) |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata entry of the committed revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written or deduplicated |
     */
    val lore_revision_commit_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_commit_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Show files that differ between two revisions.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_DIFF_FILE` | `lore_revision_diff_file_event_data_t` | Emitted for each file that differs between the two revisions |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial or numbered revision reference |
     */
    val lore_revision_diff: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_diff",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_diff`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_DIFF_FILE` | `lore_revision_diff_file_event_data_t` | Emitted for each file that differs between the two revisions |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial or numbered revision reference |
     */
    val lore_revision_diff_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_diff_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Find a revision by metadata or revision number.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_FIND` | `lore_revision_find_event_data_t` | Emitted when a matching revision is found (exact or partial match) |
     */
    val lore_revision_find: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_find",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_find`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_FIND` | `lore_revision_find_event_data_t` | Emitted when a matching revision is found (exact or partial match) |
     */
    val lore_revision_find_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_find_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve the commit history of the current branch.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_HISTORY` | `lore_revision_history_event_data_t` | Emitted once with summary info before entries stream |
     * | `LORE_EVENT_REVISION_HISTORY_ENTRY` | `lore_revision_history_entry_event_data_t` | Emitted for each revision in the history |
     */
    val lore_revision_history: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_history",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_history`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_HISTORY` | `lore_revision_history_event_data_t` | Emitted once with summary info before entries stream |
     * | `LORE_EVENT_REVISION_HISTORY_ENTRY` | `lore_revision_history_entry_event_data_t` | Emitted for each revision in the history |
     */
    val lore_revision_history_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_history_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve metadata and delta information about a specific revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_INFO` | `lore_revision_info_event_data_t` | Emitted with revision metadata (hash, branch, parents, file count, etc.) |
     * | `LORE_EVENT_REVISION_INFO_DELTA` | `lore_revision_info_delta_event_data_t` | Emitted with delta information between revision and its parent (when delta=true) |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value of the revision (when metadata=true) |
     */
    val lore_revision_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_info`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_INFO` | `lore_revision_info_event_data_t` | Emitted with revision metadata (hash, branch, parents, file count, etc.) |
     * | `LORE_EVENT_REVISION_INFO_DELTA` | `lore_revision_info_delta_event_data_t` | Emitted with delta information between revision and its parent (when delta=true) |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value of the revision (when metadata=true) |
     */
    val lore_revision_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Clear all metadata from the current revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA_CLEAR_REVISION` | `lore_metadata_clear_revision_event_data_t` | Emitted when metadata has been cleared for the current revision |
     */
    val lore_revision_metadata_clear: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_clear",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_metadata_clear`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA_CLEAR_REVISION` | `lore_metadata_clear_revision_event_data_t` | Emitted when metadata has been cleared for the current revision |
     */
    val lore_revision_metadata_clear_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_clear_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Get a specific metadata key/value pair from the current revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted with the requested key/value for the revision |
     */
    val lore_revision_metadata_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_metadata_get`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted with the requested key/value for the revision |
     */
    val lore_revision_metadata_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List all metadata key/value pairs associated with the current revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value associated with the revision |
     */
    val lore_revision_metadata_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_metadata_list`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for each metadata key/value associated with the revision |
     */
    val lore_revision_metadata_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set a metadata key/value pair on the current revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_revision_metadata_set: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_set",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_metadata_set`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_revision_metadata_set_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_metadata_set_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Restore the working directory to a previously committed revision.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_RESTORE_FILE_BEGIN` | `lore_revision_restore_file_begin_event_data_t` | Emitted when restore starts processing files |
     * | `LORE_EVENT_REVISION_RESTORE_FILE` | `lore_revision_restore_file_event_data_t` | Emitted for each file being restored |
     * | `LORE_EVENT_REVISION_RESTORE_FILE_END` | `lore_revision_restore_file_end_event_data_t` | Emitted when file processing completes |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_BEGIN` | `lore_revision_restore_fragment_begin_event_data_t` | Emitted when fragment download begins for a file |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_PROGRESS` | `lore_revision_restore_fragment_progress_event_data_t` | Emitted periodically during fragment download |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_END` | `lore_revision_restore_fragment_end_event_data_t` | Emitted when fragment download completes |
     * | `LORE_EVENT_REVISION_RESTORE_REVISION` | `lore_revision_restore_revision_event_data_t` | Emitted with the restored revision details |
     * | `LORE_EVENT_REVISION_RESTORE_SYNC_BEGIN` | `lore_revision_restore_sync_begin_event_data_t` | Emitted when starting to apply the changes on the target state |
     * | `LORE_EVENT_REVISION_RESTORE_SYNC_END` | `lore_revision_restore_sync_end_event_data_t` | Emitted after applying the changes on the target state is complete |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit of restored revision starts |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed restored revision |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during changes realization |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the restored revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for fragments written during restore commit |
     */
    val lore_revision_restore: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_restore",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_restore`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revision Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_RESTORE_FILE_BEGIN` | `lore_revision_restore_file_begin_event_data_t` | Emitted when restore starts processing files |
     * | `LORE_EVENT_REVISION_RESTORE_FILE` | `lore_revision_restore_file_event_data_t` | Emitted for each file being restored |
     * | `LORE_EVENT_REVISION_RESTORE_FILE_END` | `lore_revision_restore_file_end_event_data_t` | Emitted when file processing completes |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_BEGIN` | `lore_revision_restore_fragment_begin_event_data_t` | Emitted when fragment download begins for a file |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_PROGRESS` | `lore_revision_restore_fragment_progress_event_data_t` | Emitted periodically during fragment download |
     * | `LORE_EVENT_REVISION_RESTORE_FRAGMENT_END` | `lore_revision_restore_fragment_end_event_data_t` | Emitted when fragment download completes |
     * | `LORE_EVENT_REVISION_RESTORE_REVISION` | `lore_revision_restore_revision_event_data_t` | Emitted with the restored revision details |
     * | `LORE_EVENT_REVISION_RESTORE_SYNC_BEGIN` | `lore_revision_restore_sync_begin_event_data_t` | Emitted when sync begins |
     * | `LORE_EVENT_REVISION_RESTORE_SYNC_END` | `lore_revision_restore_sync_end_event_data_t` | Emitted when sync completes |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit of restored revision starts |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed restored revision |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during changes realization |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the restored revision |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for fragments written during restore commit |
     */
    val lore_revision_restore_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_restore_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Revert a revision, applying its inverse changes to the working tree.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_START_BEGIN` | `lore_revert_start_begin_event_data_t` | Emitted when revert begins, includes target revision info |
     * | `LORE_EVENT_REVERT_START_END` | `lore_revert_start_end_event_data_t` | Emitted when revert completes, includes conflict flag |
     * | `LORE_EVENT_REVERT_CONFLICT_FILE` | `lore_revert_conflict_file_event_data_t` | Emitted for each file with an unresolved revert conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during apply_diff phase |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified during revert realization |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during revert |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revert revision |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the auto-commit |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for fragments written during auto-commit |
     */
    val lore_revision_revert: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Abort an in-progress revert operation and restore the previous state.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_ABORT_BEGIN` | `lore_revert_abort_begin_event_data_t` | Emitted when revert abort begins |
     * | `LORE_EVENT_REVERT_ABORT_END` | `lore_revert_abort_end_event_data_t` | Emitted when revert abort completes |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization while reverting |
     */
    val lore_revision_revert_abort: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_abort",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_abort`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_ABORT_BEGIN` | `lore_revert_abort_begin_event_data_t` | Emitted when revert abort begins |
     * | `LORE_EVENT_REVERT_ABORT_END` | `lore_revert_abort_end_event_data_t` | Emitted when revert abort completes |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization while reverting |
     */
    val lore_revision_revert_abort_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_abort_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_START_BEGIN` | `lore_revert_start_begin_event_data_t` | Emitted when revert begins, includes target revision info |
     * | `LORE_EVENT_REVERT_START_END` | `lore_revert_start_end_event_data_t` | Emitted when revert completes, includes conflict flag |
     * | `LORE_EVENT_REVERT_CONFLICT_FILE` | `lore_revert_conflict_file_event_data_t` | Emitted for each file with an unresolved revert conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during apply_diff phase |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file modified during revert realization |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during revert |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-commit starts (no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed revert revision |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the auto-commit |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for fragments written during auto-commit |
     */
    val lore_revision_revert_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a revert conflict by marking conflicting files as resolved.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file marked as resolved |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_resolve`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file marked as resolved |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a revert conflict by accepting the "mine" version of each conflicting file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file resolved by keeping "mine" |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve_mine: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve_mine",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_resolve_mine`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file resolved by keeping "mine" |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve_mine_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve_mine_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a revert conflict by accepting the "theirs" version of each conflicting file.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file resolved by keeping "theirs" |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve_theirs: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve_theirs",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_resolve_theirs`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_RESOLVE_FILE` | `lore_revert_resolve_file_event_data_t` | Emitted for each file resolved by keeping "theirs" |
     * | `LORE_EVENT_REVERT_RESOLVE_REVISION` | `lore_revert_resolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_resolve_theirs_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_resolve_theirs_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Restart a revert operation, re-materializing files with conflicts.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_CONFLICT_FILE` | `lore_revert_conflict_file_event_data_t` | Emitted for each file with a remaining revert conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file re-materialized |
     */
    val lore_revision_revert_restart: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_restart",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_restart`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_CONFLICT_FILE` | `lore_revert_conflict_file_event_data_t` | Emitted for each file with a remaining revert conflict |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted during file realization |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file re-materialized |
     */
    val lore_revision_revert_restart_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_restart_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Mark conflicting files in a revert operation as unresolved.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_UNRESOLVE_FILE` | `lore_revert_unresolve_file_event_data_t` | Emitted for each file marked as unresolved |
     * | `LORE_EVENT_REVERT_UNRESOLVE_REVISION` | `lore_revert_unresolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_unresolve: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_unresolve",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_revert_unresolve`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Revert Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVERT_UNRESOLVE_FILE` | `lore_revert_unresolve_file_event_data_t` | Emitted for each file marked as unresolved |
     * | `LORE_EVENT_REVERT_UNRESOLVE_REVISION` | `lore_revert_unresolve_revision_event_data_t` | Emitted with the updated staged revision |
     */
    val lore_revision_revert_unresolve_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_revert_unresolve_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Synchronize the working directory to a target revision, optionally merging divergent branches.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Sync Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted once after resolving the target revision with source/target revision info, branch, and remote URL |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file deleted, modified, added, or merged during sync |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during file realization and once at completion with cumulative update/delete/automerge/conflict counts |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted once at the end with the resulting revision, branch, and merge/conflict flags |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial or numbered revision reference |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     * | `LORE_EVENT_BRANCH_MERGE_START_BEGIN` | `lore_branch_merge_start_begin_event_data_t` | Emitted when an auto-merge is initiated (diverged branches) |
     * | `LORE_EVENT_BRANCH_MERGE_START_END` | `lore_branch_merge_start_end_event_data_t` | Emitted when the auto-merge operation completes |
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with an unresolved merge conflict |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-merge auto-commits (no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed merge revision |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the auto-merge commit |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written during auto-merge commit |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during merge realization |
     */
    val lore_revision_sync: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_sync",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_revision_sync`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Sync Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_REVISION_SYNC_TARGET` | `lore_revision_sync_target_event_data_t` | Emitted once after resolving the target revision with source/target revision info, branch, and remote URL |
     * | `LORE_EVENT_REVISION_SYNC_FILE` | `lore_revision_sync_file_event_data_t` | Emitted for each file deleted, modified, added, or merged during sync |
     * | `LORE_EVENT_REVISION_SYNC_PROGRESS` | `lore_revision_sync_progress_event_data_t` | Emitted periodically during file realization and once at completion with cumulative update/delete/automerge/conflict counts |
     * | `LORE_EVENT_REVISION_SYNC_REVISION` | `lore_revision_sync_revision_event_data_t` | Emitted once at the end with the resulting revision, branch, and merge/conflict flags |
     * | `LORE_EVENT_REVISION_RESOLVE` | `lore_revision_resolve_event_data_t` | Emitted when resolving a partial or numbered revision reference |
     * | `LORE_EVENT_FILTER_EXCLUDE` | `lore_filter_exclude_event_data_t` | Emitted for each path excluded by view or ignore filters |
     * | `LORE_EVENT_BRANCH_MERGE_START_BEGIN` | `lore_branch_merge_start_begin_event_data_t` | Emitted when an auto-merge is initiated (diverged branches) |
     * | `LORE_EVENT_BRANCH_MERGE_START_END` | `lore_branch_merge_start_end_event_data_t` | Emitted when the auto-merge operation completes |
     * | `LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE` | `lore_branch_merge_conflict_file_event_data_t` | Emitted for each file with an unresolved merge conflict |
     * | `LORE_EVENT_REVISION_COMMIT_BEGIN` | `lore_revision_commit_begin_event_data_t` | Emitted when auto-merge auto-commits (no conflicts) |
     * | `LORE_EVENT_REVISION_COMMIT_PROGRESS` | `lore_revision_commit_progress_event_data_t` | Emitted during auto-commit |
     * | `LORE_EVENT_REVISION_COMMIT_END` | `lore_revision_commit_end_event_data_t` | Emitted when auto-commit completes |
     * | `LORE_EVENT_REVISION_COMMIT_REVISION` | `lore_revision_commit_revision_event_data_t` | Emitted with the committed merge revision |
     * | `LORE_EVENT_METADATA` | `lore_metadata_event_data_t` | Emitted for metadata of the auto-merge commit |
     * | `LORE_EVENT_FRAGMENT_WRITE` | `lore_fragment_write_event_data_t` | Emitted for each fragment written during auto-merge commit |
     * | `LORE_EVENT_FILE_STAGE_FILE` | `lore_file_stage_file_event_data_t` | Emitted for each file staged for deletion during merge realization |
     */
    val lore_revision_sync_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_sync_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Add a batch of nodes to a loaded revision tree. An entry parents onto an
     * existing node or onto an earlier entry, so one call builds a subtree. Every
     * entry is checked before any node is created, so one bad entry rejects the
     * call and creates nothing; the reason names the offending entry's batch index,
     * which a caller leaving `id` at zero has no other way to identify. A failure
     * after those checks pass is internal and may leave part of the batch created.
     * 
     * A link entry's target revision is not resolved here, so a link naming a
     * revision that cannot be read is accepted and fails only when something later
     * reads through it. Entries under separate parents are created concurrently,
     * but allocating a node slot is serialized per loaded tree.
     * 
     * | Terminal event                            | Payload                                          | Notes                                                    |
     * |-------------------------------------------|--------------------------------------------------|----------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_ADD_COMPLETE`   | `lore_revision_tree_add_complete_event_data_t`   | One per entry created or individually rejected           |
     * | `LORE_EVENT_REVISION_TREE_BATCH_COMPLETE` | `lore_revision_tree_batch_complete_event_data_t` | Exactly one, carrying the call id and the call's outcome |
     */
    val lore_revision_tree_add: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_add",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Add a batch of nodes to a loaded revision tree (async variant).
     */
    val lore_revision_tree_add_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_add_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Release a memory-based revision tree handle.
     * 
     * Subsequent calls against the same handle return `InvalidArguments`. The
     * call blocks until every in-flight op on the handle has paired its
     * decrement.
     * 
     * | Terminal event                              | Payload                                       | Notes                                              |
     * |---------------------------------------------|-----------------------------------------------|----------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_CLOSE_COMPLETE`   | `lore_revision_tree_close_complete_event_data_t` | Emitted on success carrying the caller id       |
     */
    val lore_revision_tree_close: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_close",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Release a memory-based revision tree handle (async variant).
     */
    val lore_revision_tree_close_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_close_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch the loaded revision's record-level metadata (parents, creation
     * timestamp, author identity, metadata key count). Revision-scoped — no node id.
     * 
     * | Terminal event                     | Payload                                | Notes                                                   |
     * |------------------------------------|----------------------------------------|---------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_INFO`    | `lore_revision_tree_info_event_data_t` | Carries the revision record metadata for the handle     |
     */
    val lore_revision_tree_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch the loaded revision's record-level metadata (async variant).
     */
    val lore_revision_tree_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stream the children of a directory node in a loaded revision tree.
     * 
     * | Terminal event                       | Payload                                | Notes                                                          |
     * |--------------------------------------|----------------------------------------|----------------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_CHILD`     | `lore_revision_tree_child_event_data_t` | One per child; an empty directory emits none before `Complete` |
     */
    val lore_revision_tree_list_children: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_list_children",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stream the children of a directory node (async variant).
     */
    val lore_revision_tree_list_children_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_list_children_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Open a memory-based revision tree handle on the given
     * `(store, repository, revision_hash)` tuple. `revision_hash == 0` opens an
     * empty tree suitable for committing an initial revision.
     * 
     * | Terminal event                       | Payload                                | Notes                                              |
     * |--------------------------------------|----------------------------------------|----------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_LOADED`    | `lore_revision_tree_loaded_event_data_t` | Emitted on success carrying the opened handle id |
     */
    val lore_revision_tree_load: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_load",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Open a memory-based revision tree handle (async variant).
     */
    val lore_revision_tree_load_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_load_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch the per-node record for a single node id in a loaded revision tree.
     * 
     * | Terminal event                          | Payload                                     | Notes                                                          |
     * |-----------------------------------------|---------------------------------------------|----------------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_NODE_INFO`    | `lore_revision_tree_node_info_event_data_t` | Carries the node record, uniform across every node id (revision metadata: `lore_revision_tree_info`) |
     */
    val lore_revision_tree_node_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_node_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch the per-node record for a single node id (async variant).
     */
    val lore_revision_tree_node_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_node_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Reconstruct the full UTF-8 path for a node id by walking parent pointers,
     * relative to the handle's own tree root.
     * 
     * | Terminal event                       | Payload                                     | Notes                                                  |
     * |--------------------------------------|---------------------------------------------|--------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_NODE_PATH` | `lore_revision_tree_node_path_event_data_t` | Carries the path; the root resolves to the empty path  |
     */
    val lore_revision_tree_node_path: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_node_path",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Reconstruct the full UTF-8 path for a node id (async variant).
     */
    val lore_revision_tree_node_path_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_node_path_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a UTF-8 path against a loaded revision tree to a node id. An empty
     * path resolves to the root node.
     * 
     * | Terminal event                                       | Payload                                             | Notes                                                       |
     * |------------------------------------------------------|-----------------------------------------------------|-------------------------------------------------------------|
     * | `LORE_EVENT_REVISION_TREE_RESOLVE_PATH_COMPLETE`     | `lore_revision_tree_resolve_path_complete_event_data_t` | Carries the resolved node id and the per-call outcome   |
     */
    val lore_revision_tree_resolve_path: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_resolve_path",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Resolve a UTF-8 path against a loaded revision tree (async variant).
     */
    val lore_revision_tree_resolve_path_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_revision_tree_resolve_path_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Start the Lore background service.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_service_start: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_service_start",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_service_start`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_service_start_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_service_start_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Stop the Lore background service.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_service_stop: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_service_stop",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Asynchronous version of `lore_service_stop`.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_service_stop_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_service_stop_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Install the memory allocator the library uses for its own allocations.
     * Provide functions for allocation, zeroed allocation, reallocation and
     * freeing. Call this before the library makes its first allocation; once it
     * has allocated, the allocator can no longer be changed.
     * 
     * Returns 0 when the allocator was installed and a non-zero value when it was
     * too late to install one, in which case the call does nothing.
     */
    val lore_set_allocator: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_set_allocator",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
        )
    }

    /**
     * Limits the total number of threads Lore sizes its pools for.
     * 
     * Lore internally decides how many worker, blocking and compute threads to use
     * based on this ceiling and the host's processor count. Pass `0` for "no
     * limit" (the default — pools are sized from the processor count). The
     * `LORE_MAX_THREADS` environment variable overrides this count when set above
     * zero. The `LORE_WORKER_THREADS`, `LORE_BLOCKING_THREADS` and
     * `LORE_COMPUTE_THREADS` environment variables still override the count of
     * their respective pool with an absolute value when set.
     * 
     * Must be called before the first Lore operation, while the runtime and
     * compute pool are still unconstructed. Returns `0` if the limit was applied,
     * `1` if it had already been set (or the runtime was already running).
     */
    val lore_set_thread_limit: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_set_thread_limit",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG),
        )
    }

    /**
     * Create a new shared store at the specified path.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Shared Store Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_SHARED_STORE_CREATE` | `lore_shared_store_create_event_data_t` | Emitted on success after the shared store is created, carrying the path of the newly created store |
     */
    val lore_shared_store_create: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_create",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Create a new shared store at the specified path (async).
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Shared Store Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_SHARED_STORE_CREATE` | `lore_shared_store_create_event_data_t` | Emitted on success after the shared store is created, carrying the path of the newly created store |
     */
    val lore_shared_store_create_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_create_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve the path of the configured default shared store.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Shared Store Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_SHARED_STORE_INFO` | `lore_shared_store_info_event_data_t` | Emitted on success carrying the path of the configured default shared store |
     */
    val lore_shared_store_info: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_info",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Retrieve the path of the configured default shared store (async).
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     * 
     * ## Shared Store Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_SHARED_STORE_INFO` | `lore_shared_store_info_event_data_t` | Emitted on success carrying the path of the configured default shared store |
     */
    val lore_shared_store_info_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_info_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set whether to automatically use the shared store.
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_shared_store_set_use_automatically: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_set_use_automatically",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Set whether to automatically use the shared store (async).
     * 
     * # Events
     * 
     * Events are delivered via the callback as `lore_event_t`. Use the `tag` field to identify the event type.
     * 
     * ## Standard Events
     * 
     * These events are emitted by all interface functions:
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_LOG` | `lore_log_event_data_t` | Diagnostic messages throughout execution |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | Always emitted at the end; `status` is `0` on success or the error code on failure |
     * | `LORE_EVENT_END` | `lore_end_event_data_t` | Always emitted after `COMPLETE` to signal callback termination |
     */
    val lore_shared_store_set_use_automatically_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shared_store_set_use_automatically_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Shut the library down, stopping its worker threads and releasing the
     * resources it holds. Call this once, when no further calls will be made.
     * 
     * Returns 0 on success and a non-zero value on failure.
     */
    val lore_shutdown: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_shutdown",
            FunctionDescriptor.of(ValueLayout.JAVA_INT),
        )
    }

    /**
     * Release a content-addressed storage handle.
     * 
     * Subsequent calls against the same handle return `InvalidArguments`.
     * Close does not block on the flush it spawns — `Complete` fires after
     * the in-flight counter drains, not after the flush finishes.
     */
    val lore_storage_close: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_close",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Release a content-addressed storage handle (async variant).
     */
    val lore_storage_close_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_close_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Copy content from one partition to another within the same store.
     * 
     * Same-partition source/target rejects with `INVALID_ARGUMENTS`. The
     * item's content hash is preserved; only the source address is carried
     * in the per-item event.
     */
    val lore_storage_copy: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_copy",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Copy content (async variant).
     */
    val lore_storage_copy_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_copy_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Flush pending writes through the handle's stores.
     * 
     * On disk-backed stores this performs an fsync honoring `globals.sync_data`.
     * On in-memory stores the underlying flush is a no-op and the call still
     * completes with `status: 0`.
     */
    val lore_storage_flush: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_flush",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Flush pending writes through the handle's stores (async variant).
     */
    val lore_storage_flush_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_flush_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read one or more content-addressed buffers.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_GET_HEADER` | `lore_storage_get_header_event_data_t` | Size of the item's reassembled content, emitted before any DATA events |
     * | `LORE_EVENT_STORAGE_GET_DATA` | `lore_storage_get_data_event_data_t` | Payload bytes — valid only during the callback invocation |
     * | `LORE_EVENT_STORAGE_GET_ITEM_COMPLETE` | `lore_storage_get_item_complete_event_data_t` | Terminal per-item event |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status` is `0` iff every item succeeded, else the error code |
     */
    val lore_storage_get: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read one or more content-addressed buffers (async variant).
     */
    val lore_storage_get_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Write content-addressed payloads to filesystem paths.
     * 
     * Each item emits `LORE_EVENT_STORAGE_GET_ITEM_COMPLETE`. No HEADER or
     * DATA events are produced — the payload is written straight to disk.
     * On partial-write failure the library leaves whatever state the
     * failure produced; cleanup is the caller's responsibility.
     */
    val lore_storage_get_file: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get_file",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Write content to file (async variant).
     */
    val lore_storage_get_file_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get_file_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch fragment metadata for one or more `(partition, address)` pairs without paying the
     * payload bytes. Each item's terminal event carries the resolved `Fragment` (`flags`,
     * `size_payload`, `size_content`); on miss `error_code == ADDRESS_NOT_FOUND`.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_GET_METADATA_ITEM_COMPLETE` | `lore_storage_get_metadata_item_complete_event_data_t` | Per-item terminal event |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status` is `0` iff every item succeeded, else the error code |
     */
    val lore_storage_get_metadata: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get_metadata",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Fetch fragment metadata for one or more addresses (async variant).
     */
    val lore_storage_get_metadata_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_get_metadata_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Conditionally swap one or more mutable key values. Each item updates the key to `value` when
     * its current value matches `expected`, and reports the value the key held before the swap.
     * 
     * Each item acts on the local mutable store by default, or the remote mutable store when
     * `globals.remote` is set (or the handle was opened remote-bound), over the shared storage
     * session.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_MUTABLE_COMPARE_AND_SWAP_ITEM_COMPLETE` | `lore_storage_mutable_compare_and_swap_item_complete_event_data_t` | Per-item terminal event carrying `previous`; the swap took effect when `previous == expected` |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status: 0` iff every item succeeded |
     */
    val lore_storage_mutable_compare_and_swap: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_compare_and_swap",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Conditionally swap one or more mutable key values (async variant).
     */
    val lore_storage_mutable_compare_and_swap_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_compare_and_swap_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List the mutable key-value pairs of a given type for one or more partitions.
     * 
     * Acts on the local mutable store only; a remote-targeted call (`globals.remote`, or a
     * remote-bound handle) is rejected with `INVALID_ARGUMENTS`. A zero/default partition lists
     * every partition the caller can access.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_MUTABLE_LIST_ENTRY` | `lore_storage_mutable_list_entry_event_data_t` | One `(key, value)` pair, emitted before the item's terminal event |
     * | `LORE_EVENT_STORAGE_MUTABLE_LIST_ITEM_COMPLETE` | `lore_storage_mutable_list_item_complete_event_data_t` | Per-item terminal event |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status: 0` iff every item succeeded |
     */
    val lore_storage_mutable_list: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_list",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * List mutable key-value pairs (async variant).
     */
    val lore_storage_mutable_list_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_list_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read one or more mutable key values.
     * 
     * Each item acts on the local mutable store by default, or the remote mutable store when
     * `globals.remote` is set (or the handle was opened remote-bound), over the shared storage
     * session.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_MUTABLE_LOAD_ITEM_COMPLETE` | `lore_storage_mutable_load_item_complete_event_data_t` | Per-item terminal event carrying the value; `error_code == ADDRESS_NOT_FOUND` on a miss |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status: 0` iff every item succeeded |
     */
    val lore_storage_mutable_load: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_load",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read one or more mutable key values (async variant).
     */
    val lore_storage_mutable_load_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_load_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Write one or more mutable key-value pairs. Storing the null value removes the key.
     * 
     * Each item acts on the local mutable store by default, or the remote mutable store when
     * `globals.remote` is set (or the handle was opened remote-bound), over the shared storage
     * session.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_MUTABLE_STORE_ITEM_COMPLETE` | `lore_storage_mutable_store_item_complete_event_data_t` | Per-item terminal event |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status: 0` iff every item succeeded |
     */
    val lore_storage_mutable_store: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_store",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Write one or more mutable key-value pairs (async variant).
     */
    val lore_storage_mutable_store_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_mutable_store_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Delete one or more `(partition, address)` entries from the store.
     * 
     * Idempotent on absent items; emits one `OBLITERATE_ITEM_COMPLETE` event
     * per item carrying `local_success` / `remote_success` / `error_code`.
     */
    val lore_storage_obliterate: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_obliterate",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Delete content (async variant).
     */
    val lore_storage_obliterate_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_obliterate_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Open a content-addressed storage handle.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_OPENED` | `lore_storage_opened_event_data_t` | Emitted on success carrying the opened handle id |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status` is `0` on success or the error code on failure |
     */
    val lore_storage_open: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_open",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Open a content-addressed storage handle (async variant).
     */
    val lore_storage_open_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_open_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Store one or more content-addressed buffers.
     * 
     * # Events
     * 
     * | Tag | Data Type | Description |
     * |-----|-----------|-------------|
     * | `LORE_EVENT_STORAGE_PUT_ITEM_COMPLETE` | `lore_storage_put_item_complete_event_data_t` | Emitted once per input item — success or failure |
     * | `LORE_EVENT_ERROR` | `lore_error_event_data_t` | Emitted for a non-fatal error during the operation |
     * | `LORE_EVENT_COMPLETE` | `lore_complete_event_data_t` | `status` is `0` iff every item succeeded, else the error code |
     */
    val lore_storage_put: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_put",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Store one or more content-addressed buffers (async variant).
     */
    val lore_storage_put_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_put_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read one or more files into the content-addressed store.
     * 
     * Each item emits `LORE_EVENT_STORAGE_PUT_ITEM_COMPLETE` carrying the
     * computed address. Empty files short-circuit to the zero-hash address
     * without opening for read.
     */
    val lore_storage_put_file: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_put_file",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Read files into the store (async variant).
     */
    val lore_storage_put_file_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_put_file_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Push locally-stored, not-yet-durable content to the remote store.
     * 
     * Whole-call pre-dispatch fails when the handle has no remote, when `globals.offline=1`,
     * or when `globals.local=1`. Per-item: `partition == 0` → `INVALID_ARGUMENTS`; zero hash and
     * already-durable both succeed with `already_durable=1` and no remote call; missing local
     * payload → `ADDRESS_NOT_FOUND`. Otherwise the bytes are uploaded and the local entry is
     * updated with `PayloadStoredDurable` set.
     */
    val lore_storage_upload: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_upload",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Upload deferred content (async variant).
     */
    val lore_storage_upload_async: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_storage_upload_async",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, lore_event_callback_config_t.LAYOUT),
        )
    }

    /**
     * Return the path of the directory where the library keeps its per-user data
     * as a NUL-terminated string. The string is owned by the library and must not
     * be freed by the caller.
     */
    val lore_user_directory: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_user_directory",
            FunctionDescriptor.of(ValueLayout.ADDRESS),
        )
    }

    /**
     * Return the library version as a NUL-terminated string. The string is owned
     * by the library and must not be freed by the caller.
     */
    val lore_version: MethodHandle by lazy {
        LoreLinker.downcall(
            "lore_version",
            FunctionDescriptor.of(ValueLayout.ADDRESS),
        )
    }
}
