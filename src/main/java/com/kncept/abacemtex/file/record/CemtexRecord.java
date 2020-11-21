package com.kncept.abacemtex.file.record;

public abstract class CemtexRecord {
    public final RecordDefinition definition;

    public CemtexRecord(RecordDefinition definition) {
        this.definition = definition;
    }

    abstract String toRecord();
}
