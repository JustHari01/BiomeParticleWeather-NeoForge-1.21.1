/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.gui.ConfigButton;
import org.shadowmaster435.biomeparticleweather.gui.ConfigScreen;
import org.shadowmaster435.biomeparticleweather.compat.PauseScreenAccess;
import org.shadowmaster435.biomeparticleweather.util.ParticleEngine;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.shadowmaster435.biomeparticleweather.util.Vector3;

public class StaticMethods {
    public static boolean make_single_bolt_bool = true;

    public StaticMethods() {
    }

    public static void spawn_particles(Entity lightningEntity) {
        if (!Minecraft.getInstance().isPaused() && lightningEntity.tickCount == 1) {
            if (make_single_bolt_bool) {
                ParticleEngine.spawn_particle((ParticleOptions)BiomeParticleWeather.LIGHTNING_NODE, new Vector3(lightningEntity.position()));
                for (int i = 0; i < 32; ++i) {
                    BlockPos bpos = lightningEntity.blockPosition().offset(new Vec3i(0, 0, 0));
                    BlockParticleOption effect = new BlockParticleOption(ParticleTypes.BLOCK, lightningEntity.level().getBlockState(bpos));
                    Vec3 pos = lightningEntity.position();
                    float max_vel = 1000.0f;
                    float vx = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)(-max_vel), (float)max_vel);
                    float vy = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)(max_vel / 2.0f), (float)max_vel);
                    float vz = Mth.randomBetween((RandomSource)RandomSource.createNewThreadLocalInstance(), (float)(-max_vel), (float)(max_vel * 2.0f));
                    lightningEntity.level().addParticle((ParticleOptions)effect, pos.x, pos.y + 1.0, pos.z, (double)vx, (double)vy, (double)vz);
                    make_single_bolt_bool = false;
                }
            } else {
                make_single_bolt_bool = true;
            }
        }
    }

    public static double angle_to_2d(Vector2f vector1, Vector2f vector2) {
        return Math.acos(vector2.normalize().dot((Vector2fc)vector1.normalize()));
    }

    public float angle_to_with_amplitude(Vector2f first, Vector2f second) {
        return (float)Math.acos(first.dot((Vector2fc)second));
    }

    public static void init_config(PauseScreen screen) {
        if (ParticleSettings.get_bool("config_button_visible")) {
            GridLayout gridWidget2 = new GridLayout();
            GridLayout.RowHelper adder2 = gridWidget2.createRowHelper(2);
            gridWidget2.setPosition(0, 0);
            Supplier<Screen> supplier = () -> new ConfigScreen((Screen)screen);
            ConfigButton button = new ConfigButton(0, 0, 16, 16, (Component)Component.empty(), a -> Minecraft.getInstance().setScreen((Screen)supplier.get()), null);
            adder2.addChild((LayoutElement)button);
            gridWidget2.arrangeElements();
            FrameLayout.alignInRectangle((LayoutElement)gridWidget2, (int)0, (int)0, (int)44, (int)44, (float)0.5f, (float)0.25f);
            gridWidget2.visitWidgets(arg_0 -> ((PauseScreenAccess)(Object)screen).biomeParticleWeather$addWidget(arg_0));
        }
    }
}
