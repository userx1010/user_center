package com.xtf.userscenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.xtf.userscenter.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User selectUser(String userAccount, String userPassword);

    public User getUserName(String userAccount);

    public List<User> searchAllByuserName(String userName);
}
