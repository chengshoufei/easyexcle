//package com.example.demo1.android;
//
//
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import javax.crypto.Cipher;
//import java.io.ByteArrayOutputStream;
//import java.security.*;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
////import java.util.Base64;
//import android.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: zhr     5.02版本
// * @Date: create in 2022/6/16 20:47
// * @Description:
// */
//public class Rsa {
//    public static final String CHARSET = "UTF-8";
//    public static final String RSA_ALGORITHM = "RSA"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思
////    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思
//
//    //请求公钥
//    public static final String REQ_PUB = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsvAGttnSD3ohaTkvHGAQUjvBrXlqpmNG2zvYlgLjip8E/Nq0fgRwtuTn5WHO8i8LS0ZRckGAODqQxrFgMwpOJBRBzjlXy/4V016o/lI3mZ4wPCT9q8PPc1o3Yj7k9gFB8WI36BnuL2Mq8SiG5908mN1Ye3qQE6PhewP48JzrLN5sWNVt5dRBn09rHg3uc2eORargECCXzsPWyV9ZsDFNFfZq1KHEEcAcTkUGM52UHuSS0h+huSWQpycaD3+IyZW8QQgmLHMf6a7WTYVfAXEuaP501HgGsggTOWpCl/99AWKm92WKIrSGvZKTtR4kqQLj9KxMZ3zOUmZpn4z3oYnojwIDAQAB";
//    //请求密钥
//    public static final String REQ_PRI = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCy8Aa22dIPeiFpOS8cYBBSO8GteWqmY0bbO9iWAuOKnwT82rR+BHC25OflYc7yLwtLRlFyQYA4OpDGsWAzCk4kFEHOOVfL/hXTXqj+UjeZnjA8JP2rw89zWjdiPuT2AUHxYjfoGe4vYyrxKIbn3TyY3Vh7epATo+F7A/jwnOss3mxY1W3l1EGfT2seDe5zZ45FquAQIJfOw9bJX1mwMU0V9mrUocQRwBxORQYznZQe5JLSH6G5JZCnJxoPf4jJlbxBCCYscx/prtZNhV8BcS5o/nTUeAayCBM5akKX/30BYqb3ZYoitIa9kpO1HiSpAuP0rExnfM5SZmmfjPehieiPAgMBAAECggEAB23hyXu3tf7LHlRKeXIfm1pxyyMMY4Yhrf6JGolaKyo5CcqGzeifSWitnB94eSOs414zCXxgt3TvB+DbbqMR5XuV09sboxOUkmcOuyhsuIsR051xjs0dctufCwwzc2dP9pauXYueEQecA9E78KqF1o7yGoZXLwmR1gqvLkZFmWI85Ut9WVhRNFZHpWusOM/Zm/sg0iX46xPrwUGioatNnjidRUMZwtyAezhpcIM7dynyPhaT/zo2EPRbXj8nl9c6qScCEEnBS+6xUmLup1m4PLgTVzp7YOu/mUFaZzuunJHZkDpMmhnLp3nPtOp86Ffw2iQuCq4RrRD6aWi4X5M28QKBgQDo7tssndrXLB/HoMPWRm8tsrxRdemdOEdPmQqMVyfyVcx0EnAIIp6b1M4Y25/p00eOaNmrp+TQ8T2wlAxfWOhQgtgDecD34ijSq9MtTCcuTkzMXPG+yv1nbNfTDzqEJ4d72lSpdUa/vXLtEkpEGFhAEVkLwcoIkdY5q/20elSstwKBgQDEqFGA5ZhFOb/2r+BI3XaY2f1ArPTtIRX+SADj/kCl977BzSrFEFFDiOd7gqQxvNnBs/36yjNciA2ynz8OD/oNJ09ZzYbSubfCnN+6AgaU5wNRCP5eKYvkb8+HGAzFpWiQDq8kMQN8jiUvRTJaayRFa74dGNh3Xkdqh1oIIqL66QKBgFycw2Xs6VrN/2C2ycaDQKcRvpBA6n6JJsk+FxSPfqF6fYzU29hQM3HP3ZXClMU6LOQxSgJXAcIVeKqhYn3ycoO4UjynhxLHx+VjTdZfxeBCX4SNm8x5FuGtQ+51ypVKxjJ3L1wk9dk8cNpTgFWsvEHU4+upxDM+EMfjWz8LuGjnAoGAbTkK42shMjmL24f9KpsN1oEj/jrRYa6Bl5QIIpjA8SzzHpJD0eVpLPgpkNiTNyirTMEaK6MH+bl62h1GGRpYh9uwX7Ed04XggM8+FwIvA552ohg7prKuhrZu7Zo25cMjmvb84T3zsMcF8gYdnEhrgb8AfQqil1GempUINQJ/wJkCgYBtOl8ciFPTmEXXJqtV/vD8pbrMM8m6gfJFtc7YnRoOsszrXbTIlznoxWtjvcFZNny7LcYkkvPS0dvSHzfuT/QB57J23AekXu7QermECyBOyr9GVtQh3fA5rmcJAV9CwOv/TmypZP+4aMBQvzCQmUmkFuAiRXKtVfFpxdS7x53jYQ==";
//    //响应公钥
//    public static final String RES_PUB = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuDFh9yAxI9xKlcMfLu98dcvtY4SlvjrOwl5IsckDlXj0EO/RdDxQwQ+d/1RFLj4zOAR9+DhxrR6WYb+3XccA9nrqh1Sdp6sQPDWEHDm3Mkbc6heMvQW1Uk1o4yu34SPKDo7Q3EcKJOupAPJ0X4L1iZ5+ku/LnIAfZEG6pC0c3uasmTU0aZLIXziyCplEgj9DtmM9dRcBmNhAWloyLW+mFX+Lk/7XHxHm6oQiojGcxw+lcGm6fHgUp8D9ZVO8Ycgigrsx1O1C41fnS82eYF+1KTFUm7jp44mDyJmKs95gMxrbuIzoE24RPzk2Tyt9sX/DkH+WTPXhUndsCVMzgEBkJwIDAQAB";
//    //响应密钥
//    public static final String RES_PRI = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4MWH3IDEj3EqVwx8u73x1y+1jhKW+Os7CXkixyQOVePQQ79F0PFDBD53/VEUuPjM4BH34OHGtHpZhv7ddxwD2euqHVJ2nqxA8NYQcObcyRtzqF4y9BbVSTWjjK7fhI8oOjtDcRwok66kA8nRfgvWJnn6S78ucgB9kQbqkLRze5qyZNTRpkshfOLIKmUSCP0O2Yz11FwGY2EBaWjItb6YVf4uT/tcfEebqhCKiMZzHD6Vwabp8eBSnwP1lU7xhyCKCuzHU7ULjV+dLzZ5gX7UpMVSbuOnjiYPImYqz3mAzGtu4jOgTbhE/OTZPK32xf8OQf5ZM9eFSd2wJUzOAQGQnAgMBAAECggEANeIcapL8TSVQnGcsZCqjGkLB+27+nZvOiIXUdk37wmpT2/CMMZjDdvb+nYclhdWkXirSPx8kAiUEkSvolIJ3AaQLn/Nc+4PLiBGPyEIKGacpUUH6ZgGZfa4vlOVhMXeq0H9vEkGl8g8Orbw/1wEUm2HGXW3Q/sNsa4Pe+dBItdv+Ak/AKJ7gzXWBCXJ2kL600jTQd7Mp++VkajdtwWOeTsi9X6j0Tzcg3I/2D0LeDbmcL92sPFjW02a0mVaZnJgK+Aa6+f81FeR1/gKrtegkx51OGmTBB/tdhgKG7m2yvMOCX4tTmP4qoAsXb64j9Jna1n78R4ANFH8LhKj6BSImgQKBgQDIPL59OTp4ym+mJFTZYVLdb87QwE85PP3AaD8rSi9yH26IdmqjYoILp9eXoekDAwXn07UoRxwHBVX8rzu/Myz/PWX8ZFLCcLKyeudM0y1OIgvQe1ML6ggk7v/vXytORteMXkYySlickV2tcCVBjhvVIo928UAZMF4ooQB6xl8tlwKBgQDrfM60TLgckagcoZZMNs4YLSJbIIQI5GGE6w6wdRk3gfaSTkJFCh9fFV7LbOjew+He0G+dqjWAW8mzbLemL5lGM2rahjS2UzgJCgp0eYsi0zP3tmRTaoSpcnZXwL3zSQ/HmAzgkEi8U9YzUqN7gDHkfYJ8UGHrHDjGluQf425v8QKBgCCoj6Ua7WeAFP+zE0e1MO++6atwgjtisDxuhEcKPXzOlZeaQMBPM8i04lvv43JDTvZFuFlOZ8rZWvHwnr0Lmr4yhGzK7BicivjDRPBpzQzeTrD9PElILXXgqUugaQ7GTSeuxKI1MAixSOcd0GXFyNJVZbLPnFW/ZswXnl1n92QXAoGBANWgYnBEDN218VkP6aX4LdzWzQz4IW9jp2gKSUgg4qBtYP72yb2R4kXCwD0w7sxvzO7yDd9s8p4gJomDvdKf6Z86s9vL1fP06KeimTtyq5sLGGDG8JqPzgteECepbWhPxmXd9QifO6zFLVNfy3kVIa3TglU+IqZD8umNJyQakSwxAoGADNZLHg9SM7FqIeEoMKlJkO0vQXNfJrcCGTkj2SlORULu9jyMw/Bqt+DRqE/8XyEx3Tm2xRQcPPhRmyLUzu8+kEDVOcuFMfDuLa372WSncufa8YmNGqstsesWw3PAWD4Ef534BOOs4tG1i5Ezoj35j85Br9vn89F5V3cCbo2yVX0=";
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static Map<String, String> createKeys(int keySize) {
//        // 为RSA算法创建一个KeyPairGenerator对象
//        KeyPairGenerator keyPairGenerator;
//        try {
//            keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
//        } catch (NoSuchAlgorithmException e) {
//            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
//        }
//        // 初始化KeyPairGenerator对象,密钥长度
//        keyPairGenerator.initialize(keySize);
//        // 生成密匙对
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        // 得到公钥
//        Key publicKey = keyPair.getPublic();
//
////        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//        String publicKeyStr = Base64.encodeToString(publicKey.getEncoded(),Base64.NO_WRAP);
//        // 得到私钥
//        Key privateKey = keyPair.getPrivate();
////        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//        String privateKeyStr = Base64.encodeToString(privateKey.getEncoded(),Base64.NO_WRAP);
//        // map装载公钥和私钥
//        Map<String, String> keyPairMap = new HashMap<String, String>();
//        keyPairMap.put("publicKey", publicKeyStr);
//        keyPairMap.put("privateKey", privateKeyStr);
//        // 返回map
//        return keyPairMap;
//    }
//
//    /**
//     * 得到公钥
//     *
//     * @param publicKey 密钥字符串（经过base64编码）
//     * @throws Exception
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        // 通过X509编码的Key指令获得公钥对象
//        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
////        byte[] bytes = Base64.getDecoder().decode(publicKey);
//        byte[] bytes = Base64.decode(publicKey,Base64.NO_WRAP);
//        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
//        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
//        return rsaPublicKey;
//    }
//
//    /**
//     * 得到私钥
//     *
//     * @param privateKey 密钥字符串（经过base64编码）
//     * @throws Exception
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        // 通过PKCS#8编码的Key指令获得私钥对象
//        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
////        byte[] bytes = Base64.getDecoder().decode(privateKey);
//        byte[] bytes = Base64.decode(privateKey,Base64.NO_WRAP);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytes);
//        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
//        return rsaPrivateKey;
//    }
//
//    /**
//     * 公钥加密
//     *
//     * @param data
//     * @param publicKey
//     * @return
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
//        try {
////            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
////            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
//            return Base64.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()),Base64.NO_WRAP);
//        } catch (Exception e) {
//            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    /**
//     * 私钥解密
//     *
//     * @param data
//     * @param privateKey
//     * @return
//     */
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
//        try {
////            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);
////            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), privateKey.getModulus().bitLength()), CHARSET);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decode(data,Base64.NO_WRAP), privateKey.getModulus().bitLength()), CHARSET);
//        } catch (Exception e) {
//            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    /**
//     * 私钥加密
//     *
//     * @param data
//     * @param privateKey
//     * @return
//     */
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
//        try {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            //每个Cipher初始化方法使用一个模式参数opmod，并用此模式初始化Cipher对象。此外还有其他参数，包括密钥key、包含密钥的证书certificate、算法参数params和随机源random。
//            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
////            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
//            return Base64.encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()),Base64.NO_WRAP);
//        } catch (Exception e) {
//            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    /**
//     * 公钥解密
//     *
//     * @param data
//     * @param publicKey
//     * @return
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
//        try {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, publicKey);
////            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), publicKey.getModulus().bitLength()), CHARSET);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decode(data,Base64.NO_WRAP), publicKey.getModulus().bitLength()), CHARSET);
//        } catch (Exception e) {
//            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    //rsa切割解码  , ENCRYPT_MODE,加密数据   ,DECRYPT_MODE,解密数据
//    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
//        int maxBlock = 0;  //最大块
//        if (opmode == Cipher.DECRYPT_MODE) {
//            maxBlock = keySize / 8;
//        } else {
//            maxBlock = keySize / 8 - 11;
//        }
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] buff;
//        int i = 0;
//        try {
//            while (datas.length > offSet) {
//                if (datas.length - offSet > maxBlock) {
//                    //可以调用以下的doFinal（）方法完成加密或解密数据：
//                    buff = cipher.doFinal(datas, offSet, maxBlock);
//                } else {
//                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
//                }
//                out.write(buff, 0, buff.length);
//                i++;
//                offSet = i * maxBlock;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
//        }
//        byte[] resultDatas = out.toByteArray();
////        IOUtils.closeQuietly(out);
//        return resultDatas;
//    }
//
//
//    // 简单测试____________
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void main(String[] args) throws Exception {
//
//        Map<String, String> keyMap = Rsa.createKeys(2048);
//        String publicKey = keyMap.get("publicKey");
//        String privateKey = keyMap.get("privateKey");
//
//        String data = "{source=020003, serviceCode=jszzg, apiCode=40020016, data=[{\"loginName\":\"zxc\",\"orgNo\":\"3240101\"}], target=32401}";
//        data = Rsa.publicEncrypt(data, Rsa.getPublicKey(Rsa.REQ_PUB));
//        System.out.println("加密后文字: \r\n" + data);
//
//        data = Rsa.privateDecrypt("SRFuKq2nmFr0qkM7gaYNtIItm+E3iV4i9UewkEcZN49lkcoamrpoyIeFTXTzQ08l9a6sD67CqduUOxb02tq3vCx21qsUfkbMZa1pMky1K79BWXEP76fQjwU7fq0Tv1/3ALhqPn1H0STJOzvcz6zEsUuZr40TNaOl6IHoZ9qSJQ3KXn6m/n3K7R6IIxdiFLPdJyq2g51Mw0J7eVfuHRU/k+C18Mc8aJhWtPGiX66qSRBsrYcXULswXBEo3YRtO6iMhlW/pRePnjwJsjjKyJ0+VAk7mKxQAQjnd5h1H+yHIFO9PYNbyh6v49+RtvudN8OTtJOShvqmBWttARZfo5JcUQ==", Rsa.getPrivateKey(Rsa.REQ_PRI));
//        System.out.println("解密后文字: \r\n" + data);
//
//        data = "mtMJp5kBZ9Yqr78ufT/KSPA6Xkwy6HhTmoExT3KR3yxRCGAUUDVbfMoJQ6Rg66aXibfgwfA3MgMApkR4d/8wo1CeX9hHPrPpW7cstNCH+khl45BUzXVd3Tul59a2tFwr/pTiZzGbWub48T6ftmkfY+PQc9MCQTgqHSEhqEtJLafLhD2hrkaSLsrdFH5CdJVIXGIhbAp86I03lFpB+Lte/RLsSiOJ1CDcOKjUpSLjIu/bFleMVJhjgMPitmSW2e6lUYgtLyS8Qdfuxavtz2iSaiRdfUmzLMYUNXmLED6iZOuYm7zK+G06P6up3mSRG3zRvCKuII4uHjxVznuIF00US1q8+oNj6niVqjEnbrk/JJNTAMTmuObYYBfPFYfz4QQiGsTFEPa4XaRfXRt6oyS505jAXLNy7U0WmW/2XxXAkKh7Sz66e8cKzNStM5uIqhZ/9rcMu/9v7KFqRQl/CLxkFMHvlCZfVqJlwnWWpNLnJh5xQFfH/6uZ67t6IDfYdWFYRWPIbYbq4RxDsJUZdx4ZrK4bA5M3ZxNmzsPGEWuKmVRF2RHtSEL5cRnZw+itkoGtuoG6zzElrEdixtwHvo9MfZEu7rR3o5jUkH2QKvV6vv8cG0eX474fIm9+oOCb/HfzFJbdeUHTWbed02UumCSIxPtUV1j6i0rSLvega0nehMZA5Xs4xGAeBEUiscypP+qo8tKxjHNQYFKg44eIRcjnxlnr61Z0fMBenD57peSXc8PTKQoJiIuqKmVtjgwzUB1nsBz9kX6cY5e1FEHy30PJCb2Zet8fA4jWvc3x+UgY7IMtVcoRFPOwvCPh6cnjgf5p4cHqlOn4KxsVNyDZV8vewWSy7rujB8TpA0OMyjVE+C5yPN/EXp2eNZbxP6P/0aSthCxpI5ol221EidFXIJjKkVun18w0f1sY6X3rs+FCEQBVbcxZoQkQUiql0DZSE9w649/AaNlaJolv5FrmA/ZlQr3IJvhFsBw+GXI/N1mtZ8fxxn48AeuT0NFoW6/Lds55bO0VfauRGQjum08aERIFEJh1Owbkf3ZS1TrINpQmVglKXuBcIgDxJoAdk2Pjszl4aKYygCJ3k5Jpdu4+jYD4GOCuog5gkTLhf2Cmw9MTRRHzeF1i6Hq9XRMsspOjcG3D5Bz7bvgxCXbG3YgAnIop35ptMNP28PUlMInnIUdoX5dzBCdJU2z6eh5OIk5CffCRR95tTGk78T6LQqLDGwgOjZrjccdoAlHuD2xZ1lwFD/KKIi6UP0M+7rj9akMz0mvyjQlyN6eL+V/OsdwQE+AUAxXDhQZW8uXFLKgYi8hJLriaryYu7Sppufbi4i2uI5FEBGrqlVJYgBiaIzNjiOx5Lw==";
//        data = Rsa.privateDecrypt(data, Rsa.getPrivateKey(Rsa.RES_PRI)); //传入密文和私钥,得到明文
//
//        System.out.println("解密后文字: \r\n" + data);
//
//    }
//}
