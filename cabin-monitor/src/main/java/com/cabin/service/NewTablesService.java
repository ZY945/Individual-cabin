package com.cabin.service;

import com.cabin.empty.db.TablesInfo;
import com.cabin.utils.response.Result;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/22 21:18
 */
public interface NewTablesService {
    public Result<List<String>> getTablesNameByTableSchema(String tableSchema);

    public Result<List<TablesInfo>> getTablesByTableSchema(String tableSchema);

}
