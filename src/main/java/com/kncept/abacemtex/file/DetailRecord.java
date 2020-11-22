package com.kncept.abacemtex.file;

import com.kncept.abacemtex.TransactionCode;
import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

import java.math.BigDecimal;

public class DetailRecord extends CemtexRecord<DetailRecord> {
    public DetailRecord() {
        super(RecordDefinition.TYPE_1);
        value("Record type", 1);
    }

    public DetailRecord participantAccount(String bsb, String accountNumber, String accountName) {
        return value("BSB", bsb).value("Account", accountNumber).value("Account title", accountName);
    }

    public DetailRecord transactionCode(TransactionCode code) {
        return value("Transaction Code", code);
    }

    public DetailRecord amount(long amountInCents) {
        return value("Amount", amountInCents);
    }

    public DetailRecord amount(BigDecimal amountInDollars) {
        if (amountInDollars == null) value("Amount", null);
        return amount(amountInDollars.multiply(BigDecimal.valueOf(100)).longValue());
    }

    public boolean isOutbound() {
        TransactionCode transactionCode = (TransactionCode) getValue("Transaction Code");
        if (transactionCode == null) throw new IllegalStateException("No direction yet set");
        return transactionCode != TransactionCode.code_13; // the only inbound payment
    }

    public DetailRecord standardOutbound(BigDecimal amountInDollars) {
        return transactionCode(TransactionCode.code_50).amount(amountInDollars);
    }

    public DetailRecord standardInbound(BigDecimal amountInDollars) {
        return transactionCode(TransactionCode.code_13).amount(amountInDollars);
    }

    public DetailRecord reference(String reference) {
        return value("Lodgement Reference", reference);
    }

    // will be auto populated if the header org account is set
    public DetailRecord orgAccount(String bsb, String accountNumber) {
        return value("Trace BSB", bsb).value("Trace Account", accountNumber);
    }

    // org account name?
    public DetailRecord remitter(String remitter) {
        return value("Remitter", remitter);
    }

}
