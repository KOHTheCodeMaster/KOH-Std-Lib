package stdlib.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public static String generateStringHash(String str, String algorithm) {

        try {

            return bytesToHexString(MessageDigest.getInstance(algorithm).digest(str.getBytes()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateFileHash(File file, String algorithm) {

        //  Extract binary data in form of byte array for particular file

        if (!file.isFile() || !file.exists()) {
            System.out.println("File not Found: " + file.getAbsolutePath());
            return null;
        }

        if (isValidAlgorithm(algorithm)) return bytesToHexString(extractHashByteArray(file, algorithm));

        System.out.println("Invalid Algorithm: " + algorithm);
        return null;

    }

    //  <| ======================== Private Methods ======================== |>

    private static boolean isValidAlgorithm(String algorithm) {

        switch (algorithm) {
            case "MD2":
            case "MD5":
            case "SHA-1":
            case "SHA-256":
            case "SHA-384":
            case "SHA-512":
                return true;

            default:
                return false;
        }

    }

    private static byte[] extractHashByteArray(File file, String algorithm) {

        //  Check Valid sourceFilePath
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {

            //  Initialize Empty bytes buffer of 2^15 i.e. 32768
            byte[] byteBuff = new byte[1 << 15];

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //  Read byteBuff.length bytes at a time from bufferedInputStream and update the messageDigest
            while (bufferedInputStream.read(byteBuff) != -1)
                messageDigest.update(byteBuff);

            return messageDigest.digest();

        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Exception: Failed to extract Hash Byte Array for file: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }

    }

    private static String bytesToHexString(byte[] bytes) {

        if (bytes == null) {
            System.out.println("Invalid Byte Array Received.");
            return null;
        }

        StringBuilder result = new StringBuilder();

        for (byte b : bytes)
            result.append(String.format("%02x", b));

        return result + "";
    }

}