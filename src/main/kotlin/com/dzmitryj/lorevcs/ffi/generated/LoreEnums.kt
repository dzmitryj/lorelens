// Generated from lore.h 0.8.6 by :codegen. Do not edit.
package com.dzmitryj.lorevcs.ffi.generated

/**
 * Where a branch is located.
 */
object lore_branch_location_t {
    /**
     * A branch held locally.
     */
    const val LORE_BRANCH_LOCATION_LOCAL: Int = 0
    /**
     * A branch held on the server.
     */
    const val LORE_BRANCH_LOCATION_REMOTE: Int = 1
}

/**
 * Small discriminator enum for per-item terminal events in the
 * content-addressed storage API.
 * 
 * Narrower than the general library error code — events emitted per
 * put/get/copy/etc. item embed this code so a caller can branch on the
 * common cases cheaply without parsing the companion `LORE_EVENT_ERROR`
 * detail.
 * 
 * Numbered independently of the general library error code that a `Complete`
 * event's status carries: `NONE`, `INVALID_ARGUMENTS` and `ADDRESS_NOT_FOUND`
 * happen to share its values, `INTERNAL` (3 against -1) and `SLOW_DOWN`
 * (4 against 5) do not. Compare a code from an event only against this enum.
 * 
 */
object lore_error_code_t {
    /**
     * No error; the operation succeeded.
     */
    const val LORE_ERROR_CODE_NONE: Int = 0
    /**
     * The arguments supplied to the operation were invalid.
     */
    const val LORE_ERROR_CODE_INVALID_ARGUMENTS: Int = 1
    /**
     * A content-addressable object could not be found in any store.
     */
    const val LORE_ERROR_CODE_ADDRESS_NOT_FOUND: Int = 2
    /**
     * An internal error occurred.
     */
    const val LORE_ERROR_CODE_INTERNAL: Int = 3
    /**
     * The backing store is overloaded; the caller should retry later.
     */
    const val LORE_ERROR_CODE_SLOW_DOWN: Int = 4
}

/**
 * An event delivered to a callback. Each variant names a kind of event and
 * carries the data for that event.
 */
