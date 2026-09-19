/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import org.shadowmaster435.biomeparticleweather.model.FireflyModelInstance;
import org.shadowmaster435.biomeparticleweather.model.util.AnimatedModelInstance;
import org.shadowmaster435.biomeparticleweather.model.util.SinglePartAnimatedModel;

public class FireflyModel
extends SinglePartAnimatedModel<FireflyModelInstance> {
    private final ModelPart root = FireflyModel.getTexturedModelData().bakeRoot();
    private final ModelPart body = this.root.getChild("body");
    private final ModelPart left_legs = this.body.getChild("left_legs");
    private final ModelPart right_legs = this.body.getChild("right_legs");
    private final ModelPart left_wing = this.body.getChild("left_wing");
    private final ModelPart right_wing = this.body.getChild("right_wing");
    private final ModelPart antenne = this.body.getChild("antenne");

    public FireflyModel() {
        super(ResourceLocation.fromNamespaceAndPath((String)"biomeparticleweather", (String)"firefly"), ResourceLocation.fromNamespaceAndPath((String)"biomeparticleweather", (String)"textures/particle_model/firefly.png"));
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0f, -4.0f, -4.0f, 4.0f, 4.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition left_legs = body.addOrReplaceChild("left_legs", CubeListBuilder.create().texOffs(0, 5).addBox(0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.0f, (float)0.0f, (float)-3.5f));
        PartDefinition right_legs = body.addOrReplaceChild("right_legs", CubeListBuilder.create().texOffs(0, 5).addBox(0.0f, 0.0f, -3.0f, 0.0f, 2.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-2.0f, (float)0.0f, (float)-0.5f));
        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(13, 5).addBox(0.0f, 0.0f, -3.0f, 4.0f, 0.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.0f, (float)-4.0f, (float)0.0f));
        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(13, 5).mirror().addBox(-4.0f, 0.0f, -3.0f, 4.0f, 0.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset((float)-2.0f, (float)-4.0f, (float)0.0f));
        PartDefinition antenne = body.addOrReplaceChild("antenne", CubeListBuilder.create().texOffs(16, -2).addBox(0.0f, -0.0858f, -3.4142f, 0.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(16, -2).addBox(2.0f, -0.0858f, -3.4142f, 0.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-1.0f, (float)-3.5f, (float)-2.0f, (float)-0.7854f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)modelData, (int)64, (int)64);
    }

    @Override
    public <A extends AnimatedModelInstance> void animateModel(A model, float tickDelta) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}

