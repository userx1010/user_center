package com.xtf.userscenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xtf.userscenter.pojo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
* @author xtf
* @description 针对表【user】的数据库操作Service
* @createDate 2024-10-01 21:14:43
*/

public interface UserService extends IService<User> {
    String Register(String userAccount,String userPassword,String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    List<User> search(String userName,HttpServletRequest request);

    String deleteById(Integer id,HttpServletRequest request);
}
