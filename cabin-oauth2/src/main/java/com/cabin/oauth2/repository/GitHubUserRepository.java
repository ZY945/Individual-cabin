package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.github.GitHubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 伍六七
 * @date 2023/6/23 17:40
 */
@Repository
public interface GitHubUserRepository extends JpaRepository<GitHubUser, Long> {

    GitHubUser getGitHubUserByGitHubUserId(Long gitHubUserId);

    GitHubUser getGitHubUserById(Long id);
}
