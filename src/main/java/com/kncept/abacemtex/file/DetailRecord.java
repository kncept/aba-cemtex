package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.TransactionCode;
import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DetailRecord extends CemtexRecord<DetailRecord> {
    public DetailRecord() {
        super(RecordDefinition.TYPE_1);
    }

    /**
     * Client account number
     * @param bsb
     * @param accountNumber
     * @param accountName
     * @return
     */
    public DetailRecord participantAccount(String bsb, String accountNumber, String accountName) {
        return value("BSB", bsb)
                .value("Account", accountNumber)
                .value("Account title", accountName);
    }

    /**
     * Determines the transaction type and direction.<br>
     * 13 or 50 will probably be the most common.
     * @param code
     * @return
     */
    public DetailRecord transactionCode(TransactionCode code) {
        return value(fieldDefinition("Transaction Code"), code);
    }

    /**
     * The magnitude of the amount to transfer, in cents.
     * @param amountInCents
     * @return
     */
    public DetailRecord amount(long amountInCents) {
        return amount(BigInteger.valueOf(amountInCents));
    }

    /**
     * The magnitude of the amount to transfer, in cents.
     * @param amountInCents
     * @return
     */
    public DetailRecord amount(BigInteger amountInCents) {
        return value(fieldDefinition("Amount"), amountInCents);
    }

    /**
     * The magnitude of the amount to transfer, in Dollars.
     * @param amountInDollars
     * @return
     */
    public DetailRecord amount(BigDecimal amountInDollars) {
        if (amountInDollars == null) value("Amount", null);
        return amount(amountInDollars.multiply(BigDecimal.valueOf(100)).longValue());
    }

    public boolean isOutbound() {
        TransactionCode transactionCode = (TransactionCode) getValue(fieldDefinition("Transaction Code"));
        if (transactionCode == null) throw new IllegalStateException("No direction yet set");
        return transactionCode.isOutbound();
    }

    /**
     * Accepts a +ve amount of money to transfer from the org TO the client  account, in Dollars.
     * @param amountInDollars
     * @return
     */
    public DetailRecord standardOutbound(BigDecimal amountInDollars) {
        return transactionCode(TransactionCode.code_50).amount(amountInDollars);
    }

    /**
     * Accepts a +ve amount of money to transfer from the client TO the org account, in Dollars.
     * @param amountInDollars
     * @return
     */
    public DetailRecord standardInbound(BigDecimal amountInDollars) {
        return transactionCode(TransactionCode.code_13).amount(amountInDollars);
    }

    /**
     * Accepts a SIGNED amount, +ve for outbound and -ve for inbound, in Dollars.
     * @param amountInDollars
     * @return
     */
    public DetailRecord standardTransaction(BigDecimal amountInDollars) {
        if (amountInDollars.compareTo(BigDecimal.ZERO) > 0) return standardOutbound(amountInDollars);
        if (amountInDollars.compareTo(BigDecimal.ZERO) < 0) return standardInbound(amountInDollars.abs());
        throw new IllegalArgumentException("Cannot transfer zero amounts");
    }

    /**
     * The reference that will appear on the clients bank statement.
     * @param reference
     * @return
     */
    public DetailRecord reference(String reference) {
        return value("Lodgement Reference", reference);
    }

    /**
     * Will be auto populated if the header org account is set.<br>
     * Just do that.
     * @param bsb
     * @param accountNumber
     * @return
     */
    public DetailRecord orgAccount(String bsb, String accountNumber) {
        return value("Trace BSB", bsb).value("Trace Account", accountNumber);
    }

    /**
     * Name of the originator of this transaction.
     * Appears on the clients bank statement.
     * @param remitter
     * @return
     */
    // org account name?
    public DetailRecord remitter(String remitter) {
        return value("Remitter", remitter);
    }

}
