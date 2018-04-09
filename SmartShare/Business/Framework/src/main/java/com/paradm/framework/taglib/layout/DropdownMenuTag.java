package com.paradm.framework.taglib.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.paradm.common.GlobalConstant;
import com.paradm.common.MessageConstant;
import com.paradm.common.Utility;
import com.paradm.common.dms.PermissionConstant;
import com.paradm.common.enums.DocMenuOperation;
import com.paradm.common.enums.DocumentType;
import com.paradm.common.enums.NotifyType;
import com.paradm.common.enums.RootType;
import com.paradm.common.enums.Status;
import com.paradm.common.enums.SystemFunctionCode;
import com.paradm.domain.dms.model.SmartshareDocAlertModel;
import com.paradm.domain.dms.model.SmartshareDocumentModel;
import com.paradm.domain.framework.model.SessionContainer;
import com.paradm.framework.taglib.TagUtils;
import com.paradm.services.dms.SmartshareDocAlertService;

public class DropdownMenuTag extends TagSupport {

  private static final long serialVersionUID = -204434339837264275L;

  private static final Logger log = LoggerFactory.getLogger(DropdownMenuTag.class);

  private String locale = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
  private SmartshareDocumentModel documentModel = null;
  private SessionContainer sessionContainer;
  private boolean isMobile = false;

  public DropdownMenuTag() {
    super();
  }

