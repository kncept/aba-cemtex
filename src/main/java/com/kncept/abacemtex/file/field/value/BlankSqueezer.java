package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.Collections;
import java.util.Set;

public class BlankSqueezer implements ValueSqueezer {

    public static BlankSqueezer BLANK_SQUEEZER = new BlankSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        return StringUtils.blankString(field.length);
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null) return Set.of(field.validationErrorString("must not have any value specified"));
        return Collections.emptySet();
    }
}
