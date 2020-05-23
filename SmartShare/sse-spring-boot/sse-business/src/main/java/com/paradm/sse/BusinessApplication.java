package com.paradm.sse;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.domain.framework.ApplicationContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author Jacky.shen
 * @create data 2020/4/23
 */
@SpringBootApplication
public class BusinessApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessApplication.class, args);
    ApplicationContainer applicationContainer =
        (ApplicationContainer) RequestContextHolder.getRequestAttributes().getAttribute(GlobalConstant.APPLICATION_CONTAINER_KEY, RequestAttributes.SCOPE_SESSION);
    System.out.println(applicationContainer.getSysFunctionByFunctionId(""));
  }

}
