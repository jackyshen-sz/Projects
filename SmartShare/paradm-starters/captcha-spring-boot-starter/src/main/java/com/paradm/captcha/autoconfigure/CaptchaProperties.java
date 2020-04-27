package com.paradm.captcha.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@ConfigurationProperties(prefix = "paradm.captcha")
public class CaptchaProperties implements Serializable {

  private static final long serialVersionUID = 6215888958860757215L;

  private boolean enabled = false;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getBorder() {
    return border;
  }

  public void setBorder(String border) {
    this.border = border;
  }

  public String getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

  public String getCharString() {
    return charString;
  }

  public void setCharString(String charString) {
    this.charString = charString;
  }

  public String getCharLength() {
    return charLength;
  }

  public void setCharLength(String charLength) {
    this.charLength = charLength;
  }

  public String getCharSpace() {
    return charSpace;
  }

  public void setCharSpace(String charSpace) {
    this.charSpace = charSpace;
  }

  public String getFontColor() {
    return fontColor;
  }

  public void setFontColor(String fontColor) {
    this.fontColor = fontColor;
  }

  public String getFontSize() {
    return fontSize;
  }

  public void setFontSize(String fontSize) {
    this.fontSize = fontSize;
  }

  public String getFontNames() {
    return fontNames;
  }

  public void setFontNames(String fontNames) {
    this.fontNames = fontNames;
  }

  public String getNoiseImpl() {
    return noiseImpl;
  }

  public void setNoiseImpl(String noiseImpl) {
    this.noiseImpl = noiseImpl;
  }

  public String getObscurificator() {
    return obscurificator;
  }

  public void setObscurificator(String obscurificator) {
    this.obscurificator = obscurificator;
  }

  public String getBackgroundFrom() {
    return backgroundFrom;
  }

  public void setBackgroundFrom(String backgroundFrom) {
    this.backgroundFrom = backgroundFrom;
  }

  public String getBackgroundTo() {
    return backgroundTo;
  }

  public void setBackgroundTo(String backgroundTo) {
    this.backgroundTo = backgroundTo;
  }

  public String getImageWidth() {
    return imageWidth;
  }

  public void setImageWidth(String imageWidth) {
    this.imageWidth = imageWidth;
  }

  public String getImageHeight() {
    return imageHeight;
  }

  public void setImageHeight(String imageHeight) {
    this.imageHeight = imageHeight;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }
}
