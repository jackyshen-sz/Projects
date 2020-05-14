package com.paradm.sse.security.web.authentication.handler;

import com.paradm.sse.common.constant.GlobalConstant;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * @author Jacky.shen
 * @create data 2020/5/14
 */
public class ParadmWebAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private String loginMethod = GlobalConstant.LoginMethod.WEB.toString();

}
