package com.paradm.sse.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/13
 */
@Component
@ConfigurationProperties(prefix = CustomizeProperties.CUSTOMIZE_PREFIX)
public class CustomizeProperties implements Serializable {

  public static final String CUSTOMIZE_PREFIX = "spring.customize";
}
