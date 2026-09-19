/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.model.util.AnimatedModelInstance;

public abstract class AnimatedModel<T extends AnimatedModelInstance>
extends Model {
    public boolean child;
    private ResourceLocation texture;

    protected AnimatedModel(ResourceLocation id, @Nullable ResourceLocation texture) {
        this(RenderType::entityCutoutNoCull, id, texture);
    }

    protected AnimatedModel(Function<ResourceLocation, RenderType> function, ResourceLocation id, @Nullable ResourceLocation texture) {
        super(function);
        this.texture = Objects.requireNonNullElse(texture, id);
    }

    public abstract <A extends AnimatedModelInstance> void animateModel(A var1, float var2);

    public ResourceLocation get_texture() {
        return this.texture;
    }

    public void set_texture(ResourceLocation texture) {
        this.texture = texture;
    }

    public void copyStateTo(AnimatedModel<T> copy) {
        copy.child = this.child;
    }

    public abstract void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5);
}

