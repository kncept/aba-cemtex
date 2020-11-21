package com.kncept.abacemtex.record;

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
            if (
                    !field.description.equalsIgnoreCase("blank") &&
                    !field.description.equalsIgnoreCase("Name of Use supplying file") // Duplicate field names :/
            ) {
                Assertions.assertTrue(fieldNames.add(field.description), "Duplicate field name: " + field.description);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("recordDefinitions")
    public void recordDefinitionsLengthsAreCorrect(RecordDefinition recordDefinition) {
        recordDefinition.fields.forEach(field -> {
            Assertions.assertEquals(field.endPos + 1 - field.startPos, field.length, "Length mismatch for field " + field.description);
        });
    }

    public static Stream<Arguments> recordDefinitions() {
        return Stream.of(
                Arguments.of(RecordDefinition.TYPE_0),
                Arguments.of(RecordDefinition.TYPE_1),
                Arguments.of(RecordDefinition.TYPE_7)
        );
    }
}
