package com.paradm.sse.business.controller.init;

import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.framework.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jacky.shen
 * @create data 2020/5/27
 */
@Slf4j
@Controller
@RequestMapping("init")
public class InitController extends BaseController {

  @GetMapping(params = "system")
  public String initSystem(Model model) {
    return TilesViewConstant.INIT_SYSTEM;
  }
}
