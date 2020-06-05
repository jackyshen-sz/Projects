package com.paradm.sse.business.controller.init;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.common.exception.ApplicationException;
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
    baseUrl.append(GlobalConstant.Symbol.COLON.toString()).append(GlobalConstant.Symbol.SLASH.toString()).append(GlobalConstant.Symbol.SLASH.toString());
    baseUrl.append(request.getServerName());
    baseUrl.append(GlobalConstant.Symbol.COLON.toString());
    baseUrl.append(request.getServerPort());
    baseUrl.append(request.getContextPath());
    initSystemService.initSystemForm(model, baseUrl.toString());
    return TilesViewConstant.INIT_SYSTEM_SIGNIN;
  }

  @PostMapping("sign-in")
  public @ResponseBody
  WebAsyncTask<Map<String, ? extends Object>> signin (Model model, @ModelAttribute InitSystemModel initSystemModel, @RequestParam(defaultValue = "") String captcha) {
    Map<String, Object> result = new HashMap<>();
    Callable<Map<String, ? extends Object>> asyncTask = null;
    String status = "";
    String message = "";
    try {

      status = GlobalConstant.WebStatus.SUCCESSFUL.toString();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, message);
      asyncTask = () -> {
        // create rabbitmq
        return result;
      };
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      status = GlobalConstant.WebStatus.FAILED.toString();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, message);
      asyncTask = () -> result;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      status = GlobalConstant.WebStatus.FAILED.toString();
      result.put(ModelConstant.STATUS, status);
      result.put(ModelConstant.MESSAGE, message);
      asyncTask = () -> result;
    }
    WebAsyncTask<Map<String, ? extends Object>> webAsyncTask = new WebAsyncTask<>(1000l, asyncTask);
    webAsyncTask.onTimeout(() -> result);
    return webAsyncTask;
  }
}
