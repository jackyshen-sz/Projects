package com.paradm.sse.common.api;

import com.paradm.sse.common.constant.error.CommonError;

/**
 * @author Jacky.shen
 * @create data 2020/7/2
 */
public enum ApiCode implements IApiCode {
  // request success
  SUCCESS(1, "success"),
  // request fail
  UNKNOWN_ERROR(10001, CommonError.COMMON_UNKNOWN_ERROR.getKey()),
//  PARAMETER_ERROR(10002, ErrorConstant.COMMON_PARAMETER_ERROR),
//  REQUEST_TIMEOUT(10003, ErrorConstant.COMMON_REQUEST_TIMEOUT),
//
  DB_SELECT_ERROR(10004, CommonError.DB_SELECT_ERROR.getKey()),
  DB_UPDATE_ERROR(10005, CommonError.DB_UPDATE_ERROR.getKey()),
  DB_INSERT_ERROR(10006, CommonError.DB_INSERT_ERROR.getKey()),
  DB_CONCURRENT_ERROR(10007, CommonError.DB_CONCURRENT_ERROR.getKey()),
  ;

  private int code;
  private String msg;

  ApiCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMsg() {
    return this.msg;
  }
}
