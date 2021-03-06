package com.kncept.abacemtex.file.record;

import com.kncept.abacemtex.file.DetailRecord;
import com.kncept.abacemtex.file.FooterRecord;
import com.kncept.abacemtex.file.HeaderRecord;
import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.kncept.abacemtex.file.field.FieldType.ALPHA;
import static com.kncept.abacemtex.file.field.FieldType.NUMERIC;
import static com.kncept.abacemtex.file.field.valuesqueezer.BankMneumonicSqueezer.BANK_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.BlankSqueezer.BLANK_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.BsbSqueezer.BSB_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.DateSqueezer.DATE_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.DefaultValueSqueezer.defaultValueSqueezer;
import static com.kncept.abacemtex.file.field.valuesqueezer.FixedValueSqueezer.fixedValueSqueezer;
import static com.kncept.abacemtex.file.field.valuesqueezer.NumericSqueezer.NUMERIC_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.StringSqueezer.ACCOUNT_STRING_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.StringSqueezer.STRING_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.TimeSqueezer.TIME_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.TransactionCodeSqueezer.TX_CODE_SQUEEZER;
import static com.kncept.abacemtex.file.field.valuesqueezer.WithholdingTaxIndicatorSqueezer.WITHHOLDING_SQUEEZER;

public enum RecordDefinition {
    TYPE_0("Descriptive Record", 0, () -> new HeaderRecord(), new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true, fixedValueSqueezer("0")), // Must be '0'
            new FieldDefinition(2, 8, 7, "BSB", ALPHA, false, BSB_SQUEEZER), // Bank/State/Branch number of the funds account with a hyphen in the 4th character position. e.g. 013-999.
            new FieldDefinition(9, 17, 9, "Account", ALPHA, false, ACCOUNT_STRING_SQUEEZER), // Funds account number
            new FieldDefinition(18, 18, 1, "Reserved", ALPHA, false, BLANK_SQUEEZER), // Blank filled
            new FieldDefinition(19, 20, 2, "Sequence number", ALPHA, true, fixedValueSqueezer("01")), // Also called: Reel Sequence Number. // Must be ‘01’.
            new FieldDefinition(21, 23, 3, "Name of User Financial Institution", ALPHA, true, BANK_SQUEEZER), // bank mnemonic. See 'BankDefinitionStrings' for SOME values
            new FieldDefinition(24, 30, 7, "Reserved", ALPHA, false, BLANK_SQUEEZER), // "Must be blank filled
            new FieldDefinition(31, 56, 26, "User Name", ALPHA, true, STRING_SQUEEZER), // Must be User Preferred Name as advised by User's FI. Left justified, blank filled. All coded character set valid. Must not be all blanks.
            new FieldDefinition(57, 62, 6, "User Identification number", NUMERIC, true, NUMERIC_SQUEEZER), // Direct Entry User ID. Right-justified, zero-filled
            new FieldDefinition(63, 74, 12, "Description", ALPHA, true, STRING_SQUEEZER), // All coded character set valid. Must not be all blanks. Left justified, blank filled. Description of payments in the file (e.g. Payroll, Creditors etc.)
            new FieldDefinition(75, 80, 6, "Date to be processed", ALPHA, true, DATE_SQUEEZER), // Date on which the payment is to be processed. DDMMYY (e.g. 010111).
            new FieldDefinition(81, 84, 4, "Time to be processed", ALPHA, false, TIME_SQUEEZER), // Time on which the payment is to be processed. 24 hour format -  HHmm
            new FieldDefinition(85, 120, 36, "Reserved", ALPHA, false, BLANK_SQUEEZER), // Must be blank filled.
    }),
    TYPE_1("Detail Record", 1, () -> new DetailRecord(), new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true, fixedValueSqueezer("1")), // Must be '1''
            new FieldDefinition(2, 8, 7, "BSB", ALPHA, true, BSB_SQUEEZER), // Bank/State/Branch number with a hyphen in the 4thcharacter position. e.g. 013-999.
            new FieldDefinition(9, 17, 9, "Account", ALPHA, true, ACCOUNT_STRING_SQUEEZER), // Numeric, alpha, hyphens & blanks are valid.  Right justified, blank filled. Leading zeros that are part of an Account Number must be included.
            new FieldDefinition(18, 18, 1, "Withholding Tax Indicator", ALPHA,  false, defaultValueSqueezer(" ", WITHHOLDING_SQUEEZER)),
            new FieldDefinition(19, 20, 2, "Transaction Code", NUMERIC, true, TX_CODE_SQUEEZER), // Select from the following options as appropriate: 50 General Credit. 53 Payroll. 54 Pension. 56 Dividend. 57 Debenture Interest. 13 General Debit.
            new FieldDefinition(21, 30, 10, "Amount", NUMERIC, true, NUMERIC_SQUEEZER), // Must be greater than zero. Shown in cents without punctuations. Right justified, zero filled. Unsigned
            new FieldDefinition(31, 62, 32, "Account title", ALPHA, true, STRING_SQUEEZER),
            new FieldDefinition(63, 80, 18, "Lodgement Reference", ALPHA, true, STRING_SQUEEZER), // Produced on the recipient’s Account Statement All coded character set valid. Field must be left justified
            new FieldDefinition(81, 87, 7, "Trace BSB", ALPHA, true, BSB_SQUEEZER), // source bsb
            new FieldDefinition(88, 96, 9, "Trace Account", ALPHA, true, ACCOUNT_STRING_SQUEEZER), // source account number
            new FieldDefinition(97, 112, 16, "Remitter", ALPHA, true, STRING_SQUEEZER), // Produced on the recipient’s Account Statement. "Name of originator of the entry. This may vary from Name of the User. All coded character set valid. Must not contain all blanks. Left justified, blank filled."),
            new FieldDefinition(113, 120, 8, "Withholding amount", NUMERIC, false, defaultValueSqueezer(0, NUMERIC_SQUEEZER)) // Amount of Withholding Tax. Numeric only valid. Show in cents without punctuation. Right justified, zero filled. Unsigned.
    }),
    TYPE_7("Batch Control Record ", 7, () -> new FooterRecord(), new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true, fixedValueSqueezer("7")), //"Must be '7'
            new FieldDefinition(2, 8, 7, "Reserved", ALPHA, true, fixedValueSqueezer("999-999")), // Must be '999-999'
            new FieldDefinition(9, 20, 12, "Reserved", ALPHA, false, BLANK_SQUEEZER), // Must be blank filled.
            new FieldDefinition(21, 30, 10, "Net Total Amount", NUMERIC, true, NUMERIC_SQUEEZER), // Batch Credit Total Amount minus Batch Debit Total Amount. Right justified, zero filled, unsigned, two decimal places are implied (e.g. $1001.21 is stored as ‘0000100121’).  "Numeric only valid. Must equal the difference between File Credit & File Debit Total Amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(31, 40, 10, "Credit Total Amount", NUMERIC, false, NUMERIC_SQUEEZER), // "Numeric only valid. Must equal the accumulated total of credit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(41, 50, 10, "Debit Total Amount", NUMERIC, false, NUMERIC_SQUEEZER), // "Numeric only valid. Must equal the accumulated total of debit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(51, 74, 24, "Reserved", ALPHA, false, BLANK_SQUEEZER), // Must be blank filled.,
            new FieldDefinition(75, 80, 6, "Batch Total Item Count", NUMERIC, true, NUMERIC_SQUEEZER), // Numeric only valid. Must equal accumulated number of Record Type 1 items on the file. Right justified, zero filled.
            new FieldDefinition(81, 120, 40, "Reserved", ALPHA, false, BLANK_SQUEEZER) // Must be blank filled.
    });

    public final String name;
    public final int type;
    private final Supplier<CemtexRecord> factory;
    public final List<FieldDefinition> fields;
    RecordDefinition(String name, int type, Supplier<CemtexRecord> factory, FieldDefinition[] fields) {
        this.name = name;
        this.type = type;
        this.factory = factory;
        this.fields = Arrays.asList(fields);
    }
    public CemtexRecord newRecord() {
        return factory.get();
    }

    public static CemtexRecord recordBuilderForPrefix(String line) {
        if(line == null) return null;
        for(RecordDefinition rd: RecordDefinition.values()) {
            if (line.startsWith(Integer.toString(rd.type))) {
                return rd.newRecord();
            }
        }
        return null;
    }

    public FieldDefinition fieldDefinition(String fieldName) {
        return fields
                .stream()
                .filter(field -> field.description.equals(fieldName))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Field \"" + fieldName + "\" not part of " + name + " definition"));
    }
}
