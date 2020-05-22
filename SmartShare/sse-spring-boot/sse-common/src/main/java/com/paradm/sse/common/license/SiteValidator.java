package com.paradm.sse.common.license;

import com.paradm.sse.common.xml.XMLUtility;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.Hashtable;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public enum SiteValidator {

  INSTANCE;

  private static final Object lock = new Object();

  static Document document;
  private Hashtable<String, ModuleLicenceInfo> modules;
  private LicenceInfo licenceInfo;
  private XMLUtility xmlUtility;
  private InputStream is;

  private boolean readFlag = false;
  private boolean isValidLicense = false;

  public void init(InputStream is) {
    this.is = is;
    INSTANCE.getXMLElements();
  }

  private void getXMLElements() {
    synchronized (lock) {
      if (this.readFlag) {
        return;
      }

      document = null;
      modules = new Hashtable<>();
      licenceInfo = new LicenceInfo();
      xmlUtility = new XMLUtility(is);

      try {
        NodeList rootlist = xmlUtility.getNodeListByTagName("MODULE");
        for (int intloop = 0; intloop < rootlist.getLength(); intloop++) {
          ModuleLicenceInfo ml = new ModuleLicenceInfo();
          Element node = (Element) rootlist.item(intloop);

          ml.setName(node.getElementsByTagName("MODULE_CODE").item(0).getChildNodes().item(0).getNodeValue());
          if (node.getElementsByTagName("CONCURRENT_USER") != null && node.getElementsByTagName("CONCURRENT_USER").getLength() > 0) {
            ml.setType("CONCURRENT_USER");
            ml.setNo(Integer.parseInt(node.getElementsByTagName("CONCURRENT_USER").item(0).getChildNodes().item(0).getNodeValue()));
          } else {
            ml.setType("NAMED_USER");
            ml.setNo(Integer.parseInt(node.getElementsByTagName("NAMED_USER").item(0).getChildNodes().item(0).getNodeValue()));
          }
          log.info(ml.getName() + ":" + ml.getType() + ":" + ml.getNo());
          modules.put(ml.getName(), ml);
        }

        Element infoNode = (Element) xmlUtility.getNodeListByTagName("INFORMATION").item(0);
        licenceInfo = new LicenceInfo();
        licenceInfo.setKey(infoNode.getElementsByTagName("LICENSE_KEY").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setExpDate(infoNode.getElementsByTagName("EXPIRY_DATE").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setMaintenanceStartDate(infoNode.getElementsByTagName("MAINTENANCE_START_DATE").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setMaintenanceEndDate(infoNode.getElementsByTagName("MAINTENANCE_END_DATE").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setIp(infoNode.getElementsByTagName("HOST_IP").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setComName(infoNode.getElementsByTagName("COMPANY_NAME").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setVersion(infoNode.getElementsByTagName("VERSION").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setConcurrent_user(infoNode.getElementsByTagName("CONCURRENT_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setSystem_user(infoNode.getElementsByTagName("SYSTEM_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setParascan_user(infoNode.getElementsByTagName("PARASCAN_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setClient_user(infoNode.getElementsByTagName("CLIENT_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setUploadFileNum(infoNode.getElementsByTagName("UPLOAD_FILE_NUM").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setStorageSpace(infoNode.getElementsByTagName("STORAGE_SPACE").item(0).getChildNodes().item(0).getNodeValue());
        this.readFlag = true;
      } catch (Exception e) {
        log.error("Error in parsing License File.", e);
        throw e;
      }
    }
  }

}
