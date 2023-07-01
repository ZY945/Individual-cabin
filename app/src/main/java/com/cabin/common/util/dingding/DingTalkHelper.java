package com.cabin.common.util.dingding;

import com.aliyun.credentials.utils.StringUtils;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import com.dingtalk.api.request.OapiRobotSendRequest.Text;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * 如果不需要依赖其他实例,静态方法会好一些,如果需要依赖其他实例就注入
 *
 * @author 伍六七
 * @date 2023/6/22 22:38
 */
@Slf4j
@Component
@EnableConfigurationProperties({DingDingConnect.class, DingDingAlarmRobot.class})
public class DingTalkHelper {
    /**
     * 钉钉群设置 webhook, 支持重置
     */
    @Autowired
    @Qualifier("dingDingConnect")
    private DingDingConnect connect;

    /**
     * 钉钉群设置 webhook, 支持重置
     */
    @Autowired
    @Qualifier("dingDingAlarmRobot")
    private DingDingAlarmRobot dingDingAlarmRobot;
    /**
     * 消息类型
     */
    private static final String MSG_TYPE_TEXT = "text";

    /**
     * 客户端实例
     */
    public static DingTalkClient textClient;
    public static DingTalkClient alarmClient;
    public static DefaultTaobaoClient proxyClient;

    @Bean
    public void init() {
        try {
            textClient = new DefaultDingTalkClient(connect.getAccess_token() + sign(connect.getSecret()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            alarmClient = new DefaultDingTalkClient(dingDingAlarmRobot.getAccess_token() + sign(dingDingAlarmRobot.getSecret()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param content    文本消息
     * @param mobileList 指定@ 联系人
     * @param isAtAll    是否@ 全部联系人
     * @description: 发送普通文本消息
     * @return: com.dingtalk.api.response.OapiRobotSendResponse
     * @author: niaonao
     * @date: 2019/7/6
     */
    public OapiRobotSendResponse sendMessageByText(String content, List<String> mobileList, boolean isAtAll) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        //参数	参数类型	必须	说明
        //msgtype	String	是	消息类型，此时固定为：text
        //content	String	是	消息内容
        //atMobiles	Array	否	被@人的手机号(在content里添加@人的手机号)
        //isAtAll	bool	否	@所有人时：true，否则为：false
        Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        if (!CollectionUtils.isEmpty(mobileList)) {
            // 发送消息并@ 以下手机号联系人
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(mobileList);
            at.setIsAtAll(isAtAll);
            request.setAt(at);
        }
        request.setMsgtype(DingTalkHelper.MSG_TYPE_TEXT);
        request.setText(text);

        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = DingTalkHelper.textClient.execute(request);
        } catch (ApiException e) {
            log.error("[发送普通文本消息]: 发送消息失败, 异常捕获{}", e.getMessage());
        }
        return response;
    }

    public OapiRobotSendResponse sendAlarmByMarkdown(Markdown markdown, List<String> mobileList, boolean isAtAll) {
        if (ObjectUtils.isEmpty(markdown)) {
            return null;
        }

        //参数	参数类型	必须	说明
        //msgtype	String	是	消息类型，此时固定为：text
        //content	String	是	消息内容
        //atMobiles	Array	否	被@人的手机号(在content里添加@人的手机号)
        //isAtAll	bool	否	@所有人时：true，否则为：false
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        if (!CollectionUtils.isEmpty(mobileList)) {
            // 发送消息并@ 以下手机号联系人
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(mobileList);
            at.setIsAtAll(isAtAll);
            request.setAt(at);
        }
        request.setMsgtype("markdown");
        request.setMarkdown(markdown);

        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = DingTalkHelper.textClient.execute(request);
        } catch (ApiException e) {
            log.error("[发送普通文本消息]: 发送消息失败, 异常捕获{}", e.getMessage());
        }
        return response;
    }

    public String sign(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(connect.getSecret().getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return "&timestamp=" + timestamp + "&sign=" + sign;
    }


    public DefaultTaobaoClient proxy(String proxyHost, int proxyPort) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        String url = connect.getAccess_token() + sign(connect.getSecret());
        proxyClient = new DefaultTaobaoClient(url, connect.getAccess_token(), connect.getSecret());
        SocketAddress sa = new InetSocketAddress(proxyHost, proxyPort);
        proxyClient.setProxy(new Proxy(Proxy.Type.HTTP, sa));
        return proxyClient;
    }
}
 