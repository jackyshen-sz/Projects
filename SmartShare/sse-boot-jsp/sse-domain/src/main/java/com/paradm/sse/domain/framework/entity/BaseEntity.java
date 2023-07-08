package com.paradm.sse.domain.framework.entity;

import com.paradm.sse.common.enums.RecordStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends IdEntity implements Serializable {

  private static final long serialVersionUID = -3913886534263129651L;

  @Column(name = "RECORD_STATUS", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private RecordStatus recordStatus;
  @Column(name = "UPDATE_COUNT", nullable = false)
  private Integer updateCount;
  @Column(name = "CREATOR_ID", nullable = false)
  private Integer creatorId;
  @Column(name = "CREATE_DATE", nullable = false)
  @CreatedDate
  private Date createDate;
  @Column(name = "UPDATER_ID", nullable = false)
  private Integer updaterId;
  @Column(name = "UPDATE_DATE", nullable = false)
  @LastModifiedDate
  private Date updateDate;
}
