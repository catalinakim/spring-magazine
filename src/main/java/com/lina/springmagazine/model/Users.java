package com.lina.springmagazine.model;

import com.lina.springmagazine.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
//https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html
public class Users extends TimeStamp{

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @Id
    @Column(name="user_no")
    private Long userNo; //user_no

    @Column(nullable = false, unique = true, name="user_id")
    private String username; //userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

//    @Enumerated(value = EnumType.STRING)
//    private UserRoleEnum role;

    public Users(String username, String email, String nickname, String password){
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    //public Users(UserDto userDto, UserRoleEnum role){
    public Users(UserDto userDto){
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.nickname = userDto.getNickname();
        this.password = userDto.getPassword();
        //this.role = role;
    }


}
