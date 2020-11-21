package com.kncept.abacemtex;

import com.kncept.abacemtex.parser.Parser;

import java.io.InputStream;

public class AbaCemtex {

    public static Parser parser(InputStream is) {
        return new Parser(is);
    }

}
