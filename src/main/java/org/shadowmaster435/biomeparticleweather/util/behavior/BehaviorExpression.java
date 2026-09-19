/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public record BehaviorExpression(ArrayList<String> expression) {
    public BehaviorExpression(ArrayList<String> expression) {
        this.expression = expression;
    }

    public float calculate(ParticleBehavior behavior) {
        String[] valid_types = new String[]{"==", "!=", ">", "<", "<=", ">=", "and", "or", "xor"};
        boolean comparing = false;
        Object current_value = null;
        String current_comparator = null;
        for (String string : this.expression) {
            if (Arrays.stream(valid_types).toList().contains(string)) {
                current_comparator = string;
                comparing = true;
                continue;
            }
            if (!comparing) {
                if (BehaviorExpression.is_number(string)) {
                    current_value = Float.valueOf(Float.parseFloat(string));
                    continue;
                }
                current_value = behavior.get(string);
                continue;
            }
            Object comparing_value = BehaviorExpression.is_number(string) ? Float.valueOf(Float.parseFloat(string)) : behavior.get(string);
            current_value = Float.valueOf(this.get_value(current_value, current_comparator, comparing_value));
            comparing = false;
        }
        float result = !(current_value instanceof Float) && !(current_value instanceof Double) && !(current_value instanceof Integer) ? 0.0f : ((Float)current_value).floatValue();
        return result;
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

    public float get_value(Object first, String type, Object second) {
        float result = 0.0f;
        switch (type) {
            case "+": {
                result = this.add(first, second);
                break;
            }
            case "-": {
                result = this.sub(first, second);
                break;
            }
            case "*": {
                result = this.mul(first, second);
                break;
            }
            case "/": {
                result = this.div(first, second);
                break;
            }
            case "%": {
                result = this.mod(first, second);
            }
        }
        return result;
    }

    public float add(Object first, Object second) {
        return ((Float)first).floatValue() + ((Float)second).floatValue();
    }

    public float sub(Object first, Object second) {
        return ((Float)first).floatValue() - ((Float)second).floatValue();
    }

    public float mul(Object first, Object second) {
        return ((Float)first).floatValue() * ((Float)second).floatValue();
    }

    public float div(Object first, Object second) {
        return ((Float)first).floatValue() / ((Float)second).floatValue();
    }

    public float mod(Object first, Object second) {
        return ((Float)first).floatValue() % ((Float)second).floatValue();
    }
}

