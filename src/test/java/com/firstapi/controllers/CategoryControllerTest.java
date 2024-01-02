package com.firstapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapi.LearningApiApplication;
import com.firstapi.config;
import com.firstapi.payloads.CategoryDto;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.security.JwtTokenHelper;
import com.firstapi.services.CategoryService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {config.class, LearningApiApplication.class
})
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private RoleRepo roleRepo;

    @MockBean
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;
    private CategoryDto categoryDto1, categoryDto2;
    private Logger logger = LoggerFactory.getLogger(CategoryControllerTest.class);
    List<CategoryDto> categoryDtoList;
    @BeforeEach
    void setUp(){
        categoryDto1 = CategoryDto.builder().id(1).categoryTitle("Framework").categoryDescription("knowledge about frameworks").build();
        categoryDto2 = CategoryDto.builder().id(2).categoryTitle("Testing").categoryDescription("Knowledge about testing").build();
        categoryDtoList = Arrays.asList(categoryDto1,categoryDto2);
    }
    @Test
    void createCategory() throws Exception {
    given(categoryService.createCategory(ArgumentMatchers.any())).willAnswer(invocation -> {
        Object[] args = invocation.getArguments();
        logger.info("{}",args);
        return args[0];

    });

        ResultActions resultActions = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getAllCategory() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(categoryDtoList);

        ResultActions resultActions = mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getById() throws Exception {
        when(categoryService.getById(1)).thenReturn(categoryDto1);

        ResultActions resultActions = mockMvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTitle", CoreMatchers.is(categoryDto1.getCategoryTitle())));
    }

    @Test
    void deleteCategory() throws Exception {
        willDoNothing().given(categoryService).deleteCategory(1);
        ResultActions resultActions = mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.categoryTitle").isEmpty())
                .andDo(print());
    }

    @Test
    void updatedCategories() throws Exception {
        given(categoryService.updateCategory(any(),eq(1))).willAnswer(invocation ->
        {
            Object[] args = invocation.getArguments();
            return args[0];
        });

        ResultActions resultActions = mockMvc.perform(put("/api/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto2)))
                .andDo(print())
                .andExpect(status().isOk());

    }
}