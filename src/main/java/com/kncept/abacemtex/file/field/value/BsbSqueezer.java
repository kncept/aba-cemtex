package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

public class BsbSqueezer implements ValueSqueezer {

    public static BsbSqueezer BSB_SQUEEZER = new BsbSqueezer();

    private Pattern pattern = Pattern.compile("\\d{3}-\\d{3}");

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return "       ";
        return value.toString();
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null) {
            String stringValue = squeeze(field, value);
            if (!pattern.matcher(stringValue).matches()) return Set.of(field.validationErrorString(value, "does not match bsb format 000-000"));
        }
        return Collections.emptySet();
    }
}
