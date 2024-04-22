package com.group.libraryapp.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @Column(nullable = false, length = 20, name = "name")
    private String name;
    @Column(nullable = false, name = "age")
    private Integer age;

    //기본생성자
    public User() {}
    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }


    public void updateName(String name) {
        this.name = name;
    }
}
