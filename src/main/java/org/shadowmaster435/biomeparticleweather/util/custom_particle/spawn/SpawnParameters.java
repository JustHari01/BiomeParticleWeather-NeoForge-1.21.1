/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.condition.BiomeCondition;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.condition.BlockAboveCondition;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn.SpawnConditions;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn.SpawnRangeParameters;

public record SpawnParameters(SpawnConditions conditions, SpawnRangeParameters range_parameters) {
    public SpawnParameters(SpawnConditions conditions, SpawnRangeParameters range_parameters) {
        this.conditions = conditions;
        this.range_parameters = range_parameters;
    }

    public static SpawnParameters parse(JsonObject object) {
        SpawnConditions conditions = new SpawnConditions(false, BlockAboveCondition.get_default_fallback(), BiomeCondition.get_default_fallback(), false, false);
        SpawnRangeParameters range_parameters = new SpawnRangeParameters(Vector3.ZERO, Vector3.ZERO);
        if (object.has("spawn_conditions")) {
            conditions = SpawnConditions.parse(object.get("spawn_conditions").getAsJsonObject());
        }
        if (object.has("spawn_range")) {
            range_parameters = SpawnRangeParameters.parse(object.get("spawn_range").getAsJsonObject());
        }
        return new SpawnParameters(conditions, range_parameters);
    }

    public static SpawnParameters get_default_fallback() {
        SpawnConditions conditions = new SpawnConditions(false, BlockAboveCondition.get_default_fallback(), BiomeCondition.get_default_fallback(), false, false);
        SpawnRangeParameters range_parameters = new SpawnRangeParameters(Vector3.ZERO, Vector3.ONE);
        return new SpawnParameters(conditions, range_parameters);
    }
}

