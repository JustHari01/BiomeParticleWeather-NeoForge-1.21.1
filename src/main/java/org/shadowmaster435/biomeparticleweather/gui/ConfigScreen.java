/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import org.shadowmaster435.biomeparticleweather.compat.EnvType;
import org.shadowmaster435.biomeparticleweather.compat.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.shadowmaster435.biomeparticleweather.gui.AmountSettingScreen;
import org.shadowmaster435.biomeparticleweather.gui.TogglesSettingScreen;
import org.shadowmaster435.biomeparticleweather.util.ConfigFile;

@Environment(value=EnvType.CLIENT)
public class ConfigScreen
extends Screen {
    private final Screen parent;
    public Button button1;
    public Button button2;
    public EditBox text;
    public String tst = "";

    public void onClose() {
        this.minecraft.setScreen(this.parent);
        ConfigFile.save_config();
    }

    public ConfigScreen(Screen parent) {
        super((Component)Component.literal((String)"My tutorial screen"));
        this.parent = parent;
    }

    protected void init() {
        this.button1 = Button.builder((Component)Component.literal((String)"Amount Config"), button -> Minecraft.getInstance().setScreen((Screen)new AmountSettingScreen(this))).bounds(this.width / 2 - 205, 20, 200, 20).build();
        this.button2 = Button.builder((Component)Component.literal((String)"Toggles"), button -> Minecraft.getInstance().setScreen((Screen)new TogglesSettingScreen(this))).bounds(this.width / 2 + 5, 20, 200, 20).build();
        this.addRenderableWidget(this.button1);
        this.addRenderableWidget(this.button2);
    }
}
