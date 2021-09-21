package com.paradm.sse.business.controller;

import com.paradm.sse.framework.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * @author Jacky.shen
 * @create data 2020/5/27
 */
@Slf4j
@Controller
public class HomeController extends BaseController {

//  @Autowired
//  private IHomeService homeService;
//
//  @GetMapping(value = {"", "index"})
//  public String index(Model model, String locale, String checklicense, RedirectAttributes attr) {
//    String tilesView = TilesViewConstant.LOGIN;
//    try {
//      if (!Utility.isEmpty(checklicense)) {
//        //this.addError(req, ErrorConstant.COMMON_INVALID_LICENSE + checklicense);
//      } else {
//        if (homeService.checkInit()) {
//          return REDIRECT + UrlConstant.INIT_SYSTEM;
//        } else {
//
//        }
//      }
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//    }
//    return tilesView;
//  }
}
