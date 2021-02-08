package com.kncept.abacemtex.file.field.value.withholdingtax;

public enum WithholdingTaxIndicator {
    blank(" "),
    newOrVariedDetails("N"),
    dividendPaidToTaxAgreementCountry("W"),
    dividendPaidToAnyOtherCountry("X"),
    interestPaidToNonResident("Y");

    public final String id;
    WithholdingTaxIndicator(String id) {
        this.id = id;
    }

    public static WithholdingTaxIndicator lookup(String id) {
        if (id == null) return null;
        for(WithholdingTaxIndicator code: values()) {
            if (code.id.equals(id))
                return code;
        }
        return null;
    }
}
