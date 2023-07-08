package com.paradm.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jacky.shen
 * @create date 2020/4/23
 */
@SpringBootApplication
@ComponentScan("com.paradm")
public class BusinessApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessApplication.class, args);
  }


}
