/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.shadowmaster435.biomeparticleweather.model.FireflyModelInstance;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class Firefly
extends ParticleBase {
    private FireflyModelInstance modelInstance;
    public double expVelX = 0.0;
    public double expVelY = 0.0;
    public double expVelZ = 0.0;
    public double prevVelX = 0.0;
    public double prevVelY = 0.0;
    public double prevVelZ = 0.0;

    public Firefly(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 400;
        this.quadSize = 0.25f;
        this.xd = Math.random() * 0.085 - 0.0425;
        this.yd = 0.025;
        this.zd = Math.random() * 0.085 - 0.0425;
        this.modelInstance = new FireflyModelInstance();
        this.alpha = 0.0f;
    }

    public static float angleToPoint(Vector2f a, Vector2f b) {
        Vector2f vec = new Vector2f((Vector2fc)b).sub((Vector2fc)a);
        return (float)Math.atan2(vec.y, vec.x);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        PoseStack mat = new PoseStack();
        Vec3 vec3d = camera.getPosition();
        float drawx = (float)(Mth.lerp((double)tickDelta, (double)this.xo, (double)this.x) - vec3d.x());
        float drawy = (float)(Mth.lerp((double)tickDelta, (double)this.yo, (double)this.y) - vec3d.y());
        float drawz = (float)(Mth.lerp((double)tickDelta, (double)this.zo, (double)this.z) - vec3d.z());
        mat.translate(drawx, drawy, drawz);
        Matrix4f modelMat = new Matrix4f((Matrix4fc)RenderSystem.getModelViewMatrix());
        modelMat.rotate((Quaternionfc)camera.rotation());
        modelMat.rotateX((float)Math.toRadians(180.0));
        float scale = 0.125f;
        float lerpRotX = (float)Mth.lerp((double)tickDelta, (double)this.prevVelX, (double)this.expVelX);
        float lerpRotY = (float)Mth.lerp((double)tickDelta, (double)this.prevVelY, (double)this.expVelY);
        float lerpRotZ = (float)Mth.lerp((double)tickDelta, (double)this.prevVelZ, (double)this.expVelZ);
        modelMat.scale(scale);
        Vector2d hUnitVector = new Vector2d((double)lerpRotX, (double)lerpRotZ).normalize();
        float ang = (float)Math.atan2(hUnitVector.y, hUnitVector.x);
        mat.mulPose(modelMat);
        mat.mulPose(Axis.YP.rotation(ang - 90.0f));
        mat.mulPose(Axis.XN.rotation(lerpRotY * 32.0f));
        mat.translate(0.0f, -1.375f, 0.0f);
        this.modelInstance.render(this.age, mat, this.sprite.wrap(vertexConsumer), tickDelta, this.alpha);
    }

    public void pathfind() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.prevVelX = this.expVelX;
        this.prevVelY = this.expVelY;
        this.prevVelZ = this.expVelZ;
        if ((this.onGround || this.level.getDayTime() >= 22000L || this.level.getDayTime() <= 14000L || this.level.getFluidState(new BlockPos((int)this.x, (int)this.y, (int)this.z)).is(FluidTags.WATER) || this.level.getFluidState(new BlockPos((int)this.x, (int)this.y, (int)this.z)).is(FluidTags.LAVA)) && this.level.getFluidState(new BlockPos((int)this.x, (int)this.y, (int)this.z)).is(FluidTags.LAVA)) {
            this.level.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, (double)((int)this.x), (double)((int)this.y), (double)((int)this.z), 0.0, 0.5, 0.0);
        }
        if (this.age % 100 == 0 && Math.random() < 0.75) {
            this.xd = this.random.nextGaussian() * 0.05;
            this.yd = this.random.nextGaussian() * 0.05;
            this.zd = this.random.nextGaussian() * 0.05;
        }
        if (this.age % 50 == 0 && Math.random() < 0.75) {
            this.xd += this.random.nextGaussian() * 0.05;
            this.yd += this.random.nextGaussian() * 0.05;
            this.zd += this.random.nextGaussian() * 0.05;
        }
        if (this.level.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z)).isRedstoneConductor((BlockGetter)this.level, new BlockPos((int)this.x, (int)this.y, (int)this.z))) {
            this.remove();
        }
        float veldiv = 0.95f;
        this.xd *= (double)veldiv;
        this.yd *= (double)veldiv;
        this.zd *= (double)veldiv;
        float maxvel = 0.025f;
        for (Direction dir : Direction.values()) {
            double inverter;
            if (this.level.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z).relative(dir)).isAir()) continue;
            if (dir.equals((Object)Direction.WEST)) {
                inverter = (double)maxvel * 0.1 * -1.0;
                this.expVelX -= inverter;
            }
            if (dir == Direction.EAST) {
                inverter = (double)maxvel * 0.1 * -1.0;
                this.expVelX += inverter;
            }
            if (dir.equals((Object)Direction.DOWN)) {
                inverter = (double)maxvel * 0.1 * -1.0;
                this.expVelY -= inverter;
            }
            if (dir == Direction.UP) {
                inverter = (double)maxvel * 0.1 * -1.0;
                this.expVelY += inverter;
            }
            if (dir.equals((Object)Direction.NORTH)) {
                inverter = (double)maxvel * 0.1 * -1.0;
                this.expVelZ -= inverter;
            }
            if (dir != Direction.SOUTH) continue;
            inverter = (double)maxvel * 0.1 * -1.0;
            this.expVelZ += inverter;
        }
        if (this.expVelX != this.xd) {
            this.expVelX += this.xd * 0.05;
        }
        if (this.expVelY != this.yd) {
            this.expVelY += this.yd * 0.05;
        }
        if (this.expVelZ != this.zd) {
            this.expVelZ += this.zd * 0.05;
        }
        if (this.xd > (double)maxvel) {
            this.xd = maxvel;
        }
        if (this.yd > (double)maxvel) {
            this.yd = maxvel;
        }
        if (this.zd > (double)maxvel) {
            this.zd = maxvel;
        }
        if (this.xd < (double)(-maxvel)) {
            this.xd = -maxvel;
        }
        if (this.yd < (double)(-maxvel)) {
            this.yd = -maxvel;
        }
        if (this.zd < (double)(-maxvel)) {
            this.zd = -maxvel;
        }
        if (this.expVelX > (double)maxvel) {
            this.expVelX = maxvel;
        }
        if (this.expVelY > (double)maxvel) {
            this.expVelY = maxvel;
        }
        if (this.expVelZ > (double)maxvel) {
            this.expVelZ = maxvel;
        }
        if (this.expVelX < (double)(-maxvel)) {
            this.expVelX = -maxvel;
        }
        if (this.expVelY < (double)(-maxvel)) {
            this.expVelY = -maxvel;
        }
        if (this.expVelZ < (double)(-maxvel)) {
            this.expVelZ = -maxvel;
        }
        this.move(this.expVelX, this.expVelY, this.expVelZ);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age == 1) {
            this.fade_alpha(1.0f, 20);
        }
        if (this.age > this.lifetime - 19) {
            this.alpha = 1.0f - (float)this.age / 20.0f;
        }
        this.pathfind();
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory
    implements ParticleProvider<SimpleParticleType> {
        private final FabricSpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Firefly particle = new Firefly(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

