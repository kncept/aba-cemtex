package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

public class FooterRecord extends CemtexRecord<FooterRecord> {
    public FooterRecord() {
        super(RecordDefinition.TYPE_7);
        value("Record type", 7);
    }
}
