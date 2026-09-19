/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Vector3
extends Vec3 {
    public static final Vector3 ZERO = new Vector3(0.0, 0.0, 0.0);
    public static final Vector3 ONE = new Vector3(1.0, 1.0, 1.0);

    public Vector3(float all) {
        super((double)all, (double)all, (double)all);
    }

    public Vector3(double all) {
        super(all, all, all);
    }

    public Vector3(Vec3 vec) {
        super(vec.x(), vec.y(), vec.z());
    }

    public Vector3(Vec3i vec) {
        super((double)vec.getX(), (double)vec.getY(), (double)vec.getZ());
    }

    public Vector3 negate() {
        return new Vector3(this.scale(-1.0));
    }

    public Vector3(Vector3f vec) {
        super(vec);
    }

    public Vector3(float x, float y, float z) {
        super(new Vector3f(x, y, z));
    }

    public Vector3(float h, float v) {
        super(new Vector3f(h, v, h));
    }

    public Vector3 mul(Vector3 other) {
        return new Vector3(this.multiply(other));
    }

    public Vector3 mul(float other) {
        return new Vector3(this.scale(other));
    }

    public Vector3 mul(double other) {
        return new Vector3(this.scale(other));
    }

    public Vector3 mul(int other) {
        return new Vector3(this.scale(other));
    }

    public Vector3 div(Vector3 other) {
        double x = Math.max(this.x, 0.001) / Math.max(other.x, 0.001);
        double y = Math.max(this.y, 0.001) / Math.max(other.y, 0.001);
        double z = Math.max(this.z, 0.001) / Math.max(other.z, 0.001);
        return new Vector3(x, y, z);
    }

    public BlockPos to_blockpos() {
        return new BlockPos((int)this.x, (int)this.y, (int)this.z);
    }

    public Vector3 div(float other) {
        return this.div(new Vector3(other));
    }

    public Vector3 div(double other) {
        return this.div(new Vector3(other));
    }

    public Vector3 div(int other) {
        return this.div(new Vector3(other));
    }

    public Vector3(Vector3d vec) {
        super(vec.x, vec.y, vec.z);
    }

    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    private Vector3d to_joml() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public Vector3f to_3f() {
        return new Vector3f((float)this.x, (float)this.y, (float)this.z);
    }

    public Vector3 deg_to_rad() {
        return new Vector3(Math.toRadians(this.x), Math.toRadians(this.y), Math.toRadians(this.z));
    }

    public Vector3 with_x(double x) {
        return new Vector3(x, this.y(), this.z());
    }

    public Vector3 with_y(double y) {
        return new Vector3(this.x(), y, this.z());
    }

    public Vector3 with_z(double z) {
        return new Vector3(this.x(), this.y(), z);
    }

    public Vector3 add_x(double x) {
        return new Vector3(this.x() + x, this.y(), this.z());
    }

    public Vector3 add_y(double y) {
        return new Vector3(this.x(), this.y() + y, this.z());
    }

    public Vector3 add_z(double z) {
        return new Vector3(this.x(), this.y(), this.z() + z);
    }

    public Vector3 with_axis(Direction.Axis axis, double h, double v) {
        Vector3 result = this;
        switch (axis) {
            case X: {
                result = new Vector3(this.x, v, h);
                break;
            }
            case Y: {
                result = new Vector3(h, this.y, v);
                break;
            }
            case Z: {
                result = new Vector3(h, v, this.z);
            }
        }
        return result;
    }

    public Vector3 add_axis(Direction.Axis axis, double h, double v) {
        Vector3 result = this;
        switch (axis) {
            case X: {
                result = new Vector3(this.x, this.y + v, this.z + h);
                break;
            }
            case Y: {
                result = new Vector3(this.x + h, this.y, this.z + v);
                break;
            }
            case Z: {
                result = new Vector3(this.x + h, this.y + v, this.z);
            }
        }
        return result;
    }

    public Vector3 rotate_around(Vector3 rotation, Vector3 origin) {
        return new Vector3(0.0f);
    }

    public Vector3 angle_to(Vector3 other, double amplitude) {
        Quaternionf quat = new Quaternionf();
        Vector3f thisf = this.to_3f();
        Quaternionf otherf = other.to_3f().normalize().rotationTo((Vector3fc)thisf.normalize(), quat);
        return new Vector3(otherf.x, otherf.y, otherf.z).mul(amplitude);
    }

    public Vector3 with_random_offset(float max_ofs) {
        Vector3 min = new Vector3(-max_ofs);
        Vector3 max = new Vector3(max_ofs);
        float randx = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        float randy = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        float randz = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        return new Vector3(this.add(min.lerp(max, new Vector3(randx, randy, randz))));
    }

    public Vector3 with_random_offset(Vector3 max_ofs) {
        float x = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        float y = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        float z = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)0.0f, (float)1.0f);
        return new Vector3(this.add(max_ofs.negate())).lerp(new Vector3(this.add(max_ofs)), new Vector3(x, y, z));
    }

    public Vector3 lerp(Vector3 to, double delta) {
        return new Vector3(super.lerp((Vec3)to, delta));
    }

    public Vector3 lerp(Vector3 to, Vector3 delta) {
        double x = Mth.lerp((double)delta.x, (double)this.x, (double)to.x);
        double y = Mth.lerp((double)delta.y, (double)this.y, (double)to.y);
        double z = Mth.lerp((double)delta.z, (double)this.z, (double)to.z);
        return new Vector3(x, y, z);
    }

    public Vector3 bounce(Direction direction) {
        Direction.Axis axis = direction.getAxis();
        double x = this.x;
        double y = this.y;
        double z = this.z;
        if (Objects.equals(axis.toString(), "x")) {
            x = this.x * -1.0;
        }
        if (Objects.equals(axis.toString(), "y")) {
            y = this.y * -1.0;
        }
        if (Objects.equals(axis.toString(), "z")) {
            x = this.z * -1.0;
        }
        return new Vector3(x, y, z);
    }

    public static Vector3 rand_between(Vector3 min, Vector3 max) {
        return new Vector3(Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)((float)min.x), (float)((float)max.x)), Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)((float)min.y), (float)((float)max.y)), Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)((float)min.z), (float)((float)max.z)));
    }
}

