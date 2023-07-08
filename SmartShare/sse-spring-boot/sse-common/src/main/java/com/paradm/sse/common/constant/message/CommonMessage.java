package com.paradm.sse.common.constant.message;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum CommonMessage implements Constant {
  /*

   */
  ;

  String key = "";

  CommonMessage(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }
}
