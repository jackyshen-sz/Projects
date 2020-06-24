package com.paradm.sse.persist.framework.impl;

import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.entity.BaseEntity;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.persist.framework.IBaseDao;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
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
      if (Utility.isEmpty(criteriaUpdate)) {
        throw new ApplicationException(CommonError.CRITERIA_UPDATE_OVERRIDE_ERROR.getKey());
      }
      Query query = em.createQuery(criteriaUpdate);
      int count = query.executeUpdate();
      if (count == 0) {
        throw new ApplicationException(CommonError.DB_CONCURRENT_ERROR.getKey());
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(CommonError.DB_UPDATE_ERROR.getKey());
    }
  }

  public abstract CriteriaUpdate<T> createCriteriaUpdate(CriteriaBuilder cb, T entity, SessionContainer sessionContainer);

  @Override
  public void updateBySql(boolean isNative, String sql, Map<Integer, Object> params) {
    try {
      Query query = this.createQuery(isNative, sql);
      if (!Utility.isEmpty(params)) {
        params.keySet().forEach(position -> query.setParameter(position, params.get(position)));
      }
      int count = query.executeUpdate();
      if (count == 0) {
        throw new ApplicationException(CommonError.DB_CONCURRENT_ERROR.getKey());
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(CommonError.DB_UPDATE_ERROR.getKey());
    }
  }

  private Query createQuery(boolean isNative, String sql) {
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
    if (!Utility.isEmpty(sessionContainer) && !Utility.isEmpty(sessionContainer.getUserRecordId())) {
      updaterId = sessionContainer.getUserRecordId();
    }
    return updaterId;
  }
}
