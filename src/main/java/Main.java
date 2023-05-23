import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String expectedText;
        File file = new File("src/main/resources/sample.pdf");
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            expectedText = stripper.getText(document);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(expectedText);
    }
}
