package com.firstapi.repositories;

import com.firstapi.LearningApiApplication;
import com.firstapi.config;
import com.firstapi.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {
@Autowired
    private UserRepo userRepo;
        private Logger logger = LoggerFactory.getLogger(UserRepoTest.class);
    @Test
    void findByEmail() {
        User user = User.builder().id(1).email("User12@gmail.com").build();
            userRepo.save(user);
        Optional<User> user1 = userRepo.findByEmail("User12@gmail.com");

            logger.info("{}",user1);
        assertThat(user1).isNotEmpty();
    }

}