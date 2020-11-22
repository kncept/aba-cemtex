package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;

public class DetailRecord extends CemtexRecord<DetailRecord> {
    public DetailRecord() {
        super(RecordDefinition.TYPE_1);
        value("Record type", 1);
    }
}
