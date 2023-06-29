package com.cabin.mapper.jpa;

import com.cabin.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/29 9:58
 */
@Repository
public interface RoutingRepository extends JpaRepository<Routing,Long> {
    Routing getRoutingByIdAndDeleted(@Param("id") Long id,@Param("deleted") Integer deleted);
    List<Routing> getRoutingsByDeleted(@Param("deleted") Integer deleted);
    Routing getRoutingByPathAndDeleted(@Param("path") String path,@Param("deleted") Integer deleted);
    Routing getRoutingByTitleAndDeleted(@Param("title") String title,@Param("deleted") Integer deleted);
    Boolean existsRoutingByIdAndDeleted(@Param("id") Long id,@Param("deleted") Integer deleted);
}
