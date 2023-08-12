package com.cabin.service.db;

import com.cabin.empty.db.TablesInfo;
import com.cabin.repository.db.TableRepository;
import com.cabin.service.NewTablesService;
import com.cabin.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/22 13:44
 */
@Service()
public class TablesServiceImpl implements NewTablesService {

    @Autowired
    private TableRepository mysqlRepository;

    @Override
    public Result<List<String>> getTablesNameByTableSchema(String tableSchema) {
        return null;
    }

    @Override
    public Result<List<TablesInfo>> getTablesByTableSchema(String tableSchema) {
        List<TablesInfo> allByTableSchema = mysqlRepository.getAllByTableSchema(tableSchema);
        return Result.success(allByTableSchema, "数据");
    }
}
