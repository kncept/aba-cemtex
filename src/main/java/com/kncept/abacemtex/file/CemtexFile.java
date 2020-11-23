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
        return new Builder(recordBuilder, true);
    }
    public static Builder builderWithNoValidation(Consumer<HeaderRecord> recordBuilder) {
        return new Builder(recordBuilder, false);
    }

    public static class Builder {
        private final CemtexFile file;
        private final boolean validation;
        private Builder(Consumer<HeaderRecord> recordBuilder, boolean validation) {
            file = new CemtexFile();
            this.validation = validation;
            HeaderRecord record = new HeaderRecord();
            recordBuilder.accept(record);
            if (validation) validate(record);
            file.header = record;
        }

        public Builder record(Consumer<DetailRecord> recordBuilder) {
            DetailRecord record = new DetailRecord();

            //precalculate values where possible

            //if set, this will carry them forwards
            record.orgAccount(
                    (String)file.header.getValue("BSB"),
                    (String)file.header.getValue("Account"));
            record.remitter((String)file.header.getValue("User Name"));

            recordBuilder.accept(record);
            if (validation) validate(record);
            file.body.add(record);
            return this;
        }

        public CemtexFile build(Consumer<FooterRecord> recordBuilder) {
            FooterRecord record = new FooterRecord();

            long debitTotal = 0;
            long creditTotal = 0;

            for(DetailRecord detail: file.body) {
                long amount = (Long)detail.getValue("Amount");
                if (detail.isOutbound()) {
                    creditTotal += amount;
                } else {
                    debitTotal += amount;
                }
            }
            record.creditTotal(creditTotal);
            record.debitTotal(debitTotal);
            record.netTotal(Math.abs(creditTotal - debitTotal));
            record.itemCount(file.body.size());

            recordBuilder.accept(record);
            validate(record);
            if (validation) validate(record);
            file.footer = record;
            return file;
        }

        public CemtexFile build() {
            return build(record -> {});
        }

        private void validate(CemtexRecord record) {
            Set<String> validationErrors = record.validate();
            if (!validationErrors.isEmpty())
                throw new IllegalStateException("Validation Error: " + validationErrors.toString());
        }
    }


}
