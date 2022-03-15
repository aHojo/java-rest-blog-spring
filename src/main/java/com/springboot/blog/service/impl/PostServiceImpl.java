package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private PostRepository postRepository;

  @Autowired
  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public PostDto createPost(PostDto postDto) {
    // Convert DTO to entity
//    Post post = new Post();
//    post.setTitle(postDto.getTitle());
//    post.setDescription(postDto.getDescription());
//    post.setContent(postDto.getContent());
    Post post = mapToEntity(postDto);
    Post newPost = postRepository.save(post);

    // convert entity to DTO
//    PostDto postResponse = new PostDto();
//    postResponse.setId(newPost.getId());
//    postResponse.setTitle(postDto.getTitle());
//    postResponse.setDescription(postDto.getDescription());
//    postResponse.setContent(postDto.getContent());
    PostDto postResponse = mapToDTO(post);

    return postResponse;
  }

  @Override
//  public List<PostDto> getAllPosts(int pageNo, int pageSize) {
  public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
  // Create pageable instance
//    Pageable pageable = PageRequest.of(pageNo, pageSize);
    Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
        : Sort.by(sortBy).descending();
    System.out.println("SORTING ======***** " + sort + "*******========");
//    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


//    List<Post> posts = postRepository.findAll();
    Page<Post> posts = postRepository.findAll(pageable);

    // get content for page object
    List<Post> listOfPosts = posts.getContent();

//    return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//    return listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    PostResponse postResponse = new PostResponse();
    postResponse.setContent(content);
    postResponse.setPageNo(posts.getNumber());
    postResponse.setPageSize(posts.getSize());
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLast(posts.isLast());

    return postResponse;
  }

  @Override
  public PostDto getPostById(long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    return mapToDTO(post);
  }

  @Override
  public PostDto updatePost(PostDto postDto, long id) {
    // get post by id from the database
    // if id does not exist throught exception
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());

    Post updatedPost = postRepository.save(post);

    return mapToDTO(updatedPost);


  }

  @Override
  public void deletePostById(long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    postRepository.delete(post);


  }

  //Convert entity to Dto
  private PostDto mapToDTO(Post post) {
    PostDto postDto = new PostDto();

    postDto.setId(post.getId());
    postDto.setTitle(post.getTitle());
    postDto.setDescription(post.getDescription());
    postDto.setContent(post.getContent());

    return postDto;
  }

  // Convert DTO to entity
  private Post mapToEntity(PostDto postDto) {

    Post post = new Post();
    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());
    return post;
  }
}
