package com.kncept.abacemtex.file.record;

public class HeaderRecord extends CemtexRecord{
    public HeaderRecord() {
        super(RecordDefinition.TYPE_0);
    }

    @Override
    String toRecord() {
        return "";
    }
}
