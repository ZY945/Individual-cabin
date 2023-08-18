package com.cabin;

import com.cabin.common.mail.Vo.MailVo;
import com.cabin.common.schedule.ProcessorTask;
import com.cabin.empty.influxDB.Stat;
import com.cabin.empty.vo.StatVo;
import com.cabin.influxDB.empty.bo.query.InfluxBO;
import com.cabin.influxDB.empty.bo.query.QueryType;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.notice.util.DingTalkHelper;
import com.cabin.service.QueryService;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.LimitFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/2 16:04
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class InfluxDBTest {
    //TODO 未知-com.cabin.monitor就不行,com.cabin就可以获取到bean

    @Resource(name = "influxByToken")
    private InfluxDBClient client;
    @Resource(name = "InfluxDBTemplate")
    private InfluxDBTemplate template;

    @Autowired
    private QueryService queryService;
    @Autowired
    private ProcessorTask processorTask;


    ////////////测试查询接口///////////

    @Test
    void testQueryService() {
        StatVo statVo = queryService.getOneSecondStatVo();
        System.out.println(statVo.toString());
    }

    ////////////测试influx自带方法///////////

    /**
     * 测试influx自带的方法
     */
    @Test
    void testAPI() {
        String bucket = "bucket";
        String measurement = "Stat";
        Instant stop = DateUtil.getNowInstant();
        Instant start = stop.minus(Duration.ofSeconds(1));

        Flux statFlux = Flux.from(bucket)
                .range(start, stop)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(measurement)));

        List<Stat> queryList = client.getQueryApi().query(statFlux.toString(), Stat.class);
        queryList.forEach(l -> {
            System.out.println(l.toString());
        });

        List<FluxTable> query = client.getQueryApi().query(statFlux.toString());
        StatVo statVo = new StatVo();
        for (FluxTable fluxTable : query) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                Object key = fluxRecord.getValueByKey("_field");
                Object value = fluxRecord.getValueByKey("_value");
                if (key == null) {
                    continue;
                }
                statVo.setProperty((String) key, (String) value);
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));

            }
        }
        System.out.println(statVo);
    }

    ////////////测试封装的方法///////////

    /**
     * 通过fluxStr查询
     */
    @Test
    void testInfluxFluxStr() {
        InfluxBO influxBO = new InfluxBO();

        influxBO.setFluxStr("""
                    from(bucket: "bucket")
                        |> range(start: 0)
                        |> filter(fn: (r) => r._measurement == "test-2023")
                        |> filter(fn: (r) => r._field == "value")
                """);
        List<FluxTable> tables = template.query(QueryType.FluxStr, influxBO);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }
    }

    /**
     * 通过Flux-dsl查询
     */
    @Test
    void testFlux() {
        InfluxBO influxBO = new InfluxBO();
        String bucket = "bucket";
        String measurement = "test-2023";
        LimitFlux flux = Flux.from(bucket)
                .range(-30L, ChronoUnit.DAYS)
                .filter(Restrictions.and(Restrictions.measurement().equal(measurement)))
                .limit(10);
        influxBO.setFlux(flux);
        List<FluxTable> tables = template.query(QueryType.Flux, influxBO);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }
    }

    /**
     * 统计总数
     */
    @Test
    void testCount() {
        String measurement = "Memory";
        Long cpu0Stat = template.count(measurement);
        System.out.println(cpu0Stat);
    }

    /**
     * TODO 暂时不打算提供接口,会导致数据全部丢失
     * 删除bucket的measurement(时间限制)，minus是减去,因此后面是正数
     */
    @Test
    void testDel() {
        OffsetDateTime start = OffsetDateTime.now().minus(30L, ChronoUnit.DAYS);
        OffsetDateTime stop = OffsetDateTime.now();
        System.out.println(stop);
        System.out.println(start);
        boolean del = template.del(start, stop, "bucket", "user");
        System.out.println(del);
    }

    /**
     * 测试实体类转hashmap
     */
    @Test
    void testConvertToHashMap() throws IOException {

        MailVo mailVo = new MailVo();
        String Text =
                "报警服务器:" + "ipAddress" + "</br" +
                        "cpu型号:" + "cpuName" + "</br" +
                        "cpu占用率:" + "cpuUsage" + "%";
        mailVo.setTo("userEmail")
                .setSub("服务器报警-邮件通知")
                .setText(Text);
        String s = JacksonUtils.writeValueAsString(mailVo);
        System.out.println(s);
        byte[] message = s.getBytes();
        //发送报警通知
        MailVo mailVo1 = JacksonUtils.convertValue(message, MailVo.class);
        System.out.println(mailVo1.toString());
//        long second = 2L;
//        Instant nowInstant = DateUtil.getNowInstant();
//        Instant start1 = nowInstant.minus(Duration.ofSeconds(1));
//        Instant stop = DateUtil.getBeforeSecondInstant(0L);
//        Instant start = stop.minus(Duration.ofSeconds(1));
//        System.out.println(nowInstant);
//        System.out.println(start1);
//        System.out.println(stop);
//        System.out.println(start);


//        String bucket = "bucket";
//        String[] cpuMeasurement = new String[]{"CPU1Stat", "CPU2Stat", "CPU3Stat"};
//        List<CPUStatVo> secondCpuBeforeOne = queryService.getOneSecondCpuBefore(bucket, cpuMeasurement,0L);
//        secondCpuBeforeOne.forEach(l -> {
//            System.out.println(l.toString());
//        });


//        CPUStat cpuStat = new CPUStat();
//        cpuStat.setTime(Instant.now());
//        Map<String, Object> stringObjectMap = JacksonUtils.convertToHashMap(cpuStat);
//        System.out.println(stringObjectMap.size());
//        System.out.println(stringObjectMap.get("time"));
    }

    @Resource(name = "DingTalkHelper")
    private DingTalkHelper dingTalkHelper;
    @Value("${dingding.access_token}")
    private String name;

    /**
     * 测试dingtalk
     */
    @Test
    void test() {
        dingTalkHelper.sendMessageByText("作业没有", null, true);
        System.out.println(name);
    }

}
