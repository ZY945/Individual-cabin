package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.gitee.GiteeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/6/24 23:08
 */
@Repository
public interface GiteeUserRepository extends JpaRepository<GiteeUser, Long> {
    GiteeUser getGiteeUserByGiteeUserId(Long giteeUserId);

    GiteeUser getGiteeUserById(Long id);
}
