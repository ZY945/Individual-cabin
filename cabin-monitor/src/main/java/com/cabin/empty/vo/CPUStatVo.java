package com.cabin.empty.vo;

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

    public void setProperty(int index, Long value) {
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
    public void setProperty(String key, Object value) {
        switch (key) {
            case "cpuName" -> {
                this.cpuName = (String) value;
            }
            case "user" -> {
                this.user = (Long) value;
            }
            case "nice" -> {
                this.nice = (Long) value;
            }
            case "system" -> {
                this.system = (Long) value;
            }
            case "idle" -> {
                this.idle = (Long) value;
            }
            case "iowait" -> {
                this.iowait = (Long) value;
            }
            case "irq" -> {
                this.irq = (Long) value;
            }
            case "softirq" -> {
                this.softirq = (Long) value;
            }
            case "steal" -> {
                this.steal = (Long) value;
            }
            case "guest" -> {
                this.guest = (Long) value;
            }
            case "guest_nice" -> {
                this.guest_nice = (Long) value;
            }
        }
    }

}
