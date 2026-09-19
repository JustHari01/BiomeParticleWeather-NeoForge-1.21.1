/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import org.shadowmaster435.biomeparticleweather.util.particle_model.UVFace;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelFaceVerticies;

public record VoxelFace(VoxelFaceVerticies verticies, UVFace uv) {
    public VoxelFace(VoxelFaceVerticies verticies, UVFace uv) {
        this.verticies = verticies;
        this.uv = uv;
    }
}

