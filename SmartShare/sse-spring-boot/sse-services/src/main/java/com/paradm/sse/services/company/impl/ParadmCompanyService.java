package com.paradm.sse.services.company.impl;

import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.company.model.ParadmCompanyModel;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.persist.company.ParadmCompanyRepository;
import com.paradm.sse.services.company.IParadmCompanyService;
import com.paradm.sse.services.framework.impl.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  @Override
  public void updateSigninCompany(ParadmCompanyModel paradmCompanyModel, SessionContainer sessionContainer) {
    try {
      Optional<ParadmCompany> optional = paradmCompanyRepository.findById(Utility.parseInteger(paradmCompanyModel.getId()));
      if (optional.isPresent()) {
        ParadmCompany paradmCompany = optional.get();
        paradmCompany.setCompanyName(paradmCompanyModel.getCompanyName());
        paradmCompany.setCode(paradmCompanyModel.getCode());
        paradmCompanyRepository.updateByCriteria(paradmCompany, sessionContainer);
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
