package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

import java.util.Set;

public class FooterRecord extends CemtexRecord<FooterRecord> {
    private final static class keys {
        private static final String netTotal = "Net Total Amount";
        private static final String creditTotal = "Credit Total Amount";
        private static final String debitTotal = "Debit Total Amount";
        private static final String itemCount = "Batch Total Item Count";
    }
    public FooterRecord() {
        super(RecordDefinition.TYPE_7);
    }

    FooterRecord netTotal(long total) {
        return value(keys.netTotal, total);
    }

    FooterRecord creditTotal(long total) {
        return value(keys.creditTotal, total);
    }

    FooterRecord debitTotal(long total) {
        return value(keys.debitTotal, total);
    }

    FooterRecord itemCount(int total) {
        return value(keys.itemCount, total);
    }

    public Set<String> validate() {
        Set<String> errors = super.validate();
        Long netTotal = (Long)getValue(keys.netTotal);
        Long creditTotal = (Long)getValue(keys.creditTotal);
        Long debitTotal = (Long)getValue(keys.debitTotal);
        if (netTotal.longValue() != Math.abs(creditTotal - debitTotal)) errors.add("Net Total not correctly calculated");
        return errors;
    }


}
