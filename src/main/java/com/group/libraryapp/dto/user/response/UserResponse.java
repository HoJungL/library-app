package com.group.libraryapp.dto.user.response;


import com.group.libraryapp.domain.user.User;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String name;

    public UserResponse(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private Integer age;

    public UserResponse(long id, User user) {
        this.id = id;
        this.name = user.getName();
        this.age = user.getAge();
    }
}
