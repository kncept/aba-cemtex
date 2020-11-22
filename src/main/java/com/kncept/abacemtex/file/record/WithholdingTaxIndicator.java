package com.kncept.abacemtex.file.record;

public enum WithholdingTaxIndicator {
    blank(" "),
    newOrVariedDetails("N"),
    dividendPaidToTaxAgreementCountry("W"),
    dividendPaidToAnyOtherCountry("X"),
    interestPaidToNonResident("Y");
//                    "\"N\" – for new or varied Bank/State/Branch number or name details, otherwise blank filled.\n" +
//                    "Withholding Tax Indicators:\n" +
//                    "\"W\" – dividend paid to a resident of a country where a double tax agreement is in force.\n" +
//                    "\"X\" – dividend paid to a resident of any other country.\n" +
//                    "\"Y\" – interest paid to all non-residents.\n" +
//                    "The amount of withholding tax is to appear in character positions 113-120.\n" +
//                    "Note: Where withholding tax has been deducted the appropriate Indicator as shown above is to be used and will override the normal indicator. ")

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
