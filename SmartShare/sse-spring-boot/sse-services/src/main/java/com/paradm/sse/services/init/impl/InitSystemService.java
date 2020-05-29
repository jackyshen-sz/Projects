package com.paradm.sse.services.init.impl;

import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.SystemParameterConstant;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.company.model.ParadmCompanyModel;
import com.paradm.sse.domain.dms.model.SmartshareLocMasterModel;
import com.paradm.sse.domain.init.model.InitSystemModel;
import com.paradm.sse.domain.system.model.SysParameterModel;
import com.paradm.sse.services.dms.ISmartshareLocMasterService;
import com.paradm.sse.services.init.IInitSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Slf4j
@Service
public class InitSystemService extends InitService implements IInitSystemService {

  @Autowired
  private ISmartshareLocMasterService locMasterService;

  @Override
  public void initSystemForm(Model model, String baseUrl) {
    InitSystemModel formModel = new InitSystemModel();
    try {
      List<ParadmCompany> companyList = paradmCompanyService.getAllCompany();
      if (!Utility.isEmpty(companyList)) {
        ParadmCompany paradmCompany = companyList.get(0);
        // company model
        ParadmCompanyModel paradmCompanyModel = new ParadmCompanyModel();
        paradmCompanyModel.setModelData(paradmCompany);
        formModel.setParadmCompanyModel(paradmCompanyModel);
        // parameter list model
        List<SysParameterModel> parameterModelList = new ArrayList<>();
        SysParameterModel parameterModel = new SysParameterModel();
        parameterModel.setParameterCode(SystemParameterConstant.APPLICATION_BASE_URL);
        parameterModel.setParameterValue(baseUrl);
        parameterModelList.add(parameterModel);
        formModel.setParameterModelList(parameterModelList);
        // locmaster model
        SmartshareLocMasterModel locMasterModel = locMasterService.getDefaultLocMaster();
        formModel.setLocMasterModel(locMasterModel);
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    model.addAttribute(ModelConstant.FORM_MODEL, formModel);
  }
}
