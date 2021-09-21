package com.paradm.kaptcha.utils;

import com.google.code.kaptcha.Constants;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

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

  public static Map<String, String> getParameters(KaptchaProperties.Kaptcha kaptcha) {
    Map<String, String> properties = new LinkedHashMap<>();
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, kaptcha.getSessionKey());
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_DATE, kaptcha.getSessionDateKey());

    properties.put(Constants.KAPTCHA_BORDER, kaptcha.getBorder().isEnabled() ? "yes" : "no");
    properties.put(Constants.KAPTCHA_BORDER_COLOR, getBorderColor(kaptcha.getBorder()));
    properties.put(Constants.KAPTCHA_BORDER_THICKNESS, getBorderThickness(kaptcha.getBorder()));

    properties.put(Constants.KAPTCHA_NOISE_IMPL, kaptcha.getNoise().getImpl());
    properties.put(Constants.KAPTCHA_NOISE_COLOR, kaptcha.getNoise().getColor());

    properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, kaptcha.getObscurificator().getImpl());

    properties.put(Constants.KAPTCHA_PRODUCER_IMPL, kaptcha.getProducer().getImpl());

    properties.put(Constants.KAPTCHA_TEXTPRODUCER_IMPL, kaptcha.getTextProducer().getImpl());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, getCharString(kaptcha.getTextProducer().getCharacter()));
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, getCharLength(kaptcha.getTextProducer().getCharacter()));
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, getCharSpace(kaptcha.getTextProducer().getCharacter()));
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, getFontColor(kaptcha.getTextProducer().getFont()));
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, getFontSize(kaptcha.getTextProducer().getFont()));
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, getFontNames(kaptcha.getTextProducer().getFont()));

    properties.put(Constants.KAPTCHA_WORDRENDERER_IMPL, kaptcha.getWord().getImpl());

    properties.put(Constants.KAPTCHA_BACKGROUND_IMPL, kaptcha.getBackground().getImpl());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, kaptcha.getBackground().getClearFrom());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, kaptcha.getBackground().getClearTo());

    properties.put(Constants.KAPTCHA_IMAGE_WIDTH, kaptcha.getImage().getWidth().toString());
    properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, kaptcha.getImage().getHeight().toString());
    return properties;
  }

  private static String getBorderColor(KaptchaProperties.Border border) {
    return border.getColor();
  }

  private static String getBorderThickness(KaptchaProperties.Border border) {
    return border.getThickness().toString();
  }

  private static String getCharString(KaptchaProperties.TextProducer.Char character) {
    return character.getString();
  }

  private static String getCharSpace(KaptchaProperties.TextProducer.Char character) {
    return character.getSpace().toString();
  }

  private static String getCharLength(KaptchaProperties.TextProducer.Char character) {
    return character.getLength().toString();
  }

  private static String getFontColor(KaptchaProperties.TextProducer.Font font) {
    return font.getColor();
  }

  private static String getFontSize(KaptchaProperties.TextProducer.Font font) {
    return font.getSize().toString();
  }

  private static String getFontNames(KaptchaProperties.TextProducer.Font font) {
    return StringUtils.arrayToCommaDelimitedString(font.getNames());
  }
}
