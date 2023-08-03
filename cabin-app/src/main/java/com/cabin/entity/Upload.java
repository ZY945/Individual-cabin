package com.cabin.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文件上传记录-数据库实体
 *
 * @author 伍六七
 * @date 2023/8/3 11:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DS("app")
@Table(name = "cabin_util_upload")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @Column(name = "save_name")
    private String fileName;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_type")
    private String resourceType;

    private String md5;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creatTime;

    //与数据库的datetime对应
    @DateTimeFormat(pattern = "yyyy-MM-dd")//设置接收日期参数时的格式
    @Temporal(value = TemporalType.TIMESTAMP)//数据库返回的格式
    @Column(name = "last_visit_time")
    private Date lastVisitTime;
    /**
     * 1 表示删除 0 表示未删除
     */
    @Column(columnDefinition = "int default 0 comment '是否删除(0未删除)'")
    private int deleted;
}
