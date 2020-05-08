package com.paradm.kaptcha.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jacky.shen
 * @create data 2020/4/27
 */
@ConfigurationProperties(prefix = KaptchaProperties.KAPTCHA_PREFIX)
public class KaptchaProperties {

  public static final String KAPTCHA_PREFIX = "spring.kaptcha";

  /**
   * Whether to turn on the kaptcha
   */
  private boolean enabled = false;
  /**
   * Related parameters
   */
  private Kaptcha properties = new Kaptcha();

  public Kaptcha getProperties() {
    return this.properties;
  }

  public void setProperties(Kaptcha properties) {
    this.properties = properties;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Data
  public static class Kaptcha {
    private String border = "no";
    private String borderColor = "105,179,90";
    private String charString = "0123456789";
    private String charLength = "4";
    private String charSpace = "2";
    private String fontColor = "98,98,98";
    private String fontSize = "14";
    private String fontNames = "Arial";
    private String noiseImpl = "com.google.code.kaptcha.impl.NoNoise";
    private String obscurificator = "com.paradm.kaptcha.impl.NoDistortion";
    private String backgroundFrom = "238,238,238";
    private String backgroundTo = "238,238,238";
    private String imageWidth = "36";
    private String imageHeight = "15";
    private String sessionKey = "code";
  }
}
