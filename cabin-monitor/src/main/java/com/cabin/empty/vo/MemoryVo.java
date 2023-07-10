package com.cabin.empty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
    Instant time;

    public void setProperty(String key, String value) {
        switch (key) {
            case "memTotal" -> this.memTotal = value;
            case "memFree" -> this.memFree = value;
            case "memAvailable" -> this.memAvailable = value;
            case "buffers" -> this.buffers = value;
            case "cached" -> this.cached = value;
            case "swapCached" -> this.swapCached = value;
            case "active" -> this.active = value;
            case "inactive" -> this.inactive = value;
            case "activeAnon" -> this.activeAnon = value;
            case "inactiveAnon" -> this.inactiveAnon = value;
            case "activeFile" -> this.activeFile = value;
            case "inactiveFile" -> this.inactiveFile = value;
            case "unevictable" -> this.unevictable = value;
            case "mlocked" -> this.mlocked = value;
            case "swapTotal" -> this.swapTotal = value;
            case "swapFree" -> this.swapFree = value;
            case "dirty" -> this.dirty = value;
            case "writeback" -> this.writeback = value;
            case "anonPages" -> this.anonPages = value;
            case "mapped" -> this.mapped = value;
            case "shmem" -> this.shmem = value;
            case "slab" -> this.slab = value;
            case "sReclaimable" -> this.sReclaimable = value;
            case "sUnreclaim" -> this.sUnreclaim = value;
            case "kernelStack" -> this.kernelStack = value;
            case "pageTables" -> this.pageTables = value;
            case "nfsUnstable" -> this.nfsUnstable = value;
            case "bounce" -> this.bounce = value;
            case "writebackTmp" -> this.writebackTmp = value;
            case "commitLimit" -> this.commitLimit = value;
            case "committedAS" -> this.committedAS = value;
            case "vmallocTotal" -> this.vmallocTotal = value;
            case "vmallocUsed" -> this.vmallocUsed = value;
            case "vmallocChunk" -> this.vmallocChunk = value;
            case "percpu" -> this.percpu = value;
            case "hardwareCorrupted" -> this.hardwareCorrupted = value;
            case "anonHugePages" -> this.anonHugePages = value;
            case "cmaTotal" -> this.cmaTotal = value;
            case "cmaFree" -> this.cmaFree = value;
            case "hugePagesTotal" -> this.hugePagesTotal = value;
            case "hugePagesFree" -> this.hugePagesFree = value;
            case "hugePagesRsvd" -> this.hugePagesRsvd = value;
            case "hugePagesSurp" -> this.hugePagesSurp = value;
            case "hugepagesize" -> this.hugepagesize = value;
            case "directMap4k" -> this.directMap4k = value;
            case "directMap2M" -> this.directMap2M = value;
            case "directMap1G" -> this.directMap1G = value;
        }
    }
}
