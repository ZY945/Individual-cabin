package com.cabin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinSql implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String sql;

    private String user;

    private Date createTime;

    private String status;


}