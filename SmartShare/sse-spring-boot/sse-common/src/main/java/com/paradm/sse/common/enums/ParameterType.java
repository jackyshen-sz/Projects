package com.paradm.sse.common.enums;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public enum ParameterType {
  /**
   *
   */
  STRING("S"), INTEGER("I"), BOOLEAN("B"), DATE("D");

  private String name;

  ParameterType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static ParameterType valueOf(int ordinal) {
    return ParameterType.values()[ordinal];
  }

  public static ParameterType fromAcronym(String acronym) {
    ParameterType result = ParameterType.STRING;
    if (CharSequenceUtil.isNotEmpty(acronym)) {
      for (ParameterType parameterType : ParameterType.values()) {
        if (acronym.equals(parameterType.toString())) {
          result = parameterType;
          break;
        }
      }
    }
    return result;
  }
}
