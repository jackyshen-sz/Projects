package com.paradm.sse.common.enums;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum RoleType {
  /**
   *
   */
  ADMIN("A"), USER("U"), EXTERNAL("E");

  private String name;

  RoleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static RoleType valueOf(int ordinal) {
    return RoleType.values()[ordinal];
  }

  public static RoleType fromAcronym(String acronym) {
    RoleType result = RoleType.ADMIN;
    if (CharSequenceUtil.isNotEmpty(acronym)) {
      for (RoleType roleType : RoleType.values()) {
        if (acronym.equals(roleType.toString())) {
          result = roleType;
          break;
        }
      }
    }
    return result;
  }
}
