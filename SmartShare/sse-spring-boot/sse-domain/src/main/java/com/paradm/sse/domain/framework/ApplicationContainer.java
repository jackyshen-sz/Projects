package com.paradm.sse.domain.framework;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.ObjectUtil;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.system.model.SysFunctionModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public enum ApplicationContainer {
  /**
   *
   */
  INSTANCE;

  private List<SysFunctionModel> sysFunctionList = new ArrayList<>();

  private Map<Integer, SysFunctionModel> hashSysFunction = new Hashtable<>();

  /**
   * Get system function list.
   *
   * @return A list of SysFunction in the system.
   */
  public List<SysFunctionModel> getSysFunctionList() {
    return sysFunctionList;
  }

  /**
   * Set system function list.
   *
   * @param sysFunctionList A list of SysFunction in the system.
   */
  public void setSysFunctionList(List<SysFunctionModel> sysFunctionList) {
    if (!IterUtil.isEmpty(sysFunctionList)) {
      this.sysFunctionList = sysFunctionList;
      for (SysFunctionModel sysFunctionModel : sysFunctionList) {
        this.hashSysFunction.put(Utility.parseInteger(sysFunctionModel.getId()), sysFunctionModel);
//        this.hashSysFunctionCodeToId.put(sysFunctionModel.getFunctionCode(), sysFunctionModel.getId());
      }
    }
  }

  /**
   * Get system function by functionId.
   *
   * @param functionId The function Id.
   * @return The SysFunction object of that function Id.
   * @see com.paradm.sse.domain.system.model.SysFunctionModel
   */
  public SysFunctionModel getSysFunctionByFunctionId(Integer functionId) {
    if (ObjectUtil.isEmpty(functionId)) {
      return (null);
    }
    return this.hashSysFunction.get(functionId);
  }
}
