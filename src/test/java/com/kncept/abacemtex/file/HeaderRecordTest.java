package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.field.BankMnemonic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderRecordTest {

    @Test
    public void canBuildBasicRecord() {
        HeaderRecord record = new HeaderRecord()
        .financialInstitution(BankMnemonic.CommonwealthBankAustralia)
        .orgUserName("My Account Name")
        .description("Test File")
        .dateToBeProcessed("231120")
        ;

        List<String> validationErrors = record.validate();
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        String str = record.toRecord();
        assertEquals(120, str.length());
        assertEquals("0                 01CBA       My Account Name           000000Test File   231120                                        ", str);
    }

    @Test
    public void cbaAutoPopulatesBankUserId() {
        HeaderRecord record = new HeaderRecord();
        assertNull(record.getValue("User Identification number"));
        record.financialInstitution(BankMnemonic.CommonwealthBankAustralia);
        assertEquals(record.getValue("User Identification number"), "0000");
    }

    @Test
    public void orgAccount() {
        HeaderRecord record = new HeaderRecord()
                .orgAccount("123-456", "12345678")
                .financialInstitution(BankMnemonic.NationalAustraliaBank)
                .orgUserId("000000")
                .orgUserName("My Account Name")
                .description("Test File")
                .dateToBeProcessed("231120");

        List<String> validationErrors = record.validate();
        assertTrue(validationErrors.isEmpty(), validationErrors.toString());
    }
}
