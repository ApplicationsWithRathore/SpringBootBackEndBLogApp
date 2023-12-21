package com.firstapi;

import com.firstapi.entity.Role;
import com.firstapi.repositories.RoleRepo;
import com.firstapi.security.JwtAuthenticationEntryPoint;
import com.firstapi.security.JwtAuthenticationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@SpringBootApplication()
public class LearningApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(LearningApiApplication.class, args);
		System.out.println("You are ready for testing your api!");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

@Override
	public void run(String... args)throws Exception{
		//System.out.println(this.passwordEncoder.encode("xyz"));
		try {
			Role role = new Role();
			role.setRId(1);
			role.setRName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRId(2);
			role1.setRName("ROLE_USER");
		List<Role> roles	=List.of(role,role1);
		List<Role> result = roleRepo.saveAll(roles);
		result.forEach(r->{
			System.out.println(r.getRName());
		});
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public static final String [] Public_Urls = {"/api/v1/auth/login",
			"/api/v1/auth/register",
			"/v3/api-docs",
			"/v2/api-docs"
			,"/swagger-resources/**"
			,"/swagger-ui/**"
			,"/webjars/**"};


/*	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

		http
				//.formLogin().disable()
				//.httpBasic(Customizer.withDefaults());
				.csrf((csrf)->csrf.disable())
				.authorizeHttpRequests((authorize)->{
					authorize.requestMatchers(Public_Urls).permitAll()
							.requestMatchers(HttpMethod.GET).permitAll()
							//.requestMatchers("/api/user").hasRole("ADMIN")
							.anyRequest().authenticated();
				}).exceptionHandling((authentication)->authentication.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
				//.exceptionHandling().authenticationEntryPoint(null)
				//  .httpBasic(Customizer.withDefaults());
				.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		//http.authorizeHttpRequests((authz)->authz.requestMatchers("/api/v1/auth/login").permitAll());
		return http.build();
	}*/
/*	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}*/

}
