package com.paradm.sse.domain.user.model;

import com.paradm.sse.common.enums.*;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.user.entity.UserRecord;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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

  public UserRecordModel() {
  }

  public UserRecordModel(UserRecord userRecord) {
    this.setModelData(userRecord);
  }

  @Override
  public void setModelData(IdEntity entity) {
    UserRecord userRecord = (UserRecord) entity;
    BeanUtils.copyProperties(userRecord, this);
    this.setBaseModelData(this, userRecord);
    this.setCompanyId(Utility.formatInteger(userRecord.getCompanyId()));
    this.setFailedAttempt(Utility.formatInteger(userRecord.getFailedAttempt()));
    this.setConnectorFailedAttempt(Utility.formatInteger(userRecord.getConnectorFailedAttempt()));
    this.setMaxAttempt(Utility.formatInteger(userRecord.getMaxAttempt()));
    this.setLastPwdUpdateDate(Utility.formatDate(userRecord.getLastPwdUpdateDate()));
    this.setModifyLoginPwd(userRecord.getModifyLoginPwd().toString());
    this.setPreference(userRecord.getPreference().toString());
    this.setPwdExpiryDay(Utility.formatInteger(userRecord.getPwdExpiryDay()));
    this.setPwdResetFlag(userRecord.getPwdResetFlag().toString());
    this.setStatus(userRecord.getStatus().toString());
    this.setUserLinkId(Utility.formatInteger(userRecord.getUserLinkId()));
    this.setUserType(userRecord.getUserType().toString());
    this.setLastLoginDate(Utility.formatDate(userRecord.getLastLoginDate()));
    this.setTwoFactorAuthEnable(userRecord.getTwoFactorAuthEnable().toString());
    this.setStorageQuota(Utility.formatInteger(userRecord.getStorageQuota()));
    this.setNotifyFlag(Utility.isEmpty(userRecord.getNotifyFlag()) ? YesNoFlag.YES.toString() : userRecord.getNotifyFlag().toString());
    this.setNotifyGroupByStatus(Utility.isEmpty(userRecord.getNotifyGroupByStatus()) ? NotifyGroupType.REAL_TIME.toString() : userRecord.getNotifyGroupByStatus().toString());
    this.setAmdpSyncFlag(Utility.isEmpty(userRecord.getAmdpSyncFlag()) ? YesNoFlag.NO.toString() : userRecord.getAmdpSyncFlag().toString());
  }

  @Override
  public IdEntity getEntityData() {
    UserRecord userRecord = new UserRecord();
    BeanUtils.copyProperties(this, userRecord);
    this.setBaseEntity(userRecord);
    userRecord.setCompanyId(Utility.parseInteger(this.getCompanyId()));
    userRecord.setFailedAttempt(Utility.parseInteger(this.getFailedAttempt()));
    userRecord.setConnectorFailedAttempt(Utility.parseInteger(this.getConnectorFailedAttempt()));
    userRecord.setMaxAttempt(Utility.parseInteger(this.getMaxAttempt()));
    userRecord.setLastPwdUpdateDate(Utility.parseDate(this.getLastPwdUpdateDate()));
    userRecord.setModifyLoginPwd(YesNoFlag.fromAcronym(this.getModifyLoginPwd()));
    userRecord.setPreference(PreferenceType.fromAcronym(this.getPreference()));
    userRecord.setPwdExpiryDay(Utility.parseInteger(this.getPwdExpiryDay()));
    userRecord.setPwdResetFlag(YesNoFlag.fromAcronym(this.getPwdResetFlag()));
    userRecord.setStatus(UserStatus.fromAcronym(this.getStatus()));
    userRecord.setUserLinkId(Utility.parseInteger(this.getUserLinkId()));
    userRecord.setUserType(RoleType.fromAcronym(this.getUserType()));
    userRecord.setLastLoginDate(Utility.parseDate(this.getLastLoginDate()));
    userRecord.setTwoFactorAuthEnable(YesNoFlag.fromAcronym(this.getTwoFactorAuthEnable()));
    userRecord.setStorageQuota(Utility.parseInteger(this.getStorageQuota()));
    userRecord.setNotifyFlag(YesNoFlag.fromAcronym(this.getNotifyFlag()));
    userRecord.setNotifyGroupByStatus(NotifyGroupType.fromAcronym(this.getNotifyGroupByStatus()));
    userRecord.setAmdpSyncFlag(YesNoFlag.fromAcronym(this.getAmdpSyncFlag()));
    return userRecord;
  }
}
