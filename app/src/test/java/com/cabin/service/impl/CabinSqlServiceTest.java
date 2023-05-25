package com.cabin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cabin.entity.Tables;
import com.cabin.service.TablesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/22 11:12
 */
@SpringBootTest
public class CabinSqlServiceTest {


    @Autowired
    private TablesService tablesService;

    @Test
    void getTables() {
//        List<String> cabin = cabinSqlService.findTablesName("cabin");
//        System.out.println(cabin);
        LambdaQueryWrapper<Tables> lqw = Wrappers.lambdaQuery();
        lqw.eq(Tables::getTableSchema, "cabin");
        List<Tables> list = tablesService.list(lqw);
        System.out.println(list);
    }
}
