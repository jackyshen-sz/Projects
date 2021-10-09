package com.paradm.sse.framework.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.theborakompanioni.spring.useragentutils.UserAgentUtils;
import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.global.LocaleType;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.constant.paramter.ParameterCode;
import com.paradm.sse.common.factory.SystemParameterFactory;
import com.paradm.sse.common.license.SiteValidator;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
@Slf4j
public class WebUtility {

  private static final String IS_MOBILE = "ISMOBILE";
  private static final String CHECK_LICENSE = "checkLicense";

  private WebUtility() {
  }

  public static boolean sessionMFDExpireHandel(HttpServletRequest req, HttpServletResponse res, boolean resetLastTime, boolean redirectLoginPage) {
    Integer sessionTimeout = SystemParameterFactory.getSystemParameterInteger(ParameterCode.SESSION_TIMEOUT.getKey());
    Long lastTime = (Long) req.getSession().getAttribute(GlobalConstant.SESSION_LAST_TIME);
    if (null == lastTime) {
      return true;
    }
    if (resetLastTime) {
      req.getSession().setAttribute(GlobalConstant.SESSION_LAST_TIME, System.currentTimeMillis());
    }

    if (null != sessionTimeout && sessionTimeout > 0) {
      log.debug("last time : {}", lastTime);
      if (System.currentTimeMillis() - lastTime > sessionTimeout * 1000) {
        req.getSession().removeAttribute(GlobalConstant.SESSION_LAST_TIME);
        req.getSession().invalidate();
        if (redirectLoginPage && !req.getContextPath().endsWith("login")) {
          String redirectPath = req.getContextPath() + "/connector/smartFile?action=expire";
          try {
            processRedirect(req, res, redirectPath);
          } catch (Exception e) {
            log.error("Cannot redirect to login page.", e);
          }
        }
        return true;
      }
    }
    return false;
  }

  public static boolean checkLicense(HttpServletRequest request, HttpServletResponse response) {
    try {
      String query = request.getQueryString();
      if (CharSequenceUtil.isNotEmpty(query) && query.contains(CHECK_LICENSE)) {
        return true;
      }
      if (request.getParameter("RELOAD_LICENSE") != null) {
        SiteValidator.INSTANCE.reload();
      }
      String contextPath = request.getContextPath();
      if (!SiteValidator.INSTANCE.validateLicence()) {
        log.error("Invalid License Key");
        processRedirect(request, response, contextPath + "/index?checkLicense=1");
        return false;
      } else if (!"UNLIMITED".equals(SiteValidator.INSTANCE.getExpDate())) {
        // Date format should be "yyyy-MM-dd".
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expiryDate = null;
        try {
          expiryDate = sdf.parse(SiteValidator.INSTANCE.getExpDate());
        } catch (Exception ignore1) {
          log.error("Invalid Expiry Date Format", ignore1);
          processRedirect(request, response, contextPath + "/index?checkLicense=2");
          return false;
        }
        if (expiryDate == null || new Date().after(expiryDate)) {
          log.error("License Expired");
          processRedirect(request, response, contextPath + "/index?checkLicense=3");
          return false;
        }
      } else {
        if (SiteValidator.INSTANCE.getLicenceInfo() != null && SiteValidator.INSTANCE.getLicenceInfo().getVersion() != null) {
          // appContainer.setVersion(siteValidator.getLicenceInfo().getVersion());
        }
      }
    } catch (Exception ignore) {
      log.error(ignore.getMessage(), ignore);
      return false;
    }
    return true;
  }

  public static void setMobile(HttpServletRequest request) {
    UserAgent userAgent = UserAgentUtils.getCurrentUserAgent(request);
    Optional.ofNullable(userAgent).ifPresent(e -> request.setAttribute(IS_MOBILE, isMobile(e)));
  }

