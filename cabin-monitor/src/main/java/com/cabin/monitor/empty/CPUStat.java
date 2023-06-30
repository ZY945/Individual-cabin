package com.cabin.monitor.empty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/30 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPUStat {
    private String cpuName;
    private long user;
    private long nice;
    private long system;
    private long idle;
    private long iowait;
    private long irq;
    private long softirq;
    private long steal;
    private long guest;
    private long guest_nice;

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
