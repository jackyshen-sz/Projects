package com.paradm.framework.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.paradm.common.GlobalConstant;
import com.paradm.common.MessageResourcesFactory;
import com.paradm.common.Utility;

public class TagUtils {

  private static final Logger log = LoggerFactory.getLogger(TagUtils.class);

  private static TagUtils instance = new TagUtils();

  private static final Map<String, Integer> scopes = new HashMap<String, Integer>();

  static {
    scopes.put("page", new Integer(PageContext.PAGE_SCOPE));
    scopes.put("request", new Integer(PageContext.REQUEST_SCOPE));
    scopes.put("session", new Integer(PageContext.SESSION_SCOPE));
    scopes.put("application", new Integer(PageContext.APPLICATION_SCOPE));
  }

  protected TagUtils() {
    super();
  }

  /**
   * Returns the Singleton instance of TagUtils.
   */
  public static TagUtils getInstance() {
    return instance;
  }

  public static void setInstance(TagUtils instance) {
    TagUtils.instance = instance;
  }

  public ControllerMessages getControllerMessages(PageContext pageContext, String paramName) throws JspException {
    ControllerMessages cm = new ControllerMessages();
    Object value = pageContext.findAttribute(paramName);
    if (value != null) {
      try {
        if (value instanceof String) {
          cm.add("", new ControllerMessage((String) value));
        } else if (value instanceof String[]) {
          String[] keys = (String[]) value;
          for (int i = 0; i < keys.length; i++) {
            cm.add("", new ControllerMessage(keys[i]));
          }
        } else if (value instanceof ControllerErrors) {
          ControllerErrors m = (ControllerErrors) value;
          cm.add(m);
        } else if (value instanceof ControllerMessages) {
          cm = (ControllerMessages) value;
        } else {
          throw new JspException("Cannot process ControllerMessage instance of class " + value.getClass().getName());
        }
      } catch (JspException e) {
        throw e;
      } catch (Exception e) {
        log.error("Unable to retieve ControllerMessage for paramName : " + paramName, e);
      }
    }
    return cm;
  }

  public Object lookup(PageContext pageContext, String name, String scopeName) throws JspException {
    if (scopeName == null) {
      return pageContext.findAttribute(name);
    }
    try {
      return pageContext.getAttribute(name, instance.getScope(scopeName));
    } catch (JspException e) {
      saveException(pageContext, e);
      throw e;
    }
  }

  public boolean present(PageContext pageContext, String locale, String key) {
    Locale userLocale = getUserLocale((HttpServletRequest) pageContext.getRequest(), locale);
    return this.isPresent(userLocale, key);
  }

  public boolean isPresent(Locale locale, String key) {
    String message = MessageResourcesFactory.getMessage(locale, key);
    if (message == null) {
      return false;
    } else if (message.startsWith("???") && message.endsWith("???")) {
      return false; // FIXME - Only valid for default implementation
    } else {
      return true;
    }
  }

  public String message(PageContext pageContext, String locale, String key) {
    return this.message(pageContext, locale, key, null);
  }

  public String message(PageContext pageContext, String locale, String key, Object[] args) {
    Locale userLocale = getUserLocale((HttpServletRequest) pageContext.getRequest(), locale);
    String message = null;
    if (Utility.isEmpty(args)) {
      message = MessageResourcesFactory.getMessage(userLocale, key);
    } else {
      message = MessageResourcesFactory.getMessage(userLocale, key, args);
    }
    return message;
  }

  public Locale getUserLocale(HttpServletRequest request, String locale) {
    Locale userLocale = null;
    if (locale == null) {
      locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
    }
    // Only check session if sessions are enabled
    userLocale = (Locale) WebUtils.getSessionAttribute(request, locale);
    if (userLocale == null) {
      // Returns Locale based on Accept-Language header or the server default
      userLocale = Utility.parseLocaleString(Utility.getClientLocal(request));
    }
    return userLocale;
  }

  public void write(PageContext pageContext, String text) throws JspException {
    JspWriter writer = pageContext.getOut();
    try {
      writer.print(text);
    } catch (IOException e) {
      saveException(pageContext, e);
      throw new JspException("Input/output error: " + e.toString());
    }
  }

  public void saveException(PageContext pageContext, Throwable exception) {
    pageContext.setAttribute(GlobalConstant.EXCEPTION_KEY, exception, PageContext.REQUEST_SCOPE);
  }

  public int getScope(String scopeName) throws JspException {
    Integer scope = (Integer) scopes.get(scopeName.toLowerCase());
    if (scope == null) {
      throw new JspException("Invalid bean scope: " + scope);
    }
    return scope.intValue();
  }

}
