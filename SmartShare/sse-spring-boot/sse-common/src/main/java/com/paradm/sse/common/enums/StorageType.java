package com.paradm.sse.common.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
public enum StorageType {
  /**
   *
   */
  MONGO("M"), DROPBOX("D"), ONE_DRIVE("O"), STANDARD("S");

  private String name;

  StorageType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static StorageType fromAcronym(String acronym) {
    StorageType result = StorageType.STANDARD;
    if (!StrUtil.isEmpty(acronym)) {
      for (StorageType storageType : StorageType.values()) {
        if (acronym.equals(storageType.toString())) {
          result = storageType;
          break;
        }
      }
    }
    return result;
  }
}
