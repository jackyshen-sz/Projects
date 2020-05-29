package com.paradm.sse.domain.system.model;

import com.paradm.sse.common.enums.FunctionStatus;
import com.paradm.sse.common.enums.MenuType;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.system.entity.SysFunction;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
public class SysFunctionModel extends BaseModel {

  private static final long serialVersionUID = -2835682829035735035L;

  private String functionCode;
  private String functionLabel;
  private String displaySeq;
  private String functionIcon;
  private String linkUrl;
  private String menuType;
  private String parentId;
  private String permissionType;
  private String status;

  public SysFunctionModel() {
  }

  public SysFunctionModel(SysFunction sysFunction) {
    this.setModelData(sysFunction);
  }

  @Override
  public void setModelData(IdEntity entity) {
    SysFunction sysFunction = (SysFunction) entity;
    BeanUtils.copyProperties(sysFunction, this);
    this.setId(Utility.formatInteger(sysFunction.getId()));
    this.setDisplaySeq(Utility.formatInteger(sysFunction.getDisplaySeq()));
    this.setMenuType(sysFunction.getMenuType().toString());
    this.setParentId(Utility.formatInteger(sysFunction.getParentId()));
    this.setStatus(sysFunction.getStatus().toString());
  }

  @Override
  public SysFunction getEntityData() {
    SysFunction sysFunction = new SysFunction();
    BeanUtils.copyProperties(this, sysFunction);
    sysFunction.setId(Utility.parseInteger(this.getId()));
    sysFunction.setDisplaySeq(Utility.parseInteger(this.getDisplaySeq()));
    sysFunction.setMenuType(MenuType.fromAcronym(this.getMenuType()));
    sysFunction.setParentId(Utility.parseInteger(this.getParentId()));
    sysFunction.setStatus(FunctionStatus.fromAcronym(this.getStatus()));
    return sysFunction;
  }
}
