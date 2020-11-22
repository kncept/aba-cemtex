package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.Set;

import static com.kncept.abacemtex.file.field.value.BlankPaddedSqueezer.leftPadded;
import static com.kncept.abacemtex.file.field.value.BlankPaddedSqueezer.rightPadded;

public class StringSqueezer implements ValueSqueezer {

    public static ValueSqueezer STRING_SQUEEZER = rightPadded(" ", new StringSqueezer());
    public static ValueSqueezer ACCOUNT_STRING_SQUEEZER = leftPadded(" ", new StringSqueezer());

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return "";
        return value.toString();
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        return Collections.emptySet();
    }
}
