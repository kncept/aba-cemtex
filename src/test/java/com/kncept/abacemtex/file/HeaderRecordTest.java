package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.HeaderRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

public class HeaderRecordTest {

    @Test
    public void canBuildBasicRecord() {
        HeaderRecord headerRecord = new HeaderRecord()

        // TODO This should trigger 'bank specific' behaviour.
        .financialInstitution(RecordDefinition.BankDefinitionStrings.CommonwealthBankAustralia)
        .orgUserDetails("My Account Name", "0000")
        .description("Test File")
        .dateToBeProcessed(LocalDate.now())
        ;

        Set<String> validationErrors = headerRecord.validate();
        Assertions.assertTrue(validationErrors.isEmpty(), validationErrors.toString());

        System.out.println(headerRecord.toRecord());
    }
}
