package com.johann.encryption;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 对称加密算法
 *
 * 在Java中，使用对称加密算法时，需要先获取一个密码类Cipher，使用getInstance()方法来获取这个密码对象，同时需要传入一个转换信息（transformation）。
 * 转换信息始终包含加密算法的名称，后面可能还跟有一个反馈模式（feedback mode）和填充方案（padding scheme）。
 * A transformation is of the form: "algorithm/mode/padding" or "algorithm"
 *
 * 如果不指定模式和填充模式，那么默认使用的是 "ECB/PKCS5Padding"。
 * 如果使用了CBC模式，那么还需要传入一个初始化向量（initialization vector，IV），用于第一个分组的加密。
 * 初始向量的长度需要和分组长度相同，AEC加密算法的分组长度固定为 16，DEC加密算法的分组长度固定为 8。
 *
 * @author Johann
 * @version 1.0
 * @see
 **/
@Slf4j
public class SymmetricEncryption {

    // 密钥 密钥的长度需要根据加密算法来确定，AEC加密算法的密钥长度固定为 16，DEC加密算法的密钥长度固定为 8
    private static String KEY = "JOHANNWITHJESSIE";

    // 使用CBC模式时需要一个初始化的向量(initialization vector，IV)，向量的长度需要和分组长度相同，AEC加密算法的分组长度固定为 16，DEC加密算法的分组长度固定为 8
    private static String IV = "0123456789abcdef";

    // CBC模式需要初始化向量
    private static final String CBC_NEED_IV = "CBC";

    // 填充方案选定为"NoPadding"时，如果明文长度刚好是加密算法所需的块大小的倍数时，无需处理。否则需要手动后填充
    private static final String NO_PADDING = "NoPadding";

    public static void main(String[] args) throws Exception {
        String input = "我爱北京天安门";
        CipherTransformation cipherTransformation = CipherTransformation.AES_CBC_NoPadding;
        String transformation = cipherTransformation.getTypec();
        int byteSize = cipherTransformation.getKeySize()/8;
        String algorithm = null;
        int index = transformation.indexOf("/");
        if (index != -1) {
            algorithm = transformation.substring(0, index);
        }
        log.info("加密前的原文: 【{}】, transformation: 【{}】", input, transformation);
        input = plainTextPadding(input, transformation, byteSize);
        String result = encrypt(input, transformation, algorithm, KEY, IV);
        log.info("加密后的结果: 【{}】", result);
        String decryptResult = decrypt(result, transformation, algorithm, KEY, IV);
        log.info("解密后的结果: 【{}】", decryptResult);
    }

    /**
     * 加密操作
     * @param input
     * @param transformation
     * @param algorithm
     * @param key
     * @param iv
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt(String input, String transformation, String algorithm, String key, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 获取密码类Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 获取密钥
        SecretKey secretKey = getSecretKeySpec(key, algorithm);
        // 获取初始化向量
        IvParameterSpec ivSpec = getIv(iv, transformation, cipher.getBlockSize());
        // 初始化密码类Cipher
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        // 执行加密操作
        byte[] result = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密操作
     * @param input
     * @param transformation
     * @param algorithm
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String decrypt(String input, String transformation, String algorithm, String key, String iv) throws Exception {
        // 获取密码类Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 获取密钥
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        // 获取初始化向量
        IvParameterSpec ivSpec = getIv(iv, transformation, cipher.getBlockSize());
        // 初始化密码类Cipher
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        // 执行解密操作
        byte[] result = cipher.doFinal(Base64.decodeBase64(input));
        return new String(result, StandardCharsets.UTF_8);
    }


    /**
     * 对明文进行手动填充
     * 填充方案选定为"NoPadding"时，如果明文长度刚好是加密算法所需的块大小的倍数时，无需处理。否则需要手动后填充
     * @param input
     * @param transformation
     * @param byteSize
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String plainTextPadding(String input, String transformation, int byteSize) throws UnsupportedEncodingException {
        if (transformation.contains(NO_PADDING)){
            byte[] plaintext = input.getBytes();
            int plaintextLength = plaintext.length;
            if (plaintextLength % byteSize != 0) {
                plaintextLength = plaintextLength + (byteSize - (plaintextLength % byteSize));
            }
            byte[] newplaintext = new byte[plaintextLength];
            //注意：当前自动填充的 0 ，对应的ASCII码是 NUT，而不是空格
            System.arraycopy(input.getBytes(), 0, newplaintext, 0, plaintext.length);
            input = new String(newplaintext, StandardCharsets.UTF_8);
            log.info("填充后的原文: 【{}】", input);
            return input;
        }
        // 如果选择的不是 NoPadding ，这一步可以省略
        return input;
    }

    /**
     * 获取密钥
     * @param givenKey
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey getSecretKeySpec(String givenKey, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (givenKey == null) {
            // 如果没有指定密钥，那么就会使用一个随机生成的密钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            SecretKey secretKey= keyGenerator.generateKey();
            log.info("key is not specified, the newly generate key: 【{}】", Base64.encodeBase64String(secretKey.getEncoded()));
            return secretKey;
        } else {
            // 如果指定了密钥，那么就使用指定的密钥
            return new SecretKeySpec(givenKey.getBytes(StandardCharsets.UTF_8), algorithm);
        }
    }

    /**
     * CBC模式下，获取初始化向量
     * @param givenIv
     * @param transformation
     * @param byteSize
     * @return
     * @throws UnsupportedEncodingException
     */
    public static IvParameterSpec getIv(String givenIv, String transformation, int byteSize) throws UnsupportedEncodingException {
        if (!transformation.contains(CBC_NEED_IV)){
            return null;
        }
        byte[] iv;
        // 如果没有指定初始化向量，那么就会使用一个随机生成的向量
        if (givenIv == null || givenIv.length() == 0) {
            // 生成一个随机的初始化向量,向量的长度需要和分组长度相同，AEC加密算法的分组长度固定为 16，DEC加密算法的分组长度固定为 8
            iv = new byte[byteSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            log.info("iv is not specified, the newly generate iv: 【{}】", Base64.encodeBase64String(iv));
            return new IvParameterSpec(iv);
        }
        iv = givenIv.getBytes(StandardCharsets.UTF_8);
        return new IvParameterSpec(iv);
    }
}
