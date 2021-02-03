package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

public class TimeSqueezer implements ValueSqueezer {

    public static TimeSqueezer TIME_SQUEEZER = new TimeSqueezer();

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HHmm");

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof LocalTime) {
            return ((LocalTime) value).format(dateFormat);
        }
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value instanceof LocalTime) return Collections.emptyList();
        String stringValue = squeeze(field, value);
        if (value != null && !stringValue.isBlank()) try {
            LocalTime local = LocalTime.from(dateFormat.parse(value.toString()));
            if (!squeeze(field, local).equals(value.toString()))
                return List.of(field.validationErrorString(value, "is not a valid HHmm time"));
        } catch (DateTimeParseException e) {
            return List.of(field.validationErrorString(value, "is not a valid HHmm time"));
        }
        return Collections.emptyList();
    }

    @Override
    public LocalTime identify(FieldDefinition field, String value) {
        if (value == null || value.isBlank()) return null;
        return LocalTime.parse(value, dateFormat);
    }
}
