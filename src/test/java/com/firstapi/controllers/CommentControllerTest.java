package com.firstapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapi.payloads.CommentDto;
import com.firstapi.services.CommentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    private CommentDto commentDto1, commentDto2;
    private List<CommentDto> commentDtoList;
    @BeforeEach
    void setUp() {
        commentDto1 = CommentDto.builder().content("Nice").build();
        commentDto2 = CommentDto.builder().content("Thanks").build();

        commentDtoList = Arrays.asList(commentDto1,commentDto2);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void create() throws Exception {
        given(commentService.createComment(ArgumentMatchers.any(),eq(1))).willAnswer(invocation -> {
            Object[] args = invocation.getArguments();
          return   args[0];
        });

        ResultActions response = mockMvc.perform(post("/api/post/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDto1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateComment() throws Exception {
        given(commentService.updateComment(any(),eq(1))).willAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });
        ResultActions resultActions = mockMvc.perform(put("/api/comment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDto2)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content"
                        , CoreMatchers.is(commentDto2.getContent())));

    }

    @Test
    void deleted() throws Exception {
        willDoNothing().given(commentService).deleteComment(1);

        ResultActions response  = mockMvc.perform(delete("/api/comment/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}