package com.firstapi.services;


import com.firstapi.payloads.UserDto;

import java.util.List;

public interface UserService {
   UserDto registerUser(UserDto userDto);

   UserDto updateUser(UserDto user,Integer id);

   UserDto getUserById(Integer id);
   List<UserDto> getAllUser();
   void deleteUser(Integer id);
}
