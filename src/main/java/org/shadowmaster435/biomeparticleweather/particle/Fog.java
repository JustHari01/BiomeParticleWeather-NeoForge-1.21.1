/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ParticleUtil;
import org.shadowmaster435.biomeparticleweather.util.RenderHelper;
import org.shadowmaster435.biomeparticleweather.util.RenderParams;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class Fog
extends ParticleBase {
    public Fog(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 40;
        this.alpha = 0.0f;
        this.quadSize = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.6f, (float)1.0f);
        this.fade_alpha(0.45f, 10);
        this.angular_velocity = (float)Math.random();
    }

    @Override
    public void tick() {
        double distance = Minecraft.getInstance().player.position().distanceTo((Vec3)this.get_pos());
        double fade_delta = 1.0 - distance / 16.0;
        if (this.age == 10) {
            this.fade_alpha(0.45f, 20);
        }
        if (this.age == 30) {
            this.fade_alpha(0.0f, 10);
        }
        if (distance > 16.0 || ParticleUtil.get_world().getBlockState(BlockPos.containing((Position)this.get_pos())).isSolid()) {
            this.alpha = 0.0f;
            this.remove();
        }
        super.tick();
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        for (int i = 1; i < 2; ++i) {
            double delta = (double)((float)i / 4.0f) * Math.PI;
            double life_delta = ((double)this.age + 0.001) / (double)this.lifetime * Math.PI;
            float y_offset = (float)Math.sin((delta + life_delta) % (Math.PI * 2) - Math.PI) / 8.0f;
            RenderParams params = new RenderParams(new Vector3f(0.0f, 0.0f, 0.0f), 25 * i, this.quadSize / (float)i);
            RenderHelper.render_particle_quad(vertexConsumer, camera, tickDelta, this, params);
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
            Fog particle = new Fog(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

