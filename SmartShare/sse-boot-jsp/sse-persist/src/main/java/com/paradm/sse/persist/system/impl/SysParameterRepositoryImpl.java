package com.paradm.sse.persist.system.impl;

import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.domain.system.entity.SysParameter;
import com.paradm.sse.persist.framework.impl.BaseDao;
import com.paradm.sse.persist.system.ISysParameterDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * @author Jacky.shen
 * @create data 2020/6/23
 */
public class SysParameterRepositoryImpl extends BaseDao<SysParameter> implements ISysParameterDao {
  @Override
  public CriteriaUpdate<SysParameter> createCriteriaUpdate(CriteriaBuilder cb, SysParameter entity, SessionContainer sessionContainer) {
    CriteriaUpdate<SysParameter> criteriaUpdate = cb.createCriteriaUpdate(SysParameter.class);
    Root<SysParameter> sysParameterRoot = criteriaUpdate.from(SysParameter.class);
    Integer updateCount = ObjectUtil.isEmpty(entity.getUpdateCount()) ? 0 : entity.getUpdateCount();
    criteriaUpdate.where(cb.equal(sysParameterRoot.get("id"), entity.getId()), cb.equal(sysParameterRoot.get("updateCount"), updateCount));
    this.setCommonCriteriaUpdate(criteriaUpdate, sysParameterRoot, entity, sessionContainer);
    return criteriaUpdate;
  }
}
