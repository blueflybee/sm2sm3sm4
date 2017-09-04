package com.blueflybee.library;

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

//  public byte[] encryptDataNoEncode_ECB(String plainText) {
//    try {
//      SM4_Context ctx = new SM4_Context();
//      ctx.isPadding = true;
//      ctx.mode = SM4.SM4_ENCRYPT;
//
//      byte[] keyBytes;
//      if (hexString) {
//        keyBytes = Util.hexStringToBytes(secretKey);
//      } else {
//        keyBytes = secretKey.getBytes();
//      }
//
//      SM4 sm4 = new SM4();
//      sm4.sm4_setkey_enc(ctx, keyBytes);
//      byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(UTF_8));
////      String cipherText = new BASE64Encoder().encode(encrypted);
////      if (cipherText != null && cipherText.trim().length() > 0) {
////        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
////        Matcher m = p.matcher(cipherText);
////        cipherText = m.replaceAll("");
////      }
//      return encrypted;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//  }

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

//  public byte[] decryptDataNoDecode_ECB(byte[] cipherText) {
//    try {
//      SM4_Context ctx = new SM4_Context();
//      ctx.isPadding = true;
//      ctx.mode = SM4.SM4_DECRYPT;
//
//      byte[] keyBytes;
//      if (hexString) {
//        keyBytes = Util.hexStringToBytes(secretKey);
//      } else {
//        keyBytes = secretKey.getBytes();
//      }
//
//      SM4 sm4 = new SM4();
//      sm4.sm4_setkey_dec(ctx, keyBytes);
//      byte[] decrypted = sm4.sm4_crypt_ecb(ctx, cipherText);
//      return decrypted;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//  }


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


//    unsigned char SM1_SM4_KEY[16]={ 0X01,0X23,0X45,0X67,0X89,0XAB,0XCD,0XEF,0XFE,0XDC,0XBA,0X98,0X76,0X54,0X32,0X10 };
//    unsigned char SM4_PLAIN[16]={ 0X01,0X23,0X45,0X67,0X89,0XAB,0XCD,0XEF,0XFE,0XDC,0XBA,0X98,0X76,0X54,0X32,0X10 };
//    unsigned char SM4_CIPHER[16]={ 0X68,0X1E,0XDF,0X34,0XD2,0X06,0X96,0X5E,0X86,0XB3,0XE9,0X4F,0X53,0X6E,0X42,0X46 };
    System.out.println("ECB模式无编码");
    byte[] plainTextByte = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10};
    byte[] keyByte = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10};
    byte[] cipherByteText = new byte[]{0X68, 0X1E, (byte) 0XDF, 0X34, (byte) 0XD2, 0X06, (byte) 0X96, 0X5E, (byte) 0X86, (byte) 0XB3, (byte) 0XE9, 0X4F, 0X53, 0X6E, 0X42, 0X46};
    System.out.println("明文: " + Arrays.toString(plainTextByte));
//    sm4.setSecretKey(Util.encodeHexString(keyByte, true));
//    sm4.setHexString(true);
////    byte[] cipherByteText = sm4.encryptDataNoEncode_ECB(plainTextByte);
//    System.out.println("密文: " + Arrays.toString(cipherByteText));
//    System.out.println("");
//
//    cipherByteText = sm4.decryptDataNoDecode_ECB(cipherByteText);
//    System.out.println("明文: " + Arrays.toString(cipherByteText));
//    System.out.println("");

    //加密 128bit
    byte[] out = new byte[16];
    SM4Byte sm4Byte = new SM4Byte();
//    starttime = System.nanoTime();
    sm4Byte.sms4(plainTextByte, 16, keyByte, out, 1);
    System.out.println("密文: " + Arrays.toString(out));
    System.out.println("");

    System.out.println("密文expected: " + Arrays.toString(cipherByteText));
    System.out.println("");
//    for (int i = 0; i < 16; i++)
//      System.out.print(Integer.toHexString(out[i] & 0xff) + "\t");
    //解密 128bit
//    for (int i = 0; i < out.length; i++) {
//      if (i >= 9) {
//        out[i] = 0x00;
//      }
//    }
//    System.out.println("密文: " + Arrays.toString(out));
//    sm4Byte.sms4(out, 16, keyByte, out, 0);
//    System.out.println("明文: " + Arrays.toString(out));
//    System.out.println("");
//
//    byte[] plainTextByte_1 = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10};
//    byte[] plainTextByte_2 = new byte[]{0X68, 0X1E, (byte) 0XDF, 0X34, (byte) 0XD2, 0X06, (byte) 0X96, 0X5E, (byte) 0X86, (byte) 0XB3, (byte) 0XE9, 0X4F, 0X53, 0X6E, 0X42, 0X46};
//    byte[] plainTextByte_3 = new byte[]{0X01, 0X23, 0X45, 0X67, (byte) 0X89, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XFE, (byte) 0XDC, (byte) 0XBA, (byte) 0X98, 0X76, 0X54, 0X32, 0X10, 0X68, 0X1E, (byte) 0XDF, 0X34, (byte) 0XD2, 0X06, (byte) 0X96, 0X5E, (byte) 0X86, (byte) 0XB3, (byte) 0XE9, 0X4F, 0X53, 0X6E, 0X42, 0X46};
//    System.out.println("明文1: " + Arrays.toString(plainTextByte_1));
//    System.out.println("明文2: " + Arrays.toString(plainTextByte_2));
//
//    byte[] out1 = new byte[16];
//    byte[] out2 = new byte[16];
//    byte[] out3 = new byte[32];
////    starttime = System.nanoTime();
//    sm4Byte.sms4(plainTextByte_1, 16, keyByte, out1, 1);
//    sm4Byte.sms4(plainTextByte_2, 16, keyByte, out2, 1);
//    System.out.println("密文分开加密: " + Arrays.toString(out1) + Arrays.toString(out2));
//    System.out.println("");
//
//    sm4Byte.sms4(plainTextByte_3, 32, keyByte, out3, 1);
//    System.out.println("密文合并加密: " + Arrays.toString(out3));
//    System.out.println("");
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