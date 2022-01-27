package szs.findrefund.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static javax.crypto.Cipher.*;
import static szs.findrefund.common.Constants.AESCryptoConst.*;

public class AESCryptoUtil {

  /**
   * 암호화
   */
  public static String encrypt(String plainText) throws Exception {
    Cipher cipher = getInstance(SPEC_NAME);
    SecretKeySpec keySpec = new SecretKeySpec(SPEC_KEY, "AES");

    cipher.init(ENCRYPT_MODE, keySpec, new IvParameterSpec(IV));
    byte[] encrypted = cipher.doFinal(plainText.getBytes(UTF_8));
    return new String(getEncoder().encode(encrypted));
  }

  /**
   * 복호화
   */
  public static String decrypt(String cipherText) throws Exception {
    Cipher cipher = getInstance(SPEC_NAME);
    SecretKeySpec keySpec = new SecretKeySpec(SPEC_KEY, "AES");

    cipher.init(DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
    byte[] decrypted = cipher.doFinal(getDecoder().decode(cipherText));
    return new String(decrypted, UTF_8);
  }

}
