package com.paradm.sse.framework.utils;

/**
 * @author Jacky.shen
 * @create date 2021/10/8
 */
public class UriValidator {
  private UriValidator() {
  }

  public static boolean checkUri(String uri, String query) {
    boolean validUri = false;
    if (uri.contains("login")) {
      validUri = true;
    } else if (uri.contains("loginAs")) {
      validUri = true;
    } else if (uri.contains("/r/")) {
      validUri = true;
    } else if (uri.contains("activeUserView")) {
      validUri = true;
    } else if (uri.contains("activeUser")) {
      validUri = true;
    } else if (uri.contains("forgetPwd")) {
      validUri = true;
    } else if (uri.contains("checkUser")) {
      validUri = true;
    } else if (uri.contains("kaptcha")) {
      validUri = true;
    } else if (uri.contains("resetPwd")) {
      validUri = true;
    } else if (uri.contains("resend")) {
      validUri = true;
    } else if (uri.contains("signin")) {
      validUri = true;
    } else if (uri.contains("webAppHttpService")) {
      validUri = true;
    } else if (uri.contains("webService")) {
      validUri = true;
    } else if (uri.toLowerCase().contains("locale")) {
      validUri = true;
    } else if (uri.toLowerCase().contains("privacy_statement")) {
      validUri = true;
    } else if (uri.toLowerCase().contains("terms_conditions")) {
      validUri = true;
    } else if (uri.toLowerCase().contains("share/external")) {
      validUri = true;
    } else if (uri.contains("executeSQL")) {
      validUri = true;
    } else if (uri.contains("updateDocItemSize")) {
      validUri = true;
    } else if (uri.contains("trailrun")) {
      validUri = true;
    } else if (uri.contains("select")) {
      validUri = true;
    } else if (uri.contains("init")) {
      if (uri.contains("init/system") || query.contains("system")) {
        validUri = true;
      }
    } else if (uri.contains("twoFactorAuth")) {
      validUri = true;
    } else if (uri.contains("reSendAuthCode")) {
      validUri = true;
    }
    return validUri;
  }

  public static boolean checkMobileAppUrl(String uri) {
    return uri.toLowerCase().contains("/mobile/");
  }

  public static boolean checkConnectorUri(String uri, String query) {
    boolean validUri = false;
    if (uri.contains("emailMe")) {
      validUri = true;
    } else if (uri.contains("smartFile")) {
      validUri = true;
      if (uri.contains("smartFile/home")
          || uri.contains("smartFile/list")
          || uri.contains("smartFile/validate")
          || uri.contains("smartFile/upload")) {
        validUri = false;
      }
    } else if (uri.contains("login")) {
      validUri = true;
    } else if (uri.contains("scanCode")) {
      validUri = true;
    }
    return validUri;
  }
}
