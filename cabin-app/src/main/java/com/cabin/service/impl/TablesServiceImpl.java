package com.cabin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabin.entity.Tables;
import com.cabin.mapper.mybatis.informationSchema.TablesMapper;
import com.cabin.service.TablesService;
import com.cabin.utils.response.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/22 13:44
 */
@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, Tables> implements TablesService {

    @Override
    public Result<List<String>> getTablesNameByTableSchema(String tableSchema) {
        Result<List<Tables>> tablesByTableSchema = this.getTablesByTableSchema(tableSchema);
        List<Tables> data = tablesByTableSchema.getData();
        List<String> tableNameList = data.stream().map(Tables::getTableName).toList();
        return Result.success(tableNameList, "数据库对应的表名");
    }

    @Override
    public Result<List<Tables>> getTablesByTableSchema(String tableSchema) {
        Result<List<String>> result = new Result<>();
        LambdaQueryWrapper<Tables> lqw = Wrappers.lambdaQuery();
        lqw.eq(!StringUtils.isBlank(tableSchema), Tables::getTableSchema, tableSchema);
        List<Tables> tableList = this.list(lqw);
        return Result.success(tableList, "数据库对应的表的详细信息");
    }
}
