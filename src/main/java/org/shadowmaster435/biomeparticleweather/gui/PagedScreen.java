/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.gui;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class PagedScreen
extends Screen {
    private final Screen parent;
    public int page = 0;
    public final Button previous_button;
    public final Button next_button;
    public HashMap<Integer, ArrayList<Object[]>> paged_elements = new HashMap();

    public PagedScreen(Screen parent) {
        super((Component)Component.empty());
        this.parent = parent;
        this.paged_elements.put(0, new ArrayList());
        this.next_button = Button.builder((Component)Component.nullToEmpty((String)">"), a -> this.previous_page()).bounds(parent.width - 20, parent.height - 20, 16, 16).build();
        this.previous_button = Button.builder((Component)Component.nullToEmpty((String)"<"), a -> this.next_page()).bounds(4, parent.height - 20, 16, 16).build();
        this.addRenderableWidget(this.previous_button);
        this.addRenderableWidget(this.next_button);
    }

    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    public void set_page(int page) {
        this.page = Mth.clamp((int)page, (int)0, (int)(this.paged_elements.size() - 1));
        this.update_elements();
    }

    public void previous_page() {
        this.set_page(this.page + 1);
    }

    public void next_page() {
        this.set_page(this.page - 1);
    }

    public void add_entry(Object ... widgets) {
        if (this.will_overflow(this.paged_elements.size() - 1)) {
            ArrayList<Object[]> new_list = new ArrayList<Object[]>();
            new_list.add(widgets);
            this.paged_elements.put(this.paged_elements.size(), new_list);
        } else {
            this.paged_elements.get(this.paged_elements.size() - 1).add(widgets);
        }
        this.update_elements();
    }

    public boolean will_overflow(int page) {
        return this.paged_elements.get(page).size() >= 10;
    }

    public void update_elements() {
        this.next_button.visible = this.page <= 0;
        this.next_button.active = this.page <= 0;
        this.previous_button.visible = this.page >= this.paged_elements.size() - 1;
        this.previous_button.active = this.page >= this.paged_elements.size() - 1;
        for (int i = 0; i < this.paged_elements.size(); ++i) {
            ArrayList<Object[]> page_elements = this.paged_elements.get(i);
            for (Object[] entry_elements : page_elements) {
                for (Object entry_element : entry_elements) {
                    boolean bool;
                    AbstractWidget widget = (AbstractWidget)entry_element;
                    widget.visible = bool = i == this.page;
                }
            }
        }
    }
}
