package com.paradm.sse.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jacky.shen
 * @create data 2020/5/9
 */
@Controller
public class TestController {

  @GetMapping("hello")
  public String hello() {
    return "index";
  }
}
