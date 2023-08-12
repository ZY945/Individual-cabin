package com.cabin.repository.db;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cabin.empty.db.TablesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/22 14:46
 */
@DS("information")
@Repository
public interface TableRepository extends JpaRepository<TablesInfo, Long> {

    List<TablesInfo> getAllByTableSchema(@Param("tableSchema") String tableSchema);

}
