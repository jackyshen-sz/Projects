package com.paradm.sse.common.constant;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public interface GlobalConstant extends Constant {

  String getValue();

  String APPLICATION_NAME = "SMARTSHARE-ENTERPRISE";

  String VERSION = "1.0.0.0";

  String FUNCTION_CODE_KEY = "FUNCTION_CODE";

  /**
   * The key which used to retrieve the ApplicationContainer in application scope.
   */
  String APPLICATION_CONTAINER_KEY = "applicationContainer";

  String SESSION_CONTAINER_KEY = "sessionContainer";

}
