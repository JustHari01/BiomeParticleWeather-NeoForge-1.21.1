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

public class Lightning
extends ParticleBase {
    public Lightning(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 40;
        this.quadSize = 0.5f;
        this.fade_scale(0.0f, 39);
        this.fade_rgb(new Vector3(0.7, 0.4, 0.0), 39);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public int getLightColor(float tint) {
        return 0xF000F0;
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
            Lightning particle = new Lightning(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

