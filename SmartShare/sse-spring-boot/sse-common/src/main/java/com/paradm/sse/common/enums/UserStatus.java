package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum UserStatus {
  ACTIVE("A"), INACTIVE("I"), LOCKED("X"), PENDING("P"),
  ;
  private String name;

  UserStatus(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static UserStatus valueOf(int ordinal) {
    return UserStatus.values()[ordinal];
  }

  public static UserStatus fromAcronym(String acronym) {
    UserStatus result = UserStatus.ACTIVE;
    if (!Utility.isEmpty(acronym)) {
      for (UserStatus status : UserStatus.values()) {
        if (acronym.equals(status.toString())) {
          result = status;
          break;
        }
      }
    }
    return result;
  }
}
