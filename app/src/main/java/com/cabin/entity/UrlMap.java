package com.cabin.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/2 9:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DS("app")
@Table(name = "cabini_util_shorturl")//注意name是表名
public class UrlMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longUrl;
    private String shortUrl;

    //与数据库的datetime对应
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    //与数据库的datetime对应
    @DateTimeFormat(pattern = "yyyy-MM-dd")//设置接收日期参数时的格式
//    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")//设置返回日期参数时的格式
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastVisitTime;


    @Column(columnDefinition = "bigint default 1 comment '点击次数'" )
    private Long clickCount;
    /**
     * 1 表示启动 0 表示关闭
     */
    @Column(columnDefinition = "int default 1")
    private int status;

}
