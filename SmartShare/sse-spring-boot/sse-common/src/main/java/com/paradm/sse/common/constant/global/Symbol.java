package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;

/**
 * @author Jacky.shen
 * @create data 2020/6/9
 */
public enum Symbol implements GlobalConstant {
  /**
   *
   */
  MINUS("-"), UNDERLINE("_"), SLASH("/"), BACKSLASH("\\"), COMMA(","), POINT("."),
  SEMICOLON(";"), COLON(":"), BLANK(" "), OPEN_ANGLE("<"), CLOSE_ANGLE(">"),
  AND("&"), EQUAL("="), ADD("+"),QUESTION_MARK("?"),LEFT_BRACKET("["),RIGHT_BRACKET("]"),
  ;

  String value;

  Symbol(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return getValue();
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return getValue();
  }
}
