package com.cabin.common.schedule;

import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.influxDB.CPUStat;
import com.cabin.empty.influxDB.Stat;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.empty.vo.StatVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.util.procUtil.ProcUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * @date 2023/7/3 16:41
 */
@Component
@DependsOn("ApplicationContextUtil")
public class StatTask implements Runnable {

    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final ProcUtil procUtil = (ProcUtil) ApplicationContextUtil.getBean("procUtil");

    public void writeStat() throws JsonProcessingException {
        //测试
//        JSONObject s = HttpUtil.getDataJsonObject(new JSONObject(), "http://127.0.0.1:9003/proc/stat");
//        StatVo statVo = JacksonUtils.convertValue(s, StatVo.class);
        StatVo statVo = procUtil.readStatVoInfo();
        List<CPUStatVo> cpus = statVo.getCpus();
        Stat stat = new Stat();
        BeanUtils.copyProperties(statVo, stat, Stat.class);
        Instant now = DateUtil.getNowInstant();
        now.atZone(ZoneId.systemDefault());
        stat.setTime(now);
        template.writeObject(WritePrecision.S, stat);
        for (int i = 0; i < cpus.size(); i++) {
            CPUStatVo cpuStatVo = cpus.get(i);
            CPUStat cpuStat = new CPUStat();
            BeanUtils.copyProperties(cpuStatVo, cpuStat, CPUStat.class);
            Map<String, Object> stringObjectMap = JacksonUtils.convertToHashMap(cpuStat);
            Point point = Point.measurement("CPU" + i + "Stat")
                    .addFields(stringObjectMap)
                    .time(now, WritePrecision.S);
            template.writePoint(point);
        }
    }

    @Override
    public void run() {
        try {
            writeStat();
//            System.out.println("一次定时任务");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
