package com.paradm.sse.common.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.paradm.sse.common.constant.paramter.ParameterCode;
import com.paradm.sse.common.enums.YesNoFlag;
import com.paradm.sse.common.factory.SystemParameterFactory;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Slf4j
public class Utility {

  public static boolean parseBoolean(YesNoFlag flag) {
    return BooleanUtil.toBoolean(flag.toString());
  }

  public static Integer parseInteger(String inStr) {
    try {
      return (Integer.valueOf(inStr));
    } catch (Exception e) {
      return (null);
    }
  }

  public static String formatInteger(Integer intValue) {
    if (ObjectUtil.isEmpty(intValue)) {
      return "";
    }
    return (NumberUtil.toStr(intValue));
  }

  public static Long parseLong(String inStr) {
    try {
      return (Long.valueOf(inStr));
    } catch (Exception e) {
      return (null);
    }
  }

  public static String formatLong(Long longValue) {
    if (ObjectUtil.isEmpty(longValue)) {
      return "";
    }
    return (NumberUtil.toStr(longValue));
  }

  public static String formatDate(Date inDate) {
    String format = SystemParameterFactory.getSystemParameter(ParameterCode.DB_DATE_FORMAT.getKey());
    return formatDate(inDate, format);
  }

  public static String formatDate(Date inDate, String format) {
    if (ObjectUtil.isEmpty(inDate)) {
      return "";
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(inDate);
  }

  public static Date parseDate(String inStr) {
    String format = SystemParameterFactory.getSystemParameter(ParameterCode.DB_DATE_FORMAT.getKey());
    return parseDate(inStr, format);
  }

  public static Date parseDate(String inStr, String format) {
    if (StrUtil.isEmpty(inStr)) {
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
}
