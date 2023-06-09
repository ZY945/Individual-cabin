package com.cabin.oauth2.empty.bindAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author 伍六七
 * @date 2023/6/9 16:07
 */
@Getter
public class BindAccount {

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("code")
    private String code;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("passWord")
    private String passWord;

    @JsonProperty("feiShuId")
    private Long feiShuId;

    @JsonProperty("gitHubId")
    private Long gitHubId;
    @JsonProperty("giteeId")
    private Long giteeId;
}
