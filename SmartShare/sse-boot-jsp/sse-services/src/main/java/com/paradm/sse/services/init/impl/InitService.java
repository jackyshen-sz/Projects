package com.paradm.sse.services.init.impl;

import com.paradm.sse.services.company.IParadmCompanyService;
import com.paradm.sse.services.framework.impl.BaseService;
import com.paradm.sse.services.init.IInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Slf4j
@Service
public class InitService extends BaseService implements IInitService {

  @Autowired
  protected IParadmCompanyService paradmCompanyService;
}
