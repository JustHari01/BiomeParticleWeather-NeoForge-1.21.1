/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ConfigButton
extends Button {
    public static final ResourceLocation button_texture = ResourceLocation.fromNamespaceAndPath((String)"biomeparticleweather", (String)"textures/gui/button.png");

    public ConfigButton(int x, int y, int width, int height, Component message, Button.OnPress onPress, Button.CreateNarration narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        context.blit(button_texture, this.getX(), this.getY(), 0.0f, 0.0f, 16, 16, 16, 16);
    }

    public boolean isActive() {
        return false;
    }
}

