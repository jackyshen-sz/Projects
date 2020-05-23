package com.paradm.sse.framework.configuration;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.license.SiteValidator;
import com.paradm.sse.domain.framework.ApplicationContainer;
import com.paradm.sse.domain.system.model.SysFunctionModel;
import com.paradm.sse.services.system.ISysFunctionService;
import com.paradm.sse.services.system.ISysParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public class BaseWebConfiguration {

  public void init() {
    log.info("Starting SmartShare Business Version: {}", GlobalConstant.APPLICATION_NAME + GlobalConstant.Symbol.MINUS + GlobalConstant.VERSION);
  }

  protected void initLicense() {
    try {
      ClassPathResource classPathResource = new ClassPathResource("eip-license.xml");
      SiteValidator.INSTANCE.init(classPathResource.getInputStream());
    } catch (Exception e) {
      log.info("load license failed");
      throw new ApplicationException("load license failed");
    }
  }

  @Autowired
  protected ISysParameterService sysParameterService;

  protected void initGlobalReference() {
    sysParameterService.initGlobalSysParameter();
  }

  @Autowired
  protected ISysFunctionService sysFunctionService;

  protected void initSysFunction(ApplicationContainer applicationContainer){
    log.info("init initSysFunction start");
    try {
      // get all activated system function
      List<SysFunctionModel> sysFunctionModelList = sysFunctionService.getAllSysFunction();
      applicationContainer.setSysFunctionList(sysFunctionModelList);
    } catch (Exception e) {
      log.error("init initSysFunction failed", e);
      throw new ApplicationException("init initSysFunction failed");
    }
    log.info("init initSysFunction finished");
  }
}
