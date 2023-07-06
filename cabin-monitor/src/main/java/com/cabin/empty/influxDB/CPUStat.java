package com.cabin.empty.influxDB;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author 伍六七
 * @date 2023/6/30 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "CPUStat")//influxDB注解
public class CPUStat {
    @Column
    private String cpuName;
    @Column
    private Long user;
    @Column
    private Long nice;
    @Column
    private Long system;
    @Column
    private Long idle;
    @Column
    private Long iowait;
    @Column
    private Long irq;
    @Column
    private Long softirq;
    @Column
    private Long steal;
    @Column
    private Long guest;
    @Column
    private Long guest_nice;
    @Column(timestamp = true)
    Instant time;

}
