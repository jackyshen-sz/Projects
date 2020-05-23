package com.paradm.sse.common.constant;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public interface GlobalConstant {

  String APPLICATION_NAME = "SMARTSHARE-ENTERPRISE";

  String VERSION = "1.0.0.0";

  String FUNCTION_CODE_KEY = "FUNCTION_CODE";

  /**
   * The key which used to retrieve the ApplicationContainer in application scope.
   */
  String APPLICATION_CONTAINER_KEY = "applicationContainer";

  enum Symbol {
    MINUS("-"), UNDERLINE("_"), SLASH("/"), BACKSLASH("\\"), COMMA(","), POINT("."),
    SEMICOLON(";"), COLON(":"), BLANK(" "), OPEN_ANGLE("<"), CLOSE_ANGLE(">"),
    AND("&"), EQUAL("="), ADD("+"),QUESTION_MARK("?"),LEFT_BRACKET("["),RIGHT_BRACKET("]");

    private String name;

    Symbol(String name) {
      this.name = name;
    }

    public String toString() {
      return this.name;
    }
  }

  enum LoginMethod {
    WEB, COOKIE, MFD, FTP, SYNC, MOBILE, OUTLOOK, PARA_SCAN;
  }
}
