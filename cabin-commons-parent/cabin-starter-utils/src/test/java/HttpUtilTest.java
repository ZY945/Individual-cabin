import com.cabin.utils.http.HttpUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


/**
 * @author 伍六七
 * @date 2023/5/20 21:45
 */
public class HttpUtilTest {
    @Test
    void getStrResponse() {
        String strResponse = HttpUtil.get("https://www.baidu.com",new HashMap<>());
        System.out.println(strResponse);
        // 注意需要api返回的格式
//        JSONArray jsonArrayResponse = HttpUtil.getJsonArrayResponse("https://www.baidu.com");
//        System.out.println(jsonArrayResponse);
//        JSONObject jsonObjectResponse = HttpUtil.getJSONObjectResponse("https://www.baidu.com");
//        System.out.println(jsonObjectResponse);
    }
}
