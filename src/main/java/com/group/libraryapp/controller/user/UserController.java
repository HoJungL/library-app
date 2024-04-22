package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService) {
        this.userService = userService;
    }

    //Create
    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) { // void : 아무것도 반환할 필요 없음..!!
        userService.saveUser(request);
     }

     //Read
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    //Update
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    //Delete
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
}