object lore_event_id_t {
    /**
     * A progress update.
     */
    const val LORE_EVENT_PROGRESS: Int = 0
    /**
     * An error encountered during an operation. A terminal failure is
     * reported on the `Complete` event in its `error` field.
     */
    const val LORE_EVENT_ERROR: Int = 1
    /**
     * An operation completed.
     */
    const val LORE_EVENT_COMPLETE: Int = 2
    /**
     * A metadata key and value.
     */
    const val LORE_EVENT_METADATA: Int = 3
    /**
     * A log message.
     */
    const val LORE_EVENT_LOG: Int = 4
    /**
     * The final event of a callback stream.
     */
    const val LORE_EVENT_END: Int = 5
    /**
     * A maintenance message.
     */
    const val LORE_EVENT_MAINTENANCE: Int = 6
    /**
     * An authentication URL for the user to visit.
     */
    const val LORE_EVENT_AUTH_URL: Int = 7
    /**
     * Information about the authenticated user.
     */
    const val LORE_EVENT_AUTH_USER_INFO: Int = 8
    /**
     * An authentication token for the user.
     */
    const val LORE_EVENT_AUTH_USER_TOKEN: Int = 9
    /**
     * The resolved identity of the user.
     */
    const val LORE_EVENT_AUTH_IDENTITY: Int = 10
    /**
     * A branch was created.
     */
    const val LORE_EVENT_BRANCH_CREATE: Int = 11
    /**
     * More than one instance of a branch was found.
     */
    const val LORE_EVENT_BRANCH_MULTIPLE_INSTANCE: Int = 12
    /**
     * A branch was archived.
     */
    const val LORE_EVENT_BRANCH_ARCHIVE: Int = 13
    /**
     * The start of a branch listing.
     */
    const val LORE_EVENT_BRANCH_LIST_BEGIN: Int = 14
    /**
     * One entry in a branch listing.
     */
    const val LORE_EVENT_BRANCH_LIST_ENTRY: Int = 15
    /**
     * The end of a branch listing.
     */
    const val LORE_EVENT_BRANCH_LIST_END: Int = 16
    /**
     * The start of a merge abort.
     */
    const val LORE_EVENT_BRANCH_MERGE_ABORT_BEGIN: Int = 17
    /**
     * The end of a merge abort.
     */
    const val LORE_EVENT_BRANCH_MERGE_ABORT_END: Int = 18
    /**
     * Information about a branch.
     */
    const val LORE_EVENT_BRANCH_INFO: Int = 19
    /**
     * The start of a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_BEGIN: Int = 20
    /**
     * The start of the changes in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CHANGE_BEGIN: Int = 21
    /**
     * One change in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CHANGE: Int = 22
    /**
     * The end of the changes in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CHANGE_END: Int = 23
    /**
     * The start of the conflicts in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CONFLICT_BEGIN: Int = 24
    /**
     * One conflict in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CONFLICT: Int = 25
    /**
     * The end of the conflicts in a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_CONFLICT_END: Int = 26
    /**
     * The end of a branch diff.
     */
    const val LORE_EVENT_BRANCH_DIFF_END: Int = 27
    /**
     * One entry in a listing of latest branch revisions.
     */
    const val LORE_EVENT_BRANCH_LATEST_LIST_ENTRY: Int = 28
    /**
     * A file in conflict during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_CONFLICT_FILE: Int = 29
    /**
     * A link was skipped during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_LINK_SKIPPED: Int = 30
    /**
     * A file conflict was marked unresolved during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_UNRESOLVE_FILE: Int = 31
    /**
     * A revision was marked unresolved during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_UNRESOLVE_REVISION: Int = 32
    /**
     * The start of merging changes into a file.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FILE_BEGIN: Int = 33
    /**
     * Merging changes into a file.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FILE: Int = 34
    /**
     * The end of merging changes into a file.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FILE_END: Int = 35
    /**
     * The start of merging a fragment.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_BEGIN: Int = 36
    /**
     * Progress while merging a fragment.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_PROGRESS: Int = 37
    /**
     * The end of merging a fragment.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_FRAGMENT_END: Int = 38
    /**
     * A revision merged into the target.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_REVISION: Int = 39
    /**
     * The start of synchronizing data for a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_SYNC_BEGIN: Int = 40
    /**
     * The end of synchronizing data for a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_INTO_SYNC_END: Int = 41
    /**
     * A file conflict was resolved during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_RESOLVE_FILE: Int = 42
    /**
     * A revision was resolved during a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_RESOLVE_REVISION: Int = 43
    /**
     * The start of a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_START_BEGIN: Int = 44
    /**
     * The end of starting a merge.
     */
    const val LORE_EVENT_BRANCH_MERGE_START_END: Int = 45
    /**
     * The start of a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_START_BEGIN: Int = 46
    /**
     * The end of starting a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_START_END: Int = 47
    /**
     * The start of a cherry-pick abort.
     */
    const val LORE_EVENT_CHERRY_PICK_ABORT_BEGIN: Int = 48
    /**
     * The end of a cherry-pick abort.
     */
    const val LORE_EVENT_CHERRY_PICK_ABORT_END: Int = 49
    /**
     * A file in conflict during a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_CONFLICT_FILE: Int = 50
    /**
     * A file conflict was marked unresolved during a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_UNRESOLVE_FILE: Int = 51
    /**
     * A revision was marked unresolved during a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_UNRESOLVE_REVISION: Int = 52
    /**
     * A file conflict was resolved during a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_RESOLVE_FILE: Int = 53
    /**
     * A revision was resolved during a cherry-pick.
     */
    const val LORE_EVENT_CHERRY_PICK_RESOLVE_REVISION: Int = 54
    /**
     * The start of a revert.
     */
    const val LORE_EVENT_REVERT_START_BEGIN: Int = 55
    /**
     * The end of starting a revert.
     */
    const val LORE_EVENT_REVERT_START_END: Int = 56
    /**
     * The start of a revert abort.
     */
    const val LORE_EVENT_REVERT_ABORT_BEGIN: Int = 57
    /**
     * The end of a revert abort.
     */
    const val LORE_EVENT_REVERT_ABORT_END: Int = 58
    /**
     * A file conflict was resolved during a revert.
     */
    const val LORE_EVENT_REVERT_RESOLVE_FILE: Int = 59
    /**
     * A revision was resolved during a revert.
     */
    const val LORE_EVENT_REVERT_RESOLVE_REVISION: Int = 60
    /**
     * A file in conflict during a revert.
     */
    const val LORE_EVENT_REVERT_CONFLICT_FILE: Int = 61
    /**
     * A file conflict was marked unresolved during a revert.
     */
    const val LORE_EVENT_REVERT_UNRESOLVE_FILE: Int = 62
    /**
     * A revision was marked unresolved during a revert.
     */
    const val LORE_EVENT_REVERT_UNRESOLVE_REVISION: Int = 63
    /**
     * A branch was protected.
     */
    const val LORE_EVENT_BRANCH_PROTECT: Int = 64
    /**
     * A branch was pushed.
     */
    const val LORE_EVENT_BRANCH_PUSH: Int = 65
    /**
     * The start of updating a revision during a push.
     */
    const val LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_BEGIN: Int = 66
    /**
     * The end of updating a revision during a push.
     */
    const val LORE_EVENT_BRANCH_PUSH_REVISION_UPDATE_END: Int = 67
    /**
     * The start of pushing a fragment.
     */
    const val LORE_EVENT_BRANCH_PUSH_FRAGMENT_BEGIN: Int = 68
    /**
     * Progress while pushing a fragment.
     */
    const val LORE_EVENT_BRANCH_PUSH_FRAGMENT_PROGRESS: Int = 69
    /**
     * The end of pushing a fragment.
     */
    const val LORE_EVENT_BRANCH_PUSH_FRAGMENT_END: Int = 70
    /**
     * The start of creating a branch during a push.
     */
    const val LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_BEGIN: Int = 71
    /**
     * The end of creating a branch during a push.
     */
    const val LORE_EVENT_BRANCH_PUSH_BRANCH_CREATE_END: Int = 72
    /**
     * The start of pushing a revision.
     */
    const val LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_BEGIN: Int = 73
    /**
     * An update while pushing a revision.
     */
    const val LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_UPDATE: Int = 74
    /**
     * The end of pushing a revision.
     */
    const val LORE_EVENT_BRANCH_PUSH_REVISION_PUSH_END: Int = 75
    /**
     * A branch was reset.
     */
    const val LORE_EVENT_BRANCH_RESET: Int = 76
    /**
     * The start of switching the active branch.
     */
    const val LORE_EVENT_BRANCH_SWITCH_BEGIN: Int = 77
    /**
     * The end of switching the active branch.
     */
    const val LORE_EVENT_BRANCH_SWITCH_END: Int = 78
    /**
     * A branch was unprotected.
     */
    const val LORE_EVENT_BRANCH_UNPROTECT: Int = 79
    /**
     * Information about a file.
     */
    const val LORE_EVENT_FILE_INFO: Int = 80
    /**
     * A diff for a file.
     */
    const val LORE_EVENT_FILE_DIFF: Int = 81
    /**
     * The hash of a file.
     */
    const val LORE_EVENT_FILE_HASH: Int = 82
    /**
     * The history of a file.
     */
    const val LORE_EVENT_FILE_HISTORY: Int = 83
    /**
     * A file was written.
     */
    const val LORE_EVENT_FILE_WRITE: Int = 84
    /**
     * A file was obliterated.
     */
    const val LORE_EVENT_FILE_OBLITERATE: Int = 85
    /**
     * A dump of a file.
     */
    const val LORE_EVENT_FILE_DUMP: Int = 86
    /**
     * The start of adding file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_ADD_BEGIN: Int = 87
    /**
     * One entry while adding file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_ADD_ENTRY: Int = 88
    /**
     * The end of adding file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_ADD_END: Int = 89
    /**
     * The start of removing file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_REMOVE_BEGIN: Int = 90
    /**
     * One entry while removing file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_REMOVE_ENTRY: Int = 91
    /**
     * The end of removing file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_REMOVE_END: Int = 92
    /**
     * The start of listing file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_LIST_BEGIN: Int = 93
    /**
     * A file in a dependency listing.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_LIST_FILE: Int = 94
    /**
     * One entry in a file dependency listing.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_LIST_ENTRY: Int = 95
    /**
     * The end of the entries for one file in a dependency listing.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_LIST_FILE_END: Int = 96
    /**
     * The end of listing file dependencies.
     */
    const val LORE_EVENT_FILE_DEPENDENCY_LIST_END: Int = 97
    /**
     * The start of a file reset.
     */
    const val LORE_EVENT_FILE_RESET_BEGIN: Int = 98
    /**
     * Progress during a file reset.
     */
    const val LORE_EVENT_FILE_RESET_PROGRESS: Int = 99
    /**
     * The end of a file reset.
     */
    const val LORE_EVENT_FILE_RESET_END: Int = 100
    /**
     * One file reset.
     */
    const val LORE_EVENT_FILE_RESET_FILE: Int = 101
    /**
     * A path was excluded by a filter.
     */
    const val LORE_EVENT_FILTER_EXCLUDE: Int = 102
    /**
     * The start of staging files.
     */
    const val LORE_EVENT_FILE_STAGE_BEGIN: Int = 103
    /**
     * Progress while staging files.
     */
    const val LORE_EVENT_FILE_STAGE_PROGRESS: Int = 104
    /**
     * The end of staging files.
     */
    const val LORE_EVENT_FILE_STAGE_END: Int = 105
    /**
     * The revision involved in staging files.
     */
    const val LORE_EVENT_FILE_STAGE_REVISION: Int = 106
    /**
     * One file staged.
     */
    const val LORE_EVENT_FILE_STAGE_FILE: Int = 107
    /**
     * The start of unstaging files.
     */
    const val LORE_EVENT_FILE_UNSTAGE_BEGIN: Int = 108
    /**
     * Progress while unstaging files.
     */
    const val LORE_EVENT_FILE_UNSTAGE_PROGRESS: Int = 109
    /**
     * The end of unstaging files.
     */
    const val LORE_EVENT_FILE_UNSTAGE_END: Int = 110
    /**
     * The revision involved in unstaging files.
     */
    const val LORE_EVENT_FILE_UNSTAGE_REVISION: Int = 111
    /**
     * One file unstaged.
     */
    const val LORE_EVENT_FILE_UNSTAGE_FILE: Int = 112
    /**
     * A fragment was written.
     */
    const val LORE_EVENT_FRAGMENT_WRITE: Int = 113
    /**
     * A layer was added.
     */
    const val LORE_EVENT_LAYER_ADD: Int = 114
    /**
     * One entry in a layer listing.
     */
    const val LORE_EVENT_LAYER_ENTRY: Int = 115
    /**
     * A layer was removed.
     */
    const val LORE_EVENT_LAYER_REMOVE: Int = 116
    /**
     * One staged entry in a layer listing.
     */
    const val LORE_EVENT_LAYER_STAGED_ENTRY: Int = 117
    /**
     * A link was changed.
     */
    const val LORE_EVENT_LINK_CHANGE: Int = 118
    /**
     * One entry in a link listing.
     */
    const val LORE_EVENT_LINK_ENTRY: Int = 119
    /**
     * The start of a file lock acquire report.
     */
    const val LORE_EVENT_LOCK_FILE_ACQUIRE_BEGIN: Int = 120
    /**
     * A file concerning the lock acquire report.
     */
    const val LORE_EVENT_LOCK_FILE_ACQUIRE: Int = 121
    /**
     * The start of a file lock status report.
     */
    const val LORE_EVENT_LOCK_FILE_STATUS_BEGIN: Int = 122
    /**
     * One file lock status entry.
     */
    const val LORE_EVENT_LOCK_FILE_STATUS: Int = 123
    /**
     * The start of a file lock query.
     */
    const val LORE_EVENT_LOCK_FILE_QUERY_BEGIN: Int = 124
    /**
     * One file lock query result.
     */
    const val LORE_EVENT_LOCK_FILE_QUERY: Int = 125
    /**
     * The start of a file lock release report.
     */
    const val LORE_EVENT_LOCK_FILE_RELEASE_BEGIN: Int = 126
    /**
     * A file concerning the lock release report.
     */
    const val LORE_EVENT_LOCK_FILE_RELEASE: Int = 127
    /**
     * Metadata was cleared on a file.
     */
    const val LORE_EVENT_METADATA_CLEAR_FILE: Int = 128
    /**
     * Metadata was cleared on a revision.
     */
    const val LORE_EVENT_METADATA_CLEAR_REVISION: Int = 129
    /**
     * A path was ignored.
     */
    const val LORE_EVENT_PATH_IGNORE: Int = 130
    /**
     * A repository was created.
     */
    const val LORE_EVENT_REPOSITORY_CREATE: Int = 131
    /**
     * The start of a repository clone.
     */
    const val LORE_EVENT_REPOSITORY_CLONE_BEGIN: Int = 132
    /**
     * Progress during a repository clone.
     */
    const val LORE_EVENT_REPOSITORY_CLONE_PROGRESS: Int = 133
    /**
     * The end of a repository clone.
     */
    const val LORE_EVENT_REPOSITORY_CLONE_END: Int = 134
    /**
     * The start of resolving dependencies.
     */
    const val LORE_EVENT_DEPENDENCY_RESOLVE_BEGIN: Int = 135
    /**
     * One item while resolving dependencies.
     */
    const val LORE_EVENT_DEPENDENCY_RESOLVE_ITEM: Int = 136
    /**
     * The end of resolving dependencies.
     */
    const val LORE_EVENT_DEPENDENCY_RESOLVE_END: Int = 137
    /**
     * Data about a repository.
     */
    const val LORE_EVENT_REPOSITORY_DATA: Int = 138
    /**
     * A repository configuration value.
     */
    const val LORE_EVENT_REPOSITORY_CONFIG_GET: Int = 139
    /**
     * The start of a repository dump.
     */
    const val LORE_EVENT_REPOSITORY_DUMP_BEGIN: Int = 140
    /**
     * The end of a repository dump.
     */
    const val LORE_EVENT_REPOSITORY_DUMP_END: Int = 141
    /**
     * One entry in a repository listing.
     */
    const val LORE_EVENT_REPOSITORY_LIST_ENTRY: Int = 142
    /**
     * An instance of a repository.
     */
    const val LORE_EVENT_REPOSITORY_INSTANCE: Int = 143
    /**
     * The start of verifying repository state.
     */
    const val LORE_EVENT_REPOSITORY_VERIFY_STATE_BEGIN: Int = 144
    /**
     * The end of verifying repository state.
     */
    const val LORE_EVENT_REPOSITORY_VERIFY_STATE_END: Int = 145
    /**
     * A fragment verified in a repository.
     */
    const val LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT: Int = 146
    /**
     * A fragment match found while verifying a repository.
     */
    const val LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT_MATCH: Int = 147
    /**
     * A remote fragment checked while verifying a repository.
     */
    const val LORE_EVENT_REPOSITORY_VERIFY_FRAGMENT_REMOTE: Int = 148
    /**
     * A dump of repository state.
     */
    const val LORE_EVENT_REPOSITORY_STATE_DUMP: Int = 149
    /**
     * One node in a repository state dump.
     */
    const val LORE_EVENT_REPOSITORY_STATE_DUMP_NODE: Int = 150
    /**
     * The revision involved in a repository status report.
     */
    const val LORE_EVENT_REPOSITORY_STATUS_REVISION: Int = 151
    /**
     * One file in a repository status report.
     */
    const val LORE_EVENT_REPOSITORY_STATUS_FILE: Int = 152
    /**
     * File counts in a repository status report.
     */
    const val LORE_EVENT_REPOSITORY_STATUS_COUNT: Int = 153
    /**
     * A summary of a repository status report.
     */
    const val LORE_EVENT_REPOSITORY_STATUS_SUMMARY: Int = 154
    /**
     * A result from querying the immutable store.
     */
    const val LORE_EVENT_REPOSITORY_STORE_IMMUTABLE_QUERY: Int = 155
    /**
     * The start of committing a revision.
     */
    const val LORE_EVENT_REVISION_COMMIT_BEGIN: Int = 156
    /**
     * Progress while committing a revision.
     */
    const val LORE_EVENT_REVISION_COMMIT_PROGRESS: Int = 157
    /**
     * The end of committing a revision.
     */
    const val LORE_EVENT_REVISION_COMMIT_END: Int = 158
    /**
     * The committed revision.
     */
    const val LORE_EVENT_REVISION_COMMIT_REVISION: Int = 159
    /**
     * Information about a revision.
     */
    const val LORE_EVENT_REVISION_INFO: Int = 160
    /**
     * A change in a revision's delta.
     */
    const val LORE_EVENT_REVISION_INFO_DELTA: Int = 161
    /**
     * One file in a revision diff.
     */
    const val LORE_EVENT_REVISION_DIFF_FILE: Int = 162
    /**
     * A revision found by a search.
     */
    const val LORE_EVENT_REVISION_FIND: Int = 163
    /**
     * The history of a revision.
     */
    const val LORE_EVENT_REVISION_HISTORY: Int = 164
    /**
     * One entry in a revision history.
     */
    const val LORE_EVENT_REVISION_HISTORY_ENTRY: Int = 165
    /**
     * The start of restoring a file from a revision.
     */
    const val LORE_EVENT_REVISION_RESTORE_FILE_BEGIN: Int = 166
    /**
     * A file restored from a revision.
     */
    const val LORE_EVENT_REVISION_RESTORE_FILE: Int = 167
    /**
     * The end of restoring a file from a revision.
     */
    const val LORE_EVENT_REVISION_RESTORE_FILE_END: Int = 168
    /**
     * The start of restoring a fragment.
     */
    const val LORE_EVENT_REVISION_RESTORE_FRAGMENT_BEGIN: Int = 169
    /**
     * Progress while restoring a fragment.
     */
    const val LORE_EVENT_REVISION_RESTORE_FRAGMENT_PROGRESS: Int = 170
    /**
     * The end of restoring a fragment.
     */
    const val LORE_EVENT_REVISION_RESTORE_FRAGMENT_END: Int = 171
    /**
     * The revision being restored.
     */
    const val LORE_EVENT_REVISION_RESTORE_REVISION: Int = 172
    /**
     * The start of synchronizing data for a restore.
     */
    const val LORE_EVENT_REVISION_RESTORE_SYNC_BEGIN: Int = 173
    /**
     * The end of synchronizing data for a restore.
     */
    const val LORE_EVENT_REVISION_RESTORE_SYNC_END: Int = 174
    /**
     * A revision was resolved.
     */
    const val LORE_EVENT_REVISION_RESOLVE: Int = 175
    /**
     * The target revision of a sync.
     */
    const val LORE_EVENT_REVISION_SYNC_TARGET: Int = 176
    /**
     * One file synced.
     */
    const val LORE_EVENT_REVISION_SYNC_FILE: Int = 177
    /**
     * Progress during a revision sync.
     */
    const val LORE_EVENT_REVISION_SYNC_PROGRESS: Int = 178
    /**
     * The revision involved in a sync.
     */
    const val LORE_EVENT_REVISION_SYNC_REVISION: Int = 179
    /**
     * A bisect result.
     */
    const val LORE_EVENT_REVISION_BISECT: Int = 180
    /**
     * A notification that a branch was created.
     */
    const val LORE_EVENT_NOTIFICATION_BRANCH_CREATED: Int = 181
    /**
     * A notification that a branch was deleted.
     */
    const val LORE_EVENT_NOTIFICATION_BRANCH_DELETED: Int = 182
    /**
     * A notification that a branch was pushed.
     */
    const val LORE_EVENT_NOTIFICATION_BRANCH_PUSHED: Int = 183
    /**
     * A notification that a resource was locked.
     */
    const val LORE_EVENT_NOTIFICATION_RESOURCE_LOCKED: Int = 184
    /**
     * A notification that a resource was unlocked.
     */
    const val LORE_EVENT_NOTIFICATION_RESOURCE_UNLOCKED: Int = 185
    /**
     * A notification that a subscription was created.
     */
    const val LORE_EVENT_NOTIFICATION_SUBSCRIBED: Int = 186
    /**
     * A notification that a subscription was removed.
     */
    const val LORE_EVENT_NOTIFICATION_UNSUBSCRIBED: Int = 187
    /**
     * A shared store was created.
     */
    const val LORE_EVENT_SHARED_STORE_CREATE: Int = 188
    /**
     * Information about a shared store.
     */
    const val LORE_EVENT_SHARED_STORE_INFO: Int = 189
    /**
     * One staged entry in a link listing.
     */
    const val LORE_EVENT_LINK_STAGED_ENTRY: Int = 190
    /**
     * A store was opened.
     */
    const val LORE_EVENT_STORAGE_OPENED: Int = 191
    /**
     * A put item completed.
     */
    const val LORE_EVENT_STORAGE_PUT_ITEM_COMPLETE: Int = 192
    /**
     * The header for a get item.
     */
    const val LORE_EVENT_STORAGE_GET_HEADER: Int = 193
    /**
     * A data payload for a get item.
     */
    const val LORE_EVENT_STORAGE_GET_DATA: Int = 194
    /**
     * A get item completed.
     */
    const val LORE_EVENT_STORAGE_GET_ITEM_COMPLETE: Int = 195
    /**
     * A get-metadata item completed.
     */
    const val LORE_EVENT_STORAGE_GET_METADATA_ITEM_COMPLETE: Int = 196
    /**
     * A copy item completed.
     */
    const val LORE_EVENT_STORAGE_COPY_ITEM_COMPLETE: Int = 197
    /**
     * An obliterate item completed.
     */
    const val LORE_EVENT_STORAGE_OBLITERATE_ITEM_COMPLETE: Int = 198
    /**
     * An upload item completed.
     */
    const val LORE_EVENT_STORAGE_UPLOAD_ITEM_COMPLETE: Int = 199
    /**
     * A revision tree was loaded.
     */
    const val LORE_EVENT_REVISION_TREE_LOADED: Int = 200
    /**
     * A resolve-path call completed.
     */
    const val LORE_EVENT_REVISION_TREE_RESOLVE_PATH_COMPLETE: Int = 201
    /**
     * One child node in a revision tree.
     */
    const val LORE_EVENT_REVISION_TREE_CHILD: Int = 202
    /**
     * Information about a revision tree node.
     */
    const val LORE_EVENT_REVISION_TREE_NODE_INFO: Int = 203
    /**
     * The path of a revision tree node.
     */
    const val LORE_EVENT_REVISION_TREE_NODE_PATH: Int = 204
    /**
     * An add call completed.
     */
    const val LORE_EVENT_REVISION_TREE_ADD_COMPLETE: Int = 205
    /**
     * A delete call completed.
     */
    const val LORE_EVENT_REVISION_TREE_DELETE_COMPLETE: Int = 206
    /**
     * A modify call completed.
     */
    const val LORE_EVENT_REVISION_TREE_MODIFY_COMPLETE: Int = 207
    /**
     * A move call completed.
     */
    const val LORE_EVENT_REVISION_TREE_MOVE_COMPLETE: Int = 208
    /**
     * A metadata-set call completed.
     */
    const val LORE_EVENT_REVISION_TREE_METADATA_SET_COMPLETE: Int = 209
    /**
     * A metadata-get call completed.
     */
    const val LORE_EVENT_REVISION_TREE_METADATA_GET_COMPLETE: Int = 210
    /**
     * A commit call completed.
     */
    const val LORE_EVENT_REVISION_TREE_COMMIT_COMPLETE: Int = 211
    /**
     * A close call completed.
     */
    const val LORE_EVENT_REVISION_TREE_CLOSE_COMPLETE: Int = 212
    /**
     * A list-children call began; carries the target repository and revision.
     */
    const val LORE_EVENT_REVISION_TREE_LIST_CHILDREN_BEGIN: Int = 213
    /**
     * Revision-record metadata for a loaded revision tree.
     */
    const val LORE_EVENT_REVISION_TREE_INFO: Int = 214
    /**
     * A mutable-load item completed.
     */
    const val LORE_EVENT_STORAGE_MUTABLE_LOAD_ITEM_COMPLETE: Int = 215
    /**
     * A mutable-store item completed.
     */
    const val LORE_EVENT_STORAGE_MUTABLE_STORE_ITEM_COMPLETE: Int = 216
    /**
     * A mutable-compare-and-swap item completed.
     */
    const val LORE_EVENT_STORAGE_MUTABLE_COMPARE_AND_SWAP_ITEM_COMPLETE: Int = 217
    /**
     * One key-value entry in a mutable listing.
     */
    const val LORE_EVENT_STORAGE_MUTABLE_LIST_ENTRY: Int = 218
    /**
     * A mutable-list item completed.
     */
    const val LORE_EVENT_STORAGE_MUTABLE_LIST_ITEM_COMPLETE: Int = 219
    /**
     * A store eviction pass began.
     */
    const val LORE_EVENT_EVICTION_BEGIN: Int = 220
    /**
     * One bucket was evicted during a store eviction pass.
     */
    const val LORE_EVENT_EVICTION_PROGRESS: Int = 221
    /**
     * A store eviction pass ended.
     */
    const val LORE_EVENT_EVICTION_END: Int = 222
    /**
     * A store compaction pass began.
     */
    const val LORE_EVENT_COMPACTION_BEGIN: Int = 223
    /**
     * One group was compacted during a store compaction pass.
     */
    const val LORE_EVENT_COMPACTION_PROGRESS: Int = 224
    /**
     * A store compaction pass ended.
     */
    const val LORE_EVENT_COMPACTION_END: Int = 225
    /**
     * A batch write call on a revision tree completed as a whole.
     */
    const val LORE_EVENT_REVISION_TREE_BATCH_COMPLETE: Int = 226
}

