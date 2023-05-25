
import com.cabin.utils.dateUtil.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/5/21 14:42
 */
public class DateTest {

    /**
     * 正则匹配，解析
     * 举例:
     * 2023年2月21日 下午07时14分06秒
     *
     * @throws ParseException
     */
    @Test
    void getDateByMailContent() throws ParseException {
        Date dateByMailContent = DateUtil.getDateByMailContent("2023年2月21日 下午07时14分06秒");
        System.out.println(dateByMailContent);
    }

    /**
     * jdk 1.8<br/>
     * 获取当前时间<br/>
     * 格式: HH:mm:dd<br/>
     *
     * @return String
     */
    @Test
    void getNowTimeStr() {
        String nowTimeStr = DateUtil.getNowTimeStr();
        System.out.println(nowTimeStr);
    }


    /**
     * jdk 1.8<br/>
     * 获取当前时间<br/>
     * 格式: yyyy-MM-dd<br/>
     *
     * @return String
     */
    @Test
    void getNowDateStr() {
        String nowDateStr = DateUtil.getNowDateStr();
        System.out.println(nowDateStr);
    }

    /**
     * jdk 1.8 <br/>
     * 获取当前时间<br/>
     * 格式: yyyy-MM-dd HH:mm:dd<br/>
     *
     * @return String
     */
    @Test
    void getNowDateTimeStr() {
        String nowDateTimeStr = DateUtil.getNowDateTimeStr();
        System.out.println(nowDateTimeStr);
    }

    /**
     * 只能解决当天的24
     *
     * @return
     */
    @Test
    void getTimeDelta() {
        long timeDelta = DateUtil.getTimeDelta("2023-5-21 12:00:00", "2023-5-21 14:30:00", DateUtil.Time.HOUR);
        System.out.println(timeDelta);
    }
}
