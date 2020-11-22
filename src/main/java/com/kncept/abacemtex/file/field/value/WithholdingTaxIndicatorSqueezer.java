package com.kncept.abacemtex.file.field.value;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.record.WithholdingTaxIndicator;

import java.util.Collections;
import java.util.Set;

public class WithholdingTaxIndicatorSqueezer implements ValueSqueezer {

    public static WithholdingTaxIndicatorSqueezer WITHHOLDING_SQUEEZER = new WithholdingTaxIndicatorSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof WithholdingTaxIndicator) return ((WithholdingTaxIndicator) value).id;
        return value.toString();
    }

    @Override
    public Set<String> validate(FieldDefinition field, Object value) {
        if (value != null && !(value instanceof WithholdingTaxIndicator)) {
            if (WithholdingTaxIndicator.lookup(value.toString()) == null) {
                return Set.of(field.description + " value " + value + " is not a valid Withholding Tax Indicator");
            }
        }
        return Collections.emptySet();
    }
}
