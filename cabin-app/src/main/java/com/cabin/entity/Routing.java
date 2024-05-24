package com.cabin.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/29 9:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DS("app")
@Table(name = "cabin_routing")
public class Routing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @JsonProperty(value = "prepend-icon")
    @Column(name = "prepend_icon")
    private String prependIcon;
    private String path;
    /**
     * 1 表示删除 0 表示未删除
     */
    @Column(columnDefinition = "int default 0 comment '是否注销(0未注销)'")
    private int deleted;

}
