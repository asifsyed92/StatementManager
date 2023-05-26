import extractor.ChaseRecordExtractor;
import parser.ChaseStatementParser;

public class Main {


    public static void main(String[] args) {

        String filePath = "/Users/asif/IdeaProjects/StatementManager/20230523-statements-6715-.pdf";

        ChaseStatementParser sp = new ChaseStatementParser();
        ChaseRecordExtractor re = new ChaseRecordExtractor();

        re.extract(sp.parse(filePath)).forEach(System.out::println);

    }

}
