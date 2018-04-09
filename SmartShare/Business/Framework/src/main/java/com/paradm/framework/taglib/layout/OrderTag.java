package com.paradm.framework.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.Utility;
import com.paradm.framework.taglib.TagUtils;

public class OrderTag extends TagSupport {

  private static final long serialVersionUID = 1L;

  protected String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
  protected String[][] labelMap = null;
  protected String sortOrder = "";
  protected String sortAttribute = "";
  protected String url = "";
  protected String divID = "";
  protected String formId = "";
  private String basicSortOrder = "";

  @Override
  public int doStartTag() throws JspException {
    StringBuffer sb = new StringBuffer();
    sb.append("<div class=\"btn-group\">");
    sb.append("<button data-toggle=\"dropdown\" type=\"button\" class=\"name_btn\">");
    if (!Utility.isEmpty(labelMap)) {
      for (String[] str : labelMap) {
        if (sortAttribute.equals(str[1])) {
          sb.append(TagUtils.getInstance().message(pageContext, locale, str[0]));
          if (sortOrder.equals("ASC")) {
            sb.append(" <span class=\"caret-down\"></span>");
          } else {
            sb.append(" <span class=\"caret-up\"></span>");
          }
        }
      }
    }
    sb.append("</button>");
    if (!Utility.isEmpty(labelMap)) {
      sb.append("<ul class=\"name_date_dropdown-menu\">");
      for (String[] str : labelMap) {
        String sort = "ASC";
        if (!Utility.isEmpty(basicSortOrder)) {
          sort = basicSortOrder;
        }
        if (sortAttribute.equals(str[1])) {
          if (sortOrder.equals("ASC")) {
            sort = "DESC";
          } else {
            sort = "ASC";
          }
        }
        if (sortAttribute.equals(str[1])) {
          if (sortOrder.equals("ASC")) {
            sb.append("<li><a href=\"javascript:void(0)\" onclick=\"reOrderList('" + Utility.escapeXml(str[1]) + "','" + sort + "','" + Utility.escapeXml(url) + "','" + divID + "','" + formId
                + "')\" >" + TagUtils.getInstance().message(pageContext, locale, str[0]) + " <i class=\"caret-down\"></i></a></li>");
          } else {
            sb.append("<li><a href=\"javascript:void(0)\" onclick=\"reOrderList('" + Utility.escapeXml(str[1]) + "','" + sort + "','" + Utility.escapeXml(url) + "','" + divID + "','" + formId
                + "')\" >" + TagUtils.getInstance().message(pageContext, locale, str[0]) + " <i class=\"caret-up\"></i></a></li>");
          }
        } else {
          sb.append("<li><a href=\"javascript:void(0)\" onclick=\"reOrderList('" + Utility.escapeXml(str[1]) + "','" + sort + "','" + Utility.escapeXml(url) + "','" + divID + "','" + formId
              + "')\">   " + TagUtils.getInstance().message(pageContext, locale, str[0]) + "</a></li>");
        }
      }
      sb.append("</ul>");
    }
    sb.append("</div>");
    TagUtils.getInstance().write(pageContext, sb.toString());
    return SKIP_BODY;
  }

  public String[][] getLabelMap() {
    return labelMap;
  }

  public void setLabelMap(String[][] labelMap) {
    this.labelMap = labelMap;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getSortAttribute() {
    return sortAttribute;
  }

  public void setSortAttribute(String sortAttribute) {
    this.sortAttribute = sortAttribute;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getBasicSortOrder() {
    return basicSortOrder;
  }

  public void setBasicSortOrder(String basicSortOrder) {
    this.basicSortOrder = basicSortOrder;
  }
}
