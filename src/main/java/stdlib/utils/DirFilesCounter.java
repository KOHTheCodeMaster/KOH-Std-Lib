package stdlib.utils;

import stdlib.pojos.FileSizePojo;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DirFilesCounter extends SimpleFileVisitor<Path> {

    private long fileCount;
    private long failedFileCount;
    private long dirCount;
    private long totalSize;

    public String getCounterSummary() {
        return "Dir Files Counter Summary:\n" +
                "Files - " + fileCount + "\n" +
                "Dirs. - " + dirCount + "\n" +
                "Failed Files - " + failedFileCount + "\n" +
                "Total Size - " + FileSizePojo.acquireFileSizePojo(totalSize);
    }

    public long getFileCount() {
        return fileCount;
    }

    public long getDirCount() {
        return dirCount;
    }

    public long getTotalSize() {
        return totalSize;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        //  Increment the filesCount by 1
        fileCount++;

        //  Update dirSize by adding the current file size
        totalSize += attrs.size();

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        failedFileCount++;
        System.out.println("\nFAILED to Visit File. : " + file.toAbsolutePath() + "\n");
        System.out.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        dirCount++;
        return FileVisitResult.CONTINUE;
    }

}

