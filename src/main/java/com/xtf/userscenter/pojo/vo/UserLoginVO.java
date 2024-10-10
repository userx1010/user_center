package com.xtf.userscenter.pojo.vo;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class UserLoginVO {
    private String userAccount;
    private String userPassword;
}
