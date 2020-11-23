package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Set;

public class TimeSqueezer implements ValueSqueezer {

    public static TimeSqueezer TIME_SQUEEZER = new TimeSqueezer();

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HHmm");

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return "    ";
        if (value instanceof LocalTime) {
            return ((LocalTime) value).format(dateFormat);
        }
        return value.toString();
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null && !(value instanceof LocalTime))  try {
            LocalTime local = LocalTime.from(dateFormat.parse(value.toString()));
            if (!squeeze(field, local).equals(value.toString()))
                return Set.of(field.validationErrorString(value, "is not a valid HHmm time"));
        } catch (DateTimeParseException e) {
            return Set.of(field.validationErrorString(value, "is not a valid HHmm time"));
        }
        return Collections.emptySet();
    }
}
