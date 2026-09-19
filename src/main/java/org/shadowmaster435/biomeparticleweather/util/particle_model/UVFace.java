/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.particle_model;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public record UVFace(Vector2i start, Vector2i end) {
    public UVFace(Vector2i start, Vector2i end) {
        this.start = start;
        this.end = end;
    }

    public static UVFace single_pixel(Vector2i pixel_position) {
        return new UVFace(pixel_position, new Vector2i((Vector2ic)pixel_position).add(1, 1));
    }

    public static UVFace square(Vector2i pixel_position, int size) {
        return new UVFace(pixel_position, new Vector2i((Vector2ic)pixel_position).add((Vector2ic)new Vector2i(size)));
    }

    public static UVFace rectangle(Vector2i pixel_position, Vector2i size) {
        return new UVFace(pixel_position, new Vector2i((Vector2ic)pixel_position).add((Vector2ic)size));
    }
}

