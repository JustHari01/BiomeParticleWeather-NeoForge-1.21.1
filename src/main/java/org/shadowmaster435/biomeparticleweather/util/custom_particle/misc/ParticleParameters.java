/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.misc;

import com.google.gson.JsonObject;

public record ParticleParameters(boolean remove_on_ground, boolean collides_with_world, int max_age, float horizontal_size, float vertical_size, int amount) {
    public ParticleParameters(boolean remove_on_ground, boolean collides_with_world, int max_age, float horizontal_size, float vertical_size, int amount) {
        this.remove_on_ground = remove_on_ground;
        this.collides_with_world = collides_with_world;
        this.max_age = max_age;
        this.horizontal_size = horizontal_size;
        this.vertical_size = vertical_size;
        this.amount = amount;
    }

    public static ParticleParameters parse(JsonObject json) {
        boolean remove = true;
        boolean collide = true;
        int age = 1;
        float h_size = 0.05f;
        float v_size = 0.05f;
        int amount = 1;
        if (json.has("remove_on_ground")) {
            remove = json.get("remove_on_ground").getAsBoolean();
        }
        if (json.has("collides_with_world")) {
            collide = json.get("collides_with_world").getAsBoolean();
        }
        if (json.has("max_age")) {
            age = json.get("max_age").getAsInt();
        }
        if (json.has("horizontal_size")) {
            h_size = json.get("horizontal_size").getAsFloat();
        }
        if (json.has("vertical_size")) {
            v_size = json.get("vertical_size").getAsFloat();
        }
        if (json.has("amount")) {
            amount = json.get("amount").getAsInt();
        }
        return new ParticleParameters(remove, collide, age, h_size, v_size, amount);
    }

    public static ParticleParameters get_default_fallback() {
        return new ParticleParameters(true, true, 1, 0.05f, 0.05f, 1);
    }
}

