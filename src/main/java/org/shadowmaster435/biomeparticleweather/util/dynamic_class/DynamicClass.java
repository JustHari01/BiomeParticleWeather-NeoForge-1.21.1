/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.util.dynamic_class;

public class DynamicClass {
    private final Object original_object;

    public DynamicClass() {
        this.original_object = this;
    }

    public DynamicClass(Object object) {
        this.original_object = object;
    }

    public Object get(String key) {
        Object result = null;
        try {
            result = this.original_object.getClass().getField(key).get(this.original_object);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void set(String key, Object value) {
        Object[] tst = new Object[]{1, "", Float.valueOf(0.5f)};
        try {
            this.original_object.getClass().getField(key).set(value, this.original_object);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object call(String key) {
        Object result = null;
        try {
            result = this.original_object.getClass().getMethod(key, new Class[0]).invoke(this.original_object, new Object[0]);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Object call(String key, Object[] args) {
        Object result = null;
        try {
            result = this.original_object.getClass().getMethod(key, new Class[0]).invoke(this.original_object, args);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

