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
  DASHED("-"), UNDERLINE("_"), SLASH("/"), BACKSLASH("\\"), COMMA(","), DOT("."),
  SEMICOLON(";"), COLON(":"), TAB("  "), SPACE(" "), OPEN_ANGLE("<"), CLOSE_ANGLE(">"),
  AMP("&"), EQUAL("="), ADD("+"), QUESTION_MARK("?"), BRACKET_START("["), BRACKET_END("]"),
  DELIM_START("{"), DELIM_END("}"), DOUBLE_QUOTES("\""), SINGLE_QUOTE("'"), AT("@"),
  ;

  final String value;

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
