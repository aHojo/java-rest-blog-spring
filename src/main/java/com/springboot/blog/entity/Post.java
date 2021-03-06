package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data from Lombok creates getters and setters and a toString() method hashCode and equals too
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
    name = "posts",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private Long id;

  @Column(name="title", nullable = false, length = 255)
  private String title;

  @Column(name="description", nullable = false)
  private String description;

  @Column(name="content", nullable = false)
  private String content;

  // this makes it bidirectional -- the foreign key is set in comments by post_id
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments = new HashSet<>();

}
