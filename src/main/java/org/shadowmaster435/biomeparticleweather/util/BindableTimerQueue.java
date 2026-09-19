/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.util.BindableTimer;
import org.shadowmaster435.biomeparticleweather.util.Timer;

public class BindableTimerQueue {
    public Timer full_length_timer;
    public HashMap<Integer, BindableTimer> timers = new HashMap();

    public BindableTimerQueue(HashMap<Integer, BindableTimer> timers) {
        this.timers = timers;
    }

    public static BindableTimerQueue from_json(JsonArray array, String field_name) {
        HashMap<Integer, BindableTimer> timers = new HashMap<Integer, BindableTimer>();
        int max_time = -1;
        Timer full_length_timer = new Timer();
        for (JsonElement element : array) {
            int start_time = element.getAsJsonArray().get(0).getAsInt();
            int timer_length = element.getAsJsonArray().get(1).getAsInt();
            int min_value = element.getAsJsonArray().get(2).getAsInt();
            int max_value = element.getAsJsonArray().get(3).getAsInt();
            max_time = Math.max(max_time, timer_length + start_time);
            BindableTimer timer = new BindableTimer(field_name, true, min_value, max_value);
            timer.wait_ticks = timer_length;
            timers.put(start_time, timer);
        }
        full_length_timer.wait_ticks = max_time;
        BindableTimerQueue queue = new BindableTimerQueue(timers);
        queue.full_length_timer = full_length_timer;
        return queue;
    }

    public void update(Object instance) {
        for (int start_time : this.timers.keySet()) {
            BindableTimer timer = this.timers.get(start_time);
            timer.update(instance);
            if (this.full_length_timer.wait_ticks != start_time) continue;
            timer.start();
        }
    }
}

