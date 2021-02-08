package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static com.kncept.abacemtex.file.field.valuesqueezer.BlankPaddedSqueezer.leftPadded;

public class NumericSqueezer implements ValueSqueezer {

    public static ValueSqueezer NUMERIC_SQUEEZER = leftPadded("0", new NumericSqueezer());

    private Pattern pattern = Pattern.compile("\\d+");


    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof Number) return Long.toString(((Number) value).longValue());
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value != null) {
            String stringValue = squeeze(field, value);
            if (!pattern.matcher(stringValue).matches()) return List.of(field.validationErrorString(value,"is not numeric"));
        }
        return Collections.emptyList();
    }

    @Override
    public BigInteger identify(FieldDefinition field, String value) {
        if (value == null) return null;
        return new BigInteger(value);
    }
}
