package stdlib.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectorySizeFinder extends SimpleFileVisitor<Path> {

    private long fileCount;
    private long failedFilesCount;
    private long subDirCount;
    private long dirSizeInBytes;

    public static long findTotalDirectorySizeInBytes(File dir) {

        DirectorySizeFinder directorySizeFinder = new DirectorySizeFinder();

        //  Walk each file & add-up its size to dirSizeInBytes
        try {

            if (!dir.isDirectory()) {
                String message = "Invalid Directory : " + dir.getAbsolutePath();
                System.out.println(message);
                throw new FileNotFoundException(message);
            }

            Files.walkFileTree(Paths.get(dir.getAbsolutePath()), directorySizeFinder);

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return directorySizeFinder.getDirSizeInBytes();

    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        //  Increment the filesCount by 1
        fileCount++;

        //  Update dirSizeInBytes by adding the current file size in bytes
        dirSizeInBytes += attrs.size();

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        failedFilesCount++;
        System.out.println("\nFAILED to Visit File. : " + file.toAbsolutePath() + "\n");
        System.out.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        subDirCount++;
        return FileVisitResult.CONTINUE;
    }

    //  < ======================== Getters & Setters ======================== >

    public long getFileCount() {
        return fileCount;
    }

    public long getFailedFilesCount() {
        return failedFilesCount;
    }

    public long getSubDirCount() {
        return subDirCount;
    }

    public long getDirSizeInBytes() {
        return dirSizeInBytes;
    }
}