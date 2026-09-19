/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import org.joml.Vector3f;

public record RenderParams(Vector3f offset, float rotation_offset, float scale_offset) {
    public RenderParams(Vector3f offset, float rotation_offset, float scale_offset) {
        this.offset = offset;
        this.rotation_offset = rotation_offset;
        this.scale_offset = scale_offset;
    }
}

