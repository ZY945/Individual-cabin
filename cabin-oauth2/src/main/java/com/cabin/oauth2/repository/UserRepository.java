package com.cabin.oauth2.repository;

import com.cabin.oauth2.empty.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(@Param("email") String email);

    User getUserById(@Param("id") Long id);

    boolean existsUserByUserNameAndEmail(@Param("userName") String userName,
                                         @Param("email") String email);

    boolean existsUserByUserName(@Param("userName") String userName);

    User getUserByUserNameAndPassWord(@Param("userName") String userName, @Param("passWord") String passWord);
}