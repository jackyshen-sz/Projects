/*
 * @(#)NoDistortion.java
 */
package com.paradm.captcha;

import com.google.code.kaptcha.GimpyEngine;

import java.awt.image.BufferedImage;

/**
 * NoDistortion.java
 * 
 * @author Jackyshen
 */
public class NoDistortion implements GimpyEngine {

  @Override
  public BufferedImage getDistortedImage(BufferedImage bi) {
    return bi;
  }

}
