package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.kncept.abacemtex.file.field.value.DateSqueezer.DATE_SQUEEZER;
import static org.junit.jupiter.api.Assertions.*;

public class DateSqueezerTest {
    ValueSqueezer squeezer = DATE_SQUEEZER;
    FieldDefinition field = new FieldDefinition(1, 6, 6, "date test field", FieldType.ALPHA, true, squeezer);

    @Test
    public void validStringDates() {
        assertNoErrors("231120");
    }

    @Test
    public void invalidStringDates() {
        assertFormatError("");
        assertFormatError("23/11/20");
        assertFormatError("1/1/20");
        assertFormatError("011320");
        assertFormatError("321120");
    }

    @Test
    public void validLocalDates() {
        assertNoErrors(LocalDate.now());
        assertNoErrors(null);
    }

    @Test
    public void invalidTypesForLocalDate() {
        assertFormatError(new Object());
        assertFormatError(new Date());
        assertFormatError(new GregorianCalendar());
        assertFormatError(System.currentTimeMillis());
        assertFormatError(LocalDateTime.now());
        assertFormatError(LocalTime.now());
    }

    public void assertNoErrors(Object value) {
        List<String> validationErrors = squeezer.validate(field, value);
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        if (value != null) {
            String stringValue = squeezer.squeeze(field, value);
            assertNotNull(stringValue);
            assertFalse(stringValue.isBlank());
        }
    }

    public void assertFormatError(Object value) {
        List<String> validationErrors = squeezer.validate(field, value);
        assertFalse(validationErrors.isEmpty(), validationErrors.toString());
    }
}
