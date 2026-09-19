/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParseHelper;

public record SpawnRangeParameters(Vector3 min_range, Vector3 max_range) {
    public SpawnRangeParameters(Vector3 min_range, Vector3 max_range) {
        this.min_range = min_range;
        this.max_range = max_range;
    }

    public static SpawnRangeParameters parse(JsonObject object) {
        Vector3 min = Vector3.ZERO;
        Vector3 max = Vector3.ZERO;
        min = ParseHelper.parse_vec3(object.get("min").getAsJsonObject());
        max = ParseHelper.parse_vec3(object.get("max").getAsJsonObject());
        return new SpawnRangeParameters(min, max);
    }
}

