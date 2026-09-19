/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.shadowmaster435.biomeparticleweather.util.behavior.BehaviorCondition;
import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public record BehaviorStatement(ArrayList<String> conditions, ArrayList<ArrayList<JsonArray>> functions) {
    static final String[] valid_types = new String[]{"if", "else", "elif"};

    public BehaviorStatement(ArrayList<String> conditions, ArrayList<ArrayList<JsonArray>> functions) {
        this.conditions = conditions;
        this.functions = functions;
    }

    public void try_call(ParticleBehavior behavior) {
        int current_branch = 0;
        boolean else_reached = false;
        for (int i = 0; i < this.functions.size(); ++i) {
            ArrayList<JsonArray> branch_functions = this.functions.get(i);
            if (i < this.conditions.size()) {
                BehaviorCondition cond = (BehaviorCondition)behavior.get(this.conditions.get(i));
                if (!cond.test(behavior)) continue;
                current_branch = i;
                break;
            }
            else_reached = true;
            break;
        }
    }

    public void call_branch(ParticleBehavior behavior, ArrayList<JsonArray> branch) {
        for (JsonArray element : branch) {
            behavior.call(element);
        }
    }

    public static BehaviorStatement parse(JsonArray array) {
        ArrayList<String> statement = new ArrayList<String>();
        ArrayList<ArrayList<JsonArray>> functions = new ArrayList<ArrayList<JsonArray>>();
        for (int cond_index = 0; cond_index < array.size(); ++cond_index) {
            boolean b;
            JsonElement cond = array.get(cond_index);
            String str_cond = cond.getAsString();
            boolean a = Arrays.stream(valid_types).toList().contains(str_cond);
            boolean bl = b = !cond.isJsonArray();
            if (a && b) {
                statement.add(str_cond);
                continue;
            }
            functions.add(new ArrayList<JsonArray>(Collections.singleton(cond.getAsJsonArray())));
        }
        return new BehaviorStatement(statement, functions);
    }
}

