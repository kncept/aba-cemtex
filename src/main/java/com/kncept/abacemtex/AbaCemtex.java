package com.kncept.abacemtex;

import com.kncept.abacemtex.file.CemtexFile;
import com.kncept.abacemtex.file.HeaderRecord;
import com.kncept.abacemtex.parser.Parser;

import java.io.InputStream;
import java.util.function.Consumer;

public class AbaCemtex {

    public static Parser parser(InputStream is) {
        return new Parser(is);
    }

    public CemtexFile.Builder builder(Consumer<HeaderRecord> recordBuilder) {
        return CemtexFile.builder(recordBuilder);
    }

}
