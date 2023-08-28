package com.johann.encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/** 使用 Cipher 进行加密、解密
* @Description:
* @Author: Johann
*/
public class TestEncrypt {
    
    private static String KEY = "YINFENGAML88JAVA";
    private static String IV = "YINFENGAML88JAVA";

    /**
     * "算法/模式/补码方式"
     * 加密算法有: AES,DES,DESede(DES3)和RSA 四种
     * 模式: 有CBC(有向量模式)和ECB(无向量模式),向量模式可以简单理解为偏移量,使用CBC模式需要定义一个 IvParameterSpec 对象
     * 补码方式: NoPadding: 加密内容不足8位用0补足8位;PKCS5Padding: 加密内容不足8位用余位数补足8位
    */
    private static String typec = "AES/CBC/Pkcs5Padding";
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            //新建Cipher对象,并传入指定的加密模式
            Cipher cipher = Cipher.getInstance(typec);

            //返回块的大小,以字节为单位。AEC加密算法的密钥长度固定为 16
            int blockSize = cipher.getBlockSize();
            System.out.println("blockSize "+blockSize);

            //获取待加密数据的 字节数组
            byte[] dataBytes = data.getBytes();
            //待加密数据 byte数组长度
            int plaintextLength = dataBytes.length;

            System.out.println("plaintextLength "+plaintextLength);
            //如果待加密数据的长度不是 16的倍数,则将其长度扩大至16的倍数。例如加密数据的长度为 33(33>16*2),此时会将加密数据的长度放大为 16*3=48
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            System.out.println("plaintextLength "+plaintextLength);
            //将原数据，放到一个新的数组中,新数组的长度为 16的倍数，超过原数组长度的剩余位数用 0 填充。如果选择 PKCS5Padding ，这一步可以省略
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            String dataNew = new String(plaintext);

            //SecretKeySpec和KeyGenerator支持AES,DES,DESede 三种加密算法创建密匙;KeyPairGenerator支持RSA加密算法创建密匙
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            //使用CBC模式时必须传入该参数
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            //Cipher对象使用前的初始化,传入的参数有(加密模式,加密盐值,加密向量)
            //Cipher.ENCRYPT_MODE(加密模式)和 Cipher.DECRYPT_MODE(解密模式)
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            //进行加密或解密
            byte[] encrypted = cipher.doFinal(plaintext);
            
            //返回加密字符串,直接使用 new String(encrypted) 会出现乱码
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception{
        //注意：自动填充的 0 ，对应的ASCII码是 NUT，而不是空格
        String data = "12345           ";
        String datastr = encrypt(data, KEY, IV);
        System.out.println(datastr);
        System.out.println(datastr.length());
    }
}