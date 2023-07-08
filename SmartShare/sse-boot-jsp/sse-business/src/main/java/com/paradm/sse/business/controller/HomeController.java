package com.paradm.sse.business.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.paradm.sse.common.constant.TilesViewConstant;
import com.paradm.sse.common.constant.UrlConstant;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.framework.controller.BaseController;
import com.paradm.sse.services.framework.IHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Jacky.shen
 * @create data 2020/5/27
 */
@Slf4j
@Controller
public class HomeController extends BaseController {

  @Autowired
  private IHomeService homeService;

  @GetMapping(value = {"", "index"})
  public String index(Model model, String locale, String checkLicense, RedirectAttributes attr) {
    String tilesView = TilesViewConstant.LOGIN;
    try {
      if (!CharSequenceUtil.isEmpty(checkLicense)) {
        //this.addError(req, ErrorConstant.COMMON_INVALID_LICENSE + checkLicense);
      } else {
        if (homeService.checkInit()) {
          return REDIRECT + UrlConstant.INIT_SYSTEM;
        } else {

        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return tilesView;
  }
}
