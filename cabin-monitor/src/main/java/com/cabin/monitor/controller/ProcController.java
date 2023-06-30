package com.cabin.monitor.controller;

import com.cabin.monitor.empty.Memory;
import com.cabin.monitor.empty.Processor;
import com.cabin.monitor.empty.Stat;
import com.cabin.monitor.empty.Uptime;
import com.cabin.monitor.util.procUtil.ProcUtil;
import com.cabin.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/30 14:07
 */
@RestController
@RequestMapping("/proc")
public class ProcController {
    @Autowired
    private ProcUtil procUtil;

    @GetMapping("/processor")
    public Result<List<Processor>> get() {
        List<Processor> processors = procUtil.readProcessorInfo();
        return Result.success(processors, "获得processor数据");
    }

    @GetMapping("/memory")
    public Result<Memory> getMemory() {
        Memory memory = procUtil.readMemoryInfo();
        return Result.success(memory, "获得memory数据");
    }

    @GetMapping("/stat")
    public Result<Stat> getStat() {
        Stat stat = procUtil.readStatInfo();
        return Result.success(stat, "获得stat数据");
    }

    @GetMapping("/uptime")
    public Result<Uptime> getUptime() {
        Uptime uptime = procUtil.readUptimeInfo();
        return Result.success(uptime, "获得uptime数据");
    }
}
