package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.field.BankMnemonic;
import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

import java.time.LocalDate;
import java.time.LocalTime;

public class HeaderRecord extends CemtexRecord<HeaderRecord> {
    public HeaderRecord() {
        super(RecordDefinition.TYPE_0);
    }

    public HeaderRecord orgAccount(String bsb, String accountNumber) {
        return value("BSB", bsb).value("Account", accountNumber);
    }

    public HeaderRecord financialInstitution(String bankMnemonic) {
        return financialInstitution(BankMnemonic.lookup(bankMnemonic));
    }

    public HeaderRecord financialInstitution(BankMnemonic bankMnemonic) {
        if (bankMnemonic != null) bankMnemonic.onMneumonicSet(this);
        return value(fieldDefinition("Name of User Financial Institution"), bankMnemonic);
    }

    public HeaderRecord orgUserName(String userName) {
        return value("User Name", userName);
    }

    public HeaderRecord orgUserId(String userId) {
        return value("User Identification number", userId);
    }

    public HeaderRecord description(String description) {
        return value("Description", description);
    }

    public HeaderRecord dateToBeProcessed(LocalDate date) {
        return value(fieldDefinition("Date to be processed"), date);
    }

    public HeaderRecord dateToBeProcessed(String date) {
        return value("Date to be processed", date);
    }

    public HeaderRecord timeToBeProcessed(LocalTime time) {
        return value(fieldDefinition("Time to be processed"), time);
    }
}
