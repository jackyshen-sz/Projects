package com.paradm.sse.framework.controller;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Slf4j
public class BaseController {

  protected static final String REDIRECT = "redirect:";
  protected static final String FORWARD = "forward:";

  protected HttpServletRequest request;
  protected HttpServletResponse response;

  protected String functionCode = null;

  public String getFunctionCode() {
    return functionCode;
  }

  public void setFunctionCode(Model model, String functionCode) {
    this.functionCode = functionCode;
    model.addAttribute(GlobalConstant.FUNCTION_CODE_KEY, functionCode);
  }

  @ModelAttribute
  public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
    log.debug("setReqAndResp...");
    if (Utility.isEmpty(request) || Utility.isEmpty(response)) {
      log.debug("get request and response...");
      if (!Utility.isEmpty(RequestContextHolder.getRequestAttributes())) {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
      }
    }
    this.request = request;
    this.response = response;
  }
}
