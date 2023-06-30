package com.cabin.monitor.util.os;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * @author 伍六七
 * @date 2023/6/30 11:13
 */
@Component
@ToString
@Getter
public class OSUtil {
    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    private String osName;

    private String osVersion;

    private String osArch;

    private double systemLoadAverage;

    private void init() {
        // 获取操作系统名称
        osName = osBean.getName();
        System.out.println("操作系统名称: " + osName);

        // 获取操作系统版本
        osVersion = osBean.getVersion();
        System.out.println("操作系统版本: " + osVersion);

        // 获取操作系统架构
        osArch = osBean.getArch();
        System.out.println("操作系统架构: " + osArch);

        // 获取系统负载平均值
        systemLoadAverage = osBean.getSystemLoadAverage();
        System.out.println("系统负载平均值: " + systemLoadAverage);

    }

    public static void main(String[] args) {
        OSUtil osUtil = new OSUtil();
        osUtil.init();
    }

}
