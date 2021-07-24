package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kncept.abacemtex.file.field.valuesqueezer.BsbSqueezer.BSB_SQUEEZER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void ignoresNullAndEmptyValues() {
        assertNoErrors(null);
        assertNoErrors("");
        assertNoErrors(" ");
        assertNoErrors("       ");
    }

    @Test
    public void squeezesValuesWithoutAHyphen() {
        assertNoErrors("123456");
        assertNoErrors(123456);
    }

    @Test
    public void invaldBadBsb() {
        assertFormatError(new Object());
        assertFormatError("12-3456");
        assertFormatError("123--456");
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
