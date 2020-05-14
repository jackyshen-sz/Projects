package com.paradm.sse.security.web.authentication;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
@Component
public class KaptchaAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, KaptchaWebAuthenticationDetails> {

  @Override
  public KaptchaWebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new KaptchaWebAuthenticationDetails(context);
  }
}
