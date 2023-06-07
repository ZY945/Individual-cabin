package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByEmail(String email);
}