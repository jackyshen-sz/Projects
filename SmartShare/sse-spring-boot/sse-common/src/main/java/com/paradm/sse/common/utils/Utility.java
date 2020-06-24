package com.paradm.sse.common.utils;

import com.paradm.sse.common.constant.paramter.ParameterCode;
import com.paradm.sse.common.enums.YesNoFlag;
import com.paradm.sse.common.factory.SystemParameterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Slf4j
public class Utility {

  public static boolean isNull(Object val) {
    return (null == val);
  }

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

  public static Integer parseInteger(String inStr) {
    try {
      return (Integer.valueOf(inStr));
    } catch (Exception e) {
      return (null);
    }
  }

  public static String formatInteger(Integer intValue) {
    if (isEmpty(intValue)) {
      return "";
    }
    return (String.valueOf(intValue));
  }

  public static Long parseLong(String inStr) {
    try {
      return (Long.valueOf(inStr));
    } catch (Exception e) {
      return (null);
    }
  }

  public static String formatLong(Long intValue) {
    if (isEmpty(intValue)) {
      return "";
    }
    return (String.valueOf(intValue));
  }

  public static boolean parseBoolean(String inStr) {
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

  public static boolean parseBoolean(YesNoFlag flag) {
    if (Utility.isEmpty(flag)) {
      return false;
    }
    switch (flag) {
      case YES:
        return true;
      default:
        return false;
    }
  }

  public static Date parseDate(String inStr) {
    String format = SystemParameterFactory.getSystemParameter(ParameterCode.DB_DATE_FORMAT.getKey());
    return parseDate(inStr, format);
  }

  public static Date parseDate(String inStr, String format) {
    if (Utility.isEmpty(inStr)) {
      return (null);
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.parse(inStr);
    } catch (ParseException e) {
      log.error("parse date error.");
      return (null);
    }
  }

  public static String formatDate(Date inDate) {
    String format = SystemParameterFactory.getSystemParameter(ParameterCode.DB_DATE_FORMAT.getKey());
    return formatDate(inDate, format);
  }

  public static String formatDate(Date inDate, String format) {
    if (Utility.isEmpty(inDate)) {
      return "";
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(inDate);
  }
}
