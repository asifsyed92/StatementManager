import extractor.ChaseRecordExtractor;
import manager.ChaseStatementFileManager;
import parser.ChaseStatementParser;

public class Main {


    public static void main(String[] args) {

//      String filePath = "/Users/asif/IdeaProjects/StatementManager/20230523-statements-6715-.pdf";
        String dirPath = "C:/Users/syeda/Documents/Finance/Chase Bank Statements";

        ChaseStatementFileManager sfm = new ChaseStatementFileManager(dirPath);

        ChaseStatementParser sp = new ChaseStatementParser();
        ChaseRecordExtractor re = new ChaseRecordExtractor();

        sfm.getAbsoluteNames().forEach(file -> re.extract(sp.parse(file)).forEach(System.out::println));
    }

}
