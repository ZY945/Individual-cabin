package com.cabin.controller;

import com.cabin.empty.vo.MemoryVo;
import com.cabin.empty.vo.ProcessorVo;
import com.cabin.empty.vo.StatVo;
import com.cabin.empty.vo.UptimeVo;
import com.cabin.service.QueryService;
import com.cabin.util.procUtil.ProcUtil;
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

    @Autowired
    private QueryService queryService;

    @GetMapping("/processor")
    public Result<List<ProcessorVo>> get() {
        //执行一次即可,不需要去保存
//        List<ProcessorVo> processors = queryService.getOneSecondProcessorVo();
        List<ProcessorVo> processorVos = procUtil.readProcessorVoInfo();
        return Result.success(processorVos, "获得processor数据");
    }

    @GetMapping("/memory")
    public Result<MemoryVo> getMemory() {
        MemoryVo memory = queryService.getOneSecondMemoryVo();
        return Result.success(memory, "获得memory数据");
    }

    @GetMapping("/stat")
    public Result<StatVo> getStat() {
        StatVo statVo = queryService.getOneSecondStatVo();
        return Result.success(statVo, "获得stat数据");
    }

    @GetMapping("/uptime")
    public Result<UptimeVo> getUptime() {
        UptimeVo uptime = queryService.getOneSecondUptimeVo();
        return Result.success(uptime, "获得uptime数据");
    }
}
