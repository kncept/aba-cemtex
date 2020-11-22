package com.kncept.abacemtex.file.field;

// UNVERIFIED, PARTIAL list of Australian banks
public enum BankMnemonic {

    AustraliaNewZealandBank("ANZ"),
    BankOfQueensland("BQL"),
    CommonwealthBankAustralia("CBA"),
    // NetBank doesn't require an APCA ID (also known as Bank Code). If prompted for an APCA ID you can enter 0000.
    // Account name with special characters (such as *, % or / ) won't be imported within your file. Please remove any special characters from the account names within your file before trying again
    NationalAustraliaBank("NAB"),
    Westpac("WBC");


    public final String id;
    BankMnemonic(String id) {
        this.id = id;
    }

    public static BankMnemonic lookup(String id) {
        if (id == null) return null;
        for(BankMnemonic knownBank: values()) {
            if (knownBank.id.equals(id))
                return knownBank;
        }
        return null;
    }
}
