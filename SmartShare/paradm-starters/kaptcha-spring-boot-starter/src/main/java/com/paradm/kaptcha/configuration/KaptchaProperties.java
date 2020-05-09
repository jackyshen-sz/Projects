package com.paradm.kaptcha.configuration;

import com.google.code.kaptcha.Constants;
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
   * Whether to turn on the captcha
   */
  private boolean enabled = false;
  /**
   * Access path for captcha plug-in
   */
  private String urlMappings = "/kaptcha/image";
  /**
   * Related parameters
   */
  private Kaptcha properties = new Kaptcha();

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getUrlMappings() {
    return urlMappings;
  }

  public void setUrlMappings(String urlMappings) {
    this.urlMappings = urlMappings;
  }

  public Kaptcha getProperties() {
    return properties;
  }

  public void setProperties(Kaptcha properties) {
    this.properties = properties;
  }

  @Data
  public static class Kaptcha {

    private String sessionKey = Constants.KAPTCHA_SESSION_KEY;
    private String sessionDateKey = Constants.KAPTCHA_SESSION_DATE;

    private Border border;
    private Noise noise;
    private Obscurificator obscurificator;
    private Producer producer;
    private TextProducer textProducer;
    private Word word;
    private Background background;
    private Image image;
  }

  @Data
  public static class Border {
    private boolean enabled = true;
    private String color = "105,179,90";
    private Integer thickness = 1;
  }

  @Data
  public static class Noise {
    private String color = "black";
    private String impl = "com.google.code.kaptcha.impl.NoNoise";
  }

  @Data
  public static class Obscurificator {
    private String impl = "com.paradm.kaptcha.impl.NoDistortion";
  }

  @Data
  public static class Producer {
    private String impl = "com.google.code.kaptcha.impl.DefaultKaptcha";
  }

  @Data
  public static class TextProducer {
    private String impl = "com.google.code.kaptcha.text.impl.DefaultTextCreator";
    private Char character = new Char();
    private Font font = new Font();

    @Data
    public static class Char {
      private String string = "0123456789";
      private Integer length = 4;
      private Integer space = 2;
    }

    @Data
    public static class Font {
      private String[] names = new String[]{"Arial"};
      private String color = "98,98,98";
      private Integer size = 14;
    }
  }

  @Data
  public static class Word {
    private String impl = "com.google.code.kaptcha.text.impl.DefaultWordRenderer";
  }

  @Data
  public static class Background {
    private String impl = "com.google.code.kaptcha.impl.DefaultBackground";
    private String clearFrom = "238,238,238";
    private String clearTo = "238,238,238";
  }

  @Data
  public static class Image {
    private Integer width = 36;
    private Integer height = 15;
  }
}
