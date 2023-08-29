Cipher类支持的算法类型，反馈模式，填充方案。

### 算法名称(algorithm name)
1. AES（Advanced Encryption Standard）：一种广泛使用的对称密钥加密算法，支持128、192和256位密钥
2. AESWrap：一种用于包装AES密钥的算法，遵循RFC 3394标准
3. ARCFOUR：一种流密码，也称为RC4，使用可变长度的密钥
4. Blowfish：一种对称密钥分组密码，由Bruce Schneier开发，用于加密和解密数据
5. ChaCha20：一种流密码，提供高性能和高安全性，适用于各种加密场景
6. ChaCha20-Poly1305：一种基于ChaCha20的认证加密模式，提供加密和数据完整性保护
7. DES（Data Encryption Standard）：一种过时的对称密钥分组密码，现已被AES所取代
8. DESede：一种基于DES的加密算法，也称为Triple DES，使用三个DES密钥进行加密和解密
9. DESedeWrap：一种用于包装DESede密钥的算法
10. ECIES（Elliptic Curve Integrated Encryption Scheme）：一种基于椭圆曲线密码的加密方案，结合了公钥加密和密钥协商技术
11. PBEWith<digest>And<encryption>，PBEWith<prf>And<encryption>：一种基于密码的加密方案，使用密码和伪随机函数（PRF）生成密钥
12. RC2：一种对称密钥分组密码，已被AES所取代
13. RC4：请参阅ARCFOUR。
14. RC5：一种对称密钥分组密码，已被AES所取代
15. RSA：一种广泛使用的非对称加密算法，基于大数因子分解问题，用于加密和数字签名

### 反馈模式(feedback mode)
1. NONE：没有特定的反馈模式。
2. CBC（Cipher Block Chaining）：每个明文块与前一个密文块进行异或运算，然后再加密。这种模式需要一个初始向量（IV）来启动过程
3. CCM（Counter with CBC-MAC）：这是一种基于分组密码的认证加密模式，提供了加密和数据完整性保护
4. CFB（Cipher Feedback）和CFBx：这种模式将分组密码用作流密码。CFB模式使用底层分组密码生成密钥流
5. CTR（Counter）：这种模式将分组密码转换为流密码。所有块都从0开始编号，这些数字是分配给每个块的计数器值。每个块都使用密钥、IV和计数器值进行加密
6. CTS（CipherText Stealing）：这是一种用于处理最后一个数据块不完整的情况的技术，它可以避免使用填充。
7. ECB（Electronic Codebook）：将每个明文块单独加密。这种模式可能会泄露明文模式，因为相同的明文块会产生相同的密文块
8. GCM（Galois/Counter Mode）：这是一种基于分组密码的认证加密模式，提供了加密和数据完整性保护。它结合了CTR模式的加密和Galios域上的乘法来提供认证
9. OFB（Output Feedback）和OFBx：这种模式与CFB模式类似，但它将加密输出作为反馈而不是实际的异或输出。在OFB模式中，所有块的位都被发送，而不是仅发送选定的s位
10. PCBC（Propagating Cipher Block Chaining）：这是一种修改版的CBC模式，它将前一个明文块与当前密文块进行异或运算，然后再将结果与下一个明文块进行异或运算。这种模式提供了更好的错误传播特性。

### 填充方案(padding scheme)
1. NoPadding：不使用任何填充。当明文长度刚好是加密算法所需的块大小的倍数时，可以使用此填充方案。如果明文长度不是所需块大小的倍数，您需要自己处理填充
2. ISO10126Padding：在最后一个数据块中填充随机字节，最后一个字节表示填充的字节数
3. OAEPPadding, OAEPWith<digest>And<mgf>Padding：这是一种用于RSA加密的填充方案，提供了光电子密码本（OAEP）填充。OAEP填充可以提高RSA加密的安全性。您可以指定消息摘要算法和掩码生成函数
4. PKCS1Padding：这是一种用于RSA加密的填充方案，遵循PKCS#1标准。它在数据块中填充随机非零字节
5. PKCS5Padding：这是一种常用的填充方案，适用于分组密码。它在最后一个数据块中填充字节，每个填充字节的值等于填充的字节数
6. SSL3Padding：这是一种用于SSL 3.0协议的填充方案。它在最后一个数据块中填充随机字节，最后一个字节表示填充的字节数


[Cipher类API](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html)
[Java安全标准算法名称](https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html)