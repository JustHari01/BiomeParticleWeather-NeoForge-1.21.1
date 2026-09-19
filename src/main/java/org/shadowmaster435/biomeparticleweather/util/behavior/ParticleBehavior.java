/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorCondition;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorFunction;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorStatement;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorValue;

public class ParticleBehavior {
    public HashMap<String, BehaviorValue> values = new HashMap();
    public HashMap<String, BehaviorCondition> conditions = new HashMap();
    public HashMap<String, BehaviorStatement> statements = new HashMap();
    public HashMap<String, BehaviorStatement> setters = new HashMap();
    public HashMap<String, BehaviorFunction> functions = new HashMap();

    public ParticleBehavior() {
    }

    public void set(String key, Object value) {
        try {
            this.values.get(key).set(value);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object get(String key) {
        BehaviorValue val = this.values.getOrDefault(key, null);
        BehaviorCondition cond = this.conditions.getOrDefault(key, null);
        BehaviorStatement set = this.setters.getOrDefault(key, null);
        BehaviorFunction func = this.functions.getOrDefault(key, null);
        Object result = null;
        if (val != null) {
            result = val;
        }
        if (cond != null) {
            result = cond;
        }
        if (cond != null) {
            result = set;
        }
        if (cond != null) {
            result = func;
        }
        return result;
    }

    public void call(JsonArray array) {
        String func_name = array.get(0).getAsString();
        ArrayList<JsonElement> inputs = new ArrayList<JsonElement>();
        for (int i = 0; i < array.size(); ++i) {
            if (i == 0) continue;
            inputs.add(array.get(i));
        }
        BehaviorFunction func = this.functions.getOrDefault(func_name, null);
        if (func != null) {
            func.call(inputs);
        }
    }
}

