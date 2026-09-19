/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.util.FloatifiedForLoop;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.shadowmaster435.biomeparticleweather.util.ParticleUtil;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticle;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleBehavior;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleRegistry;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn.SpawnRangeParameters;

public class ParticleEngine {
    public ParticleEngine() {
    }

    public static void try_spawn_particles() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        try {
            if (player != null && !Minecraft.getInstance().isPaused()) {
                FloatifiedForLoop.run(i -> {
                    ParticleEngine.rain();
                    ParticleEngine.fog();
                    ParticleEngine.wind();
                    ParticleEngine.sandstorm();
                    ParticleEngine.snow();
                    ParticleEngine.updraft();
                    ParticleEngine.warped_spore();
                    ParticleEngine.weeping_tears();
                    ParticleEngine.soul_storm();
                    ParticleEngine.fireflies();
                    ParticleEngine.custom();
                }, ParticleSettings.get_float("global_multiplier"));
            }
        }
        catch (Exception e) {
            System.out.println("Config File Read Error Loading Default Values");
            System.out.println(e.getMessage());
            ParticleSettings.load_defaults();
        }
    }

    public static void rain() {
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        ClientLevel world = Minecraft.getInstance().level;
        if (world.isRaining() && ParticleUtil.can_rain(player_pos)) {
            float thunder_amount = ParticleSettings.get_float("heavy_rain_amount");
            float amount = ParticleSettings.get_float("rain_amount");
            if (world.isThundering()) {
                amount = thunder_amount;
            }
            FloatifiedForLoop.run(i -> {
                Vector3 rand = ParticleEngine.get_rand_radius();
                Vector3 pos = new Vector3(player_pos.add(new Vector3(rand.x, 8.0, rand.z)));
                if (pos.subtract(0.0, 8.0, 0.0).distanceTo((Vec3)player_pos) > 1.0 && world.canSeeSky(pos.to_blockpos())) {
                    ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.RAIN, pos);
                }
            }, amount);
        }
    }

    public static void sandstorm() {
        boolean normal_desert;
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        ClientLevel world = Minecraft.getInstance().level;
        boolean a = world.getBiome(player_pos.to_blockpos()).is(Biomes.DESERT);
        boolean b = world.getBiome(player_pos.to_blockpos()).is(Biomes.BADLANDS);
        boolean c = world.getBiome(player_pos.to_blockpos()).is(Biomes.ERODED_BADLANDS);
        boolean d = world.getBiome(player_pos.to_blockpos()).is(Biomes.WOODED_BADLANDS);
        boolean bl = normal_desert = !b && !c && !d;
        if (world.isRaining() && (a || b || c || d)) {
            float red_sand_amount = ParticleSettings.get_float("red_sand_mote_amount");
            float sand_amount = ParticleSettings.get_float("sand_mote_amount");
            float tumblebush_amount = ParticleSettings.get_float("tumblebush_amount");
            Vector3 rand_offset = player_pos.with_random_offset(16.0f);
            if (world.getBlockState(rand_offset.to_blockpos()).isAir()) {
                if (normal_desert) {
                    ParticleEngine.try_spawn_particle_in_height_range((ParticleOptions)BiomeParticleWeather.SAND_MOTE, player_pos, new Vector3(ParticleEngine.get_rand_radius().x, 8.0, ParticleEngine.get_rand_radius().x), sand_amount, 63.0f, 196.0f);
                } else {
                    ParticleEngine.try_spawn_particle_in_height_range((ParticleOptions)BiomeParticleWeather.RED_SAND_MOTE, player_pos, new Vector3(ParticleEngine.get_rand_radius().x, 8.0, ParticleEngine.get_rand_radius().x), red_sand_amount, 63.0f, 196.0f);
                }
                FloatifiedForLoop.run(i -> ParticleEngine.spawn_particle_on_surface((ParticleOptions)BiomeParticleWeather.TUMBLE_BUSH, player_pos.x - 8.0, player_pos.z, 0.0, 1.25f, ParticleEngine.get_rand_radius()), tumblebush_amount);
            }
        }
    }

    public static void weeping_tears() {
        ClientLevel world = Minecraft.getInstance().level;
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        if (world.getBiome(player_pos.to_blockpos()).is(Biomes.CRIMSON_FOREST)) {
            float amount = ParticleSettings.get_float("weeping_tear_amount");
            ParticleEngine.try_spawn_particle_under_block((ParticleOptions)BiomeParticleWeather.WEEPING_TEAR, Blocks.NETHER_WART_BLOCK, player_pos, Math.round(amount * 0.75f));
            ParticleEngine.try_spawn_particle_under_block((ParticleOptions)BiomeParticleWeather.WEEPING_TEAR, Blocks.CRIMSON_FUNGUS, player_pos, Math.round(amount * 0.25f));
        }
    }

    public static void warped_spore() {
        ClientLevel world = Minecraft.getInstance().level;
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        if (world.getBiome(player_pos.to_blockpos()).is(Biomes.WARPED_FOREST)) {
            float amount = ParticleSettings.get_float("spore_amount");
            ParticleEngine.try_spawn_particle((ParticleOptions)BiomeParticleWeather.WARPED_SPORE, player_pos, ParticleEngine.get_rand_radius(), amount);
        }
    }

    public static void soul_storm() {
        ClientLevel world = Minecraft.getInstance().level;
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        if (world.getBiome(player_pos.to_blockpos()).is(Biomes.SOUL_SAND_VALLEY)) {
            float amount = ParticleSettings.get_float("soul_amount");
            ParticleEngine.try_spawn_particle_above_block((ParticleOptions)BiomeParticleWeather.STORM_SOUL, Blocks.SOUL_SAND, player_pos, (int)Math.floor(ParticleEngine.get_rand_radius().x), 4, amount * 0.5f);
            ParticleEngine.try_spawn_particle_above_block((ParticleOptions)BiomeParticleWeather.STORM_SOUL, Blocks.SOUL_SOIL, player_pos, (int)Math.floor(ParticleEngine.get_rand_radius().x), 4, amount * 0.5f);
        }
    }

    public static void snow() {
        ClientLevel world = Minecraft.getInstance().level;
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        Vector3 rand_ofs = player_pos.with_random_offset((float)ParticleEngine.get_rand_radius().x);
        if (((Biome)world.getBiome(rand_ofs.to_blockpos()).value()).getPrecipitationAt(rand_ofs.to_blockpos()) == Biome.Precipitation.SNOW) {
            float snow_amount = ParticleSettings.get_float("snow_amount");
            float blizzard_snow_amount = ParticleSettings.get_float("blizzard_snow_amount");
            float blizzard_wind_amount = ParticleSettings.get_float("blizzard_wind_amount");
            if (world.isThundering()) {
                ParticleEngine.try_spawn_particle_in_height_range((ParticleOptions)BiomeParticleWeather.BLIZZARD_SNOW, new Vector3(player_pos.add(0.0, 0.0, 8.0)), new Vector3((float)ParticleEngine.get_rand_radius().x, 8.0f), blizzard_snow_amount, 63.0f, 256.0f);
                ParticleEngine.try_spawn_particle_in_height_range((ParticleOptions)BiomeParticleWeather.BLIZZARD_WIND, player_pos, new Vector3((float)ParticleEngine.get_rand_radius().x, 8.0f), blizzard_wind_amount, 63.0f, 256.0f);
            } else if (world.isRaining()) {
                FloatifiedForLoop.run(i -> {
                    Vector3 rand = ParticleEngine.get_rand_radius();
                    Vector3 pos = new Vector3(player_pos.add(new Vector3(rand.x, 8.0, rand.z)));
                    if (pos.subtract(0.0, 8.0, 0.0).distanceTo((Vec3)player_pos) > 1.0 && world.canSeeSky(pos.to_blockpos())) {
                        ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.SNOW, pos);
                    }
                }, snow_amount);
            }
        }
    }

    public static void updraft() {
        Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
        ClientLevel world = Minecraft.getInstance().level;
        boolean a = world.getBiome(player_pos.to_blockpos()).is(Biomes.BASALT_DELTAS);
        boolean b = world.getBiome(player_pos.to_blockpos()).is(Biomes.NETHER_WASTES);
        if (a || b) {
            ParticleEngine.try_spawn_particle((ParticleOptions)BiomeParticleWeather.UPDRAFT, player_pos, new Vector3(16.0f, 8.0f), ParticleSettings.get_float("updraft_amount"));
        }
    }

    public static void fog() {
        Holder<Biome> biome = ParticleUtil.get_biome_at(BlockPos.containing((Position)ParticleUtil.get_player_pos_plus_vel(2.0f)));
        if ((biome.is(Biomes.SWAMP) || biome.is(Biomes.MANGROVE_SWAMP)) && ParticleUtil.get_world().isRaining()) {
            Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
            FloatifiedForLoop.run(i -> {
                Vector3 rand = ParticleEngine.get_rand_radius();
                Vector3 pos = new Vector3(player_pos.add(new Vector3(rand.x, 8.0, rand.z)));
                ParticleEngine.spawn_particle_on_surface((ParticleOptions)BiomeParticleWeather.FOG, pos.x, pos.z, pos.y, Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.15f, (float)0.35f), new Vector3(0.0f));
            }, ParticleSettings.get_float("fog_amount"));
        }
    }

    public static void fireflies() {
        Holder<Biome> biome = ParticleUtil.get_biome_at(BlockPos.containing((Position)ParticleUtil.get_player_pos_plus_vel(2.0f)));
        ClientLevel world = Minecraft.getInstance().level;
        boolean a1 = biome.is(Biomes.MANGROVE_SWAMP);
        if (world.getDayTime() < 22000L && world.getDayTime() > 14000L && a1) {
            Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.FIREFLY, player_pos, ParticleSettings.get_float("firefly_amount"), new Vector3(ParticleEngine.get_rand_radius().x, 8.0, ParticleEngine.get_rand_radius().x));
        }
    }

    public static void wind() {
        Holder<Biome> biome = ParticleUtil.get_biome_at(BlockPos.containing((Position)ParticleUtil.get_player_pos_plus_vel(2.0f)));
        ClientLevel world = Minecraft.getInstance().level;
        boolean a1 = biome.is(Biomes.SAVANNA) || biome.is(Biomes.SAVANNA_PLATEAU);
        boolean b1 = biome.is(Biomes.WINDSWEPT_SAVANNA);
        if (world.isRaining() && (a1 || b1)) {
            Vector3 player_pos = ParticleUtil.get_player_pos_plus_vel(2.0f);
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.WIND, player_pos, ParticleSettings.get_float("wind_amount"), new Vector3(ParticleEngine.get_rand_radius().x, 8.0, ParticleEngine.get_rand_radius().x));
        }
    }

    public static void custom() {
        try {
            for (String string : CustomParticleRegistry.behaviors.keySet()) {
                int amount = CustomParticleRegistry.behaviors.get(string).particle_parameters().amount();
                FloatifiedForLoop.run(i -> ParticleEngine.try_spawn_custom_particle(string, ParticleUtil.get_player_pos_plus_vel(2.0f)), amount);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void spawn_particle_on_surface(ParticleOptions effect, double x, double z, double ypos, float y_offset, Vector3 rand_ofs) {
        ClientLevel world = ParticleUtil.get_world();
        Vec3 pos = new Vector3((Vec3i)world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing((double)x, (double)ypos, (double)z))).add(Vector3.rand_between(rand_ofs.negate(), rand_ofs).with_y(0.0));
        ParticleEngine.spawn_particle(effect, new Vector3(pos.x(), pos.y() + (double)y_offset, pos.z()));
    }

    public static void try_spawn_particle_in_height_range(ParticleOptions effect, Vector3 pos_origin, Vector3 max_ofs, float amount, float min_y, float max_y) {
        ClientLevel world = Minecraft.getInstance().level;
        FloatifiedForLoop.run(i -> {
            boolean in_range;
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            BlockPos bp = pos.to_blockpos();
            BlockState block_at = world.getBlockState(bp);
            boolean is_not_solid = !block_at.isRedstoneConductor((BlockGetter)world, bp);
            boolean bl = in_range = pos.y > (double)min_y && pos.y < (double)max_y;
            if (is_not_solid && in_range) {
                ParticleEngine.spawn_particle(effect, pos);
            }
        }, amount);
    }

    public static void try_spawn_particle(ParticleOptions effect, Vector3 pos_origin, Vector3 max_ofs, float amount) {
        ClientLevel world = Minecraft.getInstance().level;
        FloatifiedForLoop.run(i -> {
            boolean is_not_solid;
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            BlockPos bp = pos.to_blockpos();
            BlockState block_at = world.getBlockState(bp);
            boolean bl = is_not_solid = !block_at.isRedstoneConductor((BlockGetter)world, bp);
            if (is_not_solid) {
                ParticleEngine.spawn_particle(effect, pos);
            }
        }, amount);
    }

    public static void try_spawn_particle(ParticleOptions effect, Vector3 pos_origin, float max_ofs, float amount) {
        ClientLevel world = Minecraft.getInstance().level;
        FloatifiedForLoop.run(i -> {
            boolean is_not_solid;
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            BlockPos bp = pos.to_blockpos();
            BlockState block_at = world.getBlockState(bp);
            boolean bl = is_not_solid = !block_at.isRedstoneConductor((BlockGetter)world, bp);
            if (is_not_solid) {
                ParticleEngine.spawn_particle(effect, pos);
            }
        }, amount);
    }

    public static void try_spawn_particle_under_block(ParticleOptions effect, Block block, Vector3 pos, float tries) {
        ClientLevel world = ParticleUtil.get_world();
        FloatifiedForLoop.run(i -> {
            Vector3 ofs = pos.with_random_offset(ParticleEngine.get_rand_radius());
            BlockState bs = world.getBlockState(ofs.to_blockpos());
            BlockState bs_under = world.getBlockState(ofs.to_blockpos().relative(Direction.DOWN));
            Vector3 under_pos = ofs.with_y(Math.floor(ofs.y) - (double)0.05f);
            if (bs.is(block) && bs_under.isAir()) {
                ParticleEngine.spawn_particle(effect, under_pos);
            }
        }, tries);
    }

    public static void try_spawn_particle_above_block(ParticleOptions effect, Block block, Vector3 pos, int y_ofs, float tries) {
        ClientLevel world = ParticleUtil.get_world();
        FloatifiedForLoop.run(i -> {
            BlockState bs = world.getBlockState(pos.to_blockpos());
            BlockState bs_over = world.getBlockState(pos.to_blockpos().relative(Direction.UP, y_ofs));
            Vector3 over_pos = pos.with_y(Math.floor(pos.y) + (double)y_ofs);
            if (bs.is(block) && bs_over.isAir()) {
                ParticleEngine.spawn_particle(effect, over_pos);
            }
        }, tries);
    }

    public static void try_spawn_particle_above_block(ParticleOptions effect, Block block, Vector3 pos_origin, int max_ofs, int y_ofs, float tries) {
        ClientLevel world = ParticleUtil.get_world();
        FloatifiedForLoop.run(i -> {
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            BlockState bs = world.getBlockState(pos.to_blockpos());
            BlockState bs_over = world.getBlockState(pos.to_blockpos().relative(Direction.UP, y_ofs));
            Vector3 over_pos = pos.with_y(Math.floor(pos.y) + (double)y_ofs);
            if (bs.is(block) && bs_over.isAir()) {
                ParticleEngine.spawn_particle(effect, over_pos);
            }
        }, tries);
    }

    public static void spawn_particle(ParticleOptions effect, Vector3 pos) {
        Minecraft.getInstance().level.addParticle(effect, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
    }

    public static void spawn_particle(ParticleOptions effect, Vector3 pos_origin, float tries, float max_ofs) {
        FloatifiedForLoop.run(i -> {
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            Minecraft.getInstance().level.addParticle(effect, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
        }, tries);
    }

    public static void spawn_particle(ParticleOptions effect, Vector3 pos_origin, float tries, Vector3 max_ofs) {
        FloatifiedForLoop.run(i -> {
            Vector3 pos = pos_origin.with_random_offset(max_ofs);
            Minecraft.getInstance().level.addParticle(effect, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
        }, tries);
    }

    public static Vector3 get_rand_radius() {
        return Vector3.rand_between(new Vector3(-ParticleSettings.get_int("spawn_radius")), new Vector3(ParticleSettings.get_int("spawn_radius")));
    }

    public static void try_spawn_custom_particle(String particle_name, Vector3 pos) {
        try {
            CustomParticleBehavior behavior;
            ClientLevel world = Minecraft.getInstance().level;
            if (world != null && (behavior = (CustomParticleBehavior)CustomParticleRegistry.behaviors.getOrDefault(particle_name, null)) != null) {
                CustomParticle particle;
                SpawnRangeParameters range = behavior.parameters().range_parameters();
                Vector3 min = range.min_range();
                Vector3 max = range.max_range();
                Vector3 rand = new Vector3(Vector3.rand_between(min, max).add(pos));
                if (behavior.parameters().conditions().test((Level)world, rand.to_blockpos()) && (particle = (CustomParticle)Minecraft.getInstance().particleEngine.createParticle((ParticleOptions)BiomeParticleWeather.CUSTOM, rand.x, rand.y, rand.z, 0.0, 0.0, 0.0)) != null) {
                    particle.particle_name = particle_name;
                    particle.init();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

