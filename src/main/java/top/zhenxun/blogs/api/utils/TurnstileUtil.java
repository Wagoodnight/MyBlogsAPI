package top.zhenxun.blogs.api.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Component
public class TurnstileUtil {
    private static final String VERIFY_URL = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    /**
     * 验证 Turnstile Token 是否有效
     *
     * @param token 用户提交的 Turnstile Token
     * @return 验证结果，true 表示验证通过，false 表示失败
     */
    public static boolean validateToken(String token ,String secretKey) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(VERIFY_URL);

            JSONObject requestBody = new JSONObject();
            requestBody.put("secret", secretKey);
            requestBody.put("response", token);

            httpPost.setEntity(new StringEntity(requestBody.toString()));
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(responseString);

            return jsonResponse.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
