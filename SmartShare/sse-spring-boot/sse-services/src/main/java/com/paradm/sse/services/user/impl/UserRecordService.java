package com.paradm.sse.services.user.impl;

import cn.hutool.core.collection.IterUtil;
import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.factory.UserInfoFactory;
import com.paradm.sse.domain.user.entity.UserRecord;
import com.paradm.sse.domain.user.model.UserRecordModel;
import com.paradm.sse.persist.user.UserRecordRepository;
import com.paradm.sse.services.framework.impl.BaseService;
import com.paradm.sse.services.user.IUserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Slf4j
@Service
public class UserRecordService extends BaseService implements IUserRecordService {

  @Autowired
  private UserRecordRepository userRecordRepository;

  @Override
  public void initGlobalUserInfo() {
    try {
      List<UserRecord> userList = userRecordRepository.findByRecordStatus(RecordStatus.ACTIVE);
      if (IterUtil.isNotEmpty(userList)) {
        Map<Integer, String[]> userInfoHash = new Hashtable<>();
        userList.forEach(userRecord -> userInfoHash.put(userRecord.getId(), new String[] {userRecord.getFullName(), userRecord.getLoginName(), userRecord.getUserDef1()}));
        UserInfoFactory.init(userInfoHash);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public List<UserRecordModel> getAllUsers() {
    List<UserRecordModel> result = null;
    try {
      List<UserRecord> userRecordList = userRecordRepository.findByRecordStatus(RecordStatus.ACTIVE);
      if (IterUtil.isNotEmpty(userRecordList)) {
        result = userRecordList.stream().map(UserRecordModel::new).collect(Collectors.toList());
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(CommonError.DB_SELECT_ERROR.getKey());
    }
    return result;
  }

  private UserRecordModel setUserRecordModel(UserRecord userRecord) {
    return this.setUserRecordModel(userRecord, false, false);
  }

  private UserRecordModel setUserRecordModel(UserRecord userRecord, boolean roleFlag, boolean groupFlag) {
    UserRecordModel userRecordModel = new UserRecordModel(userRecord);
//    userRecordModel.setRulesType(RulesType.USER.toString());
//    this.setCreateAndUpdateName(userRecordModel);
//    if (roleFlag) {
//      List<UserRoleModel> authorities = userRoleService.getListByUserId(userRecord.getId());
//      userRecordModel.setAuthorities(authorities);
//    }
//    if (groupFlag) {
//      List<UserGroupModel> userGroups = userGroupService.getByUserId(userRecord.getId());
//      userRecordModel.setUserGroups(userGroups);
//    }
    return userRecordModel;
  }
}
