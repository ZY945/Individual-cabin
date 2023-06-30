package com.cabin.monitor.util.diskMemory;

/**
 * @author 伍六七
 * @date 2023/6/30 11:25
 * 磁盘
 */
public class DisMemoryUtil {
    /**
     * 总物理内存(单位:Bytes)
     */
    private Long totalPhysicalMemorySize;
    /**
     * 空闲物理内存(单位:Bytes)
     */
    private Long freePhysicalMemorySize;
    /**
     * 系统总内存(单位:GB)
     */
    private double totalMemory;
    /**
     * 系统剩余内存(单位:GB)
     */
    private double freeMemory;
    /**
     * 内存占用率(单位:%)
     */
    private double memoryUseRatio;
}
