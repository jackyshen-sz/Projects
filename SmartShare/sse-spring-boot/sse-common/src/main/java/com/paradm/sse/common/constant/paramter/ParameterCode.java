package com.paradm.sse.common.constant.paramter;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum ParameterCode implements Constant {
  /**
   *
   */
  APPLICATION_BASE_URL("app.base.url"),
  DB_DATE_FORMAT("db.date.format"),
  SESSION_TIMEOUT("system.session.timeout"),
  SYSTEM_HELP_CENTER("system.help_center_url"),
  ;

  String key;

  ParameterCode(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return this.key;
  }

  @Override
  public String toString() {
    return getKey();
  }
}
