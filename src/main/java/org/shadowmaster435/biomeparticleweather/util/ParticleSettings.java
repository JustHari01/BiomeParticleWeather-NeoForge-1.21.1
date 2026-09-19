/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.util.HashMap;

public class ParticleSettings {
    public static HashMap<String, Boolean> bools = new HashMap();
    public static HashMap<String, Integer> ints = new HashMap();
    public static HashMap<String, Float> floats = new HashMap();
    public static HashMap<String, String> strings = new HashMap();

    public ParticleSettings() {
    }

    public static void load_defaults() {
        bools.clear();
        ints.clear();
        floats.clear();
        strings.clear();
        ints.put("version", 2);
        ints.put("spawn_radius", 16);
        ints.put("lightning_particle_density", 32);
        floats.put("global_multiplier", Float.valueOf(1.0f));
        floats.put("rain_amount", Float.valueOf(16.0f));
        floats.put("heavy_rain_amount", Float.valueOf(24.0f));
        floats.put("snow_amount", Float.valueOf(16.0f));
        floats.put("blizzard_snow_amount", Float.valueOf(16.0f));
        floats.put("blizzard_wind_amount", Float.valueOf(16.0f));
        floats.put("sand_mote_amount", Float.valueOf(16.0f));
        floats.put("wind_amount", Float.valueOf(16.0f));
        floats.put("red_sand_mote_amount", Float.valueOf(16.0f));
        floats.put("tumblebush_amount", Float.valueOf(0.1f));
        floats.put("updraft_amount", Float.valueOf(16.0f));
        floats.put("soul_amount", Float.valueOf(16.0f));
        floats.put("weeping_tear_amount", Float.valueOf(16.0f));
        floats.put("spore_amount", Float.valueOf(16.0f));
        floats.put("fog_amount", Float.valueOf(24.0f));
        floats.put("firefly_amount", Float.valueOf(1.0f));
        bools.put("config_button_visible", true);
        bools.put("vanilla_lightning", false);
        bools.put("rain_trails", true);
        bools.put("rain_splash", true);
        bools.put("rain_surface_ripples", true);
        bools.put("vanilla_rain_rendering", false);
        bools.put("vanilla_rain_splash", false);
    }

    public static boolean get_bool(String key) {
        try {
            return bools.get(key);
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static int get_int(String key) {
        return ints.get(key);
    }

    public static float get_float(String key) {
        return floats.get(key).floatValue();
    }

    public static String get_string(String key) {
        return strings.get(key);
    }

    public static void set_bool(String key, boolean value) {
        bools.replace(key, value);
    }

    public static void set_int(String key, int value) {
        ints.replace(key, value);
    }

    public static void set_float(String key, float value) {
        floats.replace(key, Float.valueOf(value));
    }

    public static void set_string(String key, String value) {
        strings.replace(key, value);
    }

    public static void load_bool(String[] entry) {
        String value_name = entry[0];
        String value = entry[1];
        bools.put(value_name, Boolean.parseBoolean(value));
    }

    public static void load_int(String[] entry) {
        String value_name = entry[0];
        String value = entry[1];
        ints.put(value_name, Integer.parseInt(value));
    }

    public static void load_float(String[] entry) {
        String value_name = entry[0];
        String value = entry[1];
        floats.put(value_name, Float.valueOf(Float.parseFloat(value)));
    }

    public static void load_string(String[] entry) {
        String value_name = entry[0];
        String value = entry[1];
        strings.put(value_name, value);
    }

    public static String get_config_string() {
        Object value;
        String key;
        int i;
        StringBuilder builder = new StringBuilder();
        for (i = 0; i < ints.size(); ++i) {
            key = (String)ints.keySet().stream().toList().get(i);
            value = ints.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        builder.append("float\n");
        for (i = 0; i < floats.size(); ++i) {
            key = (String)floats.keySet().stream().toList().get(i);
            value = floats.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        builder.append("bool\n");
        for (i = 0; i < bools.size(); ++i) {
            key = (String)bools.keySet().stream().toList().get(i);
            value = bools.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        builder.append("string\n");
        for (i = 0; i < strings.size(); ++i) {
            key = (String)strings.keySet().stream().toList().get(i);
            value = strings.get(key);
            builder.append(key);
            builder.append("=");
            builder.append((String)value);
            builder.append("\n");
        }
        return builder.toString();
    }
}

