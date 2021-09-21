package com.paradm.sse.services.framework;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public interface IBaseService {

  /**
   * get user full-name by user id
   *
   * @param userId
   * @return
   */
  String getUserFullName(Integer userId);
}
