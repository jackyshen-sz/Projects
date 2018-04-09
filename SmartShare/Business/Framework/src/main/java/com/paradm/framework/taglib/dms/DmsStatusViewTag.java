package com.paradm.framework.taglib.dms;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.GlobalConstant;
import com.paradm.common.MessageConstant;
import com.paradm.common.MessageResourcesFactory;
import com.paradm.common.Utility;
import com.paradm.common.dms.PermissionConstant;
import com.paradm.common.enums.DocMenuOperation;
import com.paradm.common.enums.DocumentType;
import com.paradm.common.enums.NotifyType;
import com.paradm.common.enums.Status;
import com.paradm.common.enums.YesNoFlag;
import com.paradm.domain.dms.model.SmartshareDocAlertModel;
import com.paradm.domain.dms.model.SmartshareDocCheckoutHistoryModel;
import com.paradm.domain.dms.model.SmartshareDocCommentModel;
import com.paradm.domain.dms.model.SmartshareDocumentModel;
import com.paradm.domain.framework.model.SessionContainer;
import com.paradm.framework.taglib.TagUtils;
import com.paradm.services.dms.SmartshareDocAlertService;
import com.paradm.services.dms.SmartshareDocCheckoutHistoryService;
import com.paradm.services.dms.SmartshareDocCommentService;

public class DmsStatusViewTag extends TagSupport {

  private static final long serialVersionUID = 1853536756437808193L;

  private static final Logger log = LoggerFactory.getLogger(DmsStatusViewTag.class);

  private String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

  private SmartshareDocumentModel document = null;
  private SessionContainer sessionContainer;

