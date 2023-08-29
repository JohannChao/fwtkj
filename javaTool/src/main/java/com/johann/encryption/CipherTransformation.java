package com.johann.encryption;

/**
 * Java 平台的每个实现都需要支持以下标准密码转换（密钥大小在括号中）
 * @See: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 * @See: https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
 * @author Johann
 */
public enum CipherTransformation {
    AES_CBC_NoPadding("AES/CBC/NoPadding", 128),
    AES_CBC_PKCS5Padding ("AES/CBC/PKCS5Padding",128),
    AES_ECB_NoPadding ("AES/ECB/NoPadding",128),
    AES_ECB_PKCS5Padding ("AES/ECB/PKCS5Padding",128),
    AES_GCM_NoPadding ("AES/GCM/NoPadding",128),
    DES_CBC_NoPadding ("DES/CBC/NoPadding",56),
    DES_CBC_PKCS5Padding ("DES/CBC/PKCS5Padding",56),
    DES_ECB_NoPadding ("DES/ECB/NoPadding",56),
    DES_ECB_PKCS5Padding ("DES/ECB/PKCS5Padding",56),
    DESede_CBC_NoPadding ("DESede/CBC/NoPadding",168),
    DESede_CBC_PKCS5Padding ("DESede/CBC/PKCS5Padding",168),
    DESede_ECB_NoPadding ("DESede/ECB/NoPadding",168),
    DESede_ECB_PKCS5Padding ("DESede/ECB/PKCS5Padding",168),
    RSA_ECB_PKCS1Padding_1024 ("RSA/ECB/PKCS1Padding", 1024),
    RSA_ECB_PKCS1Padding_2048 ("RSA/ECB/PKCS1Padding", 2048),
    RSA_ECB_OAEPWithSHA1AndMGF1Padding_1024 ("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 1024),
    RSA_ECB_OAEPWithSHA1AndMGF1Padding_2048 ("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 2048),
    RSA_ECB_OAEPWithSHA256AndMGF1Padding_1024 ("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 1024),
    RSA_ECB_OAEPWithSHA256AndMGF1Padding_2048 ("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 2048);

    /**
     * 算法/模式/补码方式
     */
    private String typec;

    /**
     * 密钥长度
     */
    private int keySize;

    CipherTransformation(String typec, int keySize) {
        this.typec = typec;
        this.keySize = keySize;
    }

    public String getTypec() {
        return typec;
    }

    public int getKeySize() {
        return keySize;
    }
}