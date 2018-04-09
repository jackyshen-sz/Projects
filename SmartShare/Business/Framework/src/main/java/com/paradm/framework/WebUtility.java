/*
 * @(#)Utility.java
 */
package com.paradm.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtility {

  private static final Logger log = LoggerFactory.getLogger(WebUtility.class);

  /*public static boolean sessionExpireHandel(HttpServletRequest req, HttpServletResponse res, String companyId, boolean resetLastTime, boolean redirectLoginPage) {
    Integer session_timeout = SystemParameterFactory.getSystemParameterInteger(SystemParameterConstant.SESSION_TIMEOUT);// s
    Long lastTime = (Long) req.getSession().getAttribute(GlobalConstant.SESSION_LAST_TIME);
    String manualExpireFlag = (String) req.getSession().getAttribute(GlobalConstant.SESSION_MANUAL_EXPIRE_FLAG);
    if (null == lastTime) {
      return true;
    }
    if (resetLastTime) {
      req.getSession().setAttribute(GlobalConstant.SESSION_LAST_TIME, new Long(System.currentTimeMillis()));
    }

    if (null != session_timeout && session_timeout.intValue() > 0) {
      log.debug("last time : " + String.valueOf(lastTime));
      if (System.currentTimeMillis() - lastTime > session_timeout * 1000) {
        log.debug("timeout...");
        req.getSession().removeAttribute(GlobalConstant.SESSION_LAST_TIME);
        req.getSession().invalidate();
        if (redirectLoginPage && !req.getContextPath().toString().endsWith("login")) {
          String redirectPath = req.getContextPath() + "/login?action=expire";
          try {
            processRedirect(req, res, redirectPath);
            return true;
          } catch (Exception e) {
            log.error("Cannot redirect to login page.", e);
          }
        }
      }
    }

    log.debug(GlobalConstant.SESSION_MANUAL_EXPIRE_FLAG + " : " + manualExpireFlag);
    if (GlobalConstant.TRUE.equals(manualExpireFlag)) {
      String reasonCode = (String) req.getSession().getAttribute(GlobalConstant.SESSION_MANUAL_EXPIRE_REASON_CODE);
      if (Utility.isEmpty(reasonCode)) {
        reasonCode = GlobalConstant.ACTION_TYPE_EXPIRE;
      }
      req.getSession().removeAttribute(GlobalConstant.SESSION_LAST_TIME);
      req.getSession().invalidate();
      if (redirectLoginPage && !req.getContextPath().toString().endsWith("login")) {
        String redirectPath = req.getContextPath() + "/login?action=" + reasonCode;
        try {
          processRedirect(req, res, redirectPath);
          return true;
        } catch (Exception e) {
          log.error("Cannot redirect to login page.", e);
        }
      }
    }
    return false;
  }

  public static boolean sessionMFDExpireHandel(HttpServletRequest req, HttpServletResponse res, String companyId, boolean resetLastTime, boolean redirectLoginPage) {
    Integer session_timeout = SystemParameterFactory.getSystemParameterInteger(SystemParameterConstant.SESSION_TIMEOUT);// s
    Long lastTime = (Long) req.getSession().getAttribute(GlobalConstant.SESSION_LAST_TIME);
    if (null == lastTime) {
      return true;
    }
    if (resetLastTime) {
      req.getSession().setAttribute(GlobalConstant.SESSION_LAST_TIME, new Long(System.currentTimeMillis()));
    }

    if (null != session_timeout && session_timeout.intValue() > 0) {
      log.debug("last time : " + String.valueOf(lastTime));
      if (System.currentTimeMillis() - lastTime > session_timeout * 1000) {
        req.getSession().removeAttribute(GlobalConstant.SESSION_LAST_TIME);
        req.getSession().invalidate();
        if (redirectLoginPage && !req.getContextPath().toString().endsWith("login")) {
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

  public static boolean isAjaxRequest(HttpServletRequest request) {
    if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
      return true;
    }
    return false;
  }

  public static void processRedirect(HttpServletRequest request, HttpServletResponse response, String redirectPath) throws IOException {
    if (isAjaxRequest(request)) {
      response.addHeader(GlobalConstant.SESSION_EXPIRE_REDIRECT_URL, redirectPath);
    } else {
      response.sendRedirect(redirectPath);
    }
  }

  public static void checkAndFixSystemConfig(Integer companyId) {
    if (!Utility.isEmpty(companyId)) {
      if (Utility.isEmpty(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SESSION_TIMEOUT))) {
        SystemConfigService systemConfigService = ContextLoader.getCurrentWebApplicationContext().getBean(SystemConfigService.class);
        if (!Utility.isEmpty(systemConfigService) && !Utility.isEmpty(companyId)) {
          systemConfigService.checkAndFixSystemConfig(companyId);
        }
      }
    }
  }*/
}
