/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.condition.BiomeCondition;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.condition.BlockAboveCondition;

public record SpawnConditions(boolean is_solid, BlockAboveCondition above_condition, BiomeCondition biome_condition, boolean is_raining, boolean is_thundering) {
    public SpawnConditions(boolean is_solid, BlockAboveCondition above_condition, BiomeCondition biome_condition, boolean is_raining, boolean is_thundering) {
        this.is_solid = is_solid;
        this.above_condition = above_condition;
        this.biome_condition = biome_condition;
        this.is_raining = is_raining;
        this.is_thundering = is_thundering;
    }

    public boolean test(Level world, BlockPos pos) {
        boolean biome = this.biome_condition.test((ResourceKey<Biome>)((ResourceKey)world.getBiome(pos).unwrapKey().orElseThrow()));
        boolean solid = !this.is_solid || !world.getBlockState(pos).isRedstoneConductor((BlockGetter)world, pos);
        boolean above = this.above_condition.test(world, pos);
        boolean rain = !this.is_raining || world.isRaining();
        boolean thunder = !this.is_thundering || world.isThundering();
        return biome && solid && above && rain && thunder;
    }

    public static SpawnConditions parse(JsonObject json) {
        boolean solid = true;
        boolean rain = false;
        boolean thunder = false;
        if (json.has("is_solid")) {
            solid = json.get("is_solid").getAsBoolean();
        }
        if (json.has("is_raining")) {
            rain = json.get("is_raining").getAsBoolean();
        }
        if (json.has("is_thundering")) {
            thunder = json.get("is_thundering").getAsBoolean();
        }
        BlockAboveCondition above = new BlockAboveCondition(new ArrayList<Block>(), new ArrayList<Block>(), false);
        if (json.has("below")) {
            above = BlockAboveCondition.parse(json.get("below").getAsJsonObject());
        }
        BiomeCondition biome = new BiomeCondition(new ArrayList<String>(), new ArrayList<String>(), false);
        if (json.has("biome")) {
            biome = BiomeCondition.parse(json.get("biome").getAsJsonObject());
        }
        return new SpawnConditions(solid, above, biome, rain, thunder);
    }
}

