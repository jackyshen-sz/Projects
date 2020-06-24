package com.paradm.sse.persist.company.impl;

import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.persist.company.IParadmCompanyDao;
import com.paradm.sse.persist.framework.impl.BaseDao;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
@Slf4j
public class ParadmCompanyRepositoryImpl extends BaseDao<ParadmCompany> implements IParadmCompanyDao {

  @Override
  public CriteriaUpdate<ParadmCompany> createCriteriaUpdate(CriteriaBuilder cb, ParadmCompany entity, SessionContainer sessionContainer) {
    CriteriaUpdate<ParadmCompany> criteriaUpdate = cb.createCriteriaUpdate(ParadmCompany.class);
    Root<ParadmCompany> companyRoot = criteriaUpdate.from(ParadmCompany.class);
    Integer updateCount = Utility.isEmpty(entity.getUpdateCount()) ? 0 : entity.getUpdateCount();
    criteriaUpdate.set(companyRoot.get("updateCount"), (updateCount + 1));
    criteriaUpdate.set(companyRoot.get("updaterId"), this.getUpdaterId(entity, sessionContainer));
    criteriaUpdate.set(companyRoot.get("updateDate"), new Date(System.currentTimeMillis()));
    if (!Utility.isNull(entity.getRecordStatus())) {
      criteriaUpdate.set(companyRoot.get("recordStatus"), entity.getRecordStatus());
    }
    if (!Utility.isNull(entity.getCompanyName())) {
      criteriaUpdate.set(companyRoot.get("companyName"), entity.getCompanyName());
      criteriaUpdate.set(companyRoot.get("companyNameEng"), entity.getCompanyNameEng());
    }
    if (!Utility.isNull(entity.getCode())) {
      criteriaUpdate.set(companyRoot.get("code"), entity.getCode());
    }
    if (!Utility.isNull(entity.getActiveFlag())) {
      criteriaUpdate.set(companyRoot.get("activeFlag"), entity.getActiveFlag());
    }
    if (!Utility.isNull(entity.getExpireDate())) {
      criteriaUpdate.set(companyRoot.get("expireDate"), entity.getExpireDate());
    }
    if (!Utility.isNull(entity.getRemindDate())) {
      criteriaUpdate.set(companyRoot.get("remindDate"), entity.getRemindDate());
    }
    if (!Utility.isNull(entity.getCompleteInit())) {
      criteriaUpdate.set(companyRoot.get("completeInit"), entity.getCompleteInit());
    }
    if (!Utility.isNull(entity.getConfiguringPercent())) {
      criteriaUpdate.set(companyRoot.get("configuringPercent"), entity.getConfiguringPercent());
    }
    if (!Utility.isNull(entity.getLicensedUser())) {
      criteriaUpdate.set(companyRoot.get("licensedUser"), entity.getLicensedUser());
    }
    if (!Utility.isNull(entity.getStorageSpace())) {
      criteriaUpdate.set(companyRoot.get("storageSpace"), entity.getStorageSpace());
    }
    if (!Utility.isNull(entity.getStorageType())) {
      criteriaUpdate.set(companyRoot.get("storageType"), entity.getStorageType());
    }
    if (!Utility.isNull(entity.getIsCloud())) {
      criteriaUpdate.set(companyRoot.get("isCloud"), entity.getIsCloud());
    }
    criteriaUpdate.where(cb.equal(companyRoot.get("id"), entity.getId()), cb.equal(companyRoot.get("updateCount"), updateCount));
    return criteriaUpdate;
  }

}
