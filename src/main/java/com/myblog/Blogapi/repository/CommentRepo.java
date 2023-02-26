package com.myblog.Blogapi.repository;

import com.myblog.Blogapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

   public List<Comment> findByPostId(long postId);
}
