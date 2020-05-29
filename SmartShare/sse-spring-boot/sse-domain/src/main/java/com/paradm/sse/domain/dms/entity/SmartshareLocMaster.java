package com.paradm.sse.domain.dms.entity;

import com.paradm.sse.domain.framework.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Data
@Entity
@Table(name = SmartshareLocMaster.TABLE_NAME)
public class SmartshareLocMaster extends BaseEntity {

  private static final long serialVersionUID = -1674065951481594266L;

  public static final String TABLE_NAME = "SYS_FUNCTION";

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "LOC_NAME")
  private String locName;

  @Column(name = "LOC_PATH")
  private String locPath;

  @Column(name = "SEGMENT_NO")
  private Integer segmentNo;
}
