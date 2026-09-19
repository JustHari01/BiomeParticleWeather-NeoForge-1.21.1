/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.particle;

import java.util.HashMap;
import java.util.function.Predicate;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.shadowmaster435.biomeparticleweather.util.ParticleUtil;
import org.shadowmaster435.biomeparticleweather.util.Timer;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class ParticleBase
extends TextureSheetParticle {
    private HashMap<String, Timer> animations = new HashMap();
    private float previous_alpha = 1.0f;
    private Vector3 previous_rgb = Vector3.ONE;
    private float previous_scale = 1.0f;
    private float target_alpha_fade = 1.0f;
    private Vector3 target_rgb_fade = Vector3.ONE;
    private float target_scale_fade = 1.0f;
    private boolean fading_alpha = false;
    private boolean fading_rgb = false;
    private boolean fading_scale = false;
    public long random_seed = RandomSource.createNewThreadLocalInstance().nextInt();
    public float angular_velocity = 0.0f;
    public float orbit_velocity = 0.0f;
    public FabricSpriteProvider provider;

    public ParticleBase(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos.x, pos.y, pos.z);
        this.provider = spriteProvider;
        this.create_animation_timers();
        this.hasPhysics = false;
    }

    /** Public render-state accessors keep the custom model renderer independent of Mojang access wideners. */
    public double previous_x() { return this.xo; }
    public double position_x() { return this.x; }
    public double previous_y() { return this.yo; }
    public double position_y() { return this.y; }
    public double previous_z() { return this.zo; }
    public double position_z() { return this.z; }
    public net.minecraft.client.renderer.texture.TextureAtlasSprite sprite() { return this.sprite; }
    public float red() { return this.rCol; }
    public float green() { return this.gCol; }
    public float blue() { return this.bCol; }
    public float opacity() { return this.alpha; }
    public float roll_value() { return this.roll; }
    public float u0() { return this.getU0(); }
    public float u1() { return this.getU1(); }
    public float v0() { return this.getV0(); }
    public float v1() { return this.getV1(); }

    private void create_animation_timers() {
        Timer fade_alpha = new Timer();
        Timer fade_scale = new Timer();
        Timer fade_rgb = new Timer();
        this.animations.put("fade_alpha", fade_alpha);
        this.animations.put("fade_scale", fade_scale);
        this.animations.put("fade_rgb", fade_rgb);
    }

    public void fade_alpha(float to, int ticks) {
        this.previous_alpha = this.alpha;
        this.target_alpha_fade = to;
        this.animations.get("fade_alpha").start(ticks);
        this.fading_alpha = true;
    }

    public void fade_rgb(Vector3 to, int ticks) {
        this.previous_rgb = this.get_color();
        this.target_rgb_fade = to;
        this.animations.get("fade_rgb").start(ticks);
        this.fading_rgb = true;
    }

    public void fade_scale(float to, int ticks) {
        this.previous_scale = this.quadSize;
        this.animations.get("fade_scale").start(ticks);
        this.target_scale_fade = to;
        this.fading_scale = true;
    }

    public void process_animations() {
        Timer timer;
        if (this.fading_alpha) {
            timer = this.animations.get("fade_alpha");
            this.alpha = Mth.lerp((float)timer.delta(), (float)this.previous_alpha, (float)this.target_alpha_fade);
            if (timer.is_finished()) {
                this.fading_alpha = false;
            }
            timer.update();
        }
        if (this.fading_rgb) {
            timer = this.animations.get("fade_rgb");
            this.set_color(new Vector3(this.previous_rgb.lerp(this.target_rgb_fade, timer.delta())));
            if (timer.is_finished()) {
                this.fading_rgb = false;
            }
            timer.update();
        }
        if (this.fading_scale) {
            timer = this.animations.get("fade_scale");
            this.quadSize = Mth.lerp((float)timer.delta(), (float)this.previous_scale, (float)this.target_scale_fade);
            if (timer.is_finished()) {
                this.fading_scale = false;
            }
            timer.update();
        }
    }

    public void tick() {
        super.tick();
        this.process_animations();
        this.oRoll = this.roll;
        this.roll += (float)Math.toRadians(this.angular_velocity);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public Vector3 get_color() {
        return new Vector3(this.rCol, this.gCol, this.bCol);
    }

    public void set_color(Vector3 vec) {
        this.setColor((float)vec.x, (float)vec.y, (float)vec.z);
    }

    public void bounce() {
        if (this.is_colliding()) {
            this.set_velocity(this.get_velocity().bounce(this.get_collision_direction()));
        }
    }

    public boolean in_block() {
        return this.level.getBlockState(this.get_pos().to_blockpos()).isRedstoneConductor((BlockGetter)this.level, this.get_pos().to_blockpos());
    }

    public Vector3 get_orbit_tangent(Vector3 point, float gravity_scale) {
        return this.get_pos().angle_to(point, Math.max((double)this.orbit_velocity, 0.01) / Math.max(this.get_pos().distanceTo(point), 0.01));
    }

    public Vector3 get_pull_tangent(Vector3 point, float gravity_scale) {
        Vector3 v = this.get_orbit_tangent(point);
        return v.lerp(point, gravity_scale);
    }

    public Vector3 get_orbit_tangent(Vector3 point) {
        return this.get_pos().angle_to(point, Math.max((double)this.orbit_velocity, 0.01) / Math.max(this.get_pos().distanceTo(point), 0.01));
    }

    public void div_vel(Vector3 scalar) {
        this.set_velocity(this.get_velocity().div(scalar));
    }

    public void mul_vel(Vector3 scalar) {
        this.set_velocity(this.get_velocity().mul(scalar));
    }

    public void set_position(Vector3 pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public void add_position(Vector3 pos) {
        this.x += pos.x;
        this.y += pos.y;
        this.z += pos.z;
    }

    public void orbit(Vector3 pos) {
        Vector3 tangent = this.get_orbit_tangent(pos);
        this.add_position(new Vector3(0.0, tangent.y, tangent.z).mul(this.orbit_velocity));
    }

    public void orbit(Vector3 pos, float distance) {
    }

    public void bounce(Direction direction) {
        this.set_velocity(this.get_velocity().bounce(direction));
    }

    public void bounce(Vector3 offset) {
        if (this.is_colliding(offset)) {
            this.set_velocity(this.get_velocity().bounce(this.get_collision_direction()));
        }
    }

    public void set_velocity(Vector3 vec) {
        this.xd = vec.x;
        this.yd = vec.y;
        this.zd = vec.z;
    }

    public void add_velocity(Vector3 vec) {
        this.xd += vec.x;
        this.yd += vec.y;
        this.zd += vec.z;
    }

    public Direction get_collision_direction() {
        return this.get_hit_result().getDirection();
    }

    public Direction get_collision_direction(Vector3 offset) {
        return this.get_hit_result(offset).getDirection();
    }

    public boolean is_colliding() {
        return this.get_hit_result().getType() != HitResult.Type.MISS;
    }

    public boolean is_colliding(Vector3 offset) {
        return this.get_hit_result(offset).isInside();
    }

    public boolean is_raycast_colliding(Vector3 pos) {
        return this.raycast_to(pos).getType() != HitResult.Type.MISS;
    }

    public BlockHitResult get_fluid_hit_result() {
        ClipBlockStateContext ctx = new ClipBlockStateContext((Vec3)this.get_pos(), (Vec3)this.get_pos(), BlockBehaviour.BlockStateBase::liquid);
        return ParticleUtil.get_world().isBlockInLine(ctx);
    }

    public BlockHitResult get_fluid_hit_result(Vector3 offset) {
        ClipBlockStateContext ctx = new ClipBlockStateContext((Vec3)this.get_pos(), this.get_pos().add(offset), BlockBehaviour.BlockStateBase::liquid);
        return ParticleUtil.get_world().isBlockInLine(ctx);
    }

    public BlockHitResult raycast_to(Vector3 pos) {
        ClipBlockStateContext ctx = new ClipBlockStateContext((Vec3)this.get_pos(), (Vec3)pos, Predicate.not(BlockBehaviour.BlockStateBase::isAir));
        return ParticleUtil.get_world().isBlockInLine(ctx);
    }

    public BlockHitResult get_hit_result(Vector3 offset) {
        ClipBlockStateContext ctx = new ClipBlockStateContext((Vec3)this.get_previous_pos(), this.get_pos().add(offset), BlockBehaviour.BlockStateBase::blocksMotion);
        return ParticleUtil.get_world().isBlockInLine(ctx);
    }

    public BlockHitResult get_hit_result() {
        ClipBlockStateContext ctx = new ClipBlockStateContext((Vec3)this.get_pos(), this.get_pos().add(this.get_velocity_length_vector()), BlockBehaviour.BlockStateBase::isSolid);
        return ParticleUtil.get_world().isBlockInLine(ctx);
    }

    public Vector3 get_velocity_length_vector() {
        double hlength = this.get_velocity().length();
        double vlength = new Vector3(this.get_velocity().x, 0.0, this.get_velocity().z).length();
        return new Vector3(this.get_velocity());
    }

    public Vector3 get_velocity() {
        return new Vector3(this.xd, this.yd, this.zd);
    }

    public Vector3 get_pos() {
        return new Vector3(this.x, this.y, this.z);
    }

    public Vector3 get_previous_pos() {
        return new Vector3(this.xo, this.yo, this.zo);
    }

    public Vector3 get_hit_pos(Vector3 offset) {
        return new Vector3(this.get_hit_result(offset).getLocation());
    }

    public Vector3 get_hit_pos() {
        return new Vector3(this.get_hit_result().getLocation());
    }

    public Vector3 get_hit_fluid_surface() {
        BlockHitResult hit = this.get_fluid_hit_result();
        return new Vector3(hit.getLocation());
    }

    public boolean will_collide_with_fluid() {
        BlockHitResult hit = this.get_fluid_hit_result();
        return hit.isInside();
    }

    public int getLightColor(float tint) {
        return super.getLightColor(tint);
    }
}
