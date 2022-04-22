package com.lina.springmagazine.controller;

import com.lina.springmagazine.dto.PostDto;
import com.lina.springmagazine.model.Posts;
import com.lina.springmagazine.model.ResponseMsg;
import com.lina.springmagazine.model.Users;
import com.lina.springmagazine.repository.PostRepository;
import com.lina.springmagazine.repository.UserRepository;
import com.lina.springmagazine.security.UserImpl;
import com.lina.springmagazine.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/api/posts")
    public ResponseEntity writeBoard(
            @RequestParam(value="images", required=false) MultipartFile file,
            @RequestParam(value="postTitle") String postTitle,
            @RequestParam(value="postContents") String postContents,
            HttpServletRequest request,
            @AuthenticationPrincipal UserImpl userImpl  //Users -> User -> UserImpl
    ) throws Exception {
        Users userInfo;
        if(userImpl != null){
            System.out.println("Nick:" + userImpl.getUsername());  //null->수정
            userInfo = userRepository.findByNickname(userImpl.getUsername()).orElseThrow(
                    () -> new NullPointerException("해당 닉네임이 존재하지 않습니다."));
        }else{
            System.out.println("userImpl is null");
            //userInfo = userRepository.findById(Long.valueOf(1)).orElse(null);
        }

        //Posts post = new Posts(post_title, post_contents, userInfo);  //user번호가 들어감, 다이어그램 화살표는 생김
        Posts post = new Posts(postTitle, postContents, userImpl.getUsername()); // ARC툴에서 로그인, 글쓰기하면 user가 null

        try {
            if (!file.isEmpty()){
                post.setImages(file.getOriginalFilename());
                // 경로 지정
                //String path = "C:\\Users\\sesan\\IdeaProjects\\spring-magazine\\src\\main\\resources\\static\\images\\";
                //String path = request.getSession().getServletContext().getRealPath("/") + "resources/static/images/";  //톰캣경로..
                //String path = "/home/ubuntu/images/"; //AWS fail
                String path = System.getProperty("user.home");
                File file_ = new File(path + "/images/" + file.getOriginalFilename());
                if(!file_.exists()){
                    file_.mkdirs();
                }
                //file.transferTo(new File(path + File.separator + file.getOriginalFilename()));  //+ 현재날짜?
                file.transferTo(file_);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("{'result':'fail'}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        postRepository.save(post);
        return new ResponseEntity("{'result':'success'}", HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<Posts> getBoardList(){
        //return postRepository.findAll(Sort.by(Sort.Direction.DESC, "created_at"));
        return postRepository.findAllByOrderByCreatedAtDesc();
        //return postRepository.findAll();
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    //public Posts getBoard(@PathVariable Long id){
    public ResponseEntity getBoard(@PathVariable Long id){
        if (postService.isExisted(id)){
            Posts post = postService.getBoard(id);
            return new ResponseEntity(post, HttpStatus.OK);
        }else{
            return new ResponseEntity(new ResponseMsg("fail", "존재하지 않는 글"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/posts/{id}")
    public Long editBoard(@PathVariable Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public Long delBoard(@PathVariable Long id){
        postRepository.deleteById(id);
        return id;

    }

}