/**
 * The change applied to a file.
 */
object lore_file_action_t {
    /**
     * The file is unchanged.
     */
    const val LORE_FILE_ACTION_KEEP: Int = 0
    /**
     * The file was added.
     */
    const val LORE_FILE_ACTION_ADD: Int = 1
    /**
     * The file was deleted.
     */
    const val LORE_FILE_ACTION_DELETE: Int = 2
    /**
     * The file was moved to a new path.
     */
    const val LORE_FILE_ACTION_MOVE: Int = 3
    /**
     * The file was copied from another path.
     */
    const val LORE_FILE_ACTION_COPY: Int = 4
}

/**
 * Kind of value a stored key refers to.
 */
object lore_key_type_t {
    /**
     * Key has no specific type.
     */
    const val LORE_KEY_TYPE_UNTYPED: Int = 0
    /**
     * Key refers to branch metadata.
     */
    const val LORE_KEY_TYPE_BRANCH_METADATA: Int = 1
    /**
     * Key refers to a branch identifier.
     */
    const val LORE_KEY_TYPE_BRANCH_ID: Int = 2
    /**
     * Key refers to a pointer to a branch's latest revision.
     */
    const val LORE_KEY_TYPE_BRANCH_LATEST_POINTER: Int = 3
    /**
     * Key refers to repository metadata.
     */
    const val LORE_KEY_TYPE_REPOSITORY_METADATA: Int = 4
    /**
     * Key refers to a repository identifier.
     */
    const val LORE_KEY_TYPE_REPOSITORY_ID: Int = 5
    /**
     * Key refers to a repository instance.
     */
    const val LORE_KEY_TYPE_INSTANCE: Int = 6
}

