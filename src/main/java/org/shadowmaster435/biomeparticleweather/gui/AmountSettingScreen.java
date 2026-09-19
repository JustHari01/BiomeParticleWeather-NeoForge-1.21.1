/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import java.util.HashMap;
import java.util.Objects;
import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.shadowmaster435.biomeparticleweather.gui.PagedScreen;
import org.shadowmaster435.biomeparticleweather.gui.TypedTextFieldWidget;
import org.shadowmaster435.biomeparticleweather.util.ConfigFile;
import org.shadowmaster435.biomeparticleweather.util.ConfigParameters;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;

@Environment(value=EnvType.CLIENT)
public class AmountSettingScreen
extends PagedScreen {
    private final Screen parent;
    public HashMap<ConfigParameters, EditBox> options = new HashMap();
    public final String[] valid_chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."};

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
        ConfigFile.save_config();
    }

    public AmountSettingScreen(Screen parent) {
        super(parent);
        this.parent = parent;
    }

    protected void init() {
        try {
            this.add_setting(new ConfigParameters("spawn_radius", "int"));
            this.add_setting(new ConfigParameters("global_multiplier", "float"));
            this.add_setting(new ConfigParameters("rain_amount", "float"));
            this.add_setting(new ConfigParameters("heavy_rain_amount", "float"));
            this.add_setting(new ConfigParameters("fog_amount", "float"));
            this.add_setting(new ConfigParameters("wind_amount", "float"));
            this.add_setting(new ConfigParameters("sand_mote_amount", "float"));
            this.add_setting(new ConfigParameters("red_sand_mote_amount", "float"));
            this.add_setting(new ConfigParameters("snow_amount", "float"));
            this.add_setting(new ConfigParameters("blizzard_snow_amount", "float"));
            this.add_setting(new ConfigParameters("blizzard_wind_amount", "float"));
            this.add_setting(new ConfigParameters("tumblebush_amount", "float"));
            this.add_setting(new ConfigParameters("updraft_amount", "float"));
            this.add_setting(new ConfigParameters("spore_amount", "float"));
            this.add_setting(new ConfigParameters("weeping_tear_amount", "float"));
            this.add_setting(new ConfigParameters("soul_amount", "float"));
            this.add_setting(new ConfigParameters("firefly_amount", "float"));
            this.add_setting(new ConfigParameters("lightning_particle_density", "int"));
            for (ConfigParameters parameters : this.options.keySet()) {
                if (Objects.equals(parameters.type(), "float")) {
                    this.set_text_of(parameters, String.valueOf(ParticleSettings.get_float(parameters.name())));
                }
                if (!Objects.equals(parameters.type(), "int")) continue;
                this.set_text_of(parameters, String.valueOf(ParticleSettings.get_int(parameters.name())));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_setting(ConfigParameters parameters) {
        StringWidget text_display = new StringWidget(4, 4 + 20 * (this.options.size() % 10), 512, 16, (Component)Component.translatable((String)("biomeparticleweather." + parameters.name())), Minecraft.getInstance().font);
        TypedTextFieldWidget text_input = new TypedTextFieldWidget(Minecraft.getInstance().font, this.width - 68, 4 + 20 * (this.options.size() % 10), 64, 16, Component.nullToEmpty((String)""), parameters);
        text_display.alignLeft();
        this.options.put(parameters, text_input);
        text_display.setTooltip(Tooltip.create((Component)Component.translatable((String)("biomeparticleweather." + parameters.name() + ".tooltip"))));
        text_input.setTrackedChangedListener(a -> this.changed(new TypedTextFieldWidget.TrackedListener(a.text(), a.id(), a.type())));
        this.addRenderableWidget(text_display);
        this.addRenderableWidget(text_input);
        this.add_entry(new Object[]{text_display, text_input});
    }

    public void set_float_setting(String key, float value) {
        ParticleSettings.set_float(key, value);
    }

    public void set_int_setting(String key, int value) {
        ParticleSettings.set_int(key, value);
    }

    public void changed(TypedTextFieldWidget.TrackedListener listener) {
        this.set_setting(new ConfigParameters(listener.id(), listener.type()), listener.text());
    }

    public void set_text_of(ConfigParameters parameters, String text) {
        for (ConfigParameters param : this.options.keySet()) {
            if (!param.match(parameters)) continue;
            this.options.get(param).setValue(text);
            return;
        }
    }

    public void set_setting(ConfigParameters parameters, String text) {
        if (Objects.equals(parameters.type(), "float")) {
            try {
                this.set_float_setting(parameters.name(), Float.parseFloat(text));
            }
            catch (Exception ignored) {
                this.set_text_of(new ConfigParameters(parameters.name(), parameters.type()), String.valueOf(ParticleSettings.get_float(parameters.name())));
                System.out.println("float parse failed");
            }
        }
        if (Objects.equals(parameters.type(), "int")) {
            try {
                this.set_int_setting(parameters.name(), Integer.parseInt(text));
            }
            catch (Exception ignored) {
                this.set_text_of(new ConfigParameters(parameters.name(), parameters.type()), String.valueOf(ParticleSettings.get_int(parameters.name())));
                System.out.println("int parse failed");
            }
        }
    }
}
