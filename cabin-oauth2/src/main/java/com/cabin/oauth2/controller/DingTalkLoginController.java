package com.cabin.oauth2.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DingTalkLoginController {

    public static com.aliyun.dingtalkoauth2_1_0.Client authClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    /**
     * 获取用户token
     *
     * @param authCode
     * @return
     * @throws Exception
     */
    //接口地址：注意/auth与钉钉登录与分享的回调域名地址一致
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAccessToken(@RequestParam(value = "authCode") String authCode) throws Exception {
        com.aliyun.dingtalkoauth2_1_0.Client client = authClient();
        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()

                //应用基础信息-应用信息的AppKey,请务必替换为开发的应用AppKey
                .setClientId("dingsqqsnatkqa4dil8d")

                //应用基础信息-应用信息的AppSecret，,请务必替换为开发的应用AppSecret
                .setClientSecret("mDfHpcB8ImLnH8YjDohsXNgaTGrGDycOfdpd2KXcPzyLbnIq3dsDLoG2TJIZffTD")
                .setCode(authCode)
                .setGrantType("authorization_code");
        GetUserTokenResponse getUserTokenResponse = client.getUserToken(getUserTokenRequest);
        //获取用户个人token
        String accessToken = getUserTokenResponse.getBody().getAccessToken();
        return getUserinfo(accessToken);

    }

    public static com.aliyun.dingtalkcontact_1_0.Client contactClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkcontact_1_0.Client(config);
    }

    /**
     * 获取用户个人信息
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String getUserinfo(String accessToken) throws Exception {
        com.aliyun.dingtalkcontact_1_0.Client client = contactClient();
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.xAcsDingtalkAccessToken = accessToken;
        //获取用户个人信息，如需获取当前授权人的信息，unionId参数必须传me
        String me = JSON.toJSONString(client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody());
        System.out.println(me);
        return me;
    }

    public static void main(String[] args) {
        String s = null;
        try {
            s = getUserinfo("813ffed003a73f1f8f27cd4123e81e38");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
    }
}