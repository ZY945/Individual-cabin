package com.cabin.empty.influxDB;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
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
@Measurement(name = "Uptime")//influxDB注解
public class Uptime {
    @Column
    private Double totalSeconds;
    @Column
    private Double idleSeconds;
    @Column(timestamp = true)
    Instant time;
}
