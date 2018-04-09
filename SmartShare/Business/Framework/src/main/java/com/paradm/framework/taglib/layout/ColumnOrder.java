package com.paradm.framework.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.Utility;
import com.paradm.framework.taglib.TagUtils;

public class ColumnOrder extends TagSupport {
  private static final long serialVersionUID = 1L;

  protected String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
  protected String[][] labelMap = null;
  protected String sortOrder = "";
  protected String divID = "";
  protected String formId = "";

  @Override
  public int doStartTag() throws JspException {
    if (!Utility.isEmpty(labelMap)) {
      String[] str = labelMap[0];
      StringBuffer sb = new StringBuffer();
      sb.append("<div class=\"btn-group\">");
      String sort = "";
      if (sortOrder.equalsIgnoreCase("ASC")) {
        sort = "DESC";
      } else {
        sort = "ASC";
      }
      sb.append("<button type=\"button\" class=\"name_btn\" onclick=\"reOrderList('" + Utility.escapeXml(str[1]) + "','" + sort + "','" + null + "','" + divID + "','" + formId + "')\">");

      sb.append(TagUtils.getInstance().message(pageContext, locale, str[0]));
      if (sortOrder.equalsIgnoreCase("ASC")) {
        sb.append(" <span class=\"caret-down\"></span>");
      } else {
        sb.append(" <span class=\"caret-up\"></span>");
      }
      sb.append("</button></div>");
      TagUtils.getInstance().write(pageContext, sb.toString());
    }

    return SKIP_BODY;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String[][] getLabelMap() {
    return labelMap;
  }

  public void setLabelMap(String[][] labelMap) {
    this.labelMap = labelMap;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getDivID() {
    return divID;
  }

  public void setDivID(String divID) {
    this.divID = divID;
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }


}
