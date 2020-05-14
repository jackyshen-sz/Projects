package com.paradm.sse.common.utils;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public class Utility {

  public static boolean isEmpty(Object val) {
    return (null == val);
  }

  public static boolean isEmpty(String str) {
    return StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim());
  }

  public static boolean isEmpty(CharSequence cs) {
    return (null == cs || cs.length() == 0 || "".equals(cs.toString().trim()));
  }

  public static <T> boolean isEmpty(Collection<T> c) {
    return (null == c || c.size() == 0);
  }

  public static String[] splitString(String src, String div) {
    return StringUtils.tokenizeToStringArray(src, div);
  }

  public final static boolean parseBoolean(String inStr) {
    if (isEmpty(inStr)) {
      return false;
    }
    switch (inStr.toLowerCase()) {
      case "1":
      case "y":
      case "yes":
      case "t":
      case "true":
        return true;
    }
    return false;
  }
}
