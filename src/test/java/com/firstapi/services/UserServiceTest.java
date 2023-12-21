package com.firstapi.services;

import com.firstapi.AppConstants;
import com.firstapi.entity.Role;
import com.firstapi.entity.User;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.payloads.UserDto;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.repositories.UserRepo;
import com.firstapi.serviceimpl.UserServiceImpl;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeEach
    void setUp(){

          }

/* @Test
    void createUser() {

         UserDto userDtoP = new UserDto().builder().name("Jitu").email("").password("").about("").build();
         User user = new User().builder().name("Jitu").email("").password("").about("").build();

         when(modelMapper.map(userDtoP,User.class)).thenReturn(user);
         when(userRepo.save(any(User.class))).thenReturn(user);
         when(modelMapper.map(user,UserDto.class)).thenReturn(userDtoP);

        UserDto saved =  userService.createUser(userDtoP);

        logger.info("{}",saved);

         verify(modelMapper).map(userDtoP,User.class);
         verify(userRepo).save(user);
         verify(modelMapper).map(user,UserDto.class);

         assertNotNull(saved);
         assertThat(saved).isEqualTo(userDtoP);

       }
*/

    @Test
    void registerUser( ){
        User user = User.builder().id(1).name("Josh Long").email("Jitendra@gmail.com").password("Danvega").build();
        UserDto userDto = UserDto.builder().id(1).name("Josh Long").email("Jitendra@gmail.com").password("Danvega").build();
        Role role = Role.builder().rId(2).rName("USER").build();
        User newUser = User.builder().id(1).name("Josh Long").email("Jitendra@gmail.com").password("Danvega").build();
        UserDto newUserDto = UserDto.builder().id(1).name("Josh Long").email("Jitendra@gmail.com").password("Danvega").build();

        when(userService.dtoToUser(userDto)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("DanVegassji0987");
        when(roleRepo.findById(AppConstants.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepo.save(user)).thenReturn(newUser);
        when(userService.userToDto(newUser)).thenReturn(newUserDto);
        //when(userService.userToDto(user)).thenReturn(userDto);

        UserDto userDto1 = userService.registerUser(userDto);


        assertThat(userDto1).isNotNull();
        assertThat(userDto1).hasFieldOrPropertyWithValue("name","Josh Long");
    }
    @Test
    void updateUser() {
        User user = User.builder().id(1).name("Josh Long").build();
       // User updatedUser = User.builder().id(1).name("sam").build();

        UserDto  userDto = UserDto.builder().id(1).name("sam").build();
     //   UserDto updatedUserDto = UserDto.builder().id(1).name("sam").build();
     //   logger.info("User : {}",user);
        //when(modelMapper.map(userDto,User.class)).thenReturn(user);
        when(userRepo.findById(1)).thenReturn(Optional.ofNullable(user));
        when(userRepo.save(user)).thenReturn(user);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);


        UserDto userDto1 = userService.updateUser(userDto,1);
        logger.info("Updated User : {}",userDto1);

        assertThat(userDto1).isEqualTo(userDto);
        assertThat(userDto1).hasFieldOrPropertyWithValue("name","sam");
        //logger.info("User : {}",user);

    }

    @Test
    void getUserById() {
        User user = User.builder().id(1).name("Spring").build();
        UserDto userDto = UserDto.builder().id(1).name("Spring").build();

        when(userRepo.findById(1)).thenReturn(Optional.ofNullable(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        UserDto user1 = userService.getUserById(1);
        logger.info("{}",user1);

        assertThat(user1).isEqualTo(userDto);
    }

    @Test
    void getAllUser() {

        User user1 = User.builder().name("Josh Long").build();
        User user2 =  User.builder().name("Dan Vega").build();
        List<User> userList = Arrays.asList(user1,user2);

        UserDto userDto1 = UserDto.builder().name("Josh Long").build();
        UserDto userDto2 = UserDto.builder().name("Dan Vega").build();

        when(userRepo.findAll()).thenReturn(userList);
        when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        when(modelMapper.map(user2,UserDto.class)).thenReturn(userDto2);

        List<UserDto> getAllUserDto = userService.getAllUser();
        logger.info(" Users List : {}",getAllUserDto);

        verify(userRepo).findAll();
        verify(modelMapper).map(user1,UserDto.class);
        verify(modelMapper).map(user2,UserDto.class);


        assertThat(getAllUserDto).isNotEmpty();
        assertThat(getAllUserDto).hasSize(2);
        assertThat(getAllUserDto.get(1)).isEqualTo(userDto2);

    }

    @Test
    void deleteUser() {

        User user = User.builder().id(1).name("Dan").build();
        //UserDto userDto = UserDto.builder().id(1).build();

        when(userRepo.findById(2)).thenReturn(Optional.ofNullable(user));
       // UserDto userDto1 = userService.getUserById(1);
        assertAll(()-> userService.deleteUser(2));
        verify(userRepo).delete(user);
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(()->userService.deleteUser(1)).withMessage("User not found with UserId : 1");
        logger.info("{}",user);

            }
}