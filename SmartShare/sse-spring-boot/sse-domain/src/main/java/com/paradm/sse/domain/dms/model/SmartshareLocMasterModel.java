package com.paradm.sse.domain.dms.model;

import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.dms.entity.SmartshareLocMaster;
import com.paradm.sse.domain.framework.entity.IdEntity;
import com.paradm.sse.domain.framework.model.BaseModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Data
public class SmartshareLocMasterModel extends BaseModel {

  private static final long serialVersionUID = -3762530875723372077L;

  private String description;
  private String locName;
  private String locPath;
  private String segmentNo;

  @Override
  public void setModelData(IdEntity entity) {
    SmartshareLocMaster smartshareLocMaster = (SmartshareLocMaster) entity;
    BeanUtils.copyProperties(smartshareLocMaster, this);
    this.setBaseModelData(this, smartshareLocMaster);
    this.setSegmentNo(Utility.formatInteger(smartshareLocMaster.getSegmentNo()));
  }

  @Override
  public SmartshareLocMaster getEntityData() {
    SmartshareLocMaster smartshareLocMaster = new SmartshareLocMaster();
    BeanUtils.copyProperties(this, smartshareLocMaster);
    this.setBaseEntity(smartshareLocMaster);
    smartshareLocMaster.setSegmentNo(Utility.parseInteger(this.getSegmentNo()));
    return smartshareLocMaster;
  }
}
