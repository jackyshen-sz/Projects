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

  public static final String CUSTOMIZE_PREFIX = "spring.sse";

  private Security security;
  private System system;

  @Data
  static class System implements Serializable {

    private String encrypt;
  }

  @Data
  static class Security implements Serializable {
    private static final long serialVersionUID = 1181436267785566464L;

    private String permitAll;
    private String csrfIgnoreUrl = "/api/**";
    private String staticIgnoreUrl = "/webjars/**";
    private Browser browser = new Browser();
    @Data
    static class Browser implements Serializable {
      private static final long serialVersionUID = 6311816425331964905L;

      private String loginPage = "/login";
      private String logoutUrl = "/logout";
    }
  }
}
