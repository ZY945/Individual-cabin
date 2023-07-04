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
    private long user;
    @Column
    private long nice;
    @Column
    private long system;
    @Column
    private long idle;
    @Column
    private long iowait;
    @Column
    private long irq;
    @Column
    private long softirq;
    @Column
    private long steal;
    @Column
    private long guest;
    @Column
    private long guest_nice;
    @Column(timestamp = true)
    Instant time;

    public void setProperty(int index, long value) {
        switch (index) {
            case 1 -> this.user = value;
            case 2 -> this.nice = value;
            case 3 -> this.system = value;
            case 4 -> this.idle = value;
            case 5 -> this.iowait = value;
            case 6 -> this.irq = value;
            case 7 -> this.softirq = value;
            case 8 -> this.steal = value;
            case 9 -> this.guest = value;
            case 10 -> this.guest_nice = value;
        }
    }
}
