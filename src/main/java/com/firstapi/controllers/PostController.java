package com.firstapi.controllers;

import com.firstapi.entity.Post;
import com.firstapi.payloads.ApiResponse;
import com.firstapi.payloads.PostDto;
import com.firstapi.payloads.PostResponse;
import com.firstapi.services.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto , @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createPost = this.postService.create(postDto,userId,categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
     List<PostDto> postDto =   this.postService.getByUser(userId);
     return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDto =   this.postService.getByCategory(categoryId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy
    ){
      // List<PostDto> postDto =  this.postService.getAllPost(pageNumber,pageSize);
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }
    @PutMapping("post/{postId}")
    public ResponseEntity<PostDto> updatedPost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDtos = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
    @GetMapping("post/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> postDtos = this.postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
}
