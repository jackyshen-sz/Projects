package com.paradm.sse.services.dms;

import com.paradm.sse.domain.dms.model.SmartshareLocMasterModel;
import com.paradm.sse.services.framework.IBaseService;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
public interface ISmartshareLocMasterService extends IBaseService {

  SmartshareLocMasterModel getDefaultLocMaster();
}
