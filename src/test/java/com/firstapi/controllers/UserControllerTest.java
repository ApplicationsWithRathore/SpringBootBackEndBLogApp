package com.firstapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapi.LearningApiApplication;
import com.firstapi.config;
import com.firstapi.payloads.UserDto;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.security.JwtTokenHelper;
import com.firstapi.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {config.class, LearningApiApplication.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleRepo roleRepo;
    @MockBean
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserServiceImpl userService;

    UserDto userDto , userDto1;

    @BeforeEach
            void setUp(){
        userDto = UserDto.builder().name("Jack").about("Software dev").email("Jack@gmail.com").password("Jack90").build();
        userDto1 = UserDto.builder().name("Jack").about("Java dev").email("Jack@gmail.com").password("Jack90").build();
    }





    @Test
    void updateUser() throws Exception {

        when(userService.registerUser(any(UserDto.class))).thenReturn(userDto1);


        mockMvc.perform(put("/api/user/1")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        willDoNothing().given(userService).deleteUser(1);

        mockMvc.perform(delete("/api/user/1")).andExpect(status().isOk()).andDo(print());


    }

    @Test
    void getAllUser() throws Exception {
        List<UserDto> userDtoList = Arrays.asList(userDto,userDto1);

        when(userService.getAllUser()).thenReturn(userDtoList);

        mockMvc.perform(get("/api/user")).andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(userService.getUserById(1)).thenReturn(userDto1);

        mockMvc.perform(get("/api/user/1")).andExpect(status().isOk()).andDo(print());
    }
}