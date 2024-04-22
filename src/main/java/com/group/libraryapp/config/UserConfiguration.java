package com.group.libraryapp.config;

import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserConfiguration {

/*    @Bean // UserService의 빈 어노테이션을 통해 등록된 UserRepository를 주입해준다
    public UserRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new UserRepository(jdbcTemplate);
    }*/
}
