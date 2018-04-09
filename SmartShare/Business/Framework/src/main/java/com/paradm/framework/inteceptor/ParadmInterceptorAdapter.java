package com.paradm.framework.inteceptor;

import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.github.theborakompanioni.spring.useragentutils.UserAgentUtils;
import com.paradm.common.GlobalConstant;
import com.paradm.common.ModelConstant;
import com.paradm.common.SystemParameterConstant;
import com.paradm.common.SystemParameterFactory;
import com.paradm.common.Utility;
import com.paradm.common.connector.ConnectorConstant;
import com.paradm.common.enums.LocaleType;
import com.paradm.framework.WebUtility;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class ParadmInterceptorAdapter extends HandlerInterceptorAdapter {

  private static final Logger log = LoggerFactory.getLogger(ParadmInterceptorAdapter.class);

  protected static final String ISMOBILE = "ISMOBILE";
  protected static final String DEFAULT_PARAM_NAME = "locale";
  protected static final String SN = "SN";

  private String paramName = DEFAULT_PARAM_NAME;

  protected final boolean isMobile(final UserAgent userAgent) {
    boolean mobile = false;
    if (!Utility.isEmpty(userAgent)) {
      OperatingSystem os = userAgent.getOperatingSystem();
      if (!Utility.isEmpty(os)) {
        DeviceType deviceType = os.getDeviceType();
        if (DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType)) {
          mobile = true;
        }
      }
    }
    return mobile;
  }

  protected void setIsMobile(HttpServletRequest request, ModelAndView modelAndView) {
    final UserAgent userAgent = UserAgentUtils.getCurrentUserAgent(request);
    log.debug("UserAgent: {}", userAgent);
    if (!Utility.isEmpty(modelAndView)) {
      if (null != userAgent) {
        modelAndView.addObject(ISMOBILE, isMobile(userAgent));
      }
    } else {
      if (null != userAgent) {
        request.setAttribute(ISMOBILE, isMobile(userAgent));
      }
    }
  }

  protected void setLocale(HttpServletRequest req, HttpServletResponse res) {
    Locale newLocale = (Locale) WebUtils.getSessionAttribute(req, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    if (isConnectorRequest(req)) {
      newLocale = Utility.parseLocaleString(LocaleType.ENGLISH.toString());
    } else {
      String localeParam = req.getParameter(this.paramName);
      if (!Utility.isEmpty(localeParam)) {
        newLocale = Utility.parseLocaleString(localeParam);
      }
      if (Utility.isEmpty(newLocale) || !LocaleType.contains(newLocale)) {
        newLocale = (Locale) req.getSession().getAttribute("LOCALE");
        if (Utility.isEmpty(newLocale) || !LocaleType.contains(newLocale)) {
          newLocale = Utility.parseLocaleString(Utility.getClientLocal(req));
        }
      }
    }
    LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
    if (localeResolver == null) {
      throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
    }
    localeResolver.setLocale(req, res, newLocale);
    req.getSession().setAttribute("LOCALE", (Locale) WebUtils.getSessionAttribute(req, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
    req.setAttribute(ModelConstant.HELP_CENTER_URL, SystemParameterFactory.getSystemParameter(SystemParameterConstant.SYSTEM_HELP_CENTER));
  }

  protected boolean isConnectorRequest(HttpServletRequest request) {
    String requestURL = request.getRequestURI();
    for (String url : ConnectorConstant.connectorRequestURLs) {
      if (requestURL.indexOf(url) >= 0) {
        this.setConnectorVersion(request);
        return true;
      }
    }
    return false;
  }

  protected void setConnectorVersion(HttpServletRequest request) {
    // UserAgent: FX-EWB/4.0
    String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    String connectorVersion = GlobalConstant.CONNECTOR_VERSION_4;
    if (!Utility.isEmpty(userAgent) && userAgent.indexOf(GlobalConstant.CONNECTOR_VERSION_5) != -1) {
      connectorVersion = GlobalConstant.CONNECTOR_VERSION_5;
    }
    request.setAttribute(ModelConstant.CONNECTOR_VERSION, connectorVersion);
  }

  protected void redirect(HttpServletRequest request, HttpServletResponse response, String action, String functionCode) {
    String reqUri = request.getRequestURI();
    String query = request.getQueryString();
    if (WebUtility.isAjaxRequest(request)) {
      reqUri = "home";
      query = "";
    }
    if (reqUri.indexOf(";jsessionid") > -1) {
      reqUri = reqUri.substring(0, reqUri.indexOf(";jsessionid"));
    }
    try {
      String oriPath = reqUri + (Utility.isEmpty(query) ? "" : ("?" + query));
      if (oriPath.indexOf("?isLogin=Y") > -1) {
        oriPath = Utility.replaceString(oriPath, "?isLogin=Y", "");
      } else if (oriPath.indexOf("&isLogin=Y") > -1) {
        oriPath = Utility.replaceString(oriPath, "&isLogin=Y", "");
      }
      String contextPath = request.getContextPath();
      if (!Utility.isEmpty(contextPath) && oriPath.startsWith(contextPath)) {
        oriPath = Utility.replaceString(oriPath, contextPath, "");
      }
      String redirectPath = request.getContextPath() + "/login?action=" + action;
      if (oriPath.indexOf("/login") == -1) {
        redirectPath += "&url=" + URLEncoder.encode(oriPath, "UTF-8");
      }
      if (GlobalConstant.ACTION_TYPE_ACCESS.equals(action)) {
        redirectPath = request.getContextPath() + "/home?functionCode=" + functionCode;
      } else if (GlobalConstant.ACTION_TYPE_TWOFACTORAUTH.equals(action)) {
        redirectPath = request.getContextPath() + "/twoFactorAuth";
      }
      WebUtility.processRedirect(request, response, redirectPath);
    } catch (Exception e) {
      log.error("Cannot redirect to login page", e);
    }
  }

  protected String getCookieByName(Cookie[] cookies, String name) {
    String result = "";
    if (!Utility.isEmpty(cookies)) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        if (name.equals(cookie.getName())) {
          result = cookie.getValue();
          break;
        }
      }
    }
    return result;
  }

  protected boolean cookieLogin(HttpServletRequest req, HttpServletResponse res, String loginName) {
    boolean result = true;
    if (!Utility.isEmpty(loginName)) {
      try {
        String reqUri = req.getRequestURI();
        String query = req.getQueryString();
        if (reqUri.indexOf(";jsessionid") > -1) {
          reqUri = reqUri.substring(0, reqUri.indexOf(";jsessionid"));
        }
        String oriPath = reqUri + (Utility.isEmpty(query) ? "" : ("?" + query));
        if (oriPath.indexOf("?isLogin=Y") > -1) {
          oriPath = Utility.replaceString(oriPath, "?isLogin=Y", "");
        } else if (oriPath.indexOf("&isLogin=Y") > -1) {
          oriPath = Utility.replaceString(oriPath, "&isLogin=Y", "");
        }
        String contextPath = req.getContextPath();
        if (!Utility.isEmpty(contextPath) && oriPath.startsWith(contextPath)) {
          oriPath = Utility.replaceString(oriPath, contextPath, "");
        }
        String redirectPath =
            req.getContextPath() + "/loginAs?loginName=" + URLEncoder.encode(loginName, GlobalConstant.ENCODE_UTF8) + "&url=" + URLEncoder.encode(oriPath, GlobalConstant.ENCODE_UTF8);
        WebUtility.processRedirect(req, res, redirectPath);
      } catch (Exception e) {
        log.error("Cannot redirect to login page", e);
        return true;
      }
      result = false;
    }
    return result;
  }
}
