package com.kncept.abacemtex.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class ParserTest {

    @Test
    public void parseTestFile() throws IOException  {
        // Test file from https://www.cemtexaba.com/aba-format
        InputStream is = null;
        try{
            is = getClass().getResourceAsStream("/sample_aba.txt");
            Assertions.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
