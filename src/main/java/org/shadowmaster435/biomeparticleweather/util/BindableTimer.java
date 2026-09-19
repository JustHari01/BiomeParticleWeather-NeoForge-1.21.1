/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import net.minecraft.util.Mth;
import org.shadowmaster435.biomeparticleweather.util.Timer;

public class BindableTimer
extends Timer {
    private final String field_name;
    private final boolean invert_delta;
    private final float min_value;
    private final float max_value;
    private boolean started = false;

    public BindableTimer(String field_name) {
        this.field_name = field_name;
        this.invert_delta = false;
        this.min_value = 0.0f;
        this.max_value = 1.0f;
    }

    public BindableTimer(String field_name, boolean invert_delta) {
        this.field_name = field_name;
        this.invert_delta = invert_delta;
        this.min_value = 0.0f;
        this.max_value = 1.0f;
    }

    public BindableTimer(String field_name, boolean invert_delta, float min_value, float max_value) {
        this.field_name = field_name;
        this.invert_delta = invert_delta;
        this.min_value = min_value;
        this.max_value = max_value;
    }

    public void update(Object bound_instance) {
        if (this.started) {
            super.update();
        }
        try {
            float delta = this.invert_delta ? 1.0f - this.delta() : this.delta();
            float val = Mth.lerp((float)delta, (float)this.min_value, (float)this.max_value);
            bound_instance.getClass().getField(this.field_name).setFloat(bound_instance, val);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start() {
        super.start();
        this.started = true;
    }
}

