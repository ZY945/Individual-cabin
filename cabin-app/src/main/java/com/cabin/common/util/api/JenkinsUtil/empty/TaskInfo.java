package com.cabin.common.util.api.JenkinsUtil.empty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/9/1 17:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskInfo {
    @JsonProperty("fullName")
    private String taskName;

    @JsonProperty("description")
    private String taskDescription;

    @JsonProperty("nextBuildNumber")
    private String nextBuildNumber;

    @JsonProperty("buildable")
    private String buildable;
    /**
     * 需要手动设置healthReport.description
     */
    private String buildDescription;
}