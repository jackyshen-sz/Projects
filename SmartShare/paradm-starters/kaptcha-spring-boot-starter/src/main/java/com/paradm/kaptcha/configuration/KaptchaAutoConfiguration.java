package com.paradm.kaptcha.configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.paradm.kaptcha.utils.KaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author Jacky.shen
 * @createData 2020/4/27
 *
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = KaptchaProperties.KAPTCHA_PREFIX, name = "enabled", havingValue = "true")
  public ServletRegistrationBean<KaptchaServlet> kaptchaServlet(KaptchaProperties kaptchaProperties) {
    log.info("init KaptchaServlet url: {}", kaptchaProperties.getUrlMappings());
    ServletRegistrationBean<KaptchaServlet> registrationBean = new ServletRegistrationBean<>();
    registrationBean.setServlet(new KaptchaServlet());
    registrationBean.setUrlMappings(StringUtils.commaDelimitedListToSet(kaptchaProperties.getUrlMappings()));

    registrationBean.setInitParameters(KaptchaUtil.getParameters(kaptchaProperties.getProperties()));
    return registrationBean;
  }
}
