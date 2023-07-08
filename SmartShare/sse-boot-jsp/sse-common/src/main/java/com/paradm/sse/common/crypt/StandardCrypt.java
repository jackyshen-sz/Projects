package com.paradm.sse.common.crypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class StandardCrypt {

  public static final String KEY = "ParaDM";
  public static final int BYTE_KEY = 256;
  public static final String AES = "AES";
  public static final String AES_ECB = "AES/ECB/PKCS5Padding";

  /**
   * Encrypt a string with a password
   * 
   * @param buffer The buffer to be encrypted
   * @param password The password
   * @return the encrypt password.
   */
  public static String encrypt(String buffer, String password) {
    StringBuffer sbSpace = new StringBuffer("     ");
    buffer = sbSpace.toString() + buffer + sbSpace.toString();
    char[] temp = new char[buffer.length()];

    int a = 0;
    for (int i = 0; i < buffer.length(); i++) {
      int b = password.charAt(a);
      a++;
      if (a >= password.length()) {
        a = 0;
      }
      int c = buffer.charAt(i);
      temp[i] = (char) (c ^ b);
    }
    return toHex(temp);
  }

  /**
   * Decrypt a string with a password
   * 
   * @param buffer The buffer to be decrypted
   * @param password The password
   * @return the decrypt password.
   */
  public static String decrypt(String buffer, String password) {
    char[] temp = toChar(buffer);
    String result = "";
    int a = 0;
    for (int i = 0; i < temp.length; i++) {
      int b = password.charAt(a);
      a++;
      if (a >= password.length()) {
        a = 0;
      }
      int c = temp[i];
      result += (char) (c ^ b);
    }
    return result.trim();
  }

  private static int hexToInt(char c) {
    if (c >= '0' && c <= '9') {
      return c - '0';
    }
    return (c - 'a') + 10;
  }

  private static char hexToChar(String buffer) {
    int l = hexToInt(buffer.charAt(1));
    int h = hexToInt(buffer.charAt(0));
    return (char) (h * 16 + l);
  }

  private static String toHex(char[] buffer) {
    String temp = "";
    String result = "";
    for (int i = 0; i < buffer.length; i++) {
      int c = buffer[i];
      temp = Integer.toHexString(c);
      if (temp.length() == 1) {
        temp = "0" + temp;
      }
      result += temp;
    }
    return result;
  }

  private static char[] toChar(String buffer) {
    char[] result = new char[buffer.length() / 2];
    for (int i = 0; i < buffer.length(); i += 2) {
      if (i + 2 <= buffer.length()) {
        String temp = buffer.substring(i, i + 2);
        result[i / 2] = hexToChar(temp);
      }
    }
    return result;
  }

  public static String hashEncrypt(String algorithm, String password) {
    String result = "";
    switch (algorithm) {
      case MessageDigestAlgorithms.MD5:
        result = DigestUtils.md5Hex(password);
        break;
      case MessageDigestAlgorithms.SHA_256:
        result = DigestUtils.sha256Hex(password);
        break;
      case MessageDigestAlgorithms.SHA_384:
        result = DigestUtils.sha384Hex(password);
        break;
      case MessageDigestAlgorithms.SHA_512:
        result = DigestUtils.sha512Hex(password);
        break;
      default:
        result = DigestUtils.sha1Hex(password);
        break;
    }
    return result;
  }
}
