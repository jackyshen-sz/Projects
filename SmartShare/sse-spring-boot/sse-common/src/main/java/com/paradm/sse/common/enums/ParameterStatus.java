package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public enum ParameterStatus {
  HIDE("H"), SHOW("S");

  private String name;

  ParameterStatus(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static ParameterStatus valueOf(int ordinal) {
    return ParameterStatus.values()[ordinal];
  }

  public static ParameterStatus fromAcronym(String acronym) {
    ParameterStatus result = ParameterStatus.HIDE;
    if (!Utility.isEmpty(acronym)) {
      for (ParameterStatus parameterStatus : ParameterStatus.values()) {
        if (acronym.equals(parameterStatus.toString())) {
          result = parameterStatus;
          break;
        }
      }
    }
    return result;
  }
}
