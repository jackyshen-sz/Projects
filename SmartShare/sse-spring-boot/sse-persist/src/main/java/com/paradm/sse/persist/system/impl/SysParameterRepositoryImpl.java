package com.paradm.sse.persist.system.impl;

import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.domain.system.entity.SysParameter;
import com.paradm.sse.persist.framework.impl.BaseDao;
import com.paradm.sse.persist.system.ISysParameterDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/6/23
 */
public class SysParameterRepositoryImpl extends BaseDao<SysParameter> implements ISysParameterDao {
  @Override
  public CriteriaUpdate<SysParameter> createCriteriaUpdate(CriteriaBuilder cb, SysParameter entity, SessionContainer sessionContainer) {
    CriteriaUpdate<SysParameter> criteriaUpdate = cb.createCriteriaUpdate(SysParameter.class);
    Root<SysParameter> sysParameterRoot = criteriaUpdate.from(SysParameter.class);
    Integer updateCount = Utility.isEmpty(entity.getUpdateCount()) ? 0 : entity.getUpdateCount();
    criteriaUpdate.set(sysParameterRoot.get("updateCount"), (updateCount + 1));
    criteriaUpdate.set(sysParameterRoot.get("updaterId"), this.getUpdaterId(entity, sessionContainer));
    criteriaUpdate.set(sysParameterRoot.get("updateDate"), new Date(System.currentTimeMillis()));
    if (!Utility.isNull(entity.getRecordStatus())) {
      criteriaUpdate.set(sysParameterRoot.get("recordStatus"), entity.getRecordStatus());
    }

    return null;
  }
}
