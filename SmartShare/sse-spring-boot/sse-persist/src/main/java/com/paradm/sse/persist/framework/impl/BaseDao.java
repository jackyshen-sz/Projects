package com.paradm.sse.persist.framework.impl;

import com.paradm.sse.persist.framework.IBaseDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
public class BaseDao<T> implements IBaseDao<T> {

  @PersistenceContext
  protected EntityManager em;

}
