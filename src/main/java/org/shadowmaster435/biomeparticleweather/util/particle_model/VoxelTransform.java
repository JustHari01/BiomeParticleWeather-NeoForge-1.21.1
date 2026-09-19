/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import net.minecraft.world.phys.Vec3;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public record VoxelTransform(Vector3 position, Vector3 scale, Vector3 rotation, Vector3 origin) {
    public VoxelTransform(Vector3 position, Vector3 scale, Vector3 rotation, Vector3 origin) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.origin = origin;
    }

    public static VoxelTransform def() {
        return new VoxelTransform(Vector3.ZERO, Vector3.ONE, Vector3.ZERO, Vector3.ZERO);
    }

    public static VoxelTransform transform_by(VoxelTransform transformed, VoxelTransform transformer) {
        Vec3 scale = transformed.scale.multiply(transformer.scale);
        Vec3 pos = transformer.get_transformed_pos(transformed.position()).multiply(scale);
        Vec3 rot = transformed.rotation.add(transformer.rotation);
        return new VoxelTransform(new Vector3(pos), new Vector3(scale), new Vector3(rot), transformer.origin);
    }

    public Vector3 get_transformed_pos(Vector3 vec) {
        return new Vector3(vec.add(this.position));
    }
}

