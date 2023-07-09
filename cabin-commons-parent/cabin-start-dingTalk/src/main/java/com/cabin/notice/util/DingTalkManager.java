package com.cabin.notice.util;

import com.cabin.notice.empty.DingDingConnect;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.taobao.api.DefaultTaobaoClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 伍六七
 * @date 2023/7/9 12:40
 */
@Slf4j
public class DingTalkManager {

    public static DingTalkClient textClient;
    public static DefaultTaobaoClient proxyClient;

    public static DingTalkClient connectByToken(DingDingConnect connect) {

        try {
            textClient = new DefaultDingTalkClient(connect.getAccess_token() + sign(connect.getSecret()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return textClient;
    }

    public static String sign(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return "&timestamp=" + timestamp + "&sign=" + sign;
    }


    public static DefaultTaobaoClient proxy(DingDingConnect connect) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        if (connect.getProxyHost() == null || connect.getProxyPort() == null) {
            log.error("代理未设置");
            return null;
        }
        String url = connect.getAccess_token() + sign(connect.getSecret());
        proxyClient = new DefaultTaobaoClient(url, connect.getAccess_token(), connect.getSecret());
        SocketAddress sa = new InetSocketAddress(connect.getProxyHost(), connect.getProxyPort());
        proxyClient.setProxy(new Proxy(Proxy.Type.HTTP, sa));
        return proxyClient;
    }
}
