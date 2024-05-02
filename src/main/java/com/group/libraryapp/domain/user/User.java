package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();


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

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream() // functional programming을 위해 stream 시작
                .filter(history -> history.getBookName().equals(bookName)) // 객체중에 충족하는 하는 것만 필터링
                .findFirst() //첫녀석
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn(); // 특정 녀석을 반납형태로 바꾸세요.
    }
}
