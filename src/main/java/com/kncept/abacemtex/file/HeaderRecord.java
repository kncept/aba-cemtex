package com.kncept.abacemtex.file;

import com.kncept.abacemtex.annotation.Mandatory;
import com.kncept.abacemtex.annotation.Optional;
import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

import java.time.LocalDate;
import java.time.LocalTime;

public class HeaderRecord extends CemtexRecord<HeaderRecord> {
    public HeaderRecord() {
        super(RecordDefinition.TYPE_0);
        value("Record type", 0);
        value("Sequence number", "01");
    }

    @Optional
    public HeaderRecord orgAccount(String bsb, String accountNumber) {
        return value("BSB", bsb).value("Account", accountNumber);
    }

    @Mandatory
    public HeaderRecord financialInstitution(String bankMnemonic) {
        return value("Name of User Financial Institution", bankMnemonic);
    }

    @Mandatory
    public HeaderRecord orgUserDetails(String userName, String userId) {
        return value("User Name", userName).value("User Identification number", userId);
    }

    @Mandatory
    public HeaderRecord description(String description) {
        return value("Description", description);
    }

    @Mandatory
    public HeaderRecord dateToBeProcessed(LocalDate date) {
        return value("Date to be processed", date);
    }

    @Optional
    public HeaderRecord timeToBeProcessed(LocalTime time) {
        return value("Time to be processed", time);
    }
}
