package com.paradm.sse.services.system.impl;

import com.paradm.sse.common.constant.ErrorConstant;
import com.paradm.sse.common.enums.FunctionStatus;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.system.entity.SysFunction;
import com.paradm.sse.domain.system.model.SysFunctionModel;
import com.paradm.sse.persist.system.SysFunctionRepository;
import com.paradm.sse.services.framework.impl.BaseService;
import com.paradm.sse.services.system.ISysFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
@Service
public class SysFunctionService extends BaseService implements ISysFunctionService {

  @Autowired
  private SysFunctionRepository sysFunctionRepository;

  @Override
  public List<SysFunctionModel> getAllSysFunction() {
    List<SysFunctionModel> result = null;
    try {
      List<SysFunction> tempResult = sysFunctionRepository.findByStatusOrderByDisplaySeqAsc(FunctionStatus.ACTIVE);
      if (!Utility.isEmpty(tempResult)) {
        result = tempResult.stream().map(sysFunction -> {
          SysFunctionModel sysFunctionModel = new SysFunctionModel();
          sysFunctionModel.setModelData(sysFunction);
          return sysFunctionModel;
        }).collect(Collectors.toList());
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(ErrorConstant.DB_SELECT_ERROR.getCode());
    }
    return result;
  }
}
