package com.kncept.abacemtex.file.compactor;

import com.kncept.abacemtex.file.DetailRecord;
import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.record.RecordDefinition;
import com.kncept.abacemtex.file.field.value.transactioncode.TransactionCode;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCompactor implements Compactor {

    private final String fields[];
    public BaseCompactor(String... fields) {
        if (fields == null) throw new NullPointerException();
        if (fields.length == 0) throw new IllegalArgumentException();
        for(String field: fields) field(field);
        this.fields = fields;
    }

    public FieldDefinition field(String field) {
        // throws IllegalStateException if field is missing
        return RecordDefinition.TYPE_1.fieldDefinition(field);
    }

    public Map<TransactionCode, BigInteger> sumByTransactionCode(List<DetailRecord> records) {
        Map<TransactionCode, BigInteger> sums = new HashMap<>();
        for(DetailRecord record: records) {
            sums.merge(
                    (TransactionCode)record.getValue(field("Transaction Code")),
                    (BigInteger)record.getValue(field("Amount")),
                    BigInteger::add
            );
        }
        return sums;
    }

    @Override
    public String compactionKey(DetailRecord record) {
        StringBuilder sb = new StringBuilder();
        for(String field: fields) sb.append(record.getValue(field));
        return sb.toString();
    }
}
