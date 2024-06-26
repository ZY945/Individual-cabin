package com.cabin.util.procUtil;

import com.cabin.empty.vo.CPUStatVo;
import com.cabin.empty.vo.MemoryVo;
import com.cabin.empty.vo.ProcessorVo;
import com.cabin.empty.vo.StatVo;
import com.cabin.empty.vo.UptimeVo;
import com.cabin.util.shell.ShellUtil;
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

    public List<ProcessorVo> readProcessorVoInfo() {
        List<ProcessorVo> processors = new ArrayList<>();
        try (BufferedReader reader = shellUtil.cat(CPU_INFO)) {
            // 本地读取进行测试
            // try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\study\\code\\java\\Individual-cabin\\docs\\moitor\\linux-proc\\processorinfo")));) {
            String line;
            ProcessorVo processor = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(":")) {
                    if (line.startsWith("processor")) {
                        if (processor != null) {
                            processors.add(processor);
                        }
                        processor = new ProcessorVo();
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

    public MemoryVo readMemoryVoInfo() {
        MemoryVo memory = new MemoryVo();
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

    public UptimeVo readUptimeVoInfo() {
        UptimeVo uptime = new UptimeVo();
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

    public StatVo readStatVoInfo() {
        StatVo stat = new StatVo();
        try (BufferedReader reader = shellUtil.cat(STAT)) {
            String line;
            ArrayList<CPUStatVo> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String key = parts[0].trim();
                if (key.startsWith("cpu")) {
                    CPUStatVo cpuStatVo = new CPUStatVo();
                    cpuStatVo.setCpuName(key);
                    for (int i = 1; i < parts.length; i++) {
                        cpuStatVo.setProperty(i, parts[i].trim());
                    }
                    list.add(cpuStatVo);
                    continue;
                }
                if (key.equals("intr") || key.equals("softirq")) {
                    continue;
                }
                String value = null;
                if (parts.length == 2) {
                    value = parts[1].trim();
                }
                stat.setProperty(key, value);
            }
            stat.setCpus(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stat;
    }

}
