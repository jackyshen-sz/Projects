package com.paradm.sse.business.configuration;

import com.paradm.sse.framework.configuration.BaseWebConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
@Configuration
public class WebConfiguration extends BaseWebConfiguration {

  @Override
  @PostConstruct
  public void init() {
    super.init();

//    this.initLicense();
//
//    // Initialize system parameter
//    this.initGlobalReference();
//
//    // Create a new ApplicationContainer instance
//    ApplicationContainer applicationContainer = ApplicationContainer.INSTANCE;
//    // Initialize System Function and store the cache into ApplicationContainer
//    this.initSysFunction(applicationContainer);
//
//    servletContext.setAttribute(GlobalConstant.APPLICATION_CONTAINER_KEY, applicationContainer);
  }

}
