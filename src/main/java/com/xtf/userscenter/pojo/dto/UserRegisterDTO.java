package com.xtf.userscenter.pojo.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
