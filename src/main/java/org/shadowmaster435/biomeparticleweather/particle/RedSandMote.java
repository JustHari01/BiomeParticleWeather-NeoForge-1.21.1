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
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class RedSandMote
extends ParticleBase {
    public RedSandMote(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 60;
        this.alpha = 0.0f;
        this.fade_alpha(1.0f, 10);
        this.angular_velocity = 1.0f;
    }

    @Override
    public void tick() {
        super.tick();
        this.zd = 0.5;
        this.yd = Math.sin((float)((long)this.age + this.level.getGameTime()) / 16.0f) / 16.0;
        if (this.age == 50) {
            this.fade_alpha(0.0f, 10);
        }
        if (this.in_block()) {
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
            RedSandMote particle = new RedSandMote(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

