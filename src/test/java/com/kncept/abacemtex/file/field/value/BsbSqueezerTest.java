package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.kncept.abacemtex.file.field.value.BsbSqueezer.BSB_SQUEEZER;
import static org.junit.jupiter.api.Assertions.*;

public class BsbSqueezerTest {

    ValueSqueezer squeezer = BSB_SQUEEZER;
    FieldDefinition field = new FieldDefinition(1, 7, 7, "bsb test field", FieldType.ALPHA, true, squeezer);


    @Test
    public void validateGoodBsb() {
        assertNoErrors("999-999");
        assertNoErrors("000-000");
        assertNoErrors("123-456");
    }

    @Test
    public void ignoresNullValues() {
        assertNoErrors(null);
    }

    @Test
    public void invaldBadBsb() {
        assertFormatError("");
        assertFormatError(new Object());
        assertFormatError("123456");
        assertFormatError(123456);
        assertFormatError("12-3456");
        assertFormatError("123--456");
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
