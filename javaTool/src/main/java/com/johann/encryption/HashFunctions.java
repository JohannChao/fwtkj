package com.johann.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

/**
 * 哈希函数
 * @author Johann
 */
public class HashFunctions {

    /**
     * MD5     16个字节
     * SHA-1   20个字节
     * SHA-256 32个字节
     * 一个字节等于8位二进制数，2位16进制数
     * @param input
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hashFunction(String input,String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] messageDigest = md.digest(input.getBytes());
        String formatStr = "%0" + (messageDigest.length << 1) + "x";
        return String.format(formatStr, new BigInteger(1, messageDigest));
    }

    public static String sha256HashFunction(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashText = no.toString(16);
        while (hashText.length() < 64) {
            hashText = "0" + hashText;
        }
        return hashText;
    }

    public static void main(String[] args) {
        String input = "Johann";
        try {
            System.out.println("MD5: " + hashFunction(input,"MD5"));
            System.out.println("SHA-1: " + hashFunction(input,"SHA-1"));
            System.out.println("SHA-256: " + hashFunction(input,"SHA-256"));
            System.out.println("SHA-256: " + sha256HashFunction(input));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }
}
