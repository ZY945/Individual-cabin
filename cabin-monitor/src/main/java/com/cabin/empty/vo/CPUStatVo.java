package com.cabin.empty.vo;

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
public class CPUStatVo {

    private String cpuName;
    private Long user;
    private Long nice;
    private Long system;
    private Long idle;
    private Long iowait;
    private Long irq;
    private Long softirq;
    private Long steal;
    private Long guest;
    private Long guest_nice;
    Instant time;

    public Long getTotalCpuTime() {
        return this.user +
                this.nice +
                this.system +
                this.idle +
                this.iowait +
                this.irq +
                this.softirq +
                this.steal +
                this.guest;
    }

    public Long getIdleCpuTime() {
        return this.idle;
    }

    public void setProperty(int index, String value) {
        switch (index) {
            case 1 -> this.user = Long.valueOf(value);
            case 2 -> this.nice = Long.valueOf(value);
            case 3 -> this.system = Long.valueOf(value);
            case 4 -> this.idle = Long.valueOf(value);
            case 5 -> this.iowait = Long.valueOf(value);
            case 6 -> this.irq = Long.valueOf(value);
            case 7 -> this.softirq = Long.valueOf(value);
            case 8 -> this.steal = Long.valueOf(value);
            case 9 -> this.guest = Long.valueOf(value);
            case 10 -> this.guest_nice = Long.valueOf(value);
        }
    }

    public void setProperty(String key, String value) {
        switch (key) {
            case "cpuName" -> {
                this.cpuName = value;
            }
            case "user" -> {
                this.user = Long.valueOf(value);
            }
            case "nice" -> {
                this.nice = Long.valueOf(value);
            }
            case "system" -> {
                this.system = Long.valueOf(value);
            }
            case "idle" -> {
                this.idle = Long.valueOf(value);
            }
            case "iowait" -> {
                this.iowait = Long.valueOf(value);
            }
            case "irq" -> {
                this.irq = Long.valueOf(value);
            }
            case "softirq" -> {
                this.softirq = Long.valueOf(value);
            }
            case "steal" -> {
                this.steal = Long.valueOf(value);
            }
            case "guest" -> {
                this.guest = Long.valueOf(value);
            }
            case "guest_nice" -> {
                this.guest_nice = Long.valueOf(value);
            }
        }
    }

}
