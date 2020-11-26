package com.kncept.abacemtex;

import com.kncept.abacemtex.file.CemtexFile;
import com.kncept.abacemtex.file.HeaderRecord;
import com.kncept.abacemtex.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public final class AbaCemtex {
	private AbaCemtex() {}

    public static Parser parser(InputStream in) throws IOException {
        return new Parser(in);
    }
    public static Parser parser(List<String> in) {
        return new Parser(in);
    }

    public CemtexFile.Builder builder(Consumer<HeaderRecord> recordBuilder) {
        return CemtexFile.builder(recordBuilder);
    }

}
