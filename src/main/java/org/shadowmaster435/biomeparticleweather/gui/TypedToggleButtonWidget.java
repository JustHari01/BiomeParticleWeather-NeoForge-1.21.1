package org.shadowmaster435.biomeparticleweather.gui;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.shadowmaster435.biomeparticleweather.util.ConfigParameters;

/** Small NeoForge-safe toggle widget; vanilla's Checkbox constructor is package-private in 1.21.1. */
public class TypedToggleButtonWidget extends AbstractButton {
    public Consumer<TrackedListener> listener;
    public boolean toggled;
    public String id = "";
    public String type = "";

    public TypedToggleButtonWidget(int x, int y, boolean toggled, ConfigParameters parameters) {
        super(x, y, 64, 20, Component.empty());
        this.id = parameters.name();
        this.type = parameters.type();
        this.setToggled(toggled);
    }

    @Override
    public void onPress() {
        this.setToggled(!this.toggled);
        this.update(this.toggled);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        // The visible ON/OFF label is already a complete accessible description.
    }

    public void update(boolean checked) {
        if (this.listener != null) {
            this.listener.accept(new TrackedListener(checked, this.id, this.type));
        }
    }

    public void setToggled(boolean value) {
        this.toggled = value;
        this.setMessage(Component.literal(value ? "ON" : "OFF"));
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setTrackedChangedListener(Consumer<TrackedListener> changedListener) {
        this.listener = changedListener;
    }

    public record TrackedListener(boolean value, String id, String type) {
    }
}
