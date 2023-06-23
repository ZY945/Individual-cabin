package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.feishu.FeiShuUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/6/6 17:10
 */
@Repository
public interface FeiShuUserRepository extends JpaRepository<FeiShuUser, Long> {

    FeiShuUser getFeiShuUserInfoByOpenId(@Param("open_id") String openId);

    FeiShuUser getFeiShuUserInfoById(@Param("id") Long Id);

}
