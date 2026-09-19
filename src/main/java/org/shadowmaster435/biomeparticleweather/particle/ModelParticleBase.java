/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.ModResourceLoader;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleModelPart;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;

public class ModelParticleBase
extends ParticleBase {
    private HashMap<String, ParticleModelPart> model = new HashMap();
    public VoxelTransform transform = VoxelTransform.def();

    public ModelParticleBase(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
    }

    public void load_texture_model(String name) {
        this.model.put("model", ModResourceLoader.models.get(name));
    }

    public void set_model(HashMap<String, ParticleModelPart> model) {
        this.model = model;
    }

    public void set_model(String[] keys, ParticleModelPart[] parts) {
        for (int i = 0; i < keys.length; ++i) {
            this.model.put(keys[i], parts[i]);
        }
    }

    public void rotate(Vector3 amount_degrees) {
        this.transform = new VoxelTransform(this.transform.position(), this.transform.scale(), new Vector3(this.transform.rotation().add(amount_degrees.deg_to_rad())), this.transform.origin());
    }

    public void set_rotation(Vector3 amount_degrees) {
        this.transform = new VoxelTransform(this.transform.position(), this.transform.scale(), amount_degrees, this.transform.origin());
    }

    public void rescale(Vector3 amount) {
        this.transform = new VoxelTransform(this.transform.position(), amount, this.transform.rotation(), this.transform.origin());
    }

    public void offset(Vector3 amount) {
        this.transform = new VoxelTransform(amount, this.transform.scale(), this.transform.rotation(), this.transform.origin());
    }

    public HashMap<String, ParticleModelPart> get_model() {
        return this.model;
    }

    public VoxelTransform get_transform() {
        return this.transform;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        for (ParticleModelPart part : this.model.values()) {
            part.render(vertexConsumer, camera, tickDelta, this, this.transform);
        }
    }
}

