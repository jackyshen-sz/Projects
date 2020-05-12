package com.paradm.sse.user;

import com.paradm.sse.common.enums.*;
import com.paradm.sse.framework.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Data
@Entity
@Table(name = "USER_RECORD")
public class UserRecord extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 719760678003783602L;

  @Column(name = "COMPANY_ID")
  private Integer companyId;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "FAILED_ATTEMPT")
  private Integer failedAttempt;

  @Column(name = "CONNECTOR_FAILED_ATTEMPT")
  private Integer connectorFailedAttempt;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "FULL_NAME")
  private String fullName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "LAST_PWD_UPDATE_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastPwdUpdateDate;

  @Column(name = "LOCALE")
  private String locale;

  @Column(name = "LOGIN_NAME")
  private String loginName;

  @Column(name = "LOGIN_PWD")
  private String loginPwd;

  @Column(name = "MAX_ATTEMPT")
  private Integer maxAttempt;

  @Column(name = "MODIFY_LOGIN_PWD")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag modifyLoginPwd;

  @Column(name = "PREFERENCE")
  @Enumerated(EnumType.ORDINAL)
  private PreferenceType preference;

  @Column(name = "PWD_EXPIRY_DAY")
  private Integer pwdExpiryDay;

  @Column(name = "PWD_RESET_FLAG")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag pwdResetFlag;

  @Column(name = "STATUS")
  @Enumerated(EnumType.ORDINAL)
  private Status status;

  @Column(name = "USER_DEF_1")
  private String userDef1;

  @Column(name = "USER_DEF_2")
  private String userDef2;

  @Column(name = "USER_DEF_3")
  private String userDef3;

  @Column(name = "USER_DEF_4")
  private String userDef4;

  @Column(name = "USER_DEF_5")
  private String userDef5;

  @Column(name = "USER_LINK_ID")
  private Integer userLinkId;

  @Column(name = "USER_TYPE")
  @Enumerated(EnumType.ORDINAL)
  private RoleType userType;

  @Column(name = "LAST_LOGIN_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLoginDate;

  @Column(name = "TWO_FACTOR_AUTH_ENABLE")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag twoFactorAuthEnable;

  @Column(name = "LDAP_LOGIN_NAME")
  private String ldapLoginName;

  @Column(name = "STORAGE_QUOTA")
  private Integer storageQuota;

  @Column(name = "NOTIFY_FLAG")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag notifyFlag;

  @Column(name = "NOTIFY_GROUP_BY_STATUS")
  @Enumerated(EnumType.ORDINAL)
  private NotifyGroupType notifyGroupByStatus;

  @Column(name = "AMDP_SYNC_FLAG")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag amdpSyncFlag;
}
