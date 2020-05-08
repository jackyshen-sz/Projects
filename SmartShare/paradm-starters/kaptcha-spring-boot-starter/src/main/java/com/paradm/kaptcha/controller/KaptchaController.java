package com.paradm.kaptcha.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author Jacky.shen
 * @create data 2020/5/8
 */
@Controller
@RequestMapping("kaptcha")
public class KaptchaController {

  @Autowired
  private Producer kaptchaProducer = null;

  @RequestMapping("image")
  public void image(HttpServletRequest request, HttpServletResponse response) {
    try (ServletOutputStream out = response.getOutputStream()) {
      HttpSession session = request.getSession();
      response.setDateHeader("Expires", 0);
      // Set standard HTTP/1.1 no-cache headers.
      response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
      // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
      response.addHeader("Cache-Control", "post-check=0, pre-check=0");
      // Set standard HTTP/1.0 no-cache header.
      response.setHeader("Pragma", "no-cache");
      // return a jpeg
      response.setContentType("image/jpeg");
      // create the text for the image
      String capText = kaptchaProducer.createText();
      // store the text in the session
      session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
      // create the image with the text
      BufferedImage bi = kaptchaProducer.createImage(capText);
      // write the data out
      ImageIO.write(bi, "jpg", out);
      out.flush();
    } catch (Exception e) {
    }
  }
}
