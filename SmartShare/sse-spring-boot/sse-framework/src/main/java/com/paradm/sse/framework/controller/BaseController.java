package com.paradm.sse.framework.controller;

import cn.hutool.core.util.ObjectUtil;
import com.google.code.kaptcha.Constants;
import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.domain.framework.model.BaseModel;
import com.paradm.sse.domain.framework.model.SessionContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    if (ObjectUtil.isEmpty(request) || ObjectUtil.isEmpty(response)) {
      log.debug("get request and response...");
      if (ObjectUtil.isNotEmpty(RequestContextHolder.getRequestAttributes())) {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
      }
    }
    this.request = request;
    this.response = response;
  }

  @ExceptionHandler
  public String exceptionHandler(Exception ex) {
    log.error("controller throw exception...");
    log.error(ex.getMessage(), ex);
    return TilesViewConstant.ERROR;
  }

  protected SessionContainer getSessionContainer() {
    if (ObjectUtil.isEmpty(request) || ObjectUtil.isEmpty(request.getSession())) {
      log.debug("request is null...");
      if (ObjectUtil.isNotEmpty(RequestContextHolder.getRequestAttributes())) {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      }
    }
    return (SessionContainer) request.getSession().getAttribute(GlobalConstant.SESSION_CONTAINER_KEY);
  }

    protected void checkCaptcha(BaseModel baseModel) {
      if (ObjectUtil.isEmpty(baseModel.getCaptcha())
          || !baseModel.getCaptcha().equalsIgnoreCase((String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
        throw new ApplicationException(CommonError.KAPTCHA_WRONG_ERROR.getKey());
      }
    }
}
