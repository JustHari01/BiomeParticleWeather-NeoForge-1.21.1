/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import java.util.HashMap;
import net.minecraft.core.Direction;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxelParams;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelFace;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelFaceVerticies;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelUVMap;

public record ParticleVoxel(HashMap<Direction, VoxelFace> faces, ParticleVoxelParams params) {
    public ParticleVoxel(HashMap<Direction, VoxelFace> faces, ParticleVoxelParams params) {
        this.faces = faces;
        this.params = params;
    }

    public static ParticleVoxel create(Vector3 position, Vector3 size, VoxelUVMap uv_map) {
        HashMap<Direction, VoxelFace> faces = new HashMap<Direction, VoxelFace>();
        for (Direction dir : Direction.values()) {
            faces.put(dir, new VoxelFace(VoxelFaceVerticies.create(dir, position, size), uv_map.get_value(dir)));
        }
        return new ParticleVoxel(faces, new ParticleVoxelParams(size, Vector3.ZERO, VoxelTransform.def()));
    }

    public static ParticleVoxel create(Vector3 position, Vector3 size, VoxelUVMap uv_map, Vector3 origin) {
        HashMap<Direction, VoxelFace> faces = new HashMap<Direction, VoxelFace>();
        for (Direction dir : Direction.values()) {
            faces.put(dir, new VoxelFace(VoxelFaceVerticies.create(dir, new Vector3(position.add(origin)), size), uv_map.get_value(dir)));
        }
        VoxelTransform tf = new VoxelTransform(Vector3.ZERO, Vector3.ONE, Vector3.ZERO, origin);
        return new ParticleVoxel(faces, new ParticleVoxelParams(size, Vector3.ZERO, VoxelTransform.def()));
    }
}

