/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.shadowmaster435.biomeparticleweather.model.util.AnimatedModelInstance;
import org.shadowmaster435.biomeparticleweather.model.util.SinglePartAnimatedModel;

public interface AnimatedModelRenderer<T extends AnimatedModelInstance> {
    default public void render(T model_instance, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        SinglePartAnimatedModel<? extends AnimatedModelInstance> model = ((AnimatedModelInstance)model_instance).getModel();
        AnimationDefinition anim = ((AnimatedModelInstance)model_instance).get_current_animation();
        if (model != null) {
            matrices.scale(1.0f, -1.0f, 1.0f);
            matrices.translate(0.5, -1.5, 0.5);
            model.render(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull((ResourceLocation)model.get_texture())), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            if (anim != null) {
                model.updateAnimation(((AnimatedModelInstance)model_instance).animation_state, ((AnimatedModelInstance)model_instance).get_current_animation(), (float)((AnimatedModelInstance)model_instance).get_animation_counter() + tickDelta);
            }
        }
    }
}

