package com.erac.dlm;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.erac.appsec.spring.security.service.local.EnableLocalServiceAuthentication;
import com.erac.common.startuputil.eps.client.spring.EpsPropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@EnableLocalServiceAuthentication
// TODO Add EnableServiceAuthenticationHelper annotation when Vehicle Cloud Config supports this application
public class Application extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer epsPropertySourcesPlaceholderConfigurer() {
    EpsPropertySourcesPlaceholderConfigurer epsPropertySourcesPlaceholderConfigurer = new EpsPropertySourcesPlaceholderConfigurer();
    epsPropertySourcesPlaceholderConfigurer.setAdditionalPropertyMappings(Collections.singletonMap("p.jdbc.dlm_app", "jdbc.password"));
    return epsPropertySourcesPlaceholderConfigurer;
  }
}
