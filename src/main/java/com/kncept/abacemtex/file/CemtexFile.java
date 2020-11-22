package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CemtexFile {
    HeaderRecord header;
    List<DetailRecord> body = new ArrayList<>();
    FooterRecord footer;

    public static Builder builder(Consumer<HeaderRecord> recordBuilder) {
        return new Builder(recordBuilder);
    }

    static class Builder {
        CemtexFile file = new CemtexFile();
        private Builder(Consumer<HeaderRecord> recordBuilder) {
            file = new CemtexFile();
            HeaderRecord record = new HeaderRecord();
            recordBuilder.accept(record);
            validate(record);
            file.header = record;
        }

        public Builder record(Consumer<DetailRecord> recordBuilder) {
            DetailRecord record = new DetailRecord();
            // TODO: pre calculate any values possible
            recordBuilder.accept(record);
            validate(record);
            file.body.add(record);
            return this;
        }

        public CemtexFile footer(Consumer<FooterRecord> recordBuilder) {
            FooterRecord record = new FooterRecord();
            // TODO: pre calculate any values possible
            recordBuilder.accept(record);
            validate(record);
            file.footer = record;
            return file;
        }

        private void validate(CemtexRecord record) {
            Set<String> validationErrors = record.validate();
            if (!validationErrors.isEmpty())
                throw new IllegalStateException("Validation Error: " + validationErrors.toString());
        }
    }


}
