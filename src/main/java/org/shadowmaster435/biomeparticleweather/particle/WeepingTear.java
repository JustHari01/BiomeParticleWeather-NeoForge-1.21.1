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
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class WeepingTear
extends ParticleBase {
    public WeepingTear(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 100;
        this.pickSprite((SpriteSet)spriteProvider);
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age < 10) {
            this.yd = -0.01;
        } else {
            this.yd = -1.0;
            this.hasPhysics = true;
        }
        if (this.onGround) {
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.WEEPING_TEAR_SPLASH, this.get_pos());
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
            WeepingTear particle = new WeepingTear(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

