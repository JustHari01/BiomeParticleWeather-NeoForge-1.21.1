/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import org.shadowmaster435.biomeparticleweather.util.ParticleUtil;

public class Timer {
    private long start_tick = 0L;
    private int ticks_left = 0;
    public int wait_ticks;
    private boolean finished = false;
    public boolean repeating = false;

    public Timer() {
    }

    public Timer(int ticks) {
        this.wait_ticks = ticks;
    }

    public Timer(int ticks, boolean repeat) {
        this.wait_ticks = ticks;
        this.repeating = repeat;
    }

    public boolean timeout() {
        return this.ticks_left <= 0;
    }

    public void update() {
        if (this.timeout()) {
            if (this.repeating) {
                this.start(this.wait_ticks);
            } else {
                this.finished = true;
            }
        } else {
            --this.ticks_left;
        }
    }

    public float delta() {
        if (this.wait_ticks > 0 && this.ticks_left > 0) {
            return 1.0f - (float)this.ticks_left / (float)this.wait_ticks;
        }
        return 1.0f;
    }

    public boolean is_finished() {
        return this.finished;
    }

    public void start(int ticks) {
        if (ParticleUtil.get_world() != null) {
            this.start_tick = ParticleUtil.get_world().getGameTime();
            this.ticks_left = ticks;
            this.wait_ticks = ticks;
        }
    }

    public void start() {
        if (ParticleUtil.get_world() != null) {
            this.start_tick = ParticleUtil.get_world().getGameTime();
            this.wait_ticks = this.ticks_left = this.wait_ticks;
        }
    }
}

