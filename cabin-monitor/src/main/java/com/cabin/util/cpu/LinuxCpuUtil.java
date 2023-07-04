package com.cabin.util.cpu;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * @author 伍六七
 * @date 2023/6/30 11:12
 */
@Component
@ToString
@Getter
public class LinuxCpuUtil {
    private static final String path = "F:\\study\\code\\java\\Individual-cabin\\docs\\moitor\\linux-proc\\cpuinfo";
    //    private final String path = "/proc/stat";
    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    private int availableProcessors;

    /**
     * 逻辑处理器(线程)
     */
    private int cpuNum;

    /**
     * 该进程占用CPU(单位:%)
     */
    private double processCpuLoad;
    /**
     * 系统CPU占用率(单位:%)
     */
    private double systemCpuLoad;

    private void init() {

        // 获取可用处理器数量
        availableProcessors = osBean.getAvailableProcessors();
        System.out.println("可用处理器数量: " + availableProcessors);

        // 获取系统负载平均值
        double systemLoadAverage = osBean.getSystemLoadAverage();
        System.out.println("系统负载平均值: " + systemLoadAverage);

    }

}
