package com.cabin.mapper.mybatis;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabin.entity.CabinSql;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("app")
public interface CabinSqlMapper extends BaseMapper<CabinSql> {
}