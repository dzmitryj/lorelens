# LoreLens Changelog

All notable changes to [LoreLens](https://github.com/dzmitryj/lorelens) are documented here.
Issues and source live at [github.com/dzmitryj/lorelens](https://github.com/dzmitryj/lorelens).

## [Unreleased]

### Added

- Version control integration for Lore, binding the bundled `liblore` C API in-process through
  `java.lang.foreign`. Pinned to Lore v0.8.6.
- Changes view driven by Lore's dirty set, with gutter markers and diff against the current revision.
- Automatic dirty marking as files are edited, plus an explicit Full Rescan and a scan on project open.
- Commit and push, with push-after-commit on by default, and Revert.
- Merge with a preview first, in either direction, and revert of a revision. Conflicts resolve as mine
  or theirs, can be unresolved, re-materialized, or the whole merge aborted.
- History tab showing the current branch's ancestry in one graph: one lane and colour per branch, merge
  direction drawn, unsynced revisions marked, and a ring on the checkout revision.
- Branch Graph tab laying the repository out as swimlanes, with switch and merge in either direction
  from a right-click, drag to pan, and wheel to zoom.
- Rename tracking through Lore's move support, so renames are not reported as an add plus a delete.
- File locking: editing a read-only file acquires its lock, files held by others are read-only with a
  banner naming the holder, and a status bar widget shows branch, revision and locks held.
- Clone and sync, including a client-side view filter at clone time, and a Create Lore Repository action.
- `.loreignore` file type with the platform's ignore highlighting and inspections.
- File history in the IDE's Show History UI, following renames exactly. Historical content is read by
  content address, so diffs stay correct across moves and deletes.
- Blame on the caret line as an end-of-line hint, reconstructed from a file's own history since Lore
  exposes no blame verb. Cost is bounded by how often that file changed, and results cache on the head
  revision.
- A Lore console recording every operation, with optional native debug logging.

### Changed

- The registered VCS name is `LoreLens`. A mapping left by the earlier name is repointed automatically
  on project open.
- The plugin icon is the Lore mark, used for identification.

### Notes

- Version `0.8.6.1`: Lore v0.8.6, plugin revision 1.
- Requires IntelliJ Platform 2026.1 or later, where `java.lang.foreign` is no longer a preview API.
- Bundles `liblore` for Windows x64, Linux x64, Linux arm64 and macOS arm64. Epic publishes no macOS
  x64 build, so Intel Macs are unsupported.
- Not affiliated with or endorsed by Epic Games. Lore is a trademark of Epic Games, Inc.
