package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.List;

public interface ValueSqueezer {
    String squeeze(FieldDefinition field, Object value);
    List<String> validate(FieldDefinition field, Object value);
    default Object identify(FieldDefinition field, String value) {
        return value;
    }
}
