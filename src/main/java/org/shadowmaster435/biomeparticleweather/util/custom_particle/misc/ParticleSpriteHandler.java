/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle.misc;

import java.util.HashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.shadowmaster435.biomeparticleweather.util.Timer;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticle;

public class ParticleSpriteHandler {
    public final HashMap<Integer, TextureAtlasSprite> frames;
    public final int frame_time;
    public final Timer frame_timer;
    public final boolean is_random;

    public ParticleSpriteHandler(HashMap<Integer, TextureAtlasSprite> frames, int frame_time, boolean is_random) {
        this.frames = frames;
        this.frame_time = frame_time;
        this.frame_timer = new Timer();
        this.frame_timer.wait_ticks = frame_time;
        this.frame_timer.repeating = true;
        this.is_random = is_random;
    }

    public void update(CustomParticle particle) {
        if (this.frame_timer.timeout()) {
            particle.frame = this.is_random ? Mth.randomBetweenInclusive((RandomSource)RandomSource.createNewThreadLocalInstance(), (int)0, (int)(this.frames.size() - 1)) : (particle.frame + 1) % this.frames.size();
        }
        this.frame_timer.update();
    }

    public ParticleSpriteHandler duplicate() {
        return new ParticleSpriteHandler(this.frames, this.frame_time, this.is_random);
    }

    public TextureAtlasSprite get_frame(int frame) {
        return this.frames.get(frame);
    }
}

