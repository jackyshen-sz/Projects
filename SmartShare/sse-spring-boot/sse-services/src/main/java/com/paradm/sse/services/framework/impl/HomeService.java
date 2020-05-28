package com.paradm.sse.services.framework.impl;

import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.user.model.UserRecordModel;
import com.paradm.sse.services.framework.IHomeService;
import com.paradm.sse.services.user.IUserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/27
 */
@Slf4j
@Service
public class HomeService extends BaseService implements IHomeService {

  @Autowired
  private IUserRecordService userRecordService;

  @Override
  public boolean checkInit() {
    boolean initFlag = false;
    try {
      List<UserRecordModel> userModelList = userRecordService.getAllUsers();
      if (Utility.isEmpty(userModelList)) {
        initFlag = true;
      }
    } catch (Exception e) {
      log.error("get all user error.");
    }
    return initFlag;
  }
}
