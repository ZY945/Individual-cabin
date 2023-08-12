package com.cabin.empty.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TABLES")
public class TablesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "TABLE_CATALOG", nullable = false)
    private String tableCatalog;

    @Column(name = "TABLE_SCHEMA", nullable = false)
    private String tableSchema;

    @Column(name = "TABLE_NAME", nullable = false)
    private String tableName;

    @Column(name = "TABLE_TYPE", nullable = false)
    private String tableType;

    @Column(name = "ENGINE", nullable = true)
    private String engine;

    @Column(name = "VERSION", nullable = true)
    private Long version;

    @Column(name = "ROW_FORMAT", nullable = true)
    private String rowFormat;

    @Column(name = "TABLE_ROWS", nullable = true)
    private Long tableRows;

    @Column(name = "AVG_ROW_LENGTH", nullable = true)
    private Long averageRowLength;

    @Column(name = "DATA_LENGTH", nullable = true)
    private Long dataLength;

    @Column(name = "MAX_DATA_LENGTH", nullable = true)
    private Long maxDataLength;

    @Column(name = "INDEX_LENGTH", nullable = true)
    private Long indexLength;

    @Column(name = "DATA_FREE", nullable = true)
    private Long dataFree;

    @Column(name = "AUTO_INCREMENT", nullable = true)
    private Long autoIncrement;

    @Column(name = "CREATE_TIME", nullable = true)
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME", nullable = true)
    private LocalDateTime updateTime;

    @Column(name = "CHECK_TIME", nullable = true)
    private Date checkTime;

    @Column(name = "TABLE_COLLATION", nullable = true)
    private String TABLE_COLLATION;
    @Column(name = "CHECKSUM", nullable = true)
    private Long checksum;

    @Column(name = "CREATE_OPTIONS", nullable = true)
    private String createOptions;

    @Column(name = "TABLE_COMMENT", nullable = true)
    private String tableComment;

}