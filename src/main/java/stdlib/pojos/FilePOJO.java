package stdlib.pojos;

import stdlib.enums.HashAlgorithm;
import stdlib.utils.HashGenerator;
import stdlib.utils.KOHStringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FilePOJO {

    private String name;
    private String pathHashSHA256;
    private String canonicalPath;
    private long sizeInBytes;
    private String dataHashSHA256;
    private double minimalSize;
    private String sizeUnit;
    private long lastModTimeInMicros;

    public FilePOJO(String name, String pathHashSHA256, String canonicalPath, long sizeInBytes, String dataHashSHA256, double minimalSize, String sizeUnit, long lastModTimeInMicros) {
        this.name = name;
        this.pathHashSHA256 = pathHashSHA256;
        this.canonicalPath = canonicalPath;
        this.sizeInBytes = sizeInBytes;
        this.dataHashSHA256 = dataHashSHA256;
        this.minimalSize = minimalSize;
        this.sizeUnit = sizeUnit;
        this.lastModTimeInMicros = lastModTimeInMicros;
    }

    /**
     * Time Stamp: 1st September 2K20, 06:56 PM..!!
     * <p>
     * Desc: Create FilePOJO instance using File along with the shouldIncludeDataHash
     *
     * @param currentFile           file that needs to be used for the creation of FilePOJO.
     * @param shouldIncludeDataHash if true then finds dataHashSHA256 for the file, otherwise dataHashSHA256 value is null.
     * @return returns instance of FilePOJO initialized using the currentFile.
     */
    public static FilePOJO acquireFilePojo(File currentFile, boolean shouldIncludeDataHash) {

        FilePOJO currentFilePOJO;

        try {

            //  Initialize fileSizePojo
            final FileSizePojo fileSizePojo = Objects.requireNonNull(
                    FileSizePojo.acquireFileSizePojo(currentFile.length())
            );

            String canonicalPath = KOHStringUtil.replaceBackSlashWithForwardSlash(currentFile.getCanonicalPath());
            String pathHashSHA256 = HashGenerator.generateStringHash(canonicalPath, HashAlgorithm.SHA256.getName());

            //  Instantiate FilePojo for currentFile
            currentFilePOJO = new FilePOJO(currentFile.getName(),
                    pathHashSHA256,
                    canonicalPath,
                    currentFile.length(),
                    shouldIncludeDataHash
                            ? HashGenerator.generateFileHash(currentFile, HashAlgorithm.SHA256.getName())
                            : null,
                    fileSizePojo.getMinimalSize(),
                    fileSizePojo.getSizeUnit(),
                    extractLastModifiedTimeInMicro(currentFile.toPath())
            );

        } catch (NullPointerException e) {
            //  fileSizePojo is null
            System.out.println("Exception: Invalid FileSizePojo for the File: " + currentFile.getAbsolutePath());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            //  canonical path of currentFile
            System.out.println("Exception: Failed to acquire File Pojo for the File: " + currentFile.getAbsolutePath());
            e.printStackTrace();
            return null;
        }

        return currentFilePOJO;
    }

    public static FilePOJO parseFromMap(Map<String, String> map) {

        if (map == null) {
            System.out.println("Map is Null.\nFailed To Parse Map into FilePOJO");
            return null;
        }
        FilePOJO filePOJO;
        KeysManager keysManager = new KeysManager();

        filePOJO = new FilePOJO(
                map.get(keysManager.getKeysOfFilePOJO().getName()),
                map.get(keysManager.getKeysOfFilePOJO().getPathHashSHA256()),
                map.get(keysManager.getKeysOfFilePOJO().getCanonicalPath()),
                Long.parseLong(map.get(keysManager.getKeysOfFilePOJO().getSizeInBytes())),
                map.get(keysManager.getKeysOfFilePOJO().getDataHashSHA256()),
                Double.parseDouble(map.get(keysManager.getKeysOfFilePOJO().getMinimalSize())),
                map.get(keysManager.getKeysOfFilePOJO().getSizeUnit()),
                Long.parseLong(map.get(keysManager.getKeysOfFilePOJO().getLastModTimeInMicros()))
        );

        return filePOJO;

    }

    public String getName() {
        return name;
    }

    public String getCanonicalPath() {
        return canonicalPath;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public double getMinimalSize() {
        return minimalSize;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public String getDataHashSHA256() {
        return dataHashSHA256;
    }

    public String getPathHashSHA256() {
        return pathHashSHA256;
    }

    public long getLastModTimeInMicros() {
        return lastModTimeInMicros;
    }

    public static long extractLastModifiedTimeInMicro(Path filePath) {

        try {
            return Files.getLastModifiedTime(filePath, LinkOption.NOFOLLOW_LINKS).to(TimeUnit.MICROSECONDS);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to Extract Last Modified Time in Micro Seconds.\n" +
                    "File Path : " + filePath.toAbsolutePath());
            return -1;
        }

    }

    @Override
    public String toString() {
        return "FilePOJO{" +
                "name='" + name + '\'' +
                ", pathHashSHA256='" + pathHashSHA256 + '\'' +
                ", canonicalPath='" + canonicalPath + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", dataHashSHA256='" + dataHashSHA256 + '\'' +
                ", minimalSize=" + minimalSize +
                ", sizeUnit='" + sizeUnit + '\'' +
                ", lastModTimeInMicros=" + lastModTimeInMicros +
                '}';
    }
}
