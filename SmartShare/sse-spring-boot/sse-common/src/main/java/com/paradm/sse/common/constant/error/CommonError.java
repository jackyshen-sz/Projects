package com.paradm.sse.common.constant.error;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum CommonError implements Constant {

  COMMON_UNKNOWN_ERROR("errors.common.unknown_error"),

  DB_SELECT_ERROR("errors.framework.select"),
  DB_INSERT_ERROR("errors.framework.insert"),
  DB_UPDATE_ERROR("errors.framework.update"),

  KAPTCHA_WRONG_MESSAGE("errors.captcha.dose_not_match"),
  ;

  String key = "";

  CommonError(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }
}
