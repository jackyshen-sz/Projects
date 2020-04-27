package com.paradm.captcha.autoconfigure;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "paradm.captcha", value = "enabled", havingValue = "true")
  public DefaultKaptcha captchaProducer(CaptchaProperties prop) {
    DefaultKaptcha captchaProducer = new DefaultKaptcha();
    Properties properties = new Properties();
    properties.put(Constants.KAPTCHA_BORDER, prop.getBorder());
    properties.put(Constants.KAPTCHA_BORDER_COLOR, prop.getBorderColor());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, prop.getCharString());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, prop.getCharLength());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, prop.getCharSpace());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, prop.getFontColor());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, prop.getFontSize());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, prop.getFontNames());
    properties.put(Constants.KAPTCHA_NOISE_IMPL, prop.getNoiseImpl());
    properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, prop.getObscurificator());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, prop.getBackgroundFrom());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, prop.getBackgroundTo());
    properties.put(Constants.KAPTCHA_IMAGE_WIDTH, prop.getImageWidth());
    properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, prop.getImageHeight());
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, prop.getSessionKey());
    Config config = new Config(properties);
    captchaProducer.setConfig(config);
    return captchaProducer;
  }
}
