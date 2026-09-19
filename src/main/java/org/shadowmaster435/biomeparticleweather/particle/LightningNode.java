/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class LightningNode
extends ParticleBase {
    public LightningNode(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 5;
        this.alpha = 0.0f;
        int lightning_range = 4;
        Vector3 rand_pos = Vector3.rand_between(new Vector3(pos.x - (double)lightning_range, 0.0, pos.z - (double)lightning_range), new Vector3(pos.x + (double)lightning_range, 0.0, pos.z + (double)lightning_range));
        rand_pos = new Vector3(rand_pos.x, pos.y + 12.0, rand_pos.z);
        if (Math.random() < 0.75 && pos.y > 150.0) {
            int branch_multiplier = 4;
            Vector3 rand_pos2 = Vector3.rand_between(new Vector3(pos.x - (double)(lightning_range * branch_multiplier), pos.y - 4.0, pos.z - (double)(lightning_range * branch_multiplier)), new Vector3(pos.x + (double)(lightning_range * branch_multiplier), pos.y + 4.0, pos.z + (double)(lightning_range * branch_multiplier)));
            for (int i = 0; i < ParticleSettings.get_int("lightning_particle_density"); ++i) {
                double delta = ((double)i + 0.001) / 32.0;
                Vector3 lerped = rand_pos.lerp(rand_pos2, delta);
                ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.LIGHTNING, new Vector3(lerped));
            }
        }
        for (int i = 0; i < 16; ++i) {
            double delta = ((double)i + 0.001) / 16.0;
            Vector3 lerped = rand_pos.lerp(pos, delta);
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.LIGHTNING, new Vector3(lerped));
        }
        if (rand_pos.y < 192.0) {
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.LIGHTNING_NODE, rand_pos);
        }
    }

    @Override
    public void tick() {
        super.tick();
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
            LightningNode particle = new LightningNode(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

