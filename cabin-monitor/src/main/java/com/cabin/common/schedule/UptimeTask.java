package com.cabin.common.schedule;

import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.influxDB.Uptime;
import com.cabin.empty.vo.UptimeVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.util.procUtil.ProcUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;

/**
 * @author 伍六七
 * @date 2023/7/5 14:32
 */
@Component
@DependsOn("ApplicationContextUtil")
public class UptimeTask implements Runnable {

    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final ProcUtil procUtil = (ProcUtil) ApplicationContextUtil.getBean("procUtil");

    public void writeUptime() {
        UptimeVo uptimeVo = procUtil.readUptimeVoInfo();
        Uptime uptime = new Uptime();
        BeanUtils.copyProperties(uptimeVo, uptime, Uptime.class);
        Instant now = DateUtil.getNowInstant();
        now.atZone(ZoneId.systemDefault());
        uptime.setTime(now);
        template.writeObject(WritePrecision.S, uptime);
    }

    @Override
    public void run() {
        writeUptime();
    }
}
