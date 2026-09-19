/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public record BiomeCondition(ArrayList<String> is, ArrayList<String> isnt, boolean used) {
    public BiomeCondition(ArrayList<String> is, ArrayList<String> isnt, boolean used) {
        this.is = is;
        this.isnt = isnt;
        this.used = used;
    }

    public boolean test(ResourceKey<Biome> biome) {
        if (!this.used) {
            return true;
        }
        String str = biome.location().toString();
        boolean matches_is = this.is.stream().toList().contains(str);
        boolean matches_isnt = this.isnt.stream().toList().contains(str);
        if (this.is.isEmpty()) {
            matches_isnt = true;
        }
        return matches_is && !matches_isnt;
    }

    public static BiomeCondition parse(JsonObject json) {
        boolean has_either;
        BiomeCondition default_fallback = new BiomeCondition(new ArrayList<String>(), new ArrayList<String>(), false);
        ArrayList<String> is_result = new ArrayList<String>();
        ArrayList<String> isnt_result = new ArrayList<String>();
        boolean bl = has_either = json.has("is") || json.has("isnt");
        if (!has_either) {
            return default_fallback;
        }
        if (json.has("is")) {
            for (JsonElement entry_element : json.get("is").getAsJsonArray()) {
                is_result.add(entry_element.getAsString());
            }
        }
        if (json.has("isnt")) {
            for (JsonElement entry_element : json.get("isnt").getAsJsonArray()) {
                isnt_result.add(entry_element.getAsString());
            }
        }
        return new BiomeCondition(is_result, isnt_result, true);
    }

    public static BiomeCondition get_default_fallback() {
        return new BiomeCondition(new ArrayList<String>(), new ArrayList<String>(), false);
    }
}

