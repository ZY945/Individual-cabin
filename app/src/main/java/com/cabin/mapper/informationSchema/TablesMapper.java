package com.cabin.mapper.informationSchema;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cabin.entity.Tables;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS("information")
public interface TablesMapper extends BaseMapper<Tables> {
    List<Tables> getTableNameByTableSchemas(String Schemas);
}