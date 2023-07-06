package com.cabin.empty.influxDB;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author 伍六七
 * @date 2023/6/30 13:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "Processor")//influxDB注解
public class Processor {
    @Column
    private Integer id;
    @Column
    private String vendorId;
    @Column
    private Integer cpuFamily;
    @Column
    private Integer model;
    @Column
    private String modelName;
    @Column
    private Integer stepping;
    @Column
    private String microcode;
    @Column
    private Double cpuMhz;
    @Column
    private Integer cacheSize;
    @Column
    private Integer physicalId;
    @Column
    private Integer siblings;
    @Column
    private Integer coreId;
    @Column
    private Integer cpuCores;
    @Column
    private Integer apicId;
    @Column
    private Integer initialApicId;
    @Column
    private Boolean fpu;
    @Column
    private Boolean fpuException;
    @Column
    private Integer cpuidLevel;
    @Column
    private Boolean wp;
    @Column
    private String flags;
    @Column
    private Double bogomips;
    @Column
    private Integer clflushSize;
    @Column
    private Integer cacheAlignment;
    @Column
    private String addressSizes;
    @Column
    private String powerManagement;
    @Column(timestamp = true)
    Instant time;
    // 设置属性值的方法，你可以根据实际的信息做相应的类型转换
}
