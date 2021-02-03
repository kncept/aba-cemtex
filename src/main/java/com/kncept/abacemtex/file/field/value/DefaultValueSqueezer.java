package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.List;

public class DefaultValueSqueezer implements ValueSqueezer {

    public static ValueSqueezer defaultValueSqueezer(Object defaultValue, ValueSqueezer squeezer) {
        return new DefaultValueSqueezer(defaultValue, squeezer);
    }

    private final Object defaultValue;
    private final ValueSqueezer squeezer;

    public DefaultValueSqueezer(Object defaultValue, ValueSqueezer squeezer) {
        this.defaultValue = defaultValue;
        this.squeezer = squeezer;
    }

    private Object value(Object value) {
        if (value != null) return value;
        return defaultValue;
    }

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        return squeezer.squeeze(field, value(value));
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        return squeezer.validate(field, value(value));
    }

    @Override
    public Object identify(FieldDefinition field, String value) {
        return squeezer.identify(field, value);
    }
}
