package com.firstapi.security;

import com.firstapi.entity.User;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
  @Autowired
   private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      //  User user =  this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user","email : " + username,0)) ;
    //return user;
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user","email : " + username,0)) ;

        return user;
    }
}
