package com.paradm.sse.services.init.impl;

import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.services.framework.impl.BaseService;
import com.paradm.sse.services.init.IInitSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Slf4j
@Service
public class InitSystemService extends BaseService implements IInitSystemService {

  @Override
  public void initSystemForm(Model model, String baseUrl) {
    try {

    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
