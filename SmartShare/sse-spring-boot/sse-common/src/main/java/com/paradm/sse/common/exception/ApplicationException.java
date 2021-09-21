package com.paradm.sse.common.exception;

import com.paradm.sse.common.api.ApiCode;
import com.paradm.sse.common.api.IApiCode;
import org.springframework.context.ApplicationContextException;

/**
 * @author Jackyshen
 */
public class ApplicationException extends ApplicationContextException {

  private static final long serialVersionUID = 621035947198803371L;

  private Object[] msgArg;
  private IApiCode errorCode;

  public Object[] getMsgArg() {
    return msgArg;
  }

  public void setMsgArg(Object[] msgArg) {
    this.msgArg = msgArg;
  }

  public IApiCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(IApiCode errorCode) {
    this.errorCode = errorCode;
  }

  public ApplicationException(String msg) {
    super(msg);
    this.setErrorCode(ApiCode.UNKNOWN_ERROR);
  }

  public ApplicationException(String msg, Object... args) {
    super(msg);
    this.setMsgArg(args);
    this.setErrorCode(ApiCode.UNKNOWN_ERROR);
  }

  public ApplicationException(IApiCode error) {
    super(error.getMsg());
    this.setErrorCode(error);
  }

  public ApplicationException(IApiCode error, Object... args) {
    super(error.getMsg());
    this.setErrorCode(error);
    this.setMsgArg(args);
  }

}
