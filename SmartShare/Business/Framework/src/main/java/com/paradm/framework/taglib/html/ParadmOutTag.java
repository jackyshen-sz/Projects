package com.paradm.framework.taglib.html;

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.tag.rt.core.OutTag;

import com.paradm.common.Utility;

public class ParadmOutTag extends OutTag {

  private static final long serialVersionUID = -488334904695256107L;

  private String valueLength = null;
  private String valueType = null;

  public ParadmOutTag() {
    super();
  }

  public void setValue(Object value) {
    if (value instanceof String) {
      String result = (String) value;
      int index = Utility.isEmpty(valueLength) ? 0 : Utility.parseInteger(valueLength).intValue();
      String ext = "";
      String pre = result;
      if ("D".equals(this.valueType)) {
        if (result.lastIndexOf(".") != -1) {
          pre = result.substring(0, result.lastIndexOf("."));
          ext = result.substring(result.lastIndexOf("."));
        }
      }
      if (index > 0 && index < pre.length()) {
        result = pre.substring(0, index) + "..." + ext;
      }
      this.value = result;
    } else {
      this.value = value;
    }
  }

  @Override
  public int doStartTag() throws JspException {
    this.setValue(this.value);
    return super.doStartTag();
  }

  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public String getValueLength() {
    return valueLength;
  }

  public void setValueLength(String valueLength) {
    this.valueLength = valueLength;
  }

}
