package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository -- don't need to add this because JpaRepository implements another interface that has this annotation
// Long is the second arg to the generic because it's the primary ID of Post
public interface PostRepository extends JpaRepository<Post, Long> {
    // No need to write any code here, jpa has all of the methods that we need.

}
