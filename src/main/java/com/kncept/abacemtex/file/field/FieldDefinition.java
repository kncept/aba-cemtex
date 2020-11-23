package com.kncept.abacemtex.file.field;

import com.kncept.abacemtex.file.field.value.ValueSqueezer;

import java.util.HashSet;
import java.util.Set;

public class FieldDefinition {
    public final int startPos, endPos, length;
    public final String description;
    public final FieldType type;
    public final boolean required;
    private final ValueSqueezer valueSqueezer;

    public FieldDefinition(
            int startPos,
            int endPos,
            int length,
            String description,
            FieldType type,
            boolean required,
            ValueSqueezer valueSqueezer
    ) {
        this.startPos = startPos; // inclusive start index. File spec not written by a programmer
        this.endPos = endPos; //end index
        this.length = length; // total length
        this.description = description;
        this.type = type;
        this.required = required;
        this.valueSqueezer = valueSqueezer;
    }

    public String parse(Object value) {
        return valueSqueezer.squeeze(this, value);
    }

    public Set<String> validate(Object value) {
        Set<String> errors = new HashSet<>();
        errors.addAll(valueSqueezer.validate(this, value));
        String stringValue = parse(value);
        if (stringValue == null || required && stringValue.isBlank()) return Set.of(validationErrorString("has no value"));
        if (!type.isValid(stringValue)) errors.add(validationErrorString(value, "not of type " + type.name()));
        if (stringValue.length() != length) errors.add(validationErrorString(value, "of length " + stringValue.length() + " not of length " + length));
        return errors;
    }

    public String validationErrorString(Object value, String message) {
        if (value == null) return validationErrorString("no value " + message);
        return validationErrorString("value \"" + value + "\" " + message);
    }

    public String validationErrorString(String message) {
        return description + " " + message;
    }
}