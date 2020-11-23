package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Set;

public class DateSqueezer implements ValueSqueezer {

    public static DateSqueezer DATE_SQUEEZER = new DateSqueezer();

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyy");

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(dateFormat);
        }
        return value.toString();
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null && !(value instanceof LocalDate))  try {
            LocalDate local = LocalDate.from(dateFormat.parse(value.toString()));
            if (!squeeze(field, local).equals(value.toString()))
                return Set.of(field.validationErrorString(value, "is not a valid DDMMYY date"));
        } catch (DateTimeParseException e) {
            return Set.of(field.validationErrorString(value, "is not a valid DDMMYY date"));
        }
        return Collections.emptySet();
    }
}
