package com.paradm.sse.domain.init.model;

import com.paradm.sse.domain.company.model.ParadmCompanyModel;
import com.paradm.sse.domain.dms.model.SmartshareLocMasterModel;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.system.model.SysParameterModel;
import com.paradm.sse.domain.user.model.UserRecordModel;
import lombok.Data;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Data
public class InitSystemModel extends BaseModel {

  private static final long serialVersionUID = -7024456941141207859L;

  private ParadmCompanyModel paradmCompanyModel;
  private UserRecordModel userModel;
  private List<SysParameterModel> parameterModelList;
  private SmartshareLocMasterModel locMasterModel;

  @Override
  public void setModelData(IdEntity entity) {

  }

  @Override
  public IdEntity getEntityData() {
    return null;
  }
}
