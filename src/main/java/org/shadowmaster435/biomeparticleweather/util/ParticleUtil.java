/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class ParticleUtil {
    public ParticleUtil() {
    }

    public static BlockState get_blockstate_at(BlockPos pos) {
        ClientLevel world = ParticleUtil.get_world();
        return world.getBlockState(pos);
    }

    public static boolean can_rain() {
        LocalPlayer player = Minecraft.getInstance().player;
        return ((Biome)Minecraft.getInstance().level.getBiome(player.blockPosition()).value()).getPrecipitationAt(player.blockPosition()) == Biome.Precipitation.RAIN;
    }

    public static boolean can_rain(Vector3 pos) {
        return ((Biome)Minecraft.getInstance().level.getBiome(pos.to_blockpos()).value()).getPrecipitationAt(pos.to_blockpos()) == Biome.Precipitation.RAIN;
    }

    public static Holder<Biome> get_biome_at(BlockPos pos) {
        return ParticleUtil.get_world().getBiome(pos);
    }

    public static boolean is_biome_at(BlockPos pos, ResourceKey<Biome> biome) {
        return ParticleUtil.get_biome_at(pos).is(biome);
    }

    public static Vector3 get_player_pos_plus_vel(float multipier) {
        LocalPlayer player = ParticleUtil.get_player();
        double yvel = player.getDeltaMovement().y;
        if (player.onGround()) {
            yvel = 0.0;
        }
        Vec3 result = new Vector3(player.getDeltaMovement().x, yvel, player.getDeltaMovement().z).scale(multipier);
        return new Vector3(player.position().add(result));
    }

    public static Vector3 get_player_pos_plus_vel() {
        LocalPlayer player = ParticleUtil.get_player();
        double yvel = player.getDeltaMovement().y;
        if (player.onGround()) {
            yvel = 0.0;
        }
        Vector3 result = new Vector3(player.getDeltaMovement().x, yvel, player.getDeltaMovement().z);
        return new Vector3(player.position().add((Vec3)result));
    }

    public static LocalPlayer get_player() {
        return Minecraft.getInstance().player;
    }

    public static ClientLevel get_world() {
        return Minecraft.getInstance().level;
    }
}

