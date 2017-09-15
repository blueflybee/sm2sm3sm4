package com.blueflybee.sm2sm3sm4.library;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class SM4Utils {
  public static final String UTF_8 = "UTF-8";
  private String secretKey = "";

  private String iv = "";

  private boolean hexString = false;

  private static String testStr = "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";

  public SM4Utils() {
  }

  public String encryptData_ECB(String plainText) {
    try {
      SM4_Context ctx = new SM4_Context();
      ctx.isPadding = true;
      ctx.mode = SM4.SM4_ENCRYPT;

      byte[] keyBytes;
      if (hexString) {
        keyBytes = Util.hexStringToBytes(secretKey);
      } else {
        keyBytes = secretKey.getBytes();
      }

      SM4 sm4 = new SM4();
      sm4.sm4_setkey_enc(ctx, keyBytes);
      byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(UTF_8));
      String cipherText = new BASE64Encoder().encode(encrypted);
      if (cipherText != null && cipherText.trim().length() > 0) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(cipherText);
        cipherText = m.replaceAll("");
      }
      return cipherText;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public String decryptData_ECB(String cipherText) {
    try {
      SM4_Context ctx = new SM4_Context();
      ctx.isPadding = true;
      ctx.mode = SM4.SM4_DECRYPT;

      byte[] keyBytes;
      if (hexString) {
        keyBytes = Util.hexStringToBytes(secretKey);
      } else {
        keyBytes = secretKey.getBytes();
      }

      SM4 sm4 = new SM4();
      sm4.sm4_setkey_dec(ctx, keyBytes);
      byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
      return new String(decrypted, UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String decryptData_ECB(String cipherText, boolean isPadding) {
    try {
      SM4_Context ctx = new SM4_Context();
      ctx.isPadding = isPadding;
      ctx.mode = SM4.SM4_DECRYPT;

      byte[] keyBytes;
      if (hexString) {
        keyBytes = Util.hexStringToBytes(secretKey);
      } else {
        keyBytes = secretKey.getBytes();
      }

      SM4 sm4 = new SM4();
      sm4.sm4_setkey_dec(ctx, keyBytes);
      byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
      return new String(decrypted, UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public String encryptData_CBC(String plainText) {
    try {
      SM4_Context ctx = new SM4_Context();
      ctx.isPadding = true;
      ctx.mode = SM4.SM4_ENCRYPT;

      byte[] keyBytes;
      byte[] ivBytes;
      if (hexString) {
        keyBytes = Util.hexStringToBytes(secretKey);
        ivBytes = Util.hexStringToBytes(iv);
      } else {
        keyBytes = secretKey.getBytes();
        ivBytes = iv.getBytes();
      }

      SM4 sm4 = new SM4();
      sm4.sm4_setkey_enc(ctx, keyBytes);
      byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes(UTF_8));
      String cipherText = new BASE64Encoder().encode(encrypted);
      if (cipherText != null && cipherText.trim().length() > 0) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(cipherText);
        cipherText = m.replaceAll("");
      }
      return cipherText;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String decryptData_CBC(String cipherText) {
    try {
      SM4_Context ctx = new SM4_Context();
      ctx.isPadding = true;
      ctx.mode = SM4.SM4_DECRYPT;

      byte[] keyBytes;
      byte[] ivBytes;
      if (hexString) {
        keyBytes = Util.hexStringToBytes(secretKey);
        ivBytes = Util.hexStringToBytes(iv);
      } else {
        keyBytes = secretKey.getBytes();
        ivBytes = iv.getBytes();
      }

      SM4 sm4 = new SM4();
      sm4.sm4_setkey_dec(ctx, keyBytes);
      byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
      return new String(decrypted, UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) throws IOException {
    String plainText = "abcd";

    SM4Utils sm4 = new SM4Utils();
    sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
    sm4.setHexString(false);

    System.out.println("ECB模式");
    String cipherText = sm4.encryptData_ECB(plainText);
    System.out.println("密文: " + cipherText);
    System.out.println("");

    plainText = sm4.decryptData_ECB(cipherText);
    System.out.println("明文: " + plainText);
    System.out.println("");

    System.out.println("CBC模式");
    sm4.setIv("UISwD9fW6cFh9SNS");
    cipherText = sm4.encryptData_CBC(plainText);
    System.out.println("密文: " + cipherText);
    System.out.println("");

    plainText = sm4.decryptData_CBC(cipherText);
    System.out.println("明文: " + plainText);
    System.out.println("//////////////////////////////");


    System.out.println("ECB模式无编码");
    byte[] plainTextByte = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10};
    byte[] keyByte = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10};
    byte[] cipherByteText = new byte[]{0X68, 0X1E, (byte) 0XDF, 0X34, (byte) 0XD2, 0X06, (byte) 0X96, 0X5E, (byte) 0X86, (byte) 0XB3, (byte) 0XE9, 0X4F, 0X53, 0X6E, 0X42, 0X46};
    System.out.print("明文: ");
    for (int i = 0; i < 16; i++)
      System.out.print(Integer.toHexString(plainTextByte[i] & 0xff) + "\t");
    System.out.println("");
//
//    cipherByteText = sm4.decryptDataNoDecode_ECB(cipherByteText);
//    System.out.println("明文: " + Arrays.toString(cipherByteText));
//    System.out.println("");

    //加密 128bit
    byte[] out = new byte[16];
    SM4Byte sm4Byte = new SM4Byte();
//    starttime = System.nanoTime();
    sm4Byte.sms4(plainTextByte, 16, keyByte, out, 1);
    System.out.print("密文: ");
    for (int i = 0; i < 16; i++)
      System.out.print(Integer.toHexString(out[i] & 0xff) + "\t");
    System.out.println("");

    System.out.print("密文: ");
    for (int i = 0; i < 16; i++)
      System.out.print(Integer.toHexString(cipherByteText[i] & 0xff) + "\t");
    System.out.println("");

    //////////////////////////////////////////////////////////////////////////////////////
    byte[] testKeyByte1 = new byte[]{0x4b, 0x77, (byte) 0xa4, 0x47, (byte) 0xd6, (byte) 0xb5, (byte) 0xe7, 0x22, 0x2a, (byte) 0xc9, 0x6e, 0x39, 0x6a, (byte) 0xb7, (byte) 0xa8, 0x37};

    String testCipherText = "PPnoBLUnCfh/Ce5OdG096epu20hYdMWkiNWmfKM+HEZeo/wqYO/k1DhXmFpqTd+4GzfZ4xHDxcpNsksdr6GbV3+gIsWx/eNQptKxPNn0u2ZDrWiOOJSOxCHVOcbj4jPb8LKaEKsfLyFWC+1LgcHlj8xVeSXi7CJwgQlCH18FcMOAOe1Vj+n7nUeBFtmf3ntLY/5LtAkxPyY90/jiQzmCAjGWIpfoO3lWfKuitbUWOhopepbaFwqPOWQtwipcUdNKJRW33Q0BOWgTH/ApT17/85Fl0yrj8oWbwUKH/eaqhDVkRuhyuKn6vbpba6BSnVxj6zBCvhsKILtFuwQpJqdGxXxtkzuk7RvzvTMfM06VaRWJ/6/U2Lv+LwllbqNbEqXN7JGT38heDcp4OuPolPJbC7A/5pjfqiYsoHxK0Gm2dLe8a4m+pYlKcPXWKa7eRJiUQRqbEhy+NBWEQ8NSOMDBQ7x8Y5I2Ye4LD72fA9RNsOYplCWFeM6KZTDT/2yEPdIkgLrmzEe4cgRClQoMuZlOk7AYI2nkumLetWwROD2WTfLYtzZcvloHtBF8qw8ou5U+wKioim4uPsAoMe4DegIhzyPwJxLs2GN5IUb15/Yo8EbooNDpa7kJ1P9CEz6nvKOL6atzRv1QsU46nDeXMqym9665nFb0PZxEto6Y2a4OGVghBDU4BdOLic9F+ctEx8P3JzoF2e1+Flp5jr3OR+TwC1zUfqRMlUOyk4uq8lkWT6V3tEPc+9Q2plUjRjzE97x56ltnQY5ogjDh1dOH38JC1dUYKy0dlDktSNVKiQIyiHYJ8bRSRzlmVFL7tN7ReQy3gDntVY/p+51HgRbZn957S4wh3TksUtpRaaaUlXQWnbBTohiRPOccXDA7yANiDh0QbU2ZTlKa7MUXR0XcGYaTaOgPAXcJX2v1YWCPGdZ3i5Zx2W7s0YoFOxqw6VxUkacg";
    String key = Util.encodeHexString(testKeyByte1);
    System.out.println("key = " + key);
    sm4.setSecretKey(key);
    sm4.setHexString(true);

    System.out.println("ECB模式");

    plainText = sm4.decryptData_ECB(testCipherText);
    System.out.println("明文: " + plainText);
    System.out.println("");

    String keyBase64 = "g0NfBacZwUg5xklALd1Tkw==";
    byte[] bytes = new BASE64Decoder().decodeBuffer(keyBase64);
    String result = Util.encodeHexString(bytes);
    System.out.println("result = " + result);
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getIv() {
    return iv;
  }

  public void setIv(String iv) {
    this.iv = iv;
  }

  public boolean isHexString() {
    return hexString;
  }

  public void setHexString(boolean hexString) {
    this.hexString = hexString;
  }
}