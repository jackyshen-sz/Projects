package com.paradm.sse.domain.company.entity;

import com.paradm.sse.common.enums.CompanyActiveFlag;
import com.paradm.sse.common.enums.StorageType;
import com.paradm.sse.common.enums.YesNoFlag;
import com.paradm.sse.domain.framework.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Data
@Entity
@Table(name = ParadmCompany.TABLE_NAME)
public class ParadmCompany extends BaseEntity {

  private static final long serialVersionUID = 8212149215584169437L;

  public static final String TABLE_NAME = "PARADM_COMPANY";

  @Column(name = "ADDRESS1")
  private String address1;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "ADDRESS3")
  private String address3;

  @Column(name = "AREA_CODE")
  private String areaCode;

  @Column(name = "BACKGROUND")
  private String background;

  @Column(name = "BUSINESS_ACTIVITY")
  private String businessActivity;

  @Column(name = "CIC_ID")
  private Integer cicId;

  @Column(name = "CITY")
  private String city;

  @Column(name = "CLASS_TYPE")
  private String classType;

  @Column(name = "CODE")
  private String code;

  @Column(name = "COMPANY_NAME")
  private String companyName;

  @Column(name = "COMPANY_NAME_ENG")
  private String companyNameEng;

  @Column(name = "COUNTRY_ID")
  private Integer countryId;

  @Column(name = "DB_CONFIG_ID")
  private Integer dbConfigId;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "EMPLOYEE_COUNT")
  private Integer employeeCount;

  @Column(name = "FAX_NO")
  private String faxNo;

  @Column(name = "HK_BRANCHES")
  private Integer hkBranches;

  @Column(name = "INDUSTRY_ID")
  private Integer industryId;

  @Column(name = "LEAD_SOURCE_ID")
  private Integer leadSourceId;

  @Column(name = "OVERSEAS_BRANCHES")
  private Integer overseasBranches;

  @Column(name = "OWNER_ID")
  private Integer ownerId;

  @Column(name = "PHONE_NO")
  private String phoneNo;

  @Column(name = "POSTAL_CODE")
  private String postalCode;

  @Column(name = "PROVINCE_ID")
  private Integer provinceId;

  @Column(name = "SIC_ID")
  private Integer sicId;

  @Column(name = "SPECIAL_ALERT_ID")
  private Integer specialAlertId;

  @Column(name = "STATE")
  private String state;

  @Column(name = "STATUS_ID")
  private Integer statusId;

  @Column(name = "STOCK_CODE")
  private String stockCode;

  @Column(name = "TURNOVER")
  private Long turnover;

  @Column(name = "URL")
  private String url;

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

  @Column(name = "ACTIVE_FLAG")
  @Enumerated(EnumType.ORDINAL)
  private CompanyActiveFlag activeFlag;

  @Column(name = "EXPIRE_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date expireDate;

  @Column(name = "REMIND_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date remindDate;

  @Column(name = "COMPLETE_INIT")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag completeInit;

  @Column(name = "CONFIGURING_PERCENT")
  private Integer configuringPercent;

  @Column(name = "LICENSED_USER")
  private Integer licensedUser;

  @Column(name = "STORAGE_SPACE")
  private Integer storageSpace;

  @Column(name = "IS_CLOUD")
  @Enumerated(EnumType.ORDINAL)
  private YesNoFlag isCloud;

  @Column(name = "STORAGE_TYPE")
  @Enumerated(EnumType.ORDINAL)
  private StorageType storageType;
}
