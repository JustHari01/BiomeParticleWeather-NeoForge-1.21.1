package org.shadowmaster435.biomeparticleweather.mixin;

import net.minecraft.client.renderer.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Accesses the atlas dimensions which are package-private in vanilla 1.21.1. */
@Mixin(TextureAtlas.class)
public interface TextureAtlasAccessor {
    @Accessor("width")
    int biomeParticleWeather$getWidth();

    @Accessor("height")
    int biomeParticleWeather$getHeight();
}
