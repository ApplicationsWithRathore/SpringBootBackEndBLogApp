package com.firstapi.controllers;

import com.firstapi.exceptionhandler.ApiException;
import com.firstapi.payloads.JwtAuthRequest;
import com.firstapi.payloads.JwtAuthResponse;
import com.firstapi.payloads.UserDto;
import com.firstapi.security.JwtTokenHelper;
import com.firstapi.serviceimpl.UserServiceImpl;
import com.firstapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
       String token =  this.jwtTokenHelper.generateToken(userDetails);
       JwtAuthResponse response = new JwtAuthResponse();
       response.setToken(token);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password)  {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        }catch (BadCredentialsException e){
           // System.out.println("Invalid Details");
           throw new ApiException("Invalid Username or Password");
           // throw new Exception("Invalid Username or Password !!");
        }

    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUsers(@RequestBody UserDto userDto){
        UserDto registeredUSer = this.userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUSer,HttpStatus.CREATED);
    }
}
