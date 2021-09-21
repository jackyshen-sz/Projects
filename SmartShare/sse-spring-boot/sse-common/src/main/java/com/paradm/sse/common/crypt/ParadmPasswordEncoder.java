package com.paradm.sse.common.crypt;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.springframework.security.crypto.util.EncodingUtils.concatenate;
import static org.springframework.security.crypto.util.EncodingUtils.subArray;

/**
 * @author Jackyshen
 */
public final class ParadmPasswordEncoder implements PasswordEncoder {

  private static final int DEFAULT_ITERATIONS = 1024;

  private final Digester digester;
  private final byte[] secret;

  private BytesKeyGenerator saltGenerator;

  private String algorithm;
  private boolean hasSalt;
  private int iterations = DEFAULT_ITERATIONS;

  public String getAlgorithm() {
    return algorithm;
  }

  public boolean isHasSalt() {
    return hasSalt;
  }

  public int getIterations() {
    return iterations;
  }

  public ParadmPasswordEncoder(String algorithm) {
    this(algorithm, true);
  }

  public ParadmPasswordEncoder(String algorithm, boolean hasSalt) {
    this(algorithm, hasSalt, DEFAULT_ITERATIONS);
  }

  public ParadmPasswordEncoder(String algorithm, boolean hasSalt, int iterations) {
    this(algorithm, hasSalt, iterations, "");
  }

  private ParadmPasswordEncoder(String algorithm, boolean hasSalt, int iterations, CharSequence secret) {
    this.algorithm = algorithm;
    this.hasSalt = hasSalt;
    this.iterations = iterations;
    this.digester = new Digester(algorithm, iterations);
    this.secret = Utf8.encode(secret);
    if (hasSalt) {
      this.saltGenerator = KeyGenerators.secureRandom();
    }
  }

  @Override
  public String encode(CharSequence rawPassword) {
    byte[] salt = new byte[] {};
    if (!ObjectUtil.isEmpty(saltGenerator)) {
      salt = saltGenerator.generateKey();
    }
    return encode(rawPassword, salt);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    if (StrUtil.isEmpty(encodedPassword)) {
      return false;
    }
    byte[] digested = decode(encodedPassword);
    int keyLength = 0;
    if (!ObjectUtil.isEmpty(saltGenerator)) {
      keyLength = saltGenerator.getKeyLength();
    }
    byte[] salt = subArray(digested, 0, keyLength);
    return matches(digested, digest(rawPassword, salt));
  }

  private String encode(CharSequence rawPassword, byte[] salt) {
    byte[] digest = digest(rawPassword, salt);
    return new String(Hex.encode(digest));
  }

  private byte[] digest(CharSequence rawPassword, byte[] salt) {
    byte[] digest = digester.digest(concatenate(salt, secret, Utf8.encode(rawPassword)));
    return concatenate(salt, digest);
  }

  private byte[] decode(CharSequence encodedPassword) {
    return Hex.decode(encodedPassword);
  }

  /**
   * Constant time comparison to prevent against timing attacks.
   */
  private boolean matches(byte[] expected, byte[] actual) {
    if (expected.length != actual.length) {
      return false;
    }
    int result = 0;
    for (int i = 0; i < expected.length; i++) {
      result |= expected[i] ^ actual[i];
    }
    return result == 0;
  }

  static final class Digester {

    private final MessageDigest messageDigest;
    private final int iterations;

    /**
     * Create a new Digester.
     * 
     * @param algorithm the digest algorithm; for example, "SHA-1" or "SHA-256".
     * @param iterations the number of times to apply the digest algorithm to the input
     */
    public Digester(String algorithm, int iterations) {
      try {
        messageDigest = MessageDigest.getInstance(algorithm);
      } catch (NoSuchAlgorithmException e) {
        throw new IllegalStateException("No such hashing algorithm", e);
      }

      this.iterations = iterations;
    }

    public byte[] digest(byte[] value) {
      synchronized (messageDigest) {
        for (int i = 0; i < iterations; i++) {
          value = messageDigest.digest(value);
        }
        return value;
      }
    }
  }
}
