package com.cabin.empty.influxDB;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author 伍六七
 * @date 2023/6/30 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "Stat")//influxDB注解
public class Stat {
    /**
     * TODO 中断(太长了,有时间在详细分析)
     */
    @Column
    private String intr;
    @Column
    private long ctxt;
    @Column
    private long btime;
    @Column
    private long processes;
    @Column
    private long procs_running;
    @Column
    private String procs_blocked;
    /**
     * 软中断
     */
    @Column
    private long softirq;
    @Column(timestamp = true)
    Instant time;

}
