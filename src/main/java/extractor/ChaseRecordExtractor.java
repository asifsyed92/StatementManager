package extractor;

import model.ChaseRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChaseRecordExtractor {

    private static int FOR_TAX_YEAR = 2023;
    private static String YEAR_EXTENSION = "/" + FOR_TAX_YEAR;
    private static DateTimeFormatter check = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    private static List<String> exclusions = new ArrayList<>(Arrays.asList("Payment Thank You", "AUTOMATIC PAYMENT"));

    public List<ChaseRecord> extract(String data) {

        List<ChaseRecord> list = new ArrayList<>();

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
            list.add(cr);

        }

        return list;
    }

    private boolean isMMDD(String s) {
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
    private boolean skip(String s) {
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

    private LocalDate extractDate(String s) {
        if (!isMMDD(s)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(s + YEAR_EXTENSION, check);
        return localDate;
    }

}
