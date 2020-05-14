package com.paradm.sse.configuration;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.SecurityConstant;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.security.web.authentication.KaptchaAuthenticationDetailsSource;
import com.paradm.sse.security.web.authentication.KaptchaAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * @author Jacky.shen
 * @create data 2020/5/13
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomizeProperties cusProp;
  @Autowired
  private UserDetailsService userService;
  @Autowired
  private KaptchaAuthenticationDetailsSource kaptchaAuthenticationDetailsSource;
  @Autowired
  private KaptchaAuthenticationProvider kaptchaAuthenticationProvider;
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    auth.authenticationProvider(kaptchaAuthenticationProvider);
    auth.eraseCredentials(false);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
          .ignoringAntMatchers(Utility.splitString(cusProp.getSecurity().getCsrfIgnoreUrl(), GlobalConstant.Symbol.COMMA.toString()))
        .and().authorizeRequests()
          .antMatchers(Utility.splitString(cusProp.getSecurity().getPermitAll(), GlobalConstant.Symbol.COMMA.toString())).permitAll()
          .anyRequest().authenticated()
        .and().formLogin()
          .loginPage(cusProp.getSecurity().getBrowser().getLoginPage())
          .usernameParameter(SecurityConstant.USERNAME_KEY).passwordParameter(SecurityConstant.PASSWORD_KEY)
          .authenticationDetailsSource(kaptchaAuthenticationDetailsSource)
          .permitAll()
        .and().sessionManagement()
          .sessionFixation().migrateSession()
          .invalidSessionUrl("")
          .maximumSessions(1).expiredUrl(cusProp.getSecurity().getBrowser().getLogoutUrl()).and()
        .and().logout()
          .deleteCookies(SecurityConstant.DELETE_COOKIES)
          .logoutSuccessUrl(cusProp.getSecurity().getBrowser().getLoginPage())
        .and().rememberMe()
          .rememberMeParameter(SecurityConstant.REMEMBER_ME_KEY)
          .tokenValiditySeconds(30 * 24 * 60 * 60)
          .tokenRepository(tokenRepository())
    ;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }

  @Bean
  public JdbcTokenRepositoryImpl tokenRepository() {
    JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
    jtr.setDataSource(dataSource);
    return jtr;
  }
}
