package com.cabin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cabin.common.response.Result;
import com.cabin.entity.Tables;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/22 13:43
 */
public interface TablesService extends IService<Tables> {

    Result<List<String>> getTablesNameByTableSchema(String tableSchema);

    Result<List<Tables>> getTablesByTableSchema(String tableSchema);
}
