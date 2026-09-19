/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model.util;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.AnimationState;
import org.shadowmaster435.biomeparticleweather.model.util.SinglePartAnimatedModel;

public class AnimatedModelInstance {
    private SinglePartAnimatedModel<? extends AnimatedModelInstance> model;
    private long animation_counter = 0L;
    public AnimationState animation_state = new AnimationState();
    private AnimationDefinition current_animation;

    public AnimatedModelInstance() {
    }

    public AnimationDefinition get_current_animation() {
        return this.current_animation;
    }

    public long get_animation_counter() {
        return this.animation_counter;
    }

    public void set_animation_counter(long value) {
        this.animation_counter = value;
    }

    public void set_current_animation(AnimationDefinition animation) {
        this.current_animation = animation;
    }

    public void setModel(SinglePartAnimatedModel<? extends AnimatedModelInstance> model) {
        this.model = model;
    }

    public SinglePartAnimatedModel<? extends AnimatedModelInstance> getModel() {
        return this.model;
    }
}

