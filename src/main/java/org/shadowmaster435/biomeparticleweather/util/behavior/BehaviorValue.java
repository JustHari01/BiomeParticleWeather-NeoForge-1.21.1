/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.JsonElement;

public class BehaviorValue {
    private Object value;
    private final Class<?> object_class;

    public BehaviorValue(Object value) {
        this.value = value;
        this.object_class = value.getClass();
    }

    public void set(Object value) {
        if (this.get_class().isInstance(value)) {
            this.value = value;
        } else {
            System.out.println("Incompatible Class");
        }
    }

    public Object get() {
        return this.value;
    }

    public static BehaviorValue parse(JsonElement object) {
        return new BehaviorValue(BehaviorValue.try_parse_value(object));
    }

    public final Class<?> get_class() {
        return this.object_class;
    }

    public static Object try_parse_value(JsonElement object) {
        Object result = null;
        try {
            result = object.getAsInt();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            result = Float.valueOf(object.getAsFloat());
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            result = object.getAsString();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            result = object.getAsJsonArray();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            result = object.getAsJsonObject();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }
}

