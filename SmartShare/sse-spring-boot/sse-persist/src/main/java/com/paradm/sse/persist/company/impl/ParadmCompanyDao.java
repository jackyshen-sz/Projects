package com.paradm.sse.persist.company.impl;

import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.persist.company.IParadmCompanyDao;
import com.paradm.sse.persist.framework.impl.BaseDao;

/**
 * @author Jacky.shen
 * @create data 2020/6/22
 */
public class ParadmCompanyDao extends BaseDao<ParadmCompany> implements IParadmCompanyDao {

  @Override
  public <S extends ParadmCompany> S updateObject(S entity) {
    em.createQuery("");
    return null;
  }
}
