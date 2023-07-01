package com.cabin.monitor.util.procUtil;

import com.cabin.monitor.empty.*;
import com.cabin.monitor.util.shell.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/30 13:49
 */
@Component
public class ProcUtil {
    private static final String CPU_INFO = "/proc/cpuinfo";
    private static final String UP_TIME = "/proc/uptime";
    private static final String STAT = "/proc/stat";
    private static final String MEM_INFO = "/proc/meminfo";
    @Autowired
    private ShellUtil shellUtil;

    public List<Processor> readProcessorInfo() {
        List<Processor> processors = new ArrayList<>();
        try (BufferedReader reader = shellUtil.cat(CPU_INFO)) {
            String line;
            Processor processor = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(":")) {
                    if (line.startsWith("processor")) {
                        if (processor != null) {
                            processors.add(processor);
                        }
                        processor = new Processor();
                    }
                    String[] parts = line.split(":");
                    String key = parts[0].trim();
                    String value = null;
                    if (parts.length == 2) {
                        value = parts[1].trim();
                    }
                    if (processor != null) {
                        processor.setProperty(key, value);
                    }
                }
            }
            if (processor != null) {
                processors.add(processor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processors;
    }

    public Memory readMemoryInfo() {
        Memory memory = new Memory();
        try (BufferedReader reader = shellUtil.cat(MEM_INFO)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(":")) {
                    String[] parts = line.split(":");
                    String key = parts[0].trim();
                    String value = null;
                    if (parts.length == 2) {
                        value = parts[1].trim();
                    }
                    memory.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memory;
    }

    public Uptime readUptimeInfo() {
        Uptime uptime = new Uptime();
        try (BufferedReader reader = shellUtil.cat(UP_TIME)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                double totalSeconds = Double.parseDouble(parts[0]);
                double idleSeconds = Double.parseDouble(parts[1]);
                uptime.setTotalSeconds(totalSeconds);
                uptime.setIdleSeconds(idleSeconds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uptime;
    }

    public Stat readStatInfo() {
        Stat stat = new Stat();
        try (BufferedReader reader = shellUtil.cat(STAT)) {
            String line;
            ArrayList<CPUStat> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String key = parts[0].trim();
                if (key.startsWith("cpu")) {
                    CPUStat cpuStat = new CPUStat();
                    cpuStat.setCpuName(key);
                    for (int i = 1; i < parts.length; i++) {
                        cpuStat.setProperty(i, Long.parseLong(parts[i].trim()));
                    }
                    list.add(cpuStat);
                    continue;
                }
                if (key.equals("intr") || key.equals("softirq")) {
                    continue;
                }
                String value = null;
                if (parts.length == 2) {
                    value = parts[1].trim();
                }
                stat.setProperty(key, Long.parseLong(value));
            }
            stat.setCpus(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stat;
    }

}
