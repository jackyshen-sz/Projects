package com.paradm.sse.services.init.impl;

import com.paradm.sse.common.constant.ModelConstant;
import com.paradm.sse.common.constant.error.CommonError;
import com.paradm.sse.common.constant.error.InitError;
import com.paradm.sse.common.constant.paramter.ParameterCode;
import com.paradm.sse.common.exception.ApplicationException;
import com.paradm.sse.common.utils.Utility;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import com.paradm.sse.domain.company.model.ParadmCompanyModel;
import com.paradm.sse.domain.dms.model.SmartshareLocMasterModel;
import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.domain.init.model.InitSystemModel;
import com.paradm.sse.domain.system.model.SysParameterModel;
import com.paradm.sse.domain.user.model.UserRecordModel;
import com.paradm.sse.services.company.IParadmCompanyService;
import com.paradm.sse.services.dms.ISmartshareLocMasterService;
import com.paradm.sse.services.init.IInitSystemService;
import com.paradm.sse.services.system.ISysParameterService;
import com.paradm.sse.services.user.IUserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
@Slf4j
@Service
public class InitSystemService extends InitService implements IInitSystemService {

  @Autowired
  private ISmartshareLocMasterService locMasterService;
  @Autowired
  private IUserRecordService userRecordService;
  @Autowired
  private IParadmCompanyService paradmCompanyService;
  @Autowired
  private ISysParameterService sysParameterService;

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
        parameterModel.setParameterCode(ParameterCode.APPLICATION_BASE_URL.getKey());
        parameterModel.setParameterValue(baseUrl);
        parameterModelList.add(parameterModel);
        formModel.setParameterModelList(parameterModelList);
        // loc-master model
        SmartshareLocMasterModel locMasterModel = locMasterService.getDefaultLocMaster();
        formModel.setLocMasterModel(locMasterModel);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    model.addAttribute(ModelConstant.FORM_MODEL, formModel);
  }

  @Override
  public void check(InitSystemModel initSystemModel) {
    try {
      ParadmCompanyModel paradmCompanyModel = initSystemModel.getParadmCompanyModel();
      UserRecordModel userModel = initSystemModel.getUserModel();
      List<SysParameterModel> parameterModelList = initSystemModel.getParameterModelList();
//      this.setEmailParameter(Utility.parseInteger(paradmCompanyModel.getId()), parameterModelList);
      if (Utility.isEmpty(paradmCompanyModel) || Utility.isEmpty(paradmCompanyModel.getId()) || Utility.isEmpty(parameterModelList) || Utility.isEmpty(userModel)) {
        throw new ApplicationException(InitError.PARAMETER_ERROR.getKey());
      }
      if (!Utility.isEmpty(parameterModelList)) {
        Map<String, SysParameterModel> parameterModelMap = new HashMap<>();
        parameterModelList.forEach(parameterModel -> {
          parameterModelMap.put(parameterModel.getParameterCode(), parameterModel);
        });
//        this.checkParameter(parameterModelMap, userModel.getLoginName());
      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(InitError.PARAMETER_ERROR.getKey());
    }
  }

  @Override
  @Transactional
  public UserRecordModel signIn(InitSystemModel initSystemModel, SessionContainer sessionContainer) {
    UserRecordModel userModel = initSystemModel.getUserModel();
    try {
      // 1. Input company details info
      ParadmCompanyModel paradmCompanyModel = initSystemModel.getParadmCompanyModel();
      if (Utility.isEmpty(paradmCompanyModel) || Utility.isEmpty(paradmCompanyModel.getId())) {
        String args = Utility.isEmpty(paradmCompanyModel) ? "" : paradmCompanyModel.getCompanyName();
        throw new ApplicationException(InitError.COMPANY_NOT_EXIST.getKey(), args);
      }
      // 2. Check if the system already exists users
      List<UserRecordModel> userModelList = userRecordService.getAllUsers();
      if (!Utility.isEmpty(userModelList)) {
        throw new ApplicationException(InitError.SIGN_IN_IS_COMPLETED.getKey());
      }
      Integer companyId = Utility.parseInteger(paradmCompanyModel.getId());
      // 3. update company info
      paradmCompanyService.updateSigninCompany(paradmCompanyModel, sessionContainer);
      // 4. update parameter setting
      List<SysParameterModel> parameterModelList = initSystemModel.getParameterModelList();
      if (!Utility.isEmpty(parameterModelList)) {

      }
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApplicationException(CommonError.COMMON_UNKNOWN_ERROR.getKey());
    }
    return userModel;
  }

  //  private void setEmailParameter(Integer companyId, List<SysParameterModel> parameterModelList) {
//    SysParameterModel parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.SMTP_AUTH);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SMTP_AUTH));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.SMTP_HOST);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SMTP_HOST));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.SMTP_PORT);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SMTP_PORT));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.SMTP_USER_NAME);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SMTP_USER_NAME));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.SMTP_PASSWORD);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.SMTP_PASSWORD));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.EMAIL_FROM);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.EMAIL_FROM));
//    parameterModelList.add(parameterModel);
//    parameterModel = new SysParameterModel();
//    parameterModel.setParameterCode(SystemParameterConstant.EMAIL_DISPLAY_NAME);
//    parameterModel.setParameterValue(SystemParameterFactory.getSystemParameter(SystemParameterConstant.EMAIL_DISPLAY_NAME));
//    parameterModelList.add(parameterModel);
//  }

//  private JavaMailSender initJavaMailSender(Map<String, SysParameterModel> parameterModelMap) {
//    JavaMailSender mailSender = new JavaMailSenderImpl();
//    ((JavaMailSenderImpl) mailSender).setHost(parameterModelMap.get(SystemParameterConstant.SMTP_HOST).getParameterValue());
//    ((JavaMailSenderImpl) mailSender).setPort(Utility.parseInt(parameterModelMap.get(SystemParameterConstant.SMTP_PORT).getParameterValue()));
//    if (GlobalConstant.TRUE.equalsIgnoreCase(parameterModelMap.get(SystemParameterConstant.SMTP_AUTH).getParameterValue())) {
//      log.debug("SMTP auth: False");
//      ((JavaMailSenderImpl) mailSender).setUsername(parameterModelMap.get(SystemParameterConstant.SMTP_USER_NAME).getParameterValue());
//      ((JavaMailSenderImpl) mailSender).setPassword(parameterModelMap.get(SystemParameterConstant.SMTP_PASSWORD).getParameterValue());
//    }
//    Properties javaMailProperties = new Properties();
//    javaMailProperties.put(EmailConstant.MAIL_SMTP_AUTH, parameterModelMap.get(SystemParameterConstant.SMTP_AUTH).getParameterValue());
//    if (Utility.parseInt(parameterModelMap.get(SystemParameterConstant.SMTP_PORT).getParameterValue()) == 587) {
//      javaMailProperties.put("mail.smtp.starttls.enable", "true");
//    }
//    ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
//    return mailSender;
//  }
//
//  private void checkParameter(Map<String, SysParameterModel> parameterModelMap, String to) {
//    MimeMessage mimeMsg = null;
//    try {
//      log.debug("checkParameter start..");
//      JavaMailSender mailSender = this.initJavaMailSender(parameterModelMap);
//      SimpleMailMessage mailMessage = new SimpleMailMessage();
//      mailMessage.setTo(to);
//      mailMessage.setFrom(parameterModelMap.get(SystemParameterConstant.EMAIL_FROM).getParameterValue());
//      mailMessage.setSubject("Test send email");
//      mailMessage.setText("This is a test email.");
//      mimeMsg = mailSender.createMimeMessage();
//      MimeMessageHelper mimeMessagehelper = new MimeMessageHelper(mimeMsg, true, GlobalConstant.ENCODE_UTF8);
//      mimeMessagehelper.setFrom(mailMessage.getFrom(), parameterModelMap.get(SystemParameterConstant.EMAIL_DISPLAY_NAME).getParameterValue());
//      mimeMessagehelper.setTo(mailMessage.getTo());
//      mimeMessagehelper.setSubject(mailMessage.getSubject());
//      mimeMessagehelper.setText(mailMessage.getText(), false);
//      mailSender.send(mimeMsg);
//      log.debug("checkParameter end..");
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//      throw new ApplicationException(ErrorConstant.EMAIL_PARAMETER_ERROR);
//    }
//  }
}
