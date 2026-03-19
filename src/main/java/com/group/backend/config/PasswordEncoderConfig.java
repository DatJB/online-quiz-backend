package com.group.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

      @Override
      public String encode(CharSequence rawPassword) {
        return bCrypt.encode(rawPassword);
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword.equals(rawPassword.toString())) {
          return true;
        }
        return bCrypt.matches(rawPassword, encodedPassword);
      }
    };
  }
}
