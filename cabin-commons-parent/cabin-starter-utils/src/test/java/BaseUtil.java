import com.cabin.utils.commonUtil.Base64Util;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author 伍六七
 * @date 2023/5/21 15:07
 */
public class BaseUtil {

    @Test
    void encoder() throws UnsupportedEncodingException {
        String encoderByUtf8 = Base64Util.encoderGetStrByByte("陈哈哈\uD83D\uDE13");
        System.out.println(encoderByUtf8);
        String decoderByUtf8 = Base64Util.decoderGetStrByStr(encoderByUtf8);
        System.out.println(decoderByUtf8);
    }
}
