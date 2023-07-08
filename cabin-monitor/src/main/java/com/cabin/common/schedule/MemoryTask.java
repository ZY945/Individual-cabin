package com.cabin.common.schedule;

import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.influxDB.Memory;
import com.cabin.empty.vo.MemoryVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.util.procUtil.ProcUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author 伍六七
 * @date 2023/7/5 14:32
 */
@Component
@DependsOn("ApplicationContextUtil")
public class MemoryTask implements Runnable {

    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final ProcUtil procUtil = (ProcUtil) ApplicationContextUtil.getBean("procUtil");

    public void writeMemory() {
        MemoryVo memoryVo = procUtil.readMemoryVoInfo();
        Memory memory = new Memory();
        BeanUtils.copyProperties(memoryVo, memory, Memory.class);
        Instant now = DateUtil.getNowInstant();
        memory.setTime(now);
        template.writeObject(WritePrecision.S, memory);
    }

    @Override
    public void run() {
        writeMemory();
    }
}
