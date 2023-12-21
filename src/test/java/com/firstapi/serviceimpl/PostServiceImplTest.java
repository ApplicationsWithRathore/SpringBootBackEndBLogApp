package com.firstapi.serviceimpl;

import com.firstapi.entity.Category;
import com.firstapi.entity.Post;
import com.firstapi.entity.User;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.payloads.PostDto;
import com.firstapi.payloads.PostResponse;
import com.firstapi.payloads.UserDto;
import com.firstapi.repositories.CategoryRepo;
import com.firstapi.repositories.PostRepo;
import com.firstapi.repositories.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepo postRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PostServiceImpl postService;

    Logger logger = LoggerFactory.getLogger(PostServiceImplTest.class);
    private Post post1,post2;
    private PostDto postDto1,postDto2;
    @BeforeEach
    void setUp(){
        post1 = Post.builder().postId(1).postTitle("Spring").postContent("Spring new update").build();
        post2 = Post.builder().postId(2).postTitle("Java").postContent("Java latest version is now used").build();

        postDto1 = PostDto.builder().id(1).postTitle("Spring").postContent("Spring new update").build();
        postDto2 = PostDto.builder().id(2).postTitle("Java").postContent("Java latest version is now used").build();
    }

    @Test
    void create() {
        //Arrange

        User user = User.builder().id(1).name("Josh").build();
        UserDto userDto = UserDto.builder().id(1).name("Josh").build();

        Category category = Category.builder().id(1).categoryTitle("Testing").build();


        post1.setUser(user);
        postDto1.setUser(userDto);

        postRepo.save(post1);

        categoryRepo.save(category);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(modelMapper.map(postDto1,Post.class)).thenReturn(post1);
        when(modelMapper.map(post1,PostDto.class)).thenReturn(postDto1);
        when(postRepo.save(post1)).thenReturn(post1);

        PostDto savedPostDto = postService.create(postDto1,1,1);

        logger.info("{}",savedPostDto);
        assertThat(savedPostDto).isNotNull();
        assertThat(savedPostDto).isEqualTo(postDto1);


    }

    @Test
    void updatePost() {

        when(postRepo.findById(1)).thenReturn(Optional.of(post1));
        when(postRepo.save(post1)).thenReturn(post1);
      //  when(postService.dtoToPost(postDto1)).thenReturn(post);
        when(postService.postTODto(post1)).thenReturn(postDto2);
        PostDto updated = postService.updatePost(postDto2,1);

        logger.info("{}",updated);
        assertThat(updated).isNotNull();
        assertThat(updated).isEqualTo(postDto2);
      //  assertThat(updated).hasFieldOrPropertyWithValue("postContent","Developer need to make habit of writing tests");
        assertThat(updated.getPostTitle()).isEqualTo("Java");

    }

    @Test
    void deletePost() {
      //  List<Post> posts = Arrays.asList(post1,post2);
        when(postRepo.findById(1)).thenReturn(Optional.of(post1));

        assertAll(()->postService.deletePost(1));

        verify(postRepo , times(1)).delete(post1);
      //  assertThat(posts.size()).isEqualTo(1);

    }

    @Test
    void getAllPost() {
        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "postTitle";

        List<Post> posts = Arrays.asList(post1,post2);

        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
        Page<Post> mockPage = mock(Page.class);

        when(postRepo.findAll(pageable)).thenReturn(mockPage);
        when(mockPage.getContent()).thenReturn(posts);
        when(mockPage.getNumber()).thenReturn(pageNumber);
        when(mockPage.getSize()).thenReturn(pageSize);
        when(mockPage.getTotalPages()).thenReturn(1);
        when(mockPage.isLast()).thenReturn(true);
        when(mockPage.getTotalElements()).thenReturn((long) posts.size());
        when(postService.postTODto(post1)).thenReturn(postDto1);
        when(postService.postTODto(post2)).thenReturn(postDto2);

        PostResponse postResponse = postService.getAllPost(pageNumber,pageSize,sortBy);
        logger.info("{}",postResponse);

        //assertThat(postResponse.getPageSize()).isEqualTo(10);

        verify(postRepo).findAll(pageable);

        assertThat(postResponse.getTotalElements()).isEqualTo(2L);

    }

    @Test
    void getById() {
    when(postRepo.findById(1)).thenReturn(Optional.of(post1));
    when(postService.postTODto(post1)).thenReturn(postDto1);

    PostDto postDto = postService.getById(1);

    assertThat(postDto).isNotNull();
    assertThat(postDto).isEqualTo(modelMapper.map(post1,PostDto.class));
    assertThat(postDto.getId()).isEqualTo(1);

    }

    @Test
    void getByCategory() {
        Category category = Category.builder().id(1).categoryTitle("Software").build();
        CategoryDto category1 = CategoryDto.builder().id(1).categoryTitle("Software").build();
        //post2.setCategory(category);
        postDto1.setCategory(category1);
        postRepo.save(post1);

        List<Post> posts = Collections.singletonList(post1);
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(postRepo.findByCategory(category)).thenReturn(posts);
        when(postService.postTODto(post1)).thenReturn(postDto1);

        List<PostDto> postDtoList = postService.getByCategory(1);
        logger.info("{}",postDtoList);

        verify(postRepo).findByCategory(category);
        assertThat(postDtoList).hasSize(1);
        assertThat(postDtoList.get(0)).isEqualTo(postDto1);
    }

    @Test
    void getByUser() {
        User user = User.builder().id(1).name("Dan").build();
        UserDto userDto = UserDto.builder().id(1).name("Dan").build();
        post1.setUser(user);
        post2.setUser(user);

        List<Post> posts= Arrays.asList(post1,post2);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(postRepo.findByUser(user)).thenReturn(posts);
        when(postService.postTODto(post2)).thenReturn(postDto2);
        when(postService.postTODto(post1)).thenReturn(postDto1);

        List<PostDto> postDtoList = postService.getByUser(1);
        logger.info("{}",postDtoList);

        assertThat(postDtoList).hasSize(2);
        assertThat(postDtoList.get(0)).isEqualTo(postDto1);
        assertNotNull(postDtoList);
        assertThat(postDtoList).isNotEmpty();

    }

    @Test
    void searchPost() {
        Post post = Post.builder().postId(1).postTitle("Sports").postContent("About cricket").build();
        Post post1 = Post.builder().postId(2).postTitle("Software").postContent("unit testing").build();
        Post post2 = Post.builder().postId(3).postContent("Software").postContent("Developing android apps").build();
       List<Post> postList =  Arrays.asList(post,post1,post2);
        List<Post> same = Arrays.asList(post1,post2);

        PostDto postDto = PostDto.builder().id(1).postTitle("Sports").postContent("About cricket").build();
        PostDto postDto1 = PostDto.builder().id(2).postTitle("Software").postContent("unit testing").build();
        PostDto postDto2 = PostDto.builder().id(3).postContent("Software").postContent("Developing android apps").build();

        when(postRepo.findByPostTitleContaining("Software")).thenReturn(same);
        when(postService.postTODto(post1)).thenReturn(postDto1);
        when(postService.postTODto(post2)).thenReturn(postDto2);

        List<PostDto> postDtoList = postService.searchPost("Software");
        logger.info("{}",postDtoList);

        assertThat(postDtoList).isNotEmpty();
    }
    @AfterEach
    void destroy(){

        postRepo.delete(post1);
        postRepo.delete(post2);
        post1 = new Post();
        post2 = new Post();

    }
}