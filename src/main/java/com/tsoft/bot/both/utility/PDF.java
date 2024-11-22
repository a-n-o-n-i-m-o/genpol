package com.tsoft.bot.both.utility;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.net.URL;

public class PDF {
    private static URL uri;
    private static InputStream inputStream;
    private static BufferedInputStream fileToParse;
    public static String readPDFFromURI(String URL) throws IOException {
        uri = new URL(URL);
        inputStream = uri.openStream();
        fileToParse = new BufferedInputStream(inputStream);
        String output;
        try (PDDocument document = PDDocument.load(fileToParse)) {
            output = new PDFTextStripper().getText(document);
        } finally {
            fileToParse.close();
            inputStream.close();
        }
        return output;
    }
    public static String readPDF(String fileName) throws Exception {
        File f = new File(fileName);
        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        return pdfStripper.getText(pdDoc);
    }
}
