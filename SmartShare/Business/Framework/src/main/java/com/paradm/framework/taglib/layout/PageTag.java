package com.paradm.framework.taglib.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.GlobalConstant;
import com.paradm.common.MessageConstant;
import com.paradm.common.Utility;
import com.paradm.framework.taglib.TagUtils;

public class PageTag<T> extends TagSupport {

  private static final long serialVersionUID = 1853536756437808193L;
  private static final Logger log = LoggerFactory.getLogger(PageTag.class);
  private String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
  private String paginationName = null;
  private String listPageResultDivId = null;
  private String requestMethod = "GET";
  private String formId = null;

  public PageTag() {
    super();
  }

  @SuppressWarnings("unchecked")
  @Override
  public int doStartTag() throws JspException {
    log.debug("pageTag doStartTag() start.....");
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    Page<T> pagination = (Page<T>) request.getAttribute(this.getPaginationName());
    StringBuffer strBuffer = new StringBuffer();
    Integer pageNumber = Utility.isEmpty(pagination) ? 0 : pagination.getNumber();
    strBuffer.append("<div class=\"ss-container\" style=\"text-align: right;\">");
    strBuffer.append("<input type=\"hidden\" id=\"hiddenPageNo\" value=\"" + (pageNumber + 1) + "\">");
    strBuffer.append("<ul class=\"pagination\" style=\"padding: 0px;font-size: 14px;\">");

    if (Utility.isEmpty(pagination) || Utility.isEmpty(pagination.getContent())) {
      log.debug("pagination object is null or pagination.getDatas() is null.");
    } else {
      if (pageNumber == 0) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:;\">&laquo;</a></li>");
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:;\">&lsaquo;</a></li>");
      } else {
        strBuffer.append("<li><a href=\"javascript:;\" onclick=\"").append(this.getListPageResultDivId()).append("_pageGoToPage(0)\">&laquo;</a></li>");
        strBuffer.append("<li><a href=\"javascript:;\" onclick=\"").append(this.getListPageResultDivId()).append("_pageGoToPage(" + (pageNumber - 1) + ")\">&lsaquo;</a></li>");
      }
      strBuffer
          .append(
              "<li><span aria-hidden=\"true\" style=\"padding:3px;\"><input class=\"pagination_textbox\" id=\"redirectPage_" + this.getListPageResultDivId()
                  + "\" style=\"height:26px;\" type=\"text\" value=\"" + (pageNumber + 1) + "/" + pagination.getTotalPages() + "\" size=\"4\" maxlength=\"5\" onkeypress=\"")
          .append(this.getListPageResultDivId()).append("_EnterPress(event);\"/><input type=\"text\" style=\"display:none\"/></span></li>");
      if (pageNumber + 1 == pagination.getTotalPages()) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:;\">&rsaquo;</a></li>");
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:;\">&raquo;</a></li>");
      } else {
        strBuffer.append("<li><a href=\"javascript:;\" onclick=\"").append(this.getListPageResultDivId()).append("_pageGoToPage(" + (pageNumber + 1) + ")\">&rsaquo;</a></li>");
        strBuffer.append("<li><a href=\"javascript:;\" onclick=\"").append(this.getListPageResultDivId()).append("_pageGoToPage(" + (pagination.getTotalPages() - 1) + ")\">&raquo;</a></li>");
      }
    }

    strBuffer.append("</ul>");
    strBuffer.append("</div>");

    if (!Utility.isEmpty(pagination) && !Utility.isEmpty(pagination.getContent())) {
      String noRange = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_MESSAGE_ALERT_BODY);
      String invalidateNumber = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_INVALIDATE_NUMBER);
      strBuffer.append("<script type=\"text/javascript\">");
      strBuffer.append("function ").append(this.getListPageResultDivId()).append("_pageGoToPage(pageNo) {");
      strBuffer.append("  var form = ").append("document.forms['").append(this.getFormId()).append("'];");
      strBuffer.append("  form.hiddenPageNo.value = pageNo;");
      strBuffer.append("  form.page.value = pageNo;");
      strBuffer.append("  var formParam = $(\"#" + this.getFormId() + "\").serialize();");
      strBuffer.append("  var url = $(\"#" + this.getFormId() + "\").attr(\"action\");");
      strBuffer.append("  $.ajax({type:\"" + this.getRequestMethod() + "\",url:url,cache:false,data:formParam,success:function(dataInfo){");
      strBuffer.append("    $(\"#" + this.getListPageResultDivId() + "\").html(\"\");");
      strBuffer.append("    $(\"#" + this.getListPageResultDivId() + "\").html(dataInfo);},error:function(data){alert(data);}});");
      strBuffer.append("} ");
      strBuffer.append("function ").append(this.getListPageResultDivId()).append("_EnterPress(e) {");
      strBuffer.append("  var form = ").append("document.forms['").append(this.getFormId()).append("'];");
      strBuffer.append("  var e = e || window.event;");
      strBuffer.append("  if(e.keyCode == 13) {");
      strBuffer.append("    var redirectPage = form.redirectPage_" + this.getListPageResultDivId() + ".value;");
      strBuffer.append("	redirectPage = redirectPage.trim();");
      strBuffer.append("	var re = /^[1-9]+[0-9]*]*$/;");
      strBuffer.append("	if(re.test(redirectPage)) {");
      strBuffer.append("	  if(redirectPage < 1 || redirectPage >" + pagination.getTotalPages() + ") {");
      strBuffer.append("        form.redirectPage_" + this.getListPageResultDivId() + ".value = form.hiddenPageNo.value;");
      strBuffer.append("        noticeMsg('").append(GlobalConstant.STATUS_SUCCESSFUL).append("', '").append(noRange).append(" ' + redirectPage);return false;");
      strBuffer.append("	  } else {");
      strBuffer.append("        form.hiddenPageNo.value = redirectPage;");
      strBuffer.append("        form.page.value = redirectPage - 1;");
      strBuffer.append("        var formParam = $(\"#" + this.getFormId() + "\").serialize();");
      strBuffer.append("        var url = $(\"#" + this.getFormId() + "\").attr(\"action\");");
      strBuffer.append("		$.ajax({type:\"" + this.getRequestMethod() + "\",url:url,cache:false,data:formParam,success:function(dataInfo){");
      strBuffer.append("          $(\"#" + this.getListPageResultDivId() + "\").html(\"\");");
      strBuffer.append("          $(\"#" + this.getListPageResultDivId() + "\").html(dataInfo);}});");
      strBuffer.append("	  }");
      strBuffer.append("	} else {");
      strBuffer.append("      form.redirectPage_" + this.getListPageResultDivId() + ".value = form.hiddenPageNo.value;");
      strBuffer.append("      noticeMsg('").append(GlobalConstant.STATUS_SUCCESSFUL).append("', '").append(invalidateNumber).append("');return false;");
      strBuffer.append("	}");
      strBuffer.append("  }");
      strBuffer.append("}");
      strBuffer.append("$('#redirectPage_" + this.getListPageResultDivId() + "').focus(");
      strBuffer.append("function(evt){");
      strBuffer.append("evt.stopPropagation();");
      strBuffer.append("$('#redirectPage_" + this.getListPageResultDivId() + "').val('" + (pagination.getNumber() + 1) + "')");
      strBuffer.append("}");
      strBuffer.append(");");
      strBuffer.append("$('#redirectPage_" + this.getListPageResultDivId() + "').blur(");
      strBuffer.append("function(evt){");
      strBuffer.append("evt.stopPropagation();");
      strBuffer.append("$('#redirectPage_" + this.getListPageResultDivId() + "').val('" + (pagination.getNumber() + 1) + "/" + pagination.getTotalPages() + "')");
      strBuffer.append("}");
      strBuffer.append(");");
      strBuffer.append("</script>");

    }
    try {
      pageContext.getOut().print(strBuffer.toString());
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    log.debug("doStartTag() end!");
    return SKIP_BODY;
  }

  @Override
  public void release() {
    super.release();
  }

  public String getPaginationName() {
    return paginationName;
  }

  public void setPaginationName(String paginationName) {
    this.paginationName = paginationName;
  }

  public String getListPageResultDivId() {
    return listPageResultDivId;
  }

  public void setListPageResultDivId(String listPageResultDivId) {
    this.listPageResultDivId = listPageResultDivId;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }
}
