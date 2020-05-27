package com.paradm.sse.services.user;

import com.paradm.sse.domain.user.model.UserRecordModel;
import com.paradm.sse.services.framework.IBaseService;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public interface IUserRecordService extends IBaseService {

  void initGlobalUserInfo();

  List<UserRecordModel> getAllUsers();
}
