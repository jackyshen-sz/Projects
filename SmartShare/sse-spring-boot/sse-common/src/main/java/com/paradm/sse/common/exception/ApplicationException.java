package com.paradm.sse.common.exception;

import org.springframework.context.ApplicationContextException;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public class ApplicationException extends ApplicationContextException {

  private static final long serialVersionUID = 6107452481799412379L;

  private Object[] msgArg;
  private int errorCode;

  public Object[] getMsgArg() {
    return msgArg;
  }

  public void setMsgArg(Object[] msgArg) {
    this.msgArg = msgArg;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public ApplicationException(String msg) {
    super(msg);
  }

  public ApplicationException(String msg, Object... args) {
    super(msg);
    this.msgArg = args;
  }
}
