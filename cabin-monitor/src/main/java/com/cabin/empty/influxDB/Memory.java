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
    /**
     * 物理内存总量
     */
    @Column
    private String memTotal;
    /**
     * 空闲物理内存
     */
    @Column
    private String memFree;
    /**
     * 可用物理内存
     */
    @Column
    private String memAvailable;
    /**
     * 缓冲区占用的内存
     */
    @Column
    private String buffers;
    /**
     * 缓存占用的内存
     */
    @Column
    private String cached;
    /**
     * 交换缓存占用的内存
     */
    @Column
    private String swapCached;
    /**
     * 活跃内存
     */
    @Column
    private String active;
    /**
     * 非活跃内存
     */
    @Column
    private String inactive;
    /**
     * 活跃的匿名内存
     */
    @Column
    private String activeAnon;
    /**
     * 非活跃的匿名内存
     */
    @Column
    private String inactiveAnon;
    /**
     * 活跃的文件内存
     */
    @Column
    private String activeFile;
    /**
     * 非活跃的文件内存
     */
    @Column
    private String inactiveFile;
    /**
     * 无法驱逐出的内存
     */
    @Column
    private String unevictable;
    /**
     * 锁定的内存
     */
    @Column
    private String mlocked;
    /**
     * 总交换空间大小
     */
    @Column
    private String swapTotal;
    /**
     * 空闲交换空间大小
     */
    @Column
    private String swapFree;
    /**
     * 脏页面的数量
     */
    @Column
    private String dirty;
    /**
     * 正在刷新到磁盘的页面数量
     */
    @Column
    private String writeback;
    /**
     * 匿名页面数量
     */
    @Column
    private String anonPages;
    /**
     * 映射页面数量
     */
    @Column
    private String mapped;
    /**
     * 共享内存数量
     */
    @Column
    private String shmem;
    /**
     * SLUB 分配器使用的内存数量
     */
    @Column
    private String slab;
    /**
     * 可以被分配器重新获取的 SLUB 页面数量
     */
    @Column
    private String sReclaimable;
    /**
     * 无法被分配器重新获取的 SLUB 页面数量
     */
    @Column
    private String sUnreclaim;
    /**
     * 内核栈使用的内存大小
     */
    @Column
    private String kernelStack;
    /**
     * 页表使用的内存大小
     */
    @Column
    private String pageTables;
    /**
     * 不稳定的 NFS 页面数量
     */
    @Column
    private String nfsUnstable;
    /**
     * 回滚页面数量
     */
    @Column
    private String bounce;
    /**
     * 等待刷新到磁盘的页面数量
     */
    @Column
    private String writebackTmp;
    /**
     * 用户进程可提交内存的限制
     */
    @Column
    private String commitLimit;
    /**
     * 已经向用户进程承诺的内存大小
     */
    @Column
    private String committedAS;
    /**
     * 分配给 vmalloc 区域的总内存大小
     */
    @Column
    private String vmallocTotal;
    /**
     * vmalloc 区域已使用的内存大小
     */
    @Column
    private String vmallocUsed;
    /**
     * vmalloc 区域中最大连续空闲块的大小
     */
    @Column
    private String vmallocChunk;
    /**
     * 每个 CPU 的私有数据区大小
     */
    @Column
    private String percpu;
    /**
     * 硬件错误导致的内存损坏数量
     */
    @Column
    private String hardwareCorrupted;
    /**
     * 巨大页面的匿名内存数量
     */
    @Column
    private String anonHugePages;
    /**
     * 连续内存区域的总大小
     */
    @Column
    private String cmaTotal;
    /**
     * 连续内存区域的空闲大小
     */
    @Column
    private String cmaFree;
    /**
     * 巨大页面的总数量
     */
    @Column
    private String hugePagesTotal;
    /**
     * 空闲的巨大页面数量
     */
    @Column
    private String hugePagesFree;
    /**
     * 巨大页面保留数量
     */
    @Column
    private String hugePagesRsvd;

    /**
     * 巨大页面额外数量
     */
    @Column
    private String hugePagesSurp;

    /**
     * 巨大页面的页面大小
     */
    @Column
    private String hugepagesize;

    /**
     * 直接映射的4KB页数
     */
    @Column
    private String directMap4k;

    /**
     * 直接映射的2MB页数
     */
    @Column
    private String directMap2M;

    /**
     * 直接映射的1GB页数
     */
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
