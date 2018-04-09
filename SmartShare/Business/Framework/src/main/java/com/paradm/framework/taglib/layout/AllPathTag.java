package com.paradm.framework.taglib.layout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paradm.common.GlobalConstant;
import com.paradm.common.Utility;
import com.paradm.common.enums.RootType;
import com.paradm.domain.dms.model.SmartshareDocumentModel;
import com.paradm.domain.ws.model.Ancestor;
import com.paradm.framework.taglib.TagUtils;

public class AllPathTag extends TagSupport {

  private static final long serialVersionUID = -4567445034651404151L;

  private static final Logger log = LoggerFactory.getLogger(AllPathTag.class);

  private SmartshareDocumentModel documentModel = null;
  protected boolean m_bLinkFlag = false;

  @Override
  public int doStartTag() throws JspException {
    log.debug("document path start...");
    String printPath = "";
    if (!Utility.isEmpty(this.getDocumentModel())) {
      if (this.getLinkFlag()) {
        printPath = getPathInTrash();
      } else {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        StringBuffer sb = new StringBuffer();
        sb.append("<ol class=\"properties_breadcrumb\">");
        String companyFolder = TagUtils.getInstance().message(pageContext, null, "system.label.dms_company");
        String myDocument = TagUtils.getInstance().message(pageContext, null, "system.label.dms_personal");
        sb.append("<li><a  href=\"javascript:void(0);\"");
        if (RootType.PERSONAL.toString().equals(this.getRootType())) {
          sb.append("onclick=\"goMyDocumentFloder('").append(request.getContextPath()).append("',event)\">").append(myDocument);
        } else {
          sb.append("onclick=\"goCompanyFloder('").append(request.getContextPath()).append("',event)\">").append(companyFolder);
        }
        sb.append("</a></li>");
        if (!Utility.isEmpty(this.getAncestors())) {
          int i = 0;
          for (Ancestor ancestor : this.getAncestors()) {
            if (RootType.PERSONAL.toString().equals(this.getRootType()) && GlobalConstant.ROOT_PARENT_ID.equals(ancestor.getParentID().toString())) {
              continue;
            }
            if (i == this.getAncestors().size() - 1) {
              sb.append("<li><a class=\"properties_breadcrumb_blacktext\" href=\"javascript:void(0);\"");
            } else {
              sb.append("<li><a href=\"javascript:void(0);\"");
            }
            if (RootType.PERSONAL.toString().equals(this.getRootType())) {
              sb.append("onclick=\"addPersonalCookie(").append(ancestor.getId()).append(");");
              sb.append("goMyDocumentFloder_('");
              sb.append(request.getContextPath()).append("',");
              sb.append(ancestor.getId()).append(",").append(ancestor.getRootID()).append(",event").append(");\">").append(ancestor.getDocName()).append("</a></li>");
            } else {
              sb.append("onclick=\"addCompanyCookie(").append(ancestor.getId()).append(");");
              sb.append("goCompanyFloder_('");
              sb.append(request.getContextPath()).append("',");
              sb.append(ancestor.getId()).append(",").append(ancestor.getRootID()).append(",event").append(");\">").append(ancestor.getDocName()).append("</a></li>");
            }
            i++;
          }
        }
        sb.append("</ol>");
        printPath = sb.toString();
      }
    }
    TagUtils.getInstance().write(pageContext, printPath);
    return SKIP_BODY;
  }

  private String getPathInTrash() throws JspException {
    StringBuffer sb = new StringBuffer();
    sb.append("<ol class=\"properties_breadcrumb \">");
    String companyFolder = TagUtils.getInstance().message(pageContext, null, "system.label.dms_company");
    String myDocument = TagUtils.getInstance().message(pageContext, null, "system.label.dms_personal");
    sb.append("<li><span class=\"properties_breadcrumb_blacktext\"");
    if (RootType.PERSONAL.toString().equals(this.getRootType())) {
      sb.append(">").append(myDocument);
    } else {
      sb.append(">").append(companyFolder);
    }
    sb.append("</span></li>");
    if (!Utility.isEmpty(this.getAncestors())) {
      for (Ancestor ancestor : this.getAncestors()) {
        if (RootType.PERSONAL.toString().equals(this.getRootType()) && GlobalConstant.ROOT_PARENT_ID.equals(ancestor.getParentID().toString())) {
          continue;
        }
        sb.append("<li><span class=\"properties_breadcrumb_blacktext\"");
        sb.append("\">").append(ancestor.getDocName()).append("</span></li>");
      }
    }
    sb.append("</ol>");
    return sb.toString();
  }

  public SmartshareDocumentModel getDocumentModel() {
    return documentModel;
  }

  public void setDocumentModel(SmartshareDocumentModel documentModel) {
    this.documentModel = documentModel;
  }

  public List<Ancestor> getAncestors() {
    return this.getDocumentModel().getAncestors();
  }

  public String getDocId() {
    return this.getDocumentModel().getId();
  }

  public String getRootType() {
    return this.getDocumentModel().getRootType();
  }

  public boolean getLinkFlag() {
    return this.m_bLinkFlag;
  }

  public void setLinkFlag(boolean linkFlag) {
    this.m_bLinkFlag = linkFlag;
  }
}
