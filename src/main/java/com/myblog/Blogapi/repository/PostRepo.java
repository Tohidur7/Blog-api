package com.myblog.Blogapi.repository;

import com.myblog.Blogapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
