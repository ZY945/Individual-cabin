package com.cabin.oauth2.empty.gitee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/24 23:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiteeVo {
    private String token;
    private Long userId;
}
