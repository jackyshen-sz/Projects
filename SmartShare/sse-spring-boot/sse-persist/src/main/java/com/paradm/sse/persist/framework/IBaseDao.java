package com.paradm.sse.persist.framework;

import com.paradm.sse.domain.framework.model.SessionContainer;

import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
public interface IBaseDao<T> {

  void updateByCriteria(T entity, SessionContainer sessionContainer);

  void updateBySql(boolean isNative, String sql, Map<Integer, Object> params);
}
