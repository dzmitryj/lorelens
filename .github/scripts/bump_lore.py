#!/usr/bin/env python3
"""Repin native/lore-versions.json and gradle.properties to a Lore release.

Downloads each liblore asset and the error.rs source so their checksums land in
the manifest as part of a reviewed diff, rather than being trusted at build time.
"""

import hashlib
import json
import pathlib
import subprocess
import sys
import urllib.request

REPO = "EpicGames/lore"
ERROR_SOURCE = "lore-base/src/error.rs"

PLATFORMS = {
    "win-x64": ("x86_64-pc-windows-msvc.zip", "lore.dll"),
    "linux-x64": ("x86_64-unknown-linux-gnu.tar.gz", "liblore.so"),
    "linux-arm64": ("aarch64-unknown-linux-gnu-neoverse-512tvb.tar.gz", "liblore.so"),
    "mac-arm64": ("aarch64-apple-darwin.tar.gz", "liblore.dylib"),
}

ROOT = pathlib.Path(__file__).resolve().parents[2]
MANIFEST = ROOT / "native" / "lore-versions.json"
PROPERTIES = ROOT / "gradle.properties"


def sha256_of(url: str) -> str:
    digest = hashlib.sha256()
    with urllib.request.urlopen(url) as response:
        for chunk in iter(lambda: response.read(1 << 16), b""):
            digest.update(chunk)
    return digest.hexdigest()


def asset_names(tag: str) -> set[str]:
    output = subprocess.run(
        ["gh", "release", "view", tag, "--repo", REPO, "--json", "assets", "--jq", ".assets[].name"],
        check=True,
        capture_output=True,
        text=True,
    ).stdout
    return set(output.split())


def interface_version(tag: str) -> str:
    return tag.lstrip("v")


def main(tag: str) -> int:
    available = asset_names(tag)
    manifest = json.loads(MANIFEST.read_text())

    assets = {}
    for platform, (suffix, library) in PLATFORMS.items():
        name = f"liblore-{tag}-{suffix}"
        if name not in available:
            print(f"Release {tag} has no asset named {name}", file=sys.stderr)
            return 1
        url = f"https://github.com/{REPO}/releases/download/{tag}/{name}"
        assets[platform] = {"file": name, "sha256": sha256_of(url), "library": library}

    manifest[tag] = {
        "interfaceVersion": interface_version(tag),
        "errorCodes": {
            "path": ERROR_SOURCE,
            "sha256": sha256_of(
                f"https://raw.githubusercontent.com/{REPO}/{tag}/{ERROR_SOURCE}"
            ),
        },
        "assets": assets,
    }

    MANIFEST.write_text(json.dumps(manifest, indent=2) + "\n")

    lines = PROPERTIES.read_text().splitlines()
    PROPERTIES.write_text(
        "\n".join(
            f"loreVersion={tag}" if line.startswith("loreVersion=") else line
            for line in lines
        )
        + "\n"
    )

    print(f"Pinned {tag}")
    return 0


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("usage: bump_lore.py <tag>", file=sys.stderr)
        raise SystemExit(2)
    raise SystemExit(main(sys.argv[1]))
