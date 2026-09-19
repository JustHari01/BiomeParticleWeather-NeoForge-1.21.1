/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public record BehaviorSetter(String key) {
    public BehaviorSetter(String key) {
        this.key = key;
    }

    public void set(ParticleBehavior behavior, Object value) {
        behavior.set(this.key, value);
    }
}

