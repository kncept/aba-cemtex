package com.kncept.abacemtex;

import com.kncept.abacemtex.file.CemtexFile;
import com.kncept.abacemtex.file.DetailRecord;
import com.kncept.abacemtex.file.compactor.SimpleCompactor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This example was used to patch an aba file<br/>
 * Some values are hard coded, as this was patch code
 */
public class AbaCemtexExample {

    boolean filterOutSelfBalancingRows = false;

    public static void main(String[] args) throws Exception{
        new AbaCemtexExample().fixPayRun();
    }

    public void fixPayRun() throws IOException {
        String dir = "";
        File f = new File(dir, "2020-12-PayRun.aba");

        CemtexFile cemtexFile = AbaCemtex.parser(new FileInputStream(f)).cemtexFile();
        System.out.println("\nInput file:");
        outputFileDetails(cemtexFile);

        // BSB & Account fields are optional.
        // could be extracted from 'trace' values in detail records
//        cemtexFile.header.value("BSB", "XXX-XXX");
//        cemtexFile.header.value("Account", "XXXX");

        cemtexFile.header.value(cemtexFile.header.fieldDefinition("Date to be processed"), LocalDate.now());

        for(DetailRecord record: new ArrayList<>(cemtexFile.body)) {
            if (filterOutSelfBalancingRows &&
                    record.getValue("BSB").equals(record.getValue("Trace BSB")) &&
                    record.getValue("Account").equals(record.getValue("Trace Account"))
            ) {
                cemtexFile.body.remove(record);
            }
        }
//        cemtexFile.footer = generateFooterRecord(cemtexFile.body);

        cemtexFile = cemtexFile.compact(new SimpleCompactor());

        System.out.println("\nMassaged file:");
        outputFileDetails(cemtexFile);
        File outFile = new File(dir, "2020-12-NewPayRun.aba");
        if (outFile.exists())
            outFile.delete();
        FileOutputStream fOs = new FileOutputStream(outFile);
        fOs.write(cemtexFile.toFileBytes());
        fOs.flush();
        fOs.close();
    }

    public void outputFileDetails(CemtexFile cemtexFile) {
        System.out.println("Validation Errors: " + cemtexFile.validate());

        System.out.println("From: " + cemtexFile.header.getValue("Name of User Financial Institution") + " " + cemtexFile.header.getValue("BSB") + " " + cemtexFile.header.getValue("Account"));
        System.out.println("User Name / identifier: " + cemtexFile.header.getValue("User Name") + " " + cemtexFile.header.getValue("User Identification number"));
        System.out.println("Date to be processed:" + cemtexFile.header.getValue("Date to be processed"));

        for(DetailRecord record: cemtexFile.body) {
            System.out.println((record.isOutbound() ? "outbound" : "inbound ") + " " + record.getValue("Amount") + " cents" +
                    " " + record.getValue("BSB") + " " + record.getValue("Account") + " " + record.getValue("Account title") +
                    " TRACE " + record.getValue("Trace BSB") + " " + record.getValue("Trace Account")
            );
        }

        System.out.println("Total " + cemtexFile.footer.getValue("Batch Total Item Count") + " items and " + cemtexFile.footer.getValue("Net Total Amount") + " cents");
        System.out.println("Credit / Debit Total Amount: " + cemtexFile.footer.getValue("Credit Total Amount") + " / " + cemtexFile.footer.getValue("Debit Total Amount") + " cents");
    }
}
