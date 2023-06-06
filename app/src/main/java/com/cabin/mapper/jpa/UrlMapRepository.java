package com.cabin.mapper.jpa;

import com.cabin.entity.UrlMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/6/2 10:30
 */
@Repository
public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {
    UrlMap findUrlMapByShortUrl(String shortUrl);

    boolean existsUrlMapByShortUrl(String shortUrl);
    UrlMap queryUrlMapsByLongUrl(String longUrl);

//    @Modifying
//    @Query("update UrlMap u set u.clickCount = :#{#urlMap.clickCount},u.lastVisitTime = :#{#urlMap.lastVisitTime} where u.id = :#{#urlMap.id}")
//    void updateUrlMapById(@Param("urlMap")UrlMap urlMap);
}
