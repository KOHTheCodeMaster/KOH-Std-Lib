package stdlib.enums;

public enum HashAlgorithm {

    MD5("md5"),
    SHA256("sha-256"),
    SHA512("sha-512");

    private final String name;

    HashAlgorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
