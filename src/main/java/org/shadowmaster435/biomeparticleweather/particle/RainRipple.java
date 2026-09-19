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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class RainRipple
extends ParticleBase {
    public RainRipple(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 8;
        this.alpha = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.35f, (float)0.45f);
        this.quadSize = 0.0f;
        this.fade_alpha(0.0f, 7);
    }

    @Override
    public void tick() {
        if (this.age == 1) {
            this.quadSize = 0.5f;
        }
        super.tick();
        this.setSpriteFromAge((SpriteSet)this.provider);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.getPosition();
        float f = (float)(Mth.lerp((double)tickDelta, (double)this.xo, (double)this.x) - vec3d.x());
        float g = (float)(Mth.lerp((double)tickDelta, (double)this.yo, (double)this.y) - vec3d.y());
        float h = (float)(Mth.lerp((double)tickDelta, (double)this.zo, (double)this.z) - vec3d.z());
        float i = this.getQuadSize(tickDelta);
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0f, 0.0f, -1.0f), new Vector3f(-1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, -1.0f)};
        for (int j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }
        float k = this.getU0();
        float l = this.getU1();
        float m = this.getV0();
        float n = this.getV1();
        int o = this.getLightColor(tickDelta);
        vertexConsumer.addVertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).setUv(l, n).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(o);
        vertexConsumer.addVertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).setUv(l, m).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(o);
        vertexConsumer.addVertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).setUv(k, m).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(o);
        vertexConsumer.addVertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).setUv(k, n).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(o);
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
            RainRipple particle = new RainRipple(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

