package com.lina.springmagazine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String post_title;
    private String post_contents;
    private String nickname;
    private String images;
//    private Long likes;
//    private Long views;

    public PostDto(String post_title, String post_contents, String nickname){
        this.post_title = post_title;
        this.post_contents = post_contents;
        this.nickname = nickname;
    }
}
