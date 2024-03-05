package com.admin.portal.repositories;

import com.admin.portal.entities.Role;
import com.admin.portal.entities.Role_User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleUsersRepository extends JpaRepository<Role_User, Long> {

    List<Role_User> findByRoleId(Role roleId);

}
