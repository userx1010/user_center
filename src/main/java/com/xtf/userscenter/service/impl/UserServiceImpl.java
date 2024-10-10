package com.xtf.userscenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtf.userscenter.constant.UserConStant;
import com.xtf.userscenter.mapper.UserMapper;
import com.xtf.userscenter.pojo.entity.User;
import com.xtf.userscenter.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author xtf
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-10-01 21:14:43
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final String SALT = "xtf";

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     */
    public String Register(String userAccount, String userPassword,String checkPassword) {
        // 1.账号检验，账号长度不少于4位，不包含特殊字符
        if(StringUtils.isAnyEmpty(userAccount,userPassword,checkPassword)){
            log.info("注册失败");
            return "ERROR_registration_failed";
        }
        if(userAccount.length() < 4){
            log.info("账号长度少于四位，注册失败");
            return "ERROR_account length is less than 4 characters";
        }
        String regex = "^[a-zA-z0-9_]+$";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);

        if(!matcher.find()){
            log.info("账号包含特殊字符,注册失败");
            return "ERROR_The account contains special characters";
        }
        // 2. 密码校验，长度不少于8位，
        if(!userPassword.equals(checkPassword)){
            log.info("账号密码错误，注册失败");
            return "ERROR_Wrong password!";
        }
        if(userPassword.length() < 8 || checkPassword.length() <8){
            log.info("账号密码长度过短，请重新设置");
            return "ERROR_The account password is too short. Please reset it";
        }


        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        User selectUser = userMapper.getUserName(userAccount);
        if(selectUser != null){
            log.info("账号重复，请重新输入!");
            return "ERROR_ACCOUNT_DUPLICATION";
        }

        // 3.注册账号
        User user = new User();
        user.setUserName("xtf");
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.insert(user);
        if(result < 0){
            log.info("账号注册失败");
            return "ERROR_REGISTRATION_FAILED";
        }else{
            log.info("账号注册成功");
            return "SUCCESS_REGISTRATION_SUCCESSFUL";
        }

    }

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    // TODO
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request){
        // 1.账号校验
        if(StringUtils.isAnyEmpty(userAccount,userPassword)){
            log.info("登录失败，账号或密码为空");
            return null;
        }
        if(userAccount.length() < 4){
            log.info("账号长度少于四位，登录失败");
            return null;
        }
        String regex = "^[a-zA-z0-9_]+$";
        Matcher matcher = Pattern.compile(regex).matcher(userAccount);

        if(!matcher.find()){
            log.info("账号包含特殊字符,登录失败");
            return null;
        }

        if(userPassword.length() <8){
            log.info("账号密码长度过短，请重新设置");
            return null;
        }
        // 密码加密校验
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        User user = userMapper.selectUser(userAccount, encryptPassword);

        // 判断user是否为空
        if(user == null){
            log.info("查询的用户信息:{}",user);
            log.info("账号不存在，请注册账号!");
            return null;
        }

        // 记录用户登录状态
        request.getSession().setAttribute(UserConStant.USER_LOGIN_STATE,user);



        return user;
    }

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public List<User> search(String userName,HttpServletRequest request){
        if (userName == null) {
            log.info("userName is NOT");
            return new ArrayList<>();
        }
        List<User> userList = userMapper.searchAllByuserName(userName);
        return userList;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    public String deleteById(Integer id,HttpServletRequest request) {

        if(id < 0){
            return "ID_ERROR";
        }

        int result = userMapper.deleteById(id);
        if(result < 0 ){
            log.info("The data entry does not exist and cannot be deleted");
            return "ERROR_DELETE";
        }
        return "The logical deletion was successful.";
    }
}