  @Override
  public int doStartTag() throws JspException {
    log.debug("DropdownMenuTag-->doStartTag start...");
    HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
    sessionContainer = (SessionContainer) this.pageContext.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
    boolean hasAccessRight = sessionContainer.hasAccessibleSystemFunction(SystemFunctionCode.SHARING.toString());
    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    String permission = this.getPermission();
    isMobile = (boolean) request.getAttribute("ISMOBILE");
    log.debug("permission {}", permission);
    StringBuffer strBuffer = new StringBuffer();
    strBuffer.append("<div align=\"right\">");
    strBuffer.append("<div class=\"btn-group\" id=\"dropDown_" + documentModel.getId() + "\" >");
    strBuffer.append("<button type=\"button\" class=\"SS_gray_arrow_box\" id=\"drop_" + documentModel.getId() + "\" onclick = \"initDropMenu(this.id,event)\"");
    strBuffer.append("data-toggle=\"dropdown\" aria-expanded=\"false\">");
    strBuffer.append("<img src=\"").append(pageContext.getServletContext().getContextPath()).append("/img/content/menu_dropdown.png\" width=\"9\" height=\"12\" alt=\"\"/>");
    strBuffer.append("</button>");
    strBuffer.append("<ul id=\"menu_" + documentModel.getId() + "\" class=\"SS_dropdown-menu SS_dropdown-menu-right\" role=\"menu\" >");
    boolean fisrtPart = false;
    boolean secondPart = false;
    boolean thirdPart = false;
    boolean fourthPart = false;
    boolean fifthPart = false;
    // boolean sixthPart = false;
    boolean isLocked = false;
    boolean hasDownload = false;
    boolean hasCheckOut = false;
    boolean hasCheckIn = false;
    boolean hasUnCheckOut = false;
    if (Status.LOCKED.toString().equals(this.getItemStatus())) {
      isLocked = true;
      if (this.getDocumentModel().getCheckoutUserId().equals(sessionContainer.getUserRecordID())) {
        hasDownload = true;
        hasCheckIn = true;
        hasUnCheckOut = true;
      }
    } else {
      hasDownload = true;
      hasCheckOut = true;
      if (RootType.PERSONAL.toString().equals(this.getRootType())) {
        hasCheckIn = true;
        hasCheckOut = false;
      }
    }
    String noPermission = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_MESSAGE_DROPDOWN_NO_PERMISSION);
    if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
      fisrtPart = true;
      String instancePreviewTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_INSTANCE_PREVIEW);
      String unavailablePreviewTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_UNAVAILABLE_PREVIEW);
      if (Utility.isEmpty(permission)) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + unavailablePreviewTitle + "</a></li>");
      } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_PREVIEW)) {
        strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"instancePreview('");
        strBuffer.append(request.getContextPath()).append("','");
        strBuffer.append(this.getDocId()).append("')\">");
        strBuffer.append(instancePreviewTitle).append("</a></li>");
      } else {
        strBuffer.append("<li class=\"disabled\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\"><a href=\"javascript:void(0);\">" + unavailablePreviewTitle + "</a></li>");
      }
      if (hasDownload) {
        String downTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_DOWNLOAD);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + downTitle + "</a></li>");
        } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_DOWNLOAD)) {
          // if (isMobile) {
          // //
          // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
          // +
          // // downTitle + "</a></li>");
          // } else {
          strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"downloadDoc('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("','");
          strBuffer.append(this.getContentId()).append("')\">");
          strBuffer.append(downTitle).append("</a></li>");
          // }
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + downTitle + "</a></li>");
        }
      }

      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        if (hasCheckOut) {
          String checkOutTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_OUT);
          if (Utility.isEmpty(permission)) {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + checkOutTitle + "</a></li>");
          } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_CHECK_OUT)) {
            if (isMobile) {
              // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
              // +
              // checkOutTitle + "</a></li>");
            } else {
              String downloadButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_DOWNLOAD);
              String viewButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_VIEW);
              strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"checkOut('");
              strBuffer.append(request.getContextPath()).append("','");
              strBuffer.append(checkOutTitle).append("','");
              strBuffer.append(downloadButtonLabel).append("','");
              strBuffer.append(viewButtonLabel).append("','");
              strBuffer.append(this.getDocId()).append("','");
              strBuffer.append(this.getParentId()).append("')\">");
              strBuffer.append(checkOutTitle).append("</a></li>");
            }
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + checkOutTitle + "</a></li>");
          }
        }
        if (hasCheckIn) {
          String checkInTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_IN);
          if (Utility.isEmpty(permission)) {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + checkInTitle + "</a></li>");
          } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_CHECK_IN)) {
            if (isMobile) {
              // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
              // +
              // checkInTitle + "</a></li>");
            } else {
              String checkInButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_CHECK_IN);
              String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
              strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"checkIn('");
              strBuffer.append(request.getContextPath()).append("','");
              strBuffer.append(checkInTitle).append("','");
              strBuffer.append(checkInButtonLabel).append("','");
              strBuffer.append(cancelButtonLabel).append("','");
              strBuffer.append(this.getDocId()).append("','");
              strBuffer.append(this.getParentId()).append("')\">");
              strBuffer.append(checkInTitle).append("</a></li>");
            }
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + checkInTitle + "</a></li>");
          }
        }
        if (hasUnCheckOut) {
          strBuffer.append("<li class=\"divider\"></li>");
          String undoCheckOutTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_UNDO_CHECK_OUT);
          if (Utility.isEmpty(permission)) {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + undoCheckOutTitle + "</a></li>");
          } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_UNDO_CHECK_OUT)) {
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"undoCheckOut('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(undoCheckOutTitle).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(undoCheckOutTitle).append("</a></li>");
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + undoCheckOutTitle + "</a></li>");
          }
        }
      }
    }
    if (fisrtPart) {
      strBuffer.append("<li class=\"divider\"></li>");
    }
    if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
        && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
      String favoriteTitle = null;
      if (GlobalConstant.DOCUMENT_FAVORITE_YES.equals(this.getDocumentModel().getIsFavorite())) {
        favoriteTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_FAVORITE_REMOVE);
      } else {
        favoriteTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_FAVORITE);
      }
      if (Utility.isEmpty(permission)) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + favoriteTitle + "</a></li>");
      } else if ((permission.contains(PermissionConstant.ROOT_PERMISSION_FAVORITE) && GlobalConstant.ROOT_PARENT_ID.equals(this.getParentId())
          && DocumentType.FOLDER.toString().equals(this.getDocType()))
          || (permission.contains(PermissionConstant.FOLDER_PERMISSION_FAVORITE) && DocumentType.FOLDER.toString().equals(this.getDocType()))
          || (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_FAVORITE) && DocumentType.DOCUMENT.toString().equals(this.getDocType()))) {
        if (isMobile) {
          // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
          // +
          // favoriteTitle + "</a></li>");
        } else {
          strBuffer.append("<li><a id=\"favorite_" + this.getDocId() + "\" href=\"javascript:void(0);\" onclick=\"favorite('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(favoriteTitle).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("')\">");
          strBuffer.append(favoriteTitle).append("</a></li>");
        }
      } else {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + favoriteTitle + "</a></li>");
      }
    }
    secondPart = true;
    if (!GlobalConstant.ROOT_PARENT_ID.equals(this.getParentId())) {
      String tagTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_TAGS);
      if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
        tagTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_DEFAULT_TAGS);
      }
      if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + tagTitle + "</a></li>");
        } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_TAG)) {
          String saveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_SAVE);
          String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
          strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"tag('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(tagTitle).append("','");
          strBuffer.append(saveButtonLabel).append("','");
          strBuffer.append(cancelButtonLabel).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("')\">");
          strBuffer.append(tagTitle).append("</a></li>");
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + tagTitle + "</a></li>");
        }
      }
      String makeCommentTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_MAKE_COMMENT);
      if (RootType.PUBLIC.toString().equals(this.getRootType())) {
        if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
          if (Utility.isEmpty(permission)) {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + makeCommentTitle + "</a></li>");
          } else if (((!permission.contains(PermissionConstant.FOLDER_PERMISSION_READ_COMMENT_DENY)) && (!permission.contains(PermissionConstant.DOCUMENT_PERMISSION_READ_COMMENT_DENY)))
              && (permission.contains(PermissionConstant.FOLDER_PERMISSION_READ_COMMENT) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_READ_COMMENT))) {
            String saveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_SAVE);
            String closeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"makeComment('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(makeCommentTitle).append("','");
            strBuffer.append(saveButtonLabel).append("','");
            strBuffer.append(closeButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(makeCommentTitle).append("</a></li>");
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + makeCommentTitle + "</a></li>");
          }
        }
      }
    } else {
      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        String renameTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_RENAME);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + renameTitle + "</a></li>");
        } else if (permission.contains(PermissionConstant.ROOT_PERMISSION_RENAME)) {
          if (isMobile) {
            // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
            // +
            // renameTitle + "</a></li>");
          } else {
            String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
            String changeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CHANGE);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"rename('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(renameTitle).append("','");
            strBuffer.append(changeButtonLabel).append("','");
            strBuffer.append(cancelButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(renameTitle).append("</a></li>");
          }
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + renameTitle + "</a></li>");
        }
      }
    }
    if (secondPart) {
      if (!isMobile) {
        strBuffer.append("<li class=\"divider\"></li>");
      }
    }
    if (hasAccessRight) {
      if (!GlobalConstant.ROOT_PARENT_ID.equals(this.getParentId()) && DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
        thirdPart = true;
        String shareTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_SHARE);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">" + shareTitle + "</a></li>");
        } else if (permission.contains(PermissionConstant.FOLDER_PERMISSION_SHARE) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_SHARE)) {
          String shareButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_SHARE);
          String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
          strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"share('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(shareTitle).append("','");
          strBuffer.append(shareButtonLabel).append("','");
          strBuffer.append(cancelButtonLabel).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("')\">");
          strBuffer.append(shareTitle).append("</a></li>");
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">" + shareTitle + "</a></li>");
        }
      }
      if (thirdPart) {
        strBuffer.append("<li class=\"divider\"></li>");
      }
    }
    if (!GlobalConstant.ROOT_PARENT_ID.equals(this.getParentId()) && !isLocked) {
      fourthPart = true;
      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        String moveTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_MOVE);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + moveTitle + "</li>");
        } else if (((!permission.contains(PermissionConstant.FOLDER_PERMISSION_MOVE_DENY)) && (!permission.contains(PermissionConstant.DOCUMENT_PERMISSION_MOVE_DENY)))
            && (permission.contains(PermissionConstant.FOLDER_PERMISSION_MOVE) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_MOVE))) {
          String moveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_MOVE);
          String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
          strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"move('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(moveTitle).append("','");
          strBuffer.append(moveButtonLabel).append("','");
          strBuffer.append(cancelButtonLabel).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("','");
          strBuffer.append(this.getRootType()).append("')\">");
          strBuffer.append(moveTitle).append("</a></li>");
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + moveTitle + "</li>");
        }
      }
      String copyTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_COPY);
      if (Utility.isEmpty(permission)) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + copyTitle + "</a></li>");
      } else if (((!permission.contains(PermissionConstant.FOLDER_PERMISSION_COPY_DENY)) && (!permission.contains(PermissionConstant.DOCUMENT_PERMISSION_COPY_DENY)))
          && (permission.contains(PermissionConstant.FOLDER_PERMISSION_COPY) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_COPY))) {
        String copyButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_COPY);
        String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
        strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"copy('");
        strBuffer.append(request.getContextPath()).append("','");
        strBuffer.append(copyTitle).append("','");
        strBuffer.append(copyButtonLabel).append("','");
        strBuffer.append(cancelButtonLabel).append("','");
        strBuffer.append(this.getDocId()).append("','");
        strBuffer.append(this.getParentId()).append("','");
        strBuffer.append(this.getRootType()).append("')\">");
        strBuffer.append(copyTitle).append("</a></li>");
      } else {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + copyTitle + "</a></li>");
      }
      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        String renameTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_RENAME);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + renameTitle + "</a></li>");
        } else {
          boolean hasPermission = false;
          if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
            hasPermission = permission.contains(PermissionConstant.FOLDER_PERMISSION_RENAME);
          } else if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
            hasPermission = permission.contains(PermissionConstant.DOCUMENT_PERMISSION_RENAME);
          }
          if (hasPermission) {
            if (isMobile) {
              // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
              // +
              // renameTitle + "</a></li>");
            } else {
              String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
              String changeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CHANGE);
              strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"rename('");
              strBuffer.append(request.getContextPath()).append("','");
              strBuffer.append(renameTitle).append("','");
              strBuffer.append(changeButtonLabel).append("','");
              strBuffer.append(cancelButtonLabel).append("','");
              strBuffer.append(this.getDocId()).append("','");
              strBuffer.append(this.getParentId()).append("')\">");
              strBuffer.append(renameTitle).append("</a></li>");
            }
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + renameTitle + "</a></li>");
          }
        }
      }
    }
    if (fourthPart) {
      strBuffer.append("<li class=\"divider\"></li>");
    }
    if (!isLocked) {
      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        fifthPart = true;
        String deleteTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_DELETE);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + deleteTitle + "</a></li>");
        } else {
          boolean hasDelete = false;
          if (this.isRoot()) {
            hasDelete = permission.contains(PermissionConstant.ROOT_PERMISSION_DELETE);
          } else if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
            hasDelete = permission.contains(PermissionConstant.FOLDER_PERMISSION_DELETE);
          } else if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
            hasDelete = permission.contains(PermissionConstant.DOCUMENT_PERMISSION_DELETE);
          }
          if (hasDelete) {
            String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
            String deleteButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_DELETE);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"deleteDoc('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(deleteTitle).append("','");
            strBuffer.append(deleteButtonLabel).append("','");
            strBuffer.append(cancelButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(deleteTitle).append("</a></li>");
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + deleteTitle + "</a></li>");
          }
        }
      }
    }
    if (fifthPart) {
      strBuffer.append("<li class=\"divider\"></li>");
    }
    // sixthPart = true;
    if (RootType.PUBLIC.toString().equals(documentModel.getRootType())) {
      if (!GlobalConstant.ROOT_PARENT_ID.equals(this.getParentId())) {
        SmartshareDocAlertService smartshareDocAlertService = (SmartshareDocAlertService) wac.getBean("smartshareDocAlertServiceImpl");
        SmartshareDocAlertModel docAlertModel = smartshareDocAlertService.getByDocIdAndNotifyTypeAndObjectId(this.getDocId(), NotifyType.ALERT_ME, sessionContainer.getUserRecordID());
        String alertMeTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_ALERT_ME);
        boolean flag = true;
        if (!Utility.isEmpty(docAlertModel)) {
          alertMeTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_STOP_ALERT_ME);
        } else {
          if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
            docAlertModel = smartshareDocAlertService.getByDocIdAndNotifyTypeAndObjectId(this.getParentId(), NotifyType.ALERT_ME, sessionContainer.getUserRecordID());
            if (!Utility.isEmpty(docAlertModel)) {
              flag = false;
            }
          }
        }
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + alertMeTitle + "</a></li>");
        } else if (flag && ((!permission.contains(PermissionConstant.FOLDER_PERMISSION_ALERT_DENY)) && (!permission.contains(PermissionConstant.DOCUMENT_PERMISSION_ALERT_DENY)))
            && (permission.contains(PermissionConstant.FOLDER_PERMISSION_ALERT) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_ALERT))) {
          String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_NO);
          String okButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_YES);
          strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"alertMe('");
          strBuffer.append(request.getContextPath()).append("','");
          strBuffer.append(alertMeTitle).append("','");
          strBuffer.append(okButtonLabel).append("','");
          strBuffer.append(cancelButtonLabel).append("','");
          strBuffer.append(this.getDocId()).append("','");
          strBuffer.append(this.getParentId()).append("','");
          if (!Utility.isEmpty(docAlertModel)) {
            strBuffer.append(DocMenuOperation.STOP_ALERTME.toString());
          } else {
            strBuffer.append(DocMenuOperation.ALERTME.toString());
          }
          strBuffer.append("')\">").append(alertMeTitle).append("</a></li>");
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + alertMeTitle + "</a></li>");
        }
        String teamAlertTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_TEAM_ALERT);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + teamAlertTitle + "</a></li>");
        } else if (((!permission.contains(PermissionConstant.FOLDER_PERMISSION_SET_TEAM_ALERT_DENY)) && (!permission.contains(PermissionConstant.DOCUMENT_PERMISSION_SET_TEAM_ALERT_DENY)))
            && (permission.contains(PermissionConstant.FOLDER_PERMISSION_SET_TEAM_ALERT) || permission.contains(PermissionConstant.DOCUMENT_PERMISSION_SET_TEAM_ALERT))) {
          if (isMobile) {
            // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
            // +
            // teamAlertTitle + "</a></li>");
          } else {
            String saveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_SAVE);
            String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"teamAlert('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(teamAlertTitle).append("','");
            strBuffer.append(saveButtonLabel).append("','");
            strBuffer.append(cancelButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("','");
            strBuffer.append(this.getDocumentModel().getTeamAlertStatus()).append("')\">");
            strBuffer.append(teamAlertTitle).append("</a></li>");
          }
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + teamAlertTitle + "</a></li>");
        }
      }
    }
    String propertiesTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_PROPERTIES);
    if (Utility.isEmpty(permission)) {
      strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
      strBuffer.append(noPermission).append("\">" + propertiesTitle + "</a></li>");
    } else {
      boolean hasPermission = false;
      if (this.isRoot()) {
        hasPermission = permission.contains(PermissionConstant.ROOT_PERMISSION_VIEW);
      } else if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
        hasPermission = permission.contains(PermissionConstant.FOLDER_PERMISSION_VIEW);
      } else if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
        hasPermission = permission.contains(PermissionConstant.DOCUMENT_PERMISSION_VIEW);
      }
      if (hasPermission) {
        String closeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
        strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"viewProperties('");
        strBuffer.append(request.getContextPath()).append("','");
        strBuffer.append(propertiesTitle).append("','");
        strBuffer.append(closeButtonLabel).append("','");
        strBuffer.append(this.getDocId()).append("','");
        strBuffer.append(this.getParentId()).append("')\">");
        strBuffer.append(propertiesTitle).append("</a></li>");
      } else {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + propertiesTitle + "</a></li>");
      }
    }
    if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
      if (!(RootType.PERSONAL.toString().equals(documentModel.getRootType()) && GlobalConstant.DMS_MY_QUICK_SHARES.equals(documentModel.getParentDocName())
          && GlobalConstant.DMS_PERSONAL_ROOT.equals(documentModel.getGrandpaDocName()))) {
        String versionHistoryTitile = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_VERSION_HISTORY);
        if (Utility.isEmpty(permission)) {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + versionHistoryTitile + "</a></li>");
        } else if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_VIEW)) {
          if (isMobile) {
            // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
            // +
            // versionHistoryTitile + "</a></li>");
          } else {
            String closeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"versionHistory('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(versionHistoryTitile).append("','");
            strBuffer.append(closeButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(versionHistoryTitile).append("</a></li>");
          }
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + versionHistoryTitile + "</a></li>");
        }
      }
    }
    if ((!isMobile) && RootType.PUBLIC.toString().equals(documentModel.getRootType())) {
      String auditLogTitile = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_AUDIT_LOG);
      String closeButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CLOSE);
      if (Utility.isEmpty(permission)) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + auditLogTitile + "</a></li>");
      } else {
        if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
          if (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_VIEW)) {
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"auditLog('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(auditLogTitile).append("','");
            strBuffer.append(closeButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(auditLogTitile).append("</a></li>");
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + auditLogTitile + "</a></li>");
          }
        } else {
          boolean hasPermission = false;
          if (this.isRoot()) {
            hasPermission = permission.contains(PermissionConstant.ROOT_PERMISSION_VIEW);
          } else if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
            hasPermission = permission.contains(PermissionConstant.FOLDER_PERMISSION_VIEW);
          } else if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
            hasPermission = permission.contains(PermissionConstant.DOCUMENT_PERMISSION_VIEW);
          }
          if (hasPermission) {
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"auditLog('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(auditLogTitile).append("','");
            strBuffer.append(closeButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(auditLogTitile).append("</a></li>");
          } else {
            strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
            strBuffer.append(noPermission).append("\">" + auditLogTitile + "</a></li>");
          }
        }
      }
    }
    if (RootType.PUBLIC.toString().equals(documentModel.getRootType())) {
      String setPermissionTitle = TagUtils.getInstance().message(pageContext, locale, MessageConstant.DMS_LABEL_SET_PERMISSION);
      if (Utility.isEmpty(permission)) {
        strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
        strBuffer.append(noPermission).append("\">" + setPermissionTitle + "</a></li>");
      } else {
        boolean hasPermission = false;
        if (isOwner()) {
          hasPermission = true;
        } else if (this.isRoot()) {
          hasPermission = permission.contains(PermissionConstant.ROOT_PERMISSION_SET_PERMISSION);
        } else if (DocumentType.FOLDER.toString().equals(this.getDocType())) {
          hasPermission = permission.contains(PermissionConstant.FOLDER_PERMISSION_SET_PERMISSION);
        } else if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
          hasPermission = permission.contains(PermissionConstant.DOCUMENT_PERMISSION_SET_PERMISSION);
        }
        if (hasPermission) {
          if (isMobile) {
            // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
            // +
            // setPermissionTitle + "</a></li>");
          } else {
            String cancelButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_CANCEL);
            String saveButtonLabel = TagUtils.getInstance().message(pageContext, locale, MessageConstant.COMMON_LABEL_SAVE);
            strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"setPermission('");
            strBuffer.append(request.getContextPath()).append("','");
            strBuffer.append(setPermissionTitle).append("','");
            strBuffer.append(saveButtonLabel).append("','");
            strBuffer.append(cancelButtonLabel).append("','");
            strBuffer.append(this.getDocId()).append("','");
            strBuffer.append(this.getParentId()).append("')\">");
            strBuffer.append(setPermissionTitle).append("</a></li>");
          }
        } else {
          strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"");
          strBuffer.append(noPermission).append("\">" + setPermissionTitle + "</a></li>");
        }
      }
    }
    // if (RootType.PUBLIC.toString().equals(documentModel.getRootType())) {
    // if (DocumentType.DOCUMENT.toString().equals(this.getDocType())) {
    // if (sixthPart) {
    // strBuffer.append("<li class=\"divider\"></li>");
    // }
    // String analyticsTitile = TagUtils.getInstance().message(pageContext,
    // locale,
    // MessageConstant.DMS_LABEL_ANALYTICS);
    // if (Utility.isEmpty(permission)) {
    // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
    // + analyticsTitile
    // + "</a></li>");
    // } else if
    // (permission.contains(PermissionConstant.DOCUMENT_PERMISSION_ANALYTICS)) {
    // strBuffer.append("<li><a href=\"javascript:void(0);\" onclick=\"analytics('");
    // strBuffer.append(request.getContextPath()).append("','");
    // strBuffer.append(analyticsTitile).append("','");
    // strBuffer.append(this.getDocId()).append("','");
    // strBuffer.append(this.getParentId()).append("')\">");
    // strBuffer.append(analyticsTitile).append("</a></li>");
    // } else {
    // strBuffer.append("<li class=\"disabled\"><a href=\"javascript:void(0);\">"
    // + analyticsTitile
    // + "</a></li>");
    // }
    // }
    // }
    strBuffer.append("</ul></div></div>");
    try {
      pageContext.getOut().print(strBuffer.toString());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("DropdownMenuTag-->doStartTag end!");
    return SKIP_BODY;
  }

  public String getPermission() {
    return this.getDocumentModel().getPermissionString();
  }

  public SmartshareDocumentModel getDocumentModel() {
    return documentModel;
  }

  public void setDocumentModel(SmartshareDocumentModel documentModel) {
    this.documentModel = documentModel;
  }

  public String getDocType() {
    return this.getDocumentModel().getDocType();
  }

  public String getDocId() {
    return this.getDocumentModel().getId();
  }

  public String getItemStatus() {
    return this.getDocumentModel().getItemStatus();
  }

  public String getParentId() {
    return this.getDocumentModel().getParentId();
  }

  public String getContentId() {
    return this.getDocumentModel().getCurContentId();
  }

  public String getRootType() {
    return this.getDocumentModel().getRootType();
  }

  public String getOwnerId() {
    return this.getDocumentModel().getCreatorId();
  }

  public boolean isRoot() {
    return GlobalConstant.ROOT_PARENT_ID.equals(this.getDocumentModel().getParentId());
  }

  public boolean isOwner() {
    return sessionContainer.getUserRecordID().equals(this.getDocumentModel().getOwnerId());
  }

  @Override
  public void release() {
    super.release();
  }

}
