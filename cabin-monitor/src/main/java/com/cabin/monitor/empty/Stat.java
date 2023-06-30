package com.cabin.monitor.empty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/30 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stat {
    private List<CPUStat> cpus;
    /**
     * TODO 中断(太长了,有时间在详细分析)
     */
    private String intr;
    private long ctxt;
    private long btime;
    private long processes;
    private long procs_running;
    private String procs_blocked;
    /**
     * 软中断
     */
    private long softirq;

    public void setProperty(String key, long value) {
        switch (key) {
            case "intr" -> {
                this.intr = String.valueOf(value);
            }
            case "ctxt" -> {
                this.ctxt = value;
            }
            case "btime" -> {
                this.btime = value;
            }
            case "processes" -> {
                this.processes = value;
            }
            case "procs_running" -> {
                this.procs_running = value;
            }
            case "procs_blocked" -> {
                this.procs_blocked = String.valueOf(value);
            }
            case "softirq" -> {
                this.softirq = value;
            }
        }
    }
}
