/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.shadowmaster435.biomeparticleweather.util.StaticMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LightningBolt.class})
public class LightningEntityMixin {
    public LightningEntityMixin() {
    }

    @Inject(at={@At(value="TAIL")}, method={"tick"})
    private void tick(CallbackInfo ci) {
        if (!ParticleSettings.get_bool("vanilla_lightning")) {
            StaticMethods.spawn_particles((Entity)(Object)this);
        }
    }
}
