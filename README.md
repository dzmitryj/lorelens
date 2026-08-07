# Lore Version Control

Version control integration for [Lore](https://lore.org), Epic Games' open-source version control system for
repositories that mix source code with large binary assets.

> Not affiliated with or endorsed by Epic Games. Bundles the Lore shared library, which is MIT licensed.

## Why this exists

Lore's `status` deliberately performs no filesystem walk. It reports the files it has been *told* changed, and
reconciling the whole tree is an explicit, expensive operation — on an asset repository, the difference between
instant and minutes.

An IDE knows precisely which files it touched, the moment it touches them. This plugin feeds that straight into
Lore, so the Changes view stays accurate without ever scanning the repository. No other client is positioned to
do that as well.

## Features

- **Changes view** driven by Lore's own dirty set, with gutter markers and diff against the current revision
- **Automatic dirty marking** as you edit, with an explicit Full Rescan when you want reconciliation
- **Commit, revert, and push**, with push-after-commit on by default — in a centralized VCS an unpushed commit
  is a half-finished action
- **Rename tracking**, using Lore's first-class move support rather than reporting an add plus a delete
- **File locking**: editing a read-only file acquires its lock, files held by others are read-only with a banner
  naming the holder, and the status bar shows branch, revision, and locks held
- **Clone and sync**, including a client-side view filter at clone time
- **`.loreignore`** with syntax highlighting, comment toggling and the platform's ignore inspections
- **Lore Log** tab listing revisions and commit messages

## Requirements

- IntelliJ Platform 2026.1 or later. The plugin binds Lore's C API through `java.lang.foreign`, which is a
  preview API before JBR 25.
- A Lore server. Lore is centralized: cloning an existing repository needs a URL, and creating one needs a
  server to create it on. You can run `loreserver` yourself — it starts from built-in defaults with no
  configuration.

The Lore shared library is bundled for Windows x64, Linux x64, Linux arm64 and macOS arm64. Epic publishes no
macOS x64 build, so Intel Macs are not supported.

## Development

```bash
./gradlew buildPlugin
```

`liblore` and `lore.h` are downloaded from the pinned release tag, verified against the checksums in
`native/lore-versions.json`, and bundled at package time. Binaries are never committed.

The FFM bindings in `src/main/kotlin/com/dzmitryj/lorevcs/ffi/generated` are generated from `lore.h` and
checked in, so their diffs are reviewable when Lore changes:

```bash
./gradlew :codegen:generateLoreBindings
```

Generated struct layouts are inferred from field types, which is not an error if it is wrong — it is a silently
wrong read. A generated C probe checks `sizeof`, `_Alignof` and `offsetof` for every struct against the real
compiler, and CI runs it on all three platforms.

`.github/workflows/upstream.yml` watches for new Lore releases and opens a pull request carrying the ABI diff.

### Verifying against the oldest supported IDE

The plugin compiles against 2026.2 but declares `sinceBuild = 261`, so check an installed IDE at the low end of
that range before releasing:

```bash
./gradlew verifyPlugin -PverifyAgainstIde="C:\Users\you\AppData\Local\Programs\Rider"
```

### Publishing

The first version must be uploaded manually through the Marketplace web UI and passes human moderation;
`publishPlugin` only works for later versions of an already-approved plugin. After that, `release.yml` signs and
publishes on a GitHub release, using the `PUBLISH_TOKEN`, `CERTIFICATE_CHAIN`, `PRIVATE_KEY` and
`PRIVATE_KEY_PASSWORD` secrets.

Verify signing locally before trusting CI — the private key must be PKCS#8, and a PKCS#1 key fails with an
unhelpful error:

```bash
./gradlew signPlugin
```

### Tests

```bash
./gradlew check
```

Integration tests start a real `loreserver` on loopback and drive real repositories, so they need no external
server. They skip automatically on platforms Lore does not publish a server for.
