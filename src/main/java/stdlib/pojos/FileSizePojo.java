package stdlib.pojos;

import stdlib.utils.DirectorySizeFinder;
import stdlib.enums.StandardFileSize;

import java.io.File;

public class FileSizePojo {

    private final String sizeUnit;
    private final double minimalSize;
    private final long sizeInBytes;

    public FileSizePojo(String sizeUnit, double minimalSize, long sizeInBytes) {
        this.sizeUnit = sizeUnit;
        this.minimalSize = minimalSize;
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String toString() {
        return minimalSize + " " + sizeUnit;
    }

    public static FileSizePojo acquireFileSizePojo(long fileSizeInBytes) {

        //  FileSize is an Enum denoting the units & values for different file sizes.
        StandardFileSize standardFileSize = null;

        if (fileSizeInBytes < StandardFileSize.KILO_BYTE.getSizeInBytes()) standardFileSize = StandardFileSize.BYTE;
        else if (fileSizeInBytes < StandardFileSize.MEGA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.KILO_BYTE;
        else if (fileSizeInBytes < StandardFileSize.GIGA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.MEGA_BYTE;
        else if (fileSizeInBytes < StandardFileSize.TERA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.GIGA_BYTE;
        else if (fileSizeInBytes < StandardFileSize.PETA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.TERA_BYTE;
        else if (fileSizeInBytes < StandardFileSize.EXA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.PETA_BYTE;
        else if (fileSizeInBytes < StandardFileSize.ZETA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.EXA_BYTE;
        else if (fileSizeInBytes < StandardFileSize.YOTTA_BYTE.getSizeInBytes())
            standardFileSize = StandardFileSize.ZETA_BYTE;

        if (standardFileSize != null) {

            //  minimize fileSizeInBytes according to its nearest size unit (B/KB/MB/GB/TB/PB/EB/ZB/YB)
            //  minimalSize is in the form of 1023.99 X-Bytes
            double minimalSize = (double) fileSizeInBytes / standardFileSize.getSizeInBytes();
            minimalSize = Double.parseDouble(String.format("%.2f", minimalSize));

            return new FileSizePojo(standardFileSize.getUnit(), minimalSize, fileSizeInBytes);
        }

        //  Rare case only when a file size goes beyond YOTTA_BYTE
        System.out.println("Unknown Size!");
        return null;

    }

    public static FileSizePojo acquireFileSizePojoForDirectory(File directory) {

        return FileSizePojo.acquireFileSizePojo(
                DirectorySizeFinder.findTotalDirectorySizeInBytes(directory)
        );

    }

    //  Getters and Setters
    public String getSizeUnit() {
        return sizeUnit;
    }

    public double getMinimalSize() {
        return minimalSize;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

}
