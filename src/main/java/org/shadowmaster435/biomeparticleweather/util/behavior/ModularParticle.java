/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.behavior;

import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.behavior.ParticleBehavior;

public class ModularParticle
extends ParticleBase {
    public ParticleBehavior behaivor;

    public ModularParticle(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
    }
}

