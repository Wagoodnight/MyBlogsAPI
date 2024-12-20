package top.zhenxun.blogs.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public class EncryptUtil {
    public static String encrypt(String password)  {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 将字符串转换为字节数组
            byte[] bytes = password.getBytes();

            // 使用指定的字节数组更新摘要
            md.update(bytes);

            // 生成摘要
            byte[] digest = md.digest();

            // 将摘要转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
