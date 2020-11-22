package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.field.BankMnemonic;
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

    public HeaderRecord orgAccount(String bsb, String accountNumber) {
        return value("BSB", bsb).value("Account", accountNumber);
    }

    public HeaderRecord financialInstitution(String bankMnemonic) {
        BankMnemonic knownBank = BankMnemonic.lookup(bankMnemonic);
        if (knownBank != null) return financialInstitution(knownBank);
        return value("Name of User Financial Institution", bankMnemonic);
    }
    public HeaderRecord financialInstitution(BankMnemonic bankMnemonic) {
        if (bankMnemonic == BankMnemonic.CommonwealthBankAustralia) {
            orgUserId("0000");
        }
        return value("Name of User Financial Institution", bankMnemonic);
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
        return value("Date to be processed", date);
    }

    public HeaderRecord timeToBeProcessed(LocalTime time) {
        return value("Time to be processed", time);
    }
}
