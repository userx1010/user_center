package com.xtf.userscenter.pojo.vo;

import lombok.Data;

@Data
public class UserRegisterVO {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
