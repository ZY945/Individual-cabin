package com.cabin.oauth2.empty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/7 21:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cabin_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    //与数据库的datetime对应
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creatTime;
    //与数据库的datetime对应
    @DateTimeFormat(pattern = "yyyy-MM-dd")//设置接收日期参数时的格式
    @Temporal(value = TemporalType.TIMESTAMP)//数据库返回的格式
    @Column(name = "lastlogin_time")
    private Date lastLoginTime;

    @Column(columnDefinition = "int default 0 comment '是否注销(0未注销)'")
    private int deleted;
}
