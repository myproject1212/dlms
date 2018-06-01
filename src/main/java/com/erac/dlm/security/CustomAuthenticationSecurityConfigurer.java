package com.erac.dlm.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.erac.appsec.spring.security.authentication.logexp.LogonExpressAuthenticationSecurityConfigurerAdapter;
import com.erac.appsec.spring.security.authentication.service.ServiceAuthenticationSecurityConfigurerAdapter;
import com.erac.appsec.spring.security.custom.local.LocalCustomAuthenticationServiceAppConfig;
import com.erac.appsec.spring.security.logexp.local.EnableLocalLogonExpressAuthentication;
import com.erac.appsec.spring.security.service.local.EnableLocalServiceAuthentication;

@Configuration
public class CustomAuthenticationSecurityConfigurer {

  private static final Logger LOG = LoggerFactory.getLogger("security");

  @Configuration
  @Import(LocalCustomAuthenticationServiceAppConfig.class)
  @EnableLocalServiceAuthentication
  @Order(1)
  public static class ServiceSecurityConfigAdapter extends ServiceAuthenticationSecurityConfigurerAdapter {

    @Value("${security.ignored}")
    private String[] authWhitelist;

    @Override
    protected void configureAuthorization(HttpSecurity http)
        throws Exception {
      LOG.warn("test");
      http.csrf().disable();
      http.authorizeRequests().antMatchers(authWhitelist).permitAll();
      http.antMatcher("/api/**").exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and().authorizeRequests().anyRequest()
          .authenticated();
    }
  }

  @Configuration
  @Order(2)
  public static class AuthenticateSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http)
        throws Exception {
      http.csrf().disable().antMatcher("/authenticate/**").authorizeRequests().anyRequest().permitAll();
    }
  }

  @Configuration
  @Order(3)
  @EnableLocalLogonExpressAuthentication
  public static class WebSecurityConfigAdapter extends LogonExpressAuthenticationSecurityConfigurerAdapter {

  }
}
