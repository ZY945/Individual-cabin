package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.bindAccount.OauthBind;
import com.cabin.oauth2.empty.github.GitHuBVo;
import com.cabin.oauth2.empty.github.GitHubAccessToken;
import com.cabin.oauth2.empty.github.GitHubClient;
import com.cabin.oauth2.empty.github.GithubUser;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.oauth2.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/23 17:35
 */
@RestController()
@RequestMapping("/github")
@EnableConfigurationProperties(GitHubClient.class)
public class GitHubController {

    @Autowired
    private OauthBindRepository oauthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailLoginService emailLoginService;

    @Autowired
    private GitHubService gitHubLoginService;

    @GetMapping("/code")
    public String toRequestWithCode() {
        return gitHubLoginService.getCodeUrl();
    }

    @GetMapping("/access_token")
    public Result<GitHuBVo> authorization_code(String code) {
        GitHuBVo gitHubvo = new GitHuBVo();
        GitHubAccessToken GitHubAccessToken = gitHubLoginService.getToken(code);
        //异步去保存用户信息,然后绑定是一个单独的接口
        GithubUser exit = gitHubLoginService.saveUser(GitHubAccessToken);
        String token = null;

        if (exit != null) {

            OauthBind oauthBind = oauthRepository.getOauthByGitHubUserId(exit.getGitHubUserId());
            if (oauthBind != null) {
                //表示绑定过，业务登录即可
                //TODO jwt生成
                User user = userRepository.getUserById(oauthBind.getUserId());
                token = emailLoginService.getAndSaveToken(user.getEmail());
            }
            gitHubvo.setUserId(exit.getId());
        }
        gitHubvo.setToken(token);
        return token == null ? Result.fail(gitHubvo, "未绑定账户") : Result.success(gitHubvo, "登录成功");
    }
}
