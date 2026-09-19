/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.world.entity.LightningBolt;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LightningBoltRenderer.class})
public class LightningEntityRendererMixin {
    public LightningEntityRendererMixin() {
    }

    @Inject(at={@At(value="HEAD")}, method={"render"}, cancellable=true)
    private void render(LightningBolt lightningEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!ParticleSettings.get_bool("vanilla_lightning")) {
            ci.cancel();
        }
    }
}
