package com.paradm.sse.common.api;

import com.paradm.sse.common.exception.ApplicationException;

/**
 * @author Jacky.shen
 * @create data 2020/6/30
 */
public interface IApiCode {

  /**
   * get code
   *
   * @return
   */
  int getCode();

  /**
   * get msg
   *
   * @return
   */
  String getMsg();

  /**
   * default method
   *
   * @param code
   * @param msg
   */
  default void assertSuccess(int code, String msg) {
    if (this.getCode() != code) {
      throw new ApplicationException(msg);
    }
  }
}
