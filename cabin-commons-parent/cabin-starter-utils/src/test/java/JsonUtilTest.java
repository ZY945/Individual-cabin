import com.cabin.utils.jsonUtil.JsonUtil;
import org.junit.jupiter.api.Test;

/**
 * @author 伍六七
 * @date 2023/5/23 14:38
 */
public class JsonUtilTest {
    @Test
    void formatJson() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        System.out.println(JsonUtil.formatJson(json));
    }
}
