package com.kncept.abacemtex.file.field;

import com.kncept.abacemtex.file.HeaderRecord;

import java.util.HashMap;
import java.util.Map;

public enum BankMnemonics implements BankMnemonic {
    AustraliaNewZealandBank("ANZ"),
    BankOfQueensland("BQL"),
    CommonwealthBankAustralia("CBA"),
    // NetBank doesn't require an APCA ID (also known as Bank Code). If prompted for an APCA ID you can enter 0000.
    // Account name with special characters (such as *, % or / ) won't be imported within your file. Please remove any special characters from the account names within your file before trying again
    NationalAustraliaBank("NAB"),
    WestpacBankingCorporation("WBC");

    private final String id;
    BankMnemonics(String id) {
        this.id = id;
    }

    @Override
    public String getMneumonic() {
        return id;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public void onMneumonicSet(HeaderRecord headerRecord) {
        // CBA has a 'default' org user id of "0000" - in a 6 digit field.
        if (this == CommonwealthBankAustralia) {
            headerRecord.orgUserId("0");
        }
    }
}