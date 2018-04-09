package com.paradm.framework.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.MessageConstant;
import com.paradm.framework.taglib.TagUtils;

public class ClipboardDropMenu extends TagSupport {
  private static final long serialVersionUID = -1022785828197005686L;

  private static final Logger log = LoggerFactory.getLogger(ClipboardDropMenu.class);

  private String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

  public ClipboardDropMenu() {
    super();
  }

  @Override
  public int doStartTag() throws JspException {
    StringBuilder menu = new StringBuilder();
    menu.append("<span class=\"dropdown\"> <button class=\"btn btn-default btn-sm\" type=\"button\" style=\"outline:none;\"id=\"dropdownMenu1\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">");
    menu.append(TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_MORE) + "<span class=\"caret\"></span>");
    menu.append("</button> <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu1\" style=\"top:147%;\">");
    // menu.append(" <li style=\"cursor:pointer;\"><a id=\"clipRemove\">" +
    // TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_REMOVE) +
    // "</a></li>");
    menu.append(" <li style=\"cursor:pointer;\"><a id=\"clipDelete\">" + TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_DELETE) + "</a></li>");
    menu.append("  <li style=\"cursor:pointer;\"><a id=\"clipMove\">" + TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_MOVE) + "</a></li>");
    menu.append("  <li style=\"cursor:pointer;\"><a id=\"clipCopy\">" + TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_COPY) + "</a></li>");
    menu.append("<li role=\"separator\" class=\"divider\"></li>");
    menu.append("  <li style=\"cursor:pointer;\"><a id=\"clipCheck\">" + TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_OUT) + "</a></li>");
    menu.append("  <li style=\"cursor:pointer;\"><a id=\"clipFavorite\">" + TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_ADD_FAVORITE) + "</a></li>");
    // menu.append("  <li><a id=\"clipTag\">"+TagUtils.getInstance().message(pageContext, locale,
    // MessageConstant.DMS_LABEL_TAGS)+"</a></li> </ul> </span>");
    try {
      pageContext.getOut().print(menu.toString());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("DropdownMenuTag-->doStartTag end!");
    return SKIP_BODY;
  }
}
