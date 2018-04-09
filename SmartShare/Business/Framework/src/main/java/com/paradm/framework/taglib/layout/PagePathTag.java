package com.paradm.framework.taglib.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paradm.common.GlobalConstant;
import com.paradm.common.Utility;
import com.paradm.domain.framework.ApplicationContainer;
import com.paradm.domain.framework.model.SessionContainer;
import com.paradm.domain.system.model.SysFunctionModel;
import com.paradm.framework.taglib.TagUtils;

public class PagePathTag extends TagSupport implements TryCatchFinally {

  private static final long serialVersionUID = 1853536756437808193L;

  private static final Logger log = LoggerFactory.getLogger(PagePathTag.class);

  public static final String NAVIGATION_FOLDER_LOCATION_KEY = "NAVIGATION_FOLDER_LOCATION";
  protected String m_sActionStr = "";
  protected boolean m_bDisplayActionStr = true;
  protected String m_sActionURL = "";
  protected String m_sFunctionCode = null;
  protected boolean m_bLinkFlag = true;
  protected String m_sOptionStr = "";
  protected String m_sSeparator = "\\";
  protected String m_sFunctionType = "";

  public PagePathTag() {
    super();
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      // Action string is handled by PageTitleTag
      init();
      pageContext.getOut().print(getLocationPath());
      return (SKIP_BODY);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new JspException(e);
    }
  }

  protected String getLocationPath() throws JspException {
    String functionLocation = "";
    String folderLocation = (String) pageContext.getRequest().getAttribute(NAVIGATION_FOLDER_LOCATION_KEY);
    if (!Utility.isEmpty(folderLocation)) {
      functionLocation = getSystemFunctionPath(true);
      StringBuffer buffer = new StringBuffer();
      buffer.append("<script type=\"text/javascript\">");
      buffer.append("  var funcLocation = \"").append(Utility.escapeJSString(functionLocation)).append("\";\n");
      buffer.append("  function fullLocation(folderStr) {");
      // buffer.append(" alert(folderStr);");
      buffer.append("    var fullLocation = funcLocation + folderStr;");
      buffer.append("    $('#pagePath').html(fullLocation);");
      buffer.append("  }");
      buffer.append("</script>\n");
      buffer.append("<ol id='pagePath' class='pagepath_breadcrumb'>").append(functionLocation).append(folderLocation).append("</ol>");
      return (buffer.toString());
    }
    functionLocation = getSystemFunctionPath(false);
    return (getGeneralLocation(functionLocation));
  }

  protected String getGeneralLocation(String functionLocation) throws javax.servlet.jsp.JspException {
    StringBuffer buffer = new StringBuffer("<ol class='pagepath_breadcrumb'>");
    buffer.append(functionLocation);
    // Define the action of the current form
    if (m_bDisplayActionStr) {
      buffer.append(" - " + m_sActionStr);
    }
    // Define additional function type if passed in
    if (!Utility.isEmpty(m_sFunctionType)) {
      if (!Utility.isEmpty(functionLocation)) {
        buffer.append(" - " + m_sFunctionType);
      } else {
        buffer.append(m_sFunctionType);
      }
    }
    buffer.append("</ol>");
    return (buffer.toString());

  }

  protected String getSystemFunctionPath(boolean hasFolder) throws JspException {
    HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
    SessionContainer sessionContainer = (SessionContainer) this.pageContext.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
    ApplicationContainer applicationContainer = sessionContainer.getAppContainer();

    String curFunctionCode = (String) request.getAttribute(GlobalConstant.FUNCTION_CODE_KEY);
    if (Utility.isEmpty(this.m_sFunctionCode)) {
      this.m_sFunctionCode = curFunctionCode;
    }

    SysFunctionModel sysFunctionModel = applicationContainer.getSysFunctionByFunctionCode(this.m_sFunctionCode);
    if (Utility.isEmpty(sysFunctionModel)) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    initActionTypeInfo(request, sysFunctionModel);
    if (hasFolder && m_bLinkFlag && !(Utility.isEmpty(sysFunctionModel.getLinkUrl()))) {
      buffer.append("<li class='abc_text'><a class='pagePath' onclick='removeCompanyCookie();removePersonalCookie();' href='").append(request.getContextPath()).append(m_sActionURL).append("'>")
          .append(TagUtils.getInstance().message(pageContext, null, sysFunctionModel.getFunctionLabel())).append("</a></li>");
    } else {
      buffer.append("<li class='abc_text'>").append(TagUtils.getInstance().message(pageContext, null, sysFunctionModel.getFunctionLabel())).append("</li>");
    }
    while (Utility.parseInteger(sysFunctionModel.getParentId()).intValue() != 0) {
      SysFunctionModel parentFunctionModel = applicationContainer.getSysFunctionByFunctionId(sysFunctionModel.getParentId());
      if (Utility.isEmpty(parentFunctionModel)) {
        break;
      }
      if (Utility.parseInteger(parentFunctionModel.getParentId()).intValue() != 0) {
        if ((m_bLinkFlag) && !(Utility.isEmpty(parentFunctionModel.getLinkUrl()))) {
          buffer.insert(0, "<li><a class='pagePath' href='" + request.getContextPath() + parentFunctionModel.getLinkUrl() + "'>"
              + TagUtils.getInstance().message(pageContext, null, parentFunctionModel.getFunctionLabel()) + "</a></li>");
        } else {
          buffer.insert(0, "<li>" + TagUtils.getInstance().message(pageContext, null, parentFunctionModel.getFunctionLabel()) + "</li>");
        }
      }
      sysFunctionModel = parentFunctionModel;
    }
    return (buffer.toString());
  }

  protected void initActionTypeInfo(HttpServletRequest request, SysFunctionModel sysFunctionModel) {
    if (!Utility.isEmpty(sysFunctionModel)) {
      if (sysFunctionModel.getLinkUrl().indexOf("?") == -1) {
        m_sActionURL = sysFunctionModel.getLinkUrl();
      } else {
        m_sActionURL = sysFunctionModel.getLinkUrl();
      }
    }
  }

  protected void init() {
    setDisplayActionStr(false);
  }

  public void doCatch(Throwable arg0) throws Throwable {}

  public void doFinally() {
    this.release();
  }

  @Override
  public void release() {
    super.release();
    m_sActionStr = null;
    m_sActionURL = null;
    m_sFunctionCode = null;
    m_bLinkFlag = true;
    m_sOptionStr = null;
  }

  public String getFunctionCode() {
    return this.m_sFunctionCode;
  }

  public void setFunctionCode(String functionCode) {
    if (!Utility.isEmpty(functionCode)) {
      this.m_sFunctionCode = functionCode;
    }
  }

  public String getFunctionType() {
    return this.m_sFunctionType;
  }

  public void setFunctionType(String functionType) throws JspException {
    if (!Utility.isEmpty(functionType)) {
      this.m_sFunctionType = TagUtils.getInstance().message(pageContext, null, functionType);
    }
  }

  public boolean getLinkFlag() {
    return this.m_bLinkFlag;
  }

  public void setLinkFlag(boolean linkFlag) {
    this.m_bLinkFlag = linkFlag;
  }

  public boolean getDisplayActionStr() {
    return this.m_bDisplayActionStr;
  }

  public void setDisplayActionStr(boolean displayActionStr) {
    this.m_bDisplayActionStr = displayActionStr;
  }

  public String getOptionStr() {
    return this.m_sOptionStr;
  }

  public void setOptionStr(String optionStr) {
    this.m_sOptionStr = optionStr;
  }

  public String getSeparator() {
    return this.m_sSeparator;
  }

  public void setSeparator(String separator) {
    this.m_sSeparator = separator;
  }
}
