package com.kncept.abacemtex.file;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterlRecordTest {

    @Test
    public void canBuildBasicRecord() {
        FooterRecord record = new FooterRecord()
                .creditTotal(7)
                .debitTotal(5)
                .netTotal(2)
                .itemCount(1)
        ;

        List<String> validationErrors = record.validate();
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        String str = record.toRecord();
        assertEquals(120, str.length());
        assertEquals("7999-999            000000000200000000070000000005                        000001                                        ", str);
    }
}
