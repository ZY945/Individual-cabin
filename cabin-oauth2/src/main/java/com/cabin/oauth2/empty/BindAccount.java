package com.cabin.oauth2.empty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author 伍六七
 * @date 2023/6/9 16:07
 */
@Getter
public class BindAccount {

    @JsonProperty("emailToken")
    private String emailToken;

    @JsonProperty("feiShuToken")
    private String feiShuToken;

}
