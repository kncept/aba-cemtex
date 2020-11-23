package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.List;

public interface ValueSqueezer {
    String squeeze(FieldDefinition field, Object value);
    List<String> validate(FieldDefinition field, Object value);
}
