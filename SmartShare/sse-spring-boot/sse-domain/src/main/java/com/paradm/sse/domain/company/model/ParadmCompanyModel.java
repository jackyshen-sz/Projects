package com.paradm.sse.domain.company.model;

import com.paradm.sse.common.enums.CompanyActiveFlag;
import com.paradm.sse.common.enums.StorageType;
import com.paradm.sse.common.enums.YesNoFlag;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Data
public class ParadmCompanyModel extends BaseModel {

  private static final long serialVersionUID = 1066868247162834251L;

  private String address1;
  private String address2;
  private String address3;
  private String areaCode;
  private String background;
  private String businessActivity;
  private String cicId;
  private String city;
  private String classType;
  private String code;
  private String companyName;
  private String companyNameEng;
  private String countryId;
  private String dataSourceDbName;
  private String dataSourceDbType;
  private String dataSourceDriverClass;
  private String dsAutoCommit;
  private String dsReadOnly;
  private String dsMaxActive;
  private String dsMaxIdle;
  private String dsMaxWait;
  private String dsPoolPS;
  private String dsMaxOpenPS;
  private String dsTransactionIsolation;
  private String dsLogAbandoned;
  private String dsRemoveAbandoned;
  private String dsWhileIdle;
  private String dataSourceJndiName;
  private String dataSourcePwd;
  private String dataSourceUrl;
  private String dataSourceUsername;
  private String email;
  private String employeeCount;
  private String faxNo;
  private String hkBranches;
  private String industryId;
  private String leadSourceId;
  private String overseasBranches;
  private String ownerId;
  private String phoneNo;
  private String postalCode;
  private String provinceId;
  private String sicId;
  private String specialAlertId;
  private String state;
  private String statusId;
  private String stockCode;
  private String turnover;
  private String url;
  private String userDef1;
  private String userDef2;
  private String userDef3;
  private String userDef4;
  private String userDef5;
  private String activeFlag;
  private String expireDate;
  private String remindDate;
  private String completeInit;
  private String configuringPercent;
  private String licensedUser;
  private String storageSpace;
  private String isCloud;
  private String storageType;

  @Override
  public void setModelData(IdEntity entity) {
    ParadmCompany paradmCompany = (ParadmCompany) entity;
    BeanUtils.copyProperties(paradmCompany, this);
    this.setBaseModelData(this, paradmCompany);
    this.setCicId(Utility.formatInteger(paradmCompany.getCicId()));
    this.setCountryId(Utility.formatInteger(paradmCompany.getCountryId()));
    this.setDataSourceDbType(Utility.formatInteger(paradmCompany.getDataSourceDbType()));
    this.setDsMaxActive(Utility.formatInteger(paradmCompany.getDsMaxActive()));
    this.setDsMaxIdle(Utility.formatInteger(paradmCompany.getDsMaxIdle()));
    this.setDsMaxWait(Utility.formatInteger(paradmCompany.getDsMaxWait()));
    this.setDsMaxOpenPS(Utility.formatInteger(paradmCompany.getDsMaxOpenPS()));
    this.setDsTransactionIsolation(Utility.formatInteger(paradmCompany.getDsTransactionIsolation()));
    this.setEmployeeCount(Utility.formatInteger(paradmCompany.getEmployeeCount()));
    this.setHkBranches(Utility.formatInteger(paradmCompany.getHkBranches()));
    this.setIndustryId(Utility.formatInteger(paradmCompany.getIndustryId()));
    this.setLeadSourceId(Utility.formatInteger(paradmCompany.getLeadSourceId()));
    this.setOverseasBranches(Utility.formatInteger(paradmCompany.getOverseasBranches()));
    this.setOwnerId(Utility.formatInteger(paradmCompany.getOwnerId()));
    this.setProvinceId(Utility.formatInteger(paradmCompany.getProvinceId()));
    this.setStatusId(Utility.formatInteger(paradmCompany.getStatusId()));
    this.setTurnover(Utility.formatLong(paradmCompany.getTurnover()));
    this.setActiveFlag(paradmCompany.getActiveFlag().toString());
    this.setExpireDate(Utility.formatDate(paradmCompany.getExpireDate()));
    this.setRemindDate(Utility.formatDate(paradmCompany.getRemindDate()));
    this.setCompleteInit(paradmCompany.getCompleteInit().toString());
    this.setConfiguringPercent(Utility.formatInteger(paradmCompany.getConfiguringPercent()));
    this.setLicensedUser(Utility.formatInteger(paradmCompany.getLicensedUser()));
    this.setStorageSpace(Utility.formatInteger(paradmCompany.getStorageSpace()));
    this.setIsCloud(paradmCompany.getIsCloud().toString());
    this.setStorageType(paradmCompany.getStorageType().toString());
  }

  @Override
  public ParadmCompany getEntityData() {
    ParadmCompany paradmCompany = new ParadmCompany();
    BeanUtils.copyProperties(this, paradmCompany);
    this.setBaseEntity(paradmCompany);
    paradmCompany.setCicId(Utility.parseInteger(this.getCicId()));
    paradmCompany.setCountryId(Utility.parseInteger(this.getCountryId()));
    paradmCompany.setDataSourceDbType(Utility.parseInteger(this.getDataSourceDbType()));
    paradmCompany.setDsMaxActive(Utility.parseInteger(this.getDsMaxActive()));
    paradmCompany.setDsMaxIdle(Utility.parseInteger(this.getDsMaxIdle()));
    paradmCompany.setDsMaxWait(Utility.parseInteger(this.getDsMaxWait()));
    paradmCompany.setDsMaxOpenPS(Utility.parseInteger(this.getDsMaxOpenPS()));
    paradmCompany.setDsTransactionIsolation(Utility.parseInteger(this.getDsTransactionIsolation()));
    paradmCompany.setEmployeeCount(Utility.parseInteger(this.getEmployeeCount()));
    paradmCompany.setHkBranches(Utility.parseInteger(this.getHkBranches()));
    paradmCompany.setIndustryId(Utility.parseInteger(this.getIndustryId()));
    paradmCompany.setLeadSourceId(Utility.parseInteger(this.getLeadSourceId()));
    paradmCompany.setOverseasBranches(Utility.parseInteger(this.getOverseasBranches()));
    paradmCompany.setOwnerId(Utility.parseInteger(this.getOwnerId()));
    paradmCompany.setProvinceId(Utility.parseInteger(this.getProvinceId()));
    paradmCompany.setStatusId(Utility.parseInteger(this.getStatusId()));
    paradmCompany.setTurnover(Utility.parseLong(this.getTurnover()));
    paradmCompany.setActiveFlag(CompanyActiveFlag.fromAcronym(this.getActiveFlag()));
    paradmCompany.setExpireDate(Utility.parseDate(this.getExpireDate()));
    paradmCompany.setRemindDate(Utility.parseDate(this.getRemindDate()));
    paradmCompany.setCompleteInit(YesNoFlag.fromAcronym(this.getCompleteInit()));
    paradmCompany.setConfiguringPercent(Utility.parseInteger(this.getConfiguringPercent()));
    paradmCompany.setLicensedUser(Utility.parseInteger(this.getLicensedUser()));
    paradmCompany.setStorageSpace(Utility.parseInteger(this.getStorageSpace()));
    paradmCompany.setIsCloud(YesNoFlag.fromAcronym(this.getIsCloud()));
    paradmCompany.setStorageType(StorageType.fromAcronym(this.getStorageType()));
    return paradmCompany;
  }
}
