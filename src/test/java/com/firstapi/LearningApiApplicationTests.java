package com.firstapi;

import com.firstapi.repositories.RoleRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest

class LearningApiApplicationTests {

	@Test
	void contextLoads() {
	}
	/*@MockBean
	private RoleRepo roleRepo;
	@MockBean
	private PasswordEncoder passwordEncoder;
*/
}
