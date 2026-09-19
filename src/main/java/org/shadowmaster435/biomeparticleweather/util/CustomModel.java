/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class CustomModel
extends EntityModel<Entity> {
    public static final AnimationDefinition ANIMATION = AnimationDefinition.Builder.withLength((float)1.0f).addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe[]{new Keyframe(0.0f, KeyframeAnimations.degreeVec((float)0.0f, (float)0.0f, (float)0.0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.0f, KeyframeAnimations.degreeVec((float)0.0f, (float)360.0f, (float)0.0f), AnimationChannel.Interpolations.LINEAR)})).build();
    private final ModelPart bb_main;

    public CustomModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)modelData, (int)16, (int)16);
    }

    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.bb_main.render(matrices, vertices, light, overlay, color);
    }
}

