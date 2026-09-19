/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.model.util.AnimatedModel;
import org.shadowmaster435.biomeparticleweather.model.util.AnimatedModelInstance;
import org.shadowmaster435.biomeparticleweather.model.util.ModAnimationHelper;

public abstract class SinglePartAnimatedModel<E extends AnimatedModelInstance>
extends AnimatedModel<E> {
    private static final Vector3f TEMP = new Vector3f();

    public SinglePartAnimatedModel(ResourceLocation id, ResourceLocation texture) {
        this(RenderType::entityTranslucentCull, id, texture);
    }

    public SinglePartAnimatedModel(Function<ResourceLocation, RenderType> function, ResourceLocation id, ResourceLocation texture) {
        super(function, id, texture);
    }

    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertices, light, overlay);
    }

    public abstract ModelPart getPart();

    public Optional<ModelPart> getChild(String name) {
        return name.equals("root") ? Optional.of(this.getPart()) : this.getPart().getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
    }

    public void updateAnimation(AnimationState animationState, AnimationDefinition animation, float animationProgress) {
        this.updateAnimation(animationState, animation, animationProgress, 1.0f);
    }

    protected void animateMovement(AnimationDefinition animation, float limbAngle, float limbDistance, float limbAngleScale, float limbDistanceScale) {
        long l = (long)(limbAngle * 50.0f * limbAngleScale);
        float f = Math.min(limbDistance * limbDistanceScale, 1.0f);
        ModAnimationHelper.animate(this, animation, l, f, TEMP);
    }

    protected void updateAnimation(AnimationState animationState, AnimationDefinition animation, float animationProgress, float speedMultiplier) {
        animationState.updateTime(animationProgress, 1.0f);
        animationState.ifStarted(state -> ModAnimationHelper.animate(this, animation, state.getAccumulatedTime(), 1.0f, TEMP));
    }

    public void animate(AnimationDefinition animation, long run_time) {
        ModAnimationHelper.animate(this, animation, run_time, 1.0f, TEMP);
    }
}

