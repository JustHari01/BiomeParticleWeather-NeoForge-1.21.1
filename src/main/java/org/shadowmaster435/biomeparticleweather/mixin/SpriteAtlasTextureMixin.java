/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import java.util.Objects;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={TextureAtlas.class})
public abstract class SpriteAtlasTextureMixin {
    @Shadow
    @Final
    @Deprecated
    public static ResourceLocation LOCATION_PARTICLES;

    public SpriteAtlasTextureMixin() {
    }

    @Inject(at={@At(value="TAIL")}, method={"<init>"})
    private void init(ResourceLocation id, CallbackInfo ci) {
        if (Objects.equals(id, LOCATION_PARTICLES)) {
            BiomeParticleWeather.provider = (TextureAtlas)(Object)this;
        }
    }
}
