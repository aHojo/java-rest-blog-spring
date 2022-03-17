package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {
  private long id;
  // Not adding @Column means that the names of the vars will be the column names;
  private String name;
  private String email;
  private String body;

}
