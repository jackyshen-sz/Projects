package com.paradm.sse.common.enums;

import com.paradm.sse.common.utils.Utility;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public enum MenuType {
  STRING("S"), HIDE("H");

  private String name;

  MenuType(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public static MenuType valueOf(int ordinal) {
    return MenuType.values()[ordinal];
  }

  public static MenuType fromAcronym(String acronym) {
    MenuType result = MenuType.STRING;
    if (!Utility.isEmpty(acronym)) {
      for (MenuType menuType : MenuType.values()) {
        if (acronym.equals(menuType.toString())) {
          result = menuType;
          break;
        }
      }
    }
    return result;
  }
}
