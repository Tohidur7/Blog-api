package com.myblog.Blogapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class PostDto {
    private Long id;

    @NotEmpty @Size(min = 2, message ="title should have at least 2 characters")
    private String title ;

    @NotEmpty @Size(min = 5, message ="description should have at least 5 characters")
    private String description ;

    @NotEmpty @Size(min = 10, message ="content should be at least 10 characters")
    private String content ;

    private Set<CommentDto> commentDtoSet ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDto> getCommentDtoSet() {
        return commentDtoSet;
    }
}
