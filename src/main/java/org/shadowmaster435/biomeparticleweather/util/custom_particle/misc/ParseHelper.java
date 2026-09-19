/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.misc;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class ParseHelper {
    public ParseHelper() {
    }

    public static Vector3 parse_vec3(JsonObject object) {
        Vector3 result = Vector3.ZERO;
        boolean is_all = object.has("all");
        boolean has_x = object.has("x");
        boolean has_y = object.has("y");
        boolean has_z = object.has("z");
        if (is_all) {
            return new Vector3(object.get("all").getAsFloat());
        }
        if (has_x) {
            result = result.with_x(object.get("x").getAsFloat());
        }
        if (has_y) {
            result = result.with_y(object.get("y").getAsFloat());
        }
        if (has_z) {
            result = result.with_z(object.get("z").getAsFloat());
        }
        return result;
    }
}

