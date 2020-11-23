package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.BankMnemonic;
import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.Collections;
import java.util.List;

public class BankMneumonicSqueezer implements ValueSqueezer {

    public static BankMneumonicSqueezer BANK_SQUEEZER = new BankMneumonicSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof BankMnemonic) return ((BankMnemonic) value).id;
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        return Collections.emptyList();
    }
}
