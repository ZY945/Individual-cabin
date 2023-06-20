package com.cabin.oauth2.empty.feishu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/20 22:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeiShuVo {
    private String token;
    private Long userId;
}
