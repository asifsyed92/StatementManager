import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int FOR_TAX_YEAR = 2017;
    private static String YEAR_EXTENSION = "/" + FOR_TAX_YEAR;
    private static DateTimeFormatter check = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    private static List<String> exclusions = new ArrayList<>(Arrays.asList("Payment Thank You", "AUTOMATIC PAYMENT"));
    public static void main(String[] args) {

        String filePath = "C:/Users/syeda/Documents/Finance/Chase Bank Statements/20230425-statements-6715-.pdf";

        Tika tika = new Tika();
        try(InputStream is = new FileInputStream(filePath)){
            PDFParser pdfParser = new PDFParser();

            PDFParserConfig pdfParserConfig = new PDFParserConfig();
            pdfParserConfig.setEnableAutoSpace(true);
            pdfParserConfig.setSortByPosition(true);

            ParseContext parseContext = new ParseContext();
            parseContext.set(PDFParserConfig.class,pdfParserConfig);

            ContentHandler contentHandler = new BodyContentHandler();

            pdfParser.parse(is,contentHandler,new Metadata(),parseContext);
            String data = contentHandler.toString();
            List<ChaseRecord> l = new ArrayList<>();
            for(String line : data.split("\n")){
                if(line.isEmpty()) continue;
                String[] split = line.split("\\s");
                if (split.length == 0) continue;
                String test = split[0];
                if (!isMMDD(test)) continue;
                if(skip(line)) continue;
                if (split.length < 4) continue;
                //System.out.println(line);
                ChaseRecord cr = new ChaseRecord();
                cr.date = extractDate(test);
                try {
                    String last = split[split.length - 1];
                    last = last.replaceAll(",", "");
                    cr.balance = Double.parseDouble(last);

                    String lastSecond = split[split.length - 2];
                    lastSecond = lastSecond.replaceAll(",","");
                    cr.amt = Double.parseDouble(lastSecond);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                cr.desc = String.join(" ", Arrays.copyOfRange(split, 1, split.length - 2));
                cr.desc = cr.desc.replaceAll("\\s\\s+", " ");
                l.add(cr);

            }

            l.forEach(cr -> System.err.println(cr.date + "|" + cr.desc + "|" + cr.amt + "|" + cr.balance));


        } catch (IOException | TikaException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isMMDD(String s) {
        if (s == null || s.length() != 5) {
            return false;
        }
        try {
            s += YEAR_EXTENSION;
            LocalDate.parse(s, check);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean skip(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        for (String e : exclusions) {
            if (s.contains(e)) {
                return true;
            }
        }
        return false;
    }

    protected static LocalDate extractDate(String s) {
        if (!isMMDD(s)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(s + YEAR_EXTENSION, check);
        return localDate;
    }

    public static class ChaseRecord {
        public LocalDate date;
        public String desc;
        public Double amt;
        public Double balance;

        @Override
        public String toString() {
            return "ChaseRecord{" +
                    "date=" + date +
                    ", desc='" + desc + '\'' +
                    ", amt=" + amt +
                    ", balance=" + balance +
                    '}';
        }
    }
}
