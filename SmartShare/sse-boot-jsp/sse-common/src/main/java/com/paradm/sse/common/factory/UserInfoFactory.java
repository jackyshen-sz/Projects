package com.paradm.sse.common.factory;

import cn.hutool.core.util.ObjectUtil;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/5/25
 */
public class UserInfoFactory {

  private static Map<Integer, String[]> hashUserInfo = new Hashtable<>();

  public static void init(Map<Integer, String[]> hashInfo) {
    hashUserInfo.putAll(hashInfo);
  }

  public static String getUserFullName(Integer userId) {
    if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(hashUserInfo) || ObjectUtil.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[0];
  }

  public static String getUserEmail(Integer userId) {
    if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(hashUserInfo) || ObjectUtil.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[1];
  }

  public static String getUserIcon(Integer userId) {
    if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(hashUserInfo) || ObjectUtil.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[2];
  }

  public static void setUserInfo(Integer userId, String[] userInfo) {
    if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(userInfo)) {
      return;
    }
    if (ObjectUtil.isEmpty(hashUserInfo)) {
      hashUserInfo = new Hashtable<>();
    }
    hashUserInfo.put(userId, userInfo);
  }
}
