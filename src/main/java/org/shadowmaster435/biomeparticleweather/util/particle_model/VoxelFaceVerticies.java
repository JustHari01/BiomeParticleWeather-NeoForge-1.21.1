/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;

public record VoxelFaceVerticies(Vector3 bl, Vector3 tl, Vector3 tr, Vector3 br) {
    public VoxelFaceVerticies(Vector3 bl, Vector3 tl, Vector3 tr, Vector3 br) {
        this.bl = bl;
        this.tl = tl;
        this.tr = tr;
        this.br = br;
    }

    public Vector3f[] create_array(VoxelTransform transform) {
        Vector3 rot = transform.rotation().deg_to_rad();
        Vector3f bl = this.bl.add(transform.position()).multiply((Vec3)transform.scale()).xRot((float)rot.x).yRot((float)rot.y).zRot((float)rot.z).toVector3f();
        Vector3f tl = this.tl.add(transform.position()).multiply((Vec3)transform.scale()).xRot((float)rot.x).yRot((float)rot.y).zRot((float)rot.z).toVector3f();
        Vector3f tr = this.tr.add(transform.position()).multiply((Vec3)transform.scale()).xRot((float)rot.x).yRot((float)rot.y).zRot((float)rot.z).toVector3f();
        Vector3f br = this.br.add(transform.position()).multiply((Vec3)transform.scale()).xRot((float)rot.x).yRot((float)rot.y).zRot((float)rot.z).toVector3f();
        return new Vector3f[]{bl, tl, tr, br};
    }

    public static VoxelFaceVerticies create(Direction dir, Vector3 position, Vector3 size) {
        Vector3 bl = position;
        Vector3 tl = position;
        Vector3 tr = position;
        Vector3 br = position;
        boolean negative_axis = false;
        boolean negative_axis_z = false;
        switch (dir.getAxis()) {
            case X: {
                bl = dir == Direction.WEST ? bl.add_z(size.z) : bl.add_z(size.z).add_x(size.x);
                tl = dir == Direction.WEST ? tl.add_z(size.z).add_y(size.y) : tl.add_z(size.z).add_y(size.y).add_x(size.x);
                tr = dir == Direction.WEST ? tr.add_y(size.y) : tr.add_y(size.y).add_x(size.x);
                Vector3 vector3 = br = dir == Direction.WEST ? br : br.add_x(size.x);
                if (dir.getStepX() >= 0) break;
                negative_axis = true;
                break;
            }
            case Y: {
                bl = dir == Direction.DOWN ? bl : bl.add_y(size.y);
                tl = dir == Direction.DOWN ? tl.add_z(size.z) : tl.add_z(size.z).add_y(size.y);
                tr = dir == Direction.DOWN ? tr.add_x(size.x).add_z(size.z) : tr.add_y(size.y).add_z(size.z).add_x(size.x);
                Vector3 vector3 = br = dir == Direction.DOWN ? br.add_x(size.x) : br.add_x(size.x).add_y(size.y);
                if (dir.getStepY() <= 0) break;
                negative_axis = true;
                break;
            }
            case Z: {
                bl = dir == Direction.NORTH ? bl : bl.add_z(size.z);
                tl = dir == Direction.NORTH ? tl.add_y(size.y) : tl.add_z(size.z).add_y(size.y);
                tr = dir == Direction.NORTH ? tr.add_y(size.y).add_x(size.x) : tr.add_z(size.z).add_y(size.y).add_x(size.x);
                Vector3 vector3 = br = dir == Direction.NORTH ? br.add_x(size.x) : br.add_x(size.x).add_z(size.z);
                if (dir.getStepZ() >= 0) break;
                negative_axis_z = true;
            }
        }
        return new VoxelFaceVerticies(br, tr, tl, bl);
    }
}

