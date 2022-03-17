package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service means we can create this as a spring bean and inject it to other classes
@Service
public class CommentServiceImpl implements CommentService {

  private CommentRepository commentRepository;
  private PostRepository postRepository;


  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  @Override
  public CommentDto createComment(long postId, CommentDto commentDto) {

    Comment comment = mapToEntity(commentDto);

    // retrieve post entity by id;
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    //set post to comment entity;
    comment.setPost(post);

    //save comment entity to database
    Comment newComment = commentRepository.save(comment);


    return mapToDTO(newComment);
  }

  @Override
  public List<CommentDto> getCommentsByPostId(long postId) {
    // retrieve comments by postId
    List<Comment> comments = commentRepository.findByPostId(postId);

    // Convert list of comments to DTO
    return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
  }

  private CommentDto mapToDTO(Comment comment) {
    CommentDto commentDto = new CommentDto();
    commentDto.setId(comment.getId());
    commentDto.setName(comment.getName());
    commentDto.setEmail(comment.getEmail());
    commentDto.setBody(comment.getBody());

    return commentDto;
  }

  private Comment mapToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());

    return comment;
  }
 }
