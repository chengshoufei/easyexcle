package com.example.demo1.file;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName RSAUtil
 * @Date 2022/6/18 9:46
 * @Author chengshoufei
 * @Description TODO
 */
public class RSAUtil {
    /**
     * 指定加密算法为RSA
     */
    private static String ALGORITHM = "RSA";
    /**
     * 指定key的大小
     */
    private static int KEYSIZE = 1024;
    /**
     * 指定公钥存放文件
     */
    private static String PUBLIC_KEY_FILE = "PublicKey";
    /**
     * 指定私钥存放文件
     */
    private static String PRIVATE_KEY_FILE = "PrivateKey";

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥对
     */
    public static void generateKeyPair() throws Exception {
        if (getpublickey() == null || getprivatekey() == null) {
            /** RSA算法要求有一个可信任的随机数源 */
            SecureRandom sr = new SecureRandom();
            /** 为RSA算法创建一个KeyPairGenerator对象 */
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
            /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
            kpg.initialize(KEYSIZE, sr);
            /** 生成密匙对 */
            KeyPair kp = kpg.generateKeyPair();
            /** 得到公钥 */
            Key publicKey = kp.getPublic();
            /** 得到私钥 */
            Key privateKey = kp.getPrivate();
            /** 用对象流将生成的密钥写入文件 */
            ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
            /** 清空缓存，关闭文件输出流 */
            oos1.close();
            oos2.close();
        }

    }

    /**
     * 产生签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 验证签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否有效
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source) throws Exception {
        generateKeyPair();
        /** 将文件中的公钥对象读出 */
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        Key key = (Key) ois.readObject();
        ois.close();
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int MaxBlockSize = KEYSIZE / 8;
        int len = (MaxBlockSize - 11) / 8;
        String[] datas = splitString(source, len);
        StringBuffer mi = new StringBuffer();
        for (String s : datas) {
            mi.append(bcd2Str(cipher.doFinal(s.getBytes())));
        }
        return mi.toString();

    }

    /**
     * 字符串分片
     *
     * @param string 源字符串
     * @param len    单片的长度（keysize/8）
     * @return
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * bcd 转 Str
     *
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 解密
     *
     * @param cryptograph :密文
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decrypt(String cryptograph) throws Exception {
        generateKeyPair();
        /** 将文件中的私钥对象读出 */
        @SuppressWarnings("resource") ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        Key key = (Key) ois.readObject();
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        int key_len = KEYSIZE / 8;
        byte[] bytes = cryptograph.getBytes();
        byte[] bcd = ASCII2BCD(bytes, bytes.length);
        StringBuffer sBuffer = new StringBuffer();
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            sBuffer.append(new String(cipher.doFinal(arr)));
        }
        return sBuffer.toString();
    }

    /**
     * ASCII 转 BCD
     *
     * @param ascii
     * @param asc_len
     * @return
     */
    public static byte[] ASCII2BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc2bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc2bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    /**
     * asc转bcd
     *
     * @param asc
     * @return
     */
    public static byte asc2bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * 字节数组分片
     *
     * @param data
     * @param len
     * @return
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * 将文件中的公钥对象读出
     */
    public static String getpublickey() {

        try {
            @SuppressWarnings("resource") ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            Key key = (Key) ois.readObject();
            String publickey = encryptBASE64(key.getEncoded());
            return publickey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件中的私钥对象读出
     */
    public static String getprivatekey() {
        try {
            @SuppressWarnings("resource") ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            Key key = (Key) ois.readObject();
            String privatekey = encryptBASE64(key.getEncoded());
            return privatekey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            String s = encrypt("allming.com");
            System.out.println("加密：" + s);
            System.out.println("解密：" + decrypt(s));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
