package com.xtf.userscenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtf.userscenter.mapper.UserMapper;
import com.xtf.userscenter.pojo.entity.User;
import com.xtf.userscenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    void insertTest(){
        User user = new User();
        user.setUserAccount("admin");
        user.setUserName("lisi");
        user.setUserPassword("123456");
        user.setGender(1);
        user.setPhone("12345678911");
        user.setEmail("22675@qq.com");
        user.setIsVail("1");
        user.setIsDelete(0);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
    @Test
    void resultSelectAll(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    void md5Test(){
        String password  = "123456";
        String digest = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(digest);
    }

    @Test
    public void getUserNameAllTest(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String userName = "xtf";

        if(StringUtils.isNoneBlank(userName)){
            queryWrapper.like("user_name",userName);
        }
        List<User> list = userService.list(queryWrapper);
        System.out.println(list);
    }
}
