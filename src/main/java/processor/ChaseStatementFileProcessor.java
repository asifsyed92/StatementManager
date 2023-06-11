package processor;

import extractor.ChaseRecordExtractor;
import manager.ChaseStatementFileManager;
import model.ChaseRecord;
import parser.ChaseStatementParser;

import java.util.HashSet;
import java.util.Set;

public class ChaseStatementFileProcessor {
    private ChaseStatementFileManager statementFileManager;
    private ChaseStatementParser statementParser;
    private ChaseRecordExtractor recordExtractor;

    public ChaseStatementFileProcessor(ChaseStatementFileManager chaseStatementFileManager) {
        this.statementFileManager = chaseStatementFileManager;
        statementParser = new ChaseStatementParser();
        recordExtractor = new ChaseRecordExtractor();
    }

    public Set<ChaseRecord> getChaseRecords() {
        Set<ChaseRecord> records = new HashSet<>();
        for (String filePath: statementFileManager.getAbsoluteNames()) {
            String parsedContent = statementParser.parse(filePath);
            records.addAll(recordExtractor.extract(parsedContent));
        }
        return records;
    }

}
