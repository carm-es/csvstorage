/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.gob.aapp.csvstorage.util;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import es.gob.aapp.csvstorage.exception.EncryptException;


public class CryptoUtil {

  private static final int HASH_BYTE_SIZE = 64; // 512 bits
  private static final int PBKDF2_ITERATIONS = 1000;

  private static final String ALGORITMO = "PBEWithMD5AndDES";

  private static Cipher ecipher;
  private static Cipher dcipher;
  // 8-byte Salt
  private static byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56,
      (byte) 0x35, (byte) 0xE3, (byte) 0x03};
  // Iteration count
  private static int iterationCount = 19;

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(CryptoUtil.class);

  /**
   * 
   * @param secretKey Key used to encrypt data
   * @param plainText Text input to be encrypted
   * @throws EncryptException error
   * @return Returns encrypted text
   * 
   */
  public static String encrypt(String secretKey, String plainText) throws EncryptException {
    String encStr = null;
    try {
      if (StringUtils.isNotEmpty(secretKey) && StringUtils.isNotEmpty(plainText)) {
        // Key generation for enc and desc
        PBEKeySpec keySpec =
            new PBEKeySpec(secretKey.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        SecretKey key = SecretKeyFactory.getInstance(ALGORITMO).generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        // Enc process
        ecipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        String charSet = "UTF-8";
        byte[] in = plainText.getBytes(charSet);
        byte[] out = ecipher.doFinal(in);
        encStr = DatatypeConverter.printBase64Binary(out);
      } else if (StringUtils.isEmpty(secretKey)) {
        LOG.error("No existe ninguna clave con la que encriptar la password");
        throw new EncryptException("Se ha producido un error al encriptar las password");
      }
    } catch (Exception e) {
      LOG.error("Se ha producido un error al encriptar las password", e);
      throw new EncryptException("Se ha producido un error al encriptar las password", e);
    }
    return encStr;
  }

  /**
   * @param secretKey Key used to decrypt data
   * @param encryptedText encrypted text input to decrypt
   * @return Returns plain text after decryption
   * @throws EncryptException error
   */
  public static String decrypt(String secretKey, String encryptedText) throws EncryptException {
    String plainStr = null;
    try {
      if (StringUtils.isNotEmpty(secretKey) && StringUtils.isNotEmpty(encryptedText)) {
        // Key generation for enc and desc
        PBEKeySpec keySpec =
            new PBEKeySpec(secretKey.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        SecretKey key = SecretKeyFactory.getInstance(ALGORITMO).generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        // Decryption process; same key will be used for decr
        dcipher = Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        byte[] enc = DatatypeConverter.parseBase64Binary(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet = "UTF-8";
        plainStr = new String(utf8, charSet);
      } else if (StringUtils.isEmpty(secretKey)) {
        LOG.error("No existe ninguna clave con la que desencriptar la password");
        throw new EncryptException("Se ha producido un error al desencriptar las password");
      }
      return plainStr;
    } catch (Exception e) {
      LOG.error("Se ha producido un error al desencriptar las password", e);
      throw new EncryptException("Se ha producido un error al desencriptar las password", e);
    }
  }

}
