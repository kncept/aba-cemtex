package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.List;

import static com.kncept.abacemtex.file.field.valuesqueezer.BlankPaddedSqueezer.leftPadded;
import static com.kncept.abacemtex.file.field.valuesqueezer.BlankPaddedSqueezer.rightPadded;

public class StringSqueezer implements ValueSqueezer {

    public static ValueSqueezer STRING_SQUEEZER = rightPadded(" ", new StringSqueezer());
    public static ValueSqueezer ACCOUNT_STRING_SQUEEZER = leftPadded(" ", new StringSqueezer());

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        return Collections.emptyList();
    }
}
