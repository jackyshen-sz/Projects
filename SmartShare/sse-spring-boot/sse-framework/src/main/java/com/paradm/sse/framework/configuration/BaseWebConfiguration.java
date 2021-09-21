package com.paradm.sse.framework.configuration;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.global.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public class BaseWebConfiguration {

  @Autowired
  protected ServletContext servletContext;

  public void init() {
    log.info("Starting SmartShare Business Version: {}", GlobalConstant.APPLICATION_NAME + Symbol.MINUS.getValue() + GlobalConstant.VERSION);
  }

//  protected void initLicense() {
//    try {
//      ClassPathResource classPathResource = new ClassPathResource("eip-license.xml");
//      SiteValidator.INSTANCE.init(classPathResource.getInputStream());
//    } catch (Exception e) {
//      log.info("load license failed");
//      throw new ApplicationException("load license failed");
//    }
//  }
//
//  @Autowired
//  protected ISysParameterService sysParameterService;
//  @Autowired
//  protected IUserRecordService userRecordService;
//
//  protected void initGlobalReference() {
//    log.info("init initGlobalReference start");
//    try {
//      sysParameterService.initGlobalSysParameter();
//      // Initialize user names and user emails and user icons hash tables.
//      userRecordService.initGlobalUserInfo();
//    } catch (Exception e) {
//      log.error("init initGlobalReference failed", e);
//      throw new ApplicationException("init initGlobalReference failed");
//    }
//    log.info("init initGlobalReference finished");
//  }
//
//  @Autowired
//  protected ISysFunctionService sysFunctionService;
//
//  protected void initSysFunction(ApplicationContainer applicationContainer){
//    log.info("init initSysFunction start");
//    try {
//      // get all activated system function
//      List<SysFunctionModel> sysFunctionModelList = sysFunctionService.getAllSysFunction();
//      applicationContainer.setSysFunctionList(sysFunctionModelList);
//    } catch (Exception e) {
//      log.error("init initSysFunction failed", e);
//      throw new ApplicationException("init initSysFunction failed");
//    }
//    log.info("init initSysFunction finished");
//  }
}
