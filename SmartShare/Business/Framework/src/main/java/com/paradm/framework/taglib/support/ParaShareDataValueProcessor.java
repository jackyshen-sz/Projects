package com.paradm.framework.taglib.support;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.google.common.collect.Maps;
import com.paradm.common.CsrfTokenFactory;
import com.paradm.common.Utility;

public class ParaShareDataValueProcessor implements RequestDataValueProcessor {


  private static final Logger log = LoggerFactory.getLogger(ParaShareDataValueProcessor.class);

  @Override
  public String processAction(HttpServletRequest request, String action, String httpMethod) {
    return action;
  }

  @Override
  public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
    return value;
  }

  @Override
  public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
    Map<String, String> hiddenFields = Maps.newHashMap();
    String token = CsrfTokenFactory.getTokenForSession(request.getSession());
    hiddenFields.put(CsrfTokenFactory.CSRF_PARAM_NAME, token);
    return hiddenFields;
  }

  @Override
  public String processUrl(HttpServletRequest request, String url) {
    try {
      if (!Utility.isEmpty(url) && (url.indexOf("js/") != -1 || url.indexOf("img/") != -1 || url.indexOf("css/") != -1)) {
        if (url.indexOf("/connector/") != -1 || url.indexOf("/smartbutton/") != -1) {
          return url;
        }
        String path = ParaShareDataValueProcessor.class.getClassLoader().getResource("/").getPath();
        if (path.indexOf("WEB-INF/classes") != -1) {
          path = path.substring(0, path.indexOf("WEB-INF/classes"));
        }
        if (!path.endsWith("/")) {
          path += "/";
        }
        if (url.indexOf("img/") != -1 || url.indexOf("css/") != -1) {
          path += "theme/";
        }
        path = URLDecoder.decode(path, "UTF-8");
        String filePath = url;
        if (url.indexOf(request.getContextPath()) != -1) {
          filePath = url.substring(request.getContextPath().length() + 1);
        }
        path += filePath;
        File file = new File(path);
        if (file.exists()) {
          if (url.indexOf("?") != -1) {
            url += "@data=" + file.lastModified();
          } else {
            url += "?data=" + file.lastModified();
          }
        }
      }
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    }
    return url;
  }

}
