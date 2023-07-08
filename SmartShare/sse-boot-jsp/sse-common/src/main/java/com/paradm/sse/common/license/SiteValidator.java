package com.paradm.sse.common.license;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.paradm.sse.common.constant.global.Symbol;
import com.paradm.sse.common.crypt.StandardCrypt;
import com.paradm.sse.common.xml.XMLUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public enum SiteValidator {

  /**
   *
   */
  INSTANCE;

  private static final Object LOCK = new Object();

  static Document document;
  private Hashtable<String, ModuleLicenceInfo> modules;
  private LicenceInfo licenceInfo;
  private InputStream is;

  private boolean readFlag = false;
  private boolean isValidLicense = false;

  public void init(InputStream is) {
    this.is = is;
    INSTANCE.getXMLElements();
  }

  private void getXMLElements() {
    synchronized (LOCK) {
      if (this.readFlag) {
        return;
      }
      document = null;
      modules = new Hashtable<>();
      licenceInfo = new LicenceInfo();
      XMLUtility xmlUtility = new XMLUtility(is);

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
        licenceInfo.setConcurrentUser(infoNode.getElementsByTagName("CONCURRENT_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setSystemUser(infoNode.getElementsByTagName("SYSTEM_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setParascanUser(infoNode.getElementsByTagName("PARASCAN_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setClientUser(infoNode.getElementsByTagName("CLIENT_USER").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setUploadFileNum(infoNode.getElementsByTagName("UPLOAD_FILE_NUM").item(0).getChildNodes().item(0).getNodeValue());
        licenceInfo.setStorageSpace(infoNode.getElementsByTagName("STORAGE_SPACE").item(0).getChildNodes().item(0).getNodeValue());
        this.readFlag = true;
      } catch (Exception e) {
        log.error("Error in parsing License File.", e);
        throw e;
      }
    }
  }

  public boolean validateLicence() {
    if (!this.readFlag) {
      getXMLElements();
    }
    boolean flag = false;
    synchronized (LOCK) {
      if (validateAddress()) {
        String s = "";
        s += licenceInfo.getComName();
        s += licenceInfo.getIp();
        s += licenceInfo.getExpDate();
        s += licenceInfo.getVersion();
        s += licenceInfo.getSystemUser();
        s += licenceInfo.getConcurrentUser();
        s += licenceInfo.getParascanUser();
        s += licenceInfo.getClientUser();
        s += licenceInfo.getUploadFileNum();
        s += licenceInfo.getStorageSpace();
        try {
          if (StandardCrypt.hashEncrypt(MessageDigestAlgorithms.MD5, s).equals(licenceInfo.getKey().trim())) {
            flag = true;
          }
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
      this.isValidLicense = flag;
    }
    return flag;
  }

  private boolean validateAddress() {
    getXMLElements();
    boolean flag = false;
    try {
      InetAddress[] addresses;
      addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
      if (addresses.length == 1 && "127.0.0.1".equals(addresses[0].getHostAddress())) {
        flag = true;
      } else {
        for (InetAddress address : addresses) {
          if (withinSubnet(address.getHostAddress())) {
            return (true);
          }
        }
      }
    } catch (UnknownHostException e) {
      log.error("Error When Validating Host", e);
    }
    return flag;
  }

  private boolean withinSubnet(String hostAddr) {
    String[] keyArry = null;
    String[] hostArry = null;
    boolean flag = true;

    keyArry = CharSequenceUtil.split(licenceInfo.getIp(), Symbol.DOT.getValue());
    hostArry = CharSequenceUtil.split(hostAddr, Symbol.DOT.getValue());

    if (ArrayUtil.isNotEmpty(keyArry) && ArrayUtil.isNotEmpty(hostArry)) {
      for (int i = 0; i < 4; i++) {
        if (!(keyArry[i].equals(hostArry[i]) || "*".equals(keyArry[i]))) {
          flag = false;
          break;
        }
      }
    }

    return flag;
  }

  public LicenceInfo getLicenceInfo() {
    return licenceInfo;
  }

  public String getExpDate() {
    return licenceInfo.getExpDate();
  }

  public void reload() {
    this.readFlag = false;
    this.isValidLicense = false;
    this.getXMLElements();
  }
}
