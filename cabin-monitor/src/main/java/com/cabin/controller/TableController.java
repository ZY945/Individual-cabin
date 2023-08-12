package com.cabin.controller;

import com.cabin.empty.db.TablesInfo;
import com.cabin.service.NewTablesService;
import com.cabin.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/22 15:05
 */
@RestController
@RequestMapping("/dataBase")
public class TableController {

    @Autowired
    private NewTablesService newTablesService;

    @GetMapping("/tableName")
    public Result<List<String>> getTableName(@RequestParam String tableSchema) {
        return newTablesService.getTablesNameByTableSchema(tableSchema);
    }

    @GetMapping("/table")
    public Result<List<TablesInfo>> getTable(@RequestParam String tableSchema) {
        return newTablesService.getTablesByTableSchema(tableSchema);
    }
}
