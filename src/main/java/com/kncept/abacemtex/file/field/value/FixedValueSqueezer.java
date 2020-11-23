package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.Set;

public class FixedValueSqueezer implements ValueSqueezer {

    public static FixedValueSqueezer fixedValueSqueezer(String value) {
        return new FixedValueSqueezer(value);
    }

    private final String value;

    public FixedValueSqueezer(String value) {
        this.value = value;
    }

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        return this.value;
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null) return Set.of(field.validationErrorString("must not have any value specified"));
        return Collections.emptySet();
    }
}
