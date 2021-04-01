package stdlib.pojos;

import stdlib.enums.HashAlgorithm;
import stdlib.utils.HashGenerator;
import stdlib.utils.KOHStringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class DirPOJO {

    private final String name;
    private final String pathHashSHA256;
    private final String canonicalPath;
    private final String dataHashSHA256;
    private final DirMetaDataPojo dirMetaDataPojo;

    public DirPOJO(String name, String pathHashSHA256, String canonicalPath, String dataHashSHA256, DirMetaDataPojo dirMetaDataPojo) {
        this.name = name;
        this.pathHashSHA256 = pathHashSHA256;
        this.canonicalPath = canonicalPath;
        this.dataHashSHA256 = dataHashSHA256;
        this.dirMetaDataPojo = dirMetaDataPojo;
    }

    public static DirPOJO acquireDirPojo(File currentDir, String dataHashSHA256, DirMetaDataPojo dirMetaDataPojo) {

        DirPOJO newDirPojo = null;
        String canonicalPath;
        String pathHashSHA256;
//        long lastModifiedTimeInMicro = extractLastModifiedTimeInMicro(currentDir.toPath());

        try {

            canonicalPath = KOHStringUtil.replaceBackSlashWithForwardSlash(currentDir.getCanonicalPath());
            pathHashSHA256 = HashGenerator.generateStringHash(canonicalPath, HashAlgorithm.SHA256.getName());
            newDirPojo = new DirPOJO(
                    currentDir.getName(),
                    pathHashSHA256,
                    canonicalPath,
                    dataHashSHA256,
                    dirMetaDataPojo
            );

        } catch (IOException e) {
            //  Handle Exception for canonical path
            e.printStackTrace();
        }

        return newDirPojo;
    }

    public static long extractLastModifiedTimeInMicro(Path dirPath) {

        try {
            return Files.getLastModifiedTime(dirPath, LinkOption.NOFOLLOW_LINKS).to(TimeUnit.MICROSECONDS);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to Extract Last Modified Time in Micro Seconds.\n" +
                    "File Path : " + dirPath.toAbsolutePath());
            return -1;
        }

    }

    public String getName() {
        return name;
    }

    public String getPathHashSHA256() {
        return pathHashSHA256;
    }

    public String getCanonicalPath() {
        return canonicalPath;
    }

    public String getDataHashSHA256() {
        return dataHashSHA256;
    }

    public DirMetaDataPojo getDirMetaDataPojo() {
        return dirMetaDataPojo;
    }
}
