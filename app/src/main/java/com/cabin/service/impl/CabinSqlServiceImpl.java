package com.cabin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cabin.entity.Tables;
import com.cabin.mapper.mybatis.informationSchema.TablesMapper;
import com.cabin.service.CabinSqlService;
import org.springframework.stereotype.Service;

/**
 * @author 伍六七
 * @date 2023/5/22 9:19
 */
@Service
public class CabinSqlServiceImpl extends ServiceImpl<TablesMapper, Tables> implements CabinSqlService {
}
