package com.cabin.common.util.api.JenkinsUtil.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/9/1 17:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeInfoVo {
    @JsonProperty("name")
    private String name;
    @JsonProperty("color")
    private String color;

}