package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import static com.kncept.abacemtex.file.field.value.TimeSqueezer.TIME_SQUEEZER;
import static org.junit.jupiter.api.Assertions.*;

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
        assertFormatError("");
        assertFormatError("2400");
        assertFormatError("000");

    }

    @Test
    public void validLocalDates() {
        assertNoErrors(LocalTime.now());
        assertNoErrors(null);
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
        Set<String> validationErrors = squeezer.validate(field, value);
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        if (value != null) {
            String stringValue = squeezer.squeeze(field, value);
            assertNotNull(stringValue);
            assertFalse(stringValue.isBlank());
        }
    }

    public void assertFormatError(Object value) {
        Set<String> validationErrors = squeezer.validate(field, value);
        assertFalse(validationErrors.isEmpty(), validationErrors.toString());
    }
}
