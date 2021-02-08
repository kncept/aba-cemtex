package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.Collections;
import java.util.List;

public class BlankSqueezer implements ValueSqueezer {

    public static BlankSqueezer BLANK_SQUEEZER = new BlankSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        return StringUtils.blankString(field.length);
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value != null && value instanceof String && !((String) value).isBlank()) return List.of(field.validationErrorString("must not have any value specified"));
        return Collections.emptyList();
    }
}
