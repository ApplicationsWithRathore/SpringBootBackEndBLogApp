package com.firstapi.config;

import com.firstapi.security.CustomUserDetailService;
import com.firstapi.security.JwtAuthenticationEntryPoint;
import com.firstapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@Configuration
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    public static final String [] Public_Urls = {"/api/v1/auth/login",
            "/api/v1/auth/register",
            "/v3/api-docs",
            "/v2/api-docs"
    ,"/swagger-resources/**"
    ,"/swagger-ui/**"
    ,"/webjars/**"};

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
   @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
   @Autowired
    private CustomUserDetailService customUserDetailService;
    @Bean
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
    }
   /* @Bean
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    }*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
