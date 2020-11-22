package com.kncept.abacemtex;

/**
 * 13 (General Debit - inbound to org) and 50 (General Credit - outbound from org) are likely to be your most common use case
 */
public enum TransactionCode {
    code_13(13, "Externally initiated debit items"),
    code_50(50, "Externally initiated credit items with the exception of those bearing Transaction Codes"),
    code_51(51, "Australian Government Security Interest"),
    code_52(52, "Family Allowance"),
    code_53(53, "Pay"),
    code_54(54, "Pension"),
    code_55(55, "Allotment"),
    code_56(56, "Dividend"),
    code_57(57, "Debenture/Note Interest");

    public final int code;
    public final String transactionDescription;
    TransactionCode(int code, String transactionDescription) {
        this.code = code;
        this.transactionDescription = transactionDescription;
    }
}
