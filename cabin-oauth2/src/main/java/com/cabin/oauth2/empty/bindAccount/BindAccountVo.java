package com.cabin.oauth2.empty.bindAccount;

import com.cabin.oauth2.common.enums.Oauth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/20 22:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BindAccountVo {
    private Oauth oauth;
    private String token;
}
