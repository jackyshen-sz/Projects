package com.paradm.sse.services.framework.impl;

import cn.hutool.core.collection.IterUtil;
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
      if (IterUtil.isEmpty(userModelList)) {
        initFlag = true;
      }
    } catch (Exception e) {
      log.error("get all user error.");
    }
    return initFlag;
  }
}
