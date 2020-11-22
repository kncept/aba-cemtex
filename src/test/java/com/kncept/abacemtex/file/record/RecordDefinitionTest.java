package com.kncept.abacemtex.file.record;

import com.kncept.abacemtex.file.field.FieldDefinition;
import com.kncept.abacemtex.file.record.RecordDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class RecordDefinitionTest {

    @ParameterizedTest
    @MethodSource("recordDefinitions")
    public void recordDefinitionsContainNoDuplicatedFields(RecordDefinition recordDefinition) {
        Set<String> fieldNames = new HashSet<>();
        recordDefinition.fields.forEach(field -> {
            if (!field.description.equalsIgnoreCase("Reserved")) {
                Assertions.assertTrue(fieldNames.add(field.description), "Duplicate field name: " + field.description);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("recordDefinitions")
    public void recordDefinitionNamesAreSane(RecordDefinition recordDefinition) {
        Set<String> fieldNames = new HashSet<>();
        recordDefinition.fields.forEach(field -> {
            Assertions.assertEquals(field.description.trim(), field.description);
        });
    }

    @ParameterizedTest
    @MethodSource("recordDefinitions")
    public void recordDefinitionsLengthsAreCorrect(RecordDefinition recordDefinition) {
        recordDefinition.fields.forEach(field -> {
            Assertions.assertEquals(field.endPos + 1 - field.startPos, field.length, "Length mismatch for field " + field.description);
        });
    }

    @ParameterizedTest
    @MethodSource("recordDefinitions")
    public void recordDefinitionsFieldsAreSequential(RecordDefinition recordDefinition) {
        int lastPosition = 0;
        for(FieldDefinition field: recordDefinition.fields) {
            Assertions.assertEquals(field.startPos -1, lastPosition, "Start position skipped index: " + field.description);
            lastPosition = field.endPos;
        }
        Assertions.assertEquals(lastPosition, 120, "Last field position should be 120");
    }

    public static Stream<Arguments> recordDefinitions() {
        return Stream.of(
                Arguments.of(RecordDefinition.TYPE_0),
                Arguments.of(RecordDefinition.TYPE_1),
                Arguments.of(RecordDefinition.TYPE_7)
        );
    }
}
