/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.RenderHelper;
import org.shadowmaster435.biomeparticleweather.util.RenderParams;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class BlizzardWind
extends ParticleBase {
    public BlizzardWind(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 16;
        this.alpha = 0.0f;
        this.quadSize = 0.75f;
        this.setSpriteFromAge((SpriteSet)this.provider);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 0.25f;
        this.setSpriteFromAge((SpriteSet)this.provider);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        RenderHelper.render_particle_quad(vertexConsumer, camera, tickDelta, this, new RenderParams(Vector3.ZERO.to_3f(), 0.0f, 1.0f), new Vector3(90.0f, 90.0f, 180.0f));
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
            BlizzardWind particle = new BlizzardWind(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

