package com.cabin.service;

import com.cabin.empty.influxDB.CPUStat;
import com.cabin.empty.influxDB.Stat;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.empty.vo.StatVo;
import com.cabin.influxDB.empty.bo.query.InfluxBO;
import com.cabin.influxDB.empty.bo.query.QueryType;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.reflectUtil.ReflectUtils;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.LimitFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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

    public StatVo getOneSecondStatVo() {
        String bucket = "bucket";
        String measurement = "Stat";
        String[] cpuMeasurement = new String[]{"CPU0Stat","CPU1Stat","CPU2Stat"};
        StatVo oneSecondStat = getOneSecondStat(bucket, measurement);
        List<CPUStatVo> oneSecondCpu = getOneSecondCpu(bucket, cpuMeasurement);
        StatVo statVo = new StatVo();
        BeanUtils.copyProperties(oneSecondStat,statVo);
        statVo.setCpus(oneSecondCpu);
        return statVo;
    }
    public StatVo getOneSecondStat(String bucket,String measurement) {

        Instant stop = DateUtil.getNowInstant();
        Instant start = stop.minus(Duration.ofSeconds(1));
        Flux statFlux = Flux.from(bucket)
                .range(start, stop)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));
        InfluxBO bo = new InfluxBO();
        bo.setFlux(statFlux);
        List<FluxTable> query = influxDBTemplate.query(QueryType.Flux,bo);
        StatVo statVo = new StatVo();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                Object key = fluxRecord.getValueByKey("_field");
                Object value = fluxRecord.getValueByKey("_value");
                if(key==null){
                    continue;
                }
                statVo.setProperty((String) key, value);
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));

            }
        }
        return statVo;
    }
    public List<CPUStatVo> getOneSecondCpu(String bucket,String[] measurement) {
        Instant stop = DateUtil.getNowInstant();
        Instant start = stop.minus(Duration.ofSeconds(1));
        List<CPUStatVo> cpuStatVos = new ArrayList<>();
        for (int i = 0; i < measurement.length; i++) {
            InfluxBO bo = new InfluxBO();
            Flux statFlux = Flux.from(bucket)
                    .range(start, stop)
                    .filter(Restrictions.and(
                            Restrictions.measurement().equal(measurement[i])));
            bo.setFlux(statFlux);
            List<FluxTable> query = influxDBTemplate.query(QueryType.Flux,bo);
            CPUStatVo cpuStatVo = new CPUStatVo();
            for (FluxTable fluxTable : query) {
                List<FluxRecord> records = fluxTable.getRecords();
                for (FluxRecord fluxRecord : records) {
                    Object key = fluxRecord.getValueByKey("_field");
                    Object value = fluxRecord.getValueByKey("_value");
                    if(key==null){
                        continue;
                    }
                    cpuStatVo.setProperty((String) key, value);
                    System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));

                }
            }
            cpuStatVos.add(cpuStatVo);
        }

        return cpuStatVos;
    }
//    public StatVo getStatVo() {
//        StatVo statVo = new StatVo();
//        Stat stat = new Stat();
//        List<CPUStatVo> cpuVoList = new ArrayList<>();
//        List<CPUStat> cpuList = new ArrayList<>();
//        String cPUMeasurement = "CPUStat";//从vo读取name
//        String measurement = "Stat";
//        String bucket = "bucket";
//        InfluxBO influxBO = new InfluxBO();
//
//        Instant beforeSecondInstant = DateUtil.getBeforeSecondInstant(1L);
//        Instant nowInstant = DateUtil.getNowInstant();
//
//        Flux statFlux = Flux.from(bucket)
//                .range(beforeSecondInstant, nowInstant)
//                .filter(Restrictions.and(
//                        Restrictions.measurement().equal(measurement)));
//        influxBO.setFlux(statFlux);
//        List<Stat> query = influxDBTemplate.query(QueryType.Flux, influxBO, Stat.class);
//        stat = query.get(0);
//        BeanUtils.copyProperties(stat, statVo, StatVo.class);
//
//        while (true) {
//            LimitFlux cpu = Flux.from(bucket)
//                    .range(beforeSecondInstant)
//                    .filter(Restrictions.and(
//                            Restrictions.measurement().contains(new String[]{cPUMeasurement})))
//                    .limit(1);
//            influxBO.setFlux(cpu);
//            cpuList = influxDBTemplate.query(QueryType.Flux, influxBO, CPUStat.class);
//            if (cpuList.size() == 0) {
//                break;
//            }
//            CPUStat cpuStat = cpuList.get(0);
//            CPUStatVo cpuStatVo = new CPUStatVo();
//            BeanUtils.copyProperties(cpuStat, cpuStatVo, CPUStatVo.class);
//            cpuVoList.add(cpuStatVo);
//        }
//        statVo.setCpus(cpuVoList);
//        return statVo;
//    }
}
