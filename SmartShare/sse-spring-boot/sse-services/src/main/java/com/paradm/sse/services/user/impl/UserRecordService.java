package com.paradm.sse.services.user.impl;

import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.common.factory.UserInfoFactory;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.user.entity.UserRecord;
import com.paradm.sse.persist.user.UserRecordRepository;
import com.paradm.sse.services.framework.impl.BaseServce;
import com.paradm.sse.services.user.IUserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Slf4j
@Service
public class UserRecordService extends BaseServce implements IUserRecordService {

  @Autowired
  private UserRecordRepository userRecordRepository;

  @Override
  public void initGlobalUserInfo() {
    try {
      List<UserRecord> userList = userRecordRepository.findByRecordStatusAndIdGreaterThan(RecordStatus.ACTIVE, 0);
      if (!Utility.isEmpty(userList)) {
        Map<Integer, String[]> userInfoHash = new Hashtable<>();
        userList.forEach(userRecord -> userInfoHash.put(userRecord.getId(), new String[] {userRecord.getFullName(), userRecord.getLoginName(), userRecord.getUserDef1()}));
        UserInfoFactory.init(userInfoHash);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
