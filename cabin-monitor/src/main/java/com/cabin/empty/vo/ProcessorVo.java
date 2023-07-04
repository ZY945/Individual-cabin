package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/30 13:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorVo {
    private int id;
    private String vendorId;
    private int cpuFamily;
    private int model;
    private String modelName;
    private int stepping;
    private String microcode;
    private double cpuMhz;
    private int cacheSize;
    private int physicalId;
    private int siblings;
    private int coreId;
    private int cpuCores;
    private int apicId;
    private int initialApicId;
    private boolean fpu;
    private boolean fpuException;
    private int cpuidLevel;
    private boolean wp;
    private String flags;
    private double bogomips;
    private int clflushSize;
    private int cacheAlignment;
    private String addressSizes;
    private String powerManagement;

    // 设置属性值的方法，你可以根据实际的信息做相应的类型转换

    public void setProperty(String key, String value) {
        switch (key) {
            case "processor":
                this.id = Integer.parseInt(value);
                break;
            case "vendor_id":
                this.vendorId = value;
                break;
            case "cpu family":
                this.cpuFamily = Integer.parseInt(value);
                break;
            case "model":
                this.model = Integer.parseInt(value);
                break;
            case "model name":
                this.modelName = value;
                break;
            case "stepping":
                this.stepping = Integer.parseInt(value);
                break;
            case "microcode":
                this.microcode = value;
                break;
            case "cpu MHz":
                this.cpuMhz = Double.parseDouble(value);
                break;
            case "cache size":
                this.cacheSize = Integer.parseInt(value.split(" ")[0]);
                break;
            case "physical id":
                this.physicalId = Integer.parseInt(value);
                break;
            case "siblings":
                this.siblings = Integer.parseInt(value);
                break;
            case "core id":
                this.coreId = Integer.parseInt(value);
                break;
            case "cpu cores":
                this.cpuCores = Integer.parseInt(value);
                break;
            case "apicid":
                this.apicId = Integer.parseInt(value);
                break;
            case "initial apicid":
                this.initialApicId = Integer.parseInt(value);
                break;
            case "fpu":
                this.fpu = value.equals("yes");
                break;
            case "fpu_exception":
                this.fpuException = value.equals("yes");
                break;
            case "cpuid level":
                this.cpuidLevel = Integer.parseInt(value);
                break;
            case "wp":
                this.wp = value.equals("yes");
                break;
            case "flags":
                this.flags = value;
                break;
            case "bogomips":
                this.bogomips = Double.parseDouble(value);
                break;
            case "clflush size":
                this.clflushSize = Integer.parseInt(value);
                break;
            case "cache_alignment":
                this.cacheAlignment = Integer.parseInt(value);
                break;
            case "address sizes":
                this.addressSizes = value;
                break;
            case "power management":
                this.powerManagement = value;
                break;
        }
    }

    // toString 方法以便打印处理器信息

    @Override
    public String toString() {
        return "ProcessorVo{" +
                "id=" + id +
                ", vendorId='" + vendorId + '\'' +
                ", cpuFamily=" + cpuFamily +
                ", model=" + model +
                ", modelName='" + modelName + '\'' +
                ", stepping=" + stepping +
                ", microcode='" + microcode + '\'' +
                ", cpuMhz=" + cpuMhz +
                ", cacheSize=" + cacheSize +
                ", physicalId=" + physicalId +
                ", siblings=" + siblings +
                ", coreId=" + coreId +
                ", cpuCores=" + cpuCores +
                ", apicId=" + apicId +
                ", initialApicId=" + initialApicId +
                ", fpu=" + fpu +
                ", fpuException=" + fpuException +
                ", cpuidLevel=" + cpuidLevel +
                ", wp=" + wp +
                ", flags='" + flags + '\'' +
                ", bogomips=" + bogomips +
                ", clflushSize=" + clflushSize +
                ", cacheAlignment=" + cacheAlignment +
                ", addressSizes='" + addressSizes + '\'' +
                ", powerManagement='" + powerManagement + '\'' +
                '}';
    }
}
