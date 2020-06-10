package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum LoginMethod implements GlobalConstant {
  WEB, COOKIE, MFD, FTP, SYNC, MOBILE, OUTLOOK, PARA_SCAN,
  ;

  @Override
  public String getKey() {
    return toString();
  }

  @Override
  public String getValue() {
    return toString();
  }
}
