/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import org.shadowmaster435.biomeparticleweather.compat.PauseScreenAccess;
import org.shadowmaster435.biomeparticleweather.util.StaticMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PauseScreen.class})
public abstract class GameMenuScreenMixin implements PauseScreenAccess {
    public GameMenuScreenMixin() {
    }

    @Inject(at={@At(value="TAIL")}, method={"init"})
    public void initWigits(CallbackInfo ci) {
        StaticMethods.init_config((PauseScreen)(Object)this);
    }

    @Override
    public void biomeParticleWeather$addWidget(AbstractWidget widget) {
        ((ScreenAccessor)(Object)this).biomeParticleWeather$addRenderableWidget(widget);
    }
}
