import com.cabin.utils.commonUtil.RandomUtil;
import org.junit.jupiter.api.Test;

/**
 * @author 伍六七
 * @date 2023/5/22 23:21
 */
public class RandomUtilTest {
    @Test
    void nextId() {
        System.out.println(RandomUtil.nextId());
    }

    @Test
    void getUUID() {
        System.out.println(RandomUtil.getUUID());
    }

    @Test
    void getRandStr() {
        System.out.println(RandomUtil.getRandStr(2));
    }

    @Test
    void getRandomChar() {
        System.out.println(RandomUtil.getRandomChar(2));
    }
//    @Test
//    void test(){}
//    @Test
//    void test(){}
//    @Test
//    void test(){}
//    @Test
//    void test(){}
//    @Test
//    void test(){}

}
