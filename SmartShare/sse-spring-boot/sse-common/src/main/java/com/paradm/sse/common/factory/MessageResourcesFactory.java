package com.paradm.sse.common.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Jacky.shen
 * @create data 2020/5/25
 */
@Component
public class MessageResourcesFactory implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    MessageResourcesFactory.applicationContext = applicationContext;
  }

  public static String getMessage(Locale loc, String key) {
    return getMessage(loc, key, "");
  }

  public static String getMessage(Locale loc, String key, Object... args) {
    return getMessage(key, args, "", loc);
  }

  private static String getMessage(String key, Object[] args, String defaultMessage, Locale loc) {
    return applicationContext.getMessage(key, args, defaultMessage, loc);
  }
}
