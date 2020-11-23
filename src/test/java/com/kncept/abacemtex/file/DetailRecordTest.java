package com.kncept.abacemtex.file;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetailRecordTest {

    @Test
    public void canBuildBasicRecord() {
        DetailRecord record = new DetailRecord()
                .orgAccount("007-007", "777777777")
                .standardOutbound(new BigDecimal("43.21"))
                .participantAccount("123-123", "999 9999", "destAcct")
                .reference("Lodgement ---- Ref")
                .remitter("abacemtex-text")
        ;

        List<String> validationErrors = record.validate();
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        String str = record.toRecord();
        assertEquals(120, str.length());
        assertEquals("1123-123 999 9999 500000004321destAcct                        Lodgement ---- Ref007-007777777777abacemtex-text  00000000", str);
    }
}
