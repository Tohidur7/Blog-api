package com.myblog.Blogapi.service.impl;

import com.myblog.Blogapi.entity.Comment;
import com.myblog.Blogapi.entity.Post;
import com.myblog.Blogapi.exception.BlogApiException;
import com.myblog.Blogapi.exception.ResourceNotFoundException;
import com.myblog.Blogapi.payload.CommentDto;
import com.myblog.Blogapi.repository.CommentRepo;
import com.myblog.Blogapi.repository.PostRepo;
import com.myblog.Blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepo commentRepo;
    private PostRepo postRepo ;
    private ModelMapper modelMapper ;

    public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper ;
    }



    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        //set the post to comment entity
        comment.setPost(post);

        // save comment entity to the database
        Comment commentResponse = commentRepo.save(comment);

        return mapToDto(commentResponse);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        //retrieve comments by postId
        List<Comment> commentList = commentRepo.findByPostId(postId) ;

        //converts the list of comment entity to list of comment dto
        return commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        //retrieve post by id
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        // retrieve comment by comment id
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId) ) ;


        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }


        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        //retrieve post by id
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        // retrieve comment by comment id
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId) ) ;


        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }

        //update details
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);

        return  mapToDto(updatedComment) ;
    }

    @Override
    public void deleteComment(long postId, long commentId) {

        //retrieve post by id
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

        // retrieve comment by comment id
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId) ) ;


        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }

        commentRepo.delete(comment);

    }


    //convert ENTITY INTO DTO
    private CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    // CONVERT DTO INTO ENTITY
    private Comment mapToEntity(CommentDto commentDto) {

        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;

    }
}
