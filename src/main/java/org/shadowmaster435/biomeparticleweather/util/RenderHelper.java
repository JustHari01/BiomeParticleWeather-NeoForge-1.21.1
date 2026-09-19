/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.RenderParams;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class RenderHelper {
    public RenderHelper() {
    }

    public static void render_particle_quad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ParticleBase particle) {
        Vec3 vec3d = camera.getPosition();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_x(), (double)particle.position_x()) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_y(), (double)particle.position_y()) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_z(), (double)particle.position_z()) - vec3d.z());
        float scale = particle.getQuadSize(tickDelta);
        Vector3f[] corners = new Vector3f[]{new Vector3f(-1.0f, 0.0f, -1.0f), new Vector3f(-1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, -1.0f)};
        for (int j = 0; j < 4; ++j) {
            Vector3f corner = corners[j];
            corner.mul(scale);
            corner.add(drawx, drawy, drawz);
        }
        float k = particle.u0();
        float l = particle.u1();
        float m = particle.v0();
        float n = particle.v1();
        int o = particle.getLightColor(tickDelta);
        vertexConsumer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setUv(l, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setUv(l, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setUv(k, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setUv(k, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
    }

    public static void render_particle_quad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ParticleBase particle, RenderParams params) {
        Vec3 vec3d = camera.getPosition();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_x(), (double)particle.position_x()) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_y(), (double)particle.position_y()) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_z(), (double)particle.position_z()) - vec3d.z());
        float scale = particle.getQuadSize(tickDelta);
        Vector3f[] corners = new Vector3f[]{new Vector3f(-1.0f, 0.0f, -1.0f), new Vector3f(-1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, -1.0f)};
        for (int j = 0; j < 4; ++j) {
            Vector3f corner = corners[j];
            corner.mul(scale);
            corner.rotateY((float)((double)particle.roll_value() + Math.toRadians(params.rotation_offset())));
            corner.add(drawx + params.offset().x, drawy + params.offset().y, drawz + params.offset().z);
        }
        float k = particle.u0();
        float l = particle.u1();
        float m = particle.v0();
        float n = particle.v1();
        int o = particle.getLightColor(tickDelta);
        vertexConsumer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setUv(l, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setUv(l, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setUv(k, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setUv(k, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
    }

    public static void render_particle_quad(VertexConsumer vertexConsumer, Camera camera, float tickDelta, ParticleBase particle, RenderParams params, Vector3 rot) {
        RenderSystem.disableCull();
        Vec3 vec3d = camera.getPosition();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_x(), (double)particle.position_x()) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_y(), (double)particle.position_y()) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)particle.previous_z(), (double)particle.position_z()) - vec3d.z());
        float scale = particle.getQuadSize(tickDelta);
        Vector3f[] corners = new Vector3f[]{new Vector3f(-1.0f, 0.0f, -1.0f), new Vector3f(-1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, 1.0f), new Vector3f(1.0f, 0.0f, -1.0f)};
        for (int j = 0; j < 4; ++j) {
            Vector3f corner = corners[j];
            corner.mul(scale);
            corner.rotateX((float)((double)particle.roll_value() + Math.toRadians(rot.x)));
            corner.rotateY((float)((double)particle.roll_value() + Math.toRadians(rot.y)));
            corner.rotateZ((float)((double)particle.roll_value() + Math.toRadians(rot.z)));
            corner.add(drawx + params.offset().x, drawy + params.offset().y, drawz + params.offset().z);
        }
        float k = particle.u0();
        float l = particle.u1();
        float m = particle.v0();
        float n = particle.v1();
        int o = particle.getLightColor(tickDelta);
        vertexConsumer.addVertex(corners[0].x(), corners[0].y(), corners[0].z()).setUv(l, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[1].x(), corners[1].y(), corners[1].z()).setUv(l, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[2].x(), corners[2].y(), corners[2].z()).setUv(k, m).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
        vertexConsumer.addVertex(corners[3].x(), corners[3].y(), corners[3].z()).setUv(k, n).setColor(particle.red(), particle.green(), particle.blue(), particle.opacity()).setLight(o);
    }
}

