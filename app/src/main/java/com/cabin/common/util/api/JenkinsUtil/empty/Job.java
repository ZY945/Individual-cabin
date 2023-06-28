package com.cabin.common.util.api.JenkinsUtil.empty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/28 13:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {
    @JsonProperty("_class")
    private String _class;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("color")
    private String color;
}
