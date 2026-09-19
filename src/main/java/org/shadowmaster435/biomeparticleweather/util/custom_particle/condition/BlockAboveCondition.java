/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public record BlockAboveCondition(ArrayList<Block> is, ArrayList<Block> isnt, boolean used) {
    public BlockAboveCondition(ArrayList<Block> is, ArrayList<Block> isnt, boolean used) {
        this.is = is;
        this.isnt = isnt;
        this.used = used;
    }

    public boolean test(Level world, BlockPos pos) {
        if (!this.used) {
            return true;
        }
        Block block = world.getBlockState(pos.above()).getBlock();
        boolean matches_is = this.is.stream().toList().contains(block);
        if (this.is.isEmpty()) {
            matches_is = true;
        }
        boolean matches_isnt = this.isnt.stream().toList().contains(block);
        return matches_is && !matches_isnt;
    }

    public static BlockAboveCondition parse(JsonObject json) {
        Block block;
        String entry;
        boolean has_either;
        BlockAboveCondition default_fallback = new BlockAboveCondition(new ArrayList<Block>(), new ArrayList<Block>(), false);
        ArrayList<Block> is_result = new ArrayList<Block>();
        ArrayList<Block> isnt_result = new ArrayList<Block>();
        boolean bl = has_either = json.has("is") || json.has("isnt");
        if (!has_either) {
            return default_fallback;
        }
        if (json.has("is")) {
            for (JsonElement entry_element : json.get("is").getAsJsonArray()) {
                entry = entry_element.getAsString();
                block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse((String)entry));
                is_result.add(block);
            }
        }
        if (json.has("isnt")) {
            for (JsonElement entry_element : json.get("isnt").getAsJsonArray()) {
                entry = entry_element.getAsString();
                block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse((String)entry));
                isnt_result.add(block);
            }
        }
        return new BlockAboveCondition(is_result, isnt_result, true);
    }

    public static BlockAboveCondition get_default_fallback() {
        return new BlockAboveCondition(new ArrayList<Block>(), new ArrayList<Block>(), false);
    }
}

