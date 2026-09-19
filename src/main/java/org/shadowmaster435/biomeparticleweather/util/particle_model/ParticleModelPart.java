/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.shadowmaster435.biomeparticleweather.particle.ModelParticleBase;
import org.shadowmaster435.biomeparticleweather.mixin.TextureAtlasAccessor;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxel;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxelParams;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelFace;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;

public record ParticleModelPart(ParticleVoxel[] voxels, ParticleVoxelParams params) {
    public ParticleModelPart(ParticleVoxel[] voxels, ParticleVoxelParams params) {
        this.voxels = voxels;
        this.params = params;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, VoxelTransform transform) {
        RenderSystem.disableCull();
        for (ParticleVoxel val : this.voxels) {
            this.voxel(vertexConsumer, camera, tickDelta, particle, ParticleVoxelParams.transform_by(this.params, transform), val);
        }
    }

    private void voxel(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, ParticleVoxelParams params, ParticleVoxel voxel) {
        for (Direction dir : Direction.values()) {
            this.quad(vertexConsumer, camera, tickDelta, particle, params, voxel.faces().get(dir));
        }
    }

    private void quad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, ParticleVoxelParams params, VoxelFace face) {
        Vec3 vec3d = camera.getPosition();
        VoxelTransform tf = params.transform();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_x(), (double)particle.position_x()) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_y(), (double)particle.position_y()) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_z(), (double)particle.position_z()) - vec3d.z());
        float scale = particle.getQuadSize(tickDelta);
        Vector3f[] corners = face.verticies().create_array(tf);
        for (int j = 0; j < 4; ++j) {
            Vector3f corner = corners[j];
            corner.rotateX((float)tf.rotation().x);
            corner.rotateY((float)tf.rotation().y);
            corner.rotateZ((float)tf.rotation().z);
            corner.add((Vector3fc)tf.origin().to_3f());
            corner.mul(scale);
            corner.add(drawx, drawy, drawz);
        }
        TextureAtlasAccessor atlas = (TextureAtlasAccessor)(Object)particle.provider.getAtlas();
        float k = (float)(face.uv().start().x + particle.sprite().getX()) / (float)atlas.biomeParticleWeather$getWidth();
        float l = (float)(face.uv().end().x + particle.sprite().getX()) / (float)atlas.biomeParticleWeather$getWidth();
        float m = (float)(face.uv().start().y + particle.sprite().getY()) / (float)atlas.biomeParticleWeather$getHeight();
        float n = (float)(face.uv().end().y + particle.sprite().getY()) / (float)atlas.biomeParticleWeather$getHeight();
        int o = particle.getLightColor(tickDelta);
        VertexConsumer consumer = particle.sprite().wrap(vertexConsumer);
        vertexConsumer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setUv(l, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setUv(l, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setUv(k, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setUv(k, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, VoxelTransform transform, Vector3 offset, Vector3 rot) {
        RenderSystem.disableCull();
        for (ParticleVoxel val : this.voxels) {
            this.voxel(vertexConsumer, camera, tickDelta, particle, ParticleVoxelParams.transform_by(this.params, transform), val, offset, rot);
        }
    }

    private void voxel(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, ParticleVoxelParams params, ParticleVoxel voxel, Vector3 offset, Vector3 rot) {
        for (Direction dir : Direction.values()) {
            this.quad(vertexConsumer, camera, tickDelta, particle, params, voxel.faces().get(dir), offset, rot);
        }
    }

    private void quad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ModelParticleBase particle, ParticleVoxelParams params, VoxelFace face, Vector3 offset, Vector3 rot) {
        Vec3 vec3d = camera.getPosition();
        VoxelTransform tf = params.transform();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_x(), (double)particle.position_x()) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_y(), (double)particle.position_y()) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_z(), (double)particle.position_z()) - vec3d.z());
        float scale = particle.getQuadSize(tickDelta);
        Vector3f[] corners = face.verticies().create_array(tf);
        for (int j = 0; j < 4; ++j) {
            Vector3f corner = corners[j];
            corner.rotateX((float)tf.rotation().x + (float)rot.x);
            corner.rotateY((float)tf.rotation().y + (float)rot.y);
            corner.rotateZ((float)tf.rotation().z + (float)rot.z);
            corner.add((Vector3fc)tf.origin().to_3f());
            corner.mul(scale);
            corner.add((Vector3fc)offset.to_3f()).add(drawx, drawy, drawz);
        }
        TextureAtlasAccessor atlas = (TextureAtlasAccessor)(Object)particle.provider.getAtlas();
        float k = (float)(face.uv().start().x + particle.sprite().getX()) / (float)atlas.biomeParticleWeather$getWidth();
        float l = (float)(face.uv().end().x + particle.sprite().getX()) / (float)atlas.biomeParticleWeather$getWidth();
        float m = (float)(face.uv().start().y + particle.sprite().getY()) / (float)atlas.biomeParticleWeather$getHeight();
        float n = (float)(face.uv().end().y + particle.sprite().getY()) / (float)atlas.biomeParticleWeather$getHeight();
        int o = particle.getLightColor(tickDelta);
        VertexConsumer consumer = particle.sprite().wrap(vertexConsumer);
        vertexConsumer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setUv(l, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setUv(l, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setUv(k, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setUv(k, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
    }
}
