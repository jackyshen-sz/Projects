package com.paradm.sse.services.system;

import com.paradm.sse.services.framework.IBaseService;

import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
public interface ISysParameterService extends IBaseService {

  Map<String, String> getAllSysParameter();

  void initGlobalSysParameter();

  void initGlobalSysParameter(Map<String, String> sysParaHash);
}
