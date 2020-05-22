package com.paradm.sse.domain.system.entity;

import com.paradm.sse.common.enums.ParameterStatus;
import com.paradm.sse.common.enums.ParameterType;
import com.paradm.sse.domain.framework.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
@Entity
@Table(name = SysParameter.TABLE_NAME)
public class SysParameter extends BaseEntity {

  private static final long serialVersionUID = 7341754085813412459L;

  public static final String TABLE_NAME = "SYS_PARAMETER";

  @Column(name = "PARAMETER_CODE")
  private String parameterCode;

  @Column(name = "PARAMETER_DEFAULT_VALUE")
  private String parameterDefaultValue;

  @Column(name = "PARAMETER_TYPE")
  @Enumerated(EnumType.ORDINAL)
  private ParameterType parameterType;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PARAMETER_VALUE")
  private String parameterValue;

  @Column(name = "STATUS")
  private ParameterStatus status;
}
