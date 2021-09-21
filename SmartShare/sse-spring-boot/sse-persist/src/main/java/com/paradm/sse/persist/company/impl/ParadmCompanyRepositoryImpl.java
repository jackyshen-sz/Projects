package com.paradm.sse.persist.company.impl;

import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.persist.company.IParadmCompanyDao;
import com.paradm.sse.persist.framework.impl.BaseDao;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

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
    Integer updateCount = ObjectUtil.isEmpty(entity.getUpdateCount()) ? 0 : entity.getUpdateCount();
    this.setCommonCriteriaUpdate(criteriaUpdate, companyRoot, entity, sessionContainer);
    if (ObjectUtil.isNotNull(entity.getCompanyName())) {
      criteriaUpdate.set(companyRoot.get("companyName"), entity.getCompanyName());
      criteriaUpdate.set(companyRoot.get("companyNameEng"), entity.getCompanyNameEng());
    }
    if (ObjectUtil.isNotNull(entity.getCode())) {
      criteriaUpdate.set(companyRoot.get("code"), entity.getCode());
    }
    if (ObjectUtil.isNotNull(entity.getActiveFlag())) {
      criteriaUpdate.set(companyRoot.get("activeFlag"), entity.getActiveFlag());
    }
    if (ObjectUtil.isNotNull(entity.getExpireDate())) {
      criteriaUpdate.set(companyRoot.get("expireDate"), entity.getExpireDate());
    }
    if (ObjectUtil.isNotNull(entity.getRemindDate())) {
      criteriaUpdate.set(companyRoot.get("remindDate"), entity.getRemindDate());
    }
    if (ObjectUtil.isNotNull(entity.getCompleteInit())) {
      criteriaUpdate.set(companyRoot.get("completeInit"), entity.getCompleteInit());
    }
    if (ObjectUtil.isNotNull(entity.getConfiguringPercent())) {
      criteriaUpdate.set(companyRoot.get("configuringPercent"), entity.getConfiguringPercent());
    }
    if (ObjectUtil.isNotNull(entity.getLicensedUser())) {
      criteriaUpdate.set(companyRoot.get("licensedUser"), entity.getLicensedUser());
    }
    if (ObjectUtil.isNotNull(entity.getStorageSpace())) {
      criteriaUpdate.set(companyRoot.get("storageSpace"), entity.getStorageSpace());
    }
    if (ObjectUtil.isNotNull(entity.getStorageType())) {
      criteriaUpdate.set(companyRoot.get("storageType"), entity.getStorageType());
    }
    if (ObjectUtil.isNotNull(entity.getIsCloud())) {
      criteriaUpdate.set(companyRoot.get("isCloud"), entity.getIsCloud());
    }
    criteriaUpdate.where(cb.equal(companyRoot.get("id"), entity.getId()), cb.equal(companyRoot.get("updateCount"), updateCount));
    return criteriaUpdate;
  }

}
