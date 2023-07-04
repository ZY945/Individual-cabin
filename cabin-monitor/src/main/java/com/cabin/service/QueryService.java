package com.cabin.service;

import com.cabin.empty.influxDB.CPUStat;
import com.cabin.empty.influxDB.Stat;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.empty.vo.StatVo;
import com.cabin.influxDB.empty.bo.query.InfluxBO;
import com.cabin.influxDB.empty.bo.query.QueryType;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.utils.dateUtil.DateUtil;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.LimitFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public StatVo getStatVo() {
        StatVo statVo = new StatVo();
        Stat stat = new Stat();
        ArrayList<CPUStatVo> cpuVoList = new ArrayList<>();
        ArrayList<CPUStat> cpuList = new ArrayList<>();
        String cPUMeasurement = "CPUStat";//从vo读取name
        String measurement = "Stat";
        String bucket = "bucket";
        InfluxBO influxBO = new InfluxBO();
        Instant beforeSecondInstant = DateUtil.getBeforeSecondInstant(1L);
        Instant nowInstant = DateUtil.getNowInstant();
        Flux statFlux = Flux.from(bucket)
                .range(beforeSecondInstant, nowInstant)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));
        influxBO.setFlux(statFlux);
        List<Stat> query = influxDBTemplate.query(QueryType.Flux, influxBO, Stat.class);
        List<FluxTable> query1 = influxDBTemplate.query(QueryType.Flux, influxBO);
        query1.get(0).getRecords().forEach(System.out::println);
        Stat stat1 = query.get(0);
        List<CPUStatVo> cpuStats = null;
        while (true) {
            LimitFlux cpu = Flux.from(bucket)
                    .range(beforeSecondInstant)
                    .filter(Restrictions.and(
                            Restrictions.measurement().contains(new String[]{cPUMeasurement})))
                    .limit(1);
            influxBO.setFlux(cpu);
            cpuStats = influxDBTemplate.query(QueryType.Flux, influxBO, CPUStatVo.class);
            if (cpuStats.size() == 0) {
                break;
            }
            CPUStatVo cpuStat = cpuStats.get(0);
            cpuVoList.add(cpuStat);
        }
        BeanUtils.copyProperties(stat, statVo, StatVo.class);
        BeanUtils.copyProperties(stat, statVo, StatVo.class);
        statVo.setCpus(cpuVoList);
        return statVo;
    }
}
