package stdlib.utils;

import stdlib.enums.StringOptions;

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

    /**
     * This method creates the new directory for the given dirPath
     * including all the non-existing sub-dirs.
     *
     * @param promptInputDirPath prompting user with message to input the path
     *                           of the directory which needs to be created
     * @param myTimer            instance of MyTimer to pause the timer
     * @return file reference of the newly created directory for the given dirPath
     */
    public static File createNewDir(String promptInputDirPath, MyTimer myTimer) {

        //  Time Stamp : 7th January 2K20, 05:40 PM..!!
        while (true) {

            String dirPath = KOHStringUtil.userInputString(promptInputDirPath, StringOptions.DEFAULT, myTimer);

            // TODO : Throw Proper Exception instead of null
            if (dirPath == null) return null;

            //  Input is valid if str is an existing Directory
            File file = new File(dirPath);
            if (file.isDirectory() || file.mkdirs()) return file;
            else {
                System.out.println("Invalid Directory Path Found!");
                String promptTryAgain = "Wanna try again? [Y/N] : ";
                if (!KOHStringUtil.wannaTryAgain(promptTryAgain, myTimer)) return null;
            }

        }

    }

    public static boolean isDirEmpty(File srcDir, boolean shouldBeAbsolutelyEmpty) throws InvalidObjectException {

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
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                this.result = false;
                return FileVisitResult.TERMINATE;
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

    public static boolean renameFileNameToStr(File file, String newFileName) {

        boolean hasRenamed;

        try {
            Files.move(file.toPath(), file.toPath().resolveSibling(newFileName));
            hasRenamed = true;
        } catch (IOException e) {
            e.printStackTrace();
            hasRenamed = false;
        }

        return hasRenamed;

    }

    public static boolean deleteFileNow(File file) {

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
