package com.paradm.sse.business.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Jacky.shen
 * @create data 2020/5/13
 */
@Data
@Component
@ConfigurationProperties(prefix = CustomizeProperties.CUSTOMIZE_PREFIX)
public class CustomizeProperties implements Serializable {
  private static final long serialVersionUID = -3192815276555574873L;

  public static final String CUSTOMIZE_PREFIX = "spring.customize";

  private Security security;

  @Data
  static class Security {
    private String permitAll;
    private String csrfIgnoreUrl = "/api/**";
    private Browser browser = new Browser();
    @Data
    static class Browser {
      private String loginPage = "/login";
      private String logoutUrl = "/logout";
    }
  }
}
