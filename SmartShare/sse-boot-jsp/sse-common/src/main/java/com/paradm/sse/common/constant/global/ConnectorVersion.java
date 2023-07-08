package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create date 2021/10/8
 */
public enum ConnectorVersion implements GlobalConstant {
  /**
   *
   */
  VERSION_4("FX-EWB/4.0"),
  VERSION_5("FX-EWB/5.0"),
  VERSION_7("FX-EWB/5.1"),
  ;
  private final String name;

  ConnectorVersion(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public String getKey() {
    return toString();
  }

  @Override
  public String getValue() {
    return toString();
  }
}
