package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreClient
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreSyncApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.generated.BranchMergeConflictFileEvent
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_start_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_global_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_lock_file_acquire_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_commit_args_t
import java.lang.foreign.Arena
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

/**
 * Seeds the demo repository the README screenshots are taken from, then keeps
 * the backing loreserver alive until killed. Not part of the test suite.
 *
 * A game-dev / Perforce-streams history: a mainline, two team integration
 * streams with task branches cut off them, an art stream for binary assets, and
 * a release train (1.0 stabilised and hotfixed, 1.1 shipped, 2.0 in progress).
 * Around a hundred revisions, six contributors, merges in both directions.
 *
 * Lane stacking in both graph views is the branch-point hierarchy (each branch
 * sorts under the branch it was cut from), so the cut points below are chosen
 * for how they read top to bottom. Column order is the order commits are made
 * here, so this runs strictly oldest to newest.
 *
 * Dates are not set: Lore stamps each revision's creation time from the wall
 * clock at commit, and the History view shows that, not any editable metadata.
 * The visible dates are therefore the seeding run. Backdating them would mean
 * moving the system clock per commit, which is not worth it for a demo.
 */
object DemoSeeder {

    private lateinit var root: Path

    // Contributors. The badge in the graph is the first letter of the identity,
    // so these stay distinct: D M O K J E P.
    private const val DIMA = "dima.orlov"     // lead / engine
    private const val MARCO = "marco.reyes"   // rendering
    private const val OLEG = "oleg.novak"     // environment + character art
    private const val KATYA = "katya.mills"   // gameplay
    private const val JAN = "jan.weber"       // tools / build
    private const val ELENA = "elena.costa"   // QA / release
    private const val PRIYA = "priya.rao"     // audio

