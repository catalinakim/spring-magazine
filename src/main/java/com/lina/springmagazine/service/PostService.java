package com.lina.springmagazine.service;

import com.lina.springmagazine.dto.PostDto;
import com.lina.springmagazine.model.Posts;
import com.lina.springmagazine.model.ResponseMsg;
import com.lina.springmagazine.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long update(Long id, PostDto postDto){
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글은 존재하지 않습니다.")
        );
        post.update(postDto);
        return id;
    }
    @Transactional
    public boolean isExisted(Long id){
        Optional<Posts> post = postRepository.findById(id);
        if(!post.isPresent()) {
            return false;
        }
        return true;
    }
    @Transactional
    public Posts getBoard(Long id){
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글은 존재하지 않습니다.")
        );
        int view = post.getViews();
        view += 1;
        post.setViews(view);
        postRepository.save(post);
        return post;
    }
}
