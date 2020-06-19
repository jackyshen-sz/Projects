package com.paradm.sse.common.constant.error;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum InitError implements Constant {
  PARAMETER_ERROR("errors.parameter_error"),
  COMPANY_NOT_EXIST("errors.company_not_exist"),
  ;

  String key = "";

  InitError(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }
}
