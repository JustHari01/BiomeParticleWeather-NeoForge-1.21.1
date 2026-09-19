/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public record BehaviorCondition(ArrayList<String> statement) {
    public BehaviorCondition(ArrayList<String> statement) {
        this.statement = statement;
    }

    public static BehaviorCondition parse(JsonArray array) {
        ArrayList<String> statement = new ArrayList<String>();
        for (int cond_index = 0; cond_index < array.size(); ++cond_index) {
            String cond = array.get(cond_index).getAsString();
            statement.add(cond);
        }
        return new BehaviorCondition(statement);
    }

    public boolean test(ParticleBehavior behavior) {
        String[] valid_types = new String[]{"==", "!=", ">", "<", "<=", ">=", "and", "or", "xor"};
        boolean comparing = false;
        Object current_value = null;
        String current_comparator = null;
        for (String string : this.statement) {
            if (Arrays.stream(valid_types).toList().contains(string)) {
                current_comparator = string;
                comparing = true;
                continue;
            }
            if (!comparing) {
                if (BehaviorCondition.is_number(string)) {
                    current_value = Float.valueOf(Float.parseFloat(string));
                    continue;
                }
                if (this.is_bool(string)) {
                    current_value = Boolean.parseBoolean(string);
                    continue;
                }
                current_value = behavior.get(string);
                continue;
            }
            Object comparing_value = BehaviorCondition.is_number(string) ? Float.valueOf(Float.parseFloat(string)) : (this.is_bool(string) ? Boolean.valueOf(Boolean.parseBoolean(string)) : behavior.get(string));
            current_value = this.get_value(current_value, current_comparator, comparing_value);
            comparing = false;
        }
        boolean result = !(current_value instanceof Boolean) ? false : (Boolean)current_value;
        return result;
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

    public boolean get_value(Object first, String type, Object second) {
        boolean result = false;
        switch (type) {
            case "!=": {
                result = this.not_equal(first, second);
                break;
            }
            case "==": {
                result = this.equal(first, second);
                break;
            }
            case ">": {
                result = this.greater_than(first, second);
                break;
            }
            case "<": {
                result = this.less_than(first, second);
                break;
            }
            case ">=": {
                result = this.greater_than_eq(first, second);
                break;
            }
            case "<=": {
                result = this.less_than_eq(first, second);
                break;
            }
            case "and": {
                result = this.and(first, second);
                break;
            }
            case "or": {
                result = this.or(first, second);
                break;
            }
            case "xor": {
                result = this.xor(first, second);
            }
        }
        return result;
    }

    public boolean and(Object first, Object second) {
        return (Boolean)first != false && (Boolean)second != false;
    }

    public boolean or(Object first, Object second) {
        return (Boolean)first != false || (Boolean)second != false;
    }

    public boolean xor(Object first, Object second) {
        return (Boolean)first ^ (Boolean)second;
    }

    public boolean not_equal(Object first, Object second) {
        return first != second;
    }

    public boolean equal(Object first, Object second) {
        return first == second;
    }

    public boolean greater_than(Object first, Object second) {
        return (Double)first > (Double)second;
    }

    public boolean less_than(Object first, Object second) {
        return (Double)first < (Double)second;
    }

    public boolean greater_than_eq(Object first, Object second) {
        return (Double)first >= (Double)second;
    }

    public boolean less_than_eq(Object first, Object second) {
        return (Double)first <= (Double)second;
    }
}

