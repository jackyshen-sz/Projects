package com.paradm.kaptcha.configuration;

import com.google.code.kaptcha.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author Jacky.shen
 * @create data 2020/4/27
 */
@ConfigurationProperties(prefix = KaptchaProperties.KAPTCHA_PREFIX)
public class KaptchaProperties {

  public static final String KAPTCHA_PREFIX = "spring.kaptcha";
  /**
   * Gets the value that kaptcha needs to set based on this parameter
   */
  private String suffix = "";
  /**
   * Related parameters
   */
  private Kaptcha properties = new Kaptcha();

  private boolean enabled = false;

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

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

  public Properties getKaptchaProperties() {
    return this.getProperties().getProperties();
  }

  @Data
  public static class Kaptcha {
    private String border;
    private String borderColor;
    private String charString;
    private String charLength;
    private String charSpace;
    private String fontColor;
    private String fontSize;
    private String fontNames;
    private String noiseImpl;
    private String obscurificator;
    private String backgroundFrom;
    private String backgroundTo;
    private String imageWidth;
    private String imageHeight;
    private String sessionKey;

    public Properties getProperties() {
      Properties properties = new Properties();
      properties.put(Constants.KAPTCHA_BORDER, this.getBorder());
      properties.put(Constants.KAPTCHA_BORDER_COLOR, this.getBorderColor());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, this.getCharString());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, this.getCharLength());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, this.getCharSpace());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, this.getFontColor());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, this.getFontSize());
      properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, this.getFontNames());
      properties.put(Constants.KAPTCHA_NOISE_IMPL, this.getNoiseImpl());
      properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, this.getObscurificator());
      properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, this.getBackgroundFrom());
      properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, this.getBackgroundTo());
      properties.put(Constants.KAPTCHA_IMAGE_WIDTH, this.getImageWidth());
      properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, this.getImageHeight());
      properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, this.getSessionKey());
      return properties;
    }
  }
}