    private fun commitAs(message: String, identity: String) {
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            args.writeString(lore_global_args_t.identity(globals), identity)
            val options = arena.allocate(lore_revision_commit_args_t.LAYOUT)
            args.writeString(lore_revision_commit_args_t.message(options), message)

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_revision_commit.invokeExact(globals, options, callback) as Int
                },
                "commit $message",
            )
        }
    }

    /** Starts the merge and returns the paths that came back conflicted. */
    private fun mergeAs(source: String, message: String, identity: String): List<String> =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            args.writeString(lore_global_args_t.identity(globals), identity)
            val options = arena.allocate(lore_branch_merge_start_args_t.LAYOUT)
            args.writeString(lore_branch_merge_start_args_t.branch(options), source)
            args.writeString(lore_branch_merge_start_args_t.message(options), message)

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_branch_merge_start.invokeExact(globals, options, callback) as Int
                },
                "merge $source",
            ).filter<BranchMergeConflictFileEvent>().map { it.path }
        }

    /** Acquires a lock as [identity] -- used to leave a file held by someone else. */
    private fun lockAs(path: String, identity: String) {
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            args.writeString(lore_global_args_t.identity(globals), identity)
            val options = arena.allocate(lore_lock_file_acquire_args_t.LAYOUT)
            args.writeStrings(lore_lock_file_acquire_args_t.paths(options), listOf(root.resolve(path).toString()))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_lock_file_acquire.invokeExact(globals, options, callback) as Int
                },
                "lock $path as $identity",
            )
        }
    }

    private fun write(path: String, text: String) {
        val file = root.resolve(path)
        file.parent?.createDirectories()
        file.writeText(text)
    }

    /** A small C++ translation unit, so successive versions produce real diffs. */
    private fun src(path: String, vararg body: String) =
        write(path, "// $path\n#include \"aurora.h\"\n\nnamespace aurora {\n\n" + body.joinToString("\n") + "\n\n} // namespace aurora\n")

    private fun push() = LoreClient.require(LoreWriteApi.push(root), "push")

    private fun commit(message: String, author: String, vararg paths: String, pushIt: Boolean = true) {
        LoreWriteApi.stage(root, paths.toList())
        commitAs(message, author)
        if (pushIt) push()
    }

    private fun branch(name: String) {
        LoreClient.require(LoreBranchApi.create(root, name), "create $name")
        LoreBranchApi.switch(root, name)
    }

    private fun switchTo(name: String) = LoreBranchApi.switch(root, name)

    /**
     * Merges [source] into the currently checked-out branch. When the two sides
     * touched the same file the merge stops with conflicts; the demo resolves
     * them in favour of the incoming branch and commits to finish, the way a
     * real integration would (the file contents do not matter to the graph).
     */
    private fun merge(source: String, message: String, author: String) {
        val conflicts = mergeAs(source, message, author)
        if (conflicts.isNotEmpty()) {
            LoreClient.require(
                LoreBranchApi.resolveTheirs(root, conflicts.map { root.resolve(it).toString() }),
                "resolve merge of $source",
            )
            commitAs(message, author)
        }
        push()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        root = Path.of(requireNotNull(System.getProperty("lore.demo.dir")) { "-Dlore.demo.dir required" })
        require(LoreTestServer.isAvailable()) { "loreserver missing" }

        val server = LoreTestServer.startNew()
        Files.createDirectories(root)
        LoreClient.createRepository(root, "${server.url}/aurora-${System.nanoTime()}")

        // ---- Pre-production on the mainline -------------------------------
        write("README.md", "# Aurora\n\nOpen-world action RPG. Code and cooked assets versioned with Lore.\n")
        write(".loreignore", "Build/\nIntermediate/\nDerivedDataCache/\nSaved/\n.idea/\n*.iml\n*.tmp\n")
        write("Config/engine.ini", "[Renderer]\nvsync=true\nexposure=1.0\n")
        src("Source/main.cpp", "int main() {", "    Engine engine;", "    return engine.run();", "}")
        src("Source/Engine/Renderer.cpp", "void Renderer::run() {", "    while (pump()) { drawFrame(); }", "}")
        commit("engine bootstrap and render loop", DIMA,
            "README.md", ".loreignore", "Config/engine.ini", "Source/main.cpp", "Source/Engine/Renderer.cpp")

        src("Source/Engine/Renderer.cpp", "void Renderer::run() {", "    while (pump()) { beginFrame(); drawFrame(); present(); }", "}")
        commit("renderer: split begin, draw and present", DIMA, "Source/Engine/Renderer.cpp")

        write("Config/build.ini", "[Build]\ntoolchain=clang-19\nunity=true\n")
        commit("build: toolchain preset and unity build", JAN, "Config/build.ini")

        src("Source/Engine/Device.cpp", "bool Device::init() {", "    return createSwapchain() && createQueues();", "}")
        commit("renderer: device and swapchain init", MARCO, "Source/Engine/Device.cpp")

        src("Source/Game/Entity.cpp", "EntityId World::spawn(Archetype a) {", "    return pool.create(a);", "}")
        commit("gameplay: entity/component scaffold", KATYA, "Source/Game/Entity.cpp")

        // ---- Team streams open --------------------------------------------
        branch("dev/rendering")
        src("Source/Engine/Culling.cpp", "void Culling::forwardPlus(View v) {", "    tileLights(v);", "}")
        commit("rendering: forward+ light culling", MARCO, "Source/Engine/Culling.cpp")
        src("Source/Engine/Tonemap.cpp", "float3 Tonemap::aces(float3 hdr) {", "    return acesFit(hdr);", "}")
        commit("rendering: ACES tonemap pass", MARCO, "Source/Engine/Tonemap.cpp")

        branch("feature/water-shader")
        write("Assets/Shaders/Water.hlsl", "float4 WaterPS(float2 uv) : SV_Target {\n    return float4(0.1, 0.3, 0.5, 1);\n}\n")
        commit("water: flat albedo pass", MARCO, "Assets/Shaders/Water.hlsl")
        write("Assets/Shaders/Water.hlsl", "float4 WaterPS(float2 uv) : SV_Target {\n    float f = fresnel(uv);\n    return float4(0.1, 0.3, 0.5, 1) * f;\n}\n")
        commit("water: fresnel term", MARCO, "Assets/Shaders/Water.hlsl")
        write("Assets/Shaders/Water.hlsl", "float4 WaterPS(float2 uv) : SV_Target {\n    float f = fresnel(uv);\n    float d = depthFade(uv);\n    return float4(0.1, 0.3, 0.5, 1) * f * d;\n}\n")
        commit("water: depth fade", MARCO, "Assets/Shaders/Water.hlsl")

        switchTo("dev/rendering")
        merge("feature/water-shader", "integrate water shader", MARCO)
        src("Source/Engine/Renderer.cpp", "void Renderer::run() {", "    while (pump()) { beginFrame(); drawWater(); drawFrame(); present(); }", "}")
        commit("rendering: hook water into the forward pass", MARCO, "Source/Engine/Renderer.cpp")

        switchTo("main")
        branch("dev/gameplay")
        src("Source/Game/Inventory.cpp", "bool Inventory::add(Item i) {", "    return slots.tryPush(i);", "}")
        commit("gameplay: inventory model", KATYA, "Source/Game/Inventory.cpp")
        src("Source/Game/Hotbar.cpp", "void Hotbar::select(int s) {", "    active = clamp(s, 0, 9);", "}")
        commit("gameplay: ten-slot hotbar", KATYA, "Source/Game/Hotbar.cpp")

        branch("feature/quest-editor")
        src("Source/Game/Quest.cpp", "NodeId QuestGraph::add(Node n) {", "    return nodes.push(n);", "}")
        commit("quests: graph data model", KATYA, "Source/Game/Quest.cpp")
        src("Source/Tools/QuestEditor.cpp", "void QuestEditor::paint(Canvas& c) {", "    for (auto& n : graph.nodes()) draw(c, n);", "}")
        commit("tools: quest node editor panel", KATYA, "Source/Tools/QuestEditor.cpp")
        src("Source/Tools/QuestEditor.cpp", "void QuestEditor::paint(Canvas& c) {", "    grid(c);", "    for (auto& n : graph.nodes()) draw(c, n);", "}")
        commit("tools: dockable editor window and grid", JAN, "Source/Tools/QuestEditor.cpp")

        switchTo("dev/gameplay")
        merge("feature/quest-editor", "integrate quest editor", KATYA)
        src("Source/Game/Dialogue.cpp", "void Dialogue::run(NodeId n) {", "    play(n); advance();", "}")
        commit("gameplay: wire quests to dialogue", KATYA, "Source/Game/Dialogue.cpp")

        // ---- First promotion to mainline (copy-up) ------------------------
        switchTo("main")
        merge("dev/rendering", "promote rendering stream", DIMA)
        merge("dev/gameplay", "promote gameplay stream", DIMA)
        src("Source/Engine/Loop.cpp", "void Loop::tick() {", "    fixedStep(); interpolate();", "}")
        commit("engine: fixed timestep with interpolation", DIMA, "Source/Engine/Loop.cpp")
        src("Source/Tools/Cooker.cpp", "void Cooker::cook(Asset a) {", "    write(derive(a));", "}")
        commit("build: asset cooker first pass", JAN, "Source/Tools/Cooker.cpp")
        src("Source/World/Streaming.cpp", "void Streaming::tick() {", "    while (budget()) load(next());", "}")
        commit("world: streaming grid", OLEG, "Source/World/Streaming.cpp")

        // ---- Art stream + more team work ----------------------------------
        branch("art/characters")
        write("Assets/Characters/Hero.mesh", "mesh: hero\nlods: 4\nverts: 48213\n")
        commit("art: hero base mesh", OLEG, "Assets/Characters/Hero.mesh")
        write("Assets/Characters/Hero.rig", "rig: hero\nbones: 214\nik: arms, legs, spine\n")
        commit("art: hero skeleton and IK", OLEG, "Assets/Characters/Hero.rig")
        write("Assets/Materials/Hero.mat", "material: hero\nshader: character_lit\nmaps: albedo, normal, orm\n")
        commit("art: hero material", OLEG, "Assets/Materials/Hero.mat")

        switchTo("dev/rendering")
        merge("main", "sync mainline into rendering", MARCO)
        src("Source/Engine/Shadows.cpp", "void Shadows::render(Light l) {", "    for (int c = 0; c < 4; ++c) cascade(l, c);", "}")
        commit("rendering: four-cascade shadows", MARCO, "Source/Engine/Shadows.cpp")
        src("Source/Engine/SSAO.cpp", "float SSAO::sample(float2 uv) {", "    return occlusion(uv);", "}")
        commit("rendering: SSAO", MARCO, "Source/Engine/SSAO.cpp")

        switchTo("dev/gameplay")
        src("Source/Game/AI.cpp", "void AI::sense(Actor& a) {", "    a.percepts = gather(a);", "}")
        commit("gameplay: AI perception", KATYA, "Source/Game/AI.cpp")
        src("Source/Game/Nav.cpp", "Path Nav::find(Vec a, Vec b) {", "    return astar(a, b);", "}")
        commit("gameplay: navmesh pathfinding", KATYA, "Source/Game/Nav.cpp")
        write("Assets/Audio/Master.bank", "bank: master\nbuses: sfx, music, vo\n")
        src("Source/Engine/Audio.cpp", "void Audio::route() {", "    bus(Sfx); bus(Music); bus(Vo);", "}")
        commit("audio: bus routing", PRIYA, "Source/Engine/Audio.cpp", "Assets/Audio/Master.bank")

        // ---- Cut release/1.0 ----------------------------------------------
        switchTo("main")
        merge("dev/rendering", "promote rendering stream", DIMA)
        merge("dev/gameplay", "promote gameplay stream", DIMA)
        merge("art/characters", "promote character art", DIMA)
        src("Source/Engine/Physics.cpp", "void Physics::broadphase() {", "    sweepAndPrune();", "}")
        commit("engine: physics broadphase", DIMA, "Source/Engine/Physics.cpp")
        src("Source/Tests/Smoke.cpp", "TEST(Smoke, Boots) {", "    ASSERT_TRUE(Engine{}.run(1));", "}")
        commit("qa: smoke test harness", ELENA, "Source/Tests/Smoke.cpp")

        branch("release/1.0")
        write("Config/version.ini", "[Version]\nname=1.0\ncodename=harbor\nphase=beta\n")
        commit("1.0: bump version", ELENA, "Config/version.ini")
        src("Source/World/Streaming.cpp", "void Streaming::tick() {", "    while (budget()) loadAsync(next());", "}")
        commit("1.0: fix streaming hitch on level load", ELENA, "Source/World/Streaming.cpp")
        write("Config/shipping.ini", "[Shipping]\ndebug_overlay=false\ncheats=false\n")
        commit("1.0: disable debug overlay in shipping", JAN, "Config/shipping.ini")
        src("Source/Engine/Audio.cpp", "void Audio::route() {", "    clampVoices(64);", "    bus(Sfx); bus(Music); bus(Vo);", "}")
        commit("1.0: clamp simultaneous audio voices", ELENA, "Source/Engine/Audio.cpp")
        src("Source/World/Level.cpp", "bool Level::load(Path p) {", "    auto h = open(p); if (!h) return false;", "    return parse(h);", "}")
        commit("1.0: guard the null level handle", DIMA, "Source/World/Level.cpp")
        write("Config/version.ini", "[Version]\nname=1.0\ncodename=harbor\nphase=gold\n")
        commit("1.0: mark gold", ELENA, "Config/version.ini")

        switchTo("main")
        merge("release/1.0", "back-merge 1.0 fixes to mainline", DIMA)

        // ---- Post-1.0 development, then a hotfix --------------------------
        src("Source/Engine/TAA.cpp", "float3 TAA::resolve(float2 uv) {", "    return blend(history(uv), current(uv));", "}")
        commit("rendering: temporal anti-aliasing", MARCO, "Source/Engine/TAA.cpp")
        src("Source/Game/Save.cpp", "bool Save::write(int slot) {", "    return header(slot) && world(slot);", "}")
        commit("gameplay: save slots and headers", KATYA, "Source/Game/Save.cpp")
        src("Source/Game/Save.cpp", "bool Save::write(int slot) {", "    return header(slot) && world(slot);", "}", "bool Save::migrate(V1 o) { return writeV2(up(o)); }")
        commit("tools: migrate v1 saves to v2", JAN, "Source/Game/Save.cpp")

        branch("hotfix/1.0.2")
        src("Source/Game/Quest.cpp", "NodeId QuestGraph::add(Node n) {", "    if (frozen) return NodeId::none();", "    return nodes.push(n);", "}")
        commit("1.0.2: crash reloading a completed quest", ELENA, "Source/Game/Quest.cpp")
        src("Source/Engine/Audio.cpp", "void Audio::route() {", "    clampVoices(64);", "    freeBank(previous);", "    bus(Sfx); bus(Music); bus(Vo);", "}")
        commit("1.0.2: leak freeing an audio bank", ELENA, "Source/Engine/Audio.cpp")

        switchTo("release/1.0")
        merge("hotfix/1.0.2", "roll 1.0.2 into the release", ELENA)
        switchTo("main")
        merge("hotfix/1.0.2", "forward-port 1.0.2 to mainline", DIMA)
        src("Source/World/Streaming.cpp", "void Streaming::tick() {", "    while (budget()) loadAsync(next());", "    crossfadeLods();", "}")
        commit("world: LOD crossfade", OLEG, "Source/World/Streaming.cpp")
        src("Source/Engine/Bloom.cpp", "float3 Bloom::apply(float3 c) {", "    return c + threshold(c, 1.1);", "}")
        commit("rendering: bloom threshold tuning", MARCO, "Source/Engine/Bloom.cpp")

        // ---- Streams roll on ----------------------------------------------
        switchTo("dev/rendering")
        merge("main", "sync mainline into rendering", MARCO)
        src("Source/Engine/GI.cpp", "void GI::place() {", "    volumes = fitProbes(scene);", "}")
        commit("rendering: GI probe volumes", MARCO, "Source/Engine/GI.cpp")
        src("Source/Engine/GI.cpp", "void GI::place() {", "    volumes = fitProbes(scene);", "}", "void GI::relight() { for (auto& v : volumes) bake(v); }")
        commit("rendering: probe relighting", MARCO, "Source/Engine/GI.cpp")

        switchTo("dev/gameplay")
        src("Source/Game/Dialogue.cpp", "void Dialogue::run(NodeId n) {", "    if (!cond(n)) return;", "    play(n); advance();", "}")
        commit("gameplay: conditional dialogue", KATYA, "Source/Game/Dialogue.cpp")
        src("Source/Game/Quest.cpp", "NodeId QuestGraph::add(Node n) {", "    if (frozen) return NodeId::none();", "    return nodes.push(n);", "}", "void QuestGraph::reward(NodeId n) { grant(loot(n)); }")
        commit("gameplay: quest rewards", KATYA, "Source/Game/Quest.cpp")
        src("Source/Engine/Audio.cpp", "void Audio::route() {", "    clampVoices(64);", "    footsteps(surface);", "    bus(Sfx); bus(Music); bus(Vo);", "}")
        commit("audio: surface-typed footsteps", PRIYA, "Source/Engine/Audio.cpp")

        // ---- Cut release/1.1 ----------------------------------------------
        switchTo("main")
        merge("dev/rendering", "promote rendering stream", DIMA)
        merge("dev/gameplay", "promote gameplay stream", DIMA)
        src("Source/Engine/Jobs.cpp", "void Jobs::run(Graph g) {", "    schedule(g); waitAll();", "}")
        commit("engine: job system", DIMA, "Source/Engine/Jobs.cpp")
        src("Source/Tools/Cooker.cpp", "void Cooker::cook(Asset a) {", "    if (unchanged(a)) return;", "    write(derive(a));", "}")
        commit("build: incremental cook", JAN, "Source/Tools/Cooker.cpp")

        branch("release/1.1")
        write("Config/version.ini", "[Version]\nname=1.1\ncodename=tideturn\nphase=beta\n")
        commit("1.1: bump version", ELENA, "Config/version.ini")
        src("Source/Engine/TAA.cpp", "float3 TAA::resolve(float2 uv) {", "    return clampHistory(blend(history(uv), current(uv)));", "}")
        commit("1.1: fix TAA ghosting on foliage", ELENA, "Source/Engine/TAA.cpp")
        src("Source/Engine/GI.cpp", "void GI::place() {", "    volumes = fitProbes(scene);", "}", "void GI::relight() { for (auto& v : volumes) bake(v); }", "void GI::unload() { volumes.clear(); }")
        commit("1.1: probe leak on level unload", MARCO, "Source/Engine/GI.cpp")
        src("Source/Game/Save.cpp", "bool Save::write(int slot) {", "    auto g = guard(slot);", "    return header(slot) && world(slot);", "}", "bool Save::migrate(V1 o) { return writeV2(up(o)); }")
        commit("1.1: save corruption on quit-to-desktop", ELENA, "Source/Game/Save.cpp")
        write("Config/version.ini", "[Version]\nname=1.1\ncodename=tideturn\nphase=gold\n")
        commit("1.1: mark gold", ELENA, "Config/version.ini")

        switchTo("main")
        merge("release/1.1", "back-merge 1.1 fixes to mainline", DIMA)

        // ---- Art rework -----------------------------------------------------
        switchTo("art/characters")
        merge("main", "sync mainline into character art", OLEG)
        write("Assets/Characters/HeroCloth.weights", "cloth: hero_cape\nverts: 4096\nstiffness: 0.4\n")
        commit("art: cloth weights for the cape", OLEG, "Assets/Characters/HeroCloth.weights")
        write("Assets/Characters/HeroFace.shapes", "blendshapes: hero_face\ncount: 52\nfacs: true\n")
        commit("art: facial blendshapes", OLEG, "Assets/Characters/HeroFace.shapes")
        write("Assets/Characters/Grunt.mesh", "mesh: grunt\nlods: 3\nverts: 21044\n")
        commit("art: enemy grunt mesh", OLEG, "Assets/Characters/Grunt.mesh")

        switchTo("main")
        merge("art/characters", "promote character art", DIMA)
        src("Source/Game/Mount.cpp", "void Mount::ride(Actor& a) {", "    attach(a, saddle);", "}")
        commit("gameplay: mount system", KATYA, "Source/Game/Mount.cpp")
        src("Source/Engine/Fog.cpp", "float3 Fog::march(Ray r) {", "    return integrate(r, density);", "}")
        commit("rendering: volumetric fog", MARCO, "Source/Engine/Fog.cpp")
        write("Assets/Levels/HarborNight.level", "level: harbor_night\nbase: harbor\nlightmap: harbor_night_0\nmoon_angle: 12\n")
        commit("world: harbor night variant", OLEG, "Assets/Levels/HarborNight.level")
        src("Source/Engine/Profiler.cpp", "void Profiler::frame() {", "    cpu.push(now()); gpu.push(gpuTicks());", "}")
        commit("tools: gpu timings in the profiler", JAN, "Source/Engine/Profiler.cpp")

        // ---- Big rendering feature -----------------------------------------
        switchTo("dev/rendering")
        merge("main", "sync mainline into rendering", MARCO)
        src("Source/Engine/RT.cpp", "void RT::reflections(View v) {", "    trace(v);", "}")
        commit("rendering: ray-traced reflections scaffold", MARCO, "Source/Engine/RT.cpp")
        src("Source/Engine/RT.cpp", "void RT::reflections(View v) {", "    trace(v); denoise(v);", "}")
        commit("rendering: reflection denoiser", MARCO, "Source/Engine/RT.cpp")
        src("Source/Engine/RT.cpp", "void RT::reflections(View v) {", "    if (!supported()) { ssr(v); return; }", "    trace(v); denoise(v);", "}")
        commit("rendering: fall back to SSR when RT is off", MARCO, "Source/Engine/RT.cpp")

        switchTo("main")
        merge("dev/rendering", "promote rendering stream", DIMA)

        switchTo("dev/gameplay")
        src("Source/Game/Combat.cpp", "void Combat::hit(Actor& a) {", "    a.stagger += weight;", "}")
        commit("gameplay: hit stagger", KATYA, "Source/Game/Combat.cpp")
        src("Source/Game/Combat.cpp", "void Combat::hit(Actor& a) {", "    if (a.parrying) { riposte(a); return; }", "    a.stagger += weight;", "}")
        commit("gameplay: parry window and riposte", KATYA, "Source/Game/Combat.cpp")

        switchTo("main")
        merge("dev/gameplay", "promote gameplay stream", DIMA)

        // ---- Cut release/2.0 (still open) ----------------------------------
        src("Source/Engine/Streaming.cpp", "void Streaming::stream() {", "    prefetchAsync(view);", "}")
        commit("engine: async asset streaming", DIMA, "Source/Engine/Streaming.cpp")
        src("Source/Tests/Perf.cpp", "TEST(Perf, HarborNight) {", "    ASSERT_LT(frameMs(), 16.6);", "}")
        commit("qa: perf capture rig", ELENA, "Source/Tests/Perf.cpp")

        branch("release/2.0")
        write("Config/version.ini", "[Version]\nname=2.0\ncodename=deepwater\nphase=alpha\n")
        commit("2.0: bump version", ELENA, "Config/version.ini")
        src("Source/Engine/Fog.cpp", "float3 Fog::march(Ray r) {", "    return dither(integrate(r, density));", "}")
        commit("2.0: fix fog banding on ultrawide", ELENA, "Source/Engine/Fog.cpp")
        src("Source/Engine/RT.cpp", "void RT::reflections(View v) {", "    if (!supported() || quality < High) { ssr(v); return; }", "    trace(v); denoise(v);", "}")
        commit("2.0: RT reflections default on at High", MARCO, "Source/Engine/RT.cpp")

        switchTo("main")
        src("Source/Engine/Streaming.cpp", "void Streaming::stream() {", "    prefetchAsync(view);", "    throttle(io);", "}")
        commit("engine: throttle streaming under heavy IO", DIMA, "Source/Engine/Streaming.cpp")

        switchTo("release/2.0")
        merge("main", "pull the IO throttle fix into 2.0", ELENA)
        write("Config/version.ini", "[Version]\nname=2.0\ncodename=deepwater\nphase=rc1\n")
        commit("2.0: release candidate 1", ELENA, "Config/version.ini")

        // ---- Ongoing work on the open streams and mainline -----------------
        switchTo("dev/rendering")
        src("Source/Engine/RT.cpp", "void RT::reflections(View v) {", "    if (!supported() || quality < High) { ssr(v); return; }", "    motionVectors(v);", "    trace(v); denoise(v);", "}")
        commit("rendering: motion vectors for RT", MARCO, "Source/Engine/RT.cpp")

        switchTo("dev/gameplay")
        src("Source/Game/Dialogue.cpp", "void Dialogue::run(NodeId n) {", "    if (!cond(n)) return;", "    play(n); branchOn(choice()); advance();", "}")
        commit("gameplay: branching dialogue choices", KATYA, "Source/Game/Dialogue.cpp")

        // ---- Recent mainline work, pushed by the rest of the team ----------
        switchTo("main")
        src("Source/Game/Inventory.cpp", "bool Inventory::add(Item i) {", "    if (auto* s = stackOf(i)) return s->grow();", "    return slots.tryPush(i);", "}")
        commit("gameplay: inventory stacking rules", KATYA, "Source/Game/Inventory.cpp")
        src("Source/Engine/Jobs.cpp", "void Jobs::run(Graph g) {", "    schedule(g); waitAll();", "}", "void Jobs::pool() { allocators.reserve(workerCount); }")
        commit("engine: pool per-frame allocators", DIMA, "Source/Engine/Jobs.cpp")
        src("Source/Engine/Audio.cpp", "void Audio::route() {", "    clampVoices(64);", "    footsteps(surface);", "    duckMusic(dialogue);", "    bus(Sfx); bus(Music); bus(Vo);", "}")
        commit("audio: duck music under dialogue", PRIYA, "Source/Engine/Audio.cpp")
        src("Source/Engine/Renderer.cpp", "void Renderer::run() {", "    while (pump()) { beginFrame(); clampExposure(); drawWater(); drawFrame(); present(); }", "}")
        commit("rendering: clamp exposure between frames", DIMA, "Source/Engine/Renderer.cpp")

        // Step this checkout back a few mainline revisions. History walks from the
        // remote tip, so the newer revisions the team pushed now read as unsynced
        // (not pulled into this working copy yet), greyed with a "not synced"
        // note, and a ring marks the revision we are actually on.
        LoreHistoryApi.history(root, limit = 12).getOrNull(5)?.revision?.hex?.let { behind ->
            LoreClient.require(LoreSyncApi.sync(root, behind), "step checkout back")
        }

        // ---- Working-tree state for the Local Changes + locks shot ---------
        // Staged edit.
        write("Assets/Materials/Hero.mat", "material: hero\nshader: character_lit\nmaps: albedo, normal, orm, emissive\n")
        LoreWriteApi.stage(root, listOf("Assets/Materials/Hero.mat"))
        // Unstaged edits.
        write("Config/engine.ini", "[Renderer]\nvsync=true\nexposure=1.2\nbloom=0.3\n")
        write("Assets/Levels/HarborNight.level", "level: harbor_night\nbase: harbor\nlightmap: harbor_night_1\nmoon_angle: 9\nfog: 0.3\n")
        // A binary asset locked by someone else, so the editor shows the lock banner.
        lockAs("Assets/Characters/Hero.mesh", OLEG)

        LoreHistoryApi.history(root, limit = 200).let { entries ->
            println("HIST count=${entries.size}")
            entries.take(12).forEach { println("HIST author=${it.author} subject=${it.subject}") }
        }

        println("SEED READY dir=$root url=${server.url}")
        Thread.sleep(8 * 60 * 60 * 1000L)
        server.close()
    }
}
