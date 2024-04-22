package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래의 함수가 시작될때 start transaction; 해주기
    // 함수가 예외없이 잘 끝났다면 commit
    // 문제가 있다면 rollback
    // Create
    @Transactional
    public void saveUser(UserCreateRequest request) {
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    //Read
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());
    }

    //Update
    @Transactional
    public void updateUser(UserUpdateRequest request) {
        // select * from user where id =? ;
        User user = userRepository.findById(request.getId()) //Optional<User>
                .orElseThrow(IllegalArgumentException::new);

        // Constructor 만들어주고
        // API를 통해 들어온 이름 넣어주고
        user.updateName(request.getName());
        // 변경된거를 save해줌.
        userRepository.save(user);
    }

    //Delete
    @Transactional
    public void deleteUser(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }
}
