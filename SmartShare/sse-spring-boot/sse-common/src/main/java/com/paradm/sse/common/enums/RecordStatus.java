package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum RecordStatus {
  ACTIVE("A"), INACTIVE("I"), PENDING("P"), DELETE("D"), UNFINISHED("U");

  private String name;

  RecordStatus(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static RecordStatus valueOf(int ordinal) {
    return RecordStatus.values()[ordinal];
  }

  public static RecordStatus fromAcronym(String acronym) {
    RecordStatus result = RecordStatus.ACTIVE;
    if (!Utility.isEmpty(acronym)) {
      for (RecordStatus recordStatus : RecordStatus.values()) {
        if (acronym.equals(recordStatus.toString())) {
          result = recordStatus;
          break;
        }
      }
    }
    return result;
  }
}
