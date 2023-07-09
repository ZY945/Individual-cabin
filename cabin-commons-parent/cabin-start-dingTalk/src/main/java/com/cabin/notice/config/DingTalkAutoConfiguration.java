package com.cabin.notice.config;

import com.cabin.notice.empty.DingDingConnect;
import com.cabin.notice.util.DingTalkHelper;
import com.cabin.notice.util.DingTalkManager;
import com.dingtalk.api.DingTalkClient;
import com.taobao.api.DefaultTaobaoClient;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 伍六七
 * @date 2023/7/1 22:34
 */
@Configuration
@EnableConfigurationProperties(DingDingConnect.class)
public class DingTalkAutoConfiguration {
    @Resource(name = "dingDingConnect")
    private DingDingConnect connect;

    @Bean(name = "DingTalkClient")
    public DingTalkClient getDingTalkClient() {
        return DingTalkManager.connectByToken(connect);
    }

    @Bean(name = "DingTalkProxyClient")
    public DefaultTaobaoClient getDingTalkProxyClient() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return DingTalkManager.proxy(connect);
    }

    @Bean(name = "DingTalkHelper")
    public DingTalkHelper getDingTalkHelper() {
        return DingTalkHelper.getSingleton();
    }

}
