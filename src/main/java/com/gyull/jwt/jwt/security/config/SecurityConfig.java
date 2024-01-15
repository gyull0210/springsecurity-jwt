package com.gyull.jwt.jwt.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.gyull.jwt.jwt.security.filter.JwtSecurityConfig;
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
  private final CorsFilter corsFilter;
  
  //Bcrypt 알고리즘은 예제 연습할 때는 쓰지만 연산속도가 발전한 지금은
  //취약점이 있기 때문에 Argon2를 비롯한 대체 알고리즘을 더 권장하고 있다.
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new Argon2PasswordEncoder(16, 32, 4, 1 << 16, 3);
  }

  //Spring Security 6.1.x 버전부터는 Lambda DSL의 사용으로 .and()와 같은 메서드가 불필요하다
  //Spring Security 7을 염두로 두고 deprecated, removal 되는 메서드가 무척 많았다
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        //
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
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
            .requestMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
            .requestMatchers("/admin/**").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers(PathRequest.toH2Console()).permitAll()
            .anyRequest().authenticated()
        )
        //Spring Security 6.2.0 javadoc에서는 .apply()메서드가 deprecated되었으니 .with()을 대신 쓰라고 되어있다.
        //다른 github 예제들은 jwtSecurityConfig를 따로 작성하지 않고
        //addFilterBefore을 사용하여 직접 추가해주고 있는데, jwtSecurityConfig 또한 내부적으로 addFilterBefore을 사용해서 httpSecurity에 추가해줄 수도 있다.
        .with(new JwtSecurityConfig(tokenProvider), customizer -> {});

    return http.build();
  }
}
