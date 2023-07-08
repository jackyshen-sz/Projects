package com.paradm.sse.persist.framework;

import com.paradm.sse.domain.framework.model.SessionContainer;

import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
public interface IBaseDao<T> {

  /**
   * update by criteria
   *
   * @param entity
   * @param sessionContainer
   */
  void updateByCriteria(T entity, SessionContainer sessionContainer);

  /**
   * update by sql
   *
   * @param isNative
   * @param sql
   * @param params
   */
  void updateBySql(String sql, Map<String, Object> params, boolean isNative);
}