  public static boolean isMobile(UserAgent userAgent) {
    AtomicBoolean mobile = new AtomicBoolean(false);
    Optional.ofNullable(userAgent)
        .flatMap(e -> Optional.ofNullable(e.getOperatingSystem()))
        .ifPresent(o -> mobile.set(DeviceType.MOBILE.equals(o.getDeviceType())));
    return mobile.get();
  }

  public static void setLocale(HttpServletRequest request, String localeValue) {
    Locale newLocale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    if (CharSequenceUtil.isNotEmpty(localeValue)) {
      newLocale = StringUtils.parseLocaleString(localeValue);
    }
    if (ObjectUtil.isEmpty(newLocale) || !LocaleType.contains(newLocale)) {
      newLocale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
      if (ObjectUtil.isEmpty(newLocale) || !LocaleType.contains(newLocale)) {
        newLocale = StringUtils.parseLocaleString(getClientLocal(request));
      }
    }
    WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, newLocale);
  }

  public static String getClientLocal(HttpServletRequest request) {
    // Accept-Language:en-US,en;q=0.8,zh-Hans-CN;q=0.5,zh-Hans;q=0.3
    String clientLocal = request.getHeader("Accept-Language");
    String locale = LocaleType.ENGLISH.toString();
    if (CharSequenceUtil.isNotEmpty(clientLocal)) {
      if (clientLocal.startsWith("zh-CN")) {
        locale = LocaleType.SIMPLIFIED_CHINESE.toString();
      } else if (clientLocal.startsWith("zh-HK")) {
        locale = LocaleType.TRADITIONAL_CHINESE.toString();
      }
    }
    return locale;
  }

  public static void setParameter(HttpServletRequest request) {
    request.setAttribute(ModelConstant.HELP_CENTER_URL, SystemParameterFactory.getSystemParameter(ParameterCode.SYSTEM_HELP_CENTER.getKey()));
    request.setAttribute(ModelConstant.MAX_ONLINE_USER_COUNT, SiteValidator.INSTANCE.getLicenceInfo().getConcurrentUser());

  }

  public static void processRedirect(HttpServletRequest request, HttpServletResponse response, String redirectPath) throws IOException {
    if (isAjaxRequest(request)) {
      response.addHeader(GlobalConstant.SESSION_EXPIRE_REDIRECT_URL, redirectPath);
    } else {
      response.sendRedirect(redirectPath);
    }
  }

  public static boolean isAjaxRequest(HttpServletRequest request) {
    String header = request.getHeader("x-requested-with");
    return CharSequenceUtil.isNotEmpty(header) && "XMLHttpRequest".equalsIgnoreCase(header);
  }

  /**
   * get cookie by name
   *
   * @param cookieName
   * @return
   */
  public Cookie getCookie(HttpServletRequest request, String cookieName) {
    return this.getCookie(request, Symbol.SLASH.getValue(), cookieName);
  }

  /**
   * get cookie by name
   *
   * @param cookieName
   * @return
   */
  public Cookie getCookie(HttpServletRequest request, String path, String cookieName) {
    return Optional.ofNullable(request.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies).filter(cookie -> path.equals(cookie.getPath()) && cookieName.equalsIgnoreCase(cookie.getName())).findAny())
        .orElse(null);
  }

  /**
   * save a cookie
   *
   * @param cookieName
   * @param value
   * @param expiry     (s) set effective time.
   */
  public void setCookie(String cookieName, String value, int expiry, HttpServletResponse res) {
    this.setCookie(Symbol.SLASH.getValue(), cookieName, value, expiry, res);
  }

  /**
   * save a cookie
   *
   * @param path
   * @param cookieName
   * @param value
   * @param expiry     (s) set effective time.
   */
  public void setCookie(String path, String cookieName, String value, int expiry, HttpServletResponse res) {
    if (CharSequenceUtil.isEmpty(value) || expiry < 0) {
      return;
    }
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setPath(path);
    cookie.setMaxAge(expiry);
    res.addCookie(cookie);
  }
}
