package com.firstapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.payloads.PostDto;
import com.firstapi.payloads.PostResponse;
import com.firstapi.payloads.UserDto;
import com.firstapi.services.PostService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private PostService postService;
   // private PostController postController;
    @Autowired
    private MockMvc mockMvc;
    private PostDto postDto1, postDto2;
    private Logger logger = LoggerFactory.getLogger(PostControllerTest.class);
    List<PostDto> postDtoList;
    @BeforeEach
    void setUp(){
        postDto1 = PostDto.builder().id(1).postTitle("spring new version").addedDate(new Date()).user(new UserDto()).category(new CategoryDto()).build();
        postDto2 = PostDto.builder().id(2).postTitle("Using Lambda ").addedDate(new Date()).user(new UserDto()).category(new CategoryDto()).build();
       postDtoList = Arrays.asList(postDto1,postDto2);
    }

    @Test
    void savePost() throws Exception {


        given(postService.create(ArgumentMatchers.any(),eq(1),eq(1))).willAnswer(invocation ->{
            Object[] args = invocation.getArguments();
            logger.info("Object invocation : {}",args);
            return args[0];
        });
        ResultActions resultActions = mockMvc.perform(post("/api/user/1/category/1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postDto1)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("postTitle", CoreMatchers.is(postDto1.getPostTitle())));
    }

    @Test
    void getPostsByUser() throws Exception {
        when(postService.getByUser(1)).thenReturn(postDtoList);

        ResultActions resultActions = mockMvc.perform(get("/api/user/1/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(postDtoList.size())));

    }

    @Test
    void getPostsByCategory() throws Exception {
        when(postService.getByCategory(1)).thenReturn(postDtoList);

        ResultActions resultActions = mockMvc.perform(get("/api/category/1/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(postDtoList.size())));
    }

    @Test
    void getPostById() throws Exception {
        when(postService.getById(1)).thenReturn(postDto1);

        ResultActions resultActions = mockMvc.perform(get("/api/post/1")
                .contentType(MediaType.APPLICATION_JSON))
               // .content(mapper.writeValueAsString(postDto1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.postTitle",CoreMatchers.is(postDto1.getPostTitle())));

    }

    @Test
    void getAllPosts() throws Exception {
        PostResponse response = PostResponse.builder().pageSize(1).lastPages(true).pageNumber(1).totalPages(1).content(postDtoList).build();
        when(postService.getAllPost(1,1,"postTitle")).thenReturn(response);
        ResultActions resultActions = mockMvc.perform(get("/api/posts")
                .param("pageNumber","1")
                .param("pageSize","1")
                .param("sortBy","postTitle"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",CoreMatchers.is(response.getContent().size())));
    }

    @Test
    void deleteById() throws Exception {
        willDoNothing().given(postService).deletePost(1);

        ResultActions resultActions = mockMvc.perform(delete("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updatedPost() throws Exception {
        given(postService.updatePost(ArgumentMatchers.any(),eq(1))).willAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            logger.info("Object : {}",args);
            return args[0];
        });

        ResultActions resultActions = mockMvc.perform(put("/api/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postDto2)))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.jsonPath("$.postTitle",CoreMatchers.is(postDto2.getPostTitle())),
                        MockMvcResultMatchers.jsonPath("$.id",CoreMatchers.is(postDto2.getId())));
    }

    @Test
    void searchPostByTitle() throws Exception {
        when(postService.searchPost(ArgumentMatchers.anyString())).thenReturn(postDtoList);

        ResultActions response  = mockMvc.perform(get("/api/post/search/lambda")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(postDtoList.size())));

    }
}