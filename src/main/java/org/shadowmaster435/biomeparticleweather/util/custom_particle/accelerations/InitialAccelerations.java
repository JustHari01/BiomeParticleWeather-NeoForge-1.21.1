/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParseHelper;

public record InitialAccelerations(Vector3 cardinal_vel, float angular_vel) {
    public InitialAccelerations(Vector3 cardinal_vel, float angular_vel) {
        this.cardinal_vel = cardinal_vel;
        this.angular_vel = angular_vel;
    }

    public static InitialAccelerations parse(JsonObject object) {
        Vector3 cardinal = Vector3.ZERO;
        float angular = 0.0f;
        if (object.has("cardinal_velocity")) {
            System.out.println((Object)ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject()));
            cardinal = ParseHelper.parse_vec3(object.get("cardinal_velocity").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            angular = object.get("angular_velocity").getAsFloat();
        }
        return new InitialAccelerations(cardinal, angular);
    }
}

