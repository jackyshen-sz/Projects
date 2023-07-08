package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum WebStatus implements GlobalConstant {
  /**
   *
   */
  SUCCESSFUL("successful"), FAILED("failed"),
  ;

  final String value;

  WebStatus(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return toString();
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
