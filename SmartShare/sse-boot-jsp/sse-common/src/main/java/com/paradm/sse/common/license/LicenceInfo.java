package com.paradm.sse.common.license;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
public class LicenceInfo implements Serializable {

  static final long serialVersionUID = -2585665780664946489L;

  private String key = "";
  private String expDate = "";
  private String maintenanceStartDate;
  private String maintenanceEndDate;
  private String ip = "";
  private String comName = "";
  private String version;
  private String systemUser;
  private String concurrentUser;
  private String parascanUser;
  private String clientUser;
  private String uploadFileNum;
  private String storageSpace;
}
