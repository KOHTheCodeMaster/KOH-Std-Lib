package stdlib.utils;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class KOHFilesUtil {

    public static boolean writeStrToFile(String str, File destFile) {

        //  Time Stamp: 7th November 2K19, 12:18 PM..!!

        try (ReadableByteChannel rbc = Channels.newChannel(new ByteArrayInputStream(str.getBytes()));
             FileChannel fc = new FileOutputStream(destFile).getChannel()) {

            long bytesTransferred = fc.transferFrom(rbc, 0, Long.MAX_VALUE);
            return (bytesTransferred == str.getBytes().length);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean isDirEmpty(File srcDir, boolean shouldBeAbsolutelyEmpty) throws InvalidObjectException {

        class MyDirVisitor implements FileVisitor<Path> {

            private boolean result;
            private final boolean shouldBeAbsolutelyEmpty;
            private int dirCount = -1;

            private MyDirVisitor(boolean shouldBeAbsolutelyEmpty) {
                this.shouldBeAbsolutelyEmpty = shouldBeAbsolutelyEmpty;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {

                if (shouldBeAbsolutelyEmpty) {
                    dirCount++;
                    if (dirCount > 0) {
                        this.result = false;
                        return FileVisitResult.TERMINATE;
                    }
                } /*else if (!shouldBeAbsolutelyEmpty) {


                } */
//                else this.result = true;

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

//                if (shouldBeAbsolutelyEmpty) {
                this.result = false;
                return FileVisitResult.TERMINATE;
//                }

            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.out.println("Error with File : " + file.toAbsolutePath() +
                        "\nException : " + exc);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                this.result = true;

                if (shouldBeAbsolutelyEmpty) return FileVisitResult.TERMINATE;
                else return FileVisitResult.CONTINUE;

            }

            private boolean isResult() {
                return result;
            }
        }

        if (!srcDir.isDirectory()) {
            String msg = "Required : Directory\nFound : File";
            throw new InvalidObjectException(msg);
        }

        MyDirVisitor myDirVisitor = new MyDirVisitor(shouldBeAbsolutelyEmpty);

        try {
            Files.walkFileTree(Paths.get(srcDir.getAbsolutePath()), myDirVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myDirVisitor.isResult();

    }

    private boolean renameFileNameToStr(File file, String newFileName) {

        boolean hasRenamed;/* = file.renameTo(new File(file.getParentFile(), currentTimeStamp + SHREDDED_EXTENSION));*/

        try {
            Files.move(file.toPath(), file.toPath().resolveSibling(newFileName));
            hasRenamed = true;
        } catch (IOException e) {
            e.printStackTrace();
            hasRenamed = false;
        }

        return hasRenamed;

    }

    private boolean deleteFileNow(File file) {

        boolean hasDeleted;

        try {
            hasDeleted = Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            hasDeleted = false;
        }

        return hasDeleted;

    }


}
