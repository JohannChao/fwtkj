package com.johann.encryption;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密算法
 *
 * Signature可用的算法:
 *     https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#signature-algorithms
 *
 * @author Johann
 * @version 1.0
 * @see
 **/
@Slf4j
public class AsymmetricEncryption {

    private static Map<String, Object> keyMap = new HashMap<>();
    private static String publicKeyStr;
    private static String privateKeyStr;

    public static void main(String[] args) {
        // 加密和解密：公钥加密，私钥解密
//        try {
//            initKey();
//            log.info("公钥：【{}】", publicKeyStr);
//            log.info("私钥：【{}】", privateKeyStr);
//            AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();
//            String source = "RSA非对称加密算法";
//            String encryptStr = asymmetricEncryption.encryptWithPublickey(source,publicKeyStr);
//            log.info("加密后的数据：【{}】", encryptStr);
//            String decryptStr = asymmetricEncryption.decryptWithPrivateKey(encryptStr,privateKeyStr);
//            log.info("解密后的数据：【{}】", decryptStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 签名和验签：私钥签名，公钥验签
        try{
            initKey();
            log.info("公钥：【{}】", publicKeyStr);
            log.info("私钥：【{}】", privateKeyStr);
            AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();
            String source = "RSA非对称加密算法";
            String sign = asymmetricEncryption.signWithPrivateKey(source,privateKeyStr);
            log.info("签名后的数据：【{}】", sign);
            boolean verifyFlag = asymmetricEncryption.verifyWithPublicKey(source,publicKeyStr,sign);
            log.info("验签结果：【{}】", verifyFlag);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化密钥对
     * @throws Exception
     */
    private static void initKey() throws Exception {
        // 使用KeyPairGenerator 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为 1024 位
        keyPairGenerator.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥和私钥字符串
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
    }


    /**
     * 使用公钥对数据进行加密
     * @param source
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public String encryptWithPublickey(String source,String publicKeyStr) throws Exception {
        // 获取公钥
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
        // X509EncodedKeySpec 用于构建公钥的规范
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        // 获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
        // 使用公钥对数据进行加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 使用私钥对数据进行解密
     * @param source
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public String decryptWithPrivateKey(String source,String privateKeyStr) throws Exception {
        // 获取私钥
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
        // PKCS8EncodedKeySpec 用于构建私钥的规范
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // 获取RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        // 使用私钥对数据进行解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(source.getBytes(StandardCharsets.UTF_8)));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 使用私钥对数据进行签名
     * @param source 原始数据
     * @param privateKeyStr 私钥
     * @return 签名后的数据
     * @throws Exception
     */
    public String signWithPrivateKey(String source,String privateKeyStr) throws Exception {
        // 获取私钥
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
        // PKCS8EncodedKeySpec 用于构建私钥的规范
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // 获取RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        // 使用私钥对数据进行签名
        // 获取签名对象
        Signature signature = Signature.getInstance("SHA256withRSA");
        // 初始化签名对象
        signature.initSign(privateKey);
        // 更新要签名的内容
        signature.update(source.getBytes(StandardCharsets.UTF_8));
        // 签名操作
        byte[] sign = signature.sign();
        return Base64.encodeBase64String(sign);
    }

    /**
     * 使用公钥对签名进行验证
     * @param source 原始数据
     * @param publicKeyStr 公钥
     * @param sign 签名后的数据
     * @return
     * @throws Exception
     */
    public boolean verifyWithPublicKey(String source,String publicKeyStr,String sign) throws Exception {
        // 获取公钥
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
        // X509EncodedKeySpec 用于构建公钥的规范
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        // 获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
        // 使用公钥对签名进行验证
        // 获取签名对象
        Signature signature = Signature.getInstance("SHA256withRSA");
        // 初始化签名对象
        signature.initVerify(publicKey);
        // 更新要验证的内容
        signature.update(source.getBytes(StandardCharsets.UTF_8));
        // 验证签名
        byte[] signBytes = Base64.decodeBase64(sign.getBytes(StandardCharsets.UTF_8));
        boolean verifyFlag = signature.verify(signBytes);
        return verifyFlag;
    }
}
