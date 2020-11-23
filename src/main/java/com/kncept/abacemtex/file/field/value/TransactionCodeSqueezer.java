package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.record.TransactionCode;

import java.util.Collections;
import java.util.List;

public class TransactionCodeSqueezer implements ValueSqueezer {

    public static TransactionCodeSqueezer TX_CODE_SQUEEZER = new TransactionCodeSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof TransactionCode) return ((TransactionCode) value).id;
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value != null && !(value instanceof TransactionCode)) {
            if (TransactionCode.lookup(value.toString()) == null) {
                return List.of(field.validationErrorString(value,"is not a valid Transaction Code"));
            }
        }
        return Collections.emptyList();
    }
}
