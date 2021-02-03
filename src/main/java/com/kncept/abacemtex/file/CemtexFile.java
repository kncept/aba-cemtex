package com.kncept.abacemtex.file;

import com.kncept.abacemtex.file.record.CemtexRecord;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CemtexFile {
    public static final String lineDelimiter = "\r\n"; // the old CrLf
    public HeaderRecord header;
    public final List<DetailRecord> body = new ArrayList<>();
    public FooterRecord footer;

    public CemtexFile() {}

    public static InitialBuilder initialBuilder() {
        return new InitialBuilder();
    }
    public static Builder builder(Consumer<HeaderRecord> recordBuilder) {
        return new Builder(recordBuilder, true);
    }
    public static Builder builderWithNoValidation(Consumer<HeaderRecord> recordBuilder) {
        return new Builder(recordBuilder, false);
    }

    /**
     * performs validations at the file level.
     * @return a list of all validation errors.
     */
    public List<String> validate() {
        final List<String> errors = new ArrayList<>();
        if (header != null) errors.addAll(applyPrefix("Line 0 ", header.validate()));
        for(int i = 0; i < body.size(); i++) {
            errors.addAll(applyPrefix("Line " + (i + 1) + " ", body.get(i).validate()));
        }
        if (footer != null) errors.addAll(applyPrefix("Line " + (body.size() + 1) + " ", footer.validate()));
        // TODO: Add amount total validation
        return errors;
    }

    private List<String> applyPrefix(String prefix, List<String> errors) {
        return errors.stream().map(it -> prefix + it).collect(Collectors.toList());
    }

    public List<String> toRecords() {
        List<String> records = new ArrayList<>();
        if (header != null) records.add(header.toRecord());
        records.addAll(body.stream().map(CemtexRecord::toRecord).collect(Collectors.toList()));
        if (footer != null) records.add(footer.toRecord());
        return records;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(header.toRecord());
        sb.append(lineDelimiter);
        for(DetailRecord record: body) {
            sb.append(record.toRecord());
            sb.append(lineDelimiter);
        }
        sb.append(footer.toRecord());
        sb.append(lineDelimiter);
        return sb.toString();
    }

    public byte[] toFileBytes() throws UnsupportedEncodingException {
        return toFileString().getBytes("US-ASCII");
    }

    public static class InitialBuilder {
        private static Builder header(Consumer<HeaderRecord> recordBuilder) {
            return new Builder(recordBuilder, true);
        }
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

        public static FooterRecord generateFooterRecord(List<DetailRecord> body){
            FooterRecord record = new FooterRecord();

            long debitTotal = 0;
            long creditTotal = 0;

            for(DetailRecord detail: body) {
                long amount = ((BigInteger)detail.getValue(detail.fieldDefinition("Amount"))).longValue();
                if (detail.isOutbound()) {
                    creditTotal += amount;
                } else {
                    debitTotal += amount;
                }
            }
            record.creditTotal(creditTotal);
            record.debitTotal(debitTotal);
            record.netTotal(Math.abs(creditTotal - debitTotal));
            record.itemCount(body.size());
            return record;
        }

        public CemtexFile build(Consumer<FooterRecord> recordBuilder) {
            FooterRecord record = generateFooterRecord(file.body);
            recordBuilder.accept(record);
            if (validation) validate(record);
            file.footer = record;
            return file;
        }

        public CemtexFile build() {
            return build(record -> {});
        }

        private void validate(CemtexRecord record) {
            List<String> validationErrors = record.validate();
            if (!validationErrors.isEmpty())
                throw new IllegalStateException("Validation Error: " + validationErrors.toString());
        }

        public List<String> validate() {
            return file.validate();
        }
    }
}
