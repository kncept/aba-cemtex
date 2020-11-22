package com.kncept.abacemtex.file.field;

import com.kncept.abacemtex.file.field.FieldType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldTypeTest {

    @Test
    public void numericTypeAcceptsNumbers() {
        Assertions.assertTrue(FieldType.NUMERIC.isValid("0"));
        Assertions.assertTrue(FieldType.NUMERIC.isValid("000000"));
        Assertions.assertTrue(FieldType.NUMERIC.isValid("1"));
        Assertions.assertTrue(FieldType.NUMERIC.isValid("007"));
        Assertions.assertTrue(FieldType.NUMERIC.isValid("524672"));
    }

    @Test
    public void numericTypeRejectsNonNumbers() {
        Assertions.assertFalse(FieldType.NUMERIC.isValid(""));
        Assertions.assertFalse(FieldType.NUMERIC.isValid("a"));
        Assertions.assertFalse(FieldType.NUMERIC.isValid("A"));
        Assertions.assertFalse(FieldType.NUMERIC.isValid(" "));
        Assertions.assertFalse(FieldType.NUMERIC.isValid(" 0"));
        Assertions.assertFalse(FieldType.NUMERIC.isValid("-1"));
    }

    @Test
    public void alphaTypeAcceptsNormalStrings() {
        Assertions.assertTrue(FieldType.ALPHA.isValid(" "));
        Assertions.assertTrue(FieldType.ALPHA.isValid("    "));
        Assertions.assertTrue(FieldType.ALPHA.isValid("   test data"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("other data   "));
        Assertions.assertTrue(FieldType.ALPHA.isValid("524672"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("UPPERCASE"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("Mixed Case"));
    }

    @Test
    public void alphaTypeAcceptsDefinedSpecialCharacters() {
        Assertions.assertTrue(FieldType.ALPHA.isValid(" "));
        Assertions.assertTrue(FieldType.ALPHA.isValid("&"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("'"));
        Assertions.assertTrue(FieldType.ALPHA.isValid(","));
        Assertions.assertTrue(FieldType.ALPHA.isValid("-"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("."));
        Assertions.assertTrue(FieldType.ALPHA.isValid("/"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("+"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("$"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("!"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("%"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("("));
        Assertions.assertTrue(FieldType.ALPHA.isValid(")"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("*"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("#"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("="));
        Assertions.assertTrue(FieldType.ALPHA.isValid(":"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("?"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("["));
        Assertions.assertTrue(FieldType.ALPHA.isValid("]"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("_"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("^"));
        Assertions.assertTrue(FieldType.ALPHA.isValid("@"));
    }

    @Test
    public void alphaTypeRejectsInvalidCharacters() {
        Assertions.assertFalse(FieldType.ALPHA.isValid(""));
        Assertions.assertFalse(FieldType.ALPHA.isValid("£"));
        Assertions.assertFalse(FieldType.ALPHA.isValid("\""));
        Assertions.assertFalse(FieldType.ALPHA.isValid("{"));
        Assertions.assertFalse(FieldType.ALPHA.isValid("}"));
        Assertions.assertFalse(FieldType.ALPHA.isValid("~"));
        Assertions.assertFalse(FieldType.ALPHA.isValid(";"));
    }
}
