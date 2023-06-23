package com.cabin.oauth2.empty.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GitHuBVo {
    private String token;
    private Long userId;
}