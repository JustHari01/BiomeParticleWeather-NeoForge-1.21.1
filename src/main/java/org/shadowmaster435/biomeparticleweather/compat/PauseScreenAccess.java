package org.shadowmaster435.biomeparticleweather.compat;

import net.minecraft.client.gui.components.AbstractWidget;

/** Runtime bridge implemented by the pause-screen mixin. */
public interface PauseScreenAccess {
    void biomeParticleWeather$addWidget(AbstractWidget widget);
}
