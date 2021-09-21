package com.paradm.sse.services.framework.impl;

import cn.hutool.core.util.StrUtil;
import com.paradm.sse.common.factory.UserInfoFactory;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.user.entity.UserRecord;
import com.paradm.sse.persist.user.UserRecordRepository;
import com.paradm.sse.services.framework.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Service
public class BaseService implements IBaseService {

  @Autowired
  protected UserRecordRepository userRecordRepository;

  @Override
  public String getUserFullName(Integer userId) {
    String fullName = UserInfoFactory.getUserFullName(userId);
    if (StrUtil.isEmpty(fullName)) {
      Optional<UserRecord> optional = userRecordRepository.findById(userId);
      String[] userInfo = new String[3];
      if (optional.isPresent()) {
        UserRecord userRecord = optional.get();
        userInfo[0] = userRecord.getFullName();
        userInfo[1] = userRecord.getLoginName().toLowerCase();
        userInfo[2] = StrUtil.isEmpty(userRecord.getUserDef1()) ? "" : userRecord.getUserDef1();
        fullName = userInfo[0];
        UserInfoFactory.setUserInfo(userId, userInfo);
      }
    }
    return fullName;
  }

  protected void setCreateAndUpdateName(BaseModel baseModel) {
    baseModel.setCreateName(this.getUserFullName(Utility.parseInteger(baseModel.getCreatorId())));
    baseModel.setUpdateName(this.getUserFullName(Utility.parseInteger(baseModel.getUpdaterId())));
  }
}
