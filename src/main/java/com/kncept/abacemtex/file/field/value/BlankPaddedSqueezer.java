package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.Collections;
import java.util.Set;

public class BlankPaddedSqueezer implements ValueSqueezer {

    public static ValueSqueezer leftPadded(String padding, ValueSqueezer squeezer) {
        return new BlankPaddedSqueezer(padding, squeezer, true);
    }
    public static ValueSqueezer rightPadded(String padding, ValueSqueezer squeezer) {
        return new BlankPaddedSqueezer(padding, squeezer, false);
    }

    private final String padding;
    private final ValueSqueezer squeezer;
    private final boolean leftPadded;

    public BlankPaddedSqueezer(String padding, ValueSqueezer squeezer, boolean leftPadded) {
        this.padding = padding;
        this.squeezer = squeezer;
        this.leftPadded = leftPadded;
    }

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        String stringVal = squeezer.squeeze(field, value);
        if (stringVal == null) return null;
        return leftPadded ?
                StringUtils.padding(padding, field.length - stringVal.length()) + stringVal :
                stringVal +  StringUtils.padding(padding, field.length - stringVal.length());
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        return squeezer.validate(field, value);
    }
}
