package com.kncept.abacemtex.file.compactor;

import com.kncept.abacemtex.file.DetailRecord;
import com.kncept.abacemtex.parser.Parser;

import java.math.BigInteger;
import java.util.List;

/**
 * Merges duplicated rows together.
 */
public class SimpleCompactor extends BaseCompactor {

    public SimpleCompactor() {
        super("BSB", "Account", "Account title", "Lodgement Reference", "Transaction Code");
    }

    @Override
    public DetailRecord compact(List<DetailRecord> records) {
        BigInteger amount = sumByTransactionCode(records).get(records.get(0).getValue(field("Transaction Code")));
        DetailRecord compacted = new DetailRecord();
        Parser.fill(compacted, records.get(0).toRecord());
        compacted.amount(amount);
        return compacted;
    }
}
