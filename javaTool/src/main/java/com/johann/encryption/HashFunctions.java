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
     * 对输入字符串进行哈希处理。
     * 支持的算法包括[MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256]
     *
     * MD5     16个字节
     * SHA-1   20个字节
     * SHA-256 32个字节
     * 一个字节等于8位二进制数，2位16进制数
     * @param input 待哈希的字符串
     * @param algorithm 使用的哈希算法
     * @param salt 盐值，用于增加密码的复杂度
     * @return 哈希后的字符串
     * @throws NoSuchAlgorithmException 如果指定的算法不可用，则抛出此异常
     */
    public static String hashFunction(String input,String algorithm, String salt) throws NoSuchAlgorithmException {
        // 获取指定算法的MessageDigest实例
        MessageDigest algorithmDigest = MessageDigest.getInstance(algorithm);
        // 将输入字符串与盐值拼接
        String inputWithSalt = input + salt;
        // 计算哈希值
        byte[] messageDigest = algorithmDigest.digest(inputWithSalt.getBytes());
        // 格式化字符串，确保每个字节都转换为两位十六进制数
        String formatStr = "%0" + (messageDigest.length << 1) + "x";
        // 将字节数组转换为十六进制字符串
        return String.format(formatStr, new BigInteger(1, messageDigest));
    }

    /**
     * 对输入字符串进行SHA-256哈希处理。
     * @param input 待哈希的字符串
     * @return 哈希后的字符串，固定长度为64位十六进制数
     * @throws NoSuchAlgorithmException 如果SHA-256算法不可用，则抛出此异常
     */
    public static String sha256HashFunction(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        // 将字节数组转换为BigInteger
        BigInteger no = new BigInteger(1, messageDigest);
        // 转换为十六进制字符串
        String hashText = no.toString(16);
        // 确保哈希值为64位
        while (hashText.length() < 64) {
            // 不足64位时在前面补0
            hashText = "0" + hashText;
        }
        return hashText;
    }

    public static void main(String[] args) {
        String input = "Johann";
        try {
            String salt = "Jessie";
            System.out.println("MD5: " + hashFunction(input,"MD5",salt));
            System.out.println("SHA-1: " + hashFunction(input,"SHA-1",salt));
            System.out.println("SHA-256: " + hashFunction(input,"SHA-256",salt));
            System.out.println("SHA-256: " + sha256HashFunction(input));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }
}
