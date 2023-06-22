package com.cabin.common.util.request;

import com.blueconic.browscap.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;


/**
 * @author 伍六七
 * @date 2023/4/25 10:41
 * 解析设备,这个需要注入,否则每次创建需要的时间太长
 */
@Component
public class DeviceUtil {

    private static volatile UserAgentParser userAgentParser;//防止重排序
    //优化:直接在方法上加锁，任何时候都会上锁
    //我们需要在没有实例对象时上锁即可


    /**
     * 优化方法
     * 1、@Component+@PostConstruct
     * 2、@Bean(initMethod = "init")
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @PostConstruct
    public static UserAgentParser getSingleton() throws IOException, ParseException {//加锁
        if (userAgentParser == null) {
            synchronized (UserAgentParser.class) {
                if (userAgentParser == null) {//再次判断，获取到锁的时候，可能前面已经新建了
                    userAgentParser = new UserAgentService().loadParser(Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_TYPE,
                            BrowsCapField.BROWSER_MAJOR_VERSION,
                            BrowsCapField.DEVICE_TYPE, BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION,
                            BrowsCapField.RENDERING_ENGINE_VERSION, BrowsCapField.RENDERING_ENGINE_NAME,
                            BrowsCapField.PLATFORM_MAKER, BrowsCapField.RENDERING_ENGINE_MAKER));
                }
            }
        }
        return userAgentParser;
    }

    public static Capabilities getCapabilities(String userAgent) throws IOException, ParseException {
        return userAgentParser.parse(userAgent);
    }
}
