package com.myblog.Blogapi.service.impl;

import com.myblog.Blogapi.entity.Post;
import com.myblog.Blogapi.exception.ResourceNotFoundException;
import com.myblog.Blogapi.payload.PostDto;
import com.myblog.Blogapi.payload.PostResponse;
import com.myblog.Blogapi.repository.PostRepo;
import com.myblog.Blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepo postRepo ;
    private ModelMapper modelMapper ;

    public PostServiceImpl(PostRepo postRepo , ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper ;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert dto into entity
        Post post = mapToEntity(postDto);

        Post postResponse = postRepo.save(post);

        //convert entity into dto
        PostDto postDtoResponse = mapToDto(post);


        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //ordering in sorting
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending() ;

        //create pageable instance
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, sort);

        Page<Post> allPage = postRepo.findAll(pageable);

        //get content for the page object
        List<Post> allPost = allPage.getContent();


        List<PostDto> content= allPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(allPage.getNumber());
        postResponse.setPageSize(allPage.getSize());
        postResponse.setTotalElement(allPage.getTotalElements());
        postResponse.setTotalPages(allPage.getTotalPages());
        postResponse.setLast(allPage.isLast());

        return postResponse ;
    }

    @Override
    public PostDto getPostById(long id) {

        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from the database
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);

        return mapToDto(updatedPost);

    }

    @Override
    public void deletePostById(long id) {

        //get post by id from the database
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepo.delete(post);
    }

    // convert ENTITY INTO DTO
    private PostDto mapToDto(Post post) {

        PostDto postDto = modelMapper.map(post, PostDto.class);

        return postDto;
    }

    //convert DTO INTO ENTITY
    private Post mapToEntity(PostDto postDto) {

        Post post = modelMapper.map(postDto, Post.class);

        return post;
    }
}
