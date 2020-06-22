package com.paradm.sse.persist.framework;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
public interface IBaseDao<T> {

  public default <S extends T> S updateObject(S entity) {
    return entity;
  }
}
