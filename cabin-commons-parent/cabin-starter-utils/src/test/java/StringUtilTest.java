import com.cabin.utils.commonUtil.StringUtil;
import org.junit.jupiter.api.Test;

/**
 * @author 伍六七
 * @date 2023/5/21 14:58
 */
public class StringUtilTest {
    @Test
    void getWord() {
        String content = "hello,Shan dian.hello,dong tu";
        System.out.println(StringUtil.getDistinctWordSet(content, false));
        System.out.println(StringUtil.getDistinctWordSet(content, true));
        System.out.println(StringUtil.getWord(content));
        System.out.println(StringUtil.getWordMap(content, false));
        System.out.println(StringUtil.getWordMap(content, true));

    }

    @Test
    void KMP() {
        int kmp = StringUtil.KMP("getDis tinctDistinctWordSet", "Distinct");
        System.out.println(kmp);
    }
}
