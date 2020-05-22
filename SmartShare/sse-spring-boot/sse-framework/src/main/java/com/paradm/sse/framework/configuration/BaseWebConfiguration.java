package com.paradm.sse.framework.configuration;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.license.SiteValidator;
import com.paradm.sse.services.system.ISysParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

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
}
