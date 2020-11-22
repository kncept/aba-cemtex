package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Set;

public interface ValueSqueezer {
    String squeeze(FieldDefinition field, Object value);
    Set<String> validate(FieldDefinition field, Object value);
}
