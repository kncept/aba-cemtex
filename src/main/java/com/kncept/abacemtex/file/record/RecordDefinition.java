package com.kncept.abacemtex.file.record;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Arrays;
import java.util.List;

import static com.kncept.abacemtex.file.field.FieldType.ALPHA;
import static com.kncept.abacemtex.file.field.FieldType.NUMERIC;

/**
 * Sources for field definitions include:
 * https://www.anz.com/Documents/AU/corporate/clientfileformats.pdf
 * https://www.cemtexaba.com/aba-format/cemtex-aba-file-format-details
 */
public class RecordDefinition {
    public static RecordDefinition TYPE_0 = new RecordDefinition(
            "Descriptive Record", 0, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true), // Must be '0'
            new FieldDefinition(2, 8, 7, "BSB", ALPHA, false), // Bank/State/Branch number of the funds account with a hyphen in the 4th character position. e.g. 013-999.
            new FieldDefinition(9, 17, 9, "Account", ALPHA, false), // Funds account number
            new FieldDefinition(18, 18, 1, "Reserved", ALPHA, false),// Blank filled
            new FieldDefinition(19, 20, 2, "Sequence number", ALPHA, true), // Also called: Reel Sequence Number. // Must be ‘01’.
            new FieldDefinition(21, 23, 3, "Name of User Financial Institution", ALPHA, true), // bank mnemonic. See 'BankDefinitionStrings' for SOME values
            new FieldDefinition(24, 30, 7, "Reserved", ALPHA, false), // "Must be blank filled
            new FieldDefinition(31, 56, 26, "User Name", ALPHA, true), // Must be User Preferred Name as advised by User's FI. Left justified, blank filled. All coded character set valid. Must not be all blanks.
            new FieldDefinition(57, 62, 6, "User Identification number", NUMERIC, true), // Direct Entry User ID. Right-justified, zero-filled
            new FieldDefinition(63, 74, 12, "Description", ALPHA, true), // All coded character set valid. Must not be all blanks. Left justified, blank filled. Description of payments in the file (e.g. Payroll, Creditors etc.)
            new FieldDefinition(75, 80, 6, "Date to be processed", ALPHA, true), // Date on which the payment is to be processed. DDMMYY (e.g. 010111).
            new FieldDefinition(81, 84, 4, "Time to be processed", ALPHA, false), // Time on which the payment is to be processed. 24 hour format -  HHmm
            new FieldDefinition(85, 120, 36, "Reserved", ALPHA, false), // Must be blank filled.
    });

    public static RecordDefinition TYPE_1 = new RecordDefinition(
            "Detail Record", 1, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true), // Must be '1''
            new FieldDefinition(2, 8, 7, "BSB", ALPHA, true), // Bank/State/Branch number with a hyphen in the 4thcharacter position. e.g. 013-999.
            new FieldDefinition(9, 17, 9, "Account", ALPHA, true), // Numeric, alpha, hyphens & blanks are valid.  Right justified, blank filled. Leading zeros that are part of an Account Number must be included.
            new FieldDefinition(18, 18, 1, "Withholding Tax Indicator", ALPHA,  false),
//                    "\"N\" – for new or varied Bank/State/Branch number or name details, otherwise blank filled.\n" +
//                    "Withholding Tax Indicators:\n" +
//                    "\"W\" – dividend paid to a resident of a country where a double tax agreement is in force.\n" +
//                    "\"X\" – dividend paid to a resident of any other country.\n" +
//                    "\"Y\" – interest paid to all non-residents.\n" +
//                    "The amount of withholding tax is to appear in character positions 113-120.\n" +
//                    "Note: Where withholding tax has been deducted the appropriate Indicator as shown above is to be used and will override the normal indicator. ")
            new FieldDefinition(19, 20, 2, "Transaction Code", NUMERIC, true), // Select from the following options as appropriate: 50 General Credit. 53 Payroll. 54 Pension. 56 Dividend. 57 Debenture Interest. 13 General Debit.
            new FieldDefinition(21, 30, 10, "Amount", NUMERIC, true), // Must be greater than zero. Shown in cents without punctuations. Right justified, zero filled. Unsigned
            new FieldDefinition(31, 62, 32, "Account title", ALPHA, true),
            new FieldDefinition(63, 80, 18, "Lodgement Reference", ALPHA, true), // Produced on the recipient’s Account Statement All coded character set valid. Field must be left justified
            new FieldDefinition(81, 87, 7, "Trace BSB", ALPHA, true), // source bsb
            new FieldDefinition(88, 96, 9, "Trace Account", ALPHA, true), // source account number
            new FieldDefinition(97, 112, 16, "Remitter", ALPHA, true), // Produced on the recipient’s Account Statement. "Name of originator of the entry. This may vary from Name of the User. All coded character set valid. Must not contain all blanks. Left justified, blank filled."),
            new FieldDefinition(113, 120, 8, "Withholding amount", NUMERIC, false) // Amount of Withholding Tax. Numeric only valid. Show in cents without punctuation. Right justified, zero filled. Unsigned.
    });

    public static RecordDefinition TYPE_7 = new RecordDefinition(
            "Batch Control Record ", 7, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record type", NUMERIC, true), //"Must be '7'
            new FieldDefinition(2, 8, 7, "Reserved", ALPHA, true), // Must be '999-999'
            new FieldDefinition(9, 20, 12, "Reserved", ALPHA, false), // Must be blank filled.
            new FieldDefinition(21, 30, 10, "Net Total Amount", NUMERIC, true), // Batch Credit Total Amount minus Batch Debit Total Amount. Right justified, zero filled, unsigned, two decimal places are implied (e.g. $1001.21 is stored as ‘0000100121’).  "Numeric only valid. Must equal the difference between File Credit & File Debit Total Amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(31, 40, 10, "Credit Total Amount", NUMERIC, false), // "Numeric only valid. Must equal the accumulated total of credit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(41, 50, 10, "Debit Total Amount", NUMERIC, false), // "Numeric only valid. Must equal the accumulated total of debit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(51, 74, 24, "Reserved", ALPHA, false), // Must be blank filled.,
            new FieldDefinition(75, 80, 6, "Batch Total Item Count", NUMERIC, true), //"Numeric only valid. Must equal accumulated number of Record Type 1 items on the file. Right justified, zero filled."),
            new FieldDefinition(81, 120, 40, "Reserved", ALPHA, false) // "Must be blank filled."),
    });

    public final String name;
    public final int type;
    public final List<FieldDefinition> fields;
    public RecordDefinition(String name, int type, FieldDefinition[] fields) {
        this.name = name;
        this.type = type;
        this.fields = Arrays.asList(fields);
    }
}
