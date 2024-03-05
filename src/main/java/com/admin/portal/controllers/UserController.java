package com.admin.portal.controllers;

import com.admin.portal.entities.User;
import com.admin.portal.models.UserRequest;
import com.admin.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    //    private final AutoAssignmentService autoAssignmentService;
    private final UserService userService;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/makers")
    public List<User> findAllMakers () {
        return this.userService.findAllMakers();
    }

    @GetMapping("/checkers")
    public List<User> findAllCheckers () {
        return this.userService.findAllCheckers();
    }

    @PostMapping("/findByUserId")
    public User findUserById (@RequestParam String id) {
        return this.userService.findUserById(id);
    }


//    @PostMapping("/userIdAndRole")
//    public User findUserByIdAndRole (@RequestParam String userID, @RequestParam String RoleName){
//        return this.userService.getUserByUserIdAndRole(userID, RoleName);
//    }
}
