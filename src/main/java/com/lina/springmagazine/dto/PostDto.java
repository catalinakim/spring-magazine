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
    private String postTitle;
    private String postContents;
    private String nickname;
    private String images;
//    private Long likes;
//    private Long views;

    public PostDto(String post_title, String post_contents, String nickname){
        this.postTitle = post_title;
        this.postContents = post_contents;
        this.nickname = nickname;
    }
}
