package com.kncept.abacemtex.file.record;

import java.util.Arrays;
import java.util.List;

/**
 * https://www.cemtexaba.com/aba-format/cemtex-aba-file-format-details
 */
public class RecordDefinition {
    public static RecordDefinition TYPE_0 = new RecordDefinition(
            "Descriptive Record", (byte) 0, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record Type 0", "Must be '0'"),
            new FieldDefinition(2, 18, 17, "Blank", "Must be filled."),
            new FieldDefinition(19, 20, 2, "Reel Sequence Number", "Must be numeric commencing at 01.\n" +
                    "Right justified. Zero filled."),
            new FieldDefinition(21, 23, 3, "Name of User's Financial Institution", "Must be approved Financial Institution abbreviation. Bank of Queensland's abbreviation is BQL, Westpac's abbreviation is \"WBC\". Consult your Bank for correct abbreviation."),
            new FieldDefinition(24, 30, 7, "Blank", "Must be blank filled."),
            new FieldDefinition(31, 56, 26, "Name of Use supplying file", "Must be User Preferred Specification as advised by User's FI. Left justified, blank filled. All coded character set valid. Must not be all blanks."),
            new FieldDefinition(57, 62, 6, "Name of Use supplying file", "Must be User Identification Number which is allocated by APCA. Must be numeric, right justified, zero filled."),
            new FieldDefinition(63, 74, 12, "Description of entries on file e.g. \"PAYROLL\"", "All coded character set valid. Must not be all blanks. Left justified, blank filled."),
            new FieldDefinition(75, 80, 6, "Date to be processed (i,e. the date transactions are released to all Financial Institutions)", "Must be numeric in the formal of DDMMYY. Must be a valid date. Zero filled."),
            new FieldDefinition(81, 120, 40, "Blank", "Must be blank filled.")
    });

    public static RecordDefinition TYPE_1 = new RecordDefinition(
            "Detail Record", (byte) 1, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record Type 1", "Must be '1'"),
            new FieldDefinition(2, 8, 7, "Bank/State/Branch Number", "Must be numeric with hyphen in character position 5. Character positions 2 and 3 must equal valid Financial Institution number. Character position 4 must equal a valid state number (0-9). For credits to Employee Benefits Card accounts, field must always contain BSB 032-898"),
            new FieldDefinition(9, 17, 9, "Account number to be credited/debited", "Numeric, hyphens and blanks only are valid. Must not contain all blanks (unless a credit card transaction) or zeros. Leading zeros which are part of a valid account number must be shown, e.g. 00-1234.\n" +
                    "\n" +
                    "Where account number exceeds nine characters, edit out hyphens. Right justified, blank filled. For credits to Employee Benefits Card accounts, Account Number field must always be 999999"),
            new FieldDefinition(18, 18, 1, "Indicator", "\"N\" – for new or varied Bank/State/Branch number or name details, otherwise blank filled.\n" +
                    "Withholding Tax Indicators:\n" +
                    "\"W\" – dividend paid to a resident of a country where a double tax agreement is in force.\n" +
                    "\"X\" – dividend paid to a resident of any other country.\n" +
                    "\"Y\" – interest paid to all non-residents.\n" +
                    "The amount of withholding tax is to appear in character positions 113-120.\n" +
                    "Note: Where withholding tax has been deducted the appropriate Indicator as shown above is to be used and will override the normal indicator. "),
            new FieldDefinition(19, 20, 2, "Transaction Code", "For most transactions this will be 53. A full list of compatible transaction codes are listed below."),
            new FieldDefinition(21, 30, 10, "Amount", "Only numeric valid. Must be greater than zero. Shown in cents without punctuations. Right justified, zero filled. Unsigned."),
            new FieldDefinition(31, 62, 32, "Title of Account to be credited/debited", "All coded character set valid. Must not be all blanks. Left justified, blank filled.\n" +
                    "Desirable Format for Transaction Account credits:\n" +
                    "   -  Surname (period)\n" +
                    "       Blank\n" +
                    "   -  given name with blanks between each name "),
            new FieldDefinition(63, 80, 18, "Lodgement Reference", "All coded character set valid. Field must be left justified, and contain only the 16 character Employee Benefits Card number; for example 5550033890123456.\n" +
                    "No leading spaces, zeroes, hyphens or other characters can be included."),
            new FieldDefinition(81, 87, 7, "Trace Record\n" +
                    "(BSB Number in format XXX-XXX)", "Bank (FI)/State/Branch and account number of User to enable retracing of the entry to its source if necessary. Only numeric and hyphens valid. Character positions 81 & 82 must equal a valid Financial Institution number. Character position 83 must equal a valid State number (0-9). Character position 84 must be a hyphen."),
            new FieldDefinition(88, 96, 9, "(Account number)", "Right justified, blank filled."),
            new FieldDefinition(97, 112, 16, "Name of Remitter", "Name of originator of the entry. This may vary from Name of the User. All coded character set valid. Must not contain all blanks. Left justified, blank filled."),
            new FieldDefinition(113, 120, 8, "Amount of Withholding Tax", "Numeric only valid. Show in cents without punctuation. Right justified, zero filled. Unsigned.")
    });

    public static RecordDefinition TYPE_7 = new RecordDefinition(
            "File Total Record ", (byte) 7, new FieldDefinition[]{
            new FieldDefinition(1, 1, 1, "Record Type 7", "Must be '7'"),
            new FieldDefinition(2, 8, 7, "BSB Format Filler", "Must be '999-999'"),
            new FieldDefinition(9, 20, 12, "Blank", "Must be blank filled."),
            new FieldDefinition(21, 30, 10, "File (User) Net Total Amount", "Numeric only valid. Must equal the difference between File Credit & File Debit Total Amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(31, 40, 10, "File (User) Credit Total Amount", "Numeric only valid. Must equal the accumulated total of credit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(41, 50, 10, "File (User) Debit Total Amount", "Numeric only valid. Must equal the accumulated total of debit Detail Record amounts. Show in cents without punctuation. Right justified, zero filled. Unsigned."),
            new FieldDefinition(51, 74, 24, "Blank", "Must be blank filled."),
            new FieldDefinition(75, 80, 6, "File (user) count of Records Type 1", "Numeric only valid. Must equal accumulated number of Record Type 1 items on the file. Right justified, zero filled."),
            new FieldDefinition(81, 120, 40, "Blank", "Must be blank filled."),
    });

    public final String name;
    public final byte type;
    public final List<FieldDefinition> fields;
    public RecordDefinition(String name, byte type, FieldDefinition[] fields) {
        this.name = name;
        this.type = type;
        this.fields = Arrays.asList(fields);
    }

    enum RecordType {
        NUMERIC, ALPHA
    }

    public static class FieldDefinition {
        public final int startPos, endPos, length;
        public final String description, additionalInfo;
        FieldDefinition(int startPos, int endPos, int length, String description, String additionalInfo) {
            this.startPos = startPos; // inclusive start index. File spec not written by a programmer
            this.endPos = endPos; //end index
            this.length = length; // total length
            this.description = description;
            this.additionalInfo = additionalInfo;
        }

        /* from  https://www.anz.com/Documents/AU/corporate/clientfileformats.pdf
        String types:
        Letters: A-Z, a-z oNumbers: 0-9 oThe following Characters: spaces ( ) , ampersands (&), apostrophes (‘), commas (,) , hyphens (-), full stops (.), forward slashes (/), plus sign (+), dollar sign ($), exclamation mark (!), percentage sign (%), left parenthesis ((), right parenthesis ()), asterisk (*), number sign (#), equal sign (=), colon (:), question mark (?), left square bracket ([), right square bracket (]), underscore (_), circumflex (^) and the at symbol (@)
        Numeric types:
        Fields that are marked ‘Numeric’ in the ‘Type’ column are limited to:
            Numbers: 0-9.
            ‘Optional’ Numeric fields must be filled with zeros if no data exists.

        CRLF delimited (HEX 0D0A)
         */
    }
}
