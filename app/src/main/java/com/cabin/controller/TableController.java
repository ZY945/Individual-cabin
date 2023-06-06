package com.cabin.controller;

import com.cabin.entity.response.Result;
import com.cabin.entity.Tables;
import com.cabin.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/22 9:27
 */
@RestController
@RequestMapping("/")
public class TableController {

    @Autowired
    private TablesService tablesService;

    @GetMapping("/tableName")
    public Result<List<String>> getTableName(@RequestParam String tableSchema) {
        return tablesService.getTablesNameByTableSchema(tableSchema);
    }

    @GetMapping("/table")
    public Result<List<Tables>> getTable(@RequestParam String tableSchema) {
        return tablesService.getTablesByTableSchema(tableSchema);
    }
}
