package com.paradm.sse.common.license;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
public class ModuleLicenceInfo implements Serializable {

  static final long serialVersionUID = 9042801134613386313L;

  private String name = "";
  private String type = "";
  private int no = -1;
}
