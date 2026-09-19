/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import java.util.HashMap;
import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.shadowmaster435.biomeparticleweather.gui.TypedToggleButtonWidget;
import org.shadowmaster435.biomeparticleweather.util.ConfigFile;
import org.shadowmaster435.biomeparticleweather.util.ConfigParameters;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;

@Environment(value=EnvType.CLIENT)
public class TogglesSettingScreen
extends Screen {
    private final Screen parent;
    public HashMap<ConfigParameters, TypedToggleButtonWidget> options = new HashMap();
    public EditBox text;
    public String tst = "";

    public void onClose() {
        this.minecraft.setScreen(this.parent);
        ConfigFile.save_config();
    }

    public TogglesSettingScreen(Screen parent) {
        super((Component)Component.literal((String)"My tutorial screen"));
        this.parent = parent;
    }

    protected void init() {
        try {
            this.add_setting(new ConfigParameters("config_button_visible", "bool"));
            this.add_setting(new ConfigParameters("vanilla_lightning", "bool"));
            this.add_setting(new ConfigParameters("rain_trails", "bool"));
            this.add_setting(new ConfigParameters("rain_splash", "bool"));
            this.add_setting(new ConfigParameters("rain_surface_ripples", "bool"));
            this.add_setting(new ConfigParameters("vanilla_rain_rendering", "bool"));
            this.add_setting(new ConfigParameters("vanilla_rain_splash", "bool"));
            for (ConfigParameters parameters : this.options.keySet()) {
                this.set_value_of(parameters, ParticleSettings.get_bool(parameters.name()));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_setting(ConfigParameters parameters) {
        StringWidget text_display = new StringWidget(4, 4 + 20 * this.options.size(), 512, 16, (Component)Component.translatable((String)("biomeparticleweather." + parameters.name())), Minecraft.getInstance().font);
        TypedToggleButtonWidget button = new TypedToggleButtonWidget(this.width - 68, 4 + 20 * this.options.size(), ParticleSettings.get_bool(parameters.name()), parameters);
        text_display.alignLeft();
        text_display.setTooltip(Tooltip.create((Component)Component.translatable((String)("biomeparticleweather." + parameters.name() + ".tooltip"))));
        this.options.put(parameters, button);
        button.setTrackedChangedListener(a -> this.changed(new TypedToggleButtonWidget.TrackedListener(a.value(), a.id(), a.type())));
        this.addRenderableWidget(text_display);
        this.addRenderableWidget(button);
    }

    public void changed(TypedToggleButtonWidget.TrackedListener listener) {
        this.set_setting(new ConfigParameters(listener.id(), listener.type()), listener.value());
    }

    public void set_value_of(ConfigParameters parameters, boolean value) {
        for (ConfigParameters param : this.options.keySet()) {
            if (!param.match(parameters)) continue;
            this.options.get(param).setToggled(value);
            return;
        }
    }

    public void set_setting(ConfigParameters parameters, boolean value) {
        try {
            ParticleSettings.set_bool(parameters.name(), value);
        }
        catch (Exception ignored) {
            System.out.println("bool '" + parameters.name() + "' doesn't exist.");
        }
    }
}
