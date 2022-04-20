package com.lina.springmagazine.model;

import com.lina.springmagazine.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자 생성해줘
@Entity  //테이블 역할하는 클래스
@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Posts extends TimeStamp{

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ_GENERATOR")
    @Id
    private Long post_no;

    @Column(nullable = false)
    private String post_title;

    @Column(nullable = false)
    private String post_contents;

    @Column(nullable = false)
    private String nickname;  // FK

//    @ManyToOne
//    @JoinColumn(name = "nickname2")
//    private Users user;

    //@Column(nullable = false)
    private String images;

    //@Column(nullable = false)
    private int likes;

    //@Column(nullable = false)
    //private Long views; //->데이터에 null로 들어감
    private int views;

    public Posts(PostDto postDto){
//    public Posts(PostDto postDto, Users user){
        this.post_title = postDto.getPost_title();
        this.post_contents = postDto.getPost_contents();
//        this.nickname = postDto.getNickname();
//        this.user = user;
        this.likes = 0;
        this.views = 0;
    }

//    public Posts(String post_title, String post_contents,Users user){
//        this.post_title = post_title;
//        this.post_contents = post_contents;
//        this.user = user;
//    }

    public Posts(String post_title, String post_contents, String nickname){
        this.post_title = post_title;
        this.post_contents = post_contents;
        this.nickname = nickname;
//        this.user = user;
    }



    public void update(PostDto postDto){
        this.post_title = postDto.getPost_title();
        this.post_contents = postDto.getPost_contents();
        this.images = postDto.getImages();
    }

    public void setImages(String filename){
        this.images = filename;
    }


}
