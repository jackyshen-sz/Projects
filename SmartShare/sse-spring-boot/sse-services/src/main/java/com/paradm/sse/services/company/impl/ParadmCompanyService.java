package com.paradm.sse.services.company.impl;

import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.persist.company.ParadmCompanyRepository;
import com.paradm.sse.services.company.IParadmCompanyService;
import com.paradm.sse.services.framework.impl.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Slf4j
@Service
public class ParadmCompanyService extends BaseService implements IParadmCompanyService {

  @Autowired
  private ParadmCompanyRepository paradmCompanyRepository;

  @Override
  public List<ParadmCompany> getAllCompany() {
    log.info("get all active company...");
    return paradmCompanyRepository.findByRecordStatus(RecordStatus.ACTIVE);
  }
}
