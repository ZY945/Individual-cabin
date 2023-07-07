package com.cabin.service;

import com.cabin.empty.vo.*;
import com.cabin.influxDB.empty.bo.query.InfluxBO;
import com.cabin.influxDB.empty.bo.query.QueryType;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.utils.dateUtil.DateUtil;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/4 13:37
 */
@Service
public class QueryService {
    @Autowired
    private InfluxDBTemplate influxDBTemplate;

    public List<ProcessorVo> getOneSecondProcessorVo() {
        String bucket = "bucket";
        String[] cpuMeasurement = new String[]{"Processor1", "Processor2", "Processor3"};
        return getOneSecondProcessorBefore(bucket, cpuMeasurement, 0L);
    }

    public MemoryVo getOneSecondMemoryVo() {
        String bucket = "bucket";
        String measurement = "Memory";
        Instant stop = DateUtil.getNowInstant();
        Instant start = stop.minus(Duration.ofSeconds(1));
        Flux statFlux = Flux.from(bucket)
                .range(start, stop)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));
        InfluxBO bo = new InfluxBO();
        bo.setFlux(statFlux);
        List<FluxTable> query = influxDBTemplate.query(QueryType.Flux, bo);
        MemoryVo memoryVo = new MemoryVo();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                if (memoryVo.getTime() == null) {
                    Object key = fluxRecord.getValueByKey("_time");
                    memoryVo.setTime((Instant) key);
                }
                Object key = fluxRecord.getValueByKey("_field");
                Object value = fluxRecord.getValueByKey("_value");
                if (key == null || value == null) {
                    continue;
                }
                memoryVo.setProperty((String) key, value.toString());
            }
        }
        return memoryVo;
    }

    public UptimeVo getOneSecondUptimeVo() {
        String bucket = "bucket";
        String measurement = "Uptime";
        Instant stop = DateUtil.getNowInstant();
        Instant start = stop.minus(Duration.ofSeconds(1));
        Flux statFlux = Flux.from(bucket)
                .range(start, stop)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));
        InfluxBO bo = new InfluxBO();
        bo.setFlux(statFlux);
        List<FluxTable> query = influxDBTemplate.query(QueryType.Flux, bo);
        UptimeVo uptimeVo = new UptimeVo();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                if (uptimeVo.getTime() == null) {
                    Object key = fluxRecord.getValueByKey("_time");
                    uptimeVo.setTime((Instant) key);
                }
                Object key = fluxRecord.getValueByKey("_field");
                Object value = fluxRecord.getValueByKey("_value");
                if (key == null || value == null) {
                    continue;
                }
                uptimeVo.setProperty((String) key, value.toString());
            }
        }
        return uptimeVo;
    }

    /**
     * 获取前一秒stat信息
     *
     * @return
     */
    public StatVo getOneSecondStatVo() {
        String bucket = "bucket";
        String measurement = "Stat";
        String[] cpuMeasurement = new String[]{"CPU1Stat", "CPU2Stat", "CPU3Stat"};
        StatVo oneSecondStat = getOneSecondStatBefore(bucket, measurement, 0L);
        List<CPUStatVo> oneSecondCpu = getOneSecondCpuBefore(bucket, cpuMeasurement, 0L);
        StatVo statVo = new StatVo();
        BeanUtils.copyProperties(oneSecondStat, statVo);
        statVo.setCpus(oneSecondCpu);
        return statVo;
    }

    /**
     * 获取stat信息
     *
     * @return
     */
    public StatVo getOneSecondStatVoBefore() {
        String bucket = "bucket";
        String measurement = "Stat";
        String[] cpuMeasurement = new String[]{"CPU1Stat", "CPU2Stat", "CPU3Stat"};
        StatVo oneSecondStat = getOneSecondStatBefore(bucket, measurement, 0L);
        List<CPUStatVo> oneSecondCpu = getOneSecondCpuBefore(bucket, cpuMeasurement, 0L);
        StatVo statVo = new StatVo();
        BeanUtils.copyProperties(oneSecondStat, statVo);
        statVo.setCpus(oneSecondCpu);
        return statVo;
    }

    public StatVo getOneSecondStatBefore(String bucket, String measurement, Long second) {
        if (second == null || second < 0) {
            throw new RuntimeException("second不能为null或负数");
        }
        Instant stop = DateUtil.getBeforeSecondInstant(second);
        Instant start = stop.minus(Duration.ofSeconds(1));
        Flux statFlux = Flux.from(bucket)
                .range(start, stop)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));
        InfluxBO bo = new InfluxBO();
        bo.setFlux(statFlux);
        List<FluxTable> query = influxDBTemplate.query(QueryType.Flux, bo);
        StatVo statVo = new StatVo();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                if (statVo.getTime() == null) {
                    Object key = fluxRecord.getValueByKey("_time");
                    statVo.setTime((Instant) key);
                }
                Object key = fluxRecord.getValueByKey("_field");
                Object value = fluxRecord.getValueByKey("_value");
                if (key == null || value == null) {
                    continue;
                }
                statVo.setProperty((String) key, value.toString());
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }
        return statVo;
    }

    public List<ProcessorVo> getOneSecondProcessorBefore(String bucket, String[] measurement, Long second) {
        if (second == null || second < 0) {
            throw new RuntimeException("second不能为null或负数");
        }
        Instant stop = DateUtil.getBeforeSecondInstant(second);
        Instant start = stop.minus(Duration.ofSeconds(1));
        List<ProcessorVo> processorVos = new ArrayList<>();
        for (int i = 0; i < measurement.length; i++) {
            InfluxBO bo = new InfluxBO();
            Flux statFlux = Flux.from(bucket)
                    .range(start, stop)
                    .filter(Restrictions.and(
                            Restrictions.measurement().equal(measurement[i])));
            bo.setFlux(statFlux);
            List<FluxTable> query = influxDBTemplate.query(QueryType.Flux, bo);
            ProcessorVo processorVo = new ProcessorVo();
            for (FluxTable fluxTable : query) {
                List<FluxRecord> records = fluxTable.getRecords();
                for (FluxRecord fluxRecord : records) {
                    if (processorVo.getTime() == null) {
                        Object key = fluxRecord.getValueByKey("_time");
                        processorVo.setTime((Instant) key);
                    }
                    Object key = fluxRecord.getValueByKey("_field");
                    Object value = fluxRecord.getValueByKey("_value");
                    if (key == null || value == null) {
                        continue;
                    }
                    processorVo.setProperty((String) key, value.toString());
//                    System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
                }
            }
            processorVos.add(processorVo);
        }

        return processorVos;
    }

    public List<CPUStatVo> getOneSecondCpuBefore(String bucket, String[] measurement, Long second) {
        if (second == null || second < 0) {
            throw new RuntimeException("second不能为null或负数");
        }
        Instant stop = DateUtil.getBeforeSecondInstant(second);
        Instant start = stop.minus(Duration.ofSeconds(1));
        List<CPUStatVo> cpuStatVos = new ArrayList<>();
        for (int i = 0; i < measurement.length; i++) {
            InfluxBO bo = new InfluxBO();
            Flux statFlux = Flux.from(bucket)
                    .range(start, stop)
                    .filter(Restrictions.and(
                            Restrictions.measurement().equal(measurement[i])));
            bo.setFlux(statFlux);
            List<FluxTable> query = influxDBTemplate.query(QueryType.Flux, bo);
            CPUStatVo cpuStatVo = new CPUStatVo();
            for (FluxTable fluxTable : query) {
                List<FluxRecord> records = fluxTable.getRecords();
                for (FluxRecord fluxRecord : records) {
                    if (cpuStatVo.getTime() == null) {
                        Object key = fluxRecord.getValueByKey("_time");
                        cpuStatVo.setTime((Instant) key);
                    }
                    Object key = fluxRecord.getValueByKey("_field");
                    Object value = fluxRecord.getValueByKey("_value");
                    if (key == null || value == null) {
                        continue;
                    }
                    cpuStatVo.setProperty((String) key, value.toString());
//                    System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
                }
            }
            cpuStatVos.add(cpuStatVo);
        }

        return cpuStatVos;
    }
}
