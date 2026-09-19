/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.custom_particle;

import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.biomeparticleweather.particle.ParticleBase;
import org.shadowmaster435.biomeparticleweather.util.Vector3;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleBehavior;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleRegistry;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.misc.ParticleSpriteHandler;

public class CustomParticle
extends ParticleBase {
    public int frame = 0;
    private int last_frame = 0;
    public String particle_name = "missing";
    public CustomParticleBehavior behavior;
    public Vector3 random_cardinal_velocity = Vector3.ZERO;
    public float random_angular_velocity = 0.0f;
    public ParticleSpriteHandler sprite_handler;

    public CustomParticle(ClientLevel world, Vector3 pos, FabricSpriteProvider spriteProvider) {
        super(world, pos, spriteProvider);
        this.lifetime = 10000;
    }

    public void init() {
        this.behavior = CustomParticleRegistry.behaviors.getOrDefault(this.particle_name, null);
        this.random_cardinal_velocity = this.behavior.accelerations().random_accelerations().sample_cardinal();
        this.angular_velocity += this.behavior.accelerations().constant().angular();
        this.random_angular_velocity += this.behavior.accelerations().random_accelerations().sample_angular();
        this.hasPhysics = this.behavior.particle_parameters().collides_with_world();
        this.lifetime = this.behavior.particle_parameters().max_age();
        this.setSize(this.behavior.particle_parameters().horizontal_size(), this.behavior.particle_parameters().vertical_size());
        this.sprite_handler = CustomParticleRegistry.sprites.get(this.particle_name).duplicate();
        this.quadSize = 0.125f;
        this.setSprite(this.sprite_handler.get_frame(0));
        this.alpha = 1.0f;
    }

    @Override
    public void tick() {
        super.tick();
        try {
            Vector3 constant_cardinal = this.behavior.accelerations().constant().cardinal();
            Vector3 trig_cardinal = new Vector3(this.get_sine().add(this.get_cosine()));
            Vector3 additive_cardinal = this.behavior.accelerations().additive().cardinal().mul(this.age);
            Vector3 damping_cardinal = this.behavior.accelerations().damping().cardinal_vel();
            float constant_angular = this.behavior.accelerations().constant().angular();
            float additive_angular = this.behavior.accelerations().additive().angular() * (float)this.age;
            float damping_angular = this.behavior.accelerations().damping().angular_vel();
            Vector3 damped_cardinal = damping_cardinal.div(this.age + 1);
            float damped_angular = damping_angular / (float)(this.age + 1);
            if (damping_angular == 0.0f) {
                damped_angular = 1.0f;
            }
            if (damping_cardinal.x == 0.0) {
                damped_cardinal = damped_cardinal.with_x(1.0);
            }
            if (damping_cardinal.y == 0.0) {
                damped_cardinal = damped_cardinal.with_y(1.0);
            }
            if (damping_cardinal.z == 0.0) {
                damped_cardinal = damped_cardinal.with_z(1.0);
            }
            this.set_velocity(new Vector3(new Vector3(constant_cardinal.add(this.random_cardinal_velocity).add((Vec3)trig_cardinal).add((Vec3)additive_cardinal)).multiply(damped_cardinal)));
            this.angular_velocity = (constant_angular + this.random_angular_velocity + additive_angular) * damped_angular;
            this.sprite_handler.update(this);
            if (this.last_frame != this.frame) {
                this.setSprite(this.sprite_handler.get_frame(this.frame));
                this.last_frame = this.frame;
            }
            if (this.behavior.particle_parameters().remove_on_ground() && this.onGround || this.age >= this.lifetime) {
                this.remove();
            }
        }
        catch (Exception ignored) {
            this.remove();
            System.out.println("null particle");
        }
    }

    public Vector3 get_sine() {
        Vector3 amp = this.behavior.accelerations().trig().sine_amplitude();
        Vector3 spd = this.behavior.accelerations().trig().sine_speed();
        double x = Math.sin((double)this.age * spd.x) * (amp.x * spd.x);
        double y = Math.sin((double)this.age * spd.y) * (amp.y * spd.y);
        double z = Math.sin((double)this.age * spd.z) * (amp.z * spd.z);
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        if (Double.isNaN(y)) {
            y = 0.0;
        }
        if (Double.isNaN(z)) {
            z = 0.0;
        }
        return new Vector3(x, y, z);
    }

    public Vector3 get_cosine() {
        Vector3 amp = this.behavior.accelerations().trig().cosine_amplitude();
        Vector3 spd = this.behavior.accelerations().trig().cosine_speed();
        double x = Math.cos((double)this.age * spd.x) * (amp.x * spd.x);
        double y = Math.cos((double)this.age * spd.y) * (amp.y * spd.y);
        double z = Math.cos((double)this.age * spd.z) * (amp.z * spd.z);
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        if (Double.isNaN(y)) {
            y = 0.0;
        }
        if (Double.isNaN(z)) {
            z = 0.0;
        }
        return new Vector3(x, y, z);
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
            CustomParticle particle = new CustomParticle(world, new Vector3(x, y, z), this.spriteProvider);
            particle.pickSprite((SpriteSet)this.spriteProvider);
            return particle;
        }
    }
}

