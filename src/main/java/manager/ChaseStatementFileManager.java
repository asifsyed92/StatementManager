package manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChaseStatementFileManager {

    private Set<String> statementDirectories;

    public ChaseStatementFileManager(Set<String> statementDirectories) {
        this.statementDirectories = statementDirectories;
    }

    public ChaseStatementFileManager(String statementDirectory) {
        this.statementDirectories = new HashSet<>();
        this.statementDirectories.add(statementDirectory);
    }

    public ChaseStatementFileManager() {

    }

    private Set<String> getFileNamesFromDir(String dir) {
        Set<String> fileNames = new HashSet<>();
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            fileNames = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    public Set<String> getFileNames() {
        if (statementDirectories.isEmpty()) throw new RuntimeException("Directory path not specified.");

        Set<String> fileNames = new HashSet<>();

        for(String dirPath: statementDirectories) {
            fileNames.addAll(this.getFileNamesFromDir(dirPath));
        }

        return fileNames;
    }

    public Set<String> getAbsoluteNames() {
        if (statementDirectories.isEmpty()) throw new RuntimeException("Directory path not specified.");

        Set<String> absoluteNames = new HashSet<>();

        for(String dirPath: statementDirectories) {
            this.getFileNamesFromDir(dirPath).forEach(fileName -> absoluteNames.add(dirPath +"\\"+ fileName));
        }

        return absoluteNames;

    }


    public void setStatementDirectories(Set<String> statementDirectories) {
        this.statementDirectories = statementDirectories;
    }

    public void setStatementDirectory(String statementDirectory) {
        statementDirectories = new HashSet<>();
        statementDirectories.add(statementDirectory);
    }

}
