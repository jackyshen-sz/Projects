package com.paradm.sse.common.constant;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public enum ErrorConstant {
  COMMON_UNKNOWN_ERROR("errors.common.unknown_error"),

  DB_SELECT_ERROR("errors.framework.select"),
  DB_INSERT_ERROR("errors.framework.insert"),
  DB_UPDATE_ERROR("errors.framework.update"),
  ;

  String code;

  ErrorConstant(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
