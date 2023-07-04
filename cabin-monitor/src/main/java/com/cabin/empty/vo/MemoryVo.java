package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryVo {
    private String memTotal;
    private String memFree;
    private String memAvailable;
    private String buffers;
    private String cached;
    private String swapCached;
    private String active;
    private String inactive;
    private String activeAnon;
    private String inactiveAnon;
    private String activeFile;
    private String inactiveFile;
    private String unevictable;
    private String mlocked;
    private String swapTotal;
    private String swapFree;
    private String dirty;
    private String writeback;
    private String anonPages;
    private String mapped;
    private String shmem;
    private String slab;
    private String sReclaimable;
    private String sUnreclaim;
    private String kernelStack;
    private String pageTables;
    private String nfsUnstable;
    private String bounce;
    private String writebackTmp;
    private String commitLimit;
    private String committedAS;
    private String vmallocTotal;
    private String vmallocUsed;
    private String vmallocChunk;
    private String percpu;
    private String hardwareCorrupted;
    private String anonHugePages;
    private String cmaTotal;
    private String cmaFree;
    private String hugePagesTotal;
    private String hugePagesFree;
    private String hugePagesRsvd;
    private String hugePagesSurp;
    private String hugepagesize;
    private String directMap4k;
    private String directMap2M;
    private String directMap1G;

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
