package com.wangjm.http;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * AES 加密工具类
 *
 * @author Zhouxd 2018/2/24 15:33
 * @version 1.0
 */
public class AesUtil {
    /**
     * 字符串默认编码格式为UTF-8
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * AES加密
     */
    private static final String KEY_AES = "AES";

    private static final int PARSE_BYTE_2_HEX_STR_DIV = 2;

    /**
     * AES加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static String aesEncrypt(String data, String key) {
        try {
            if (null == data || "".equals(data)) {
                return null;
            }
            byte[] dataByte = data.getBytes(DEFAULT_CHARSET);
            SecretKeySpec keySpec = newCipher(key);
            Cipher cipher = Cipher.getInstance(KEY_AES);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(dataByte);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param data 密文
     * @param key  密钥
     * @return 明文
     */
    public static String aesDencrypt(String data, String key) {
        try {
            if (null == data || "".equals(data)) {
                return null;
            }
            byte[] dataByte = parseHexStr2Byte(data);
            SecretKeySpec keySpec = newCipher(key);
            Cipher cipher = Cipher.getInstance(KEY_AES);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(dataByte);
            return new String(result, DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建密码器
     *
     * @param key 密钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec newCipher(String key) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_AES);
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(key.getBytes());
        keyGenerator.init(128,secureRandom);
        //3.产生原始对称密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] enCodeFormat = secretKey.getEncoded();
        //5.根据字节数组生成AES密钥
        //6.根据指定算法AES创建密码器
        return new SecretKeySpec(enCodeFormat, KEY_AES);
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / PARSE_BYTE_2_HEX_STR_DIV; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 根据明文生产128位的加密密文
     *
     * @param key 定义的密码
     * @return 生产的128位密文
     * @throws Exception
     */
    public static String getAesKey(String key) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_AES);
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(key.getBytes());
        keyGenerator.init(128,secureRandom);
        //3.产生原始对称密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] enCodeFormat = secretKey.getEncoded();
        return Arrays.toString(enCodeFormat);
    }
}
