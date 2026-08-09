# Regenerating the README screenshots

The screenshots in `docs/readme` come from a real sandbox IDE over a seeded demo
repository, captured headlessly: the harness paints the Swing frame to a PNG, so
no window focus, mouse, or screen capture is involved.

Two source files live here so they never ship in the plugin:

- `DemoSeeder.kt` — seeds the `aurora` demo repository (a game-dev streams
  history: mainline, dev/art streams, a 1.0/1.1/2.0 release train, ~100 commits)
  against a loopback `loreserver` and keeps the server alive until killed.
  Commits carry per-author identities through `lore_global_args_t.identity`;
  merges resolve conflicts in favour of the incoming branch so the run never
  stalls.
- `ShotHarness.kt` — a `ProjectActivity` that opens the History and Branch Graph
  tabs, an editor with blame, and the Local Changes view, renders the IDE frame
  at 2x, and writes `branch-graph.png`, `history.png`, `blame.png`,
  `local-changes.png`. It widens the frame for the Branch Graph so the whole
  ~100-column graph fits on screen.

Lock owners: the loopback server has no auth, so a lock's owner comes back as the
sentinel `<unknown>` and the lock banner renders it blank. A real Lore server
names the holder. The Local Changes shot therefore leans on the staged asset
edits rather than the banner.

## Steps

1. Copy `DemoSeeder.kt` to `src/test/kotlin/com/dzmitryj/lorelens/` and
   `ShotHarness.kt` to `src/main/kotlin/com/dzmitryj/lorelens/demo/`, and
   register the harness in `plugin.xml`:

   ```xml
   <postStartupActivity implementation="com.dzmitryj.lorelens.demo.ShotHarness"/>
   ```

2. Add to `build.gradle.kts`, with your own paths:

   ```kotlin
   tasks.register("demoClasspath") {
       dependsOn("testClasses", fetchLoreNative)
       doLast {
           layout.buildDirectory.file("demo-classpath.txt").get().asFile
               .writeText(tasks.test.get().classpath.asPath)
       }
   }

   tasks.runIde {
       args = listOf("<demo repo dir>")
       jvmArgs("-Dlorelens.shots.dir=<output dir>")
   }
   ```

3. Seed and keep the server running (`./gradlew demoClasspath
   --no-configuration-cache` first, then run `DemoSeeder` with the classpath
   from `build/demo-classpath.txt` and `-Dlore.native.dir`, `-Dlore.server.dir`,
   `-Dlore.demo.dir` set; see the task wiring in `tasks.test` for the values).

4. `./gradlew runIde --no-configuration-cache` and wait for the four PNGs.
   The IDE window can sit behind other windows the whole time.

5. Crop as desired, drop the results in `docs/readme`, and revert step 1-2.
