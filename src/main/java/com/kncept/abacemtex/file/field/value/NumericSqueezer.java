package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import static com.kncept.abacemtex.file.field.value.BlankPaddedSqueezer.leftPadded;

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
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null) {
            String stringValue = squeeze(field, value);
            if (!pattern.matcher(stringValue).matches()) return Set.of(field.description + " value " + value + " is not numeric");
        }
        return Collections.emptySet();
    }
}
