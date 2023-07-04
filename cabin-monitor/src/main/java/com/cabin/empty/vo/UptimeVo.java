package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/30 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UptimeVo {
    private double totalSeconds;
    private double idleSeconds;
}
