package com.paradm.sse.domain.system.model;

import com.paradm.sse.common.enums.ParameterStatus;
import com.paradm.sse.common.enums.ParameterType;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.system.entity.SysParameter;
import lombok.Data;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Data
public class SysParameterModel extends BaseModel {

  private static final long serialVersionUID = 3385244460095771322L;

  private String parameterCode;
  private String parameterDefaultValue;
  private String parameterType;
  private String description;
  private String parameterValue;
  private String status;

  @Override
  public void setModelData(IdEntity entity) {
    SysParameter sysParameter = (SysParameter) entity;
    this.setBaseModelData(sysParameter);
    this.setParameterType(sysParameter.getParameterType().toString());
    this.setStatus(sysParameter.getStatus().toString());
  }

  @Override
  public SysParameter getEntityData() {
    SysParameter sysParameter = new SysParameter();
    this.setBaseEntity(sysParameter);
    sysParameter.setParameterType(ParameterType.fromAcronym(this.getParameterType()));
    sysParameter.setStatus(ParameterStatus.fromAcronym(this.getStatus()));
    return sysParameter;
  }
}
