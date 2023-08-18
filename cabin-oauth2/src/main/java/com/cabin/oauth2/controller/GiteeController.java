package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.bindAccount.OauthBind;
import com.cabin.oauth2.empty.gitee.GiteeAccessToken;
import com.cabin.oauth2.empty.gitee.GiteeClient;
import com.cabin.oauth2.empty.gitee.GiteeUser;
import com.cabin.oauth2.empty.gitee.GiteeVo;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.oauth2.service.GiteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/24 23:07
 */
@RestController()
@RequestMapping("/gitee")
@EnableConfigurationProperties(GiteeClient.class)
public class GiteeController {
    @Autowired
    private OauthBindRepository oauthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailLoginService emailLoginService;

    @Autowired
    private GiteeService giteeService;

    @GetMapping("/code")
    public String toRequestWithCode() {
        return giteeService.getCodeUrl();
    }

    @GetMapping("/access_token")
    public Result<GiteeVo> authorization_code(String code) {
        GiteeVo giteeVo = new GiteeVo();
        GiteeAccessToken giteeAccessToken = giteeService.getToken(code);
        //异步去保存用户信息,然后绑定是一个单独的接口
        GiteeUser exit = giteeService.saveUser(giteeAccessToken);
        String token = null;

        if (exit != null) {

            OauthBind oauthBind = oauthRepository.getOauthByGiteeUserId(exit.getGiteeUserId());
            if (oauthBind != null) {
                //表示绑定过，业务登录即可
                //TODO jwt生成
                User user = userRepository.getUserById(oauthBind.getUserId());
                token = emailLoginService.getAndSaveToken(user.getEmail());
            } else {
                //TODO 临时游客token
                token = emailLoginService.getAndSaveGuestToken(exit.getId());
            }
            giteeVo.setUserId(exit.getId());
        }
        giteeVo.setToken(token);
        return token == null ? Result.fail(giteeVo, "未绑定账户") : Result.success(giteeVo, "登录成功");
    }
}
