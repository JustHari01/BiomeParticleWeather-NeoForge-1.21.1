/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorValue;

public record BehaviorParser(BehaviorValue[] values) {
    private static final Gson GSON = new GsonBuilder().create();

    public BehaviorParser(BehaviorValue[] values) {
        this.values = values;
    }

    public static BehaviorParser parse(String[] strings) {
        BehaviorParser result = new BehaviorParser(new BehaviorValue[0]);
        for (String string : strings) {
        }
        return result;
    }

    public static void read_json(JsonObject obj) {
    }
}