/**
 * Severity level of a log message.
 */
object lore_log_level_t {
    /**
     * No logging.
     */
    const val LORE_LOG_LEVEL_NONE: Int = 0
    /**
     * Most detailed tracing messages.
     */
    const val LORE_LOG_LEVEL_TRACE: Int = 1
    /**
     * Debugging messages.
     */
    const val LORE_LOG_LEVEL_DEBUG: Int = 2
    /**
     * Informational messages.
     */
    const val LORE_LOG_LEVEL_INFO: Int = 3
    /**
     * Warnings about unexpected but recoverable situations.
     */
    const val LORE_LOG_LEVEL_WARN: Int = 4
    /**
     * Errors.
     */
    const val LORE_LOG_LEVEL_ERROR: Int = 5
}

/**
 * A metadata value, tagged by the kind of value it holds.
 */
object lore_metadata_tag_t {
    /**
     * An address value.
     */
    const val LORE_METADATA_ADDRESS: Int = 0
    /**
     * A boolean value, stored as a byte.
     */
    const val LORE_METADATA_BOOLEAN: Int = 1
    /**
     * A block of raw bytes.
     */
    const val LORE_METADATA_BINARY: Int = 2
    /**
     * A context value.
     */
    const val LORE_METADATA_CONTEXT: Int = 3
    /**
     * A hash value.
     */
    const val LORE_METADATA_HASH: Int = 4
    /**
     * An unsigned integer value.
     */
    const val LORE_METADATA_NUMERIC: Int = 5
    /**
     * A string value.
     */
    const val LORE_METADATA_STRING: Int = 6
}

/**
 * The kind of value held by a metadata entry.
 */
object lore_metadata_type_t {
    /**
     * A block of raw bytes.
     */
    const val LORE_METADATA_TYPE_BINARY: Int = 0
    /**
     * An unsigned integer value.
     */
    const val LORE_METADATA_TYPE_NUMERIC: Int = 1
    /**
     * A string value.
     */
    const val LORE_METADATA_TYPE_STRING: Int = 2
}

/**
 * The kind of a tracked node.
 */
object lore_node_type_t {
    /**
     * A directory.
     */
    const val LORE_NODE_TYPE_DIRECTORY: Int = 0
    /**
     * A file.
     */
    const val LORE_NODE_TYPE_FILE: Int = 1
    /**
     * A symbolic link.
     */
    const val LORE_NODE_TYPE_LINK: Int = 2
}

