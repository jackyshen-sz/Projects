package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public enum FunctionStatus {
  ACTIVE("A");
  private String name;

  FunctionStatus(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static FunctionStatus valueOf(int ordinal) {
    return FunctionStatus.values()[ordinal];
  }

  public static FunctionStatus fromAcronym(String acronym) {
    FunctionStatus result = FunctionStatus.ACTIVE;
    if (!Utility.isEmpty(acronym)) {
      for (FunctionStatus functionStatus : FunctionStatus.values()) {
        if (acronym.equals(functionStatus.toString())) {
          result = functionStatus;
          break;
        }
      }
    }
    return result;
  }
}
