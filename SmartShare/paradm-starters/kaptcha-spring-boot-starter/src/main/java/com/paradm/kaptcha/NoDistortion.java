/*
 * @(#)NoDistortion.java
 */
package com.paradm.kaptcha;

import com.google.code.kaptcha.GimpyEngine;

import java.awt.image.BufferedImage;

/**
 * NoDistortion.java
 *
 * @author Jacky.shen
 * @create data 2020/4/27
 */
public class NoDistortion implements GimpyEngine {

  @Override
  public BufferedImage getDistortedImage(BufferedImage bi) {
    return bi;
  }

}
