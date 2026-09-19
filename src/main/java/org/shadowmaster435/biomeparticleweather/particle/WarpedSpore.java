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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class WarpedSpore
extends ParticleBase {
    public final Vector2f vel_vec;

    public WarpedSpore(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 100;
        this.alpha = 0.0f;
        this.vel_vec = new Vector2f(Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f), Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f)).normalize();
        this.fade_alpha(1.0f, 10);
        this.roll = -0.5f;
        this.pickSprite((SpriteSet)spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        this.yd = -0.05;
        double sine = Math.sin((float)this.age / 8.0f) / 4.0;
        this.xd = sine * (double)this.vel_vec.x;
        this.zd = sine * (double)this.vel_vec.y;
        if (Math.random() > 0.975) {
            ParticleEngine.spawn_particle((ParticleOptions)ParticleTypes.PORTAL, this.get_pos());
        }
        this.angular_velocity = (float)sine * 16.0f;
        if (this.onGround && this.age < 90) {
            this.age = 90;
        }
        if (this.age == 90) {
            this.fade_scale(0.0f, 10);
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
            WarpedSpore particle = new WarpedSpore(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

