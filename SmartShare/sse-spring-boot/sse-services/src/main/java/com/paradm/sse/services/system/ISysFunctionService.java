package com.paradm.sse.services.system;

import com.paradm.sse.domain.system.model.SysFunctionModel;
import com.paradm.sse.services.framework.IBaseServce;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public interface ISysFunctionService extends IBaseServce {

  List<SysFunctionModel> getAllSysFunction();
}
