package com.paradm.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jacky.shen
 * @create data 2020/6/3
 */
@RestController
public class HelloController {

  @GetMapping("hello")
  public String hello() {
    return "hello world!";
  }
}
