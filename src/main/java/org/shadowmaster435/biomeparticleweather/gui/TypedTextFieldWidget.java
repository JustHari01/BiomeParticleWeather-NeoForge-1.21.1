/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import java.util.function.Consumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.shadowmaster435.biomeparticleweather.util.ConfigParameters;

public class TypedTextFieldWidget
extends EditBox {
    public Consumer<TrackedListener> listener;
    public String id = "";
    public String type = "";

    public TypedTextFieldWidget(Font textRenderer, int x, int y, int width, int height, Component text, ConfigParameters parameters) {
        super(textRenderer, x, y, width, height, text);
        this.id = parameters.name();
        this.type = parameters.type();
    }

    protected boolean clicked(double mouseX, double mouseY) {
        return super.clicked(mouseX, mouseY);
    }

    public void setFocused(boolean focused) {
        if (focused) {
            this.moveCursorTo(0, false);
            this.setHighlightPos(this.getValue().length());
        } else {
            this.setCursorPosition(0);
            this.setHighlightPos(0);
        }
        super.setFocused(focused);
    }

    public void setTrackedChangedListener(Consumer<TrackedListener> changedListener) {
        this.listener = changedListener;
    }

    public void insertText(String text) {
        super.insertText(text);
        this.listener.accept(new TrackedListener(this.getValue(), this.id, this.type));
    }

    public void moveCursorTo(int cursor, boolean shiftKeyPressed) {
        super.moveCursorTo(cursor, shiftKeyPressed);
        this.listener.accept(new TrackedListener(this.getValue(), this.id, this.type));
    }

    public void setMaxLength(int maxLength) {
        super.setMaxLength(maxLength);
        this.listener.accept(new TrackedListener(this.getValue(), this.id, this.type));
    }

    public void setValue(String text) {
        super.setValue(text);
        this.listener.accept(new TrackedListener(this.getValue(), this.id, this.type));
    }

    public record TrackedListener(String text, String id, String type) {
        public TrackedListener(String text, String id, String type) {
            this.text = text;
            this.id = id;
            this.type = type;
        }
    }
}

