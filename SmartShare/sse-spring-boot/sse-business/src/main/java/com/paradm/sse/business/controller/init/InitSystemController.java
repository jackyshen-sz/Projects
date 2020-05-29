package com.paradm.sse.business.controller.init;

import com.paradm.sse.common.constant.GlobalConstant;
import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.services.init.IInitSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
