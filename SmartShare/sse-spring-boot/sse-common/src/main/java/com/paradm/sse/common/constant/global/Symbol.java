package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum Symbol implements GlobalConstant {
  MINUS("-"), UNDERLINE("_"), SLASH("/"), BACKSLASH("\\"), COMMA(","), POINT("."),
  SEMICOLON(";"), COLON(":"), BLANK(" "), OPEN_ANGLE("<"), CLOSE_ANGLE(">"),
  AND("&"), EQUAL("="), ADD("+"),QUESTION_MARK("?"),LEFT_BRACKET("["),RIGHT_BRACKET("]"),
  ;

  String name;

  Symbol(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public String getCode() {
    return toString();
  }
}
