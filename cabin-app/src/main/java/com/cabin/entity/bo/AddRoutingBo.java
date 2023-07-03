package com.cabin.entity.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/29 9:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoutingBo {
    private String title;
    @JsonProperty(value = "prepend-icon")
    private String prependIcon;
    private String path;
}
