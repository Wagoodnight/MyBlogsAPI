package top.zhenxun.blogs.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.pojo.CurrentLoginUser;

import java.util.Date;

/**
 * Token 工具类，用于生成和解析 JWT (JSON Web Token)
 * 1. 创建登录令牌
 * 2. 从令牌中解析用户信息
 * 3. 验证令牌有效性
 *
 * @author li
 */
public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 生成登录令牌
     *
     * @param loginUser 当前登录用户对象
     * @return 生成的 JWT 字符串
     */
    public static String createLoginToken(CurrentLoginUser loginUser) {
        // 设置令牌过期时间
        Date date = new Date(System.currentTimeMillis() + Const.ADMIN_EXPIRE_TIME);
        // 使用 HMAC256 算法加密令牌
        Algorithm algorithm = Algorithm.HMAC256(Const.TOKEN_SECRET);
        // 将登录用户信息序列化为 JSON 字符串
        String jsonStr = JSON.toJSONString(loginUser);
        // 创建并返回令牌
        return JWT.create()
                .withClaim("body", jsonStr) // 添加自定义的 "body" 声明，存储用户信息
                .withExpiresAt(date)       // 设置令牌的过期时间
                .sign(algorithm);          // 使用指定算法签名令牌
    }

    /**
     * 从令牌中解析用户信息
     *
     * @param token JWT 字符串
     * @return 解析出的用户信息对象
     */
    public static CurrentLoginUser getPayload(String token) {
        // 解码令牌
        DecodedJWT jwt = JWT.decode(token);
        // 获取 "body" 声明的内容（用户信息 JSON 字符串）
        String jsonStr = jwt.getClaim("body").asString();
        // 将 JSON 字符串反序列化为用户信息对象
        CurrentLoginUser userToken = JSONObject.parseObject(jsonStr, CurrentLoginUser.class);
        return userToken;
    }

    /**
     * 验证令牌有效性
     *
     * @param token JWT 字符串
     * @return 验证结果，true 表示令牌有效，false 表示令牌无效
     */
    public static boolean verify(String token) {
        try {
            // 使用 HMAC256 算法和密钥构造验证器
            Algorithm algorithm = Algorithm.HMAC256(Const.TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 验证令牌的有效性
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            log.warn("Token 验证失败");
            return false;
        }
    }
}
