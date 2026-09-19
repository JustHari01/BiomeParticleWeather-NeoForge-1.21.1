/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import net.minecraft.client.Minecraft;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public class MinecraftClientMixin {
    public MinecraftClientMixin() {
    }

    @Inject(at={@At(value="TAIL")}, method={"tick"})
    private void tick(CallbackInfo ci) {
        ParticleEngine.try_spawn_particles();
    }
}

