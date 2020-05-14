package com.paradm.sse.framework.utils;

import com.paradm.sse.common.utils.Utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
public class WebUtility {

  public static boolean isAjaxRequest(HttpServletRequest request) {
    String header = request.getHeader("x-requested-with");
    if (!Utility.isEmpty(header) && "XMLHttpRequest".equalsIgnoreCase(header)) {
      return true;
    }
    return false;
  }

  /**
   * get cookie by name
   *
   * @param cookieName
   * @return
   */
  public Cookie getCookie(HttpServletRequest request, String cookieName) {
    return this.getCookie(request, "/", cookieName);
  }

  /**
   * get cookie by name
   *
   * @param cookieName
   * @return
   */
  public Cookie getCookie(HttpServletRequest request, String path, String cookieName) {
    Cookie[] cookies = request.getCookies();
    Cookie result = null;
    if (!Utility.isEmpty(cookies)) {
      Optional<Cookie> optional = Arrays.stream(cookies).filter(cookie -> cookieName.equalsIgnoreCase(cookie.getName())).findAny();
      if (optional.isPresent()) {
        result = optional.get();
      }
    }
    return result;
  }

  /**
   * save a cookie
   *
   * @param cookieName
   * @param value
   * @param expiry (s) set effective time.
   */
  public void setCookie(String cookieName, String value, int expiry, HttpServletResponse res) {
    this.setCookie("/", cookieName, value, expiry, res);
  }

  /**
   * save a cookie
   *
   * @param path
   * @param cookieName
   * @param value
   * @param expiry (s) set effective time.
   */
  public void setCookie(String path, String cookieName, String value, int expiry, HttpServletResponse res) {
    if (Utility.isEmpty(value) || expiry < 0) {
      return;
    }
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setPath(path);
    cookie.setMaxAge(expiry);
    res.addCookie(cookie);
  }
}
