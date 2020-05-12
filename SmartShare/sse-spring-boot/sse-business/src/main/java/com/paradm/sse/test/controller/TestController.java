package com.paradm.sse.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jacky.shen
 * @create data 2020/5/9
 */
@Controller
public class TestController {

  @GetMapping("hello")
  public String hello(Model model) {
    //    model.addAttribute("pref", "1");
    return "index";
  }
}
