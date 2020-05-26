package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum NotifyGroupType {
  NO("N"), ONE_HOUR("O"), REAL_TIME("R");

  private String name;

  NotifyGroupType(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static NotifyGroupType valueOf(int ordinal) {
    return NotifyGroupType.values()[ordinal];
  }

  public static NotifyGroupType fromAcronym(String acronym) {
    NotifyGroupType result = NotifyGroupType.REAL_TIME;
    if (!Utility.isEmpty(acronym)) {
      for (NotifyGroupType flag : NotifyGroupType.values()) {
        if (acronym.equals(flag.toString())) {
          result = flag;
          break;
        }
      }
    }
    return result;
  }
}
