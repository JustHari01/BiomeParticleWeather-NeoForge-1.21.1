/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.util.BindableTimer;
import org.shadowmaster435.biomeparticleweather.util.BindableTimerQueue;

public record ParticleAnimations(BindableTimerQueue red, BindableTimerQueue green, BindableTimerQueue blue, BindableTimerQueue alpha, BindableTimerQueue scale) {
    public ParticleAnimations(BindableTimerQueue red, BindableTimerQueue green, BindableTimerQueue blue, BindableTimerQueue alpha, BindableTimerQueue scale) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.scale = scale;
    }

    public static ParticleAnimations parse(JsonObject object) {
        JsonArray obj;
        BindableTimerQueue red = new BindableTimerQueue(new HashMap<Integer, BindableTimer>());
        BindableTimerQueue green = new BindableTimerQueue(new HashMap<Integer, BindableTimer>());
        BindableTimerQueue blue = new BindableTimerQueue(new HashMap<Integer, BindableTimer>());
        BindableTimerQueue alpha = new BindableTimerQueue(new HashMap<Integer, BindableTimer>());
        BindableTimerQueue scale = new BindableTimerQueue(new HashMap<Integer, BindableTimer>());
        if (object.has("red")) {
            obj = object.get("red").getAsJsonArray();
            red = BindableTimerQueue.from_json(obj, "red");
        }
        if (object.has("green")) {
            obj = object.get("green").getAsJsonArray();
            green = BindableTimerQueue.from_json(obj, "green");
        }
        if (object.has("blue")) {
            obj = object.get("blue").getAsJsonArray();
            blue = BindableTimerQueue.from_json(obj, "blue");
        }
        if (object.has("alpha")) {
            obj = object.get("alpha").getAsJsonArray();
            alpha = BindableTimerQueue.from_json(obj, "alpha");
        }
        if (object.has("scale")) {
            obj = object.get("scale").getAsJsonArray();
            scale = BindableTimerQueue.from_json(obj, "scale");
        }
        return new ParticleAnimations(red, green, blue, alpha, scale);
    }
}

