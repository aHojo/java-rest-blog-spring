package com.springboot.blog.payload;

import lombok.Data;

// @Data Implements all setters, getters, etc.
@Data
public class PostDto {
  private Long id;
  private String title;
  private String description;
  private String content;
}
