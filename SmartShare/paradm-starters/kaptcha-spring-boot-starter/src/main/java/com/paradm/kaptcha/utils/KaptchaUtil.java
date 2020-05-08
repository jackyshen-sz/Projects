package com.paradm.kaptcha.utils;

import com.google.code.kaptcha.Constants;
import com.paradm.kaptcha.configuration.KaptchaProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * @author Jacky.shen
 * @create data 2020/4/27
 */
public class KaptchaUtil {

  public static boolean validation(boolean isValidate, String kaptchaValue) {
    boolean result = true;
    if (isValidate) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      String sessionValue = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
      result = !StringUtils.isEmpty(kaptchaValue) && kaptchaValue.equalsIgnoreCase(sessionValue);
    }
    return result;
  }

  public static Properties getProperties(KaptchaProperties.Kaptcha kaptcha) {
    Properties properties = new Properties();
    properties.put(Constants.KAPTCHA_BORDER, kaptcha.getBorder());
    properties.put(Constants.KAPTCHA_BORDER_COLOR, kaptcha.getBorderColor());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, kaptcha.getCharString());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, kaptcha.getCharLength());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, kaptcha.getCharSpace());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptcha.getFontColor());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, kaptcha.getFontSize());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, kaptcha.getFontNames());
    properties.put(Constants.KAPTCHA_NOISE_IMPL, kaptcha.getNoiseImpl());
    properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, kaptcha.getObscurificator());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, kaptcha.getBackgroundFrom());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, kaptcha.getBackgroundTo());
    properties.put(Constants.KAPTCHA_IMAGE_WIDTH, kaptcha.getImageWidth());
    properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, kaptcha.getImageHeight());
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, kaptcha.getSessionKey());
    return properties;
  }
}
