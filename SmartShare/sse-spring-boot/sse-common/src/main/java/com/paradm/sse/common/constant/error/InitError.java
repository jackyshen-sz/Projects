package com.paradm.sse.common.constant.error;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum InitError implements Constant {
  PARAMETER_ERROR("");
  ;

  String code = "";

  InitError(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
