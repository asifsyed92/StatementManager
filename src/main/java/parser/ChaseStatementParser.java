package parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChaseStatementParser {
    public String parse(String filePath) {
        try (InputStream is = new FileInputStream(filePath)) {
            PDDocument pd = PDDocument.load(is);

            PDFTextStripper ts = new PDFTextStripper();
            ts.setSortByPosition(true);
            ts.setWordSeparator(" ");

            return ts.getText(pd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
