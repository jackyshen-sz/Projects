package com.paradm.sse.framework.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.global.ConnectorVersion;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.enums.YesNoFlag;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.framework.utils.UriValidator;
import com.paradm.sse.framework.utils.WebUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jacky.shen
 * @create date 2021/10/8
 */
@Slf4j
public class LoginFilter extends GenericFilterBean {

  private static final String DEFAULT_PARAMETER_NAME = "locale";

  private static final String SN = "SN";

  private String parameterName = DEFAULT_PARAMETER_NAME;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    log.debug("LoginFilter.doFilter start...{}", httpServletRequest.getRequestURI());
    WebUtility.setParameter(httpServletRequest);
    WebUtility.setLocale(httpServletRequest, this.obtainParameterName(httpServletRequest));
    WebUtility.setMobile(httpServletRequest);

    if (!WebUtility.checkLicense(httpServletRequest, httpServletResponse)) {
      return;
    }
    if (this.isConnectorLogin(httpServletRequest, httpServletResponse)) {
      log.debug("MFD request...");
      httpServletResponse.addHeader(ModelConstant.CONNECTOR_HEADER, GlobalConstant.CONNECTOR_HEADER_VALUE);
    } else if (isMobileAPPLogin(httpServletRequest)) {
      log.debug("mobile request...");
    } else if (!isLogin(httpServletRequest, httpServletResponse)) {
      return;
    }
    filterChain.doFilter(request, response);
  }

  private boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
    boolean result = true;
    String reqUri = request.getRequestURI();
    SessionContainer sessionContainer = (SessionContainer) request.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
    if (ObjectUtil.isNotEmpty(sessionContainer) && ObjectUtil.isNotEmpty(sessionContainer.getUserRecordModel())) {
      String globalTwoFactorEnable = sessionContainer.getTwoFactorAuthEnable();
      String twoFactorEnable = sessionContainer.getUserRecordModel().getTwoFactorAuthEnable();
      if (GlobalConstant.TRUE.equals(globalTwoFactorEnable)
        && YesNoFlag.YES.toString().equals(twoFactorEnable)
        && !sessionContainer.isTwoFactorAuth()) {
        if (UriValidator.checkUri(reqUri, "")) {
          return true;
        }
        try {
          String contextPath = request.getContextPath();
          String oriPath = reqUri + (CharSequenceUtil.isEmpty(request.getQueryString()) ? "" : ("?" + request.getQueryString()));
          String redirectPath = contextPath + "/twoFactorAuth?url=" + URLEncoder.encode(oriPath, StandardCharsets.UTF_8.toString());
          response.sendRedirect(redirectPath);
        } catch (Exception e) {
          log.error("Cannot redirect to login page.", e);
        }
        result = false;
      }
    }
    return result;
  }

  private boolean isMobileAPPLogin(HttpServletRequest request) {
    return UriValidator.checkMobileAppUrl(request.getRequestURI());
  }

  private boolean isConnectorLogin(HttpServletRequest request, HttpServletResponse response) {
    boolean result = false;
    if (isConnectorRequest(request)) {
      String reqUri = request.getRequestURI();
      String contextPath = request.getContextPath();
      String sn = (String) request.getSession().getAttribute(SN);
      if (CharSequenceUtil.isEmpty(sn)) {
        sn = request.getParameter(SN); // "755568"; //
        log.debug("Interceptor get SN from request parameter {}", sn);
        request.getSession().setAttribute(SN, sn);
      }
      if (reqUri.contains(contextPath)) {
        reqUri = reqUri.substring(reqUri.indexOf(contextPath) + contextPath.length());
      }
      String query = request.getQueryString();
      if (reqUri.contains(";jsessionid")) {
        reqUri = reqUri.substring(0, reqUri.indexOf(";jsessionid"));
      }
      SessionContainer sessionContainer = (SessionContainer) request.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
      boolean haveSessionContainer = (ObjectUtil.isNotEmpty(sessionContainer) && ObjectUtil.isNotEmpty(sessionContainer.getUserRecordModel()))
          || CharSequenceUtil.isNotEmpty(request.getParameter("sessid"));
      if (haveSessionContainer) {
        return !WebUtility.sessionMFDExpireHandel(request, response, true, true);
      } else if (UriValidator.checkConnectorUri(reqUri, query)) {
        result = true;
      } else {
        try {
          String oriPath = reqUri + (CharSequenceUtil.isEmpty(request.getQueryString()) ? "" : ("?" + request.getQueryString()));
          String redirectPath = contextPath + "/connector/smartFile?action=expire&url=" + URLEncoder.encode(oriPath, StandardCharsets.UTF_8.toString());
          response.sendRedirect(redirectPath);
        } catch (Exception e) {
          log.error("Cannot redirect to login page.", e);
        }
      }
    }
    return result;
  }

  private boolean isConnectorRequest(HttpServletRequest request) {
    AntPathRequestMatcher aprm = new AntPathRequestMatcher("/connector/**");
    boolean result = aprm.matches(request);
    if (result) {
      this.setConnectorVersion(request);
    }
    return result;
  }

  private void setConnectorVersion(HttpServletRequest request) {
    // UserAgent: FX-EWB/4.0
    String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    String connectorVersion = ConnectorVersion.VERSION_4.toString();
    String connectorIpAddr = "127.0.0.1";
    if (CharSequenceUtil.isNotEmpty(userAgent)) {
      if (userAgent.contains(ConnectorVersion.VERSION_5.toString())) {
        connectorVersion = ConnectorVersion.VERSION_5.toString();
      } else if (userAgent.contains(ConnectorVersion.VERSION_7.toString())) {
        connectorVersion = ConnectorVersion.VERSION_7.toString();
      }
      Map<String, String> attributeMap = this.getAttributeMap(userAgent);
      if (CharSequenceUtil.isNotEmpty(attributeMap.get(ModelConstant.CONNECTOR_IP_ADDR))) {
        connectorIpAddr = attributeMap.get(ModelConstant.CONNECTOR_IP_ADDR);
      }
    }
    request.setAttribute(ModelConstant.CONNECTOR_VERSION, connectorVersion);
    request.setAttribute(ModelConstant.CONNECTOR_IP_ADDR, connectorIpAddr);
  }

  private Map<String, String> getAttributeMap(String userAgent) {
    // UserAgent: FX-EWB/4.0 (PCode:TC100728; JFI:3.5.0; IPAddr:127.0.0.1; DConf:49)
    Map<String, String> attributeMap = new HashMap<>();
    String regex = "(.+?)\\((.+?)\\)";
    Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher m = p.matcher(userAgent);
    if (m.find() && m.group(2) != null) {
      // m.group(2) = PCode:TC100728; JFI:3.5.0; IPAddr:127.0.0.1; DConf:49
      String[] attributeAry = StringUtils.split(m.group(2).trim(), Symbol.SEMICOLON.getValue());
      Optional.ofNullable(attributeAry).ifPresent(attrs -> {
        for (String attr : attrs) {
          if (CharSequenceUtil.isNotEmpty(attr) && attr.contains(Symbol.COLON.getValue())) {
            String[] attribute = StringUtils.split(attr, Symbol.COLON.getValue());
            Optional.ofNullable(attribute).ifPresent(a -> {
              String key = a[0].trim();
              String value = a[1].trim();
              attributeMap.put(key, value);
            });
          }
        }
      });
    }
    return attributeMap;
  }

  protected String obtainParameterName(HttpServletRequest request) {
    return request.getParameter(this.parameterName);
  }

  public String getParameterName() {
    return parameterName;
  }

  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }
}
