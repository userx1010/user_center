package com.xtf.userscenter;

import com.xtf.userscenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest

public class RegisterTest {
    @Autowired
    private UserService userService;

    @Test
    public void accountTest(){
        // 测试账号为空和账号长度小于4位
        String userAccount = "ad";
        String userPassword = "123456";
        String checkPassword = "123456";

//        userService.Register(userAccount,userPassword,checkPassword);

        // 测试账号包含特殊字符和账号长度正常及账号重复注册
        // 测试密码
        userAccount = "admin_";
        userPassword = "12345678";
        checkPassword = "12345678";
        userService.Register(userAccount,userPassword,checkPassword);

//        userPassword = "12345678";
//        checkPassword = "12345678";
//        userService.Register(userAccount,userPassword,checkPassword);

        // 查看账号，密码和校验码为空
//        userAccount = "";
//        userPassword = "";
//        checkPassword = "";
//        userService.Register(userAccount,userPassword,checkPassword);
    }

}
