package com.lina.springmagazine.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String user_id;
    private String email;
    private String nickname;
    private String password;
}
