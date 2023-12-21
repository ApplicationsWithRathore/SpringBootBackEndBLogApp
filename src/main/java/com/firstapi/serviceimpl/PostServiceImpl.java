package com.firstapi.serviceimpl;
import com.firstapi.entity.Category;
import com.firstapi.entity.Post;
import com.firstapi.entity.User;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.payloads.PostDto;
import com.firstapi.payloads.PostResponse;
import com.firstapi.repositories.CategoryRepo;
import com.firstapi.repositories.PostRepo;
import com.firstapi.repositories.UserRepo;
import com.firstapi.services.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
   private PostRepo postRepo;
   private ModelMapper modelMapper;
   private UserRepo userRepo;
   private CategoryRepo categoryRepo;
    @Override
    public PostDto create(PostDto postDto,Integer userId,Integer categoryId) {
       User user = this.userRepo.findById(userId)
               .orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
       Category category = this.categoryRepo.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        Post post = this.dtoToPost(postDto);
        post.setImgName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post updated = this.postRepo.save(post);
        return this.postTODto(updated);
    }

    @Override
    public PostDto updatePost(PostDto postDto,Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
            post.setPostTitle(postDto.getPostTitle());
            post.setPostContent(postDto.getPostContent());
            Post updatedPost = this.postRepo.save(post);
        return this.postTODto(updatedPost);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {

        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagePosts = this.postRepo.findAll(p);
        List<Post> post = pagePosts.getContent();
        List<PostDto> postDto = post.stream().map(this::postTODto).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLastPages(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getById(Integer id) {
        Post post = this.postRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","PostId",id));

        return this.postTODto(post);
    }

    @Override
    public List<PostDto> getByCategory(Integer categoryId) {
       Category category = this.categoryRepo.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
      List<Post> post= this.postRepo.findByCategory(category);
     List<PostDto> postDto = post.stream().map(this::postTODto).collect(Collectors.toList());
      return postDto;
    }

    @Override
    public List<PostDto> getByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        List<Post> posts = this.postRepo.findByUser(user);

        return posts.stream().map(this::postTODto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
       List<Post> post = this.postRepo.findByPostTitleContaining(keyword);
        List<PostDto> posts = post.stream().map(this::postTODto).toList();
        return posts;
    }
    public PostDto postTODto(Post post){
        return this.modelMapper.map(post, PostDto.class);
    }

    public Post dtoToPost(PostDto postDto){
        return  this.modelMapper.map(postDto,Post.class);
    }
}
