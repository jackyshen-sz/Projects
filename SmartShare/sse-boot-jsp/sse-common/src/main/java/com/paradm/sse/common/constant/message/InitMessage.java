package com.paradm.sse.common.constant.message;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/19
 */
public enum InitMessage implements Constant {

  /**
   *
   */
  SIGN_IN_SUCCESS("init.message.sign_in_success"),
  ;

  String key = "";

  InitMessage(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }
}
