package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum WebStatus implements GlobalConstant {
  SUCCESSFUL("successful"), FAILED("failed"),
  ;

  String name;

  WebStatus(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public String getCode() {
    return toString();
  }
}
