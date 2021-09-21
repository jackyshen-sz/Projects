package com.paradm.sse.common.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public enum YesNoFlag {
  /**
   *
   */
  YES("Y"), NO("N");

  private String name;

  YesNoFlag(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static YesNoFlag valueOf(int ordinal) {
    return YesNoFlag.values()[ordinal];
  }

  public static YesNoFlag fromAcronym(String acronym) {
    YesNoFlag result = YesNoFlag.YES;
    if (!StrUtil.isEmpty(acronym)) {
      for (YesNoFlag flag : YesNoFlag.values()) {
        if (acronym.equals(flag.toString())) {
          result = flag;
          break;
        }
      }
    }
    return result;
  }
}
