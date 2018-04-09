package com.paradm.framework.inteceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.paradm.common.Utility;

public class CsrfInterceptor extends ParadmInterceptorAdapter {

  private static final Logger log = LoggerFactory.getLogger(CsrfInterceptor.class);

  private List<String> fxAgents_;
  private List<Pattern> uaPatterns_;

  public void setFxUserAgents(List<String> agents) {
    this.fxAgents_ = agents;
  }

  public void init() {
    uaPatterns_ = new ArrayList<Pattern>();
    // Pre-compile the user-agent patterns as specified in mvc.xml
    for (final String ua : fxAgents_) {
      try {
        uaPatterns_.add(Pattern.compile(ua, Pattern.CASE_INSENSITIVE));
      } catch (PatternSyntaxException e) {
        // Ignore the pattern, if it failed to compile for whatever reason.
      }
    }
  }

  private final boolean isFXWeb(HttpServletRequest request) {
    boolean fxWeb = false;
    final String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    // FX-EWB/4.0 (PCode:TC100728; JFI:3.5.0; IPAddr:127.0.0.1; DConf:49)
    for (final Pattern p : uaPatterns_) {
      final Matcher m = p.matcher(userAgent);
      if (!Utility.isEmpty(m) && m.find()) {
        fxWeb = true;
        break;
      }
    }
    return fxWeb;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.debug("csrf request url : {}, request method : {}", request.getRequestURI(), request.getMethod());
    /*if (!isFXWeb(request) && !URIValidator.checkCSRFURI(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
      String csrfToken = CsrfTokenFactory.getTokenFromRequest(request);
      if (csrfToken == null || !csrfToken.equals(request.getSession().getAttribute(CsrfTokenFactory.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
        String action = null;
        if (URIValidator.checkURI(request.getRequestURI(), null)) {
          action = GlobalConstant.SESSION_TIME_OUT;
        } else {
          action = GlobalConstant.ACTION_TYPE_EXPIRE;
        }
        redirect(request, response, action, "");
        return false;
      }
    }*/
    return true;
  }
}
