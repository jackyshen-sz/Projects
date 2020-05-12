package com.paradm.sse.framework.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Data
@MappedSuperclass
public class IdEntity implements Serializable {
  private static final long serialVersionUID = -3988100617886492492L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
}
