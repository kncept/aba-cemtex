package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.kncept.abacemtex.file.field.valuesqueezer.TimeSqueezer.TIME_SQUEEZER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeSqueezerTest {
    ValueSqueezer squeezer = TIME_SQUEEZER;
    FieldDefinition field = new FieldDefinition(1, 4, 4, "time test field", FieldType.ALPHA, true, squeezer);

    @Test
    public void validStringTimes() {
        assertNoErrors("0000");
        assertNoErrors("2359");
        assertNoErrors("0117");
    }

    @Test
    public void invalidStringTimes() {
        assertFormatError("2400");
        assertFormatError("000");
        assertFormatError("01:17");
    }

    @Test
    public void validLocalTimes() {
        assertNoErrors(LocalTime.now());
        assertNoErrors(null);
        assertNoErrors("");
        assertNoErrors(" ");
        assertNoErrors("    ");
    }

    @Test
    public void invalidTypesForLocalDate() {
        assertFormatError(new Object());
        assertFormatError(new Date());
        assertFormatError(new GregorianCalendar());
        assertFormatError(System.currentTimeMillis());
        assertFormatError(LocalDateTime.now());
        assertFormatError(LocalDate.now());
    }

    public void assertNoErrors(Object value) {
        List<String> validationErrors = squeezer.validate(field, value);
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
    }

    public void assertFormatError(Object value) {
        List<String> validationErrors = squeezer.validate(field, value);
        assertFalse(validationErrors.isEmpty(), validationErrors.toString());
    }
}
