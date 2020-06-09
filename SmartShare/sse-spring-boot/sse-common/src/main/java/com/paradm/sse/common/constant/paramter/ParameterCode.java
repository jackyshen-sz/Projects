package com.paradm.sse.common.constant.paramter;

import com.paradm.sse.common.constant.Constant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum ParameterCode implements Constant {
  APPLICATION_BASE_URL("app.base.url"),
  DB_DATE_FORMAT("db.date.format"),
  ;

  String code;

  ParameterCode(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
