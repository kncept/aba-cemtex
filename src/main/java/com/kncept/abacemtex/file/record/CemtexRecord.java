package com.kncept.abacemtex.file.record;

import com.kncept.abacemtex.file.field.FieldDefinition;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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

    public Object getValue(String fieldName) {
        return getValue(fieldDefinition(fieldName));
    }
    public Object getValue(FieldDefinition field) {
        if (!definition.fields.contains(field)) throw new IllegalStateException("Field not part of " + definition.name + " definition");
        return values.get(field);
    }

    public Set<String> validate() {
        final Set<String> errors = new LinkedHashSet<>();
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
