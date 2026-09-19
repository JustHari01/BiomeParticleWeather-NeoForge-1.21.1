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
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.shadowmaster435.biomeparticleweather.particle.ModelParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleModelPart;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxel;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelUVMap;

public class TumbleBush
extends ModelParticleBase {
    private int current_sprite = 0;
    public float current_rot = 0.0f;
    private boolean hit_wall = false;

    public TumbleBush(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 100;
        this.quadSize = 0.5f;
        this.current_sprite = (int)Math.round(Math.random() * 3.0);
        this.hasPhysics = true;
        this.setSize(1.0f, 1.0f);
        this.alpha = 0.0f;
        this.fade_alpha(1.0f, 10);
        this.make_model();
    }

    public void make_model() {
        VoxelUVMap uv = VoxelUVMap.rectangle(new Vector2i(0, 0), new Vector2i(8, 8));
        ParticleVoxel outer_voxel = ParticleVoxel.create(new Vector3(-0.5, -0.5, -0.5), new Vector3(new Vector3(4.0f, 4.0f, 4.0f).scale(0.25)), uv);
        ParticleModelPart outer = new ParticleModelPart(new ParticleVoxel[]{outer_voxel}, outer_voxel.params());
        ParticleVoxel inner_voxel = ParticleVoxel.create(new Vector3(-0.25, -0.25, -0.25), new Vector3(new Vector3(2.0f, 2.0f, 2.0f).scale(0.25)), uv);
        ParticleModelPart inner = new ParticleModelPart(new ParticleVoxel[]{inner_voxel}, inner_voxel.params());
        this.set_model(new String[]{"outer", "inner"}, new ParticleModelPart[]{outer, inner});
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        super.render(vertexConsumer, camera, tickDelta);
        if (!Minecraft.getInstance().isPaused()) {
            this.current_rot = !this.hit_wall ? (this.current_rot += 0.25f * tickDelta) : (this.current_rot -= 0.125f * tickDelta * (1.0f - (float)((this.age - 40) / 60)));
            this.transform = new VoxelTransform(this.transform.position(), Vector3.ONE, new Vector3(0.0f, 0.0f, -this.current_rot), Vector3.ZERO);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.yd -= 0.05;
        if (this.age == 10) {
            this.fade_alpha(1.0f, 60);
        }
        if (this.age == 60) {
            this.fade_alpha(0.0f, 35);
        }
        this.xd = 0.35;
        if (this.onGround) {
            this.yd *= (double)-0.8f;
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
            TumbleBush particle = new TumbleBush(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

