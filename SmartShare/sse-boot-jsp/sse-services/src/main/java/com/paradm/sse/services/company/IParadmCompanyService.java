package com.paradm.sse.services.company;

import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.company.model.ParadmCompanyModel;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.services.framework.IBaseService;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
public interface IParadmCompanyService extends IBaseService {

  List<ParadmCompany> getAllCompany();

  void updateSigninCompany(ParadmCompanyModel paradmCompanyModel, SessionContainer sessionContainer);
}
