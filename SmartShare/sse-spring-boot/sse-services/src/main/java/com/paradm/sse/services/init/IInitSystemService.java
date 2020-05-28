package com.paradm.sse.services.init;

import com.paradm.sse.services.framework.IBaseService;
import org.springframework.ui.Model;

/**
 * @author Jacky.shen
 * @create data 2020/5/28
 */
public interface IInitSystemService extends IBaseService {

  void initSystemForm(Model model, String baseUrl);

}
