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
    @Test
    void shortUrl() {
        String shortUrl = StringUtil.shortUrl("https://cn.bing.com/search?q=%E5%90%AF%E5%8A%A8%E7%B1%BB%E6%88%96%E8%80%85%E9%85%8D%E7%BD%AE%E7%B1%BB%E4%B8%AD%E9%85%8D%E7%BD%AE%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BA%BF%E7%A8%8B%E6%B1%A0&aqs=edge..69i57&FORM=ANCMS9&PC=EDGEINJP");
        System.out.println(shortUrl);
    }
}
