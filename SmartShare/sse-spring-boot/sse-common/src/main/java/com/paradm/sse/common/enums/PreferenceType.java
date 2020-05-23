package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum PreferenceType {
  STANDARD("standard"), DARK_GREY("darkGrey"), BLACK("black");

  private String name;

  PreferenceType(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static PreferenceType valueOf(int ordinal) {
    return PreferenceType.values()[ordinal];
  }

  public static PreferenceType fromAcronym(String acronym) {
    PreferenceType result = PreferenceType.STANDARD;
    if (!Utility.isEmpty(acronym)) {
      for (PreferenceType preferenceType : PreferenceType.values()) {
        if (acronym.equals(preferenceType.toString())) {
          result = preferenceType;
          break;
        }
      }
    }
    return result;
  }
}
