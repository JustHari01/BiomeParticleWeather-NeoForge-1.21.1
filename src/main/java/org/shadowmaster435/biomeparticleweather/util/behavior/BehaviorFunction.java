/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorValue;
import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public class BehaviorFunction {
    public final ParticleBehavior parent;
    public HashMap<String, BehaviorValue> values = new HashMap();

    public BehaviorFunction(ParticleBehavior parent, String[] lines) {
        this.parent = parent;
    }

    public Object get(String key) {
        BehaviorValue val = this.values.getOrDefault(key, null);
        Object result = val != null ? val : this.parent.get(key);
        return result;
    }

    public void call(ArrayList<JsonElement> elements) {
        for (JsonElement element : elements) {
            String string = element.getAsString();
            if (!this.is_bool(string) && !BehaviorFunction.is_number(string)) continue;
        }
    }

    public boolean is_bool(String str) {
        boolean is_false = str.equalsIgnoreCase("false");
        boolean is_true = str.equalsIgnoreCase("true");
        return is_false || is_true;
    }

    public static boolean is_number(String str) {
        boolean result = false;
        try {
            Integer.parseInt(str);
            result = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            Float.parseFloat(str);
            result = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }
}

