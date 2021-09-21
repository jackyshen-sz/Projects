package com.paradm.sse.business.sercurity.authentication;

import cn.hutool.core.util.BooleanUtil;
import com.google.code.kaptcha.Constants;
import com.paradm.sse.common.constant.SecurityConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
@Getter
@Setter
public class KaptchaWebAuthenticationDetails extends WebAuthenticationDetails {
  private static final long serialVersionUID = 3524117009961565786L;

  private boolean isValidate;
  private String captcha;
  private String sessionCaptcha;

  /**
   * Records the remote address and will also set the session Id if a session already
   * exists (it won't create one).
   *
   * @param request that the authentication request was received from
   */
  public KaptchaWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    this.isValidate = BooleanUtil.toBoolean(request.getParameter(SecurityConstant.IS_VALIDATE_KEY));
    this.captcha = request.getParameter(SecurityConstant.CAPTCHA_KEY);
    this.sessionCaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
  }
}
