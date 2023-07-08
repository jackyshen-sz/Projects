package com.paradm.sse.domain.system.entity;

import com.paradm.sse.common.enums.FunctionStatus;
import com.paradm.sse.common.enums.MenuType;
import com.paradm.sse.domain.framework.entity.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
@Entity
@Table(name = SysFunction.TABLE_NAME)
public class SysFunction extends IdEntity {

  private static final long serialVersionUID = -775844043943018013L;

  public static final String TABLE_NAME = "SYS_FUNCTION";

  @Column(name = "FUNCTION_CODE")
  private String functionCode;

  @Column(name = "FUNCTION_LABEL")
  private String functionLabel;

  @Column(name = "DISPLAY_SEQ")
  private Integer displaySeq;

  @Column(name = "FUNCTION_ICON")
  private String functionIcon;

  @Column(name = "LINK_URL")
  private String linkUrl;

  @Column(name = "MENU_TYPE")
  @Enumerated(EnumType.ORDINAL)
  private MenuType menuType;

  @Column(name = "PARENT_ID")
  private Integer parentId;

  @Column(name = "PERMISSION_TYPE")
  private String permissionType;

  @Column(name = "STATUS")
  @Enumerated(EnumType.ORDINAL)
  private FunctionStatus status;
}
