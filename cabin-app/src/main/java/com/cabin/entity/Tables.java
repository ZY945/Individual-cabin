package com.cabin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tables implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String tableCatalog;

    /**
     *
     */
    private String tableSchema;

    /**
     *
     */
    private String tableName;

    /**
     *
     */
    private String tableType;

    /**
     *
     */
    private String engine;

    /**
     *
     */
    private Integer version;

    /**
     *
     */
    private String rowFormat;

    /**
     *
     */
    private Long tableRows;

    /**
     *
     */
    private Long avgRowLength;

    /**
     *
     */
    private Long dataLength;

    /**
     *
     */
    private Long maxDataLength;

    /**
     *
     */
    private Long indexLength;

    /**
     *
     */
    private Long dataFree;

    /**
     *
     */
    private Long autoIncrement;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date checkTime;

    /**
     *
     */
    private String tableCollation;

    /**
     *
     */
    private Long checksum;

    /**
     *
     */
    private String createOptions;

    /**
     *
     */
    private String tableComment;


}