package com.admin.portal.repositories;

import com.admin.portal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String id);

//    @Query("SELECT u FROM User u JOIN u.roleId ru WHERE u.userId = :userId AND ru.role = :roleName")
//    User findByUserIdAndRole(String userId, String roleName);
}
