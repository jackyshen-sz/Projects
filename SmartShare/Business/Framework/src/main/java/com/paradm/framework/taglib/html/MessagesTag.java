package com.paradm.framework.taglib.html;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.GlobalConstant;
import com.paradm.framework.taglib.ControllerMessage;
import com.paradm.framework.taglib.ControllerMessages;
import com.paradm.framework.taglib.TagUtils;

public class MessagesTag extends TagSupport {

  private static final long serialVersionUID = 2546874806504725323L;

  private static final String SUFFIX = "common.message.suffix";
  private static final String PREFIX = "common.message.prefix";
  private static final String FOOTER = "common.message.footer";
  private static final String HEADER = "common.message.header";

  protected String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
  protected String name = GlobalConstant.MESSAGE_KEY;
  protected String property = null;
  protected String header = null;
  protected String footer = null;
  protected String prefix = null;
  protected String suffix = null;

  public MessagesTag() {
    super();
  }

  public String getLocale() {
    return (this.locale);
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getName() {
    return (this.name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProperty() {
    return (this.property);
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getHeader() {
    return (header == null) ? HEADER : header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getFooter() {
    return (footer == null) ? FOOTER : footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

  public String getPrefix() {
    return (prefix == null) ? PREFIX : prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return (suffix == null) ? SUFFIX : suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  @Override
  public int doStartTag() throws JspException {
    ControllerMessages messages = null;
    try {
      messages = TagUtils.getInstance().getControllerMessages(pageContext, name);
    } catch (JspException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw e;
    }
    if ((messages == null) || messages.isEmpty()) {
      return (EVAL_BODY_INCLUDE);
    }
    boolean headerPresent = TagUtils.getInstance().present(pageContext, locale, getHeader());
    boolean footerPresent = TagUtils.getInstance().present(pageContext, locale, getFooter());
    boolean prefixPresent = TagUtils.getInstance().present(pageContext, locale, getPrefix());
    boolean suffixPresent = TagUtils.getInstance().present(pageContext, locale, getSuffix());
    StringBuffer results = new StringBuffer();
    boolean headerDone = false;
    String message = null;
    Iterator<ControllerMessage> reports = (property == null) ? messages.get() : messages.get(property);
    while (reports.hasNext()) {
      ControllerMessage report = reports.next();
      if (!headerDone) {
        if (headerPresent) {
          message = TagUtils.getInstance().message(pageContext, locale, getHeader());
          results.append(message);
        }
        headerDone = true;
      }
      if (prefixPresent) {
        message = TagUtils.getInstance().message(pageContext, locale, getPrefix());
        results.append(message);
      }

      if (report.isResource()) {
        message = TagUtils.getInstance().message(pageContext, locale, report.getKey(), report.getValues());
      } else {
        message = report.getKey();
      }

      if (message != null) {
        results.append(message);
      }

      if (suffixPresent) {
        message = TagUtils.getInstance().message(pageContext, locale, getSuffix());
        results.append(message);
      }
    }
    if (headerDone && footerPresent) {
      message = TagUtils.getInstance().message(pageContext, locale, getFooter());
      results.append(message);
    }
    TagUtils.getInstance().write(pageContext, results.toString());
    return (EVAL_BODY_INCLUDE);
  }

  @Override
  public int doEndTag() throws JspException {
    return super.doEndTag();
  }

  @Override
  public void release() {
    super.release();
    locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
    name = GlobalConstant.MESSAGE_KEY;
    property = null;
    header = null;
    footer = null;
    prefix = null;
    suffix = null;
  }

}
