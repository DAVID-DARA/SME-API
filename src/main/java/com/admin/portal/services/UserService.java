package com.admin.portal.services;

import com.admin.portal.entities.Role;
import com.admin.portal.entities.Role_User;
import com.admin.portal.entities.User;
import com.admin.portal.models.UserRequest;
import com.admin.portal.repositories.RoleRepository;
import com.admin.portal.repositories.RoleUsersRepository;
import com.admin.portal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleUsersRepository roleUsersRepository;
    private final RoleRepository roleRepository;


    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    public List<User> findAllMakers () {
        Optional<Role> roles = this.roleRepository.findById(2L);
        Role role = roles.get();
        List<Role_User> rpcMakerRoleUsers = this.roleUsersRepository.findByRoleId(role);
        List<User> rpcMakerUsers = new ArrayList<>();

        for (Role_User rpcMaker : rpcMakerRoleUsers) {
            String userId = rpcMaker.getUserId().getUserId();
            User makerUser = this.userRepository.findByUserId(userId);
            if (rpcMakerUsers.contains(makerUser)) {
                break;
            } else {
                rpcMakerUsers.add(makerUser);
            }
        }
        return rpcMakerUsers;
    }

    public List<User> findAllCheckers() {
        Optional<Role> roles = this.roleRepository.findById(3L);
        Role role = roles.get();
        List<Role_User> rpcSupervisorRoleUser = this.roleUsersRepository.findByRoleId(role);
        List<User> rpcSupervisorUser = new ArrayList<>();

        for (Role_User rpcSupervisor : rpcSupervisorRoleUser) {
            String userId = rpcSupervisor.getUserId().getUserId();
            User makerUser = this.userRepository.findByUserId(userId);
            if (rpcSupervisorUser.contains(makerUser)) {
                break;
            } else {
                rpcSupervisorUser.add(makerUser);
            }
        }
        return rpcSupervisorUser;
    }

    public List<User> findAllAdministrators() {
        Optional<Role> roles = this.roleRepository.findById(4L);
        Role role = roles.get();
        List<Role_User> rpcAdministratorRoleUser = this.roleUsersRepository.findByRoleId(role);
        List<User> rpcAdministratorUser = new ArrayList<>();

        for (Role_User rpcSupervisor : rpcAdministratorRoleUser) {
            String userId = rpcSupervisor.getUserId().getUserId();
            User makerUser = this.userRepository.findByUserId(userId);
            if (rpcAdministratorUser.contains(makerUser)) {
                break;
            } else {
                rpcAdministratorUser.add(makerUser);
            }
        }
        return rpcAdministratorUser;
    }

    public User findUserById (String userId) {
        return this.userRepository.findByUserId(userId);
    }

//    public User getUserByUserIdAndRole(String userId, String roleName) {
//        return userRepository.findByUserIdAndRole(userId, roleName);
//    }
}
