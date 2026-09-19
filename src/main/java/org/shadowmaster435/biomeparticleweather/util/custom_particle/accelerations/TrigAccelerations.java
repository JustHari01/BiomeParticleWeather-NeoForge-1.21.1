/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParseHelper;

public record TrigAccelerations(Vector3 sine_speed, Vector3 sine_amplitude, Vector3 cosine_speed, Vector3 cosine_amplitude) {
    public TrigAccelerations(Vector3 sine_speed, Vector3 sine_amplitude, Vector3 cosine_speed, Vector3 cosine_amplitude) {
        this.sine_speed = sine_speed;
        this.sine_amplitude = sine_amplitude;
        this.cosine_speed = cosine_speed;
        this.cosine_amplitude = cosine_amplitude;
    }

    public static TrigAccelerations parse(JsonObject object) {
        JsonObject obj;
        Vector3 sin = Vector3.ZERO;
        Vector3 sin_amplitude = Vector3.ZERO;
        Vector3 cos = Vector3.ZERO;
        Vector3 cos_amplitude = Vector3.ZERO;
        if (object.has("sine")) {
            obj = object.get("sine").getAsJsonObject();
            if (obj.has("speed")) {
                sin = ParseHelper.parse_vec3(obj.get("speed").getAsJsonObject());
            }
            if (obj.has("amplitude")) {
                sin_amplitude = ParseHelper.parse_vec3(obj.get("amplitude").getAsJsonObject());
            }
        }
        if (object.has("cosine")) {
            obj = object.get("cosine").getAsJsonObject();
            if (obj.has("speed")) {
                cos = ParseHelper.parse_vec3(obj.get("speed").getAsJsonObject());
            }
            if (obj.has("amplitude")) {
                cos_amplitude = ParseHelper.parse_vec3(obj.get("amplitude").getAsJsonObject());
            }
        }
        return new TrigAccelerations(sin, sin_amplitude, cos, cos_amplitude);
    }
}

