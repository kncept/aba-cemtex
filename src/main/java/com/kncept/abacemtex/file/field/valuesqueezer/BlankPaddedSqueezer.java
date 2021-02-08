package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.List;

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
    public List<String> validate(FieldDefinition field, Object value) {
        return squeezer.validate(field, value);
    }

    @Override
    public Object identify(FieldDefinition field, String value) {
        if (value == null) return null;
        if (leftPadded) {
            while (value.startsWith(" "))
                value = value.substring(1);
        } else {
            while (value.endsWith(" "))
                value = value.substring(0, value.length() - 1);
        }
        return squeezer.identify(field, value);
    }
}
