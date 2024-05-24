package com.cabin.empty.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author 伍六七
 * @date 2023/7/22 15:06
 */

@Entity
@Table(name = "INNODB_TABLES")
public class InnodbTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "FLAG", nullable = false)
    private Integer flag;

    @Column(name = "N_COLS", nullable = false)
    private Integer nCols;

    @Column(name = "SPACE", nullable = false)
    private Long space;

    @Column(name = "ROW_FORMAT", nullable = true)
    private String rowFormat;

    @Column(name = "ZIP_PAGE_SIZE", nullable = false)
    private Integer zipPageSize;

    @Column(name = "SPACE_TYPE", nullable = true)
    private String spaceType;

    @Column(name = "INSTANT_COLS", nullable = false)
    private Integer instantCols;

    @Column(name = "TOTAL_ROW_VERSIONS", nullable = false)
    private Integer totalRowVersions;

    // Getter and Setter methods for all the fields go here...
}
