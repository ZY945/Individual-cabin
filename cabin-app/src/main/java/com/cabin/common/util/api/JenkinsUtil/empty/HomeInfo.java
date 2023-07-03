package com.cabin.common.util.api.JenkinsUtil.empty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/28 13:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeInfo {
    @JsonProperty("nodeName")
    private String nodeName;
    @JsonProperty("nodeDescription")
    private String nodeDescription;
    @JsonProperty("jobs")
    List<Job> jobs;
}
