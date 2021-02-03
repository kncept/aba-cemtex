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
    public T value(String fieldName, String value) {
        FieldDefinition field = fieldDefinition(fieldName);
        return value(field, field.identify(value));
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

    public String getValue(String fieldName) {
        FieldDefinition field = fieldDefinition(fieldName);
        return field.parse(getValue(field));
    }
    public Object getValue(FieldDefinition field) {
        if (!definition.fields.contains(field)) throw new IllegalStateException("Field not part of " + definition.name + " definition");
        return values.get(field);
    }

    public List<String> validate() {
        final List<String> errors = new ArrayList<>();
        definition.fields.stream()
            .forEachOrdered(field -> errors.addAll(field.validate(values.get(field))));
        return errors;
    }

    public String toRecord() {
        StringBuilder sb = new StringBuilder();
        definition.fields.stream().forEachOrdered(field -> {
            String value = field.parse(values.get(field));
            if (value == null)
                throw new NullPointerException();
            sb.append(field.parse(values.get(field)));
        });
        return sb.toString();
    }

}
