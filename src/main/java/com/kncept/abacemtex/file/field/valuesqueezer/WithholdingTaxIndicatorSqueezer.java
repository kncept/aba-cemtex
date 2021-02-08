package com.kncept.abacemtex.file.field.valuesqueezer;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.field.value.withholdingtax.WithholdingTaxIndicator;

import java.util.Collections;
import java.util.List;

public class WithholdingTaxIndicatorSqueezer implements ValueSqueezer {

    public static WithholdingTaxIndicatorSqueezer WITHHOLDING_SQUEEZER = new WithholdingTaxIndicatorSqueezer();

    @Override
    public String squeeze(FieldDefinition field, Object value) {
        if (value == null) return null;
        if (value instanceof WithholdingTaxIndicator) return ((WithholdingTaxIndicator) value).id;
        return value.toString();
    }

    @Override
    public List<String> validate(FieldDefinition field, Object value) {
        if (value != null && !(value instanceof WithholdingTaxIndicator)) {
            if (WithholdingTaxIndicator.lookup(value.toString()) == null) {
                return List.of(field.validationErrorString(value,"is not a valid Withholding Tax Indicator"));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public WithholdingTaxIndicator identify(FieldDefinition field, String value) {
        return WithholdingTaxIndicator.lookup(value);
    }
}
