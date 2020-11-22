package com.kncept.abacemtex.file.record;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CemtexRecord<T extends CemtexRecord<T>> {
    public final RecordDefinition definition;
    private final Map<FieldDefinition, Object> values;

    public CemtexRecord(RecordDefinition definition) {
        this.definition = definition;
        values = new HashMap<>();
    }

    public FieldDefinition fieldDefinition(String name) {
        Set<FieldDefinition> found = definition.fields.stream().filter(field -> field.description.equals(name)).collect(Collectors.toSet());
        if (found.isEmpty()) throw new IllegalStateException("Field \"" + name + "\" not part of " + definition.name + " definition");
        if (found.size() > 1) throw new IllegalStateException("Multiple \"" + name + "\" in " + definition.name + " definition");
        return found.iterator().next();
    }
    public T value(String fieldName, Object value) {
        return value(fieldDefinition(fieldName), value);
    }
    public T value(FieldDefinition field, Object value) {
        if (!definition.fields.contains(field)) throw new IllegalStateException("Field not part of " + definition.name + " definition");

        if (value == null) {
            values.remove(field);
        } else {
            values.put(field, value);
        }
        return (T) this;
    }

    public Set<String> validate() {
        final Set<String> errors = new LinkedHashSet<>();
        definition.fields.stream()
            .filter(field -> field.required)
            .filter(field -> !values.containsKey(field))
            .forEachOrdered(field -> errors.add("Missing required field: " + field.description));
        values.keySet().stream()
            .forEachOrdered(field -> errors.addAll(validateField(field, values.get(field))));
        return errors;
    }

    public Set<String> validateField(FieldDefinition field, Object value) {
        Set<String> errors = new LinkedHashSet<>();
        if (value == null) {
            errors.add("Null value for field " + field.description);
        } else {
            String stringValue = toValueString(field, value);
            if (!field.type.isValid(stringValue)) {
                errors.add("value " + value + " is not valid for type " + field.type.name());
            }
        }

        return errors;
    }

    private String toValueString(FieldDefinition field, Object value) {
//        if (value == null) field.defaultValue();

        return value.toString();
    }

    public String toRecord() {
        StringBuilder sb = new StringBuilder();
        definition.fields.stream().forEachOrdered(field -> {
            sb.append(toValueString(field, values.get(field)));
        });
        return sb.toString();
    }

}
