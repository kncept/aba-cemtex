package com.kncept.abacemtex.parser;

import com.kncept.abacemtex.file.CemtexFile;
import com.kncept.abacemtex.file.FooterRecord;
import com.kncept.abacemtex.file.HeaderRecord;
import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.record.CemtexRecord;
import com.kncept.abacemtex.file.record.RecordDefinition;
import com.kncept.abacemtex.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<CemtexRecord> records = new ArrayList<>();

    public Parser(InputStream in) throws IOException {
        BufferedReader bIn = new BufferedReader(new InputStreamReader(in));
        String line = bIn.readLine();
        while (line != null) {
            parseLine(line);
            line = bIn.readLine();
        }
    }

    public Parser(List<String> in) {
        in.forEach(this::parseLine);
    }

    public Parser parseLine(String line) {
        CemtexRecord record = RecordDefinition.recordBuilderForPrefix(line);
        if (record == null) throw new IllegalArgumentException("Does not start with a valid type indicator: " + line);
        fill(record, line);
        records.add(record);
        return this;
    }

    private void fill(CemtexRecord record, String line) {
        if (line.length() < 120) line = line + StringUtils.blankString(120 - line.length());
        for(FieldDefinition field: record.definition.fields) {
            String subLine = line.substring(field.startPos - 1, field.endPos);
            Object fieldValue = field.identify(subLine);
            record.value(field, fieldValue);
        }
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        for(int i = 0; i < records.size(); i++) {
            // TODO.....................................
        }
        return errors;
    }

    public CemtexFile cemtexFile() {
        if (records.size() < 2) throw new IllegalStateException("Not enough records for a complete file");
        CemtexFile file = new CemtexFile();
        file.header = (HeaderRecord) records.get(0);
        file.body.addAll((List)records.subList(1, records.size() - 1));
        file.footer = (FooterRecord)records.get(records.size() - 1);
        return file;
    }



}
