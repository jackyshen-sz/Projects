package com.paradm.sse.persist.framework.impl;

import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.common.api.ApiCode;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.domain.framework.entity.BaseEntity;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.persist.framework.IBaseDao;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
@Slf4j
public abstract class BaseDao<T> implements IBaseDao<T> {

  @PersistenceContext
  protected EntityManager em;

  @Override
  public void updateByCriteria(T entity, SessionContainer sessionContainer) {
    try {
      CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
      CriteriaUpdate<T> criteriaUpdate = this.createCriteriaUpdate(criteriaBuilder, entity, sessionContainer);
      Query query = em.createQuery(criteriaUpdate);
      int count = query.executeUpdate();
      if (count == 0) {
        throw new ApplicationException(ApiCode.DB_CONCURRENT_ERROR);
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(ApiCode.DB_UPDATE_ERROR);
    }
  }

  @Override
  public void updateBySql(String sql, Map<String, Object> params, boolean isNative) {
    try {
      Query query = this.createQuery(sql, isNative);
      this.setQueryParams(params, query);
      int count = query.executeUpdate();
      if (count == 0) {
        throw new ApplicationException(ApiCode.DB_CONCURRENT_ERROR);
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(ApiCode.DB_UPDATE_ERROR);
    }
  }

  protected void setQueryParams(Map<String, Object> params, Query query) {
    if (ObjectUtil.isNotEmpty(params)) {
      params.forEach((key, value) -> {
        if (value instanceof Date) {
          query.setParameter(key, (Date) value, TemporalType.TIMESTAMP);
        } else {
          query.setParameter(key, value);
        }
      });
    }
  }

  private Query createQuery(String sql, boolean isNative) {
    Query query = null;
    if (isNative) {
      query = em.createNativeQuery(sql);
    } else {
      query = em.createQuery(sql);
    }
    return query;
  }

  protected Integer getUpdaterId(BaseEntity entity, SessionContainer sessionContainer) {
    Integer updaterId = entity.getUpdaterId();
    if (!ObjectUtil.isEmpty(sessionContainer) && !ObjectUtil.isEmpty(sessionContainer.getUserRecordId())) {
      updaterId = sessionContainer.getUserRecordId();
    }
    return updaterId;
  }

  public abstract CriteriaUpdate<T> createCriteriaUpdate(CriteriaBuilder cb, T entity, SessionContainer sessionContainer);

  protected void setCommonCriteriaUpdate(CriteriaUpdate<? extends BaseEntity> criteriaUpdate, Root<? extends BaseEntity> entityRoot, BaseEntity entity, SessionContainer sessionContainer) {
    Integer updateCount = ObjectUtil.isEmpty(entity.getUpdateCount()) ? 0 : entity.getUpdateCount();
    criteriaUpdate.set(entityRoot.get("updateCount"), (updateCount + 1));
    criteriaUpdate.set(entityRoot.get("updaterId"), this.getUpdaterId(entity, sessionContainer));
    criteriaUpdate.set(entityRoot.get("updateDate"), new Date(System.currentTimeMillis()));
    if (ObjectUtil.isNotNull(entity.getRecordStatus())) {
      criteriaUpdate.set(entityRoot.get("recordStatus"), entity.getRecordStatus());
    }
  }
}
