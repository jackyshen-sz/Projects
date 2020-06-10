package com.paradm.sse.services.dms.impl;

import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.dms.entity.SmartshareLocMaster;
import com.paradm.sse.domain.dms.model.SmartshareLocMasterModel;
import com.paradm.sse.persist.dms.SmartshareLocMasterRepository;
import com.paradm.sse.services.dms.ISmartshareLocMasterService;
import com.paradm.sse.services.framework.impl.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Slf4j
@Service
public class SmartshareLocMasterService extends BaseService implements ISmartshareLocMasterService {

  @Autowired
  private SmartshareLocMasterRepository locMasterRepository;

  @Override
  public SmartshareLocMasterModel getDefaultLocMaster() {
    SmartshareLocMasterModel locMasterModel = null;
    try {
      List<SmartshareLocMaster> locMasterList = locMasterRepository.findAll();
      if (!Utility.isEmpty(locMasterList)) {
        locMasterModel = new SmartshareLocMasterModel();
        locMasterModel.setModelData(locMasterList.get(0));
        this.setCreateAndUpdateName(locMasterModel);
      }
    } catch (ApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new ApplicationException(CommonError.DB_SELECT_ERROR.getKey());
    }
    return locMasterModel;
  }
}