  @Override
  public int doStartTag() throws JspException {
    sessionContainer = (SessionContainer) this.pageContext.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    SmartshareDocCommentService smartshareDocCommentService = (SmartshareDocCommentService) wac.getBean("smartshareDocCommentServiceImpl");
    SmartshareDocCheckoutHistoryService smartshareDocCheckoutHistoryService = (SmartshareDocCheckoutHistoryService) wac.getBean("smartshareDocCheckoutHistoryServiceImpl");
    StringBuilder tagContent = new StringBuilder();
    tagContent.append("<div style=\"line-height:20px;\" id =\"dmsStatusViewDiv\">");
    if (Status.LOCKED.toString().equals(document.getItemStatus())) {
      SmartshareDocCheckoutHistoryModel smartshareDocCheckout = smartshareDocCheckoutHistoryService.getCheckoutDetail(Utility.parseInteger(document.getId()));
      if (!Utility.isEmpty(smartshareDocCheckout)) {
        String auditTrailMsg = MessageResourcesFactory.getMessage(sessionContainer.getCurrentLocale(), MessageConstant.DMS_MESSAGE_CHECK_OUT_AUDIT,
            new String[] {smartshareDocCheckout.getUpdateName(), smartshareDocCheckout.getCheckoutTime()});
        document.setCheckoutAudit(auditTrailMsg);
      }
      tagContent.append("<span class=\"ss-modified-name ss-cursor\"  data-toggle=\"tooltip\" ");
      tagContent.append("title=\"");
      tagContent.append(document.getCheckoutAudit());
      tagContent.append("\"");
      if (this.hasPermission(PermissionConstant.PERMISSION_CHECK_IN) && !Utility.isEmpty(document.getCheckoutUserId()) && document.getCheckoutUserId().equals(sessionContainer.getUserRecordID())) {
        tagContent.append("onclick=\"checkIn('");
        tagContent.append(pageContext.getServletContext().getContextPath()).append("','");
        String checkInTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_IN);
        tagContent.append(checkInTitle).append("','");
        String checkInButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_IN);
        String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
        tagContent.append(checkInButtonLabel).append("','");
        tagContent.append(cancelButtonLabel).append("','");
        tagContent.append(document.getId()).append("','");
        tagContent.append(document.getParentId()).append("',event)\"");
      }
      tagContent.append(">");
      tagContent.append("<img src=\"").append(pageContext.getServletContext().getContextPath()).append("/img/content/lock.png\" width=\"17\" height=\"17\" alt=\"\"/>");
      tagContent.append("</span>");
      tagContent.append("&nbsp;&nbsp;");
    }
    if (DocumentType.FOLDER.toString().equals(document.getDocType())) {
      List<SmartshareDocCommentModel> docCommentList = smartshareDocCommentService.getListByDocIdAndVersionId(Utility.parseInteger(document.getId()), Utility.parseInteger(document.getCurVersionId()));
      if (!Utility.isEmpty(docCommentList) && this.hasPermission(PermissionConstant.PERMISSION_READ_COMMENT)) {
        tagContent.append("<span class=\"ss-modified-name ss-cursor\" onclick=\"makeComment('");
        tagContent.append(pageContext.getServletContext().getContextPath()).append("','");
        String makeCommentTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_MAKE_COMMENT);
        tagContent.append(makeCommentTitle).append("','");
        String saveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_SAVE);
        String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
        tagContent.append(saveButtonLabel).append("','");
        tagContent.append(cancelButtonLabel).append("','");
        tagContent.append(document.getId()).append("','");
        tagContent.append(document.getParentId()).append("',event)\">");
        tagContent.append("<img src=\"").append(pageContext.getServletContext().getContextPath()).append("/img/content/comment.png\" width=\"17\" height=\"17\" alt=\"\"/>");
        tagContent.append("</span>");
        tagContent.append("&nbsp;&nbsp;");
      }
    }
    if (YesNoFlag.YES.toString().equals(document.getIsShare())) {
      String shareDocTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_SHARE_TITLE);
      String closeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
      tagContent.append("<span class=\"ss-modified-name ss-cursor\" onclick=\"showDocShareDetails('");
      tagContent.append(pageContext.getServletContext().getContextPath()).append("','");
      tagContent.append(shareDocTitle).append("','");
      tagContent.append(closeButtonLabel).append("','");
      tagContent.append(document.getId()).append("','");
      tagContent.append(document.getParentId()).append("',");
      tagContent.append("event)\">");
      tagContent.append("<img src=\"").append(pageContext.getServletContext().getContextPath()).append("/img/content/share.png\" width=\"17\" height=\"17\" alt=\"\"/>");
      tagContent.append("</span>");
      tagContent.append("&nbsp;&nbsp;");
    }
    SmartshareDocAlertService smartshareDocAlertService = (SmartshareDocAlertService) wac.getBean("smartshareDocAlertServiceImpl");
    SmartshareDocAlertModel docAlertModel = smartshareDocAlertService.getByDocIdAndNotifyTypeAndObjectId(document.getId(), NotifyType.ALERT_ME, sessionContainer.getUserRecordID());
    if (!Utility.isEmpty(docAlertModel) && this.hasPermission(PermissionConstant.PERMISSION_ALERT)) {
      tagContent.append("<span class=\"ss-modified-name ss-cursor\" onclick=\"alertMe('");
      tagContent.append(pageContext.getServletContext().getContextPath()).append("','");
      String alertMeTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_STOP_ALERT_ME);
      tagContent.append(alertMeTitle).append("','");
      String okButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_YES);
      String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_NO);
      tagContent.append(okButtonLabel).append("','");
      tagContent.append(cancelButtonLabel).append("','");
      tagContent.append(document.getId()).append("','");
      tagContent.append(document.getParentId()).append("','");
      tagContent.append(DocMenuOperation.STOP_ALERTME.toString()).append("',event)\">");
      tagContent.append("<img src=\"").append(pageContext.getServletContext().getContextPath()).append("/img/content/alert.png\" width=\"17\" height=\"17\" alt=\"\"/>");
      tagContent.append("</span>");
    }
    tagContent.append("</div>");
    TagUtils.getInstance().write(pageContext, tagContent.toString());
    return SKIP_BODY;
  }

  public boolean hasPermission(String rightStr) {
    String permission = document.getPermissionString();
    log.debug("document id {} 's permission is : {}", document.getId(), permission);
    String right = this.getDocumentPermissionBySingle(document.getParentId(), DocumentType.fromAcronym(document.getDocType()), rightStr);
    return (!Utility.isEmpty(permission)) && (permission.indexOf(right) != -1);
  }

  public String getDocumentPermissionBySingle(String parentId, DocumentType documentType, String single) {
    if (Utility.isEmpty(single) || Utility.isEmpty(parentId) || Utility.isEmpty(documentType)) {
      return "";
    }
    if (DocumentType.DOCUMENT.equals(documentType)) {
      return PermissionConstant.PREFIX_DOCUMENT_PERMISSION + single;
    } else {
      if (GlobalConstant.ROOT_PARENT_ID.equals(parentId)) {
        return PermissionConstant.PREFIX_ROOT_PERMISSION + single;
      } else {
        return PermissionConstant.PREFIX_FOLDER_PERMISSION + single;
      }
    }
  }

  public SmartshareDocumentModel getDocument() {
    return document;
  }

  public void setDocument(SmartshareDocumentModel document) {
    this.document = document;
  }

}
