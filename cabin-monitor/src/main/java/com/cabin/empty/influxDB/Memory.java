package com.cabin.empty.influxDB;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "Memory")//influxDB注解
public class Memory {
    @Column
    private String memTotal;
    @Column
    private String memFree;
    @Column
    private String memAvailable;
    @Column
    private String buffers;
    @Column
    private String cached;
    @Column
    private String swapCached;
    @Column
    private String active;
    @Column
    private String inactive;
    @Column
    private String activeAnon;
    @Column
    private String inactiveAnon;
    @Column
    private String activeFile;
    @Column
    private String inactiveFile;
    @Column
    private String unevictable;
    @Column
    private String mlocked;
    @Column
    private String swapTotal;
    @Column
    private String swapFree;
    @Column
    private String dirty;
    @Column
    private String writeback;
    @Column
    private String anonPages;
    @Column
    private String mapped;
    @Column
    private String shmem;
    @Column
    private String slab;
    @Column
    private String sReclaimable;
    @Column
    private String sUnreclaim;
    @Column
    private String kernelStack;
    @Column
    private String pageTables;
    @Column
    private String nfsUnstable;
    @Column
    private String bounce;
    @Column
    private String writebackTmp;
    @Column
    private String commitLimit;
    @Column
    private String committedAS;
    @Column
    private String vmallocTotal;
    @Column
    private String vmallocUsed;
    @Column
    private String vmallocChunk;
    @Column
    private String percpu;
    @Column
    private String hardwareCorrupted;
    @Column
    private String anonHugePages;
    @Column
    private String cmaTotal;
    @Column
    private String cmaFree;
    @Column
    private String hugePagesTotal;
    @Column
    private String hugePagesFree;
    @Column
    private String hugePagesRsvd;
    @Column
    private String hugePagesSurp;
    @Column
    private String hugepagesize;
    @Column
    private String directMap4k;
    @Column
    private String directMap2M;
    @Column
    private String directMap1G;
    @Column(timestamp = true)
    Instant time;

    public void setProperty(String key, String value) {
        switch (key) {
            case "MemTotal" -> this.memTotal = value;
            case "MemFree" -> this.memFree = value;
            case "MemAvailable" -> this.memAvailable = value;
            case "Buffers" -> this.buffers = value;
            case "Cached" -> this.cached = value;
            case "SwapCached" -> this.swapCached = value;
            case "Active" -> this.active = value;
            case "Inactive" -> this.inactive = value;
            case "Active(anon)" -> this.activeAnon = value;
            case "Inactive(anon)" -> this.inactiveAnon = value;
            case "Active(file)" -> this.activeFile = value;
            case "Inactive(file)" -> this.inactiveFile = value;
            case "Unevictable" -> this.unevictable = value;
            case "Mlocked" -> this.mlocked = value;
            case "SwapTotal" -> this.swapTotal = value;
            case "SwapFree" -> this.swapFree = value;
            case "Dirty" -> this.dirty = value;
            case "Writeback" -> this.writeback = value;
            case "AnonPages" -> this.anonPages = value;
            case "Mapped" -> this.mapped = value;
            case "Shmem" -> this.shmem = value;
            case "Slab" -> this.slab = value;
            case "SReclaimable" -> this.sReclaimable = value;
            case "SUnreclaim" -> this.sUnreclaim = value;
            case "KernelStack" -> this.kernelStack = value;
            case "PageTables" -> this.pageTables = value;
            case "NFS_Unstable" -> this.nfsUnstable = value;
            case "Bounce" -> this.bounce = value;
            case "WritebackTmp" -> this.writebackTmp = value;
            case "CommitLimit" -> this.commitLimit = value;
            case "Committed_AS" -> this.committedAS = value;
            case "VmallocTotal" -> this.vmallocTotal = value;
            case "VmallocUsed" -> this.vmallocUsed = value;
            case "VmallocChunk" -> this.vmallocChunk = value;
            case "Percpu" -> this.percpu = value;
            case "HardwareCorrupted" -> this.hardwareCorrupted = value;
            case "AnonHugePages" -> this.anonHugePages = value;
            case "CmaTotal" -> this.cmaTotal = value;
            case "CmaFree" -> this.cmaFree = value;
            case "HugePages_Total" -> this.hugePagesTotal = value;
            case "HugePages_Free" -> this.hugePagesFree = value;
            case "HugePages_Rsvd" -> this.hugePagesRsvd = value;
            case "HugePages_Surp" -> this.hugePagesSurp = value;
            case "Hugepagesize" -> this.hugepagesize = value;
            case "DirectMap4k" -> this.directMap4k = value;
            case "DirectMap2M" -> this.directMap2M = value;
            case "DirectMap1G" -> this.directMap1G = value;
        }
    }
}
