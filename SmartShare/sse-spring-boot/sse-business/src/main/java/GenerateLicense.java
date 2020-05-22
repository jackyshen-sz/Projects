import com.paradm.sse.common.crypt.StandardCrypt;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class GenerateLicense {
  static PrintStream out = null;
  static String company_name = "";
  static String expire_date = "";
  static String maintenance_start_date = "";
  static String maintenance_end_date = "";
  static String hostIP = "";
  static String version = "";
  static String conCurrentUser = "";
  static String licenseKey = "";
  static String systemUser = "";
  static String parascanUser = "";
  static String clientUser = "";
  static String uploadFileNum = "";
  static String storageSpace = "";

  public static void main(String[] argv) throws Exception {
    Properties props = new Properties();
    String path = GenerateLicense.class.getClassLoader().getResource("license.properties").getPath();
    File inputFile = new File(path);
    if (!inputFile.exists()) {
      System.out.println("license.properties not exist...");
    }
    String outputFile = inputFile.getParent() + "/eip-license.xml";
    props.load(new FileInputStream(inputFile));
    company_name = props.getProperty("COMPANY_NAME");
    expire_date = props.getProperty("EXPIRY_DATE");
    maintenance_start_date = props.getProperty("MAINTENANCE_START_DATE");
    maintenance_end_date = props.getProperty("MAINTENANCE_END_DATE");
    hostIP = props.getProperty("HOST_IP");
    version = props.getProperty("VERSION");
    conCurrentUser = props.getProperty("CONCURRENT_USER");
    systemUser = props.getProperty("SYSTEM_USER");
    parascanUser = props.getProperty("PARASCAN_USER");
    clientUser = props.getProperty("CLIENT_USER");
    uploadFileNum = props.getProperty("UPLOAD_FILE_NUM");
    storageSpace = props.getProperty("STORAGE_SPACE");
    licenseKey = generateLicenseKey();
    openSrcFile(outputFile);
    generateXML();
    closeSrcFile();
    System.out.println("License generated success...");
    System.out.println("License File Location: " + outputFile);
  }

  public static String generateLicenseKey() throws Exception {
    String s = "";
    s += company_name;
    s += hostIP;
    s += expire_date;
    s += version;
    s += systemUser;
    s += conCurrentUser;
    s += parascanUser;
    s += clientUser;
    s += uploadFileNum;
    s += storageSpace;
    return StandardCrypt.hashEncrypt(MessageDigestAlgorithms.MD5, s);
  }

  public static void openSrcFile(String openFile) throws Exception {
    try {
      out = new PrintStream(new FileOutputStream(openFile, false), true);
    } catch (Exception e) {
      out = System.out;
    }
  }

  public static void generateXML() {
    String tmpCompany = company_name.replaceAll("&", "&amp;");
    out.println("<DCIVISION>");
    out.println("  <INFORMATION> ");
    out.println("    <LICENSE_KEY>" + licenseKey + "</LICENSE_KEY>");
    out.println("    <EXPIRY_DATE>" + expire_date + "</EXPIRY_DATE>");
    out.println("    <MAINTENANCE_START_DATE>" + maintenance_start_date + "</MAINTENANCE_START_DATE>");
    out.println("    <MAINTENANCE_END_DATE>" + maintenance_end_date + "</MAINTENANCE_END_DATE>");
    out.println("    <COMPANY_NAME>" + tmpCompany + "</COMPANY_NAME>");
    out.println("    <HOST_IP>" + hostIP + "</HOST_IP>");
    out.println("    <VERSION>" + version + "</VERSION>");
    out.println("    <SYSTEM_USER>" + systemUser + "</SYSTEM_USER>");
    out.println("    <CONCURRENT_USER>" + conCurrentUser + "</CONCURRENT_USER>");
    out.println("    <PARASCAN_USER>" + parascanUser + "</PARASCAN_USER>");
    out.println("    <CLIENT_USER>" + clientUser + "</CLIENT_USER>");
    out.println("    <UPLOAD_FILE_NUM>" + uploadFileNum + "</UPLOAD_FILE_NUM>");
    out.println("    <STORAGE_SPACE>" + storageSpace + "</STORAGE_SPACE>");
    out.println("  </INFORMATION> ");
    out.println("</DCIVISION>");
  }

  public static void closeSrcFile() throws Exception {
    try {
      out.close();
    } catch (Exception ignore) {
    } finally {
      out = null;
    }
  }
}
