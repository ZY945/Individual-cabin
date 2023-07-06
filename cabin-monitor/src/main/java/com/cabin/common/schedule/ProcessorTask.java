package com.cabin.common.schedule;

import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.influxDB.Processor;
import com.cabin.empty.vo.ProcessorVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.util.procUtil.ProcUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * @author 伍六七
 * @date 2023/7/5 14:32
 */
@Component
@DependsOn("ApplicationContextUtil")
public class ProcessorTask implements Runnable {

    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final ProcUtil procUtil = (ProcUtil) ApplicationContextUtil.getBean("procUtil");

    public void writeProcessor() {
        List<ProcessorVo> processorVos = procUtil.readProcessorVoInfo();
        Instant now = DateUtil.getNowInstant();
        now.atZone(ZoneId.systemDefault());
        for (int i = 0; i < processorVos.size(); i++) {
            ProcessorVo processorVo = processorVos.get(i);
            Processor processor = new Processor();
            BeanUtils.copyProperties(processorVo, processor, Processor.class);
            // 不要加这个,加上后数据会出问题,导致无法parse,部分数据后加i
            // processor.setTime(now);

            Map<String, Object> stringObjectMap = JacksonUtils.convertToHashMap(processor);
            Point point = Point.measurement("Processor" + (i + 1) + "No")
                    .addFields(stringObjectMap)
                    .time(now, WritePrecision.S);
            template.writePoint(point);
        }
    }

    @Override
    public void run() {
        writeProcessor();
    }
}
