package com.xtf.userscenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtf.userscenter.constant.UserConStant;
import com.xtf.userscenter.mapper.UserMapper;
import com.xtf.userscenter.pojo.entity.User;
import com.xtf.userscenter.pojo.dto.UserLoginDTO;
import com.xtf.userscenter.pojo.dto.UserRegisterDTO;
import com.xtf.userscenter.service.UserService;
import com.xtf.userscenter.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    @PostMapping("/register")
    public Result<String> userRegister(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册",userRegisterDTO);
        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();

        String info = userService.Register(userAccount, userPassword, checkPassword);
        return Result.success(info);
    }

    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        log.info("账号登录信息:{}",userLoginDTO);
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();


        User user = userService.userLogin(userAccount, userPassword, request);
        return Result.success(user);
    }

    @GetMapping("/search")
    public Result<List<User>> search(@RequestBody String userName,HttpServletRequest request){
        log.info("根据用户名查询信息：{}",userName);

        Object obj = request.getSession().getAttribute(UserConStant.USER_LOGIN_STATE);
        User user = (User) obj;

        if(user == null || user.getUserRole() != UserConStant.ENABLE){
            log.info("The account lacks permission");
            return Result.error("ERROR_NOT_LOGIN");
        }

        if(StringUtils.isNoneBlank(userName)){
            log.info("查询数据");
        }
        List<User> userList = userMapper.searchAllByuserName(user.getUserName());
        return Result.success(userList);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id,HttpServletRequest request){
        log.info("根据id删除账号信息",id);
        Object obj = request.getSession().getAttribute(UserConStant.USER_LOGIN_STATE);
        User user = (User) obj;

        if(user == null || user.getUserRole() != UserConStant.ENABLE){
            log.info("The account lacks permission");
            return Result.error("ERROR_NOT");
        }

        String info = userService.deleteById(id, request);
        return Result.error(info);
    }


}
