package com.paradm.sse.security.web.authentication;

import com.paradm.sse.common.utils.Utility;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
@Component
public class KaptchaAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    KaptchaWebAuthenticationDetails details = (KaptchaWebAuthenticationDetails) authentication.getDetails();
    boolean isValidate = details.isValidate();
    if (isValidate) {
      String captcha = details.getCaptcha();
      String sessionCaptcha = details.getSessionCaptcha();
      if (Utility.isEmpty(captcha) || !captcha.equals(sessionCaptcha)) {
        throw new InternalAuthenticationServiceException("The captcha is invalid.");
      }
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
