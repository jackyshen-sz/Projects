package com.paradm.sse.common.factory;

import com.paradm.sse.common.utils.Utility;

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
    if (Utility.isEmpty(userId) || Utility.isEmpty(hashUserInfo) || Utility.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[0];
  }

  public static String getUserEmail(Integer userId) {
    if (Utility.isEmpty(userId) || Utility.isEmpty(hashUserInfo) || Utility.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[1];
  }

  public static String getUserIcon(Integer userId) {
    if (Utility.isEmpty(userId) || Utility.isEmpty(hashUserInfo) || Utility.isEmpty(hashUserInfo.get(userId))) {
      return "";
    }
    return hashUserInfo.get(userId)[2];
  }

  public static void setUserInfo(Integer userId, String[] userInfo) {
    if (Utility.isEmpty(userId) || Utility.isEmpty(userInfo)) {
      return;
    }
    if (Utility.isEmpty(hashUserInfo)) {
      hashUserInfo = new Hashtable<>();
    }
    hashUserInfo.put(userId, userInfo);
  }
}
