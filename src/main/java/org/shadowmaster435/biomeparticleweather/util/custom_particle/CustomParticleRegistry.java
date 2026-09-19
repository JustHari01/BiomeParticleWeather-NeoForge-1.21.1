/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle;

import com.google.gson.JsonObject;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleBehavior;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParticleSpriteHandler;

public class CustomParticleRegistry {
    public static HashMap<String, CustomParticleBehavior> behaviors = new HashMap();
    public static HashMap<String, ParticleSpriteHandler> sprites = new HashMap();

    public CustomParticleRegistry() {
    }

    public static void register(String file_name, JsonObject json) {
        String[] name_arr = file_name.replace(".json", "").split("/");
        String name = name_arr[name_arr.length - 1];
        CustomParticleBehavior behavior = CustomParticleBehavior.parse(name, json);
        behaviors.put(name, behavior);
    }
}

