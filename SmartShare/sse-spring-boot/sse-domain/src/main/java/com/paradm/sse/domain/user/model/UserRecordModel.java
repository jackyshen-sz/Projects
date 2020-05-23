package com.paradm.sse.domain.user.model;

import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import lombok.Data;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
public class UserRecordModel extends BaseModel {

  private static final long serialVersionUID = 4829812931408742799L;

  private String email;
  private String failedAttempt;
  private String connectorFailedAttempt;
  private String firstName;
  private String fullName;
  private String lastName;
  private String lastPwdUpdateDate;
  private String locale;
  private String loginName;
  private String loginPwd;
  private String maxAttempt;
  private String modifyLoginPwd;
  private String preference;
  private String pwdExpiryDay;
  private String pwdResetFlag;
  private String status;
  private String userDef1;
  private String userDef2;
  private String userDef3;
  private String userDef4;
  private String userDef5;
  private String userLinkId;
  private String userType;
  private String lastLoginDate;
  private String twoFactorAuthEnable;
  private String ldapLoginName;
  private String storageQuota;
  private String notifyFlag;
  private String notifyGroupByStatus;
  private String amdpSyncFlag;

  @Override
  public void setModelData(IdEntity entity) {

  }

  @Override
  public IdEntity getEntityData() {
    return null;
  }
}
