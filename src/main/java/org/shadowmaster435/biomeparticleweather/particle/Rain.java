/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class Rain
extends ParticleBase {
    public Rain(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        assert (Minecraft.getInstance().level != null);
        this.lifetime = 200;
        this.alpha = 0.0f;
        this.fade_alpha(0.8f, 2);
        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        BlockPos blockPos;
        FluidState fluidState;
        super.tick();
        this.quadSize = 0.125f;
        this.yd = -1.0;
        boolean trails = ParticleSettings.get_bool("rain_trails");
        boolean splash = ParticleSettings.get_bool("rain_splash");
        boolean ripples = ParticleSettings.get_bool("rain_surface_ripples");
        this.setSprite(this.provider.get(RandomSource.create((long)this.random_seed)));
        if (this.alpha >= 0.8f && this.get_pos().y > Minecraft.getInstance().player.position().y - 8.0 && trails) {
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.RAIN_TRAIL, new Vector3(this.get_pos().add(new Vector3(0.0, 0.02, 0.0))));
        }
        if (!(fluidState = this.level.getFluidState(blockPos = BlockPos.containing((double)this.x, (double)this.y, (double)this.z))).isEmpty() && fluidState.isSource()) {
            float height = fluidState.getOwnHeight();
            if (splash) {
                if (fluidState.is((Fluid)Fluids.LAVA)) {
                    ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.STEAM_JET, new Vector3(this.x, (double)((float)blockPos.getY() + height) + 0.15, this.z));
                } else {
                    ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.RAIN_SPLASH, new Vector3(this.x, (double)((float)blockPos.getY() + height) + 0.05, this.z));
                }
            }
            if (ripples) {
                ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.RAIN_RIPPLE, new Vector3(this.x, (double)((float)blockPos.getY() + height), this.z));
            }
            this.remove();
        } else if (this.onGround && this.get_collision_direction().name().equals("UP")) {
            if (splash) {
                ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.RAIN_SPLASH, new Vector3(this.get_hit_pos().add(new Vec3(0.0, 1.0625, 0.0))));
            }
            this.remove();
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory
    implements ParticleProvider<SimpleParticleType> {
        private final FabricSpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Rain particle = new Rain(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}
