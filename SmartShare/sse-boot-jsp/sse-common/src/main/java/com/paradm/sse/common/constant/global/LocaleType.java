package com.paradm.sse.common.constant.global;

import com.paradm.sse.common.constant.GlobalConstant;
import org.springframework.util.StringUtils;

import java.util.EnumMap;
import java.util.Locale;

/**
 * @author Jacky.shen
 * @create date 2021/10/8
 */
public enum LocaleType implements GlobalConstant {
  /**
   *
   */
  ENGLISH("en"), SIMPLIFIED_CHINESE("zh_CN"), TRADITIONAL_CHINESE("zh_HK"),
  ;

  private final String name;

  LocaleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static boolean contains(Locale locale) {
    EnumMap<LocaleType, Locale> enumMap = new EnumMap<>(LocaleType.class);
    for (LocaleType localeType : LocaleType.values()) {
      enumMap.put(localeType, StringUtils.parseLocaleString(localeType.toString()));
    }
    return enumMap.containsValue(locale);
  }

  @Override
  public String getKey() {
    return toString();
  }

  @Override
  public String getValue() {
    return toString();
  }

}
