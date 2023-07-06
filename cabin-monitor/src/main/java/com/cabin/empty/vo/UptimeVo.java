package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author 伍六七
 * @date 2023/6/30 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UptimeVo {
    private Double totalSeconds;
    private Double idleSeconds;
    Instant time;

    public void setProperty(String key, String value) {
        switch (key) {
            case "totalSeconds" -> {
                this.totalSeconds = Double.valueOf(value);
            }
            case "idleSeconds" -> {
                this.idleSeconds = Double.valueOf(value);
            }
        }
    }
}
