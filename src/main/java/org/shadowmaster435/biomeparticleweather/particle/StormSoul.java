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

public class StormSoul
extends ParticleBase {
    public StormSoul(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 100;
        this.alpha = 0.0f;
        this.hasPhysics = true;
        this.quadSize = 0.35f;
        this.fade_alpha(1.0f, 10);
        this.setSize(0.05f, 0.25f);
        this.setSpriteFromAge((SpriteSet)spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        this.yd = -0.1;
        if (this.onGround && this.age < 90) {
            ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.STORM_SOUL_IMPACT, this.get_pos().with_y(this.get_pos().y + (double)0.35f));
            this.remove();
        }
        this.setSprite(this.provider.get(Math.max(0, (this.age * 3 + (int)(this.level.getGameTime() + 2000L) % 2000) % this.lifetime), this.lifetime));
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
            StormSoul particle = new StormSoul(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}
