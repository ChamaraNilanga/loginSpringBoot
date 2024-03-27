package com.login.demo.config;

import com.login.demo.filter.JWTAuthFilter;
import com.login.demo.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    //for the hardcoded users
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("Admin")
//                .password(this.passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("User")
//                .password(this.passwordEncoder.encode("user"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin,user);
//    }

    //for the users from DB
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //for the spring security without jwt and custom filter chain
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/user/v1/welcome","/user/v1/register","/login/v1").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/user/**")
//                .authenticated().and().formLogin().and().build();

        //with custom filter chain
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/v1/welcome","/user/v1/register","/login/v1").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/user/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
