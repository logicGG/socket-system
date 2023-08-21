package club.ovelya.socketsystem.utils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @version V1.0 &#064;desc  AES 加密工具类
 */
@Component
@Slf4j
public class AESUtil {

  private static final String KEY_ALGORITHM = "AES";
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
  private static final Encoder encoder = Base64.getEncoder();
  private static final Decoder decoder = Base64.getDecoder();
  //自定义密码
  private static String ASSETS_DEV_PWD_FIELD;

  public static String getAssetsDevPwdField() {
    return ASSETS_DEV_PWD_FIELD;
  }

  @Value("${aes-password}")
  public void setAssetsDevPwdField(String assetsDevPwdField) {
    ASSETS_DEV_PWD_FIELD = assetsDevPwdField;
  }

  /**
   * AES 加密操作
   *
   * @param content 待加密内容
   * @return 返回Base64转码后的加密数据
   */
  public static String encrypt(String content) {
    try {
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
      byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(ASSETS_DEV_PWD_FIELD));// 初始化为加密模式的密码器
      byte[] result = cipher.doFinal(byteContent);// 加密
      return encoder.encodeToString(result);//通过Base64转码返回
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return null;
  }

  /**
   * AES 解密操作
   *
   * @param content 解密密文
   * @return 明文
   */
  public static String decrypt(String content) {

    try {
      //实例化
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      //使用密钥初始化，设置为解密模式
      cipher.init(Cipher.DECRYPT_MODE, getSecretKey(ASSETS_DEV_PWD_FIELD));
      //执行操作
      byte[] result = cipher.doFinal(decoder.decode(content));
      return new String(result, StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return null;
  }

  /**
   * 生成加密秘钥
   *
   * @return AES密钥
   */
  private static SecretKeySpec getSecretKey(String password) {
    //返回生成指定算法密钥生成器的 KeyGenerator 对象
    KeyGenerator kg;
    try {
      kg = KeyGenerator.getInstance(KEY_ALGORITHM);
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      random.setSeed(password.getBytes());
      //AES 要求密钥长度为 128
      kg.init(128, random);
      //生成一个密钥
      SecretKey secretKey = kg.generateKey();
      return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage());
    }
    return null;
  }

}