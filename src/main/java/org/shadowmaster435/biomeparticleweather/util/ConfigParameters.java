/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util;

import java.util.Objects;

public record ConfigParameters(String name, String type) {
    public ConfigParameters(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public boolean match(ConfigParameters parameters) {
        return Objects.equals(parameters.name, this.name()) && Objects.equals(parameters.type, this.type());
    }
}

