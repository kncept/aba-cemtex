package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

public class FooterRecord extends CemtexRecord<FooterRecord> {
    public FooterRecord() {
        super(RecordDefinition.TYPE_7);
        value("Record type", 7);
    }

    FooterRecord netTotal(long total) {
        return value("Net Total Amount", total);
    }

    FooterRecord creditTotal(long total) {
        return value("Credit Total Amount", total);
    }

    FooterRecord debitTotal(long total) {
        return value("Debit Total Amount", total);
    }

    FooterRecord itemCount(int total) {
        return value("Batch Total Item Count", total);
    }


}
