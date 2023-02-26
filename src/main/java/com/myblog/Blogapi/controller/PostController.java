package com.myblog.Blogapi.controller;

import com.myblog.Blogapi.payload.PostDto;
import com.myblog.Blogapi.payload.PostResponse;
import com.myblog.Blogapi.service.PostService;
import com.myblog.Blogapi.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create post rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all post rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {

        return postService.getAllPosts(pageNo,pageSize, sortBy, sortDir);
    }

    //get post by id rest api
    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by id rest api
    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {

        PostDto postDtoResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
    }

    //delete post by id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);

        return new ResponseEntity<>("post entity is deleted successfully", HttpStatus.OK);
    }
}
