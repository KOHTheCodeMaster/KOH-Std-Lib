package stdlib.pojos;

public class DirMetaDataPojo {

    private final FileSizePojo fileSizePojo;
    private final long filesCount;
    private final long dirsCount;
    private final long lastModTimeInMicros;
    private final String modTimeHashSHA256;

    public DirMetaDataPojo(FileSizePojo fileSizePojo, long filesCount, long dirsCount, long lastModTimeInMicros, String modTimeHashSHA256) {
        this.fileSizePojo = fileSizePojo;
        this.filesCount = filesCount;
        this.dirsCount = dirsCount;
        this.lastModTimeInMicros = lastModTimeInMicros;
        this.modTimeHashSHA256 = modTimeHashSHA256;
    }

    public FileSizePojo getFileSizePojo() {
        return fileSizePojo;
    }

    public long getFilesCount() {
        return filesCount;
    }

    public long getDirsCount() {
        return dirsCount;
    }

    public long getLastModTimeInMicros() {
        return lastModTimeInMicros;
    }

    public String getModTimeHashSHA256() {
        return modTimeHashSHA256;
    }
}
