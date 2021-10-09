package com.paradm.sse.business.configuration;

import cn.hutool.core.text.CharSequenceUtil;
import com.paradm.sse.business.sercurity.authentication.KaptchaAuthenticationDetailsSource;
import com.paradm.sse.business.sercurity.authentication.KaptchaAuthenticationProvider;
import com.paradm.sse.common.constant.SecurityConstant;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.crypt.ParadmPasswordEncoder;
import com.paradm.sse.framework.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
  private KaptchaAuthenticationProvider kaptchaAuthenticationProvider;
  @Autowired
  private KaptchaAuthenticationDetailsSource kaptchaAuthenticationDetailsSource;
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    auth.authenticationProvider(kaptchaAuthenticationProvider);
    auth.eraseCredentials(false);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
          .configurationSource(configurationSource()).and()
        .csrf()
          .ignoringAntMatchers(CharSequenceUtil.split(cusProp.getSecurity().getCsrfIgnoreUrl(), Symbol.COMMA.getValue())).and()
        .authorizeRequests()
          .antMatchers(CharSequenceUtil.split(cusProp.getSecurity().getPermitAll(), Symbol.COMMA.getValue())).permitAll()
          .anyRequest().authenticated().and()
        .formLogin()
          .loginPage(cusProp.getSecurity().getBrowser().getLoginPage())
          .usernameParameter(SecurityConstant.USERNAME_KEY).passwordParameter(SecurityConstant.PASSWORD_KEY)
          .authenticationDetailsSource(kaptchaAuthenticationDetailsSource)
          .permitAll().and()
        .sessionManagement()
          .sessionFixation().migrateSession()
          .invalidSessionUrl(Symbol.SLASH.getValue())
          .maximumSessions(1).expiredUrl(cusProp.getSecurity().getBrowser().getLogoutUrl()).and().and()
        .logout()
          .deleteCookies(SecurityConstant.DELETE_COOKIES)
          .logoutSuccessUrl(cusProp.getSecurity().getBrowser().getLoginPage()).and()
        .rememberMe()
          .rememberMeParameter(SecurityConstant.REMEMBER_ME_KEY)
          .tokenValiditySeconds(30 * 24 * 60 * 60)
          .tokenRepository(tokenRepository()).and()
        .addFilterBefore(loginFilter(), ChannelProcessingFilter.class)
    ;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(CharSequenceUtil.split(cusProp.getSecurity().getStaticIgnoreUrl(), Symbol.COMMA.getValue()));
  }

  @Bean
  public UrlBasedCorsConfigurationSource configurationSource() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    Map<String, CorsConfiguration> corsConfigurations = new LinkedHashMap<>();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
    corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name()));
    corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
    corsConfigurations.put("/api/**", corsConfiguration);
    urlBasedCorsConfigurationSource.setCorsConfigurations(corsConfigurations);
    return urlBasedCorsConfigurationSource;
  }

  @Bean
  public JdbcTokenRepositoryImpl tokenRepository() {
    JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
    jtr.setDataSource(dataSource);
    return jtr;
  }

  @Bean
  public LoginFilter loginFilter() {
    return new LoginFilter();
  }

  @Bean
  public ParadmPasswordEncoder passwordEncoder() {
    return new ParadmPasswordEncoder(cusProp.getSystem().getEncrypt(), false, 1);
  }
}
