package com.firstapi.serviceimpl;

import com.firstapi.config.AppConstant;
import com.firstapi.entity.Role;
import com.firstapi.entity.User;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.payloads.UserDto;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.repositories.UserRepo;
import com.firstapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
   UserRepo uR;

  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RoleRepo roleRepo;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        Role role  = this.roleRepo.findById(AppConstant.ROLE_USER).get();
        user.setRoles(user.getRoles());
        User newUser = this.uR.save(user);

        return this.userToDto(newUser);
    }

/*    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.uR.save(user);
        return this.userToDto(savedUser);
    }*/

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = this.uR.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","UserId",id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = this.uR.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = this.uR.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","UserId",id));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> user = this.uR.findAll();
       List<UserDto> userDtos =  user.stream().map(this::userToDto).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer id) {
       User user = this.uR.findById(id)
                 .orElseThrow(()-> new ResourceNotFoundException("User","UserId",id));
            this.uR.delete(user);
    }
    public User dtoToUser(UserDto userDto){
       User user = this.modelMapper.map(userDto,User.class);
       return  user;
      /* user.setId(userDto.getId());
       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setPassword(userDto.getPassword());
       user.setAbout(userDto.getAbout());
        return user;*/
    }

    public  UserDto userToDto(User user){

        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
        /*UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        return userDto;*/
    }
}
