package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.List;

public class FixedValueSqueezer implements ValueSqueezer {

    public static FixedValueSqueezer fixedValueSqueezer(String value) {
        return new FixedValueSqueezer(value);
    }

    private final String fixedValue;

    public FixedValueSqueezer(String value) {
        this.fixedValue = value;
    }

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        return fixedValue;
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value != null && !fixedValue.equals(value)) return List.of(field.validationErrorString("must not have any value specified"));
        return Collections.emptyList();
    }
}
