package com.firstapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapi.LearningApiApplication;

import com.firstapi.config;
import com.firstapi.entity.User;
import com.firstapi.payloads.JwtAuthRequest;
import com.firstapi.payloads.RoleDto;
import com.firstapi.payloads.UserDto;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.repositories.UserRepo;
import com.firstapi.security.JwtAuthenticationEntryPoint;
import com.firstapi.security.JwtAuthenticationFilter;
import com.firstapi.security.JwtTokenHelper;
import com.firstapi.serviceimpl.UserServiceImpl;
import com.firstapi.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)

//@SpringBootTest(classes = {SecurityConfig.class})
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = config.class)
class AuthControllerTest {
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
        UserServiceImpl userService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtTokenHelper jwtTokenHelper;
    @MockBean
    RoleRepo roleRepo;
    @MockBean
    UserRepo userRepo;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void createToken() {

    }

    @Test
    void registerUsers() throws Exception {
        RoleDto roleDto = new RoleDto(1,"ADMIN");
        RoleDto roleDto1 = new RoleDto(2,"USER");
        UserDto userDto = UserDto.builder().name("DanVega").email("DanVega@gmail.com").password(passwordEncoder.encode("DanVega")).about("Java").roles(Set.of(roleDto1)).build();
       /* userDto.setName("DanVega");
        userDto.setAbout("Java ");
        userDto.setEmail("DanVega@gmail.com");
        userDto.setPassword(passwordEncoder.encode("DanVega"));
       userDto.setRoles(Set.of(roleDto1));
        User user = modelMapper.map(userDto,User.class);
            userRepo.save(user);*/
    //   when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);
      when(userService.registerUser(any(UserDto.class))).thenReturn(userDto);

        /*given(userService.registerUser(ArgumentMatchers.any())).willAnswer(invocation->{
            Object[] args = invocation.getArguments();
            return args[0];
        });
*/
      MvcResult mockMvcResultMatchers = mockMvc.perform(post("/api/v1/auth/register")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(userDto))

                      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
              .andDo(print())
                .andReturn();
            String  response = mockMvcResultMatchers.getResponse().getContentAsString();
        System.out.println("Response  :" + response);


    }
}