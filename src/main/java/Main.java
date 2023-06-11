import extractor.ChaseRecordExtractor;
import manager.ChaseStatementFileManager;
import parser.ChaseStatementParser;
import processor.ChaseStatementFileProcessor;

public class Main {


    public static void main(String[] args) {

//      String filePath = "/Users/asif/IdeaProjects/StatementManager/20230523-statements-6715-.pdf";
        String dirPath = "C:/Users/syeda/Documents/Finance/Chase Bank Statements";

        ChaseStatementFileManager sfm = new ChaseStatementFileManager(dirPath);

        ChaseStatementFileProcessor sfp = new ChaseStatementFileProcessor(sfm);

        sfp.getChaseRecords().forEach(System.out::println);


    }

}
