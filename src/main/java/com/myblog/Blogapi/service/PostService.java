package com.myblog.Blogapi.service;

import com.myblog.Blogapi.payload.PostDto;
import com.myblog.Blogapi.payload.PostResponse;


public interface PostService {
     PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById( long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
