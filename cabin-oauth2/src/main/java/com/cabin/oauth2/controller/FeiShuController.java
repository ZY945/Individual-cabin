package com.cabin.oauth2.controller;


import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.bindAccount.OauthBind;
import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuClient;
import com.cabin.oauth2.empty.feishu.FeiShuUser;
import com.cabin.oauth2.empty.feishu.FeiShuVo;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.oauth2.service.FeiShuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/6 10:14
 */
@RestController()
@RequestMapping("/feishu")
@EnableConfigurationProperties(FeiShuClient.class)
public class FeiShuController {


    @Autowired
    private FeiShuService feiShuService;

    @Autowired
    private OauthBindRepository oauthRepository;

    @Autowired
    private EmailLoginService emailLoginService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 不能是void
     *
     * @return
     */
    @GetMapping("/code")
    public String toRequestWithCode() {
        return feiShuService.getCodeUrl();
    }


    @GetMapping("/access_token")
    public Result<FeiShuVo> getAccessToken(@RequestParam String code) {
        FeiShuVo feiShuVo = new FeiShuVo();
        FeiShuAccessToken FeiShuToken = feiShuService.getToken(code);
        //异步去保存用户信息,然后绑定是一个单独的接口
        FeiShuUser exit = feiShuService.saveUser(FeiShuToken);
        String token = null;

        if (exit != null) {
            OauthBind oauthBind = oauthRepository.getOauthByFeiShuOpenId(exit.getOpenId());
            if (oauthBind != null) {
                //表示绑定过，业务登录即可
                //"user:token:" + token
                //TODO 用户token
                User user = userRepository.getUserById(oauthBind.getUserId());
                token = emailLoginService.getAndSaveToken(user.getEmail());
            } else {
                //TODO 临时游客token
                token = emailLoginService.getAndSaveGuestToken(exit.getId());
            }
            feiShuVo.setUserId(exit.getId());
        }
        feiShuVo.setToken(token);
        return token == null ? Result.fail(feiShuVo, "未绑定账户") : Result.success(feiShuVo, "登录成功");
    }
}
