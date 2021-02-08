package com.kncept.abacemtex.file.field.value.transactioncode;

/**
 * 13 (General Debit - inbound to org) and 50 (General Credit - outbound from org) are likely to be your most common use case
 */
public enum TransactionCode {
    code_13("13", "Externally initiated debit items"),
    code_50("50", "Externally initiated credit items with the exception of those bearing Transaction Codes"),
    code_51("51", "Australian Government Security Interest"),
    code_52("52", "Family Allowance"),
    code_53("53", "Pay"),
    code_54("54", "Pension"),
    code_55("55", "Allotment"),
    code_56("56", "Dividend"),
    code_57("57", "Debenture/Note Interest");

    public final String id;
    public final String transactionDescription;
    TransactionCode(String id, String transactionDescription) {
        this.id = id;
        this.transactionDescription = transactionDescription;
    }

    public static TransactionCode lookup(String id) {
        if (id == null) return null;
        for(TransactionCode code: values()) {
            if (code.id.equals(id))
                return code;
        }
        return null;
    }

    public boolean isOutbound() {
        return this != code_13; //code 13 in INbound, the rest are OUTbound
    }
}
