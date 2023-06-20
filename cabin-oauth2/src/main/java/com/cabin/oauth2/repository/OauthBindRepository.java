package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.OauthBind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/6/9 14:08
 */
@Repository
public interface OauthBindRepository extends JpaRepository<OauthBind, Long> {
    OauthBind getOauthByFeiShuOpenId(String openId);
}
