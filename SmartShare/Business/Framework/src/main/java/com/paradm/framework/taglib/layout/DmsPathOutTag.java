package com.paradm.framework.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.GlobalConstant;
import com.paradm.common.MessageConstant;
import com.paradm.common.Utility;
import com.paradm.common.enums.RootType;
import com.paradm.framework.taglib.TagUtils;

public class DmsPathOutTag extends TagSupport implements TryCatchFinally {

  private static final long serialVersionUID = -488334904695256107L;

  private static final Logger log = LoggerFactory.getLogger(DmsPathOutTag.class);

  private String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

  private String path = null;
  private String rootType = null;
  private String spanId = null;

  public DmsPathOutTag() {
    super();
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      pageContext.getOut().print(getLocationPath());
      return (SKIP_BODY);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new JspException(e);
    }
  }

  public String getLocationPath() {
    String[] paths = Utility.splitString(this.getPath(), GlobalConstant.CLOSE_ANGLE);
    StringBuilder sb = new StringBuilder("");
    StringBuilder fullPath = new StringBuilder(this.getPath());
    sb.append("<span id='dmsPath_").append(this.getSpanId()).append("'>\n");
    if (!Utility.isEmpty(this.getRootType())) {
      String rootName = TagUtils.getInstance().message(pageContext, locale, MessageConstant.SYSTEM_LABEL_DMS_COMPANY);
      if (RootType.PERSONAL.toString().equals(this.getRootType())) {
        rootName = TagUtils.getInstance().message(pageContext, locale, MessageConstant.SYSTEM_LABEL_DMS_PERSONAL);
      }
      sb.append(rootName);
      fullPath.insert(0, rootName + GlobalConstant.BLANK + GlobalConstant.CLOSE_ANGLE + GlobalConstant.BLANK);
    }
    if (!Utility.isEmpty(paths)) {
      boolean hasEllipsis = false;
      if (!Utility.isEmpty(this.getRootType())) {
        if (paths.length > 2) {
          hasEllipsis = true;
        }
        sb.append(GlobalConstant.BLANK).append(GlobalConstant.CLOSE_ANGLE).append(GlobalConstant.BLANK);
      } else {
        if (paths.length > 3) {
          hasEllipsis = true;
        }
      }
      sb.append(paths[0]);
      if (!hasEllipsis) {
        for (int i = 1; i < paths.length; i++) {
          sb.append(GlobalConstant.BLANK).append(GlobalConstant.CLOSE_ANGLE).append(GlobalConstant.BLANK);
          sb.append(paths[i]);
        }
      } else {
        sb.append(GlobalConstant.BLANK).append(GlobalConstant.CLOSE_ANGLE).append(GlobalConstant.BLANK);
        sb.append(paths[1]);
        sb.append(GlobalConstant.BLANK).append(GlobalConstant.CLOSE_ANGLE).append(GlobalConstant.BLANK);
        sb.append("<a href='javascript:void(0);' onclick=\"showFullLocation('");
        sb.append(Utility.escapeJSString(fullPath.toString())).append("','dmsPath_").append(this.getSpanId()).append("');\">...</a>");
        sb.append(GlobalConstant.BLANK).append(GlobalConstant.CLOSE_ANGLE).append(GlobalConstant.BLANK);
        sb.append(paths[paths.length - 1]);
      }
    }
    sb.append("</span>\n");
    return sb.toString();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getRootType() {
    return rootType;
  }

  public void setRootType(String rootType) {
    this.rootType = rootType;
  }

  public String getSpanId() {
    return spanId;
  }

  public void setSpanId(String spanId) {
    this.spanId = spanId;
  }

  public void doCatch(Throwable arg0) throws Throwable {}

  public void doFinally() {
    this.release();
  }

  @Override
  public void release() {
    super.release();
    path = null;
    rootType = null;
    spanId = null;
  }

}
