/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;

public record ParticleVoxelParams(Vector3 size, Vector3 origin, VoxelTransform transform) {
    public ParticleVoxelParams(Vector3 size, Vector3 origin, VoxelTransform transform) {
        this.size = size;
        this.origin = origin;
        this.transform = transform;
    }

    public static ParticleVoxelParams transform_by(ParticleVoxelParams transformed, VoxelTransform transformer) {
        return new ParticleVoxelParams(transformed.size, transformed.origin, VoxelTransform.transform_by(transformed.transform, transformer));
    }
}

