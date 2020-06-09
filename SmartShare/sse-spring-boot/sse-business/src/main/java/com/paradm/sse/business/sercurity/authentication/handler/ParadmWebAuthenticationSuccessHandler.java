package com.paradm.sse.business.sercurity.authentication.handler;

import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.UrlConstant;
import com.paradm.sse.common.constant.global.LoginMethod;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
@Slf4j
public class ParadmWebAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private String loginMethod = LoginMethod.WEB.toString();

  public String getLoginMethod() {
    return loginMethod;
  }

  public void setLoginMethod(String loginMethod) {
    this.loginMethod = loginMethod;
  }

  @Override
  protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String targetUrl = UrlConstant.HOME;
    try {

    } catch (Exception e) {
      log.error(e.getMessage(), e);
//      targetUrl = "/login?error&action=" + GlobalConstant.ActionType.UNKNOWN_ERROR.toString();
      String url = request.getParameter(ModelConstant.URL);
      if (!Utility.isEmpty(url)) {
        if (!url.startsWith(Symbol.SLASH.toString())) {
          url = Symbol.SLASH.toString() + url;
        }
        targetUrl += "&url=" + url;
      }
    }
    if (response.isCommitted()) {
      log.debug("Response has already been committed. Unable to redirect to {}", targetUrl);
      return;
    }
    this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}
