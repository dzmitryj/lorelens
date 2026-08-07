// Generated from lore.h 0.8.6 by :codegen. Do not edit.
package com.dzmitryj.lorelens.ffi.generated

/**
 * Status codes carried by a Complete event and returned by operation
 * functions. Distinct from [lore_error_code_t], which is the storage-layer
 * enum in lore.h and is numbered independently.
 */
enum class LoreStatus(val code: Int, val message: String) {
    SUCCESS(0, "success"),
    INVALID_ARGUMENTS(1, "invalid arguments: {reason}"),
    ADDRESS_NOT_FOUND(2, "Address not found: {}"),
    FILE_NOT_FOUND(3, "file not found: {resource}"),
    PAYLOAD_NOT_FOUND(4, "Payload not found: {}"),
    SLOW_DOWN(5, "Store overloaded, slow down"),
    DISCONNECTED(6, "Disconnected from server"),
    NOT_AUTHORIZED(7, "Not authorized to access repository"),
    LOCK_NOT_FOUND(8, "lock does not exist"),
    LOCK_NOT_OWNED(9, "resource locked by somebody else"),
    SHARED_STORE_NOT_FOUND(10, "A shared store was supposed to exist at {path}"),
    MAINTENANCE(11, "Server is in maintenance mode"),
    NOT_AUTHENTICATED(12, "Not authenticated"),
    NOT_FOUND(13, "Not found"),
    NO_REMOTE(14, "No remote configured"),
    NODE_NOT_FOUND(15, "Node not found"),
    LINK_NOT_FOUND(16, "Link not found"),
    NOT_CONNECTED(17, "Not connected to remote: {reason}"),
    NOT_SUPPORTED(18, "Operation not supported: {operation}"),
    ALREADY_LINKED(19, "Target repository is already used in a layer"),
    LAYER_NOT_FOUND(20, "Layer not found"),
    NOTHING_STAGED(21, "Nothing staged for commit"),
    BRANCH_ADVANCED(22, "Branch has been advanced by another instance, sync and re-stage to commit"),
    CONFLICT(23, "Unable to commit when {path} is still in conflict"),
    LINK_PATH_NOT_FOUND(24, "Link not found at path: {path}"),
    NOT_ALINK(25, "Path is not a link: {path}"),
    OVERSIZED(26, "Oversized: {context}"),
    PLUGIN_NOT_FOUND(27, ""),
    PLUGIN_CONFIG_ERROR(28, "Plugin '{plugin_name}' configuration error: {message}"),
    PLUGIN_INIT_ERROR(29, "Plugin '{plugin_name}' initialization failed: {message}"),
    WRITE_REQUIRED(30, "Operation requires write access"),
    INVALID_PATH(31, "invalid path: {path}"),
    INVALID_ADDRESS(32, "invalid address: {address}"),
    REVISION_NOT_FOUND(33, "revision not found: {revision}"),
    BRANCH_NOT_FOUND(34, "branch not found: {branch}"),
    IDENTICAL_METADATA(35, "New metadata was identical to original"),
    TOKEN_NOT_FOUND(36, "No token stored"),
    NOT_ALAYER(37, "Path is not a layer: {path}"),
    INVALID_NODE_HIERARCHY(38, "Node {node} has parent {actual_parent} but was reached as a child of {expected_parent}"),
    LOCAL_MODIFICATIONS(39, "Local modifications prevent synchronization"),
    BRANCH_ALREADY_EXISTS(40, "Branch {branch} already exists, use switch instead"),
    REPOSITORY_ALREADY_EXISTS(41, "Repository already exist in path {path}"),
    DELETE_PROTECTED(42, "Unable to delete a protected branch: {branch}"),
    DELETE_CURRENT(43, "Cannot delete the current branch: {branch}"),
    DELETE_DEFAULT(44, "Unable to delete default branch: {branch}"),
    REPOSITORY_NOT_FOUND(45, "Repository not found: {repository}"),
    DIVERGENT(46, "Branch history is divergent"),
    MAX_HISTORY_SEARCH_DEPTH(47, "Branch history has reached maximum search depth"),
    MISSING_IDENTITY(48, "No commit identity configured; pass --identity or set identity in .lore/config.toml"),
    ;

    companion object {
        private val byCode = entries.associateBy { it.code }

        fun of(code: Int): LoreStatus? = byCode[code]
    }
}
