package com.kncept.abacemtex.file.field;

import java.util.regex.Pattern;

public enum FieldType {
    ALPHA("(\\d|[a-z]|[A-Z]|\\ |\\&|\\'|\\,|\\-|\\.|\\/|\\+|\\$|\\!|\\%|\\(|\\)|\\*|\\#|\\=|\\:|\\?|\\[|\\]|\\_|\\^|\\@)+"),
    NUMERIC("\\d+");
    private Pattern pattern;

    FieldType(String regex) {
        pattern = Pattern.compile(regex);
    }

    /*
    from  https://www.anz.com/Documents/AU/corporate/clientfileformats.pdf
        String types:
        Letters: A-Z, a-z
        Numbers: 0-9
        The following Characters: spaces ( ) , ampersands (&), apostrophes (‘), commas (,) ,
        hyphens (-), full stops (.), forward slashes (/), plus sign (+), dollar sign ($), exclamation mark (!), percentage sign (%), left parenthesis ((), right parenthesis ()), asterisk (*), number sign (#), equal sign (=), colon (:), question mark (?), left square bracket ([), right square bracket (]), underscore (_), circumflex (^) and the at symbol (@)
        Numeric types:
        Fields that are marked ‘Numeric’ in the ‘Type’ column are limited to:
            Numbers: 0-9.
            ‘Optional’ Numeric fields must be filled with zeros if no data exists.
     */
    public boolean isValid(String data) {
        return pattern.matcher(data).matches();
    }
}

