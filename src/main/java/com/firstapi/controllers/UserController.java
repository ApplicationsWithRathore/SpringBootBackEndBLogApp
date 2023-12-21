package com.firstapi.controllers;

import com.firstapi.payloads.UserDto;
import com.firstapi.services.UserService;
import jakarta.validation.Valid;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable Integer id){
        UserDto updatedDto = this.userService.updateUser(userDto ,id);
        return  ResponseEntity.ok(updatedDto);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Integer id){
      this.userService.deleteUser(id);
      return new ResponseEntity<>(Map.of("Status","User Deleted Successfully"),HttpStatus.OK);
    }
      //  @PreAuthorize("hasRole('ADMIN_USER')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }
}
