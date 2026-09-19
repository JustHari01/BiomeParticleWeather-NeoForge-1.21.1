/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParseHelper;

public record ConstantAccelerations(Vector3 cardinal, float angular) {
    public ConstantAccelerations(Vector3 cardinal, float angular) {
        this.cardinal = cardinal;
        this.angular = angular;
    }

    public static ConstantAccelerations parse(JsonObject object) {
        Vector3 cardinal = Vector3.ZERO;
        float angular = 0.0f;
        if (object.has("cardinal_velocity")) {
            cardinal = ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            angular = object.get("angular_velocity").getAsFloat();
        }
        return new ConstantAccelerations(cardinal, angular);
    }
}

