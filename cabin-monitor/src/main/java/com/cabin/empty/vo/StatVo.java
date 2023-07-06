package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/30 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatVo {

    private List<CPUStatVo> cpus;
    /**
     * TODO 中断(太长了,有时间在详细分析)
     */
    private String intr;
    private Long ctxt;
    private Long btime;
    private Long processes;
    private Long procs_running;
    private String procs_blocked;
    /**
     * 软中断
     */
    private Long softirq;
    Instant time;

    public void setProperty(String key, String value) {
        switch (key) {
            case "intr" -> {
                this.intr = value;
            }
            case "ctxt" -> {
                this.ctxt = Long.valueOf(value);
            }
            case "btime" -> {
                this.btime = Long.valueOf(value);
            }
            case "processes" -> {
                this.processes = Long.valueOf(value);
            }
            case "procs_running" -> {
                this.procs_running = Long.valueOf(value);
            }
            case "procs_blocked" -> {
                this.procs_blocked = value;
            }
            case "softirq" -> {
                this.softirq = Long.valueOf(value);
            }
        }
    }
}
