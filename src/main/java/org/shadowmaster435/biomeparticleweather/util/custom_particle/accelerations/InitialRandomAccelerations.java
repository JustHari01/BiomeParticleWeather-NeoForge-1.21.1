/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParseHelper;

public record InitialRandomAccelerations(Vector3 cardinal_vel_min, Vector3 cardinal_vel_max, float angular_vel_min, float angular_vel_max) {
    public InitialRandomAccelerations(Vector3 cardinal_vel_min, Vector3 cardinal_vel_max, float angular_vel_min, float angular_vel_max) {
        this.cardinal_vel_min = cardinal_vel_min;
        this.cardinal_vel_max = cardinal_vel_max;
        this.angular_vel_min = angular_vel_min;
        this.angular_vel_max = angular_vel_max;
    }

    public static InitialRandomAccelerations parse(JsonObject object) {
        JsonObject obj;
        Vector3 cardinal_min = Vector3.ZERO;
        Vector3 cardinal_max = Vector3.ZERO;
        float angular_min = 0.0f;
        float angular_max = 0.0f;
        if (object.has("cardinal_velocity")) {
            obj = object.get("cardinal_velocity").getAsJsonObject();
            cardinal_min = ParseHelper.parse_vec3(obj.get("min").getAsJsonObject());
            cardinal_max = ParseHelper.parse_vec3(obj.get("max").getAsJsonObject());
        }
        if (object.has("angular_velocity")) {
            obj = object.get("angular_velocity").getAsJsonObject();
            angular_min = obj.get("min").getAsFloat();
            angular_max = obj.get("max").getAsFloat();
        }
        return new InitialRandomAccelerations(cardinal_min, cardinal_max, angular_min, angular_max);
    }

    public Vector3 sample_cardinal() {
        return Vector3.rand_between(this.cardinal_vel_min, this.cardinal_vel_max);
    }

    public float sample_angular() {
        return Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)this.angular_vel_min, (float)this.angular_vel_max);
    }
}

