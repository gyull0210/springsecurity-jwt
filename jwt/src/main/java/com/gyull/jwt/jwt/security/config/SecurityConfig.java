package com.gyull.jwt.jwt.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gyull.jwt.jwt.security.filter.JwtFilter;
import com.gyull.jwt.jwt.security.handler.JwtAccessDeniedHandler;
import com.gyull.jwt.jwt.security.handler.JwtAuthenticationEntryPoint;
import com.gyull.jwt.jwt.security.provider.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new Argon2PasswordEncoder(0, 0, 0, 0, 0);
  }

  //Spring Security 6.1.x 버전부터는 Lambda DSL의 사용으로 .and()와 같은 메서드가 불필요하다
  //Spring Security 7을 염두로 두고 deprecated, removal 되는 메서드가 무척 많았다
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf((csrf) -> csrf.disable())
        
        .exceptionHandling((exceptionHandling) ->
            exceptionHandling
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        )
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
          authorize -> 
          authorize
            .requestMatchers("/").permitAll()
            .requestMatchers("/admin/**").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .anyRequest().authenticated()
        )
        //Spring Security 6.2.0 javadoc에서는 .apply()메서드가 deprecated되었으니 .with()을 대신 쓰라고 했지만
        //github 예제들은 jwtSecurityConfig를 따로 작성하지 않고
        //addFilterBefore을 사용하여 직접 추가해주고 있어서 이렇게 작성한다.
        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutSuccessUrl("/"));

    return httpSecurity.build();
  }
}
