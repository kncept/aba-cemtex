package com.kncept.abacemtex.parser;

import com.kncept.abacemtex.AbaCemtex;
import com.kncept.abacemtex.file.CemtexFile;
import com.kncept.abacemtex.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    public void parseTestFile() throws IOException  {
        // Test file from https://www.cemtexaba.com/aba-format
        InputStream in = null;
        try{
            in = getClass().getResourceAsStream("/sample_aba.txt");
            CemtexFile file = AbaCemtex.parser(in).cemtexFile();
            List<String> validationErrors = file.validate();
            assertTrue(validationErrors.isEmpty(), validationErrors.toString());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Test
    public void reconstituteTestFile() throws IOException  {
        List<String> lines = new ArrayList<>();
        InputStream in = null;
        try{
            in = getClass().getResourceAsStream("/sample_aba.txt");
            BufferedReader bIn = new BufferedReader(new InputStreamReader(in));
            String line = bIn.readLine();
            while (line != null) {
                if (!line.isBlank()) lines.add(line);
                line = bIn.readLine();
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        CemtexFile file = new Parser(lines).cemtexFile();
        List<String> output = file.toRecords();
        assertEquals(lines.size(), output.size());
        for(int i = 0; i < lines.size(); i++) {
            String sourceLine = lines.get(i);
            // yes, the examples on the internet are not entirely to spec.
            if (sourceLine.length() != 120) sourceLine = sourceLine + StringUtils.blankString(120 - sourceLine.length());
            String generatedLine = output.get(i);
            assertEquals(sourceLine, generatedLine);
        }
    }
}
