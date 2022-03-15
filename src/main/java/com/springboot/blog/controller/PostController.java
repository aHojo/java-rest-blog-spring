package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {


  // Interface with DTO that interfaces with entity
  private PostService postService;

  // Dependency injection
  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  // Get All Posts
  @GetMapping
//  public List<PostDto> getAllPosts(
  public PostResponse getAllPosts(
      @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
  ){


    return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
  }

  // Create blog pos rest api
  @PostMapping
  // @Request body turns the body into json
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  // get post by ID
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
      return ResponseEntity.ok(postService.getPostById(id));
  }

  // update post by id rest api
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
    PostDto postResponse = postService.updatePost(postDto, id);

    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  // delete post rest api
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
    postService.deletePostById(id);

    return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
  }

}
