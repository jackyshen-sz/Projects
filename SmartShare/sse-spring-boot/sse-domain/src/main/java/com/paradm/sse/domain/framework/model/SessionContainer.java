package com.paradm.sse.domain.framework.model;

import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.common.enums.RoleType;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.ApplicationContainer;
import com.paradm.sse.domain.user.model.UserRecordModel;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public class SessionContainer implements Serializable {

  private static final long serialVersionUID = -8919408200146824704L;

  private UserRecordModel userRecordModel = null;

  /**
   * Getting the application container of this session.
   *
   * @return Return the application container as an instance of <code>ApplicationContainer</code>
   */
  public ApplicationContainer getAppContainer() {
    return ApplicationContainer.INSTANCE;
  }

  public UserRecordModel getUserRecordModel() {
    return userRecordModel;
  }

  public void setUserRecordModel(UserRecordModel userRecordModel) {
    this.userRecordModel = userRecordModel;
  }

  public Integer getUserRecordId() {
    Integer userId = null;
    if (!ObjectUtil.isEmpty(this.getUserRecordModel())) {
      userId = Utility.parseInteger(this.getUserRecordModel().getId());
    }
    return userId;
  }

  public String getUserFullName() {
    return ObjectUtil.isEmpty(this.getUserRecordModel()) ? "" : this.getUserRecordModel().getFullName();
  }

  public Integer getCompanyId() {
    Integer companyId = null;
    if (!ObjectUtil.isEmpty(this.getUserRecordModel())) {
      companyId = Utility.parseInteger(this.getUserRecordModel().getCompanyId());
    }
    return companyId;
  }

  public boolean isAdmin() {
    boolean isAdmin = false;
    if (!ObjectUtil.isEmpty(this.getUserRecordModel())) {
      isAdmin = RoleType.ADMIN.toString().equals(this.getUserRecordModel().getUserType());
    }
    return isAdmin;
  }
}
