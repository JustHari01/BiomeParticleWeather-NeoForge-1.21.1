/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.util.function.Consumer;

public class FloatifiedForLoop {
    private final Consumer<Integer> function;
    private final float iterations;
    private int iteration = 0;
    public int sub_iteration_success_count = 0;

    public FloatifiedForLoop(Consumer<Integer> function, float iterations) {
        this.function = function;
        this.iterations = iterations;
    }

    public static void run(Consumer<Integer> function, float iterations) {
        FloatifiedForLoop loop = new FloatifiedForLoop(function, iterations);
        loop.run();
    }

    public void run() {
        if (this.iterations < 1.0f) {
            double rand = Math.random();
            if (rand <= (double)this.iterations) {
                this.function.accept(1);
            }
        } else {
            int i = 0;
            while ((double)i < Math.ceil(this.iterations)) {
                if (this.iterations - (float)i < 0.0f) {
                    if (Math.random() > (double)this.iterations % 1.0) {
                        ++this.sub_iteration_success_count;
                        ++this.iteration;
                        this.function.accept(this.iteration);
                    }
                } else {
                    ++this.iteration;
                    this.function.accept(this.iteration);
                }
                ++i;
            }
        }
    }
}

