package org.example.douyin;

import org.example.douyin.entity.User;
import org.example.douyin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DouyinApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User();
        user.setEmail("2378150995@qq.com");
        user.setNickName("slkdjf");
        userMapper.insert(user); 
    }

}
