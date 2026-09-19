/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleRegistry;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.Accelerations;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParticleParameters;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParticleSpriteHandler;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.spawn.SpawnParameters;

public record CustomParticleBehavior(String name, String type, Accelerations accelerations, SpawnParameters parameters, ParticleParameters particle_parameters) {
    public CustomParticleBehavior(String name, String type, Accelerations accelerations, SpawnParameters parameters, ParticleParameters particle_parameters) {
        this.name = name;
        this.type = type;
        this.accelerations = accelerations;
        this.parameters = parameters;
        this.particle_parameters = particle_parameters;
    }

    public static CustomParticleBehavior parse(String name, JsonObject object) {
        String type = object.get("type").getAsString();
        Accelerations accelerations = Accelerations.get_default_fallback();
        SpawnParameters parameters = SpawnParameters.get_default_fallback();
        ParticleParameters particle_parameters = ParticleParameters.get_default_fallback();
        JsonObject sprite = object.get("sprite").getAsJsonObject();
        if (object.has("accelerations")) {
            accelerations = Accelerations.parse(object.get("accelerations").getAsJsonObject());
        }
        if (object.has("spawn_parameters")) {
            parameters = SpawnParameters.parse(object.get("spawn_parameters").getAsJsonObject());
        }
        if (object.has("particle_parameters")) {
            particle_parameters = ParticleParameters.parse(object.get("particle_parameters").getAsJsonObject());
        }
        JsonArray arr = sprite.get("frames").getAsJsonArray();
        HashMap<Integer, TextureAtlasSprite> frame_map = new HashMap<Integer, TextureAtlasSprite>();
        boolean is_random = false;
        if (sprite.has("random")) {
            is_random = sprite.get("random").getAsBoolean();
        }
        int frame_time = 1;
        if (sprite.has("frame_time")) {
            frame_time = Math.max(1, sprite.get("frame_time").getAsInt());
        }
        for (int i = 0; i < arr.size(); ++i) {
            JsonElement element = arr.get(i);
            String path = element.getAsString();
            frame_map.put(i, BiomeParticleWeather.provider.getSprite(ResourceLocation.parse((String)path)));
        }
        CustomParticleRegistry.sprites.put(name, new ParticleSpriteHandler(frame_map, frame_time, is_random));
        return new CustomParticleBehavior(name, type, accelerations, parameters, particle_parameters);
    }
}

