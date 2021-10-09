package com.paradm.sse.common.enums;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
public enum CompanyActiveFlag {
  /**
   *
   */
  NONE_PURCHASED("0"),
  NONE_PURCHASED_EXPIRED("1"),
  PURCHASED("2"),
  PURCHASED_EXPIRED("3"),
  DELETE("4");

  private String name;

  CompanyActiveFlag(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static CompanyActiveFlag valueOf(int ordinal) {
    return CompanyActiveFlag.values()[ordinal];
  }

  public static CompanyActiveFlag fromAcronym(String acronym) {
    CompanyActiveFlag result = CompanyActiveFlag.PURCHASED;
    if (CharSequenceUtil.isNotEmpty(acronym)) {
      for (CompanyActiveFlag companyActiveFlag : CompanyActiveFlag.values()) {
        if (acronym.equals(companyActiveFlag.toString())) {
          result = companyActiveFlag;
          break;
        }
      }
    }
    return result;
  }
}
