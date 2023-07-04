package com.cabin.empty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * @author 伍六七
 * @date 2023/7/4 9:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBo {
    private String bucket;
    private String measurement;
    private OffsetDateTime start;
    private OffsetDateTime stop;
    private Integer limit;
}
