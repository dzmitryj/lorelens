<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# LoreLens Changelog

## [Unreleased]

### Added

- Version control integration for Lore, binding the bundled `liblore` C API in-process through
  `java.lang.foreign`. Pinned to Lore v0.8.6.
- Changes view driven by Lore's dirty set, with gutter markers and diff against the current revision.
- Automatic dirty marking as files are edited, plus an explicit Full Rescan and a scan on project open.
- Commit and push, with push-after-commit on by default, and Revert.
- Rename tracking through Lore's move support, so renames are not reported as an add plus a delete.
- File locking: editing a read-only file acquires its lock, files held by others are read-only with a banner
  naming the holder, and a status bar widget shows branch, revision and locks held.
- Clone and sync, including a client-side view filter at clone time, and a Create Lore Repository action.
- `.loreignore` file type with the platform's ignore highlighting and inspections.
- LoreLens tab listing revisions with author, date and message, plus the changed files of the selected revision.
- File history in the IDE's Show History UI, following renames exactly.
- Blame on the caret line as an end-of-line hint, reconstructed from a file's own history since Lore exposes no
  blame verb. Cost is bounded by how often that file changed, and results cache on the head revision.

### Changed

- The registered VCS name is `LoreLens`. A mapping left by the earlier name is repointed automatically on
  project open.

### Notes

- Requires IntelliJ Platform 2026.1 or later, where `java.lang.foreign` is no longer a preview API.
- Bundles `liblore` for Windows x64, Linux x64, Linux arm64 and macOS arm64. Epic publishes no macOS x64
  build, so Intel Macs are unsupported.
- Not affiliated with or endorsed by Epic Games.
