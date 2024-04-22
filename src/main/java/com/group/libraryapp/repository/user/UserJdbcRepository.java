package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create
    public void saveUser(String name, Integer age) {
        String sql = "insert into user (name, age) values (?, ?)";
        jdbcTemplate.update(sql, name, age);

    }

    //Read
    public List<UserResponse> getUsers() {
            String sql = "select * from user";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            });
    }

    //Update
    public boolean isUserNotExist(long id) {
        String readSql = "select * from user where id = ?";
        // 조회되었을때, 결과가 있으면 무조건 0을 반환,이때, 0은 list로 반환됨! 물음표에는 request.getId() 반환
        // 0이 조회가 안되면 isUSerNotExist는 false 가 뜰거야!
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id) {
        String sql = "update user set name = ? where id = ?";
        jdbcTemplate.update(sql, name, id);
    }


    //Delete
    public boolean isUserNotExist(String name) {
        String readSql = "select * from user where name = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
    }

    public void deleteUser(String name) {
        String sql = "delete from user where name = ?";
        jdbcTemplate.update(sql, name);
    }


}
