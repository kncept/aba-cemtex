package com.kncept.abacemtex.file.field;

import com.kncept.abacemtex.file.field.valuesqueezer.ValueSqueezer;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FieldDefinition {
    public final int startPos, endPos, length;
    public final String description;
    public final FieldType type;
    public final boolean required;
    private final ValueSqueezer valueSqueezer; // squeeze to a NICE STRING value

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
        String stringValue = valueSqueezer.squeeze(this, value);
        if (stringValue == null && !required) {
            return StringUtils.padding(type == FieldType.ALPHA ? " " : "0", length);
        }
        return stringValue;
    }

    public List<String> validate(Object value) {
        List<String> errors = new ArrayList<>();
        errors.addAll(valueSqueezer.validate(this, value));
        String stringValue = parse(value);
        if (stringValue == null || required && stringValue.isBlank()) return List.of(validationErrorString("has no value"));
        if (!type.isValid(stringValue)) errors.add(validationErrorString(value, "not of type " + type.name()));
        if (stringValue.length() != length) errors.add(validationErrorString(value, "of length " + stringValue.length() + " not of length " + length));
        return errors;
    }

    public Object identify(String value) {
        return valueSqueezer.identify(this, value);
    }

    public String validationErrorString(Object value, String message) {
        if (value == null) return validationErrorString("no value " + message);
        return validationErrorString("value \"" + value + "\" " + message);
    }

    public String validationErrorString(String message) {
        return description + " " + message;
    }
}