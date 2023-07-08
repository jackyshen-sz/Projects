package com.paradm.sse.services.init;

import com.paradm.sse.domain.framework.model.SessionContainer;
import com.paradm.sse.domain.init.model.InitSystemModel;
import com.paradm.sse.domain.user.model.UserRecordModel;
import org.springframework.ui.Model;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
public interface IInitSystemService extends IInitService {

  void initSystemForm(Model model, String baseUrl);

  void check(InitSystemModel initSystemModel);

  UserRecordModel signIn(InitSystemModel initSystemModel, SessionContainer sessionContainer);
}
