package com.paradm.sse.common.constant.message;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum CommonMessage implements Constant {
  ;

  String code = "";

  CommonMessage(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
