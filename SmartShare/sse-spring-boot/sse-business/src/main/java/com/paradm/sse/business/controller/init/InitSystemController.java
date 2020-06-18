package com.paradm.sse.business.controller.init;

import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.constant.global.WebStatus;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.factory.MessageResourcesFactory;
import com.paradm.sse.domain.init.model.InitSystemModel;
import com.paradm.sse.services.init.IInitSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Slf4j
@Controller
@RequestMapping("init/system")
public class InitSystemController extends InitController {

  @Autowired
  private IInitSystemService initSystemService;

  @GetMapping("sign-in")
  public String signin(Model model) {
    log.debug("init system form...");
    StringBuilder baseUrl = new StringBuilder(request.getScheme());
    baseUrl.append(Symbol.COLON.getValue()).append(Symbol.SLASH.getValue()).append(Symbol.SLASH.getValue());
    baseUrl.append(request.getServerName());
    baseUrl.append(Symbol.COLON.getValue());
    baseUrl.append(request.getServerPort());
    baseUrl.append(request.getContextPath());
    initSystemService.initSystemForm(model, baseUrl.toString());
    return TilesViewConstant.INIT_SYSTEM_SIGNIN;
  }

  @PostMapping("sign-in")
  public @ResponseBody
  WebAsyncTask<Map<String, ? extends Object>> signin (Model model, @ModelAttribute InitSystemModel initSystemModel) {
    Map<String, Object> result = new HashMap<>();
    Callable<Map<String, ? extends Object>> asyncTask = null;
    String status = "";
    String message = "";
    try {
      this.checkCaptcha(initSystemModel);
      status = WebStatus.SUCCESSFUL.getValue();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, WebStatus.SUCCESSFUL.getValue());
      asyncTask = () -> {
        // create rabbitmq
        return result;
      };
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      status = WebStatus.FAILED.getValue();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, MessageResourcesFactory.getMessage(e.getMessage(), e.getMsgArg()));
      asyncTask = () -> result;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      status = WebStatus.FAILED.getValue();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, MessageResourcesFactory.getMessage(CommonError.COMMON_UNKNOWN_ERROR.getKey()));
      asyncTask = () -> result;
    }
    WebAsyncTask<Map<String, ? extends Object>> webAsyncTask = new WebAsyncTask<>(1000l, asyncTask);
    webAsyncTask.onTimeout(() -> result);
    return webAsyncTask;
  }
}
