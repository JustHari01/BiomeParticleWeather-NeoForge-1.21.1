/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations;

import com.google.gson.JsonObject;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.AdditiveAccelerations;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.ConstantAccelerations;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.DampingAccelerations;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.InitialRandomAccelerations;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.accelerations.TrigAccelerations;

public record Accelerations(ConstantAccelerations constant, AdditiveAccelerations additive, DampingAccelerations damping, TrigAccelerations trig, InitialRandomAccelerations random_accelerations) {
    public Accelerations(ConstantAccelerations constant, AdditiveAccelerations additive, DampingAccelerations damping, TrigAccelerations trig, InitialRandomAccelerations random_accelerations) {
        this.constant = constant;
        this.additive = additive;
        this.damping = damping;
        this.trig = trig;
        this.random_accelerations = random_accelerations;
    }

    public static Accelerations parse(JsonObject object) {
        JsonObject obj;
        ConstantAccelerations constant = new ConstantAccelerations(Vector3.ZERO, 0.0f);
        AdditiveAccelerations additive = new AdditiveAccelerations(Vector3.ZERO, 0.0f);
        DampingAccelerations damping = new DampingAccelerations(Vector3.ZERO, 1.0f);
        TrigAccelerations trig = new TrigAccelerations(Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO);
        InitialRandomAccelerations initial_random = new InitialRandomAccelerations(Vector3.ZERO, Vector3.ZERO, 0.0f, 0.0f);
        if (object.has("constant")) {
            obj = object.get("constant").getAsJsonObject();
            constant = ConstantAccelerations.parse(obj);
        }
        if (object.has("additive")) {
            obj = object.get("additive").getAsJsonObject();
            additive = AdditiveAccelerations.parse(obj);
        }
        if (object.has("trig")) {
            obj = object.get("trig").getAsJsonObject();
            trig = TrigAccelerations.parse(obj);
        }
        if (object.has("damping_forces")) {
            obj = object.get("damping_forces").getAsJsonObject();
            damping = DampingAccelerations.parse(obj);
        }
        if (object.has("initial_random")) {
            obj = object.get("initial_random").getAsJsonObject();
            initial_random = InitialRandomAccelerations.parse(obj);
        }
        return new Accelerations(constant, additive, damping, trig, initial_random);
    }

    public static Accelerations get_default_fallback() {
        ConstantAccelerations constant = new ConstantAccelerations(Vector3.ZERO, 0.0f);
        AdditiveAccelerations additive = new AdditiveAccelerations(Vector3.ZERO, 0.0f);
        TrigAccelerations trig = new TrigAccelerations(Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO);
        DampingAccelerations damping = new DampingAccelerations(Vector3.ONE, 1.0f);
        InitialRandomAccelerations initial_random = new InitialRandomAccelerations(Vector3.ZERO, Vector3.ZERO, 0.0f, 0.0f);
        return new Accelerations(constant, additive, damping, trig, initial_random);
    }
}

