package com.kncept.abacemtex.file.field;

public class FieldDefinition {
    public final int startPos, endPos, length;
    public final String description;
    public final FieldType type;
    public final boolean required;
    public FieldDefinition(int startPos, int endPos, int length, String description, FieldType type, boolean required) {
        this.startPos = startPos; // inclusive start index. File spec not written by a programmer
        this.endPos = endPos; //end index
        this.length = length; // total length
        this.description = description;
        this.type = type;
        this.required = required;
    }
}