/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.shadowmaster435.biomeparticleweather.util.Timer;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class TrailParticleBase
extends ParticleBase {
    public ParticleOptions effect;
    public Timer interval_timer = new Timer();
    public int trail_density = 1;
    public int spawn_interval = 1;

    public TrailParticleBase(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        this.update_timer();
    }

    public void update_timer() {
        if (this.effect != null) {
            if (this.spawn_interval <= 1) {
                ParticleEngine.spawn_particle(this.effect, this.get_pos());
                if (this.trail_density > 0) {
                    this.spawn_trail_detail();
                }
            } else {
                if (this.interval_timer.is_finished()) {
                    ParticleEngine.spawn_particle(this.effect, this.get_pos());
                    if (this.trail_density > 0) {
                        this.spawn_trail_detail();
                    }
                    this.interval_timer.start(this.spawn_interval);
                }
                this.interval_timer.update();
            }
        }
    }

    public void spawn_trail_detail() {
        for (int i = 0; i < this.trail_density; ++i) {
            float delta = (float)(i + 1) / (float)(this.trail_density + 1);
            ParticleEngine.spawn_particle(this.effect, this.get_pos().lerp(this.get_previous_pos(), 1.0 - (double)delta));
        }
    }
}

