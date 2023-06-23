package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.common.enums.Oauth;
import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.bindAccount.BindAccountVo;
import com.cabin.oauth2.empty.bindAccount.OauthBind;
import com.cabin.oauth2.empty.feishu.FeiShuUser;
import com.cabin.oauth2.empty.github.GithubUser;
import com.cabin.oauth2.repository.FeiShuUserRepository;
import com.cabin.oauth2.repository.GitHubUserRepository;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.AccountLoginService;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.oauth2.service.OauthBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 伍六七
 * @date 2023/6/9 14:25
 */
@Service
public class OauthBindServiceImpl implements OauthBindService {

    @Autowired
    private OauthBindRepository oauthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeiShuUserRepository feiShuUserRepository;
    @Autowired
    private GitHubUserRepository gitHubUserRepository;
    @Autowired
    private EmailLoginService emailLoginService;
    @Autowired
    private AccountLoginService accountLoginService;

    @Override
    public BindAccountVo bindFeiShuByAccount(String userName, String passWord, Long id) {
        BindAccountVo vo = new BindAccountVo();
        String token = accountLoginService.login(userName, passWord);
        if (token == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据email获取用户
        //加密密码
        String passWordEncode = accountLoginService.encryptedPassword(passWord);
        User user = userRepository.getUserByUserNameAndPassWord(userName, passWordEncode);
        //根据Id获取飞书用户
        FeiShuUser feiShuUser = feiShuUserRepository.getFeiShuUserInfoById(id);
        if (feiShuUser == null) {
            vo.setOauth(Oauth.ISNOTFEISHUUSER);
            return vo;
        }
        String openId = feiShuUser.getOpenId();
        vo = bindFeiShu(user.getId(), openId);
        vo.setToken(token);
        return vo;
    }

    @Override
    public BindAccountVo bindFeiShuByEmail(String email, String code, Long id) {
        BindAccountVo vo = new BindAccountVo();
        String token = emailLoginService.login(email, code);
        vo.setToken(token);
        if (token == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据email获取用户
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据Id获取飞书用户
        FeiShuUser feiShuUser = feiShuUserRepository.getFeiShuUserInfoById(id);
        if (feiShuUser == null) {
            vo.setOauth(Oauth.ISNOTFEISHUUSER);
            return vo;
        }
        String openId = feiShuUser.getOpenId();
        vo = bindFeiShu(user.getId(), openId);
        return vo;
    }

    @Override
    public BindAccountVo bindGitHubByAccount(String userName, String passWord, Long id) {
        BindAccountVo vo = new BindAccountVo();
        String token = accountLoginService.login(userName, passWord);
        if (token == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据email获取用户
        //加密密码
        String passWordEncode = accountLoginService.encryptedPassword(passWord);
        User user = userRepository.getUserByUserNameAndPassWord(userName, passWordEncode);
        //根据Id获取github用户
        GithubUser gitHubUser = gitHubUserRepository.getGithubUserById(id);
        if (gitHubUser == null) {
            vo.setOauth(Oauth.ISNOTFEISHUUSER);
            return vo;
        }
        Long githubUserId = gitHubUser.getGitHubUserId();
        vo = bindGitHub(user.getId(), githubUserId);
        vo.setToken(token);
        return vo;
    }

    @Override
    public BindAccountVo bindGitHubByEmail(String email, String code, Long id) {
        BindAccountVo vo = new BindAccountVo();
        String token = emailLoginService.login(email, code);
        vo.setToken(token);
        if (token == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据email获取用户
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据Id获取github用户
        GithubUser gitHubUser = gitHubUserRepository.getGithubUserById(id);
        if (gitHubUser == null) {
            vo.setOauth(Oauth.ISNOTFEISHUUSER);
            return vo;
        }
        Long githubUserId = gitHubUser.getGitHubUserId();
        vo = bindGitHub(user.getId(), githubUserId);
        return vo;
    }


    private BindAccountVo bindFeiShu(Long userId, String openId) {
        BindAccountVo vo = new BindAccountVo();

        OauthBind oauthBind = oauthRepository.getOauthByFeiShuOpenId(openId);
        //第一次绑定
        if (oauthBind == null) {
            OauthBind newBind = new OauthBind();

            newBind.setUserId(userId);
            newBind.setFeiShuOpenId(openId);
            oauthRepository.save(newBind);
            vo.setOauth(Oauth.NEWBIND);
            return vo;
        }
        //之前绑定过，并且github也绑定过
        if (oauthBind.getFeiShuOpenId() != null) {
            //已绑定
            vo.setOauth(Oauth.OLDBIND);
            return vo;
        } else {
            //之前绑定过，但github没绑定过
            oauthBind.setFeiShuOpenId(openId);
            oauthRepository.save(oauthBind);
            vo.setOauth(Oauth.NEWBIND);
            return vo;
        }
    }


    private BindAccountVo bindGitHub(Long userId, Long githubUserId) {
        BindAccountVo vo = new BindAccountVo();

        OauthBind oauthBind = oauthRepository.getOauthByUserId(userId);
        //第一次绑定
        if (oauthBind == null) {
            OauthBind newBind = new OauthBind();

            newBind.setUserId(userId);
            newBind.setGitHubUserId(githubUserId);
            oauthRepository.save(newBind);
            vo.setOauth(Oauth.NEWBIND);
            return vo;
        }
        //之前绑定过，并且github也绑定过
        if (oauthBind.getGitHubUserId() != null) {
            //已绑定
            vo.setOauth(Oauth.OLDBIND);
            return vo;
        } else {
            //之前绑定过，但github没绑定过
            oauthBind.setGitHubUserId(githubUserId);
            oauthRepository.save(oauthBind);
            vo.setOauth(Oauth.NEWBIND);
            return vo;
        }
    }

}
