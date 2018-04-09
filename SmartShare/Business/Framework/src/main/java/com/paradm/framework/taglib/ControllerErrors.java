package com.paradm.framework.taglib;

import java.io.Serializable;

public class ControllerErrors extends ControllerMessages implements Serializable {

  private static final long serialVersionUID = -6638868311304216004L;

  public static final String GLOBAL_ERROR = "GLOBAL_ERROR";

  public ControllerErrors() {
    super();
  }

  public ControllerErrors(ControllerErrors messages) {
    super(messages);
  }

}
